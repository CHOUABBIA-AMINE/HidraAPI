/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : NominationSemanticPostgresIntegrationTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-08
 *
 * @Type        : Class
 * @Layer       : Test
 * @Module      : planning
 * @Package     : dz.sh.hidra.modules.planning.infrastructure.persistence
 *
 * @Description : Executes approved transitions and actual owner-provider transactional saves.
 *
 */
package dz.sh.hidra.modules.planning.infrastructure.persistence;

import java.nio.file.*;
import java.sql.*;
import java.time.Instant;
import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.*;
import dz.sh.hidra.modules.planning.domain.model.Nomination;
import dz.sh.hidra.modules.planning.domain.value.NominationStatus;
import dz.sh.hidra.modules.planning.application.port.out.NominationRepositoryPort;
import dz.sh.hidra.modules.planning.infrastructure.persistence.adapter.*;
import dz.sh.hidra.modules.planning.infrastructure.persistence.repository.*;
import dz.sh.hidra.modules.custody.application.contract.planning.PlanningProductReferenceContract;
import dz.sh.hidra.modules.custody.infrastructure.integration.PlanningProductReferenceQueryAdapter;
import dz.sh.hidra.modules.custody.infrastructure.persistence.repository.CustodyCatalogEntryJpaRepository;
import dz.sh.hidra.modules.telemetry.application.contract.planning.PlanningUnitReferenceContract;
import dz.sh.hidra.modules.telemetry.infrastructure.integration.PlanningUnitReferenceQueryAdapter;
import dz.sh.hidra.modules.telemetry.infrastructure.persistence.repository.TelemetryUnitJpaRepository;
import dz.sh.hidra.modules.party.application.contract.planning.PlanningPartyReferenceContract;
import dz.sh.hidra.modules.party.infrastructure.integration.PlanningPartyReferenceQueryAdapter;
import dz.sh.hidra.modules.party.infrastructure.persistence.repository.PartyJpaRepository;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.*;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.data.jpa.repository.support.JpaRepositoryFactory;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.transaction.interceptor.TransactionInterceptor;
import org.springframework.transaction.annotation.AnnotationTransactionAttributeSource;
import org.springframework.transaction.support.TransactionTemplate;

