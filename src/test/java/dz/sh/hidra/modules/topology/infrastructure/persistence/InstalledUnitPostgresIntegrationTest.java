/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : InstalledUnitPostgresIntegrationTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-28
 *
 * @Type        : Class
 * @Layer       : Infrastructure Test
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.infrastructure.persistence
 *
 * @Description : Verifies installed-unit identity, immutable history and native PostgreSQL overlap fences.
 *
 */
package dz.sh.hidra.modules.topology.infrastructure.persistence;

import dz.sh.hidra.modules.topology.application.port.out.InstalledUnitRepositoryPort;
import dz.sh.hidra.modules.topology.domain.exception.InvalidTopologyValueException;
import dz.sh.hidra.modules.topology.domain.model.InstalledUnit;
import dz.sh.hidra.modules.topology.domain.model.InstalledUnit.Evidence;
import dz.sh.hidra.modules.topology.domain.model.InstalledUnit.Verification;
import dz.sh.hidra.modules.topology.domain.model.InstalledUnitInstallation;
import dz.sh.hidra.modules.topology.infrastructure.persistence.adapter.JdbcInstalledUnitRepositoryAdapter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Instant;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.transaction.support.TransactionTemplate;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import static org.junit.jupiter.api.Assertions.*;

@Testcontainers(disabledWithoutDocker = true)
class InstalledUnitPostgresIntegrationTest {
    @Container
    static final PostgreSQLContainer<?> POSTGRES = new PostgreSQLContainer<>("postgres:16")
            .withDatabaseName("hidra_test");
    private static final Path MIGRATION = Path.of(
            "src/main/resources/db/migration/V20261028_002__topology_installed_unit_identity_and_history.sql");
    private static final Instant T = Instant.parse("2026-01-01T00:00:00Z");
    private DriverManagerDataSource source;
    private JdbcTemplate jdbc;
    private InstalledUnitRepositoryPort repo;

    @BeforeEach
    void reset() throws Exception {
        source = new DriverManagerDataSource(POSTGRES.getJdbcUrl(),
                POSTGRES.getUsername(), POSTGRES.getPassword());
        source.setDriverClassName("org.postgresql.Driver");
        jdbc = new JdbcTemplate(source);
        jdbc.execute("DROP SCHEMA public CASCADE; CREATE SCHEMA public");
        jdbc.execute("CREATE TABLE hidra_topology_equipment (id varchar(80) PRIMARY KEY)");
        jdbc.update("INSERT INTO hidra_topology_equipment (id) VALUES ('position-1'),('position-2')");
        jdbc.execute(Files.readString(MIGRATION));
        repo = new JdbcInstalledUnitRepositoryAdapter(jdbc);
    }

    private InstalledUnit unit(String id,String serial) {
        return new InstalledUnit(id,"synthetic-company","compressor",
                new Evidence("synthetic-manufacturer",Verification.UNVERIFIED,"synthetic-evidence"),
                Evidence.unknown(),
                new Evidence(serial,Verification.UNVERIFIED,"synthetic-evidence"),null);
    }
    private InstalledUnitInstallation period(String id,String unit,String position,long start,Long until) {
        return new InstalledUnitInstallation(id,unit,position,"synthetic-company",
                T.plusSeconds(start),until==null?null:T.plusSeconds(until),
                T,"synthetic-installation-evidence",1);
    }

    @Test
    void separatePhysicalIdentitiesReplayWithoutSerialMerging() {
        var a=unit("u1","serial-repeated");
        var b=unit("u2","serial-repeated");
        assertEquals(a,repo.register(a));
        assertEquals(a,repo.register(a));
        assertEquals(b,repo.register(b));
        assertNotEquals(a.id(),b.id());
        assertEquals(a.serialNumber(),b.serialNumber());
        assertEquals(a,repo.findUnit("synthetic-company","u1").orElseThrow());
        assertTrue(repo.findUnit("other-company","u1").isEmpty());
        assertThrows(InvalidTopologyValueException.class,()->
                repo.register(new InstalledUnit("u1","synthetic-company","valve",
                        a.manufacturerParty(),a.manufacturerModel(),a.serialNumber(),null)));
    }

