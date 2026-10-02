/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : OrganizationMultilingualTableRetirementMigrationTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-27
 *
 * @Type        : Class
 * @Layer       : Organization Test
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization.infrastructure.persistence
 *
 * @Description : Verifies parity-gated retirement of the legacy organization unit-type translation table.
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
 * Verifies ORG-039 against real PostgreSQL.
 *
 * <p>Business role: proves accepted Arabic/French/English unit-type labels and descriptions
 * remain preserved on the owning entity before the legacy translation table is retired.
 *
 * <p>Architecture role: destructive migration contract test with an explicit pre-drop
 * recovery checkpoint at ORG-036.
 *
 * <p>Validation: proves forward table retirement after exact parity and transactional
 * rollback with the recovery table intact when parity is violated.
 *
 * <p>Usage: guards only the ORG-039 translation-table retirement. Legacy base description
 * and shift/position compatibility columns are outside this task.
 */
@Testcontainers(disabledWithoutDocker = true)
class OrganizationMultilingualTableRetirementMigrationTest {

    private static final MigrationVersion ORG_036 =
            MigrationVersion.fromVersion("20260927.002");
    private static final Instant NOW = Instant.parse("2026-09-27T12:00:00Z");

    @Container
    static final PostgreSQLContainer<?> POSTGRESQL =
            new PostgreSQLContainer<>(DockerImageName.parse("postgres:18-alpine"))
                    .withDatabaseName("hidra_org_multilingual_retirement_test")
                    .withUsername("hidra")
                    .withPassword("hidra");

    @BeforeEach
    void migrateToRecoveryCheckpoint() {
        flyway().clean();
        flyway(ORG_036).migrate();
    }

    @Test
    void dropsLegacyTranslationTableOnlyAfterEmbeddedValuesMatchExactly() throws SQLException {
        insertUnitTypeWithEmbeddedTranslations(
                "unit-type-station",
                "STATION",
                "STATION",
                "محطة",
                "Station",
                "Station",
                "وحدة محطة",
                "Unité station",
                "Station unit"
        );
        insertTranslation("translation-ar", "unit-type-station", " AR ", "محطة", "وحدة محطة");
        insertTranslation("translation-fr", "unit-type-station", "fr", "Station", "Unité station");
        insertTranslation("translation-en", "unit-type-station", "EN", "Station", "Station unit");

        flyway().migrate();

        assertThat(tableExists("hidra_org_unit_type_translation")).isFalse();
        assertThat(queryString(
                "SELECT name_ar FROM hidra_org_unit_type WHERE id = 'unit-type-station'"
        )).isEqualTo("محطة");
        assertThat(queryString(
                "SELECT name_fr FROM hidra_org_unit_type WHERE id = 'unit-type-station'"
        )).isEqualTo("Station");
        assertThat(queryString(
                "SELECT name_en FROM hidra_org_unit_type WHERE id = 'unit-type-station'"
        )).isEqualTo("Station");
        assertThat(queryString(
                "SELECT description_ar FROM hidra_org_unit_type WHERE id = 'unit-type-station'"
        )).isEqualTo("وحدة محطة");
        assertThat(queryString(
                "SELECT description_fr FROM hidra_org_unit_type WHERE id = 'unit-type-station'"
        )).isEqualTo("Unité station");
        assertThat(queryString(
                "SELECT description_en FROM hidra_org_unit_type WHERE id = 'unit-type-station'"
        )).isEqualTo("Station unit");
    }

    @Test
    void dropsEmptyLegacyTableForEmbeddedOnlyUnitTypesCreatedAfterCutover() throws SQLException {
        insertUnitTypeWithEmbeddedTranslations(
                "unit-type-new",
                "NEW_TYPE",
                "OTHER",
                "نوع جديد",
                "Nouveau type",
                "New Type",
                null,
                null,
                null
        );

        flyway().migrate();

        assertThat(tableExists("hidra_org_unit_type_translation")).isFalse();
        assertThat(queryString(
                "SELECT name_en FROM hidra_org_unit_type WHERE id = 'unit-type-new'"
        )).isEqualTo("New Type");
    }

