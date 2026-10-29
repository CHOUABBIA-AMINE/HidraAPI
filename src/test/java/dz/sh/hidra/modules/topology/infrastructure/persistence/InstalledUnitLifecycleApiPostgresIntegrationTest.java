/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : InstalledUnitLifecycleApiPostgresIntegrationTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-29
 *
 * @Type        : Class
 * @Layer       : Topology Test
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.infrastructure.persistence
 *
 * @Description : Checks the genuine fresh PostgreSQL replacement receipt catalog without claiming native HTTP authorization.
 *
 */
package dz.sh.hidra.modules.topology.infrastructure.persistence;

import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.*;
import java.sql.*;
import static org.junit.jupiter.api.Assertions.*;

/** Physical schema-only proof; authenticated native HTTP qualification remains a separate acceptance gate. */
@Testcontainers(disabledWithoutDocker=true)
class InstalledUnitLifecycleApiPostgresIntegrationTest {
    @Container static final PostgreSQLContainer<?> POSTGRES=new PostgreSQLContainer<>("postgres:16")
            .withCommand("postgres","-c","max_locks_per_transaction=256");
    private static volatile boolean migrated;
    private static synchronized void migrate() {
        if(!migrated) {
            Flyway.configure().dataSource(POSTGRES.getJdbcUrl(),POSTGRES.getUsername(),POSTGRES.getPassword())
                    .load().migrate();
            migrated=true;
        }
    }
    private boolean exists(String query) throws SQLException {
        migrate();
        try(var connection=DriverManager.getConnection(POSTGRES.getJdbcUrl(),
                POSTGRES.getUsername(),POSTGRES.getPassword());
            var statement=connection.createStatement();
            var result=statement.executeQuery(query)) {
            assertTrue(result.next());
            return result.getBoolean(1);
        }
    }
    @Test void freshMigratedReceiptHasExactReplacementColumnAndForeignKey() throws SQLException {
        assertTrue(exists("""
            SELECT EXISTS(
              SELECT 1 FROM pg_catalog.pg_attribute
              WHERE attrelid='public.hidra_topology_installed_unit_operation_request'::regclass
                AND attname='replacement_unit_id' AND NOT attisdropped
                AND atttypid='varchar'::regtype AND atttypmod=84 AND NOT attnotnull)
            """));
        assertTrue(exists("""
            SELECT EXISTS(
              SELECT 1 FROM pg_catalog.pg_constraint
              WHERE conrelid='public.hidra_topology_installed_unit_operation_request'::regclass
                AND conname='fk_installed_request_replacement_unit_company'
                AND contype='f' AND convalidated AND NOT condeferrable)
            """));
    }
    @Test void databaseDoesNotAllowGenericReplacementRequestShape() throws SQLException {
        assertTrue(exists("""
            SELECT EXISTS(
              SELECT 1 FROM pg_catalog.pg_constraint
              WHERE conrelid='public.hidra_topology_installed_unit_operation_request'::regclass
                AND conname='ck_installed_request_replacement_unit'
                AND contype='c' AND convalidated)
            """));
        assertTrue(exists("""
            SELECT EXISTS(
              SELECT 1 FROM pg_catalog.pg_constraint
              WHERE conrelid='public.hidra_topology_installed_unit_operation_request'::regclass
                AND conname='ck_installed_request_operation'
                AND pg_catalog.pg_get_constraintdef(oid) LIKE '%REPLACE%')
            """));
    }
    @Test void retainedInstalledUnitReceiptHasImmutableHistoryTrigger() throws SQLException {
        assertTrue(exists("""
            SELECT EXISTS(
              SELECT 1 FROM pg_catalog.pg_trigger
              WHERE tgrelid='public.hidra_topology_installed_unit_operation_request'::regclass
                AND NOT tgisinternal AND tgenabled<>'D'
                AND (tgtype & 16)<>0)
            """));
    }
}
