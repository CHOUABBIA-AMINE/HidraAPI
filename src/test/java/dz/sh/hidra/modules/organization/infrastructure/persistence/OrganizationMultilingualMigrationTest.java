/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : OrganizationMultilingualMigrationTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-27
 *
 * @Type        : Class
 * @Layer       : Organization Test
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization.infrastructure.persistence
 *
 * @Description : Verifies the additive PostgreSQL migration from unit-type translations to embedded organization multilingual fields.
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
import java.sql.Timestamp;
import java.time.Instant;

import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.FlywayException;
import org.flywaydb.core.api.MigrationVersion;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

/**
 * Verifies ORG-036 against real PostgreSQL.
 *
 * <p>Business role: protects Arabic/French/English organization labels while moving
 * unit-type translations onto the owning entity.
 *
 * <p>Architecture role: migration contract test using Flyway and PostgreSQL without
 * loading the Spring application context.
 *
 * <p>Validation: proves deterministic backfill, preservation of legacy fields/table,
 * and fail-closed rollback for unsafe legacy translation shapes.
 *
 * <p>Usage: guards the ORG-036 additive migration only; domain/API cutover belongs to
 * later organization roadmap tasks.
 */
@Testcontainers(disabledWithoutDocker = true)
class OrganizationMultilingualMigrationTest {

    private static final MigrationVersion PRE_ORG_036 =
            MigrationVersion.fromVersion("20260927.001");
    private static final MigrationVersion ORG_036 =
            MigrationVersion.fromVersion("20260927.002");
    private static final Instant NOW = Instant.parse("2026-09-27T10:00:00Z");

    @Container
    static final PostgreSQLContainer<?> POSTGRESQL =
            new PostgreSQLContainer<>(DockerImageName.parse("postgres:18-alpine"))
                    .withDatabaseName("hidra_org_multilingual_test")
                    .withUsername("hidra")
                    .withPassword("hidra");

    @BeforeEach
    void migrateToPreOrg036Baseline() {
        flyway().clean();
        flyway(PRE_ORG_036).migrate();
    }

    @Test
    void backfillsDeterministicUnitTypeTranslationsAndPreservesLegacyColumns() throws SQLException {
        insertUnitType("unit-type-station", "STATION", "STATION", "legacy unit-type description");
        insertTranslation("translation-ar", "unit-type-station", " AR ", "محطة", "وحدة محطة");
        insertTranslation("translation-fr", "unit-type-station", "fr", "Station", "Unité station");
        insertTranslation("translation-en", "unit-type-station", "EN", "Station", "Station unit");
        insertPosition("position-operator", "OPERATOR", "legacy position description");
        insertShift("shift-day", "DAY_SHIFT", "Legacy Day Shift");

        flyway(ORG_036).migrate();

        try (Connection connection = connection();
             PreparedStatement statement = connection.prepareStatement(
                     """
                     SELECT name_ar, name_fr, name_en,
                            description_ar, description_fr, description_en, description
                     FROM hidra_org_unit_type
                     WHERE id = ?
                     """
             )) {
            statement.setString(1, "unit-type-station");
            try (ResultSet resultSet = statement.executeQuery()) {
                assertThat(resultSet.next()).isTrue();
                assertThat(resultSet.getString("name_ar")).isEqualTo("محطة");
                assertThat(resultSet.getString("name_fr")).isEqualTo("Station");
                assertThat(resultSet.getString("name_en")).isEqualTo("Station");
                assertThat(resultSet.getString("description_ar")).isEqualTo("وحدة محطة");
                assertThat(resultSet.getString("description_fr")).isEqualTo("Unité station");
                assertThat(resultSet.getString("description_en")).isEqualTo("Station unit");
                assertThat(resultSet.getString("description"))
                        .isEqualTo("legacy unit-type description");
            }
        }

        assertThat(queryString(
                "SELECT description FROM hidra_org_position WHERE id = 'position-operator'"
        )).isEqualTo("legacy position description");
        assertThat(queryString(
                "SELECT description_ar FROM hidra_org_position WHERE id = 'position-operator'"
        )).isNull();

        assertThat(queryString(
                "SELECT name FROM hidra_org_shift WHERE id = 'shift-day'"
        )).isEqualTo("Legacy Day Shift");
        assertThat(queryString(
                "SELECT name_ar FROM hidra_org_shift WHERE id = 'shift-day'"
        )).isNull();

        assertThat(queryLong(
                "SELECT count(*) FROM hidra_org_unit_type_translation WHERE unit_type_id = 'unit-type-station'"
        )).isEqualTo(3L);
    }

