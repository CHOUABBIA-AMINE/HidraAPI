/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : OrganizationEmployeeContactBackfillMigrationTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-28
 *
 * @Type        : Class
 * @Layer       : Organization Test
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization.infrastructure.persistence
 *
 * @Description : Verifies deterministic, idempotent legacy Employee contact backfill.
 *
 */
package dz.sh.hidra.modules.organization.infrastructure.persistence;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.MigrationVersion;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

/**
 * Verifies HRA-021 against real PostgreSQL.
 *
 * <p>The migration only creates a canonical contact when no contact point of that type
 * already exists for the Employee. Existing exact matches and conflicts are preserved;
 * conflicting legacy values remain in the Employee compatibility columns for explicit
 * review rather than being silently promoted over canonical state.</p>
 */
@Testcontainers(disabledWithoutDocker = true)
class OrganizationEmployeeContactBackfillMigrationTest {

    private static final MigrationVersion BEFORE_HRA_021 =
            MigrationVersion.fromVersion("20260928.001");

    private static final Path HRA_021_MIGRATION = Path.of(
            "src/main/resources/db/migration/"
                    + "V20260928_002__backfill_employee_contact_points.sql"
    );

    @Container
    static final PostgreSQLContainer<?> POSTGRESQL =
            new PostgreSQLContainer<>(DockerImageName.parse("postgres:18-alpine"))
                    .withDatabaseName("hidra_org_employee_contact_backfill_test")
                    .withUsername("hidra")
                    .withPassword("hidra");

    @BeforeEach
    void migrateToPreHra021Checkpoint() throws SQLException {
        flyway().clean();
        flyway(BEFORE_HRA_021).migrate();
        seedLegacyAndCanonicalContactScenarios();
    }

    @Test
    void backfillsOnlyUnambiguousLegacyContacts() throws SQLException {
        flyway().migrate();

        assertThat(contactCount("employee-new", "EMAIL")).isEqualTo(1L);
        assertThat(contactValue("employee-new", "EMAIL")).isEqualTo("new@example.test");
        assertThat(contactCount("employee-new", "MOBILE")).isEqualTo(1L);
        assertThat(contactValue("employee-new", "MOBILE")).isEqualTo("+213555000001");

        assertThat(contactCount("employee-exact", "EMAIL")).isEqualTo(1L);
        assertThat(contactValue("employee-exact", "EMAIL")).isEqualTo("exact@example.test");

        assertThat(contactCount("employee-conflict", "EMAIL")).isEqualTo(1L);
        assertThat(contactValue("employee-conflict", "EMAIL")).isEqualTo("canonical@example.test");
        assertThat(employeeLegacyEmail("employee-conflict"))
                .isEqualTo("legacy-conflict@example.test");

        assertThat(contactCount("employee-blank", "EMAIL")).isZero();
        assertThat(contactCount("employee-blank", "MOBILE")).isZero();
    }

    @Test
    void migrationSqlIsIdempotentWhenReexecuted() throws SQLException, IOException {
        flyway().migrate();

        long countBefore = totalEmployeeContactCount();
        executeMigrationSourceAgain();
        long countAfter = totalEmployeeContactCount();

        assertThat(countAfter).isEqualTo(countBefore);
        assertThat(contactCount("employee-new", "EMAIL")).isEqualTo(1L);
        assertThat(contactCount("employee-new", "MOBILE")).isEqualTo(1L);
        assertThat(contactCount("employee-exact", "EMAIL")).isEqualTo(1L);
        assertThat(contactCount("employee-conflict", "EMAIL")).isEqualTo(1L);
    }

    @Test
    void deterministicIdsStayWithinOrganizationIdStorageLimit() throws SQLException {
        flyway().migrate();

        assertThat(contactId("employee-new", "EMAIL"))
                .startsWith("hra021-email-")
                .hasSizeLessThanOrEqualTo(80);
        assertThat(contactId("employee-new", "MOBILE"))
                .startsWith("hra021-mobile-")
                .hasSizeLessThanOrEqualTo(80);
    }

    private static void seedLegacyAndCanonicalContactScenarios() throws SQLException {
        insertEmployee(
                "employee-new",
                "E-NEW",
                "  new@example.test  ",
                "  +213555000001  "
        );
        insertEmployee(
                "employee-exact",
                "E-EXACT",
                "exact@example.test",
                null
        );
        insertEmployee(
                "employee-conflict",
                "E-CONFLICT",
                "legacy-conflict@example.test",
                null
        );
        insertEmployee(
                "employee-blank",
                "E-BLANK",
                "   ",
                ""
        );

        insertContact(
                "existing-exact-email",
                "EMAIL",
                "employee-exact",
                "exact@example.test"
        );
        insertContact(
                "existing-conflict-email",
                "EMAIL",
                "employee-conflict",
                "canonical@example.test"
        );
    }

