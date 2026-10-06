/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AuthorizationDelegationGrantIntegrityMigrationTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-06
 *
 * @Type        : Class
 * @Layer       : Identity Test
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.semantic
 *
 * @Description : Enforces the admitted Batch 5 AuthorizationDelegationGrantIntegrityMigrationTest contract.
 *
 */
package dz.sh.hidra.modules.identity.semantic;

import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.*;
import org.junit.jupiter.api.*;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import static org.assertj.core.api.Assertions.*;

@Testcontainers(disabledWithoutDocker = true)
class AuthorizationDelegationGrantIntegrityMigrationTest {
    @Container static final PostgreSQLContainer<?> POSTGRES = new PostgreSQLContainer<>("postgres:16-alpine");
    private Connection open() throws SQLException {
        return DriverManager.getConnection(POSTGRES.getJdbcUrl(), POSTGRES.getUsername(), POSTGRES.getPassword());
    }
    private void sql(String sql) throws SQLException {
        try (var connection = open(); var statement = connection.createStatement()) { statement.execute(sql); }
    }
    private void migrate() throws Exception {
        sql(Files.readString(Path.of("src/main/resources/db/migration/V20261006_011__hmr_086_delegation_contract.sql")));
    }
    @BeforeEach void setup() throws Exception {
        sql("DROP SCHEMA public CASCADE; CREATE SCHEMA public");
        sql(Files.readString(Path.of("src/main/resources/db/migration/V20260611_001__create_identity_tables.sql")));

    }
    @Test void rejectsUnboundedBlankAndUnknownOptionalReferences() throws Exception {
        migrate();
        String insert = "INSERT INTO hidra_identity_authorization_delegation_grant (id,delegator_user_id,delegate_user_id,valid_from,valid_to,status,created_at,reason,role_id) VALUES ";
        sql(insert + "('ok','u','v',now(),now(),'ACTIVE',now(),'reason',NULL)");
        assertThatThrownBy(() -> sql(insert + "('bad','u','v',now(),NULL,'ACTIVE',now(),'reason',NULL)")).isInstanceOf(SQLException.class);
        assertThatThrownBy(() -> sql(insert + "('bad','u','v',now(),now(),'ACTIVE',now(),' ',NULL)")).isInstanceOf(SQLException.class);
        assertThatThrownBy(() -> sql(insert + "('bad','u','v',now(),now(),'SUSPENDED',now(),'reason',NULL)")).isInstanceOf(SQLException.class);
        assertThatThrownBy(() -> sql(insert + "('bad','u','v',now(),now(),'ACTIVE',now(),'reason','unknown')")).isInstanceOf(SQLException.class);
    }
    @Test void doesNotInventReasonForLegacyDelegation() throws Exception {
        sql("INSERT INTO hidra_identity_authorization_delegation_grant (id,delegator_user_id,delegate_user_id,valid_from,status,created_at) VALUES ('legacy','u','v',now(),'ACTIVE',now())");
        assertThatThrownBy(this::migrate).isInstanceOf(SQLException.class);
    }
}
