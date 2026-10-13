/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ProviderLinkSecurityGenerationPostgresIntegrationTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-13
 *
 * @Type        : Class
 * @Layer       : Identity Test
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.infrastructure.persistence
 *
 * @Description : Verifies database-owned provider and link generations through real PostgreSQL, Flyway and JPA writers.
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
import java.io.StringReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;
import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.Test;
import org.postgresql.PGConnection;
import org.postgresql.util.PGobject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
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
class ProviderLinkSecurityGenerationPostgresIntegrationTest {
    @Container static final PostgreSQLContainer<?> POSTGRES = new PostgreSQLContainer<>("postgres:16")
            .withDatabaseName("hidra_test");
    private static final String PROVIDER = "public.hidra_identity_provider";
    private static final String LINK = "public.hidra_identity_external_identity";
    private static final Instant NOW = Instant.parse("2026-10-13T10:00:00Z");
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

    @Test void freshChainMatchesCurrentInventoryAndInstallsBothGuards() throws Exception {
        flyway.validate(); assertEquals(0, flyway.info().pending().length);
        var inventory = Stream.of(new PathMatchingResourcePatternResolver()
                .getResources("classpath*:db/migration/V*__*.sql")).map(Resource::getFilename).toList();
        assertThat(inventory).isNotEmpty().doesNotHaveDuplicates();
        assertThat(jdbc.queryForList("SELECT script FROM flyway_schema_history WHERE success AND type='SQL'", String.class))
                .containsExactlyInAnyOrderElementsOf(inventory);
        assertEquals(0, jdbc.queryForObject("SELECT count(*) FROM flyway_schema_history WHERE NOT success", Integer.class));
        assertEquals(1, jdbc.queryForObject("SELECT count(*) FROM flyway_schema_history WHERE version='20261012.002' AND success", Integer.class));
        for (String table : List.of(PROVIDER, LINK)) {
            assertEquals(3, jdbc.queryForObject("SELECT count(*) FROM pg_trigger WHERE tgrelid=?::regclass AND NOT tgisinternal", Integer.class, table));
            assertEquals(1L, version(table, table.equals(PROVIDER) ? provider() : link()));
        }
    }

    @Test void predecessorUpgradePreservesEveryOriginalFieldAndHistoryChecksum() {
        String database = "provider_upgrade_" + id().replace("-", "");
        jdbc.execute("CREATE DATABASE " + database);
        var source = new DriverManagerDataSource(POSTGRES.getJdbcUrl().replace("/hidra_test", "/" + database),
                POSTGRES.getUsername(), POSTGRES.getPassword());
        var upgrade = new JdbcTemplate(source);
        Flyway.configure().dataSource(source).locations("classpath:db/migration").target("20261012.001").load().migrate();
        String owner = id(), provider = id(), link = id();
        insertUser(upgrade, owner); insertProvider(upgrade, provider); insertLink(upgrade, link, owner, provider);
        var providerBefore = row(upgrade, PROVIDER, provider); var linkBefore = row(upgrade, LINK, link);
        var history = upgrade.queryForList("SELECT installed_rank,version,type,script,checksum,success FROM flyway_schema_history ORDER BY installed_rank");
        var latest = Flyway.configure().dataSource(source).locations("classpath:db/migration").load();
        int pending = latest.info().pending().length; assertTrue(pending > 0);
        assertEquals(pending, latest.migrate().migrationsExecuted); latest.validate();
        assertEquals(0, latest.info().pending().length);
        for (var entry : Map.of(PROVIDER, provider, LINK, link).entrySet()) {
            var after = row(upgrade, entry.getKey(), entry.getValue());
            assertEquals(1L, after.remove("security_version"));
            assertEquals(entry.getKey().equals(PROVIDER) ? providerBefore : linkBefore, after);
        }
        assertEquals(history, upgrade.queryForList("SELECT installed_rank,version,type,script,checksum,success FROM flyway_schema_history WHERE installed_rank<=? ORDER BY installed_rank", history.getLast().get("installed_rank")));
    }

