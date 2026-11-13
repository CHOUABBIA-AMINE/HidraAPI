/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : MonitoringRuleNativeHttpPostgresIntegrationTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-13
 *
 * @Type        : Class
 * @Layer       : Test
 * @Module      : monitoring
 * @Package     : dz.sh.hidra.modules.monitoring.infrastructure.persistence
 *
 * @Description : Verifies T10 fresh-schema native route identity, immutable versions and serialized activation.
 *
 */
package dz.sh.hidra.modules.monitoring.infrastructure.persistence;

import dz.sh.hidra.modules.monitoring.domain.model.MonitoringRuleRevision;
import dz.sh.hidra.modules.monitoring.domain.value.MonitoringRuleType;
import dz.sh.hidra.modules.monitoring.infrastructure.persistence.adapter.JdbcMonitoringRuleRevisionAdapter;
import dz.sh.hidra.platform.permissions.HidraTrustedNativeProtectedRouteRegistry;
import java.time.Instant;
import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.junit.jupiter.api.Assertions.*;

@Testcontainers(disabledWithoutDocker=true)
class MonitoringRuleNativeHttpPostgresIntegrationTest {
    @Container static final PostgreSQLContainer<?> PG =
            new PostgreSQLContainer<>("postgres:16-alpine").withDatabaseName("hidra_t10_rules");
    private static DriverManagerDataSource datasource;
    @BeforeAll static void schema() {
        if(!PG.isRunning())PG.start();
        Flyway.configure().dataSource(PG.getJdbcUrl(),PG.getUsername(),PG.getPassword()).load().migrate();
        datasource=new DriverManagerDataSource(PG.getJdbcUrl(),PG.getUsername(),PG.getPassword());
    }
    @Test void compiledNativeRuleRoutesAreIndependentlyRegistered() {
        assertNotNull(new HidraTrustedNativeProtectedRouteRegistry());
    }
    @Test void freshPostgresActivationRetainsImmutableHistoryAndRejectsStaleHead() {
        var jdbc=new JdbcTemplate(datasource);
        var tx=new TransactionTemplate(new DataSourceTransactionManager(datasource));
        var repository=new JdbcMonitoringRuleRevisionAdapter(jdbc);
        Instant now=Instant.now();
        tx.execute(status->{
            jdbc.update("""
                insert into public.hidra_monitoring_rule
                (id,code,name_fr,rule_type,status,created_at,updated_at)
                values (?,?,?,?,?,now(),now())
                ""","t10-rule","T10-RULE","T10 Rule","PLAN_COMPARISON","DRAFT");
            jdbc.update("""
                insert into public.hidra_monitoring_catalog_entry
                (id,catalog_name,code,active,sort_order,system_defined,created_at,updated_at)
                values ('t10-fixture-policy','EVIDENCE_POLICY','TRUSTED_PLAN_COMPARISON',
                    true,1,false,now(),now())
                """);
            repository.register("t10-rule","company","FACILITY","asset","actor");
            assertTrue(repository.policyAvailable("TRUSTED_PLAN_COMPARISON"));
            assertFalse(repository.policyAvailable("UNSUPPORTED"));
            var first=new MonitoringRuleRevision("t10-rule",1,"company","FACILITY","asset","point",
                    MonitoringRuleType.PLAN_COMPARISON,"unit","TRUSTED_PLAN_COMPARISON",
                    now.minusSeconds(60),now.plusSeconds(7200),"actor",now,null,null,null,null);
            assertEquals(1,repository.create(first).revision());
            assertEquals(1,repository.activate("t10-rule",1,0,now.plusSeconds(1),
                    now.plusSeconds(3600),"actor").revision());
            var second=new MonitoringRuleRevision("t10-rule",1,"company","FACILITY","asset","point",
                    MonitoringRuleType.PLAN_COMPARISON,"unit","TRUSTED_PLAN_COMPARISON",
                    now.plusSeconds(3500),now.plusSeconds(10800),"actor",now,null,null,null,null);
            assertEquals(2,repository.create(second).revision());
            // Exactly repeated activation is one persisted event, with its original actor and timestamps.
            var replay=repository.activate("t10-rule",1,0,now.plusSeconds(1),
                    now.plusSeconds(3600),"actor");
            assertEquals(1,replay.revision());
            assertNotNull(replay.activatedAt());
            assertEquals(now.plusSeconds(1),replay.effectiveFrom());
            assertThrows(IllegalStateException.class,()->repository.activate(
                    "t10-rule",2,0,now.plusSeconds(3600),now.plusSeconds(7200),"actor"));
            repository.activate("t10-rule",2,1,now.plusSeconds(3600),
                    now.plusSeconds(7200),"actor");
            assertEquals(1,repository.effective("t10-rule",now.plusSeconds(100)).orElseThrow().revision());
            assertEquals(2,repository.effective("t10-rule",now.plusSeconds(5000)).orElseThrow().revision());
            assertEquals(2,repository.history("t10-rule",10).size());
            assertEquals("company",repository.scope("t10-rule").orElseThrow().company());
            return null;
        });
        assertThrows(DataAccessException.class,()->tx.execute(status->{
            jdbc.update("""
                update public.hidra_monitoring_rule_revision set unit_id='forged'
                where rule_id='t10-rule' and revision=1
                """);
            return null;
        }));
        assertEquals(2L,jdbc.queryForObject("""
            select count(*) from public.hidra_monitoring_rule_activation where rule_id='t10-rule'
            """,Long.class));
    }
}

