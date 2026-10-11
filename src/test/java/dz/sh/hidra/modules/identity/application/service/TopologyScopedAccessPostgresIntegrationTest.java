/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TopologyScopedAccessPostgresIntegrationTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-11
 *
 * @Type        : Class
 * @Layer       : Test
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.application.service
 *
 * @Description : Verifies paired Topology access against live synthetic PostgreSQL Identity evidence.
 *
 */
package dz.sh.hidra.modules.identity.application.service;

import static org.junit.jupiter.api.Assertions.*;

import dz.sh.hidra.modules.identity.application.contract.topology.TopologyCurrentActorContract;
import dz.sh.hidra.modules.identity.application.contract.topology.TopologyScopedAccessContract.Operation;
import dz.sh.hidra.modules.identity.application.contract.topology.TopologyScopedAccessContract.Request;
import dz.sh.hidra.modules.identity.application.port.out.AuthorizationDecisionRepositoryPort;
import dz.sh.hidra.modules.identity.domain.model.AuthorizationDecision;
import dz.sh.hidra.modules.identity.domain.service.AuthorizationPolicyEvaluator;
import dz.sh.hidra.modules.identity.infrastructure.persistence.adapter.JpaAuthorizationEvidenceAdapter;
import dz.sh.hidra.modules.identity.infrastructure.persistence.entity.*;
import jakarta.persistence.EntityManager;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers(disabledWithoutDocker = true)
class TopologyScopedAccessPostgresIntegrationTest {

    @Container
    private static final PostgreSQLContainer<?> POSTGRES = new PostgreSQLContainer<>("postgres:16-alpine");
    private static SessionFactory factory;
    private static final Request REQUEST = new Request(Operation.PUBLISH, "company-a", "facility-a");

    @BeforeAll
    static void createFactory() {
        var cfg = new Configuration().setProperty("hibernate.connection.url", POSTGRES.getJdbcUrl())
                .setProperty("hibernate.connection.username", POSTGRES.getUsername())
                .setProperty("hibernate.connection.password", POSTGRES.getPassword())
                .setProperty("hibernate.hbm2ddl.auto", "none");
        for (Class<?> type : List.of(UserJpaEntity.class, PermissionJpaEntity.class, RoleJpaEntity.class,
                GroupJpaEntity.class, UserPermissionGrantJpaEntity.class, UserRoleGrantJpaEntity.class,
                RolePermissionGrantJpaEntity.class, UserGroupMembershipJpaEntity.class, GroupRoleGrantJpaEntity.class,
                AuthorizationPolicyJpaEntity.class, AuthorizationPolicyVersionJpaEntity.class,
                AuthorizationPolicyRuleJpaEntity.class, SubjectSecurityAttributeJpaEntity.class,
                AttributeDefinitionJpaEntity.class, ExternalIdentityJpaEntity.class, IdentityProviderJpaEntity.class,
                ExternalGroupMappingJpaEntity.class, ExternalRoleMappingJpaEntity.class,
                ExternalPermissionMappingJpaEntity.class, AuthorizationDecisionJpaEntity.class)) {
            cfg.addAnnotatedClass(type);
        }
        factory = cfg.buildSessionFactory();
    }

    @AfterAll
    static void closeFactory() {
        if (factory != null) { factory.close(); }
    }

