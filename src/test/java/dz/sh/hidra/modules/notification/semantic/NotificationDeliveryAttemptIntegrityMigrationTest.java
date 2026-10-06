/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : NotificationDeliveryAttemptIntegrityMigrationTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-06
 *
 * @Type        : Class
 * @Layer       : Notification Test
 * @Module      : notification
 * @Package     : dz.sh.hidra.modules.notification.semantic
 *
 * @Description : Exercises append-only attempt evidence and concurrent inserts on PostgreSQL 16.
 *
 */
package dz.sh.hidra.modules.notification.semantic;

import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import static org.assertj.core.api.Assertions.*;

@Testcontainers(disabledWithoutDocker = true)
class NotificationDeliveryAttemptIntegrityMigrationTest {
    @Container
    static final PostgreSQLContainer<?> POSTGRES = new PostgreSQLContainer<>("postgres:16-alpine");
    @BeforeEach
    void baseline() throws Exception {
        execute("DROP SCHEMA public CASCADE; CREATE SCHEMA public; "
            + "CREATE TABLE hidra_notification_message(id text PRIMARY KEY, channel_id text NOT NULL); "
            + "CREATE TABLE hidra_notification_delivery_attempt(id text PRIMARY KEY, message_id text NOT NULL, "
            + "channel_id text NOT NULL, attempt_status text NOT NULL, next_retry_at timestamptz); "
            + "INSERT INTO hidra_notification_message VALUES('message','channel')");
    }
    @Test
    void protectsChannelAndRejectsAllEvidenceRewrites() throws Exception {
        execute("INSERT INTO hidra_notification_delivery_attempt VALUES('before','message','channel','SENT',null)");
        migrate();
        assertThat(scalar("SELECT attempt_status FROM hidra_notification_delivery_attempt WHERE id='before'")).isEqualTo("SENT");
        for (String sql : new String[]{
            "INSERT INTO hidra_notification_delivery_attempt VALUES('wrong','message','other','SENT',null)",
            "UPDATE hidra_notification_delivery_attempt SET attempt_status='DELIVERED' WHERE id='before'",
            "DELETE FROM hidra_notification_delivery_attempt WHERE id='before'",
            "TRUNCATE hidra_notification_delivery_attempt",
            "INSERT INTO hidra_notification_delivery_attempt VALUES('before','message','channel','DELIVERED',null)",
            "UPDATE hidra_notification_message SET channel_id='other' WHERE id='message'"}) {
            assertThatThrownBy(() -> execute(sql)).isInstanceOf(SQLException.class);
        }
        assertThat(scalar("SELECT attempt_status FROM hidra_notification_delivery_attempt WHERE id='before'")).isEqualTo("SENT");
    }
    @Test
    void forbidsPermanentRetryButKeepsTemporaryRetry() throws Exception {
        migrate();
        for (String status : new String[]{"FAILED_PERMANENT", "CANCELLED"}) {
            assertThatThrownBy(() -> execute("INSERT INTO hidra_notification_delivery_attempt VALUES('bad','message',"
                + "'channel','"+status+"',now())")).isInstanceOf(SQLException.class);
            execute("INSERT INTO hidra_notification_delivery_attempt VALUES('"+status+"','message','channel','"+status+"',null)");
        }
        execute("INSERT INTO hidra_notification_delivery_attempt VALUES('temporary','message','channel','FAILED_TEMPORARY',now())");
    }
    @Test
    void concurrentDuplicateIdCannotOverwriteFirstWriter() throws Exception {
        migrate();
        try (var first = connection(); var second = connection()) {
            first.setAutoCommit(false);
            first.createStatement().execute("INSERT INTO hidra_notification_delivery_attempt VALUES('race','message','channel','SENT',null)");
            var started = new java.util.concurrent.CountDownLatch(1);
            var executor = java.util.concurrent.Executors.newSingleThreadExecutor();
            try {
                var result = executor.submit(() -> {
                    started.countDown();
                    try (var s = second.createStatement()) {
                        s.setQueryTimeout(10);
                        s.execute("INSERT INTO hidra_notification_delivery_attempt VALUES('race','message','channel','DELIVERED',null)");
                        return "unexpected success";
                    } catch (SQLException expected) { return expected.getSQLState(); }
                });
                assertThat(started.await(5, java.util.concurrent.TimeUnit.SECONDS)).isTrue();
                first.commit();
                assertThat(result.get(15, java.util.concurrent.TimeUnit.SECONDS)).isEqualTo("23505");
            } finally { executor.shutdownNow(); }
        }
        assertThat(scalar("SELECT attempt_status FROM hidra_notification_delivery_attempt WHERE id='race'")).isEqualTo("SENT");
    }
    @Test
    void abortsLegacyChannelMismatchWithoutRepairingIt() throws Exception {
        execute("INSERT INTO hidra_notification_delivery_attempt VALUES('legacy','message','other','SENT',null)");
        assertThatThrownBy(this::migrate).isInstanceOf(SQLException.class).hasMessageContaining("HMR-060 preflight failed");
        assertThat(scalar("SELECT channel_id FROM hidra_notification_delivery_attempt WHERE id='legacy'")).isEqualTo("other");
    }
    private void migrate() throws Exception {
        String sql = Files.readString(Path.of("src/main/resources/db/migration/V20261006_006__hmr_060_notification_attempt_evidence.sql"));
        try (var c = connection(); var s = c.createStatement()) {
            c.setAutoCommit(false);
            try { s.execute(sql); c.commit(); }
            catch (SQLException failure) { c.rollback(); throw failure; }
        }
    }
    private void execute(String sql) throws SQLException {
        try (var c = connection(); var s = c.createStatement()) { s.execute(sql); }
    }
    private String scalar(String sql) throws SQLException {
        try (var c = connection(); var s = c.createStatement(); var r = s.executeQuery(sql)) {
            assertThat(r.next()).isTrue(); return r.getString(1);
        }
    }
    private Connection connection() throws SQLException {
        return DriverManager.getConnection(POSTGRES.getJdbcUrl(), POSTGRES.getUsername(), POSTGRES.getPassword());
    }
}
