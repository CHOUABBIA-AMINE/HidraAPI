/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : OrganizationInternalReferenceIntegrityMigrationTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-27
 *
 * @Type        : Class
 * @Layer       : Organization Test
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization.infrastructure.persistence
 *
 * @Description : Verifies fail-closed database integrity for Organization-owned references.
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

/**
 * Verifies ORG-046 against real PostgreSQL.
 *
 * <p>Only same-module Organization references are constrained by foreign keys.
 * Polymorphic Organization references retain their typed column pairs and receive
 * closed discriminator checks plus migration-time target existence preflight.</p>
 *
 * <p>No topology, identity, or other bounded-context table is referenced by this test
 * or by the migration under test.</p>
 */
@Testcontainers(disabledWithoutDocker = true)
class OrganizationInternalReferenceIntegrityMigrationTest {

    private static final MigrationVersion ORG_039 =
            MigrationVersion.fromVersion("20260927.003");

    @Container
    static final PostgreSQLContainer<?> POSTGRESQL =
            new PostgreSQLContainer<>(DockerImageName.parse("postgres:18-alpine"))
                    .withDatabaseName("hidra_org_reference_integrity_test")
                    .withUsername("hidra")
                    .withPassword("hidra");

    @BeforeEach
    void migrateToRecoveryCheckpoint() {
        flyway().clean();
        flyway(ORG_039).migrate();
    }

    @Test
    void addsInternalForeignKeysAndClosedDiscriminatorChecks() throws SQLException {
        seedReferenceParents();

        flyway().migrate();

        assertThat(constraintExists("fk_org_district_state")).isTrue();
        assertThat(constraintExists("fk_org_locality_district")).isTrue();
        assertThat(constraintExists("fk_org_employee_address_employee")).isTrue();
        assertThat(constraintExists("fk_org_employee_address_locality")).isTrue();
        assertThat(constraintExists("fk_org_unit_type")).isTrue();
        assertThat(constraintExists("fk_org_unit_parent")).isTrue();
        assertThat(constraintExists("fk_org_employee_assignment_employee")).isTrue();
        assertThat(constraintExists("fk_org_employee_assignment_unit")).isTrue();
        assertThat(constraintExists("fk_org_employee_assignment_position")).isTrue();
        assertThat(constraintExists("fk_org_shift_assignment_employee")).isTrue();
        assertThat(constraintExists("fk_org_shift_assignment_shift")).isTrue();
        assertThat(constraintExists("fk_org_shift_assignment_unit")).isTrue();
        assertThat(constraintExists("fk_org_delegation_delegator")).isTrue();
        assertThat(constraintExists("fk_org_delegation_delegate")).isTrue();
        assertThat(constraintExists("fk_org_delegation_responsibility")).isTrue();
        assertThat(constraintExists("fk_org_hierarchy_snapshot_employee")).isTrue();

        assertThat(constraintExists("ck_org_contact_target_type")).isTrue();
        assertThat(constraintExists("ck_org_reporting_source_type")).isTrue();
        assertThat(constraintExists("ck_org_reporting_target_type")).isTrue();
        assertThat(constraintExists("ck_org_responsibility_assignee_type")).isTrue();

        assertThatThrownBy(() -> execute(
                """
                INSERT INTO hidra_org_administrative_district
                    (id, state_id, code, active, created_at, updated_at)
                VALUES ('district-orphan', 'missing-state', 'ORPHAN', true, now(), now())
                """
        )).isInstanceOf(SQLException.class)
                .hasMessageContaining("fk_org_district_state");

        assertThatThrownBy(() -> execute(
                """
                INSERT INTO hidra_org_contact_point
                    (id, contact_point_type, target_type, target_id, value,
                     primary_contact, emergency_contact, active, created_at, updated_at)
                VALUES ('contact-invalid', 'EMAIL', 'POSITION', 'position-1', 'ops@example.test',
                        false, false, true, now(), now())
                """
        )).isInstanceOf(SQLException.class)
                .hasMessageContaining("ck_org_contact_target_type");

        assertThatThrownBy(() -> execute(
                """
                INSERT INTO hidra_org_reporting_line
                    (id, reporting_line_type, source_type, source_id, target_type, target_id,
                     valid_from, active, created_at, updated_at)
                VALUES ('line-invalid', 'DIRECT', 'EMPLOYEE', 'employee-1',
                        'PIPELINE', 'pipeline-1', now(), true, now(), now())
                """
        )).isInstanceOf(SQLException.class)
                .hasMessageContaining("ck_org_reporting_target_type");

        assertThatThrownBy(() -> execute(
                """
                INSERT INTO hidra_org_responsibility_assignment
                    (id, responsibility_type, assignee_type, assignee_id,
                     description, valid_from, status, created_at, updated_at)
                VALUES ('responsibility-invalid', 'OPERATIONS', 'POSITION', 'position-1',
                        NULL, now(), 'ACTIVE', now(), now())
                """
        )).isInstanceOf(SQLException.class)
                .hasMessageContaining("ck_org_responsibility_assignee_type");
    }