    private static void insertEmployee(
            String id,
            String employeeNumber,
            String email,
            String mobile
    ) throws SQLException {
        try (Connection connection = connection();
             PreparedStatement statement = connection.prepareStatement(
                     """
                     INSERT INTO hidra_org_employee (
                         id,
                         employee_number,
                         email_address,
                         mobile_number,
                         employee_type,
                         status,
                         created_at,
                         updated_at
                     )
                     VALUES (?, ?, ?, ?, 'PERMANENT', 'ACTIVE', now(), now())
                     """
             )) {
            statement.setString(1, id);
            statement.setString(2, employeeNumber);
            statement.setString(3, email);
            statement.setString(4, mobile);
            statement.executeUpdate();
        }
    }

    private static void insertContact(
            String id,
            String type,
            String employeeId,
            String value
    ) throws SQLException {
        try (Connection connection = connection();
             PreparedStatement statement = connection.prepareStatement(
                     """
                     INSERT INTO hidra_org_contact_point (
                         id,
                         contact_point_type,
                         target_type,
                         target_id,
                         value,
                         primary_contact,
                         emergency_contact,
                         active,
                         created_at,
                         updated_at
                     )
                     VALUES (?, ?, 'EMPLOYEE', ?, ?, false, false, true, now(), now())
                     """
             )) {
            statement.setString(1, id);
            statement.setString(2, type);
            statement.setString(3, employeeId);
            statement.setString(4, value);
            statement.executeUpdate();
        }
    }

    private static long contactCount(String employeeId, String type) throws SQLException {
        return queryLong(
                """
                SELECT count(*)
                FROM hidra_org_contact_point
                WHERE target_type = 'EMPLOYEE'
                  AND target_id = ?
                  AND contact_point_type = ?
                """,
                employeeId,
                type
        );
    }

    private static long totalEmployeeContactCount() throws SQLException {
        return queryLong(
                """
                SELECT count(*)
                FROM hidra_org_contact_point
                WHERE target_type = 'EMPLOYEE'
                """
        );
    }

    private static String contactValue(String employeeId, String type) throws SQLException {
        return queryString(
                """
                SELECT value
                FROM hidra_org_contact_point
                WHERE target_type = 'EMPLOYEE'
                  AND target_id = ?
                  AND contact_point_type = ?
                """,
                employeeId,
                type
        );
    }

    private static String contactId(String employeeId, String type) throws SQLException {
        return queryString(
                """
                SELECT id
                FROM hidra_org_contact_point
                WHERE target_type = 'EMPLOYEE'
                  AND target_id = ?
                  AND contact_point_type = ?
                """,
                employeeId,
                type
        );
    }

    private static String employeeLegacyEmail(String employeeId) throws SQLException {
        return queryString(
                "SELECT email_address FROM hidra_org_employee WHERE id = ?",
                employeeId
        );
    }

    private static long queryLong(String sql, Object... parameters) throws SQLException {
        try (Connection connection = connection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            bind(statement, parameters);
            try (ResultSet resultSet = statement.executeQuery()) {
                assertThat(resultSet.next()).isTrue();
                return resultSet.getLong(1);
            }
        }
    }

    private static String queryString(String sql, Object... parameters) throws SQLException {
        try (Connection connection = connection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            bind(statement, parameters);
            try (ResultSet resultSet = statement.executeQuery()) {
                assertThat(resultSet.next()).isTrue();
                return resultSet.getString(1);
            }
        }
    }

    private static void bind(PreparedStatement statement, Object... parameters)
            throws SQLException {
        for (int index = 0; index < parameters.length; index++) {
            statement.setObject(index + 1, parameters[index]);
        }
    }

    private static void executeMigrationSourceAgain() throws IOException, SQLException {
        String sql = Files.readString(HRA_021_MIGRATION);
        try (Connection connection = connection();
             Statement statement = connection.createStatement()) {
            statement.execute(sql);
        }
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

    private static Flyway flyway(MigrationVersion target) {
        return Flyway.configure()
                .dataSource(
                        POSTGRESQL.getJdbcUrl(),
                        POSTGRESQL.getUsername(),
                        POSTGRESQL.getPassword()
                )
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
}