    @Test void everyProviderSecurityFieldAndNullableTransitionAdvancesButNeutralFieldsDoNot() {
        String provider = provider();
        var changes = new LinkedHashMap<String, Object>();
        changes.put("code", "changed-" + id()); changes.put("provider_type", "LDAP");
        changes.put("issuer_uri", "https://changed.hidra.invalid");
        for (String column : List.of("authorization_endpoint", "token_endpoint", "jwks_uri", "directory_base_dn",
                "user_search_base", "group_search_base", "username_attribute", "email_attribute", "display_name_attribute",
                "external_id_attribute", "group_membership_attribute", "secret_reference")) changes.put(column, "synthetic-changed");
        changes.put("sync_enabled", true); changes.put("just_in_time_provisioning_enabled", true);
        changes.put("status", "FAILED"); changes.put("metadata", "{\"changed\":true}");
        checkEachSecurityField(PROVIDER, provider, changes);
        checkNeutral(PROVIDER, provider, Map.of("name", "Neutral name", "updated_at", Timestamp.from(NOW.plusSeconds(1))));
    }

    @Test void unexpectedUpgradeColumnCollisionCannotLeavePartialProviderGuards() {
        String database = "provider_collision_" + id().replace("-", "");
        jdbc.execute("CREATE DATABASE " + database);
        var source = new DriverManagerDataSource(POSTGRES.getJdbcUrl().replace("/hidra_test", "/" + database),
                POSTGRES.getUsername(), POSTGRES.getPassword());
        var upgrade = new JdbcTemplate(source);
        Flyway.configure().dataSource(source).locations("classpath:db/migration").target("20261012.001").load().migrate();
        var history = upgrade.queryForList("SELECT installed_rank,version,type,script,checksum,success FROM flyway_schema_history ORDER BY installed_rank");
        // Disposable test-owner collision, not a production migration or history repair.
        upgrade.execute("ALTER TABLE " + LINK + " ADD COLUMN security_version bigint");
        assertThrows(RuntimeException.class, () -> Flyway.configure().dataSource(source)
                .locations("classpath:db/migration").load().migrate());
        assertEquals(0, upgrade.queryForObject("SELECT count(*) FROM information_schema.columns WHERE table_schema='public' AND table_name='hidra_identity_provider' AND column_name='security_version'", Integer.class));
        assertEquals(0, upgrade.queryForObject("SELECT count(*) FROM pg_proc WHERE pronamespace='public'::regnamespace AND proname IN ('hidra_identity_provider_security_guard','hidra_identity_external_identity_security_guard')", Integer.class));
        assertEquals(history, upgrade.queryForList("SELECT installed_rank,version,type,script,checksum,success FROM flyway_schema_history ORDER BY installed_rank"));
    }

    @Test void everyLinkSecurityFieldAndNullableTransitionAdvancesButProfileFieldsDoNot() {
        String link = link();
        var changes = new LinkedHashMap<String, Object>();
        changes.put("user_id", user()); changes.put("identity_provider_id", provider());
        changes.put("external_subject", "changed-subject"); changes.put("external_immutable_id", "changed-immutable");
        changes.put("external_username", "changed-user"); changes.put("external_distinguished_name", "cn=changed");
        changes.put("status", "DISABLED_EXTERNAL"); checkEachSecurityField(LINK, link, changes);
        var neutral = new LinkedHashMap<String, Object>();
        neutral.put("external_email", "changed@hidra.invalid"); neutral.put("external_display_name", "Changed profile");
        neutral.put("external_attributes_snapshot", "[true,null]");
        for (String column : List.of("last_login_at", "last_synced_at", "updated_at")) neutral.put(column, Timestamp.from(NOW.plusSeconds(1)));
        checkNeutral(LINK, link, neutral);
    }

