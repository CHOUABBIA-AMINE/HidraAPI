/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : FoundationIdentityLifecyclePostgresIntegrationTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-03
 *
 * @Type        : Class
 * @Layer       : Infrastructure Test
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.infrastructure.persistence
 *
 * @Description : Verifies real fresh-schema PostgreSQL Identity lifecycle CAS, revocation, evidence and rollback.
 *
 */
package dz.sh.hidra.modules.identity.infrastructure.persistence;

import dz.sh.hidra.HidraApplication;
import dz.sh.hidra.modules.identity.application.port.in.FoundationIdentityLifecycleUseCase.GrantKind;
import dz.sh.hidra.modules.identity.infrastructure.persistence.adapter.JdbcFoundationIdentityLifecycleAdapter;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.transaction.TransactionDefinition.ISOLATION_READ_COMMITTED;

@Testcontainers(disabledWithoutDocker = true)
@SpringBootTest(classes=HidraApplication.class, webEnvironment=SpringBootTest.WebEnvironment.MOCK)
@ActiveProfiles("test")
class FoundationIdentityLifecyclePostgresIntegrationTest {
    @Container static final PostgreSQLContainer<?> PG = new PostgreSQLContainer<>("postgres:16")
            .withDatabaseName("hidra_test");
    @DynamicPropertySource static void properties(DynamicPropertyRegistry r) {
        r.add("spring.datasource.url",PG::getJdbcUrl);
        r.add("spring.datasource.username",PG::getUsername);
        r.add("spring.datasource.password",PG::getPassword);
        r.add("spring.jpa.hibernate.ddl-auto",() -> "validate");
        r.add("spring.flyway.enabled",() -> "true");
        r.add("spring.flyway.validate-on-migrate",() -> "true");
    }

    @Autowired JdbcTemplate jdbc;
    @Autowired JdbcFoundationIdentityLifecycleAdapter adapter;
    @Autowired PlatformTransactionManager manager;

    private String id() {return UUID.randomUUID().toString();}
    private TransactionTemplate tx() {
        var t = new TransactionTemplate(manager);
        t.setIsolationLevel(ISOLATION_READ_COMMITTED);
        return t;
    }

    private String createUser(String status) {
        String user=id();
        jdbc.update("INSERT INTO public.hidra_identity_user"
                +"(id,username,user_type,status,failed_login_count,created_at,updated_at)"
                +" VALUES (?,?,'HUMAN',?,0,clock_timestamp(),clock_timestamp())",
                user,"fixture-"+user,status);
        return user;
    }

    private String role() {
        var ids=jdbc.queryForList("SELECT id FROM public.hidra_identity_role WHERE code='HIDRA_ADMIN'",String.class);
        if (!ids.isEmpty())return ids.getFirst();
        String role=id();
        jdbc.update("INSERT INTO public.hidra_identity_role"
                +"(id,code,role_type,status,created_at,updated_at)"
                +" VALUES (?,'HIDRA_ADMIN','ADMIN','ACTIVE',clock_timestamp(),clock_timestamp())",role);
        return role;
    }

    private String adminGrant(String user, String role) {
        String grant=id();
        jdbc.update("INSERT INTO public.hidra_identity_user_role_grant"
                +"(id,user_id,role_id,scope_type,valid_from,status,created_at)"
                +" VALUES (?,?,?,'GLOBAL',clock_timestamp()-interval '1 hour','ACTIVE',clock_timestamp())",
                grant,user,role);
        return grant;
    }

    private String admin() {
        var user=createUser("ACTIVE");
        adminGrant(user,role());
        return user;
    }

