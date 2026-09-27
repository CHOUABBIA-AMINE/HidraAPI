/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : OrganizationMultilingualCompatibilityRetirementMigrationTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-27
 *
 * @Type        : Class
 * @Layer       : Organization Test
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization.infrastructure.persistence
 *
 * @Description : Verifies fail-closed retirement of Organization multilingual compatibility columns.
 *
 */
package dz.sh.hidra.modules.organization.infrastructure.persistence;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.FlywayException;
import org.flywaydb.core.api.MigrationVersion;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

@Testcontainers(disabledWithoutDocker = true)
class OrganizationMultilingualCompatibilityRetirementMigrationTest {

    private static final MigrationVersion ORG_046 =
            MigrationVersion.fromVersion("20260927.004");

    @Container
    static final PostgreSQLContainer<?> POSTGRESQL =
            new PostgreSQLContainer<>(DockerImageName.parse("postgres:18-alpine"))
                    .withDatabaseName("hidra_org_multilingual_compatibility_retirement_test")
                    .withUsername("hidra")
                    .withPassword("hidra");

    @BeforeEach
    void migrateToRecoveryCheckpoint() {
        flyway().clean();
        flyway(ORG_046).migrate();
    }

    @Test
    void dropsCompatibilityColumnsOnlyAfterParityIsProven() throws SQLException {
        execute("""
                INSERT INTO hidra_org_unit_type (
                    id, code, kind, description,
                    name_ar, name_fr, name_en,
                    description_ar, description_fr, description_en,
                    active, created_at, updated_at
                ) VALUES (
                    'unit-type-1', 'UNIT_TYPE_1', 'OTHER', 'Station unit',
                    'محطة', 'Station', 'Station',
                    'وحدة محطة', 'Unité station', 'Station unit',
                    true, now(), now()
                )
                """);

        execute("""
                INSERT INTO hidra_org_position (
                    id, code, title_ar, title_fr, title_en, level,
                    description, description_ar, description_fr, description_en,
                    status, created_at, updated_at
                ) VALUES (
                    'position-1', 'POSITION_1', NULL, 'Opérateur', 'Operator', NULL,
                    'Operator position', NULL, NULL, 'Operator position',
                    'ACTIVE', now(), now()
                )
                """);

        execute("""
                INSERT INTO hidra_org_shift (
                    id, code, name, name_ar, name_fr, name_en,
                    shift_type, start_time, end_time, timezone,
                    active, created_at, updated_at
                ) VALUES (
                    'shift-1', 'DAY_SHIFT', 'Day Shift', 'وردية نهارية', 'Quart de jour', ' Day Shift ',
                    'DAY', '08:00', '16:00', 'Africa/Algiers',
                    true, now(), now()
                )
                """);

        flyway().migrate();

        assertThat(columnExists("hidra_org_unit_type", "description")).isFalse();
        assertThat(columnExists("hidra_org_position", "description")).isFalse();
        assertThat(columnExists("hidra_org_shift", "name")).isFalse();

        assertThat(queryString(
                "SELECT description_en FROM hidra_org_unit_type WHERE id = 'unit-type-1'"
        )).isEqualTo("Station unit");
        assertThat(queryString(
                "SELECT description_en FROM hidra_org_position WHERE id = 'position-1'"
        )).isEqualTo("Operator position");
        assertThat(queryString(
                "SELECT name_en FROM hidra_org_shift WHERE id = 'shift-1'"
        )).isEqualTo(" Day Shift ");
    }

    @Test
    void rejectsUnpreservedUnitTypeDescriptionAndKeepsAllColumns() throws SQLException {
        execute("""
                INSERT INTO hidra_org_unit_type (
                    id, code, kind, description,
                    name_en, description_en, active, created_at, updated_at
                ) VALUES (
                    'unit-type-mismatch', 'UNIT_TYPE_MISMATCH', 'OTHER',
                    'Legacy ambiguous description',
                    'Unit Type', 'Different embedded description',
                    true, now(), now()
                )
                """);

        assertThatThrownBy(() -> flyway().migrate())
                .isInstanceOf(FlywayException.class)
                .hasStackTraceContaining(
                        "ORG-047 parity failed: legacy organization unit type description"
                );

        assertCompatibilityColumnsRemain();
    }

