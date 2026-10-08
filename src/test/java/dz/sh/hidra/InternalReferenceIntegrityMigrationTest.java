/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : InternalReferenceIntegrityMigrationTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-08
 *
 * @Type        : Class
 * @Layer       : Repository Remediation Test
 * @Module      : repository-remediation
 * @Package     : dz.sh.hidra
 *
 * @Description : Verifies consolidated same-module scalar reference integrity migrations.
 *
 */
package dz.sh.hidra;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
 * Verifies HRA-111 against real PostgreSQL.
 *
 * <p>The consolidated migrations add only same-module foreign keys. HRA-110 references classified
 * as cross-module, historical, external, or typed/non-relational remain deliberately unconstrained
 * at the database boundary.</p>
 *
 * <p>PostgreSQL {@code NOT VALID} constraints protect new writes immediately; the following
 * {@code VALIDATE CONSTRAINT} statements then fail the migration when legacy orphan rows exist.</p>
 */
@Testcontainers(disabledWithoutDocker = true)
class InternalReferenceIntegrityMigrationTest {

    private static final MigrationVersion PRE_HRA_111 =
            MigrationVersion.fromVersion("20260928.002");

    private static final int NEW_HRA_111_FOREIGN_KEYS = 551;

    // HMR-057/093/091 replace misclassified catalog FKs with owner-local lineage FKs.
    private static final Map<String, ForeignKeyReplacement> SAME_MODULE_REPLACEMENTS = Map.of(
            "hmr057_request_fk", new ForeignKeyReplacement("reporting", "hidra_reporting_run", "hidra_reporting_request"),
            "hmr057_parameter_request_fk", new ForeignKeyReplacement("reporting", "hidra_reporting_parameter_value", "hidra_reporting_request"),
            "hmr093_run_fk", new ForeignKeyReplacement("reporting", "hidra_reporting_output_artifact", "hidra_reporting_run"),
            "fk_hmr091_related_incident", new ForeignKeyReplacement("incident", "hidra_incident_related_incident", "hidra_incident")
    );

    private record ForeignKeyReplacement(String module, String childTable, String parentTable) {}

    private static final Map<String, String> MODULE_TABLE_PREFIXES = new HashMap<>();

    static {
        MODULE_TABLE_PREFIXES.put("identity", "hidra_identity_");
        MODULE_TABLE_PREFIXES.put("party", "hidra_party_");
        MODULE_TABLE_PREFIXES.put("topology", "hidra_topology_");
        MODULE_TABLE_PREFIXES.put("telemetry", "hidra_telemetry_");
        MODULE_TABLE_PREFIXES.put("planning", "hidra_planning_");
        MODULE_TABLE_PREFIXES.put("monitoring", "hidra_monitoring_");
        MODULE_TABLE_PREFIXES.put("alarm", "hidra_alarm_");
        MODULE_TABLE_PREFIXES.put("leakdetection", "hidra_leak_detection_");
        MODULE_TABLE_PREFIXES.put("incident", "hidra_incident_");
        MODULE_TABLE_PREFIXES.put("risk", "hidra_risk_");
        MODULE_TABLE_PREFIXES.put("hse", "hidra_hse_");
        MODULE_TABLE_PREFIXES.put("integrity", "hidra_integrity_");
        MODULE_TABLE_PREFIXES.put("assets", "hidra_asset_");
        MODULE_TABLE_PREFIXES.put("custody", "hidra_custody_");
        MODULE_TABLE_PREFIXES.put("workflow", "hidra_workflow_");
        MODULE_TABLE_PREFIXES.put("audit", "hidra_audit_");
        MODULE_TABLE_PREFIXES.put("documents", "hidra_documents_");
        MODULE_TABLE_PREFIXES.put("integration", "hidra_integration_");
        MODULE_TABLE_PREFIXES.put("configuration", "hidra_configuration_");
        MODULE_TABLE_PREFIXES.put("notification", "hidra_notification_");
        MODULE_TABLE_PREFIXES.put("simulation", "hidra_simulation_");
        MODULE_TABLE_PREFIXES.put("analytics", "hidra_analytics_");
        MODULE_TABLE_PREFIXES.put("reporting", "hidra_reporting_");
    }

    @Container
    static final PostgreSQLContainer<?> POSTGRESQL =
            new PostgreSQLContainer<>(DockerImageName.parse("postgres:18-alpine"))
                    .withDatabaseName("hidra_reference_integrity_test")
                    .withUsername("hidra")
                    .withPassword("hidra");

    @BeforeEach
    void resetDatabase() {
        flyway().clean();
    }

