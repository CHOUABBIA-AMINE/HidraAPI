/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : UserIntegrityMigrationTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-06
 *
 * @Type        : Class
 * @Layer       : Identity Test
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.semantic
 *
 * @Description : Enforces the admitted Batch 5 UserIntegrityMigrationTest contract.
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
class UserIntegrityMigrationTest {
    @Container static final PostgreSQLContainer<?> POSTGRES = new PostgreSQLContainer<>("postgres:16-alpine");
    private Connection open() throws SQLException {
        return DriverManager.getConnection(POSTGRES.getJdbcUrl(), POSTGRES.getUsername(), POSTGRES.getPassword());
    }
    private void sql(String sql) throws SQLException {
        try (var connection = open(); var statement = connection.createStatement()) { statement.execute(sql); }
    }
    private void migrate() throws Exception {
        sql(Files.readString(Path.of("src/main/resources/db/migration/V20261006_010__hmr_063_identity_user_uniqueness.sql")));
    }
    @BeforeEach void setup() throws Exception {
        sql("DROP SCHEMA public CASCADE; CREATE SCHEMA public");
        sql(Files.readString(Path.of("src/main/resources/db/migration/V20260611_001__create_identity_tables.sql")));

    }
    private String user(String id, String username, String email) {
        return "INSERT INTO hidra_identity_user (id,username,email_address,user_type,status,failed_login_count,created_at,updated_at) VALUES ('" + id + "','" + username + "'," + email + ",'HUMAN','ACTIVE',0,now(),now())";
    }
    @Test void enforcesDatabaseUniquenessAndAllowsMultipleAbsentEmails() throws Exception {
        migrate();
        sql(user("u1", "alice", "NULL")); sql(user("u2", "bob", "NULL"));
        assertThatThrownBy(() -> sql(user("u3", "alice", "NULL"))).isInstanceOf(SQLException.class);
        sql(user("u4", "carol", "'a@example.com'"));
        assertThatThrownBy(() -> sql(user("u5", "dave", "'a@example.com'"))).isInstanceOf(SQLException.class);
        assertThatThrownBy(() -> sql(user("u6", "  ", "NULL"))).isInstanceOf(SQLException.class);
    }
    @Test void rejectsLegacyDuplicatesWithoutRewritingThem() throws Exception {
        sql(user("u1", "alice", "NULL")); sql(user("u2", "alice", "NULL"));
        assertThatThrownBy(this::migrate).isInstanceOf(SQLException.class);
        try (var c = open(); var rows = c.createStatement().executeQuery("SELECT count(*) FROM hidra_identity_user")) {
            rows.next(); assertThat(rows.getInt(1)).isEqualTo(2);
        }
    }
}
