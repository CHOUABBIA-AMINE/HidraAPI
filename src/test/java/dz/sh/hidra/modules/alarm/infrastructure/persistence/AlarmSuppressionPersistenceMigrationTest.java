/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AlarmSuppressionPersistenceMigrationTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-02
 *
 * @Type        : Test
 * @Layer       : Test
 * @Module      : alarm
 * @Package     : dz.sh.hidra.modules.alarm.infrastructure.persistence
 *
 * @Description : Verifies migrated suppression concurrency and Audit taxonomy invariants on PostgreSQL.
 *
 */
package dz.sh.hidra.modules.alarm.infrastructure.persistence;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;
import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

@Testcontainers(disabledWithoutDocker = true)
class AlarmSuppressionPersistenceMigrationTest {

    @Container
    static final PostgreSQLContainer<?> POSTGRESQL =
            new PostgreSQLContainer<>(DockerImageName.parse("postgres:18-alpine"))
                    .withDatabaseName("hidra_alarm_suppression_test")
                    .withUsername("hidra")
                    .withPassword("hidra");

    @Test
    void migratedSchemaEnforcesSingleExactActiveScopeAndProvidesExpiryAuditTaxonomy()
            throws SQLException {
        flyway().clean();
        flyway().migrate();

        Instant now = Instant.parse("2026-10-02T10:00:00Z");
        execute("""
                INSERT INTO hidra_alarm_catalog_entry (
                    id, catalog_name, code, active, sort_order, system_defined, created_at, updated_at
                ) VALUES (?, 'SUPPRESSION_REASON', 'MAINTENANCE', true, 1, true, ?, ?)
                """,
                "alarm-reason-maintenance", Timestamp.from(now), Timestamp.from(now));

        insertSuppression("suppression-1", "TOPOLOGY_ASSET", "asset-1", "ACTIVE", now);

        assertThatThrownBy(() ->
                insertSuppression("suppression-2", "TOPOLOGY_ASSET", "asset-1", "ACTIVE", now)
        ).isInstanceOf(SQLException.class);

        insertSuppression("suppression-3", "TOPOLOGY_ASSET", "asset-1", "RELEASED", now);

        assertThat(queryLong("""
                SELECT count(*)
                FROM hidra_alarm_suppression
                WHERE scope_type = 'TOPOLOGY_ASSET'
                  AND scope_reference_id = 'asset-1'
                """)).isEqualTo(2L);

        assertThat(queryLong("""
                SELECT count(*)
                FROM hidra_audit_catalog_entry
                WHERE catalog_name = 'EVENT_TYPE'
                  AND code = 'ALARM_SUPPRESSION_EXPIRED'
                  AND active
                """)).isEqualTo(1L);
    }

    private static void insertSuppression(
            String id,
            String scopeType,
            String scopeReferenceId,
            String status,
            Instant now
    ) throws SQLException {
        execute("""
                INSERT INTO hidra_alarm_suppression (
                    id, scope_type, scope_reference_id, suppression_reason_id,
                    suppressed_by_actor_id, suppressed_at, suppressed_until, status, correlation_id
                ) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)
                """,
                id,
                scopeType,
                scopeReferenceId,
                "alarm-reason-maintenance",
                "actor-1",
                Timestamp.from(now.minusSeconds(300)),
                Timestamp.from(now.plusSeconds(3600)),
                status,
                "corr-" + id
        );
    }

    private static Flyway flyway() {
        return Flyway.configure()
                .dataSource(
                        POSTGRESQL.getJdbcUrl(),
                        POSTGRESQL.getUsername(),
                        POSTGRESQL.getPassword()
                )
                .locations("classpath:db/migration")
                .cleanDisabled(false)
                .load();
    }

    private static void execute(String sql, Object... parameters) throws SQLException {
        try (Connection connection = DriverManager.getConnection(
                POSTGRESQL.getJdbcUrl(),
                POSTGRESQL.getUsername(),
                POSTGRESQL.getPassword()
        );
             PreparedStatement statement = connection.prepareStatement(sql)) {
            for (int i = 0; i < parameters.length; i++) {
                statement.setObject(i + 1, parameters[i]);
            }
            statement.executeUpdate();
        }
    }

    private static long queryLong(String sql, Object... parameters) throws SQLException {
        try (Connection connection = DriverManager.getConnection(
                POSTGRESQL.getJdbcUrl(),
                POSTGRESQL.getUsername(),
                POSTGRESQL.getPassword()
        );
             PreparedStatement statement = connection.prepareStatement(sql)) {
            for (int i = 0; i < parameters.length; i++) {
                statement.setObject(i + 1, parameters[i]);
            }
            try (ResultSet resultSet = statement.executeQuery()) {
                assertThat(resultSet.next()).isTrue();
                return resultSet.getLong(1);
            }
        }
    }
}