    @Test
    void installsAndValidatesEveryClassifiedSameModuleForeignKey() throws SQLException {
        flyway().migrate();

        int observed = 0;
        try (Connection connection = connection();
             PreparedStatement statement = connection.prepareStatement(
                     """
                     SELECT constraint_name, child_table, parent_table, validated
                     FROM (
                         SELECT c.conname AS constraint_name,
                                child.relname AS child_table,
                                parent.relname AS parent_table,
                                c.convalidated AS validated
                         FROM pg_constraint c
                         JOIN pg_class child ON child.oid = c.conrelid
                         JOIN pg_class parent ON parent.oid = c.confrelid
                         WHERE c.contype = 'f'
                           AND (c.conname LIKE 'fk_hra111_%'
                                OR c.conname IN ('hmr057_request_fk', 'hmr057_parameter_request_fk', 'hmr093_run_fk', 'fk_hmr091_related_incident'))
                     ) integrity_constraints
                     ORDER BY constraint_name
                     """
             );
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                observed++;
                String constraintName = resultSet.getString("constraint_name");
                String childTable = resultSet.getString("child_table");
                String parentTable = resultSet.getString("parent_table");
                boolean validated = resultSet.getBoolean("validated");

                String module;
                if (SAME_MODULE_REPLACEMENTS.containsKey(constraintName)) {
                    ForeignKeyReplacement endpoints = SAME_MODULE_REPLACEMENTS.get(constraintName);
                    assertThat(childTable).as("replacement child for %s", constraintName).isEqualTo(endpoints.childTable());
                    assertThat(parentTable).as("replacement parent for %s", constraintName).isEqualTo(endpoints.parentTable());
                    module = endpoints.module();
                } else {
                    module = moduleFromConstraintName(constraintName);
                }

                assertThat(tableBelongsToModule(module, childTable))
                        .as("child table %s for %s stays in module %s",
                                childTable, constraintName, module)
                        .isTrue();
                assertThat(tableBelongsToModule(module, parentTable))
                        .as("parent table %s for %s stays in module %s",
                                parentTable, constraintName, module)
                        .isTrue();
                assertThat(validated)
                        .as("%s is validated", constraintName)
                        .isTrue();
            }
        }

        assertThat(observed).isEqualTo(NEW_HRA_111_FOREIGN_KEYS);
        for (String replacement : SAME_MODULE_REPLACEMENTS.keySet()) {
            assertThat(constraintExists(replacement)).as("replacement %s exists", replacement).isTrue();
        }
        for (String superseded : List.of("fk_hra111_reporting_019", "fk_hra111_reporting_012", "fk_hra111_reporting_009", "fk_hra111_incident_013")) {
            assertThat(constraintExists(superseded)).as("superseded %s is removed", superseded).isFalse();
        }
        assertThat(constraintExists("fk_org_district_state")).isTrue();
        assertThat(constraintExists("fk_hidra_identity_local_credential_user")).isTrue();
    }

    @Test
    void failsClosedAndRollsBackWhenExistingSameModuleOrphanIsDetected() throws SQLException {
        flyway(PRE_HRA_111).migrate();

        execute(
                """
                INSERT INTO hidra_identity_authorization_policy_version
                    (id, policy_id, version_number, status, effective_from, created_at)
                VALUES ('orphan-policy-version', 'missing-policy', 1, 'DRAFT', now(), now())
                """
        );

        assertThatThrownBy(() -> flyway().migrate())
                .isInstanceOf(FlywayException.class)
                .hasStackTraceContaining("fk_hra111_identity_");

        assertThat(queryLong(
                "SELECT count(*) FROM hidra_identity_authorization_policy_version "
                        + "WHERE id = 'orphan-policy-version'"
        )).isEqualTo(1L);
        assertThat(queryLong(
                "SELECT count(*) FROM pg_constraint WHERE conname LIKE 'fk_hra111_%'"
        )).isZero();
    }

    private static String moduleFromConstraintName(String constraintName) {
        String remainder = constraintName.substring("fk_hra111_".length());
        return remainder.substring(0, remainder.lastIndexOf('_'));
    }

    private static boolean tableBelongsToModule(String module, String table) {
        String prefix = MODULE_TABLE_PREFIXES.get(module);
        if (prefix == null) {
            return false;
        }
        if (table.startsWith(prefix)) {
            return true;
        }
        return ("alarm".equals(module) && "hidra_alarm".equals(table))
                || ("incident".equals(module) && "hidra_incident".equals(table))
                || ("risk".equals(module) && table.startsWith("hidra_residual_risk_"));
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

    private static long queryLong(String sql) throws SQLException {
        try (Connection connection = connection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            assertThat(resultSet.next()).isTrue();
            return resultSet.getLong(1);
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