    @Test
    void appendsAndClosesHistoryWithoutChangingEarlierRevision() {
        repo.register(unit("u1","s1"));repo.register(unit("u2","s2"));
        var first=period("install-1","u1","position-1",0,null);
        assertEquals(first,repo.record(first));
        assertEquals(first,repo.record(first));
        assertEquals(List.of(first),repo.history("synthetic-company","install-1"));
        var closed=repo.close("synthetic-company","install-1",1,T.plusSeconds(100));
        assertEquals(2,closed.revision());
        assertEquals(T.plusSeconds(100),closed.effectiveUntil());
        assertEquals(List.of(first,closed),repo.history("synthetic-company","install-1"));
        assertThrows(InvalidTopologyValueException.class,()->
                repo.close("synthetic-company","install-1",1,T.plusSeconds(110)));
        var next=period("install-2","u2","position-1",100,null);
        assertEquals(next,repo.record(next));
        assertEquals(2,jdbc.queryForObject(
                "SELECT count(*) FROM hidra_topology_installed_unit_installation",Integer.class));
    }

    @Test
    void nativeExclusionRejectsSimultaneousUnitAndPositionOccupancy() {
        repo.register(unit("u1","s1"));repo.register(unit("u2","s2"));
        repo.record(period("i1","u1","position-1",0,null));
        assertThrows(DataAccessException.class,()->
                repo.record(period("i2","u1","position-2",1,null)));
        assertThrows(DataAccessException.class,()->
                repo.record(period("i3","u2","position-1",1,null)));
        assertEquals(1,jdbc.queryForObject(
                "SELECT count(*) FROM hidra_topology_installed_unit_installation",Integer.class));
    }

    @Test
    void failedReplacementRollsBackClosureAndEvent() {
        repo.register(unit("u1","s1"));repo.register(unit("u2","s2"));repo.register(unit("u3","s3"));
        var first=period("i1","u1","position-1",0,null);
        repo.record(first);
        repo.record(period("i2","u2","position-2",0,null));
        var transaction=new TransactionTemplate(new DataSourceTransactionManager(source));
        assertThrows(DataAccessException.class,()->transaction.execute(status->{
            repo.close("synthetic-company","i1",1,T.plusSeconds(100));
            repo.record(period("i3","u3","position-2",100,null));
            return null;
        }));
        assertEquals(first,repo.findInstallation("synthetic-company","i1").orElseThrow());
        assertEquals(List.of(first),repo.history("synthetic-company","i1"));
    }

    @Test
    void concurrentCompetingInstallationsHaveOneWinner() throws Exception {
        repo.register(unit("u1","s1"));repo.register(unit("u2","s2"));
        var ready=new CountDownLatch(2);var go=new CountDownLatch(1);
        var wins=new AtomicInteger();
        try(var pool=Executors.newFixedThreadPool(2)){
            var tasks=List.of(
                    period("i1","u1","position-1",0,null),
                    period("i2","u2","position-1",0,null));
            var futures=tasks.stream().map(row->pool.submit(()->{
                ready.countDown();
                if(!go.await(15,TimeUnit.SECONDS))throw new AssertionError("Barrier deadline");
                try{repo.record(row);wins.incrementAndGet();}
                catch(DataAccessException expected){/* native exclusion */ }
                return null;
            })).toList();
            assertTrue(ready.await(15,TimeUnit.SECONDS));
            go.countDown();
            for(var future:futures)future.get(20,TimeUnit.SECONDS);
        }
        assertEquals(1,wins.get());
        assertEquals(1,jdbc.queryForObject(
                "SELECT count(*) FROM hidra_topology_installed_unit_installation",Integer.class));
    }

    @Test
    void nativeImmutableEvidenceRejectsDirectTampering() {
        repo.register(unit("u1","s1"));
        repo.record(period("i1","u1","position-1",0,null));
        assertThrows(DataAccessException.class,()->
                jdbc.update("UPDATE hidra_topology_installed_unit SET equipment_kind_id='valve' WHERE id='u1'"));
        assertThrows(DataAccessException.class,()->
                jdbc.update("DELETE FROM hidra_topology_installed_unit_installation_event WHERE installation_id='i1'"));
        assertThrows(DataAccessException.class,()->
                jdbc.update("UPDATE hidra_topology_installed_unit_installation SET unit_id='tampered' WHERE installation_id='i1'"));
    }

    @Test
    void fullFreshFlywayChainIncludesTheNewTables() {
        jdbc.execute("DROP SCHEMA public CASCADE; CREATE SCHEMA public");
        var result=Flyway.configure().dataSource(source)
                .locations("classpath:db/migration").load().migrate();
        assertTrue(result.migrationsExecuted>0);
        assertEquals(1,jdbc.queryForObject("""
                SELECT count(*) FROM information_schema.tables
                WHERE table_schema='public' AND table_name='hidra_topology_installed_unit_installation_event'
                """,Integer.class));
        assertEquals(1,jdbc.queryForObject("""
                SELECT count(*) FROM flyway_schema_history
                WHERE version='20261028.002' AND success=true
                """,Integer.class));
    }
}
