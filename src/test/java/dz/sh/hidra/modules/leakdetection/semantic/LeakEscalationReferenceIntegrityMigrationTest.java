/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : LeakEscalationReferenceIntegrityMigrationTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-06
 *
 * @Type        : Class
 * @Layer       : Leak Detection Test
 * @Module      : leakdetection
 * @Package     : dz.sh.hidra.modules.leakdetection.semantic
 *
 * @Description : Exercises the escalation candidate migration against PostgreSQL 16.
 *
 */
package dz.sh.hidra.modules.leakdetection.semantic;

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
class LeakEscalationReferenceIntegrityMigrationTest {
    @Container
    static final PostgreSQLContainer<?> POSTGRES = new PostgreSQLContainer<>("postgres:16-alpine");

    @BeforeEach
    void baseline() throws Exception {
        execute("DROP TABLE IF EXISTS hidra_leak_detection_escalation_reference; "
                + "DROP TABLE IF EXISTS hidra_leak_detection_candidate; "
                + "CREATE TABLE hidra_leak_detection_candidate (id varchar(80) PRIMARY KEY); "
                + "CREATE TABLE hidra_leak_detection_escalation_reference "
                + "(id varchar(80) PRIMARY KEY, candidate_id varchar(80)); "
                + "INSERT INTO hidra_leak_detection_candidate VALUES ('candidate-1')");
    }

    @Test
    void protectsWritesUpdatesAndDeletesWhilePreservingNull() throws Exception {
        execute("INSERT INTO hidra_leak_detection_escalation_reference VALUES ('before', 'candidate-1')");
        migrate();
        execute("INSERT INTO hidra_leak_detection_escalation_reference VALUES ('optional', NULL)");
        assertThatThrownBy(() -> execute(
                "INSERT INTO hidra_leak_detection_escalation_reference VALUES ('orphan', 'missing')"))
                .isInstanceOf(SQLException.class);
        assertThatThrownBy(() -> execute(
                "UPDATE hidra_leak_detection_escalation_reference SET candidate_id = 'missing' WHERE id = 'before'"))
                .isInstanceOf(SQLException.class);
        assertThatThrownBy(() -> execute("DELETE FROM hidra_leak_detection_candidate WHERE id = 'candidate-1'"))
                .isInstanceOf(SQLException.class);
    }

    @Test
    void refusesLegacyOrphansWithoutRewritingEvidence() throws Exception {
        execute("INSERT INTO hidra_leak_detection_escalation_reference VALUES ('legacy', 'missing')");
        assertThatThrownBy(this::migrate).isInstanceOf(SQLException.class)
                .hasMessageContaining("HMR-059 preflight failed");
        try (var connection = connection(); var statement = connection.createStatement();
             var rows = statement.executeQuery(
                     "SELECT candidate_id FROM hidra_leak_detection_escalation_reference WHERE id = 'legacy'")) {
            assertThat(rows.next()).isTrue();
            assertThat(rows.getString(1)).isEqualTo("missing");
        }
    }

    private void migrate() throws Exception {
        String sql = Files.readString(Path.of(
                "src/main/resources/db/migration/V20261006_001__hmr_059_leak_escalation_candidate_integrity.sql"));
        try (var connection = connection(); var statement = connection.createStatement()) {
            connection.setAutoCommit(false);
            try {
                statement.execute(sql);
                connection.commit();
            } catch (SQLException exception) {
                connection.rollback();
                throw exception;
            }
        }
    }

    private void execute(String sql) throws SQLException {
        try (var connection = connection(); var statement = connection.createStatement()) {
            statement.execute(sql);
        }
    }

    private Connection connection() throws SQLException {
        return DriverManager.getConnection(POSTGRES.getJdbcUrl(), POSTGRES.getUsername(), POSTGRES.getPassword());
    }
}
