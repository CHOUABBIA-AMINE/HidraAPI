/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : OrganizationMandatoryReferenceMigrationSupport
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-06
 *
 * @Type        : Class
 * @Layer       : Organization Test
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization.semantic
 *
 * @Description : Exercises mandatory-reference corrections against PostgreSQL.
 *
 */
package dz.sh.hidra.modules.organization.semantic;

import jakarta.persistence.Column;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.PostgreSQLContainer;
import static org.assertj.core.api.Assertions.*;

abstract class OrganizationMandatoryReferenceMigrationSupport {
    protected abstract PostgreSQLContainer<?> postgres();
    protected abstract String table();
    protected abstract String column();
    protected abstract String migration();
    protected abstract Class<?> entity();
    protected abstract String field();
    protected abstract String task();

    @BeforeEach
    void preparePreCorrectionSchema() throws Exception {
        execute("DROP TABLE IF EXISTS " + table() + "; DROP TABLE IF EXISTS reference_parent; "
                + "CREATE TABLE reference_parent (id varchar(80) PRIMARY KEY); "
                + "CREATE TABLE " + table() + " (id varchar(80) PRIMARY KEY, " + column()
                + " varchar(80) REFERENCES reference_parent(id)); "
                + "INSERT INTO reference_parent VALUES ('parent-1')");
    }

    @Test
    void mappingMatchesMandatoryReference() throws Exception {
        assertThat(entity().getDeclaredField(field()).getAnnotation(Column.class).nullable()).isFalse();
    }

    @Test
    void migrationPreservesValidReferencesAndRejectsNewNullsAndOrphans() throws Exception {
        execute("INSERT INTO " + table() + " VALUES ('valid', 'parent-1')");
        migrate();
        assertThat(nullable()).isFalse();
        assertThat(reference()).isEqualTo("parent-1");
        assertThatThrownBy(() -> execute("INSERT INTO " + table() + " VALUES ('null', NULL)"))
                .isInstanceOf(SQLException.class);
        assertThatThrownBy(() -> execute("UPDATE " + table() + " SET " + column() + " = NULL WHERE id = 'valid'"))
                .isInstanceOf(SQLException.class);
        assertThatThrownBy(() -> execute("INSERT INTO " + table() + " VALUES ('orphan', 'missing')"))
                .isInstanceOf(SQLException.class);
    }

    @Test
    void legacyNullFailsMigrationWithoutInventingReplacementIdentity() throws Exception {
        execute("INSERT INTO " + table() + " VALUES ('valid', NULL)");
        assertThatThrownBy(this::migrate).isInstanceOf(SQLException.class)
                .hasMessageContaining(task() + " preflight failed");
        assertThat(nullable()).isTrue();
        assertThat(reference()).isNull();
    }

    private void migrate() throws Exception {
        String sql = Files.readString(Path.of("src/main/resources/db/migration/" + migration()));
        try (var connection = connection(); var statement = connection.createStatement()) {
            connection.setAutoCommit(false);
            try {
                statement.execute(sql);
                connection.commit();
            } catch (SQLException exception) {
                connection.rollback();
                throw exception;
            }
        }
    }

    private boolean nullable() throws SQLException {
        try (var connection = connection(); var statement = connection.prepareStatement(
                "SELECT is_nullable FROM information_schema.columns WHERE table_schema = 'public' "
                        + "AND table_name = ? AND column_name = ?")) {
            statement.setString(1, table());
            statement.setString(2, column());
            try (var rows = statement.executeQuery()) {
                assertThat(rows.next()).isTrue();
                return "YES".equals(rows.getString(1));
            }
        }
    }

    private String reference() throws SQLException {
        try (var connection = connection(); var statement = connection.createStatement();
             var rows = statement.executeQuery("SELECT " + column() + " FROM " + table() + " WHERE id = 'valid'")) {
            assertThat(rows.next()).isTrue();
            return rows.getString(1);
        }
    }

    private void execute(String sql) throws SQLException {
        try (var connection = connection(); var statement = connection.createStatement()) {
            statement.execute(sql);
        }
    }

    private Connection connection() throws SQLException {
        return DriverManager.getConnection(postgres().getJdbcUrl(), postgres().getUsername(), postgres().getPassword());
    }
}