    @Test void semanticJsonAndMultipleSecurityChangesAdvanceOnlyWhenRequired() {
        String provider = provider();
        set(PROVIDER, provider, "metadata", "{\"n\":2,\"synthetic\":true}"); assertEquals(1L, version(PROVIDER, provider));
        set(PROVIDER, provider, "metadata", null); assertEquals(2L, version(PROVIDER, provider));
        set(PROVIDER, provider, "metadata", "null"); assertEquals(3L, version(PROVIDER, provider));
        set(PROVIDER, provider, "metadata", "null"); assertEquals(3L, version(PROVIDER, provider));
        jdbc.update("UPDATE " + PROVIDER + " SET status='FAILED',secret_reference='new-secret-reference' WHERE id=?", provider);
        assertEquals(4L, version(PROVIDER, provider));
        jdbc.update("UPDATE " + PROVIDER + " SET status='INACTIVE',secret_reference='synthetic-reference' WHERE id=?", provider);
        assertEquals(5L, version(PROVIDER, provider));
        String link = link(), originalUser = (String) row(jdbc, LINK, link).get("user_id");
        set(LINK, link, "user_id", user()); set(LINK, link, "user_id", originalUser);
        assertEquals(3L, version(LINK, link));
        jdbc.update("UPDATE " + LINK + " SET status='DISABLED_EXTERNAL',external_username='changed-together' WHERE id=?", link);
        assertEquals(4L, version(LINK, link));
    }

    @Test void callerVersionsIdentityMutationDeletionAndTruncationCannotResetEitherOwner() {
        for (String table : List.of(PROVIDER, LINK)) {
            String key = table.equals(PROVIDER) ? provider() : link(); var before = row(jdbc, table, key);
            for (Object bad : List.of(0L, -1L, 2L)) reject(() -> set(table, key, "security_version", bad));
            reject(() -> set(table, key, "security_version", null));
            reject(() -> set(table, key, "id", id()));
            reject(() -> set(table, key, "created_at", Timestamp.from(NOW.plusSeconds(1))));
            reject(() -> jdbc.update("DELETE FROM " + table + " WHERE id=?", key));
            reject(() -> jdbc.update("DELETE FROM " + table));
            reject(() -> jdbc.execute("TRUNCATE " + table + " CASCADE"));
            assertEquals(before, row(jdbc, table, key));
            for (String supplied : List.of("NULL", "0", "-1", "2")) {
                String missing = id();
                if (table.equals(PROVIDER)) reject(() -> jdbc.update("INSERT INTO " + PROVIDER + "(id,code,name,provider_type,sync_enabled,just_in_time_provisioning_enabled,status,created_at,updated_at,security_version) VALUES (?,?,'Synthetic','OIDC',false,false,'INACTIVE',?,?," + supplied + ")", missing, missing, Timestamp.from(NOW), Timestamp.from(NOW)));
                else reject(() -> jdbc.update("INSERT INTO " + LINK + "(id,user_id,identity_provider_id,external_subject,status,created_at,updated_at,security_version) VALUES (?,?,?,'synthetic-subject','LINKED',?,?," + supplied + ")", missing, user(), provider(), Timestamp.from(NOW), Timestamp.from(NOW)));
                assertEquals(0, jdbc.queryForObject("SELECT count(*) FROM " + table + " WHERE id=?", Integer.class, missing));
            }
        }
    }

    @Test void realJpaWritersPreserveGeneratedValuesAndRollbackBothOwnersAtomically() {
        String provider = id(), link = id(), user = user();
        save(provider(provider, null, "Original provider"), link(link, user, provider, null, "Original profile"));
        assertEquals(1L, version(PROVIDER, provider)); assertEquals(1L, version(LINK, link));
        save(provider(provider, "{}", "Original provider"), link(link, user, provider, "[]", "Original profile"));
        assertEquals(2L, version(PROVIDER, provider)); assertEquals(1L, version(LINK, link));
        save(provider(provider, "{}", "Neutral provider"), link(link, user, provider, null, "Neutral profile"));
        assertEquals(2L, version(PROVIDER, provider)); assertEquals(1L, version(LINK, link));
        var providerBefore = row(jdbc, PROVIDER, provider); var linkBefore = row(jdbc, LINK, link);
        assertThrows(IllegalStateException.class, () -> tx().executeWithoutResult(s -> {
            providers.save(provider(provider, "true", "Rollback provider"));
            links.save(link(link, user(), provider, "true", "Rollback profile")); em.flush(); em.clear();
            assertEquals(3L, version(PROVIDER, provider)); assertEquals(2L, version(LINK, link));
            throw new IllegalStateException("Synthetic downstream failure");
        }));
        assertEquals(providerBefore, row(jdbc, PROVIDER, provider)); assertEquals(linkBefore, row(jdbc, LINK, link));
        tx().executeWithoutResult(s -> {
            links.save(link(link, user(), provider, "{}", "Reassigned profile")); em.flush(); em.clear();
            assertEquals(2L, version(LINK, link));
            assertThat(links.findById(link).orElseThrow().userId()).isNotEqualTo(user);
        });
    }

