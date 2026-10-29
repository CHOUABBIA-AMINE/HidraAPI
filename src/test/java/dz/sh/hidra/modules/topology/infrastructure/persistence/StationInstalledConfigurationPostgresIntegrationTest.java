/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : StationInstalledConfigurationPostgresIntegrationTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-29
 *
 * @Type        : Class
 * @Layer       : Test
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.infrastructure.persistence
 *
 * @Description : Verifies real fresh Flyway catalog constraints and immutable station declarations.
 *
 */
package dz.sh.hidra.modules.topology.infrastructure.persistence;

import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.Test;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.*;
import static org.junit.jupiter.api.Assertions.*;

@Testcontainers(disabledWithoutDocker=true)
class StationInstalledConfigurationPostgresIntegrationTest {
    @Container static final PostgreSQLContainer<?> POSTGRES =
            new PostgreSQLContainer<>("postgres:16").withCommand("postgres","-c","max_locks_per_transaction=256");
    private static volatile boolean migrated;
    private static synchronized void migrate() {
        if (!migrated) {
            Flyway.configure().dataSource(POSTGRES.getJdbcUrl(),POSTGRES.getUsername(),POSTGRES.getPassword())
                .load().migrate();
            migrated = true;
        }
    }
    private JdbcTemplate jdbc() {
        migrate();
        var source=new DriverManagerDataSource(POSTGRES.getJdbcUrl(),POSTGRES.getUsername(),POSTGRES.getPassword());
        source.setDriverClassName("org.postgresql.Driver");
        return new JdbcTemplate(source);
    }
    private boolean exists(String sql) {return Boolean.TRUE.equals(jdbc().queryForObject(sql,Boolean.class));}

    @Test void freshFlywayCreatesFourConfigurationRelations() {
        for (String suffix : new String[]{"revision","member","head","request"}) {
            assertTrue(exists("SELECT to_regclass('public.hidra_topology_station_configuration_"+suffix+"') IS NOT NULL"));
        }
    }
    @Test void physicalMemberRequiresOwnedUnitAndInstallationEventWitness() {
        assertTrue(exists("""
            SELECT EXISTS(SELECT 1 FROM pg_constraint
            WHERE conrelid='public.hidra_topology_station_configuration_member'::regclass
              AND conname='fk_station_configuration_member_event'
              AND contype='f' AND convalidated AND NOT condeferrable)
            """));
        assertTrue(exists("""
            SELECT EXISTS(SELECT 1 FROM pg_constraint
            WHERE conrelid='public.hidra_topology_station_configuration_member'::regclass
              AND conname='fk_station_configuration_member_unit_company' AND contype='f')
            """));
        assertTrue(exists("""
            SELECT EXISTS(SELECT 1 FROM pg_constraint
            WHERE conrelid='public.hidra_topology_station_configuration_member'::regclass
              AND conname='ck_station_configuration_member_binding' AND contype='c')
            """));
    }
    @Test void headIsConstrainedToExactRevisionAndSelectedEpoch() {
        assertTrue(exists("""
            SELECT EXISTS(SELECT 1 FROM pg_constraint
            WHERE conrelid='public.hidra_topology_station_configuration_head'::regclass
              AND conname='fk_station_configuration_head_selection' AND contype='f')
            """));
        assertTrue(exists("""
            SELECT EXISTS(SELECT 1 FROM pg_trigger
            WHERE tgrelid='public.hidra_topology_station_configuration_head'::regclass
              AND tgname='trg_station_configuration_head_cas' AND tgenabled<>'D')
            """));
    }
    @Test void immutableRevisionMemberRequestRetainUpdateDeleteAndTruncateFences() {
        for(String suffix:new String[]{"revision","member","request"}) {
            assertTrue(exists("SELECT EXISTS(SELECT 1 FROM pg_trigger WHERE tgrelid="
                +"'public.hidra_topology_station_configuration_"+suffix+"'::regclass"
                +" AND tgname='trg_station_configuration_"+suffix+"_immutable' AND tgenabled<>'D')"));
            assertTrue(exists("SELECT EXISTS(SELECT 1 FROM pg_trigger WHERE tgrelid="
                +"'public.hidra_topology_station_configuration_"+suffix+"'::regclass"
                +" AND tgname='trg_station_configuration_"+suffix+"_no_truncate' AND tgenabled<>'D')"));
        }
    }
    @Test void invalidHeadReferenceFailsUnderRealForeignKey() {
        assertThrows(DataAccessException.class,()->jdbc().update("""
            INSERT INTO public.hidra_topology_station_configuration_head(
             company_party_id,facility_id,revision_id,head_epoch)
            VALUES('synthetic-company','synthetic-facility','missing-revision',1)
            """));
    }
}
