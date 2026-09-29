/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : OrganizationOperationalScopeSchemaMigrationTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-29
 *
 * @Type        : Class
 * @Layer       : Organization Test
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization.infrastructure.persistence
 *
 * @Description : Verifies the additive operational-scope registry and canonical responsibility schema hardening.
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
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

/**
 * Exercises ORG-028 against real PostgreSQL.
 *
 * <p>The original registry migration remains immutable and backward-compatible:
 * canonical assignments use {@code scope_id}, while unreconciled legacy rows may keep
 * {@code scope_id = null}. ORG-028 adds canonical-only temporal validation plus an
 * ACTIVE-identity index used by the serialized overlap/idempotency query path.</p>
 */
@Testcontainers(disabledWithoutDocker = true)
class OrganizationOperationalScopeSchemaMigrationTest {

    private static final MigrationVersion REGISTRY_BASELINE =
            MigrationVersion.fromVersion("20260927.001");

    private static final MigrationVersion BEFORE_ORG_028 =
            MigrationVersion.fromVersion("20260929.002");

    @Container
    static final PostgreSQLContainer<?> POSTGRESQL =
            new PostgreSQLContainer<>(DockerImageName.parse("postgres:18-alpine"))
                    .withDatabaseName("hidra_org_scope_schema_test")
                    .withUsername("hidra")
                    .withPassword("hidra");

    @Test
    void existingRegistryMigrationIsAdditiveAndPreservesLegacyColumns() throws SQLException {
        clean();
        flyway(REGISTRY_BASELINE).migrate();

        assertThat(columnExists("hidra_org_responsibility_assignment", "scope_id")).isTrue();
        assertThat(columnNullable("hidra_org_responsibility_assignment", "scope_id")).isTrue();
        assertThat(columnNullable(
                "hidra_org_responsibility_assignment",
                "operational_scope_type"
        )).isTrue();
        assertThat(columnNullable(
                "hidra_org_responsibility_assignment",
                "operational_scope_id"
        )).isTrue();
        assertThat(columnExists(
                "hidra_org_responsibility_assignment",
                "operational_scope_code"
        )).isTrue();
        assertThat(columnExists(
                "hidra_org_responsibility_assignment",
                "operational_scope_name"
        )).isTrue();

        execute("""
                INSERT INTO hidra_org_operational_scope (id, scope_type, target_id)
                VALUES (100, 'GLOBAL', NULL)
                """);
        execute("""
                INSERT INTO hidra_org_operational_scope (id, scope_type, target_id)
                VALUES (101, 'PIPELINE', 'pipeline-101')
                """);

        assertThatThrownBy(() -> execute("""
                INSERT INTO hidra_org_operational_scope (scope_type, target_id)
                VALUES ('GLOBAL', NULL)
                """))
                .isInstanceOf(SQLException.class)
                .hasMessageContaining("uq_hidra_org_operational_scope_global");

        assertThatThrownBy(() -> execute("""
                INSERT INTO hidra_org_operational_scope (scope_type, target_id)
                VALUES ('PIPELINE', NULL)
                """))
                .isInstanceOf(SQLException.class)
                .hasMessageContaining("ck_hidra_org_operational_scope_shape");

        assertThatThrownBy(() -> execute("""
                INSERT INTO hidra_org_operational_scope (scope_type, target_id)
                VALUES ('PIPELINE', 'pipeline-101')
                """))
                .isInstanceOf(SQLException.class)
                .hasMessageContaining("uq_hidra_org_operational_scope_target");

        executeResponsibility(
                "canonical-1",
                101L,
                "2026-09-29T08:00:00Z",
                null
        );

        assertThat(queryLong("""
                SELECT count(*)
                FROM hidra_org_responsibility_assignment
                WHERE id = 'canonical-1'
                  AND scope_id = 101
                  AND operational_scope_type IS NULL
                  AND operational_scope_id IS NULL
                """)).isEqualTo(1L);

        assertThatThrownBy(() -> executeResponsibility(
                "missing-scope",
                999L,
                "2026-09-29T08:00:00Z",
                null
        )).isInstanceOf(SQLException.class)
                .hasMessageContaining("fk_hidra_org_responsibility_assignment_scope");
    }