    @BeforeEach
    void setup() throws Exception {
        sql("DROP SCHEMA public CASCADE; CREATE SCHEMA public");
        sql(Files.readString(Path.of("src/main/resources/db/migration/V20260611_001__create_identity_tables.sql")));
        for (String migration : List.of("V20261004_007__hmr_007_identity_role.sql",
                "V20261004_010__hmr_010_identity_identity_provider.sql", "V20261004_011__hmr_011_identity_permission.sql",
                "V20261006_010__hmr_063_identity_user_uniqueness.sql", "V20261006_013__hmr_088_direct_permission_bounds.sql")) {
            sql(Files.readString(Path.of("src/main/resources/db/migration", migration)));
        }
        sql("INSERT INTO hidra_identity_user (id,username,user_type,status,failed_login_count,created_at,updated_at) "
                + "VALUES ('user-a','synthetic-a','HUMAN','ACTIVE',0,now(),now()),"
                + "('user-b','synthetic-b','HUMAN','ACTIVE',0,now(),now())");
        sql("INSERT INTO hidra_identity_permission (id,code,permission_domain,resource_type,action,sensitive,status,created_at,updated_at) "
                + "VALUES ('publish','topology:station-revision:publish','topology','STATION_GRAPH_REVISION','publish',false,'ACTIVE',now(),now())");
    }

    @Test
    void bothCompanyAndFacilityGrantsAreRequired() throws Exception {
        assertFalse(permitted("user-a", REQUEST));
        grant("company", "user-a", "CUSTOM", "topology-company:company-a", "GRANT");
        assertFalse(permitted("user-a", REQUEST));
        grant("facility", "user-a", "FACILITY", "facility-a", "GRANT");
        assertTrue(permitted("user-a", REQUEST));
        sql("DELETE FROM hidra_identity_user_permission_grant WHERE id='company'");
        assertFalse(permitted("user-a", REQUEST));
    }

    @Test
    void companiesFacilitiesAndUsersRemainIndependent() throws Exception {
        pair();
        assertTrue(permitted("user-a", REQUEST));
        assertFalse(permitted("user-a", new Request(Operation.PUBLISH, "company-b", "facility-a")));
        assertFalse(permitted("user-a", new Request(Operation.PUBLISH, "company-a", "facility-b")));
        assertFalse(permitted("user-b", REQUEST));
        grant("company-b", "user-b", "CUSTOM", "topology-company:company-b", "GRANT");
        grant("facility-b", "user-b", "FACILITY", "facility-b", "GRANT");
        assertTrue(permitted("user-b", new Request(Operation.PUBLISH, "company-b", "facility-b")));
        assertFalse(permitted("user-b", REQUEST));
        assertFalse(permitted("user-a", new Request(Operation.PUBLISH, "company-b", "facility-b")));
    }

    @ParameterizedTest
    @ValueSource(strings = {"company", "facility"})
    void explicitDenialOnEitherScopeOverridesItsGrant(String scope) throws Exception {
        pair();
        grant("deny", "user-a", scope.equals("company") ? "CUSTOM" : "FACILITY",
                scope.equals("company") ? "topology-company:company-a" : "facility-a", "DENY");
        assertFalse(permitted("user-a", REQUEST));
    }

    @ParameterizedTest
    @ValueSource(strings = {"company", "facility"})
    void revocationIsObservedOnNextInvocation(String scope) throws Exception {
        pair();
        assertTrue(permitted("user-a", REQUEST));
        sql("UPDATE hidra_identity_user_permission_grant SET status='REVOKED',revoked_at=now() WHERE id='" + scope + "'");
        assertFalse(permitted("user-a", REQUEST));
    }

    @ParameterizedTest
    @ValueSource(strings = {"company", "facility"})
    void expiredAndFutureGrantsDoNotPermit(String scope) throws Exception {
        pair();
        sql("UPDATE hidra_identity_user_permission_grant SET valid_from=now()-interval '2 hours',"
                + "valid_to=now()-interval '1 hour' WHERE id='" + scope + "'");
        assertFalse(permitted("user-a", REQUEST));
        sql("UPDATE hidra_identity_user_permission_grant SET valid_from=now()+interval '1 hour',"
                + "valid_to=now()+interval '2 hours' WHERE id='" + scope + "'");
        assertFalse(permitted("user-a", REQUEST));
    }

