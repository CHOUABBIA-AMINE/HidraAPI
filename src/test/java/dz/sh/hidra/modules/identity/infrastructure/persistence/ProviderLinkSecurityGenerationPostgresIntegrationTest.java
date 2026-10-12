/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ProviderLinkSecurityGenerationPostgresIntegrationTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-12
 *
 * @Type        : Class
 * @Layer       : Infrastructure Test
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.infrastructure.persistence
 *
 * @Description : Verifies real PostgreSQL provider and external-link generations, ORM compatibility, rollback and upgrade.
 *
 */
package dz.sh.hidra.modules.identity.infrastructure.persistence;

import dz.sh.hidra.HidraApplication;
import dz.sh.hidra.modules.identity.application.port.out.IdentityProviderRepositoryPort;
import dz.sh.hidra.modules.identity.domain.model.IdentityProvider;
import dz.sh.hidra.modules.identity.domain.value.ExternalIdentityStatus;
import dz.sh.hidra.modules.identity.domain.value.IdentityProviderStatus;
import dz.sh.hidra.modules.identity.domain.value.ProviderType;
import dz.sh.hidra.modules.identity.infrastructure.persistence.entity.ExternalIdentityJpaEntity;
import dz.sh.hidra.modules.identity.infrastructure.persistence.repository.ExternalIdentityJpaRepository;
import jakarta.persistence.EntityManager;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.UUID;
import org.flywaydb.core.Flyway;
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

@Testcontainers(disabledWithoutDocker = true)
@SpringBootTest(classes = HidraApplication.class, webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@ActiveProfiles("test")
class ProviderLinkSecurityGenerationPostgresIntegrationTest {
    @Container static final PostgreSQLContainer<?> POSTGRES = new PostgreSQLContainer<>("postgres:16").withDatabaseName("hidra_test");
    private static final String PROVIDER = "public.hidra_identity_provider";
    private static final String LINK = "public.hidra_identity_external_identity";
    private static final Instant NOW = Instant.parse("2026-10-12T19:00:00Z");
    @Autowired JdbcTemplate jdbc;
    @Autowired IdentityProviderRepositoryPort providers;
    @Autowired ExternalIdentityJpaRepository links;
    @Autowired EntityManager em;
    @Autowired PlatformTransactionManager transactions;
    @Autowired Flyway flyway;

    @DynamicPropertySource static void properties(DynamicPropertyRegistry r) {
        r.add("spring.datasource.url", POSTGRES::getJdbcUrl);
        r.add("spring.datasource.username", POSTGRES::getUsername);
        r.add("spring.datasource.password", POSTGRES::getPassword);
        r.add("spring.jpa.hibernate.ddl-auto", () -> "validate");
        r.add("spring.flyway.enabled", () -> "true");
        r.add("spring.flyway.validate-on-migrate", () -> "true");
    }

    @Test void actualProviderAdapterPreservesGeneratedVersionAndRollsBack() {
        String id = UUID.randomUUID().toString();
        var value = providerModel(id, "Synthetic provider", IdentityProviderStatus.INACTIVE, NOW);
        tx().executeWithoutResult(s -> { providers.save(value); em.flush(); });
        assertEquals(1L, version(PROVIDER, id));
        tx().executeWithoutResult(s -> { providers.save(providerModel(id, "Renamed provider", IdentityProviderStatus.INACTIVE, NOW.plusSeconds(1))); em.flush(); });
        assertEquals(1L, version(PROVIDER, id));
        tx().executeWithoutResult(s -> { providers.save(providerModel(id, "Renamed provider", IdentityProviderStatus.FAILED, NOW.plusSeconds(2))); em.flush(); });
        assertEquals(2L, version(PROVIDER, id));
        assertThrows(IllegalStateException.class, () -> tx().executeWithoutResult(s -> {
            providers.save(providerModel(id, "Renamed provider", IdentityProviderStatus.INACTIVE, NOW.plusSeconds(3))); em.flush();
            assertEquals(3L, version(PROVIDER, id)); throw new IllegalStateException("Synthetic downstream rollback");
        }));
        assertEquals(2L, version(PROVIDER, id));
        assertEquals(IdentityProviderStatus.FAILED, providers.findById(id).orElseThrow().status());
    }

    @Test void actualLinkRepositoryPreservesGeneratedVersionAndRollsBack() {
        String id = UUID.randomUUID().toString(), user = user(), provider = provider();
        tx().executeWithoutResult(s -> { links.save(linkModel(id, user, provider, "subject", "Synthetic", ExternalIdentityStatus.LINKED, NOW)); em.flush(); });
        assertEquals(1L, version(LINK, id));
        tx().executeWithoutResult(s -> { links.save(linkModel(id, user, provider, "subject", "Renamed", ExternalIdentityStatus.LINKED, NOW.plusSeconds(1))); em.flush(); });
        assertEquals(1L, version(LINK, id));
        tx().executeWithoutResult(s -> { links.save(linkModel(id, user, provider, "subject-rotated", "Renamed", ExternalIdentityStatus.LINKED, NOW.plusSeconds(2))); em.flush(); });
        assertEquals(2L, version(LINK, id));
        assertThrows(IllegalStateException.class, () -> tx().executeWithoutResult(s -> {
            links.save(linkModel(id, user, provider, "subject", "Renamed", ExternalIdentityStatus.LINKED, NOW.plusSeconds(3))); em.flush();
            assertEquals(3L, version(LINK, id)); throw new IllegalStateException("Synthetic downstream rollback");
        }));
        assertEquals(2L, version(LINK, id));
        assertEquals("subject-rotated", links.findById(id).orElseThrow().externalSubject());
    }

    private TransactionTemplate tx() { return new TransactionTemplate(transactions); }
    private long version(String table, String id) { return jdbc.queryForObject("SELECT security_version FROM " + table + " WHERE id=?", Long.class, id); }
    private String user() {
        String id = UUID.randomUUID().toString();
        jdbc.update("INSERT INTO public.hidra_identity_user(id,username,user_type,status,failed_login_count,created_at,updated_at) VALUES (?,?,'HUMAN','ACTIVE',0,?,?)", id, id, Timestamp.from(NOW), Timestamp.from(NOW));
        return id;
    }
    private String provider() {
        String id = UUID.randomUUID().toString();
        jdbc.update("INSERT INTO " + PROVIDER + "(id,code,name,provider_type,issuer_uri,sync_enabled,just_in_time_provisioning_enabled,status,created_at,updated_at) VALUES (?,?,'Synthetic provider','OIDC',?,false,false,'INACTIVE',?,?)", id, id, "https://" + id + ".hidra.invalid", Timestamp.from(NOW), Timestamp.from(NOW));
        return id;
    }
    private IdentityProvider providerModel(String id, String name, IdentityProviderStatus status, Instant updatedAt) {
        return new IdentityProvider(id, id, name, ProviderType.OIDC, "https://" + id + ".hidra.invalid",
                null, null, null, null, null, null, null, null, null, null, null,
                false, false, status, null, null, NOW, updatedAt);
    }
    private ExternalIdentityJpaEntity linkModel(String id, String user, String provider, String subject, String name, ExternalIdentityStatus status, Instant updatedAt) {
        return new ExternalIdentityJpaEntity(id, user, provider, subject, null, null, null, name, null, null, null, null, status, NOW, updatedAt);
    }
}