    @Test
    void rejectsDirectOrphanBeforeAnyConstraintIsInstalled() throws SQLException {
        execute(
                """
                INSERT INTO hidra_org_administrative_district
                    (id, state_id, code, active, created_at, updated_at)
                VALUES ('district-orphan', 'missing-state', 'ORPHAN', true, now(), now())
                """
        );

        assertThatThrownBy(() -> flyway().migrate())
                .isInstanceOf(FlywayException.class)
                .hasStackTraceContaining(
                        "ORG-046 preflight failed: orphan administrative district state_id"
                );

        assertThat(constraintExists("fk_org_district_state")).isFalse();
        assertThat(queryLong(
                "SELECT count(*) FROM hidra_org_administrative_district WHERE id = 'district-orphan'"
        )).isEqualTo(1L);
    }

    @Test
    void rejectsPolymorphicOrphanBeforeDiscriminatorConstraintsAreInstalled() throws SQLException {
        execute(
                """
                INSERT INTO hidra_org_contact_point
                    (id, contact_point_type, target_type, target_id, value,
                     primary_contact, emergency_contact, active, created_at, updated_at)
                VALUES ('contact-orphan', 'EMAIL', 'EMPLOYEE', 'missing-employee',
                        'ops@example.test', false, false, true, now(), now())
                """
        );

        assertThatThrownBy(() -> flyway().migrate())
                .isInstanceOf(FlywayException.class)
                .hasStackTraceContaining("ORG-046 preflight failed: orphan contact-point target");

        assertThat(constraintExists("ck_org_contact_target_type")).isFalse();
        assertThat(queryLong(
                "SELECT count(*) FROM hidra_org_contact_point WHERE id = 'contact-orphan'"
        )).isEqualTo(1L);
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

    private static void seedReferenceParents() throws SQLException {
        execute("""
                INSERT INTO hidra_org_administrative_state
                    (id, code, active, created_at, updated_at)
                VALUES ('state-1', '16', true, now(), now())
                """);
        execute("""
                INSERT INTO hidra_org_administrative_district
                    (id, state_id, code, active, created_at, updated_at)
                VALUES ('district-1', 'state-1', 'DISTRICT_1', true, now(), now())
                """);
        execute("""
                INSERT INTO hidra_org_administrative_locality
                    (id, district_id, code, active, created_at, updated_at)
                VALUES ('locality-1', 'district-1', 'LOCALITY_1', true, now(), now())
                """);
        execute("""
                INSERT INTO hidra_org_unit_type
                    (id, code, kind, name_en, active, created_at, updated_at)
                VALUES ('unit-type-1', 'UNIT_TYPE_1', 'OTHER', 'Unit Type', true, now(), now())
                """);
        execute("""
                INSERT INTO hidra_org_unit
                    (id, code, name_en, unit_type_id, status, created_at, updated_at)
                VALUES ('unit-1', 'UNIT_1', 'Unit 1', 'unit-type-1', 'ACTIVE', now(), now())
                """);
        execute("""
                INSERT INTO hidra_org_position
                    (id, code, title_en, status, created_at, updated_at)
                VALUES ('position-1', 'POSITION_1', 'Position 1', 'ACTIVE', now(), now())
                """);
        execute("""
                INSERT INTO hidra_org_employee
                    (id, employee_number, employee_type, status, created_at, updated_at)
                VALUES ('employee-1', 'E-1', 'PERMANENT', 'ACTIVE', now(), now())
                """);
        execute("""
                INSERT INTO hidra_org_shift
                    (id, code, name, name_en, shift_type, start_time, end_time, timezone,
                     active, created_at, updated_at)
                VALUES ('shift-1', 'SHIFT_1', 'Shift 1', 'Shift 1', 'DAY',
                        '08:00', '16:00', 'Africa/Algiers', true, now(), now())
                """);
        execute("""
                INSERT INTO hidra_org_responsibility_assignment
                    (id, responsibility_type, assignee_type, assignee_id,
                     description, valid_from, status, created_at, updated_at)
                VALUES ('responsibility-1', 'OPERATIONS', 'EMPLOYEE', 'employee-1',
                        NULL, now(), 'ACTIVE', now(), now())
                """);
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