    @Test
    void inactiveAndLockedUsersDenyDespiteSuppliedTrustedActor() throws Exception {
        pair();
        assertTrue(permitted("user-a", REQUEST));
        sql("UPDATE hidra_identity_user SET status='SUSPENDED' WHERE id='user-a'");
        assertFalse(permitted("user-a", REQUEST));
        sql("UPDATE hidra_identity_user SET status='ACTIVE',locked_until=now()+interval '1 hour' WHERE id='user-a'");
        assertFalse(permitted("user-a", REQUEST));
        sql("UPDATE hidra_identity_user SET locked_until=now()-interval '1 hour' WHERE id='user-a'");
        assertTrue(permitted("user-a", REQUEST));
        sql("UPDATE hidra_identity_user SET status='LOCKED',locked_until=NULL WHERE id='user-a'");
        assertFalse(permitted("user-a", REQUEST));
    }

    @Test
    void wrongActionResourceAndInactiveOrMissingPermissionDeny() throws Exception {
        pair();
        assertFalse(permitted("user-a", new Request(Operation.READ_CURRENT, "company-a", "facility-a")));
        sql("UPDATE hidra_identity_permission SET resource_type='PIPELINE' WHERE id='publish'");
        assertFalse(permitted("user-a", REQUEST));
        sql("UPDATE hidra_identity_permission SET resource_type='STATION_GRAPH_REVISION',status='DISABLED' WHERE id='publish'");
        assertFalse(permitted("user-a", REQUEST));
        sql("DELETE FROM hidra_identity_user_permission_grant; DELETE FROM hidra_identity_permission");
        assertFalse(permitted("user-a", REQUEST));
    }

    @Test
    void ownerSchemaRejectsDottedProposalWhileCanonicalPermissionWorks() throws Exception {
        pair();
        assertTrue(permitted("user-a", REQUEST));
        SQLException failure = assertThrows(SQLException.class, () ->
                sql("UPDATE hidra_identity_permission SET code='topology.station-revision.publish' WHERE id='publish'"));
        assertEquals("23514", failure.getSQLState());
        assertTrue(permitted("user-a", REQUEST));
    }

    private void pair() throws Exception {
        grant("company", "user-a", "CUSTOM", "topology-company:company-a", "GRANT");
        grant("facility", "user-a", "FACILITY", "facility-a", "GRANT");
    }

    private void grant(String id, String user, String scopeType, String reference, String effect) throws Exception {
        sql("INSERT INTO hidra_identity_user_permission_grant "
                + "(id,user_id,permission_id,effect,scope_type,scope_reference_id,grant_reason,emergency_access,valid_from,valid_to,status,created_at) "
                + "VALUES ('" + id + "','" + user + "','publish','" + effect + "','" + scopeType + "','" + reference
                + "','synthetic fixture',false,now()-interval '1 hour',now()+interval '1 hour','ACTIVE',now())");
    }

    private boolean permitted(String user, Request request) {
        try (EntityManager em = factory.createEntityManager()) {
            em.getTransaction().begin();
            var evaluator = new IdentityAuthorizationApplicationService(new AuthorizationDecisionRepositoryPort() {
                public AuthorizationDecision save(AuthorizationDecision decision) { throw new AssertionError("Persistence disabled"); }
                public Optional<AuthorizationDecision> findById(String id) { throw new AssertionError("No prior decision authority"); }
            }, new AuthorizationPolicyEvaluator(), new JpaAuthorizationEvidenceAdapter(em), ignored -> null, () -> false);
            // Authentication isolation only; actual permission results always come from live PostgreSQL evidence.
            var service = new TopologyScopedAccessQueryService(() -> Optional.of(new TopologyCurrentActorContract.Actor(user)), evaluator);
            boolean result = service.permitted(request);
            em.getTransaction().commit();
            return result;
        }
    }

    private void sql(String statement) throws SQLException {
        try (var connection = DriverManager.getConnection(POSTGRES.getJdbcUrl(), POSTGRES.getUsername(), POSTGRES.getPassword());
                var statementHandle = connection.createStatement()) {
            statementHandle.execute(statement);
        }
    }
}