    @Test void nativeBulkAndJpqlWritersExecuteBothGenerationGuards() {
        String a = provider(), b = provider(), x = link(), y = link();
        jdbc.update("UPDATE " + PROVIDER + " SET secret_reference='bulk' WHERE id IN (?,?)", a, b);
        jdbc.update("UPDATE " + LINK + " SET external_username='bulk' WHERE id IN (?,?)", x, y);
        for (String key : List.of(a, b)) assertEquals(2L, version(PROVIDER, key));
        for (String key : List.of(x, y)) assertEquals(2L, version(LINK, key));
        tx().executeWithoutResult(s -> {
            assertEquals(2, em.createQuery("UPDATE IdentityProviderJpaEntity p SET p.status=:status WHERE p.id IN (:ids)")
                    .setParameter("status", IdentityProviderStatus.FAILED).setParameter("ids", List.of(a, b)).executeUpdate());
            assertEquals(2, em.createQuery("UPDATE ExternalIdentityJpaEntity e SET e.status=:status WHERE e.id IN (:ids)")
                    .setParameter("status", ExternalIdentityStatus.DISABLED_EXTERNAL).setParameter("ids", List.of(x, y)).executeUpdate());
            em.clear();
        });
        for (String key : List.of(a, b)) assertEquals(3L, version(PROVIDER, key));
        for (String key : List.of(x, y)) assertEquals(3L, version(LINK, key));
    }

    @Test void copyMergeAndUpsertCannotBypassEitherGenerationGuard() throws Exception {
        for (String table : List.of(PROVIDER, LINK)) {
            String key = table.equals(PROVIDER) ? provider() : link();
            String column = table.equals(PROVIDER) ? "secret_reference" : "external_username";
            jdbc.update("MERGE INTO " + table + " AS t USING (VALUES (?,?)) AS v(id,value) ON t.id=v.id WHEN MATCHED THEN UPDATE SET " + column + "=v.value", key, "merged");
            assertEquals(2L, version(table, key));
            if (table.equals(PROVIDER)) jdbc.update("INSERT INTO " + PROVIDER + "(id,code,name,provider_type,sync_enabled,just_in_time_provisioning_enabled,status,secret_reference,created_at,updated_at) VALUES (?,?,'Synthetic','OIDC',false,false,'INACTIVE','upserted',?,?) ON CONFLICT(id) DO UPDATE SET secret_reference=EXCLUDED.secret_reference", key, key, Timestamp.from(NOW), Timestamp.from(NOW));
            else {
                var row = row(jdbc, LINK, key);
                jdbc.update("INSERT INTO " + LINK + "(id,user_id,identity_provider_id,external_subject,external_username,status,created_at,updated_at) VALUES (?,?,?,?,'upserted','LINKED',?,?) ON CONFLICT(id) DO UPDATE SET external_username=EXCLUDED.external_username", key, row.get("user_id"), row.get("identity_provider_id"), row.get("external_subject"), Timestamp.from(NOW), Timestamp.from(NOW));
            }
            assertEquals(3L, version(table, key));
            String copied = id(), owner = user(), provider = provider();
            String columns = table.equals(PROVIDER) ? "id,code,name,provider_type,sync_enabled,just_in_time_provisioning_enabled,status,created_at,updated_at,security_version" : "id,user_id,identity_provider_id,external_subject,status,created_at,updated_at,security_version";
            String values = table.equals(PROVIDER) ? copied + "," + copied + ",Synthetic,OIDC,false,false,INACTIVE" : copied + "," + owner + "," + provider + ",synthetic-" + copied + ",LINKED";
            try (Connection c = connection()) {
                var copy = c.unwrap(PGConnection.class).getCopyAPI();
                String sql = "COPY " + table + "(" + columns + ") FROM STDIN WITH (FORMAT csv)";
                assertEquals(1L, copy.copyIn(sql, new StringReader(values + ",2026-10-13 10:00:00+00,2026-10-13 10:00:00+00,1\n")));
                String invalid = values.replace(copied, id());
                SQLException failure = assertThrows(SQLException.class, () -> copy.copyIn(sql, new StringReader(invalid + ",2026-10-13 10:00:00+00,2026-10-13 10:00:00+00,9\n")));
                assertEquals("23514", failure.getSQLState());
            }
            assertEquals(1L, version(table, copied));
        }
    }