@Testcontainers(disabledWithoutDocker=true)
class NominationSemanticPostgresIntegrationTest {
    @Container static final PostgreSQLContainer<?> POSTGRES=new PostgreSQLContainer<>("postgres:16-alpine");
    Connection connection() throws SQLException {return DriverManager.getConnection(POSTGRES.getJdbcUrl(),POSTGRES.getUsername(),POSTGRES.getPassword());}
    void sql(String text) throws SQLException {try(var c=connection();var s=c.createStatement()){s.execute(text);}}
    String scalar(String text) throws SQLException {try(var c=connection();var s=c.createStatement();var r=s.executeQuery(text)){r.next();return r.getString(1);}}
    void file(String name) throws Exception {
        try(var c=connection()) {
            c.setAutoCommit(false);
            try(var s=c.createStatement()){s.execute(Files.readString(Path.of("src/main/resources/db/migration",name)));c.commit();}
            catch(Exception e){c.rollback();throw e;}
        }
    }
    @BeforeEach void baseline() throws Exception {
        sql("DROP SCHEMA public CASCADE; CREATE SCHEMA public");
        for(String name:List.of("V20260611_006__create_planning_tables.sql","V20260611_005__create_telemetry_tables.sql",
                "V20260611_015__create_custody_tables.sql","V20260611_003__create_party_tables.sql",
                "V20261008_019__hmr_094_planning_target_value_policy.sql","V20261008_020__hmr_094_planning_plan_target_integrity.sql")) file(name);
        sql("INSERT INTO hidra_planning_plan_revision(id,plan_id,revision_number,revision_code,status,created_at,updated_at) VALUES('rev','plan',1,'R','DRAFT',now(),now()),('other','plan',2,'O','DRAFT',now(),now())");
        sql("INSERT INTO hidra_planning_catalog_entry VALUES('type','NOMINATION_TYPE','TEST',true,0,false,now(),now()),('lp','LEGACY','LP',true,0,false,now(),now()),('lq','LEGACY','LQ',true,0,false,now(),now())");
        sql("ALTER TABLE hidra_planning_nomination ADD CONSTRAINT fk_hra111_planning_009 FOREIGN KEY(nomination_type_id) REFERENCES hidra_planning_catalog_entry(id), ADD CONSTRAINT fk_hra111_planning_010 FOREIGN KEY(product_type_id) REFERENCES hidra_planning_catalog_entry(id), ADD CONSTRAINT fk_hra111_planning_011 FOREIGN KEY(quantity_unit_id) REFERENCES hidra_planning_catalog_entry(id), ADD CONSTRAINT fk_hra111_planning_012 FOREIGN KEY(revision_id) REFERENCES hidra_planning_plan_revision(id)");
        sql("INSERT INTO hidra_custody_catalog_entry VALUES('p','OWNER_FIXTURE','PRODUCT',true,0,false,now(),now())");
        sql("INSERT INTO hidra_telemetry_unit(id,code,symbol,dimension,active,system_defined,created_at,updated_at) VALUES('q','QUANTITY','q','FIXTURE',true,false,now(),now()),('r','RATE','r','FIXTURE',true,false,now(),now())");
        sql("INSERT INTO hidra_party_party(id,code,party_type_id,legal_name,country_code,status,created_at,updated_at) VALUES('party','CANONICAL','fixture','Fixture','DZ','ACTIVE',now(),now())");
        file("V20261008_025__hmr_080_nomination_owner_reference_policies.sql");
    }
    void approvals() throws SQLException {
        sql("INSERT INTO hidra_custody_planning_product_policy VALUES('p',true,'OWNER_APPROVED_TEST_FIXTURE')");
        sql("INSERT INTO hidra_telemetry_planning_unit_role VALUES('q','QUANTITY',true,'OWNER_APPROVED_TEST_FIXTURE'),('r','RATE',true,'OWNER_APPROVED_TEST_FIXTURE')");
        sql("INSERT INTO hidra_telemetry_planning_unit_pair VALUES('q','r',true,'OWNER_APPROVED_TEST_FIXTURE')");
    }
    void migrate() throws Exception {file("V20261008_026__hmr_080_planning_nomination_integrity.sql");}
    void configured() throws Exception {approvals();migrate();}
    void legacy() throws SQLException {
        sql("INSERT INTO hidra_planning_nomination(id,revision_id,code,nomination_type_id,product_type_id,quantity,quantity_unit_id,rate_unit_id,shipper_party_id,shipper_party_code_snapshot,contract_reference_id,status,period_start,period_end,created_at,updated_at) VALUES('legacy','rev','N','type','lp',1,'lq','lr','party','HISTORICAL','neutral','DRAFT',now(),now()+interval '1 hour',now(),now())");
    }
    void mappings() throws SQLException {
        sql("INSERT INTO hidra_planning_nomination_reference_mapping VALUES('legacy','PRODUCT','lp','p','OWNER_APPROVED_TEST_FIXTURE'),('legacy','QUANTITY_UNIT','lq','q','OWNER_APPROVED_TEST_FIXTURE'),('legacy','RATE_UNIT','lr','r','OWNER_APPROVED_TEST_FIXTURE')");
    }
    void oldState() throws SQLException {
        assertEquals("lp:lq:lr",scalar("SELECT product_type_id||':'||quantity_unit_id||':'||rate_unit_id FROM hidra_planning_nomination WHERE id='legacy'"));
        assertEquals("2",scalar("SELECT count(*) FROM pg_constraint WHERE conname IN ('fk_hra111_planning_010','fk_hra111_planning_011')"));
    }
    @Test void emptyMetadataAndEmptyDatabaseInstallWithoutSeededClassifications() throws Exception {
        migrate();
        for(String table:List.of("hidra_custody_planning_product_policy","hidra_telemetry_planning_unit_role","hidra_telemetry_planning_unit_pair","hidra_planning_nomination_reference_mapping")) assertEquals("0",scalar("SELECT count(*) FROM "+table));
    }
    @Test void missingMismatchedUnknownAndAmbiguousMappingsFailBeforeAnyTransition() throws Exception {
        legacy();assertThrows(SQLException.class,this::migrate);oldState();
        mappings();assertThrows(SQLException.class,this::migrate);oldState();approvals();
        sql("UPDATE hidra_planning_nomination_reference_mapping SET legacy_id='wrong' WHERE field_name='PRODUCT'");
        assertThrows(SQLException.class,this::migrate);oldState();
        sql("UPDATE hidra_planning_nomination_reference_mapping SET legacy_id='lp',canonical_owner_id='unknown' WHERE field_name='PRODUCT'");
        assertThrows(SQLException.class,this::migrate);oldState();
        assertThrows(SQLException.class,() -> sql("INSERT INTO hidra_planning_nomination_reference_mapping VALUES('legacy','PRODUCT','lp','p','SECOND_APPROVAL')"));
        sql("UPDATE hidra_planning_nomination_reference_mapping SET canonical_owner_id='p' WHERE field_name='PRODUCT'");migrate();
    }
    @Test void explicitTransitionPreservesEveryOtherColumnAndInactiveHistoricalEvidence() throws Exception {
        legacy();mappings();approvals();
        sql("UPDATE hidra_custody_planning_product_policy SET active=false");sql("UPDATE hidra_telemetry_planning_unit_role SET active=false");sql("UPDATE hidra_telemetry_planning_unit_pair SET active=false");
        String before=scalar("SELECT (to_jsonb(n)-'product_type_id'-'quantity_unit_id'-'rate_unit_id')::text FROM hidra_planning_nomination n");
        migrate();assertEquals("p:q:r",scalar("SELECT product_type_id||':'||quantity_unit_id||':'||rate_unit_id FROM hidra_planning_nomination"));
        assertEquals(before,scalar("SELECT (to_jsonb(n)-'product_type_id'-'quantity_unit_id'-'rate_unit_id')::text FROM hidra_planning_nomination n"));
        assertEquals("3",scalar("SELECT count(*) FROM hidra_planning_nomination_reference_mapping"));
    }
    @Test void incompatiblePairInvalidHistoryAndCrossRevisionScenarioAbortAndPermitReviewedRetry() throws Exception {
        legacy();mappings();approvals();sql("DELETE FROM hidra_telemetry_planning_unit_pair");
        assertThrows(SQLException.class,this::migrate);oldState();
        sql("INSERT INTO hidra_telemetry_planning_unit_pair VALUES('q','r',true,'OWNER_APPROVED_TEST_FIXTURE')");
        for(String invalid:List.of("quantity=0","quantity=-1","period_end=period_start","code=' '","source_asset_type='PIPELINE',source_asset_id=null")) {
            sql("UPDATE hidra_planning_nomination SET "+invalid);assertThrows(SQLException.class,this::migrate);oldState();
            sql("UPDATE hidra_planning_nomination SET quantity=1,period_end=period_start+interval '1 hour',code='N',source_asset_type=null,source_asset_id=null");
        }
        sql("INSERT INTO hidra_planning_plan_scenario(id,revision_id,code,name_fr,scenario_type_id,primary_scenario,status,created_at,updated_at) VALUES('scenario','other','S','Fixture','fixture',false,'DRAFT',now(),now())");
        sql("UPDATE hidra_planning_nomination SET scenario_id='scenario'");assertThrows(SQLException.class,this::migrate);oldState();
        sql("UPDATE hidra_planning_plan_scenario SET revision_id='rev'");migrate();
        assertThrows(SQLException.class,() -> sql("UPDATE hidra_planning_plan_scenario SET revision_id='other'"));
        assertThrows(SQLException.class,() -> sql("DELETE FROM hidra_planning_plan_revision WHERE id='rev'"));
    }
    @Test void approvalHistoryCannotBeDeletedTruncatedOrRemappedAndRegisteredOwnersCannotBeDeleted() throws Exception {
        legacy();mappings();approvals();migrate();
        for(String table:List.of("hidra_custody_planning_product_policy","hidra_telemetry_planning_unit_role","hidra_telemetry_planning_unit_pair","hidra_planning_nomination_reference_mapping")) {
            assertThrows(SQLException.class,() -> sql("DELETE FROM "+table));
            assertThrows(SQLException.class,() -> sql("TRUNCATE "+table+" CASCADE"));
            assertThrows(SQLException.class,() -> sql("UPDATE "+table+" SET approval_reference='rewritten'"));
        }
        assertThrows(SQLException.class,() -> sql("DELETE FROM hidra_custody_catalog_entry"));
        assertThrows(SQLException.class,() -> sql("DELETE FROM hidra_telemetry_unit"));
        assertThrows(SQLException.class,() -> sql("UPDATE hidra_planning_catalog_entry SET catalog_name='OTHER' WHERE id='type'"));
        assertThrows(SQLException.class,() -> sql("TRUNCATE hidra_planning_catalog_entry CASCADE"));
    }
    @Test void duplicateHistoricalCodeAndWrongFamilyAbortWithoutInstallingConstraints() throws Exception {
        legacy();mappings();approvals();
        sql("INSERT INTO hidra_planning_nomination SELECT 'second',revision_id,scenario_id,code,nomination_type_id,product_type_id,quantity,quantity_unit_id,rate,rate_unit_id,source_asset_type,source_asset_id,source_asset_code,destination_asset_type,destination_asset_id,destination_asset_code,shipper_party_id,shipper_party_code_snapshot,counterparty_id,contract_reference_id,priority,status,period_start,period_end,created_at,updated_at FROM hidra_planning_nomination WHERE id='legacy'");
        assertThrows(SQLException.class,this::migrate);oldState();sql("DELETE FROM hidra_planning_nomination WHERE id='second'");
        sql("UPDATE hidra_planning_catalog_entry SET catalog_name='TARGET_TYPE' WHERE id='type'");
        assertThrows(SQLException.class,this::migrate);oldState();
        sql("UPDATE hidra_planning_catalog_entry SET catalog_name='NOMINATION_TYPE' WHERE id='type'");migrate();
    }
    @Test void nullOptionalRateNeedsNoPairAndSameIdentityStillNeedsExplicitMapping() throws Exception {
        legacy();sql("UPDATE hidra_planning_nomination SET rate_unit_id=null");approvals();
        sql("DELETE FROM hidra_telemetry_planning_unit_pair");
        sql("INSERT INTO hidra_planning_catalog_entry VALUES('p','LEGACY','P',true,0,false,now(),now()),('q','LEGACY','Q',true,0,false,now(),now())");
        sql("UPDATE hidra_planning_nomination SET product_type_id='p',quantity_unit_id='q'");
        assertThrows(SQLException.class,this::migrate);
        sql("INSERT INTO hidra_planning_nomination_reference_mapping VALUES('legacy','PRODUCT','p','p','OWNER_APPROVED_TEST_FIXTURE'),('legacy','QUANTITY_UNIT','q','q','OWNER_APPROVED_TEST_FIXTURE')");
        migrate();assertEquals("p:q",scalar("SELECT product_type_id||':'||quantity_unit_id FROM hidra_planning_nomination"));
        assertNull(scalar("SELECT rate_unit_id FROM hidra_planning_nomination"));
    }
    Nomination requested(String id,String code,String revision,String scenario,String party) {
        var at=Instant.parse("2026-10-08T00:00:00Z");
        return new Nomination(id,revision,scenario,code,"type","p",BigDecimal.ONE,"q",null,"r",null,null,null,null,null,null,party,"CALLER",null,"neutral",1,NominationStatus.DRAFT,at,at.plusSeconds(3600),at,at);
    }
    class RuntimeFixture implements AutoCloseable {
        final LocalContainerEntityManagerFactoryBean factory=new LocalContainerEntityManagerFactoryBean();
        final NominationRepositoryPort port;
        final TransactionTemplate transaction;
        final JpaTransactionManager manager;
        RuntimeFixture() {
            var dataSource=new DriverManagerDataSource(POSTGRES.getJdbcUrl(),POSTGRES.getUsername(),POSTGRES.getPassword());
            factory.setDataSource(dataSource);factory.setPackagesToScan("dz.sh.hidra.modules.planning.infrastructure.persistence.entity","dz.sh.hidra.modules.custody.infrastructure.persistence.entity","dz.sh.hidra.modules.telemetry.infrastructure.persistence.entity","dz.sh.hidra.modules.party.infrastructure.persistence.entity");
            factory.setJpaVendorAdapter(new HibernateJpaVendorAdapter());factory.setJpaPropertyMap(Map.of("hibernate.hbm2ddl.auto","none"));factory.afterPropertiesSet();
            var emf=Objects.requireNonNull(factory.getObject());var repos=new JpaRepositoryFactory(SharedEntityManagerCreator.createSharedEntityManager(emf));
            manager=new JpaTransactionManager(emf);manager.setDataSource(dataSource);transaction=new TransactionTemplate(manager);var jdbc=new JdbcTemplate(dataSource);
            var products=(PlanningProductReferenceContract)proxied(new PlanningProductReferenceQueryAdapter(repos.getRepository(CustodyCatalogEntryJpaRepository.class),jdbc));
            var units=(PlanningUnitReferenceContract)proxied(new PlanningUnitReferenceQueryAdapter(repos.getRepository(TelemetryUnitJpaRepository.class),jdbc));
            var parties=(PlanningPartyReferenceContract)proxied(new PlanningPartyReferenceQueryAdapter(repos.getRepository(PartyJpaRepository.class)));
            var validation=new NominationReferenceValidation(repos.getRepository(PlanRevisionJpaRepository.class),repos.getRepository(PlanScenarioJpaRepository.class),repos.getRepository(PlanningCatalogEntryJpaRepository.class),products,units,parties,(type,id) -> Optional.empty());
            port=(NominationRepositoryPort)proxied(new JpaNominationRepositoryAdapter(repos.getRepository(NominationJpaRepository.class),validation));
        }
        Object proxied(Object target) {var proxy=new ProxyFactory(target);proxy.addAdvice(new TransactionInterceptor(manager,new AnnotationTransactionAttributeSource()));return proxy.getProxy();}
        public void close(){factory.destroy();}
    }
    @Test void actualOwnerProvidersSaveCanonicalPartyAndRollbackTheFlushedRecord() throws Exception {
        configured();try(var runtime=new RuntimeFixture()) {
            assertThrows(IllegalStateException.class,() -> runtime.transaction.execute(status -> {runtime.port.save(requested("rollback","R","rev",null,"party"));throw new IllegalStateException("forced after flush");}));
            assertEquals("0",scalar("SELECT count(*) FROM hidra_planning_nomination"));
            assertEquals("CANONICAL",runtime.port.save(requested("saved","N","rev",null,"party")).shipperPartyCodeSnapshot());
            runtime.port.save(requested("other","N","other",null,null));
            assertThrows(RuntimeException.class,() -> runtime.port.save(requested("duplicate","N","rev",null,null)));
            assertThrows(RuntimeException.class,() -> runtime.port.save(requested("unknown-party","P","rev",null,"unknown")));
            assertEquals("2",scalar("SELECT count(*) FROM hidra_planning_nomination"));
        }
    }
    @Test void inactiveUnchangedHistoryAndSnapshotsReplayButFreshReferencesFailClosed() throws Exception {
        configured();try(var runtime=new RuntimeFixture()) {
            runtime.port.save(requested("saved","N","rev",null,"party"));
            sql("UPDATE hidra_custody_planning_product_policy SET active=false");sql("UPDATE hidra_telemetry_planning_unit_role SET active=false");
            sql("UPDATE hidra_telemetry_planning_unit_pair SET active=false");sql("UPDATE hidra_planning_catalog_entry SET active=false WHERE id='type'");
            sql("UPDATE hidra_party_party SET code='CHANGED'");
            assertEquals("CANONICAL",runtime.port.save(requested("saved","N","rev",null,"party")).shipperPartyCodeSnapshot());
            assertThrows(RuntimeException.class,() -> runtime.port.save(requested("fresh","F","rev",null,null)));
            assertEquals("1",scalar("SELECT count(*) FROM hidra_planning_nomination"));
        }
    }
    @Test void duplicateCodeRaceHasExactlyOneWinner() throws Exception {
        configured();try(var runtime=new RuntimeFixture()) {
            var pool=Executors.newFixedThreadPool(2);var ready=new CountDownLatch(2);var start=new CountDownLatch(1);
            try {
                var futures=new ArrayList<Future<Boolean>>();
                for(String id:List.of("first","second")) futures.add(pool.submit(() -> {ready.countDown();start.await();try {runtime.port.save(requested(id,"N","rev",null,null));return true;}catch(RuntimeException denied){return false;}}));
                assertTrue(ready.await(5,TimeUnit.SECONDS));start.countDown();
                int winners=0;for(var future:futures)if(future.get(15,TimeUnit.SECONDS))winners++;
                assertEquals(1,winners);assertEquals("1",scalar("SELECT count(*) FROM hidra_planning_nomination"));
            } finally {start.countDown();pool.shutdownNow();}
        }
    }
    @Test void committedEligibilityAndParentMutationsWinAgainstFreshWrites() throws Exception {
        configured();sql("INSERT INTO hidra_planning_plan_scenario(id,revision_id,code,name_fr,scenario_type_id,primary_scenario,status,created_at,updated_at) VALUES('scenario','rev','S','Fixture','fixture',false,'DRAFT',now(),now())");
        for(String change:List.of("UPDATE hidra_custody_planning_product_policy SET active=false","UPDATE hidra_telemetry_planning_unit_role SET active=false WHERE usage_role='QUANTITY'","UPDATE hidra_telemetry_planning_unit_pair SET active=false","UPDATE hidra_planning_catalog_entry SET active=false WHERE id='type'","UPDATE hidra_planning_catalog_entry SET catalog_name='OTHER' WHERE id='type'","UPDATE hidra_planning_plan_scenario SET revision_id='other'","DELETE FROM hidra_party_party WHERE id='party'")) {
            try(var runtime=new RuntimeFixture();var changing=connection()) {
                var pool=Executors.newSingleThreadExecutor();var attempting=new CountDownLatch(1);
                try {
                    changing.setAutoCommit(false);try(var s=changing.createStatement()){s.execute(change);}
                    var writing=pool.submit(() -> {attempting.countDown();try{runtime.port.save(requested("late","L","rev","scenario","party"));return false;}catch(RuntimeException denied){return true;}});
                    assertTrue(attempting.await(5,TimeUnit.SECONDS));assertThrows(TimeoutException.class,() -> writing.get(150,TimeUnit.MILLISECONDS));
                    changing.commit();assertTrue(writing.get(15,TimeUnit.SECONDS));assertEquals("0",scalar("SELECT count(*) FROM hidra_planning_nomination"));
                } finally {pool.shutdownNow();}
            }
            sql("UPDATE hidra_custody_planning_product_policy SET active=true");sql("UPDATE hidra_telemetry_planning_unit_role SET active=true");sql("UPDATE hidra_telemetry_planning_unit_pair SET active=true");sql("UPDATE hidra_planning_catalog_entry SET active=true,catalog_name='NOMINATION_TYPE' WHERE id='type'");sql("UPDATE hidra_planning_plan_scenario SET revision_id='rev'");
        }
    }
    @Test void freshSaveLocksOwnersAndFamilyUntilCommitAndKeepsDependentTargetIntegrity() throws Exception {
        configured();try(var runtime=new RuntimeFixture()) {
            var pool=Executors.newSingleThreadExecutor();
            try {
                var changed=runtime.transaction.execute(status -> {
                    runtime.port.save(requested("saved","N","rev",null,"party"));var attempting=new CountDownLatch(1);
                    var change=pool.submit(() -> {attempting.countDown();try(var c=connection();var s=c.createStatement()){s.execute("UPDATE hidra_planning_catalog_entry SET catalog_name='OTHER' WHERE id='type'");return false;}catch(SQLException denied){return true;}});
                    try {assertTrue(attempting.await(5,TimeUnit.SECONDS));assertThrows(TimeoutException.class,() -> change.get(150,TimeUnit.MILLISECONDS));}
                    catch(InterruptedException e){Thread.currentThread().interrupt();throw new IllegalStateException(e);}
                    return change;
                });
                assertTrue(changed.get(15,TimeUnit.SECONDS));
                assertEquals("NOMINATION_TYPE",scalar("SELECT catalog_name FROM hidra_planning_catalog_entry WHERE id='type'"));
                sql("INSERT INTO hidra_planning_catalog_entry VALUES('target-type','TARGET_TYPE','TARGET',true,0,false,now(),now())");
                sql("INSERT INTO hidra_planning_target_value_policy VALUES('target-type','NUMERIC',true)");
                sql("INSERT INTO hidra_planning_plan_target(id,revision_id,nomination_id,target_type_id,topology_asset_type,topology_asset_id,target_value,unit_id,status,valid_from,valid_to,created_at,updated_at) VALUES('target','rev','saved','target-type','PIPELINE','fixture',1,'q','DRAFT',now(),now(),now(),now())");
                assertThrows(SQLException.class,() -> sql("UPDATE hidra_planning_nomination SET revision_id='other' WHERE id='saved'"));
                assertThrows(SQLException.class,() -> sql("DELETE FROM hidra_planning_nomination WHERE id='saved'"));
            } finally {pool.shutdownNow();}
        }
    }
    @Test void committedRevisionDeletionRejectsTheWaitingFreshSave() throws Exception {
        configured();try(var runtime=new RuntimeFixture();var deleting=connection()) {
            var pool=Executors.newSingleThreadExecutor();var attempting=new CountDownLatch(1);
            try {
                deleting.setAutoCommit(false);try(var s=deleting.createStatement()){s.execute("DELETE FROM hidra_planning_plan_revision WHERE id='rev'");}
                var save=pool.submit(() -> {attempting.countDown();try {runtime.port.save(requested("late","N","rev",null,null));return false;}catch(RuntimeException denied){return true;}});
                assertTrue(attempting.await(5,TimeUnit.SECONDS));assertThrows(TimeoutException.class,() -> save.get(150,TimeUnit.MILLISECONDS));
                deleting.commit();assertTrue(save.get(15,TimeUnit.SECONDS));assertEquals("0",scalar("SELECT count(*) FROM hidra_planning_nomination"));
            } finally {pool.shutdownNow();}
        }
    }
}
