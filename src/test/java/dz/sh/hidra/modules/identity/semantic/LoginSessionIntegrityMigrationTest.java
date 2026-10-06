/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : LoginSessionIntegrityMigrationTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-06
 *
 * @Type        : Class
 * @Layer       : Identity Test
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.semantic
 *
 * @Description : Enforces the admitted Batch 5 LoginSessionIntegrityMigrationTest contract.
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
class LoginSessionIntegrityMigrationTest {
    @Container static final PostgreSQLContainer<?> POSTGRES = new PostgreSQLContainer<>("postgres:16-alpine");
    private Connection open() throws SQLException {
        return DriverManager.getConnection(POSTGRES.getJdbcUrl(), POSTGRES.getUsername(), POSTGRES.getPassword());
    }
    private void sql(String sql) throws SQLException {
        try (var connection = open(); var statement = connection.createStatement()) { statement.execute(sql); }
    }
    private void migrate() throws Exception {
        sql(Files.readString(Path.of("src/main/resources/db/migration/V20261006_012__hmr_087_login_session_evidence.sql")));
    }
    @BeforeEach void setup() throws Exception {
        sql("DROP SCHEMA public CASCADE; CREATE SCHEMA public");
        sql(Files.readString(Path.of("src/main/resources/db/migration/V20260611_001__create_identity_tables.sql")));

    }
    @Test void requiresKnownProtocolAndPreservesNullableLocalReferences() throws Exception {
        migrate();
        sql("INSERT INTO hidra_identity_login_session (id,user_id,started_at,status,session_type) VALUES ('s','u',now(),'ACTIVE','LOCAL')");
        assertThatThrownBy(() -> sql("INSERT INTO hidra_identity_login_session (id,user_id,started_at,status) VALUES ('bad','u',now(),'ACTIVE')")).isInstanceOf(SQLException.class);
        assertThatThrownBy(() -> sql("UPDATE hidra_identity_login_session SET session_type='UNKNOWN' WHERE id='s'")).isInstanceOf(SQLException.class);
    }
    @Test void rejectsGuessingLegacyProtocol() throws Exception {
        sql("INSERT INTO hidra_identity_login_session (id,user_id,started_at,last_seen_at,status) VALUES ('s','u',now(),now(),'LOGGED_OUT')");
        assertThatThrownBy(this::migrate).isInstanceOf(SQLException.class);
    }
}