    @Test void overflowRejectsSecurityChangesAndFixtureCannotPersistPrivilegedBypass() throws Exception {
        for (String table : List.of(PROVIDER, LINK)) {
            String key = table.equals(PROVIDER) ? provider() : link();
            String trigger = table.equals(PROVIDER) ? "trg_p25_provider_security_write" : "trg_p25_external_identity_security_write";
            String column = table.equals(PROVIDER) ? "secret_reference" : "external_username";
            try (Connection c = connection(); var s = c.createStatement()) {
                c.setAutoCommit(false);
                try {
                    s.execute("ALTER TABLE " + table + " DISABLE TRIGGER " + trigger);
                    s.execute("UPDATE " + table + " SET security_version=9223372036854775807 WHERE id='" + key + "'");
                    s.execute("ALTER TABLE " + table + " ENABLE TRIGGER " + trigger);
                    s.execute("UPDATE " + table + " SET updated_at=updated_at WHERE id='" + key + "'");
                    var savepoint = c.setSavepoint();
                    SQLException failure = assertThrows(SQLException.class, () -> s.execute("UPDATE " + table + " SET " + column + "='overflow' WHERE id='" + key + "'"));
                    assertEquals("22003", failure.getSQLState()); c.rollback(savepoint);
                    try (var r = s.executeQuery("SELECT security_version FROM " + table + " WHERE id='" + key + "'")) { assertTrue(r.next()); assertEquals(Long.MAX_VALUE, r.getLong(1)); }
                } finally { c.rollback(); }
            }
            assertEquals(1L, version(table, key)); set(table, key, column, "after-overflow"); assertEquals(2L, version(table, key));
        }
    }

    @Test void independentWritersSerializeAndRollbackConsumesNoGenerationForEitherOwner() throws Exception {
        for (String table : List.of(PROVIDER, LINK)) {
            String key = table.equals(PROVIDER) ? provider() : link(), isolated = table.equals(PROVIDER) ? provider() : link();
            String column = table.equals(PROVIDER) ? "secret_reference" : "external_username";
            try (Connection first = connection(); var executor = Executors.newSingleThreadExecutor()) {
                first.setAutoCommit(false);
                try (var s = first.createStatement()) { s.execute("UPDATE " + table + " SET " + column + "='first' WHERE id='" + key + "'"); }
                var entered = new CountDownLatch(1); var backend = new AtomicInteger();
                var second = executor.submit(() -> {
                    try (Connection c = connection(); var s = c.createStatement()) {
                        s.execute("SET statement_timeout='10s'");
                        try (var r = s.executeQuery("SELECT pg_backend_pid()")) { r.next(); backend.set(r.getInt(1)); }
                        entered.countDown(); s.execute("UPDATE " + table + " SET " + column + "='second' WHERE id='" + key + "'");
                    }
                    return true;
                });
                try {
                    assertTrue(entered.await(5, TimeUnit.SECONDS));
                    long deadline = System.nanoTime() + TimeUnit.SECONDS.toNanos(5); boolean blocked = false;
                    while (System.nanoTime() < deadline && !blocked) {
                        blocked = Boolean.TRUE.equals(jdbc.queryForObject("SELECT EXISTS(SELECT 1 FROM pg_stat_activity WHERE pid=? AND wait_event_type='Lock')", Boolean.class, backend.get()));
                        if (!blocked) Thread.onSpinWait();
                    }
                    assertTrue(blocked, "Second independent writer must be observed blocked on the first row lock");
                    assertFalse(second.isDone()); set(table, isolated, column, "isolated");
                    assertEquals(2L, version(table, isolated)); first.commit(); assertTrue(second.get(10, TimeUnit.SECONDS));
                } finally { first.rollback(); }
            }
            assertEquals(3L, version(table, key));
            assertThrows(IllegalStateException.class, () -> tx().executeWithoutResult(s -> { set(table, key, column, "rollback"); throw new IllegalStateException("Synthetic rollback"); }));
            assertEquals(3L, version(table, key)); assertEquals(2L, version(table, isolated));
        }
    }

