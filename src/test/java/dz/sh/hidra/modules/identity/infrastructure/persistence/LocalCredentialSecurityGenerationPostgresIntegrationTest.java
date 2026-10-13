/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : LocalCredentialSecurityGenerationPostgresIntegrationTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-13
 *
 * @Type        : Class
 * @Layer       : Infrastructure Test
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.infrastructure.persistence
 *
 * @Description : Verifies real PostgreSQL LOCAL credential generations, owner compatibility, rollback and Flyway upgrade.
 *
 */
package dz.sh.hidra.modules.identity.infrastructure.persistence;

import dz.sh.hidra.HidraApplication;
import dz.sh.hidra.modules.identity.application.port.in.BootstrapLocalAdministratorUseCase;
import dz.sh.hidra.modules.identity.application.port.out.LocalCredentialRepositoryPort;
import dz.sh.hidra.modules.identity.domain.model.LocalCredential;
import dz.sh.hidra.modules.identity.domain.value.UserStatus;
import dz.sh.hidra.modules.identity.domain.value.UserType;
import dz.sh.hidra.modules.identity.infrastructure.persistence.entity.UserJpaEntity;
import dz.sh.hidra.modules.identity.infrastructure.persistence.repository.UserJpaRepository;
import jakarta.persistence.EntityManager;
import java.io.StringReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.Test;
import org.postgresql.PGConnection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
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
import static org.junit.jupiter.api.Assertions.*;