    @Test
    void rejectsDuplicateNormalizedLanguageRowsAndRollsBackSchemaChanges() throws SQLException {
        insertUnitType("unit-type-duplicate", "DUPLICATE", "OTHER", null);
        insertTranslation("translation-ar-1", "unit-type-duplicate", "ar", "الأولى", null);
        insertTranslation("translation-ar-2", "unit-type-duplicate", " AR ", "الثانية", null);
        insertTranslation("translation-fr", "unit-type-duplicate", "fr", "Français", null);
        insertTranslation("translation-en", "unit-type-duplicate", "en", "English", null);

        assertThatThrownBy(() -> flyway(ORG_036).migrate())
                .isInstanceOf(FlywayException.class)
                .hasStackTraceContaining("ORG-036 preflight failed: duplicate organization unit type translations");

        assertThat(columnExists("hidra_org_unit_type", "name_ar")).isFalse();
        assertThat(queryLong(
                "SELECT count(*) FROM hidra_org_unit_type_translation WHERE unit_type_id = 'unit-type-duplicate'"
        )).isEqualTo(4L);
    }

    @Test
    void rejectsMissingRequiredLanguageRows() throws SQLException {
        insertUnitType("unit-type-missing", "MISSING", "OTHER", null);
        insertTranslation("translation-ar", "unit-type-missing", "ar", "عربي", null);
        insertTranslation("translation-fr", "unit-type-missing", "fr", "Français", null);

        assertThatThrownBy(() -> flyway(ORG_036).migrate())
                .isInstanceOf(FlywayException.class)
                .hasStackTraceContaining("must have deterministic ar, fr and en translation rows");

        assertThat(columnExists("hidra_org_unit_type", "name_en")).isFalse();
    }

    @Test
    void rejectsOrphanUnitTypeTranslations() throws SQLException {
        insertTranslation("translation-orphan", "missing-unit-type", "ar", "يتيم", null);

        assertThatThrownBy(() -> flyway(ORG_036).migrate())
                .isInstanceOf(FlywayException.class)
                .hasStackTraceContaining("orphan organization unit type translation");

        assertThat(columnExists("hidra_org_unit_type", "name_ar")).isFalse();
    }

    @Test
    void rejectsUnsupportedLanguageCodes() throws SQLException {
        insertTranslation("translation-de", "missing-unit-type", "de", "Deutsch", null);

        assertThatThrownBy(() -> flyway(ORG_036).migrate())
                .isInstanceOf(FlywayException.class)
                .hasStackTraceContaining("unsupported organization unit type language_code");

        assertThat(columnExists("hidra_org_unit_type", "name_ar")).isFalse();
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

    private static void insertUnitType(
            String id,
            String code,
            String kind,
            String description
    ) throws SQLException {
        execute(
                """
                INSERT INTO hidra_org_unit_type (
                    id, code, kind, description, active, created_at, updated_at
                ) VALUES (?, ?, ?, ?, true, ?, ?)
                """,
                id, code, kind, description, Timestamp.from(NOW), Timestamp.from(NOW)
        );
    }

    private static void insertTranslation(
            String id,
            String unitTypeId,
            String languageCode,
            String label,
            String description
    ) throws SQLException {
        execute(
                """
                INSERT INTO hidra_org_unit_type_translation (
                    id, unit_type_id, language_code, label, description, created_at, updated_at
                ) VALUES (?, ?, ?, ?, ?, ?, ?)
                """,
                id, unitTypeId, languageCode, label, description, Timestamp.from(NOW), Timestamp.from(NOW)
        );
    }

    private static void insertPosition(
            String id,
            String code,
            String description
    ) throws SQLException {
        execute(
                """
                INSERT INTO hidra_org_position (
                    id, code, title_ar, title_fr, title_en, level, description,
                    status, created_at, updated_at
                ) VALUES (?, ?, NULL, NULL, NULL, NULL, ?, 'ACTIVE', ?, ?)
                """,
                id, code, description, Timestamp.from(NOW), Timestamp.from(NOW)
        );
    }

    private static void insertShift(
            String id,
            String code,
            String name
    ) throws SQLException {
        execute(
                """
                INSERT INTO hidra_org_shift (
                    id, code, name, shift_type, start_time, end_time, timezone,
                    active, created_at, updated_at
                ) VALUES (?, ?, ?, 'DAY', '08:00', '16:00', 'Africa/Algiers', true, ?, ?)
                """,
                id, code, name, Timestamp.from(NOW), Timestamp.from(NOW)
        );
    }

    private static void execute(String sql, Object... parameters) throws SQLException {
        try (Connection connection = connection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            for (int index = 0; index < parameters.length; index++) {
                statement.setObject(index + 1, parameters[index]);
            }
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

    private static long queryLong(String sql) throws SQLException {
        try (Connection connection = connection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            assertThat(resultSet.next()).isTrue();
            return resultSet.getLong(1);
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