    @Test void existingProviderConstraintsAndLinkForeignKeysRemainAtomic() {
        String provider = provider(), other = provider(), link = link();
        var providerBefore = row(jdbc, PROVIDER, provider); var linkBefore = row(jdbc, LINK, link);
        rejectState("23505", () -> set(PROVIDER, provider, "code", other));
        set(PROVIDER, provider, "status", "ACTIVE");
        rejectState("23514", () -> set(PROVIDER, provider, "issuer_uri", null));
        String issuer = (String) row(jdbc, PROVIDER, provider).get("issuer_uri");
        set(PROVIDER, other, "issuer_uri", issuer); rejectState("23505", () -> set(PROVIDER, other, "status", "ACTIVE"));
        for (String type : List.of("LOCAL", "LDAP", "ACTIVE_DIRECTORY")) rejectState("23505", () -> tx().executeWithoutResult(s -> {
            for (int i = 0; i < 2; i++) { String key = provider(); jdbc.update("UPDATE " + PROVIDER + " SET provider_type=?,status='ACTIVE' WHERE id=?", type, key); }
        }));
        for (String column : List.of("user_id", "identity_provider_id")) rejectState("23503", () -> set(LINK, link, column, id()));
        assertEquals(linkBefore, row(jdbc, LINK, link));
        set(PROVIDER, provider, "status", "INACTIVE");
        var after = row(jdbc, PROVIDER, provider); after.remove("security_version"); providerBefore.remove("security_version");
        assertEquals(providerBefore, after);
    }