@Testcontainers(disabledWithoutDocker = true)
@SpringBootTest(classes = HidraApplication.class, webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@ActiveProfiles("test")
class LocalCredentialSecurityGenerationPostgresIntegrationTest {
    @Container static final PostgreSQLContainer<?> POSTGRES = new PostgreSQLContainer<>("postgres:16").withDatabaseName("hidra_test");
    private static final String TABLE = "public.hidra_identity_local_credential";
    private static final Instant NOW = Instant.parse("2026-10-12T10:00:00Z");
    @Autowired JdbcTemplate jdbc;
    @Autowired LocalCredentialRepositoryPort credentials;
    @Autowired UserJpaRepository users;
    @Autowired BootstrapLocalAdministratorUseCase bootstrap;
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

    @Test void freshAndPredecessorUpgradePreserveRowsAndOldChecksums() {
        flyway.validate();
        assertEquals(0, flyway.info().pending().length);
        assertEquals(1, jdbc.queryForObject("SELECT count(*) FROM flyway_schema_history WHERE success AND type='SQL' AND version='20261012.001'", Integer.class));
        assertEquals(3, jdbc.queryForObject("SELECT count(*) FROM pg_trigger WHERE tgrelid=?::regclass AND NOT tgisinternal", Integer.class, TABLE).intValue());
        String database = "credential_upgrade_" + UUID.randomUUID().toString().replace("-", "");
        jdbc.execute("CREATE DATABASE " + database);
        var source = new DriverManagerDataSource(POSTGRES.getJdbcUrl().replace("/hidra_test", "/" + database), POSTGRES.getUsername(), POSTGRES.getPassword());
        var upgrade = new JdbcTemplate(source);
        Flyway.configure().dataSource(source).locations("classpath:db/migration").target("20261011.002").load().migrate();
        upgrade.update("INSERT INTO hidra_identity_user(id,username,user_type,status,failed_login_count,created_at,updated_at) VALUES ('upgrade-user','upgrade-user','HUMAN','ACTIVE',0,?,?)", Timestamp.from(NOW), Timestamp.from(NOW));
        upgrade.update("INSERT INTO " + TABLE + "(id,user_id,password_hash,credential_status,created_at,updated_at) VALUES ('upgrade-credential','upgrade-user','synthetic-hash','ACTIVE',?,?)", Timestamp.from(NOW), Timestamp.from(NOW));
        var before = upgrade.queryForMap("SELECT * FROM " + TABLE + " WHERE id='upgrade-credential'");
        var history = upgrade.queryForList("SELECT installed_rank,version,script,checksum,success FROM flyway_schema_history ORDER BY installed_rank");
        var latest = Flyway.configure().dataSource(source).locations("classpath:db/migration").load();
        int pending = latest.info().pending().length;
        assertEquals(pending, latest.migrate().migrationsExecuted); latest.validate();
        assertEquals(0, latest.info().pending().length);
        var after = upgrade.queryForMap("SELECT * FROM " + TABLE + " WHERE id='upgrade-credential'");
        assertEquals(1L, after.remove("security_version")); assertEquals(before, after);
        assertEquals(history, upgrade.queryForList("SELECT installed_rank,version,script,checksum,success FROM flyway_schema_history WHERE installed_rank<=? ORDER BY installed_rank", history.getLast().get("installed_rank")));
    }

    @Test void neutralWritesAndEachSecurityFieldHaveCorrectGeneration() {
        var c = credential(); assertEquals(1L, version(c.id()));
        update(c.id(), "updated_at=updated_at + interval '1 second'");
        update(c.id(), "password_hash=password_hash"); assertEquals(1L, version(c.id()));
        update(c.id(), "password_hash='synthetic-b'"); update(c.id(), "password_hash='synthetic-a'");
        update(c.id(), "credential_status='DISABLED'"); update(c.id(), "credential_status='ACTIVE'");
        update(c.id(), "password_changed_at=NULL"); update(c.id(), "password_changed_at='2026-10-12 10:00:00+00'");
        assertEquals(7L, version(c.id()));
    }

    @Test void callerVersionIdentityChangesAndDeleteResetAreRejected() {
        var c = credential();
        for (String change : List.of("security_version=NULL", "security_version=0", "security_version=2", "id='reassigned'", "user_id='reassigned'", "created_at=created_at + interval '1 second'"))
            assertThrows(DataAccessException.class, () -> update(c.id(), change));
        String anotherUser = user();
        for (String supplied : List.of("NULL", "0", "-1", "2"))
            assertThrows(DataAccessException.class, () -> jdbc.update("INSERT INTO " + TABLE + "(id,user_id,password_hash,credential_status,created_at,updated_at,security_version) VALUES (?,?,?,'ACTIVE',?,?," + supplied + ")", UUID.randomUUID().toString(), anotherUser, "synthetic-insert", Timestamp.from(NOW), Timestamp.from(NOW)));
        assertThrows(DataAccessException.class, () -> jdbc.update("DELETE FROM " + TABLE + " WHERE id=?", c.id()));
        assertThrows(DataAccessException.class, () -> jdbc.update("DELETE FROM " + TABLE));
        assertThrows(DataAccessException.class, () -> jdbc.execute("TRUNCATE " + TABLE));
        assertEquals(1L, version(c.id()));
    }

    @Test void actualJpaAdapterOmitsVersionAndRollsBackHashAndGenerationTogether() {
        var c = credential();
        tx().executeWithoutResult(s -> { credentials.save(copy(c, "synthetic-jpa", NOW)); em.flush(); });
        assertEquals(2L, version(c.id()));
        var changed = credentials.findById(c.id()).orElseThrow();
        tx().executeWithoutResult(s -> { credentials.save(copy(changed, changed.passwordHash(), NOW.plusSeconds(1))); em.flush(); });
        assertEquals(2L, version(c.id()));
        assertThrows(IllegalStateException.class, () -> tx().executeWithoutResult(s -> {
            credentials.save(copy(changed, "synthetic-rollback", NOW.plusSeconds(2))); em.flush();
            assertEquals(3L, version(c.id())); throw new IllegalStateException("Synthetic downstream failure");
        }));
        assertEquals(2L, version(c.id()));
        assertEquals("synthetic-jpa", credentials.findById(c.id()).orElseThrow().passwordHash());
    }

    @Test void realBootstrapIsIdempotentAndRefusesIncompleteIdentity() {
        String name = "bootstrap-" + UUID.randomUUID();
        var command = new BootstrapLocalAdministratorUseCase.BootstrapCommand(name, "synthetic-bootstrap-password", name + "@hidra.invalid", "Synthetic bootstrap");
        var first = bootstrap.bootstrap(command); var c = credentials.findByUserId(first.userId()).orElseThrow();
        assertTrue(first.created()); assertEquals(1L, version(c.id()));
        var again = bootstrap.bootstrap(command);
        assertFalse(again.created()); assertEquals(first.userId(), again.userId());
        assertEquals(c, credentials.findByUserId(first.userId()).orElseThrow()); assertEquals(1L, version(c.id()));
        String incomplete = user();
        assertThrows(IllegalStateException.class, () -> bootstrap.bootstrap(new BootstrapLocalAdministratorUseCase.BootstrapCommand(incomplete, "synthetic-password", null, null)));
    }

    @Test void nativeBulkCopyMergeAndUpsertExecuteGenerationGuards() throws Exception {
        var a = credential(); var b = credential();
        jdbc.update("UPDATE " + TABLE + " SET password_hash='synthetic-bulk' WHERE id IN (?,?)", a.id(), b.id());
        assertEquals(2L, version(a.id())); assertEquals(2L, version(b.id()));
        jdbc.update("MERGE INTO " + TABLE + " AS c USING (VALUES (?,?)) AS v(id,hash) ON c.id=v.id WHEN MATCHED THEN UPDATE SET password_hash=v.hash", a.id(), "synthetic-merge");
        assertEquals(3L, version(a.id()));
        jdbc.update("INSERT INTO " + TABLE + "(id,user_id,password_hash,credential_status,password_changed_at,created_at,updated_at) VALUES (?,?,?,'ACTIVE',?,?,?) ON CONFLICT(id) DO UPDATE SET password_hash=EXCLUDED.password_hash", a.id(), a.userId(), "synthetic-upsert", Timestamp.from(NOW), Timestamp.from(NOW), Timestamp.from(NOW));
        assertEquals(4L, version(a.id()));
        String id = UUID.randomUUID().toString(), owner = user();
        try (Connection connection = connection()) {
            var copy = connection.unwrap(PGConnection.class).getCopyAPI();
            String sql = "COPY " + TABLE + "(id,user_id,password_hash,credential_status,created_at,updated_at,security_version) FROM STDIN WITH (FORMAT csv)";
            assertEquals(1L, copy.copyIn(sql, new StringReader(id + "," + owner + ",synthetic-copy,ACTIVE,2026-10-12 10:00:00+00,2026-10-12 10:00:00+00,1\n")));
            assertThrows(SQLException.class, () -> copy.copyIn(sql, new StringReader(UUID.randomUUID() + "," + owner + ",synthetic-copy,ACTIVE,2026-10-12 10:00:00+00,2026-10-12 10:00:00+00,5\n")));
        }
        assertEquals(1L, version(id));
    }

    @Test void overflowIsRejectedUsingOnlyAnUncommittedOwnerFixture() throws Exception {
        var value = credential();
        try (Connection c = connection(); var s = c.createStatement()) {
            c.setAutoCommit(false);
            try {
                // Test-owner setup only. All fixture DDL/data are rolled back after testing the enabled guard.
                s.execute("ALTER TABLE " + TABLE + " DISABLE TRIGGER trg_p25_local_credential_security_write");
                s.execute("UPDATE " + TABLE + " SET security_version=9223372036854775807 WHERE id='" + value.id() + "'");
                s.execute("ALTER TABLE " + TABLE + " ENABLE TRIGGER trg_p25_local_credential_security_write");
                s.execute("UPDATE " + TABLE + " SET updated_at=updated_at WHERE id='" + value.id() + "'");
                var savepoint = c.setSavepoint();
                var failure = assertThrows(SQLException.class, () -> s.execute("UPDATE " + TABLE + " SET password_hash='overflow' WHERE id='" + value.id() + "'"));
                assertEquals("22003", failure.getSQLState()); c.rollback(savepoint);
                try (var r = s.executeQuery("SELECT security_version FROM " + TABLE + " WHERE id='" + value.id() + "'")) { assertTrue(r.next()); assertEquals(Long.MAX_VALUE, r.getLong(1)); }
            } finally { c.rollback(); }
        }
        assertEquals(1L, version(value.id())); update(value.id(), "password_hash='after-fixture'"); assertEquals(2L, version(value.id()));
    }

    @Test void independentWritersSerializeAndRollbackConsumesNoGeneration() throws Exception {
        var value = credential();
        try (Connection first = connection(); var executor = Executors.newSingleThreadExecutor()) {
            first.setAutoCommit(false);
            try (var s = first.createStatement()) { s.execute("UPDATE " + TABLE + " SET password_hash='first-writer' WHERE id='" + value.id() + "'"); }
            var entered = new CountDownLatch(1); var backend = new java.util.concurrent.atomic.AtomicInteger();
            var second = executor.submit(() -> {
                try (Connection c = connection(); var s = c.createStatement()) {
                    s.execute("SET statement_timeout='10s'");
                    try (var r = s.executeQuery("SELECT pg_backend_pid()")) { r.next(); backend.set(r.getInt(1)); }
                    entered.countDown(); s.execute("UPDATE " + TABLE + " SET password_hash='second-writer' WHERE id='" + value.id() + "'");
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
                assertTrue(blocked, "Independent backend must be observed waiting for the first row lock"); assertFalse(second.isDone());
                first.commit(); assertTrue(second.get(10, TimeUnit.SECONDS));
            } finally { first.rollback(); }
        }
        assertEquals(3L, version(value.id()));
        assertThrows(IllegalStateException.class, () -> tx().executeWithoutResult(s -> { update(value.id(), "password_hash='rolled-back'"); throw new IllegalStateException("Synthetic rollback"); }));
        assertEquals(3L, version(value.id()));
        assertThrows(DataAccessException.class, () -> jdbc.update("DELETE FROM " + TABLE + " WHERE id=?", value.id())); assertEquals(3L, version(value.id()));
    }

    private TransactionTemplate tx() { return new TransactionTemplate(transactions); }
    private long version(String id) { return jdbc.queryForObject("SELECT security_version FROM " + TABLE + " WHERE id=?", Long.class, id); }
    private void update(String id, String set) { jdbc.update("UPDATE " + TABLE + " SET " + set + " WHERE id=?", id); }
    private Connection connection() throws SQLException { return DriverManager.getConnection(POSTGRES.getJdbcUrl(), POSTGRES.getUsername(), POSTGRES.getPassword()); }
    private String user() {
        String id = UUID.randomUUID().toString();
        users.saveAndFlush(new UserJpaEntity(id, id, id + "@hidra.invalid", "Synthetic User", UserType.HUMAN, UserStatus.ACTIVE, null, null, 0, null, NOW, NOW, null, null, NOW)); return id;
    }
    private LocalCredential credential() {
        var c = new LocalCredential(UUID.randomUUID().toString(), user(), "synthetic-a", "ACTIVE", NOW, NOW, NOW);
        tx().executeWithoutResult(s -> { credentials.save(c); em.flush(); }); return c;
    }
    private LocalCredential copy(LocalCredential c, String hash, Instant update) { return new LocalCredential(c.id(), c.userId(), hash, c.credentialStatus(), c.passwordChangedAt(), c.createdAt(), update); }
}
