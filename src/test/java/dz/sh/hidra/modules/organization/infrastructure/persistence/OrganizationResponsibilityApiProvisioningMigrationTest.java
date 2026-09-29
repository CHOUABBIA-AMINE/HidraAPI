/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : OrganizationResponsibilityApiProvisioningMigrationTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-29
 *
 * @Type        : Class
 * @Layer       : Organization Test
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization.infrastructure.persistence
 *
 * @Description : Verifies ORG-030 permission and Audit taxonomy provisioning on empty PostgreSQL.
 *
 */
package dz.sh.hidra.modules.organization.infrastructure.persistence;

import static org.assertj.core.api.Assertions.assertThat;

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
class OrganizationResponsibilityApiProvisioningMigrationTest {

    @Container
    static final PostgreSQLContainer<?> POSTGRESQL =
            new PostgreSQLContainer<>(DockerImageName.parse("postgres:18-alpine"))
                    .withDatabaseName("hidra_org_api_provisioning_test")
                    .withUsername("hidra")
                    .withPassword("hidra");

    @Test
    void emptyDatabaseProvisioningContainsRequiredActivePermissionsAndAuditTaxonomy()
            throws SQLException {
        flyway().clean();
        flyway().migrate();

        for (String code : new String[] {
                "organization:operational-scope:register",
                "organization:responsibility:assign",
                "organization:responsibility:revoke",
                "organization:responsibility:reconcile"
        }) {
            assertThat(queryLong(
                    "SELECT count(*) FROM hidra_identity_permission WHERE code = ? AND status = 'ACTIVE'",
                    code
            )).isEqualTo(1L);
        }

        assertThat(queryLong(
                "SELECT count(*) FROM hidra_audit_catalog_entry "
                        + "WHERE catalog_name = 'EVENT_CATEGORY' AND code = 'BUSINESS' AND active"
        )).isEqualTo(1L);

        for (String code : new String[] {
                "ORGANIZATION_RESPONSIBILITY_ASSIGNED",
                "ORGANIZATION_RESPONSIBILITY_REVOKED",
                "ORGANIZATION_RESPONSIBILITY_RECONCILED"
        }) {
            assertThat(queryLong(
                    "SELECT count(*) FROM hidra_audit_catalog_entry "
                            + "WHERE catalog_name = 'EVENT_TYPE' AND code = ? AND active",
                    code
            )).isEqualTo(1L);
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
