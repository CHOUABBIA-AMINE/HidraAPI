/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : UserPermissionGrantIntegrityMigrationTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-06
 *
 * @Type        : Class
 * @Layer       : Identity Test
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.semantic
 *
 * @Description : Enforces the admitted Batch 5 UserPermissionGrantIntegrityMigrationTest contract.
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
class UserPermissionGrantIntegrityMigrationTest {
    @Container static final PostgreSQLContainer<?> POSTGRES = new PostgreSQLContainer<>("postgres:16-alpine");
    private Connection open() throws SQLException {
        return DriverManager.getConnection(POSTGRES.getJdbcUrl(), POSTGRES.getUsername(), POSTGRES.getPassword());
    }
    private void sql(String sql) throws SQLException {
        try (var connection = open(); var statement = connection.createStatement()) { statement.execute(sql); }
    }
    private void migrate() throws Exception {
        sql(Files.readString(Path.of("src/main/resources/db/migration/V20261006_013__hmr_088_direct_permission_bounds.sql")));
    }
    @BeforeEach void setup() throws Exception {
        sql("DROP SCHEMA public CASCADE; CREATE SCHEMA public");
        sql(Files.readString(Path.of("src/main/resources/db/migration/V20260611_001__create_identity_tables.sql")));

    }
    @Test void enforcesDirectGrantContractOnDatabaseWrites() throws Exception {
        migrate();
        String insert = "INSERT INTO hidra_identity_user_permission_grant (id,user_id,permission_id,effect,grant_reason,emergency_access,valid_from,valid_to,status,created_at) VALUES ";
        sql(insert + "('ok','u','p','GRANT','reason',true,now(),now(),'ACTIVE',now())");
        assertThatThrownBy(() -> sql(insert + "('bad','u','p','GRANT','reason',true,now(),NULL,'ACTIVE',now())")).isInstanceOf(SQLException.class);
        assertThatThrownBy(() -> sql(insert + "('bad','u','p','GRANT',' ',false,now(),now(),'ACTIVE',now())")).isInstanceOf(SQLException.class);
        assertThatThrownBy(() -> sql(insert + "('bad','u','p','GRANT','reason',false,now(),now(),'SUSPENDED',now())")).isInstanceOf(SQLException.class);
    }
    @Test void rejectsLegacyUnboundedGrant() throws Exception {
        sql("INSERT INTO hidra_identity_user_permission_grant (id,user_id,permission_id,effect,grant_reason,emergency_access,valid_from,status,created_at) VALUES ('legacy','u','p','GRANT','reason',true,now(),'ACTIVE',now())");
        assertThatThrownBy(this::migrate).isInstanceOf(SQLException.class);
    }
}