    @Test void realCredentialActivationResetSessionRevocationAndImmutableAudit() {
        String actor=admin(), ordinary=createUser("REGISTERED");
        var before=tx().execute(s -> adapter.readUser(ordinary,actor));
        assertThrows(IllegalStateException.class,() -> tx().execute(
                s -> adapter.changeUser(ordinary,before.updatedAt(),true,"activate",actor)));
        var first=tx().execute(s -> adapter.setCredential(ordinary,
                "$2a$10$synthetic-credential-hash-material-for-tests",0,"initial issue",actor));
        assertEquals(1L,first.securityVersion());
        var active=tx().execute(s -> adapter.changeUser(ordinary,before.updatedAt(),true,"activate",actor));
        assertEquals("ACTIVE",active.status());
        assertThrows(IllegalStateException.class,() -> tx().execute(
                s -> adapter.changeUser(ordinary,before.updatedAt(),false,"stale",actor)));
        String session=id();
        jdbc.update("INSERT INTO public.hidra_identity_login_session"
                +"(id,user_id,started_at,last_seen_at,expires_at,status,session_type)"
                +" VALUES (?,?,clock_timestamp(),clock_timestamp(),clock_timestamp()+interval '1 hour','ACTIVE','LOCAL')",
                session,ordinary);
        var reset=tx().execute(s -> adapter.setCredential(ordinary,
                "$2a$10$updated-synthetic-credential-hash-material",1,"recovery",actor));
        assertEquals(2L,reset.securityVersion());
        assertEquals("REVOKED",jdbc.queryForObject(
                "SELECT status FROM public.hidra_identity_login_session WHERE id=?",String.class,session));
        var disabled=tx().execute(s -> adapter.changeUser(ordinary,active.updatedAt(),false,"deactivate",actor));
        assertEquals("DISABLED",disabled.status());
        assertEquals(4,jdbc.queryForObject("SELECT count(*) FROM public.hidra_identity_lifecycle_event"
                +" WHERE actor_id=? AND target_id=?",Integer.class,actor,ordinary).intValue());
        assertThrows(org.springframework.dao.DataAccessException.class, () -> jdbc.update(
                "UPDATE public.hidra_identity_lifecycle_event SET reason='rewrite' WHERE target_id=?",ordinary));
    }

    @Test void historicalGrantRevocationAndReplay() {
        String actor=admin(), second=createUser("ACTIVE"), grant=adminGrant(second,role());
        Instant grantCreated=jdbc.queryForObject("SELECT created_at FROM public.hidra_identity_user_role_grant"
                +" WHERE id=?", (rs,n)->rs.getTimestamp(1).toInstant(),grant);
        var revoked=tx().execute(s -> adapter.revokeGrant(GrantKind.USER_ROLE,grant,grantCreated,
                "removal of secondary administrator",actor));
        assertEquals("REVOKED",revoked.status());
        assertEquals("removal of secondary administrator",jdbc.queryForObject(
                "SELECT revoked_reason FROM public.hidra_identity_user_role_grant WHERE id=?",String.class,grant));
        assertThrows(IllegalStateException.class,() -> tx().execute(
                s -> adapter.revokeGrant(GrantKind.USER_ROLE,grant,grantCreated,"replay",actor)));
        assertEquals(1,jdbc.queryForObject("SELECT count(*) FROM public.hidra_identity_user_role_grant"
                +" WHERE id=?",Integer.class,grant));
    }

    @Test void rollbackRestoresStatusAndAuditAtomically() {
        String actor=admin(),ordinary=createUser("REGISTERED");
        var before=tx().execute(s -> adapter.readUser(ordinary,actor));
        tx().execute(s -> adapter.setCredential(ordinary,
                "$2a$10$synthetic-credential-hash-material-for-tests",0,"initial",actor));
        int baseline=jdbc.queryForObject("SELECT count(*) FROM public.hidra_identity_lifecycle_event"
                +" WHERE target_id=?",Integer.class,ordinary);
        assertThrows(IllegalStateException.class, () -> tx().execute(s -> {
            adapter.changeUser(ordinary,before.updatedAt(),true,"activation",actor);
            throw new IllegalStateException("Synthetic downstream failure");
        }));
        assertEquals("REGISTERED",jdbc.queryForObject(
                "SELECT status FROM public.hidra_identity_user WHERE id=?",String.class,ordinary));
        assertEquals(baseline,jdbc.queryForObject("SELECT count(*) FROM public.hidra_identity_lifecycle_event"
                +" WHERE target_id=?",Integer.class,ordinary));
    }
}
