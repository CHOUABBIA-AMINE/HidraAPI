/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : IdentityProviderLinkJsonbPostgresIntegrationTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-13
 *
 * @Type        : Class
 * @Layer       : Identity Test
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.infrastructure.persistence
 *
 * @Description : Verifies existing provider and link JSONB mappings through real PostgreSQL and JPA writers.
 *
 */
package dz.sh.hidra.modules.identity.infrastructure.persistence;

import dz.sh.hidra.HidraApplication;
import dz.sh.hidra.modules.identity.application.port.out.IdentityProviderRepositoryPort;
import dz.sh.hidra.modules.identity.domain.exception.InvalidIdentityValueException;
import dz.sh.hidra.modules.identity.domain.model.IdentityProvider;
import dz.sh.hidra.modules.identity.domain.value.ExternalIdentityStatus;
import dz.sh.hidra.modules.identity.domain.value.IdentityProviderStatus;
import dz.sh.hidra.modules.identity.domain.value.ProviderType;
import dz.sh.hidra.modules.identity.infrastructure.persistence.entity.ExternalIdentityJpaEntity;
import dz.sh.hidra.modules.identity.infrastructure.persistence.repository.ExternalIdentityJpaRepository;
import jakarta.persistence.EntityManager;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;
import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataAccessException;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@Testcontainers(disabledWithoutDocker = true)
@SpringBootTest(classes = HidraApplication.class, webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@ActiveProfiles("test")
class IdentityProviderLinkJsonbPostgresIntegrationTest {
    @Container
    static final PostgreSQLContainer<?> POSTGRES = new PostgreSQLContainer<>("postgres:16")
            .withDatabaseName("hidra_test");
    private static final String PROVIDER = "public.hidra_identity_provider";
    private static final String LINK = "public.hidra_identity_external_identity";
    private static final Instant NOW = Instant.parse("2026-10-12T20:00:00Z");
    private static final List<String> PAYLOADS = List.of(
            "{\"synthetic\":{\"n\":2},\"items\":[1,true,null]}",
            "[1,{\"synthetic\":false},\"sample\"]", "42", "true", "\"sample\"", "null");
    private static final List<String> TYPES = List.of("object", "array", "number", "boolean", "string", "null");

    @Autowired JdbcTemplate jdbc;
    @Autowired IdentityProviderRepositoryPort providers;
    @Autowired ExternalIdentityJpaRepository links;
    @Autowired EntityManager em;
    @Autowired PlatformTransactionManager transactions;
    @Autowired Flyway flyway;

    @DynamicPropertySource
    static void properties(DynamicPropertyRegistry r) {
        r.add("spring.datasource.url", POSTGRES::getJdbcUrl);
        r.add("spring.datasource.username", POSTGRES::getUsername);
        r.add("spring.datasource.password", POSTGRES::getPassword);
        r.add("spring.jpa.hibernate.ddl-auto", () -> "validate");
        r.add("spring.flyway.enabled", () -> "true");
        r.add("spring.flyway.validate-on-migrate", () -> "true");
    }

    @Test
    void providerNullInsertAndMergePreserveJavaShapeAndOtherFields() {
        String id = id();
        saveProvider(provider(id, null, "Synthetic provider", NOW));
        saveProvider(provider(id, null, "Renamed provider", NOW.plusSeconds(1)));
        assertSqlNull(PROVIDER, "metadata", id);
    }

    @Test
    void linkNullInsertAndMergePreserveJavaShapeAndOtherFields() {
        String id = id(), user = user(), provider = provider();
        saveLink(link(id, user, provider, null, "Synthetic profile", NOW));
        saveLink(link(id, user, provider, null, "Renamed profile", NOW.plusSeconds(1)));
        assertSqlNull(LINK, "external_attributes_snapshot", id);
    }

    @Test
    void providerJsonRootsInsertReplaceAndClearWithoutDoubleEncoding() {
        String merged = id();
        saveProvider(provider(merged, null, "Synthetic provider", NOW));
        for (int i = 0; i < PAYLOADS.size(); i++) {
            String inserted = id(), payload = PAYLOADS.get(i);
            saveProvider(provider(inserted, payload, "Synthetic provider", NOW));
            assertJson(PROVIDER, "metadata", inserted, payload, TYPES.get(i));
            saveProvider(provider(merged, payload, "Synthetic provider", NOW.plusSeconds(i + 1)));
            assertJson(PROVIDER, "metadata", merged, payload, TYPES.get(i));
        }
        saveProvider(provider(merged, null, "Synthetic provider", NOW.plusSeconds(10)));
        assertSqlNull(PROVIDER, "metadata", merged);
    }

    @Test
    void linkJsonRootsInsertReplaceAndClearWithoutDoubleEncoding() {
        String merged = id(), user = user(), provider = provider();
        saveLink(link(merged, user, provider, null, "Synthetic profile", NOW));
        for (int i = 0; i < PAYLOADS.size(); i++) {
            String inserted = id(), payload = PAYLOADS.get(i);
            saveLink(link(inserted, user, provider, payload, "Synthetic profile", NOW));
            assertJson(LINK, "external_attributes_snapshot", inserted, payload, TYPES.get(i));
            saveLink(link(merged, user, provider, payload, "Synthetic profile", NOW.plusSeconds(i + 1)));
            assertJson(LINK, "external_attributes_snapshot", merged, payload, TYPES.get(i));
        }
        saveLink(link(merged, user, provider, null, "Synthetic profile", NOW.plusSeconds(10)));
        assertSqlNull(LINK, "external_attributes_snapshot", merged);
    }

    @Test
    void malformedJsonInsertsAndUpdatesRollBackBothOwnersAtomically() {
        String provider = provider(), link = id(), user = user();
        saveProvider(provider(provider, PAYLOADS.getFirst(), "Original provider", NOW));
        saveLink(link(link, user, provider, PAYLOADS.get(1), "Original profile", NOW));
        for (boolean malformedProvider : List.of(true, false)) {
            RuntimeException failure = assertThrows(RuntimeException.class, () -> tx().executeWithoutResult(s -> {
                providers.save(provider(provider, malformedProvider ? "{" : "true", "Changed provider", NOW.plusSeconds(1)));
                links.save(link(link, user, provider, malformedProvider ? "42" : "[", "Changed profile", NOW.plusSeconds(1)));
                em.flush();
            }));
            assertEquals("22P02", sqlState(failure));
            assertOriginal(provider, link, user);
            String missing = id();
            RuntimeException insertFailure = assertThrows(RuntimeException.class, () -> tx().executeWithoutResult(s -> {
                if (malformedProvider) providers.save(provider(missing, "{", "Invalid provider", NOW));
                else links.save(link(missing, user, provider, "[", "Invalid profile", NOW));
                em.flush();
            }));
            assertEquals("22P02", sqlState(insertFailure));
            assertEquals(0, jdbc.queryForObject("SELECT count(*) FROM " + (malformedProvider ? PROVIDER : LINK) + " WHERE id=?", Integer.class, missing));
        }
    }

    @Test
    void downstreamFailureAfterFlushedChangesRollsBackBothOwners() {
        String provider = provider(), link = id(), user = user();
        saveProvider(provider(provider, PAYLOADS.getFirst(), "Original provider", NOW));
        saveLink(link(link, user, provider, PAYLOADS.get(1), "Original profile", NOW));
        assertThrows(IllegalStateException.class, () -> tx().executeWithoutResult(s -> {
            providers.save(provider(provider, "true", "Changed provider", NOW.plusSeconds(1)));
            links.save(link(link, user, provider, "42", "Changed profile", NOW.plusSeconds(1)));
            em.flush(); em.clear();
            assertJson(PROVIDER, "metadata", provider, "true", "boolean");
            assertJson(LINK, "external_attributes_snapshot", link, "42", "number");
            throw new IllegalStateException("Synthetic downstream rollback");
        }));
        assertOriginal(provider, link, user);
    }

    @Test
    void providerCodeAndActiveProviderConstraintsRemainEffective() {
        String existing = provider(), duplicate = id();
        IdentityProvider value = configuredProvider(duplicate, existing, ProviderType.OIDC,
                "https://" + duplicate + ".hidra.invalid", IdentityProviderStatus.INACTIVE, "{}");
        assertThrows(InvalidIdentityValueException.class, () -> tx().executeWithoutResult(s -> {
            providers.save(value); em.flush();
        }));
        assertSqlFailure("23505", () -> jdbc.update("UPDATE " + PROVIDER + " SET code=? WHERE id=?", existing, provider()));
        String active = id(), issuer = "https://" + active + ".hidra.invalid";
        saveProvider(configuredProvider(active, active, ProviderType.OIDC, issuer, IdentityProviderStatus.ACTIVE, "{}"));
        String other = id();
        RuntimeException conflict = assertThrows(RuntimeException.class, () -> tx().executeWithoutResult(s -> {
            providers.save(configuredProvider(other, other, ProviderType.OIDC, issuer, IdentityProviderStatus.ACTIVE, "[]")); em.flush();
        }));
        assertEquals("23505", sqlState(conflict));
        assertThrows(InvalidIdentityValueException.class, () -> tx().executeWithoutResult(s ->
                providers.save(configuredProvider(other, other, ProviderType.OIDC, null, IdentityProviderStatus.ACTIVE, "{}"))));
        assertSqlFailure("23514", () -> jdbc.update("UPDATE " + PROVIDER + " SET issuer_uri=NULL WHERE id=?", active));
        for (String type : List.of("LOCAL", "LDAP", "ACTIVE_DIRECTORY")) {
            assertSqlFailure("23505", () -> {
                for (int i = 0; i < 2; i++) {
                    String id = id();
                    jdbc.update("INSERT INTO " + PROVIDER + "(id,code,name,provider_type,sync_enabled,just_in_time_provisioning_enabled,status,metadata,created_at,updated_at) VALUES (?,?,'Synthetic active provider',?,false,false,'ACTIVE','{}'::jsonb,?,?)",
                            id, id, type, Timestamp.from(NOW), Timestamp.from(NOW));
                }
            });
        }
    }

    @Test
    void linkUserAndProviderForeignKeysRejectOrphansWithoutChangingValidOwner() {
        String provider = provider(), link = id(), user = user();
        saveLink(link(link, user, provider, "{}", "Original profile", NOW));
        for (boolean missingUser : List.of(true, false)) {
            RuntimeException failure = assertThrows(RuntimeException.class, () -> tx().executeWithoutResult(s -> {
                links.save(link(link, missingUser ? id() : user, missingUser ? provider : id(), "[]", "Changed profile", NOW.plusSeconds(1)));
                em.flush();
            }));
            assertEquals("23503", sqlState(failure));
            tx().executeWithoutResult(s -> {
                em.clear();
                assertLinkShape(link(link, user, provider, "{}", "Original profile", NOW));
            });
        }
    }

    @Test
    void freshFullMigrationChainValidatesExistingJsonbSchemaWithoutNewSql() throws IOException {
        flyway.validate();
        assertEquals(0, flyway.info().pending().length);
        List<String> currentSqlInventory = Stream.of(new PathMatchingResourcePatternResolver()
                .getResources("classpath*:db/migration/V*__*.sql"))
                .map(Resource::getFilename).toList();
        assertThat(currentSqlInventory).isNotEmpty().doesNotHaveDuplicates();
        assertThat(jdbc.queryForList("SELECT script FROM flyway_schema_history WHERE success AND type='SQL'", String.class))
                .containsExactlyInAnyOrderElementsOf(currentSqlInventory);
        assertEquals(0, jdbc.queryForObject("SELECT count(*) FROM flyway_schema_history WHERE NOT success", Integer.class));
        assertEquals(1, jdbc.queryForObject("SELECT count(*) FROM flyway_schema_history WHERE success AND type='SQL' AND version='20261012.001'", Integer.class));
        for (String column : List.of("metadata", "external_attributes_snapshot")) {
            String table = column.equals("metadata") ? "hidra_identity_provider" : "hidra_identity_external_identity";
            assertEquals("jsonb", jdbc.queryForObject("SELECT udt_name FROM information_schema.columns WHERE table_schema='public' AND table_name=? AND column_name=?", String.class, table, column));
        }
    }

    private void saveProvider(IdentityProvider expected) {
        tx().executeWithoutResult(s -> {
            providers.save(expected); em.flush(); em.clear();
            assertProviderShape(expected);
        });
    }

    private void saveLink(ExternalIdentityJpaEntity expected) {
        tx().executeWithoutResult(s -> {
            links.save(expected); em.flush(); em.clear();
            assertLinkShape(expected);
        });
    }

    private void assertProviderShape(IdentityProvider expected) {
        IdentityProvider actual = providers.findById(expected.id()).orElseThrow();
        assertThat(actual).usingRecursiveComparison().ignoringFields("metadata").isEqualTo(expected);
        assertJavaJson(expected.metadata(), actual.metadata());
    }

    private void assertLinkShape(ExternalIdentityJpaEntity expected) {
        ExternalIdentityJpaEntity actual = links.findById(expected.id()).orElseThrow();
        assertThat(actual).usingRecursiveComparison().ignoringFields("externalAttributesSnapshot").isEqualTo(expected);
        assertJavaJson(expected.externalAttributesSnapshot(), actual.externalAttributesSnapshot());
    }

    private void assertJavaJson(String expected, String actual) {
        if (expected == null) assertNull(actual);
        else {
            assertNotNull(actual, "JSON null literal must remain a String, distinct from SQL NULL");
            assertTrue(Boolean.TRUE.equals(jdbc.queryForObject("SELECT ?::jsonb = ?::jsonb", Boolean.class, expected, actual)));
        }
    }

    private void assertJson(String table, String column, String id, String value, String type) {
        assertEquals(type, jdbc.queryForObject("SELECT jsonb_typeof(" + column + ") FROM " + table + " WHERE id=?", String.class, id));
        assertTrue(Boolean.TRUE.equals(jdbc.queryForObject("SELECT " + column + " = ?::jsonb FROM " + table + " WHERE id=?", Boolean.class, value, id)));
        assertFalse(Boolean.TRUE.equals(jdbc.queryForObject("SELECT " + column + " IS NULL FROM " + table + " WHERE id=?", Boolean.class, id)));
    }

    private void assertSqlNull(String table, String column, String id) {
        assertTrue(Boolean.TRUE.equals(jdbc.queryForObject("SELECT " + column + " IS NULL FROM " + table + " WHERE id=?", Boolean.class, id)));
    }

    private void assertOriginal(String provider, String link, String user) {
        tx().executeWithoutResult(s -> {
            em.clear();
            assertProviderShape(provider(provider, PAYLOADS.getFirst(), "Original provider", NOW));
            assertLinkShape(link(link, user, provider, PAYLOADS.get(1), "Original profile", NOW));
        });
    }

    private void assertSqlFailure(String state, Runnable write) {
        DataAccessException failure = assertThrows(DataAccessException.class, () -> tx().executeWithoutResult(s -> write.run()));
        assertEquals(state, sqlState(failure));
    }

    private String sqlState(Throwable failure) {
        for (Throwable cause = failure; cause != null; cause = cause.getCause()) {
            if (cause instanceof SQLException sql) return sql.getSQLState();
        }
        fail("Expected a PostgreSQL rejection", failure);
        return null;
    }

    private TransactionTemplate tx() { return new TransactionTemplate(transactions); }
    private String id() { return UUID.randomUUID().toString(); }
    private String user() {
        String id = id();
        jdbc.update("INSERT INTO public.hidra_identity_user(id,username,user_type,status,failed_login_count,created_at,updated_at) VALUES (?,?,'HUMAN','ACTIVE',0,?,?)",
                id, id, Timestamp.from(NOW), Timestamp.from(NOW));
        return id;
    }
    private String provider() {
        String id = id(); saveProvider(provider(id, null, "Synthetic provider", NOW)); return id;
    }
    private IdentityProvider configuredProvider(String id, String code, ProviderType type, String issuer, IdentityProviderStatus status, String json) {
        return new IdentityProvider(id, code, "Synthetic provider", type, issuer,
                null, null, null, null, null, null, null, null, null, null, null,
                false, false, status, json, null, NOW, NOW);
    }
    private IdentityProvider provider(String id, String json, String name, Instant updated) {
        String uri = "https://" + id + ".hidra.invalid";
        return new IdentityProvider(id, id, name, ProviderType.OIDC, uri,
                uri + "/authorize", uri + "/token", uri + "/jwks", "dc=synthetic", "ou=users", "ou=groups",
                "uid", "mail", "displayName", "entryUUID", "memberOf", true, true,
                IdentityProviderStatus.INACTIVE, json, "synthetic-reference", NOW, updated);
    }
    private ExternalIdentityJpaEntity link(String id, String user, String provider, String json, String name, Instant updated) {
        return new ExternalIdentityJpaEntity(id, user, provider, "synthetic-subject-" + id, "synthetic-immutable-" + id,
                "synthetic-user", "synthetic@hidra.invalid", name, "cn=synthetic", json, NOW, NOW,
                ExternalIdentityStatus.LINKED, NOW, updated);
    }
}