    private void checkEachSecurityField(String table, String key, Map<String, Object> changes) {
        long expected = version(table, key);
        for (var change : changes.entrySet()) {
            String column = change.getKey(); Object original = row(jdbc, table, key).get(column);
            set(table, key, column, change.getValue()); assertEquals(++expected, version(table, key), column);
            set(table, key, column, change.getValue()); assertEquals(expected, version(table, key), column + " unchanged");
            set(table, key, column, original); assertEquals(++expected, version(table, key), column + " A/B/A");
            if (Boolean.TRUE.equals(jdbc.queryForObject("SELECT is_nullable='YES' FROM information_schema.columns WHERE table_schema='public' AND table_name=? AND column_name=?", Boolean.class, table.substring(7), column))) {
                assertNotNull(original, "Synthetic fixture must cover both null transitions for " + column);
                set(table, key, column, null); assertEquals(++expected, version(table, key), column + " to null");
                set(table, key, column, original); assertEquals(++expected, version(table, key), column + " from null");
            }
        }
    }
    private void checkNeutral(String table, String key, Map<String, Object> changes) {
        long expected = version(table, key);
        for (var change : changes.entrySet()) {
            set(table, key, change.getKey(), change.getValue()); assertEquals(expected, version(table, key), change.getKey());
            if (!change.getKey().equals("name") && !change.getKey().equals("updated_at")) {
                set(table, key, change.getKey(), null); assertEquals(expected, version(table, key), change.getKey() + " to null");
                set(table, key, change.getKey(), change.getValue()); assertEquals(expected, version(table, key), change.getKey() + " from null");
            }
        }
    }
    private void set(String table, String key, String column, Object value) {
        if (value instanceof PGobject object) value = object.getValue();
        boolean json = column.equals("metadata") || column.equals("external_attributes_snapshot");
        jdbc.update("UPDATE " + table + " SET " + column + "=?" + (json ? "::jsonb" : "") + " WHERE id=?", value, key);
    }
    private Map<String, Object> row(JdbcTemplate template, String table, String key) { return new LinkedHashMap<>(template.queryForMap("SELECT * FROM " + table + " WHERE id=?", key)); }
    private long version(String table, String key) { return jdbc.queryForObject("SELECT security_version FROM " + table + " WHERE id=?", Long.class, key); }
    private String id() { return UUID.randomUUID().toString(); }
    private String user() { String key = id(); insertUser(jdbc, key); return key; }
    private String provider() { String key = id(); insertProvider(jdbc, key); return key; }
    private String link() { String key = id(); insertLink(jdbc, key, user(), provider()); return key; }
    private void insertUser(JdbcTemplate template, String key) { template.update("INSERT INTO public.hidra_identity_user(id,username,user_type,status,failed_login_count,created_at,updated_at) VALUES (?,?,'HUMAN','ACTIVE',0,?,?)", key, key, Timestamp.from(NOW), Timestamp.from(NOW)); }
    private void insertProvider(JdbcTemplate template, String key) {
        template.update("INSERT INTO " + PROVIDER + "(id,code,name,provider_type,issuer_uri,authorization_endpoint,token_endpoint,jwks_uri,directory_base_dn,user_search_base,group_search_base,username_attribute,email_attribute,display_name_attribute,external_id_attribute,group_membership_attribute,sync_enabled,just_in_time_provisioning_enabled,status,metadata,secret_reference,created_at,updated_at) VALUES (?,?,'Synthetic provider','OIDC',?,'synthetic-original','synthetic-original','synthetic-original','synthetic-original','synthetic-original','synthetic-original','synthetic-original','synthetic-original','synthetic-original','synthetic-original','synthetic-original',false,false,'INACTIVE','{\"synthetic\":true,\"n\":2}'::jsonb,'synthetic-reference',?,?)", key, key, "https://" + key + ".hidra.invalid", Timestamp.from(NOW), Timestamp.from(NOW));
    }
    private void insertLink(JdbcTemplate template, String key, String user, String provider) { template.update("INSERT INTO " + LINK + "(id,user_id,identity_provider_id,external_subject,external_immutable_id,external_username,external_email,external_display_name,external_distinguished_name,external_attributes_snapshot,last_login_at,last_synced_at,status,created_at,updated_at) VALUES (?,?,?,?,'synthetic-original','synthetic-original','synthetic@hidra.invalid','Synthetic profile','synthetic-original','{}'::jsonb,?,?,'LINKED',?,?)", key, user, provider, "synthetic-" + key, Timestamp.from(NOW), Timestamp.from(NOW), Timestamp.from(NOW), Timestamp.from(NOW)); }
    private IdentityProvider provider(String key, String json, String name) { String uri = "https://" + key + ".hidra.invalid"; return new IdentityProvider(key, key, name, ProviderType.OIDC, uri, uri + "/authorize", uri + "/token", uri + "/jwks", "dc=synthetic", "ou=users", "ou=groups", "uid", "mail", "displayName", "entryUUID", "memberOf", false, false, IdentityProviderStatus.INACTIVE, json, "synthetic-reference", NOW, NOW); }
    private ExternalIdentityJpaEntity link(String key, String user, String provider, String json, String name) { return new ExternalIdentityJpaEntity(key, user, provider, "synthetic-" + key, "immutable-" + key, "synthetic-user", "synthetic@hidra.invalid", name, "cn=synthetic", json, NOW, NOW, ExternalIdentityStatus.LINKED, NOW, NOW); }
    private void save(IdentityProvider provider, ExternalIdentityJpaEntity link) {
        tx().executeWithoutResult(s -> {
            providers.save(provider); links.save(link); em.flush(); em.clear();
            assertThat(providers.findById(provider.id()).orElseThrow()).usingRecursiveComparison().isEqualTo(provider);
            assertThat(links.findById(link.id()).orElseThrow()).usingRecursiveComparison().isEqualTo(link);
        });
    }
    private TransactionTemplate tx() { return new TransactionTemplate(transactions); }
    private Connection connection() throws SQLException { return DriverManager.getConnection(POSTGRES.getJdbcUrl(), POSTGRES.getUsername(), POSTGRES.getPassword()); }
    private void reject(Runnable write) { rejectState("23514", write); }
    private void rejectState(String expected, Runnable write) {
        DataAccessException failure = assertThrows(DataAccessException.class, () -> tx().executeWithoutResult(s -> write.run()));
        for (Throwable cause = failure; cause != null; cause = cause.getCause()) if (cause instanceof SQLException sql) { assertEquals(expected, sql.getSQLState()); return; }
        fail("Expected PostgreSQL SQLSTATE " + expected, failure);
    }
}
