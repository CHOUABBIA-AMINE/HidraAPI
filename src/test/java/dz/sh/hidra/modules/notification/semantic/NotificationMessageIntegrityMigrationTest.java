/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : NotificationMessageIntegrityMigrationTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-06
 *
 * @Type        : Class
 * @Layer       : Notification Test
 * @Module      : notification
 * @Package     : dz.sh.hidra.modules.notification.semantic
 *
 * @Description : Exercises message composition and immutable rendering evidence on PostgreSQL 16.
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
class NotificationMessageIntegrityMigrationTest {
    @Container
    static final PostgreSQLContainer<?> POSTGRES = new PostgreSQLContainer<>("postgres:16-alpine");
    @BeforeEach
    void baseline() throws Exception {
        execute("DROP SCHEMA public CASCADE; CREATE SCHEMA public; "
            + "CREATE TABLE hidra_notification_request_recipient(id text PRIMARY KEY, request_id text NOT NULL); "
            + "CREATE TABLE hidra_notification_catalog_entry(id text PRIMARY KEY, catalog_name text, active boolean); "
            + "CREATE TABLE hidra_notification_template(id text PRIMARY KEY); "
            + "CREATE TABLE hidra_notification_template_version(id text PRIMARY KEY, template_id text, "
            + "version_number int, status text, subject_template text, body_template text, content_format text, variable_schema_json jsonb); "
            + "CREATE TABLE hidra_notification_message(id text PRIMARY KEY, request_id text, recipient_id text, "
            + "template_id text, template_version_id text, priority_id text, status text); "
            + "CREATE TABLE hidra_notification_message_variable(id text PRIMARY KEY, message_id text, variable_name text, value_snapshot text); "
            + "INSERT INTO hidra_notification_request_recipient VALUES('recipient','request'); "
            + "INSERT INTO hidra_notification_catalog_entry VALUES('priority','NOTIFICATION_PRIORITY',true), "
            + "('wrong','NOTIFICATION_CATEGORY',true),('inactive','NOTIFICATION_PRIORITY',false); "
            + "INSERT INTO hidra_notification_template VALUES('template'),('other'); "
            + "INSERT INTO hidra_notification_template_version VALUES('version','template',1,'ACTIVE',null,'content','TEXT', "
            + "'{\"required\":[\"asset\"]}'); "
            + "INSERT INTO hidra_notification_template_version VALUES('simple','template',2,'ACTIVE',null,'content','TEXT',null)");
    }
    @Test
    void rejectsRecipientMismatchAndInvalidPriorityButAllowsManualContent() throws Exception {
        migrate();
        execute("INSERT INTO hidra_notification_message VALUES('manual','request','recipient',null,null,null,'READY')");
        for (String priority : new String[]{"missing", "wrong", "inactive"}) {
            assertThatThrownBy(() -> execute("INSERT INTO hidra_notification_message VALUES('bad','request',"
                + "'recipient',null,null,'" + priority + "','READY')")).isInstanceOf(SQLException.class);
        }
        assertThatThrownBy(() -> execute("UPDATE hidra_notification_message SET request_id='other' WHERE id='manual'"))
            .isInstanceOf(SQLException.class);
        assertThatThrownBy(() -> execute("UPDATE hidra_notification_request_recipient SET request_id='other'"))
            .isInstanceOf(SQLException.class);
    }
    @Test
    void requiresExactVersionAndInputsBeforeReadyOrScheduled() throws Exception {
        migrate();
        assertThatThrownBy(() -> execute("INSERT INTO hidra_notification_message VALUES('missing','request',"
            + "'recipient','template',null,null,'READY')")).isInstanceOf(SQLException.class);
        assertThatThrownBy(() -> execute("INSERT INTO hidra_notification_message VALUES('mismatch','request',"
            + "'recipient','other','simple',null,'READY')")).isInstanceOf(SQLException.class);
        execute("INSERT INTO hidra_notification_message VALUES('draft','request','recipient','template','version','priority','DRAFT')");
        for (String status : new String[]{"READY", "SCHEDULED", "DISPATCHING"}) {
            assertThatThrownBy(() -> execute("UPDATE hidra_notification_message SET status='"+status+"' WHERE id='draft'"))
                .isInstanceOf(SQLException.class);
        }
        execute("INSERT INTO hidra_notification_message_variable VALUES('input','draft','asset',' ')");
        assertThatThrownBy(() -> execute("UPDATE hidra_notification_message SET status='READY' WHERE id='draft'"))
            .isInstanceOf(SQLException.class);
        execute("UPDATE hidra_notification_message_variable SET value_snapshot='pipeline-1'; "
            + "UPDATE hidra_notification_message SET status='READY' WHERE id='draft'");
        assertThat(scalar("SELECT status FROM hidra_notification_message WHERE id='draft'")).isEqualTo("READY");
        assertThatThrownBy(() -> execute("UPDATE hidra_notification_message SET status='DRAFT' WHERE id='draft'"))
            .isInstanceOf(SQLException.class);
        assertThatThrownBy(() -> execute("DELETE FROM hidra_notification_message_variable"))
            .isInstanceOf(SQLException.class);
        assertThatThrownBy(() -> execute("UPDATE hidra_notification_message SET template_version_id='simple' WHERE id='draft'"))
            .isInstanceOf(SQLException.class);
        assertThatThrownBy(() -> execute("UPDATE hidra_notification_template_version SET body_template='rewritten' WHERE id='version'"))
            .isInstanceOf(SQLException.class);
        assertThatThrownBy(() -> execute("UPDATE hidra_notification_catalog_entry SET catalog_name='OTHER' WHERE id='priority'"))
            .isInstanceOf(SQLException.class);
    }
    @Test
    void malformedRequiredContractFailsClosed() throws Exception {
        execute("UPDATE hidra_notification_template_version SET variable_schema_json='{\"required\":true}' WHERE id='version'");
        migrate();
        assertThatThrownBy(() -> execute("INSERT INTO hidra_notification_message VALUES('bad','request',"
            + "'recipient','template','version',null,'READY')")).isInstanceOf(SQLException.class);
    }
    @Test
    void abortsLegacyInconsistencyWithoutRewritingRows() throws Exception {
        execute("INSERT INTO hidra_notification_message VALUES('legacy','other','recipient',null,null,null,'READY')");
        assertThatThrownBy(this::migrate).isInstanceOf(SQLException.class).hasMessageContaining("HMR-052 preflight failed");
        assertThat(scalar("SELECT request_id FROM hidra_notification_message WHERE id='legacy'")).isEqualTo("other");
    }
    private void migrate() throws Exception {
        String sql = Files.readString(Path.of("src/main/resources/db/migration/V20261006_005__hmr_052_notification_message_composition.sql"));
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