    @Test
    void rejectsLabelMismatchAndKeepsRecoveryTableIntact() throws SQLException {
        insertUnitTypeWithEmbeddedTranslations(
                "unit-type-mismatch",
                "MISMATCH",
                "OTHER",
                "عربي",
                "Français",
                "Embedded English",
                null,
                null,
                null
        );
        insertTranslation("translation-en", "unit-type-mismatch", "en", "Legacy English", null);

        assertThatThrownBy(() -> flyway().migrate())
                .isInstanceOf(FlywayException.class)
                .hasStackTraceContaining(
                        "ORG-039 parity failed: embedded organization unit type multilingual fields differ"
                );

        assertThat(tableExists("hidra_org_unit_type_translation")).isTrue();
        assertThat(queryLong(
                "SELECT count(*) FROM hidra_org_unit_type_translation WHERE unit_type_id = 'unit-type-mismatch'"
        )).isEqualTo(1L);
        assertThat(queryString(
                "SELECT name_en FROM hidra_org_unit_type WHERE id = 'unit-type-mismatch'"
        )).isEqualTo("Embedded English");
    }

    @Test
    void rejectsNullSafeDescriptionMismatchAndKeepsRecoveryTableIntact() throws SQLException {
        insertUnitTypeWithEmbeddedTranslations(
                "unit-type-description-mismatch",
                "DESCRIPTION_MISMATCH",
                "OTHER",
                "عربي",
                "Français",
                "English",
                null,
                null,
                null
        );
        insertTranslation(
                "translation-en",
                "unit-type-description-mismatch",
                "en",
                "English",
                "Legacy description still not copied"
        );

        assertThatThrownBy(() -> flyway().migrate())
                .isInstanceOf(FlywayException.class)
                .hasStackTraceContaining(
                        "ORG-039 parity failed: embedded organization unit type multilingual fields differ"
                );

        assertThat(tableExists("hidra_org_unit_type_translation")).isTrue();
        assertThat(queryString(
                "SELECT description_en FROM hidra_org_unit_type WHERE id = 'unit-type-description-mismatch'"
        )).isNull();
    }

    @Test
    void rejectsUnsupportedLanguageCodeBeforeDrop() throws SQLException {
        insertUnitTypeWithEmbeddedTranslations(
                "unit-type-unsupported",
                "UNSUPPORTED",
                "OTHER",
                "عربي",
                "Français",
                "English",
                null,
                null,
                null
        );
        insertTranslation("translation-de", "unit-type-unsupported", "de", "Deutsch", null);

        assertThatThrownBy(() -> flyway().migrate())
                .isInstanceOf(FlywayException.class)
                .hasStackTraceContaining(
                        "ORG-039 parity failed: unsupported organization unit type language_code"
                );

        assertThat(tableExists("hidra_org_unit_type_translation")).isTrue();
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

    private static void insertUnitTypeWithEmbeddedTranslations(
            String id,
            String code,
            String kind,
            String nameAr,
            String nameFr,
            String nameEn,
            String descriptionAr,
            String descriptionFr,
            String descriptionEn
    ) throws SQLException {
        execute(
                """
                INSERT INTO hidra_org_unit_type (
                    id, code, kind, description,
                    name_ar, name_fr, name_en,
                    description_ar, description_fr, description_en,
                    active, created_at, updated_at
                ) VALUES (?, ?, ?, NULL, ?, ?, ?, ?, ?, ?, true, ?, ?)
                """,
                id,
                code,
                kind,
                nameAr,
                nameFr,
                nameEn,
                descriptionAr,
                descriptionFr,
                descriptionEn,
                Timestamp.from(NOW),
                Timestamp.from(NOW)
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
                id,
                unitTypeId,
                languageCode,
                label,
                description,
                Timestamp.from(NOW),
                Timestamp.from(NOW)
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

    private static boolean tableExists(String tableName) throws SQLException {
        try (Connection connection = connection();
             PreparedStatement statement = connection.prepareStatement(
                     """
                     SELECT EXISTS (
                         SELECT 1
                         FROM information_schema.tables
                         WHERE table_schema = current_schema()
                           AND table_name = ?
                     )
                     """
             )) {
            statement.setString(1, tableName);
            try (ResultSet resultSet = statement.executeQuery()) {
                assertThat(resultSet.next()).isTrue();
                return resultSet.getBoolean(1);
            }
        }
    }
}
