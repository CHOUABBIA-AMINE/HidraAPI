/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : OrganizationEmployeeBirthDataMigrationTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-28
 *
 * @Type        : Class
 * @Layer       : Organization Test
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization.infrastructure.persistence
 *
 * @Description : Verifies PostgreSQL persistence and same-module integrity for Employee birth data.
 *
 */
package dz.sh.hidra.modules.organization.infrastructure.persistence;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.MigrationVersion;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

/**
 * Verifies HRA-013 against real PostgreSQL.
 *
 * <p>The migration adds nullable birth data columns to {@code hidra_org_employee}.
 * The optional normalized {@code birth_locality_id} is protected by an Organization-owned
 * foreign key, while free-text Arabic/French/English birthplace values remain independent.</p>
 */
@Testcontainers(disabledWithoutDocker = true)
class OrganizationEmployeeBirthDataMigrationTest {

    private static final MigrationVersion BEFORE_HRA_013 =
            MigrationVersion.fromVersion("20260927.005");

    @Container
    static final PostgreSQLContainer<?> POSTGRESQL =
            new PostgreSQLContainer<>(DockerImageName.parse("postgres:18-alpine"))
                    .withDatabaseName("hidra_org_employee_birth_test")
                    .withUsername("hidra")
                    .withPassword("hidra");

    @BeforeEach
    void migrateToPreHra013Checkpoint() {
        flyway().clean();
        flyway(BEFORE_HRA_013).migrate();
    }

    @Test
    void addsBirthColumnsIndexAndLocalityForeignKey() throws SQLException {
        assertThat(columnExists("hidra_org_employee", "date_of_birth")).isFalse();
        assertThat(columnExists("hidra_org_employee", "birth_locality_id")).isFalse();

        flyway().migrate();

        assertThat(columnExists("hidra_org_employee", "date_of_birth")).isTrue();
        assertThat(columnExists("hidra_org_employee", "birth_locality_id")).isTrue();
        assertThat(columnExists("hidra_org_employee", "birth_place_ar")).isTrue();
        assertThat(columnExists("hidra_org_employee", "birth_place_fr")).isTrue();
        assertThat(columnExists("hidra_org_employee", "birth_place_en")).isTrue();
        assertThat(indexExists("ix_hidra_org_employee_birth_locality_id")).isTrue();
        assertThat(constraintExists("fk_org_employee_birth_locality")).isTrue();
    }

    @Test
    void persistsCanonicalBirthDataAndRejectsUnknownLocality() throws SQLException {
        flyway().migrate();
        seedAdministrativeLocality();

        LocalDate dateOfBirth = LocalDate.of(1990, 5, 3);
        execute(
                """
                INSERT INTO hidra_org_employee
                    (id, employee_number, date_of_birth, birth_locality_id,
                     birth_place_ar, birth_place_fr, birth_place_en,
                     employee_type, status, created_at, updated_at)
                VALUES ('employee-birth-1', 'E-BIRTH-1', ?, 'locality-birth-1',
                        'الجزائر', 'Alger', 'Algiers',
                        'PERMANENT', 'REGISTERED', now(), now())
                """,
                Date.valueOf(dateOfBirth)
        );

        try (Connection connection = connection();
             PreparedStatement statement = connection.prepareStatement(
                     """
                     SELECT date_of_birth, birth_locality_id,
                            birth_place_ar, birth_place_fr, birth_place_en
                     FROM hidra_org_employee
                     WHERE id = 'employee-birth-1'
                     """
             );
             ResultSet resultSet = statement.executeQuery()) {
            assertThat(resultSet.next()).isTrue();
            assertThat(resultSet.getObject("date_of_birth", LocalDate.class)).isEqualTo(dateOfBirth);
            assertThat(resultSet.getString("birth_locality_id")).isEqualTo("locality-birth-1");
            assertThat(resultSet.getString("birth_place_ar")).isEqualTo("الجزائر");
            assertThat(resultSet.getString("birth_place_fr")).isEqualTo("Alger");
            assertThat(resultSet.getString("birth_place_en")).isEqualTo("Algiers");
        }

        assertThatThrownBy(() -> execute(
                """
                INSERT INTO hidra_org_employee
                    (id, employee_number, birth_locality_id,
                     employee_type, status, created_at, updated_at)
                VALUES ('employee-birth-orphan', 'E-BIRTH-ORPHAN', 'missing-locality',
                        'PERMANENT', 'REGISTERED', now(), now())
                """
        )).isInstanceOf(SQLException.class)
                .hasMessageContaining("fk_org_employee_birth_locality");
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

    private static void seedAdministrativeLocality() throws SQLException {
        execute(
                """
                INSERT INTO hidra_org_administrative_state
                    (id, code, active, created_at, updated_at)
                VALUES ('state-birth-1', '16', true, now(), now())
                """
        );
        execute(
                """
                INSERT INTO hidra_org_administrative_district
                    (id, state_id, code, active, created_at, updated_at)
                VALUES ('district-birth-1', 'state-birth-1', 'DISTRICT_BIRTH_1',
                        true, now(), now())
                """
        );
        execute(
                """
                INSERT INTO hidra_org_administrative_locality
                    (id, district_id, code, active, created_at, updated_at)
                VALUES ('locality-birth-1', 'district-birth-1', 'LOCALITY_BIRTH_1',
                        true, now(), now())
                """
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

    private static boolean indexExists(String indexName) throws SQLException {
        try (Connection connection = connection();
             PreparedStatement statement = connection.prepareStatement(
                     """
                     SELECT EXISTS (
                         SELECT 1
                         FROM pg_indexes
                         WHERE schemaname = current_schema()
                           AND indexname = ?
                     )
                     """
             )) {
            statement.setString(1, indexName);
            try (ResultSet resultSet = statement.executeQuery()) {
                assertThat(resultSet.next()).isTrue();
                return resultSet.getBoolean(1);
            }
        }
    }

    private static boolean constraintExists(String constraintName) throws SQLException {
        try (Connection connection = connection();
             PreparedStatement statement = connection.prepareStatement(
                     """
                     SELECT EXISTS (
                         SELECT 1
                         FROM information_schema.table_constraints
                         WHERE constraint_schema = current_schema()
                           AND constraint_name = ?
                     )
                     """
             )) {
            statement.setString(1, constraintName);
            try (ResultSet resultSet = statement.executeQuery()) {
                assertThat(resultSet.next()).isTrue();
                return resultSet.getBoolean(1);
            }
        }
    }
}