    @Test
    void rejectsUnpreservedPositionDescriptionAndKeepsAllColumns() throws SQLException {
        execute("""
                INSERT INTO hidra_org_position (
                    id, code, title_en, description, description_en,
                    status, created_at, updated_at
                ) VALUES (
                    'position-mismatch', 'POSITION_MISMATCH', 'Position',
                    'Legacy ambiguous description', 'Different embedded description',
                    'ACTIVE', now(), now()
                )
                """);

        assertThatThrownBy(() -> flyway().migrate())
                .isInstanceOf(FlywayException.class)
                .hasStackTraceContaining(
                        "ORG-047 parity failed: legacy position description"
                );

        assertCompatibilityColumnsRemain();
    }

    @Test
    void rejectsShiftNameThatDiffersFromCanonicalProjectionAndKeepsAllColumns() throws SQLException {
        execute("""
                INSERT INTO hidra_org_shift (
                    id, code, name, name_en,
                    shift_type, start_time, end_time, timezone,
                    active, created_at, updated_at
                ) VALUES (
                    'shift-mismatch', 'DAY_SHIFT', 'Legacy Shift', 'Day Shift',
                    'DAY', '08:00', '16:00', 'Africa/Algiers',
                    true, now(), now()
                )
                """);

        assertThatThrownBy(() -> flyway().migrate())
                .isInstanceOf(FlywayException.class)
                .hasStackTraceContaining(
                        "ORG-047 parity failed: legacy shift name"
                );

        assertCompatibilityColumnsRemain();
    }

    private static void assertCompatibilityColumnsRemain() throws SQLException {
        assertThat(columnExists("hidra_org_unit_type", "description")).isTrue();
        assertThat(columnExists("hidra_org_position", "description")).isTrue();
        assertThat(columnExists("hidra_org_shift", "name")).isTrue();
    }

    private static Flyway flyway() {
        return Flyway.configure()
                .dataSource(POSTGRESQL.getJdbcUrl(), POSTGRESQL.getUsername(), POSTGRESQL.getPassword())
                .locations("classpath:db/migration")
                .cleanDisabled(false)
                .load();
    }

    private static Flyway flyway(MigrationVersion target) {
        return Flyway.configure()
                .dataSource(POSTGRESQL.getJdbcUrl(), POSTGRESQL.getUsername(), POSTGRESQL.getPassword())
                .locations("classpath:db/migration")
                .cleanDisabled(false)
                .target(target)
                .load();
    }

    private static Connection connection() throws SQLException {
        return DriverManager.getConnection(
                POSTGRESQL.getJdbcUrl(),
                POSTGRESQL.getUsername(),
                POSTGRESQL.getPassword()
        );
    }

    private static void execute(String sql) throws SQLException {
        try (Connection connection = connection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.executeUpdate();
        }
    }

    private static String queryString(String sql) throws SQLException {
        try (Connection connection = connection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            assertThat(resultSet.next()).isTrue();
            return resultSet.getString(1);
        }
    }

    private static boolean columnExists(String tableName, String columnName) throws SQLException {
        try (Connection connection = connection();
             PreparedStatement statement = connection.prepareStatement(
                     """
                     SELECT EXISTS (
                         SELECT 1
                         FROM information_schema.columns
                         WHERE table_schema = current_schema()
                           AND table_name = ?
                           AND column_name = ?
                     )
                     """
             )) {
            statement.setString(1, tableName);
            statement.setString(2, columnName);
            try (ResultSet resultSet = statement.executeQuery()) {
                assertThat(resultSet.next()).isTrue();
                return resultSet.getBoolean(1);
            }
        }
    }
}