    @Test
    void hardeningAddsCanonicalTemporalConstraintAndActiveIdentityIndex()
            throws SQLException {
        clean();
        flyway(BEFORE_ORG_028).migrate();
        seedCanonicalScope();

        executeLegacyResponsibilityWithInvalidHistoricalPeriod("legacy-unreconciled");

        flyway().migrate();

        assertThat(constraintExists("ck_org_responsibility_canonical_temporal")).isTrue();
        assertThat(indexExists("ix_org_responsibility_active_identity")).isTrue();

        executeResponsibility(
                "canonical-valid",
                201L,
                "2026-09-29T09:00:00Z",
                "2026-09-29T12:00:00Z"
        );

        assertThatThrownBy(() -> executeResponsibility(
                "canonical-invalid",
                201L,
                "2026-09-29T12:00:00Z",
                "2026-09-29T11:00:00Z"
        )).isInstanceOf(SQLException.class)
                .hasMessageContaining("ck_org_responsibility_canonical_temporal");

        assertThat(queryLong("""
                SELECT count(*)
                FROM hidra_org_responsibility_assignment
                WHERE id = 'legacy-unreconciled'
                  AND scope_id IS NULL
                """)).isEqualTo(1L);
    }

    @Test
    void hardeningFailsClosedForInvalidPreexistingCanonicalPeriod() throws SQLException {
        clean();
        flyway(BEFORE_ORG_028).migrate();
        seedCanonicalScope();

        executeResponsibility(
                "preexisting-invalid",
                201L,
                "2026-09-29T12:00:00Z",
                "2026-09-29T11:00:00Z"
        );

        assertThatThrownBy(() -> flyway().migrate())
                .isInstanceOf(FlywayException.class)
                .hasStackTraceContaining("ck_org_responsibility_canonical_temporal");

        assertThat(constraintExists("ck_org_responsibility_canonical_temporal")).isFalse();
        assertThat(queryLong("""
                SELECT count(*)
                FROM hidra_org_responsibility_assignment
                WHERE id = 'preexisting-invalid'
                """)).isEqualTo(1L);
    }

    private static void seedCanonicalScope() throws SQLException {
        execute("""
                INSERT INTO hidra_org_operational_scope (id, scope_type, target_id)
                VALUES (201, 'PIPELINE', 'pipeline-201')
                """);
    }

    private static void executeResponsibility(
            String id,
            Long scopeId,
            String validFrom,
            String validTo
    ) throws SQLException {
        try (Connection connection = connection();
             PreparedStatement statement = connection.prepareStatement(
                     """
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
                     VALUES (?, 'RESPONSIBLE', 'ORGANIZATION_UNIT', 'unit-001', ?, NULL,
                             ?::timestamptz, ?::timestamptz, 'ACTIVE', now(), now())
                     """
             )) {
            statement.setString(1, id);
            statement.setObject(2, scopeId);
            statement.setString(3, validFrom);
            statement.setString(4, validTo);
            statement.executeUpdate();
        }
    }

    private static void executeLegacyResponsibilityWithInvalidHistoricalPeriod(String id)
            throws SQLException {
        try (Connection connection = connection();
             PreparedStatement statement = connection.prepareStatement(
                     """
                     INSERT INTO hidra_org_responsibility_assignment (
                         id,
                         responsibility_type,
                         assignee_type,
                         assignee_id,
                         operational_scope_type,
                         operational_scope_id,
                         valid_from,
                         valid_to,
                         status,
                         created_at,
                         updated_at
                     )
                     VALUES (?, 'RESPONSIBLE', 'ORGANIZATION_UNIT', 'unit-legacy',
                             'PIPELINE', 'legacy-pipeline-id',
                             '2026-09-29T12:00:00Z'::timestamptz,
                             '2026-09-29T11:00:00Z'::timestamptz,
                             'ACTIVE', now(), now())
                     """
             )) {
            statement.setString(1, id);
            statement.executeUpdate();
        }
    }

    private static void clean() {
        flyway().clean();
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

    private static boolean constraintExists(String constraintName) throws SQLException {
        return queryBoolean(
                """
                SELECT EXISTS (
                    SELECT 1
                    FROM pg_constraint
                    WHERE conname = ?
                )
                """,
                constraintName
        );
    }

    private static boolean indexExists(String indexName) throws SQLException {
        return queryBoolean(
                """
                SELECT EXISTS (
                    SELECT 1
                    FROM pg_indexes
                    WHERE schemaname = current_schema()
                      AND indexname = ?
                )
                """,
                indexName
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
}
