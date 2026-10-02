/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : OrganizationGreenfieldScopePersistenceTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-29
 *
 * @Type        : Class
 * @Layer       : Organization Test
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization.infrastructure.persistence
 *
 * @Description : Verifies canonical operational-scope persistence from an empty PostgreSQL database.
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
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

@Testcontainers(disabledWithoutDocker = true)
class OrganizationGreenfieldScopePersistenceTest {

    @Container
    static final PostgreSQLContainer<?> POSTGRESQL =
            new PostgreSQLContainer<>(DockerImageName.parse("postgres:18-alpine"))
                    .withDatabaseName("hidra_org_greenfield_test")
                    .withUsername("hidra")
                    .withPassword("hidra");

    @Test
    void emptyDatabaseMigratesAndCanonicalResponsibilityUsesOnlyRegistryIdentity()
            throws SQLException {
        cleanAndMigrate();

        assertThat(queryLong("SELECT count(*) FROM hidra_org_operational_scope")).isZero();
        assertThat(queryLong("SELECT count(*) FROM hidra_org_responsibility_assignment")).isZero();
        assertLegacyScopeColumnsRetired();
        assertThat(columnNullable(
                "hidra_org_responsibility_assignment",
                "scope_id"
        )).isFalse();

        execute("""
                INSERT INTO hidra_org_operational_scope (id, scope_type, target_id)
                VALUES (501, 'PIPELINE', 'pipeline-greenfield-1')
                """);

        execute("""
                INSERT INTO hidra_org_responsibility_assignment (
                    id,
                    responsibility_type,
                    assignee_type,
                    assignee_id,
                    scope_id,
                    description,
                    valid_from,
                    valid_to,
                    status,
                    created_at,
                    updated_at
                )
                VALUES (
                    'responsibility-greenfield-1',
                    'RESPONSIBLE',
                    'ORGANIZATION_UNIT',
                    'unit-greenfield-1',
                    501,
                    'greenfield canonical persistence',
                    '2026-09-29T11:00:00Z'::timestamptz,
                    NULL,
                    'ACTIVE',
                    now(),
                    now()
                )
                """);

        assertThat(queryLong("""
                SELECT count(*)
                FROM hidra_org_responsibility_assignment assignment
                JOIN hidra_org_operational_scope scope ON scope.id = assignment.scope_id
                WHERE assignment.id = 'responsibility-greenfield-1'
                  AND assignment.scope_id = 501
                  AND scope.scope_type = 'PIPELINE'
                  AND scope.target_id = 'pipeline-greenfield-1'
                """)).isEqualTo(1L);

        assertThatThrownBy(() -> execute("""
                INSERT INTO hidra_org_operational_scope (scope_type, target_id)
                VALUES ('PIPELINE', 'pipeline-greenfield-1')
                """))
                .isInstanceOf(SQLException.class)
                .hasMessageContaining("uq_hidra_org_operational_scope_target");

        assertThat(queryLong("""
                SELECT count(*)
                FROM hidra_org_operational_scope
                WHERE scope_type = 'PIPELINE'
                  AND target_id = 'pipeline-greenfield-1'
                """)).isEqualTo(1L);
    }

    @Test
    void canonicalResponsibilityRejectsMissingRegistryIdentity() throws SQLException {
        cleanAndMigrate();

        assertThatThrownBy(() -> execute("""
                INSERT INTO hidra_org_responsibility_assignment (
                    id,
                    responsibility_type,
                    assignee_type,
                    assignee_id,
                    scope_id,
                    valid_from,
                    status,
                    created_at,
                    updated_at
                )
                VALUES (
                    'responsibility-missing-scope',
                    'RESPONSIBLE',
                    'ORGANIZATION_UNIT',
                    'unit-greenfield-1',
                    999999,
                    now(),
                    'ACTIVE',
                    now(),
                    now()
                )
                """))
                .isInstanceOf(SQLException.class)
                .hasMessageContaining("fk_hidra_org_responsibility_assignment_scope");
    }

    private static void assertLegacyScopeColumnsRetired() throws SQLException {
        for (String table : new String[] {
                "hidra_org_unit",
                "hidra_org_employee_assignment",
                "hidra_org_responsibility_assignment"
        }) {
            for (String column : new String[] {
                    "operational_scope_type",
                    "operational_scope_id",
                    "operational_scope_code",
                    "operational_scope_name"
            }) {
                assertThat(columnExists(table, column))
                        .as(table + "." + column + " must be retired")
                        .isFalse();
            }
        }
    }

    private static boolean columnExists(String table, String column) throws SQLException {
        return queryBoolean(
                """
                SELECT EXISTS (
                    SELECT 1
                    FROM information_schema.columns
                    WHERE table_schema = current_schema()
                      AND table_name = ?
                      AND column_name = ?
                )
                """,
                table,
                column
        );
    }

    private static boolean columnNullable(String table, String column) throws SQLException {
        return queryBoolean(
                """
                SELECT EXISTS (
                    SELECT 1
                    FROM information_schema.columns
                    WHERE table_schema = current_schema()
                      AND table_name = ?
                      AND column_name = ?
                      AND is_nullable = 'YES'
                )
                """,
                table,
                column
        );
    }

    private static boolean queryBoolean(String sql, Object... parameters) throws SQLException {
        try (Connection connection = connection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            for (int index = 0; index < parameters.length; index++) {
                statement.setObject(index + 1, parameters[index]);
            }
            try (ResultSet resultSet = statement.executeQuery()) {
                assertThat(resultSet.next()).isTrue();
                return resultSet.getBoolean(1);
            }
        }
    }

    private static void cleanAndMigrate() {
        flyway().clean();
        flyway().migrate();
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

    private static long queryLong(String sql) throws SQLException {
        try (Connection connection = connection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            assertThat(resultSet.next()).isTrue();
            return resultSet.getLong(1);
        }
    }
}
