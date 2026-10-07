/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AuditSemanticPostgresIntegrationTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-07
 *
 * @Type        : Class
 * @Layer       : Test
 * @Module      : audit
 * @Package     : dz.sh.hidra.modules.audit.infrastructure.persistence
 *
 * @Description : Enforces Audit evidence integrity and explicit owner boundaries.
 *
 */
package dz.sh.hidra.modules.audit.infrastructure.persistence;
import dz.sh.hidra.modules.audit.application.service.*;
import dz.sh.hidra.modules.audit.application.command.*;
import dz.sh.hidra.modules.audit.application.port.out.*;
import dz.sh.hidra.modules.audit.domain.model.*;
import dz.sh.hidra.modules.audit.domain.value.*;
import dz.sh.hidra.modules.audit.infrastructure.persistence.adapter.*;
import dz.sh.hidra.modules.audit.infrastructure.persistence.entity.*;
import dz.sh.hidra.modules.audit.infrastructure.persistence.repository.*;
import org.hibernate.cfg.Configuration;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.*;
import org.springframework.transaction.support.TransactionTemplate;
import java.nio.file.*;
import java.sql.*;
import java.time.Instant;
import java.util.concurrent.*;
@Testcontainers(disabledWithoutDocker=true)
class AuditSemanticPostgresIntegrationTest {
    @Container static final PostgreSQLContainer<?> POSTGRES=new PostgreSQLContainer<>("postgres:16-alpine");
    static final Path MIGRATIONS=Path.of("src/main/resources/db/migration");
    Connection connection()throws SQLException{return DriverManager.getConnection(POSTGRES.getJdbcUrl(),POSTGRES.getUsername(),POSTGRES.getPassword());}
    void sql(String value)throws SQLException{try(var c=connection();var s=c.createStatement()){s.execute(value);}}
    void emptySchema()throws Exception{
        sql("DROP SCHEMA public CASCADE; CREATE SCHEMA public");
        sql(Files.readString(MIGRATIONS.resolve("V20260611_017__create_audit_tables.sql")));
    }
    void migrate()throws Exception{
        for(String name:new String[]{"V20261007_006__hmr_083_audit_export_request.sql","V20261007_007__hmr_095_audit_event.sql"}) sql(Files.readString(MIGRATIONS.resolve(name)));
    }
    void catalog(String id,String family)throws SQLException{sql("INSERT INTO hidra_audit_catalog_entry(id,catalog_name,code,active,sort_order,system_defined,created_at,updated_at) VALUES ('"+id+"','"+family+"','"+id+"',true,0,false,now(),now())");}
    @BeforeEach void setup()throws Exception{emptySchema();migrate();catalog("purpose","EXPORT_PURPOSE");catalog("type","EVENT_TYPE");catalog("category","EVENT_CATEGORY");catalog("severity","SEVERITY");catalog("reason","DECISION_REASON");catalog("mask","MASK_REASON");}
    String export(String id,String purpose,String filter){return "INSERT INTO hidra_audit_export_request(id,requested_by_actor_id,purpose_id,filter_json,format,status,requested_at) VALUES ('"+id+"','actor','"+purpose+"','"+filter+"','JSON','REQUESTED',now())";}
    String access(String id,String event,String export){return "INSERT INTO hidra_audit_access_record(id,actor_id,access_type,audit_event_id,export_request_id,accessed_at) VALUES ('"+id+"','actor','EXPORT',"+(event==null?"null":"'"+event+"'")+","+(export==null?"null":"'"+export+"'")+",now())";}
    @Test void exportPurposeFormatJsonBudgetDepthAndLifecycleAreProtected()throws Exception{
        sql(export("export","purpose","{}"));
        assertThrows(SQLException.class,()->sql(export("wrong","type","{}")));
        sql("UPDATE hidra_audit_catalog_entry SET active=false WHERE id='purpose'");
        assertThrows(SQLException.class,()->sql(export("inactive","purpose","{}")));
        sql("UPDATE hidra_audit_catalog_entry SET active=true WHERE id='purpose'");
        assertThrows(SQLException.class,()->sql(export("huge","purpose","[\""+"é".repeat(32767)+"\"]")));
        assertThrows(SQLException.class,()->sql(export("deep","purpose","[".repeat(33)+"0"+"]".repeat(33))));
        assertThrows(SQLException.class,()->sql(export("scalar","purpose","null")));
        assertThrows(SQLException.class,()->sql(export("format","purpose","{}").replace("'JSON'","' '")));
        assertThrows(SQLException.class,()->sql(export("approved","purpose","{}").replace("'REQUESTED'","'APPROVED'")));
    }
    @Test void legacyWrongPurposeAbortsWithoutRetagging()throws Exception{
        emptySchema();catalog("type","EVENT_TYPE");sql(export("legacy","type","{}"));
        assertThrows(SQLException.class,()->migrate());
        try(var c=connection();var s=c.createStatement();var r=s.executeQuery("SELECT purpose_id FROM hidra_audit_export_request WHERE id='legacy'")){assertTrue(r.next());assertEquals("type",r.getString(1));}
    }
    Configuration configuration(Class<?>... entities){
        var cfg=new Configuration().setProperty("hibernate.connection.url",POSTGRES.getJdbcUrl())
                .setProperty("hibernate.connection.username",POSTGRES.getUsername())
                .setProperty("hibernate.connection.password",POSTGRES.getPassword())
                .setProperty("hibernate.hbm2ddl.auto","none");
        for(var entity:entities)cfg.addAnnotatedClass(entity);return cfg;
    }
    @Test void actualJpaExportPersistsJsonAndDuplicateIdCannotMerge()throws Exception{
        try(var factory=configuration(AuditExportRequestJpaEntity.class).buildSessionFactory()){
            try(var session=factory.openSession()){
                var tx=session.beginTransaction();
                var adapter=new JpaAuditExportRequestRepositoryAdapter(mock(AuditExportRequestJpaRepository.class),session,(id,family)->{},id->true,id->true,new AuditInputPolicy());
                var r=new AuditExportRequest("jpa","actor",null,"purpose","{\"token\":\"raw\"}","JSON",AuditExportStatus.REQUESTED,null,null,null,null,Instant.now(),null,null);
                assertFalse(adapter.save(r).filterJson().contains("raw"));tx.commit();
            }
            try(var session=factory.openSession()){
                var tx=session.beginTransaction();
                var adapter=new JpaAuditExportRequestRepositoryAdapter(mock(AuditExportRequestJpaRepository.class),session,(id,family)->{},id->true,id->true,new AuditInputPolicy());
                assertThrows(RuntimeException.class,()->adapter.save(new AuditExportRequest("jpa","actor",null,"purpose","{}","JSON",AuditExportStatus.REQUESTED,null,null,null,null,Instant.now(),null,null)));
                tx.rollback();
            }
        }
        try(var c=connection();var s=c.createStatement();var r=s.executeQuery("SELECT filter_json::text FROM hidra_audit_export_request WHERE id='jpa'")){assertTrue(r.next());assertTrue(r.getString(1).contains("[REDACTED]"));}
    }
    @Test void actualSpringTransactionRollsBackRequestWhenAccessEvidenceFails(){
        var ds=new DriverManagerDataSource(POSTGRES.getJdbcUrl(),POSTGRES.getUsername(),POSTGRES.getPassword());
        var jdbc=new JdbcTemplate(ds);var template=new TransactionTemplate(new DataSourceTransactionManager(ds));
        var requests=mock(AuditExportRequestRepositoryPort.class);var access=mock(AuditAccessRecordRepositoryPort.class);
        when(requests.save(any())).thenAnswer(i->{AuditExportRequest r=i.getArgument(0);jdbc.update(export(r.id(),r.purposeId(),r.filterJson()));return r;});
        when(access.save(any())).thenAnswer(i->{AuditAccessRecord r=i.getArgument(0);jdbc.update(access(r.id(),null,r.exportRequestId()));throw new IllegalStateException("simulated evidence failure");});
        var service=new AuditApplicationService(mock(AuditEventRepositoryPort.class),requests,access,new AuditInputPolicy());
        assertThrows(IllegalStateException.class,()->template.execute(status->service.requestAuditExport(new RequestAuditExportCommand("actor",null,"purpose","{}","JSON",null))));
        assertEquals(Long.valueOf(0),jdbc.queryForObject("SELECT count(*) FROM hidra_audit_export_request",Long.class));
        assertEquals(Long.valueOf(0),jdbc.queryForObject("SELECT count(*) FROM hidra_audit_access_record",Long.class));
    }
    static AuditEvent event(String id,String source,String target,String targetType,String severity,String reason,String reasonText,String payload) {
        return new AuditEvent(id,
                "type",
                "category",
                severity,
                source,
                null,
                null,
                "OBSERVED",
                null,
                AuditEventStatus.RECORDED,
                null,
                AuditActorType.SYSTEM,
                null,
                null,
                null,
                null,
                null,
                null,
                target,
                targetType,
                "asset",
                null,
                null,
                AuditOperation.READ,
                null,
                reason,
                reasonText,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                Instant.now(),
                Instant.now(),
                null,
                null,
                null,
                payload);
    }

    String eventSql(String id){return "INSERT INTO hidra_audit_event(id,event_type_id,event_category_id,source_module,action_code,event_status,actor_type,target_module,target_type,target_id,operation,occurred_at,recorded_at) VALUES ('"+id+"','type','category','audit','OBSERVED','RECORDED','SYSTEM','topology','ASSET','asset','READ',now(),now())";}
    @Test void eventRequiredFamiliesOptionalCatalogsAndPayloadAreProtected()throws Exception{
        assertThrows(SQLException.class,()->sql(eventSql("blank").replace("'audit'","' '")));
        assertThrows(SQLException.class,()->sql(eventSql("wrong").replace("'type','category'","'category','type'")));
        assertThrows(SQLException.class,()->sql(eventSql("severity").replace("operation,occurred_at","operation,severity_id,occurred_at").replace("'READ',now()","'READ','reason',now()")));
        assertThrows(SQLException.class,()->sql(eventSql("reason").replace("operation,occurred_at","operation,reason_id,occurred_at").replace("'READ',now()","'READ','severity',now()")));
        assertThrows(SQLException.class,()->sql(eventSql("large").replace("operation,occurred_at","operation,payload_json,occurred_at").replace("'READ',now()","'READ','[\""+"x".repeat(65533)+"\"]',now()")));
        sql("UPDATE hidra_audit_catalog_entry SET active=false WHERE id='type'");
        assertThrows(SQLException.class,()->sql(eventSql("inactive")));
    }
    @Test void eventCannotBeUpdatedDeletedOrUpserted()throws Exception{
        sql(eventSql("event"));
        assertThrows(SQLException.class,()->sql("UPDATE hidra_audit_event SET event_status='SEALED' WHERE id='event'"));
        assertThrows(SQLException.class,()->sql("DELETE FROM hidra_audit_event WHERE id='event'"));
        assertThrows(SQLException.class,()->sql(eventSql("event")+" ON CONFLICT(id) DO UPDATE SET target_id='other'"));
    }
    int insertAfter(CountDownLatch go,String query)throws Exception{go.await(10,TimeUnit.SECONDS);try{sql(query);return 1;}catch(SQLException e){assertEquals("23505",e.getSQLState());return 0;}}
    @Test void concurrentEventIdsHaveOneWinner()throws Exception{
        var pool=Executors.newFixedThreadPool(2);var go=new CountDownLatch(1);
        try{var a=pool.submit(()->insertAfter(go,eventSql("same")));var b=pool.submit(()->insertAfter(go,eventSql("same")));go.countDown();assertEquals(1,a.get(20,TimeUnit.SECONDS)+b.get(20,TimeUnit.SECONDS));}finally{pool.shutdownNow();}
    }
    @Test void actualJpaEventSanitizesAndDuplicateCannotReplaceEvidence()throws Exception{
        try(var factory=configuration(AuditEventJpaEntity.class).buildSessionFactory()){
            try(var session=factory.openSession()){
                var tx=session.beginTransaction();
                var adapter=new JpaAuditEventRepositoryAdapter(mock(AuditEventJpaRepository.class),session,(id,family)->{},new AuditInputPolicy());
                adapter.save(event("jpa-event","audit","topology","ASSET",null,null,"Routine","{\"secret\":\"raw\"}"));tx.commit();
            }
            try(var session=factory.openSession()){
                var tx=session.beginTransaction();
                var adapter=new JpaAuditEventRepositoryAdapter(mock(AuditEventJpaRepository.class),session,(id,family)->{},new AuditInputPolicy());
                assertThrows(RuntimeException.class,()->adapter.save(event("jpa-event","audit","topology","ASSET",null,null,"Changed",null)));tx.rollback();
            }
        }
        try(var c=connection();var s=c.createStatement();var r=s.executeQuery("SELECT reason_text,payload_json::text FROM hidra_audit_event WHERE id='jpa-event'")){assertTrue(r.next());assertEquals("Routine",r.getString(1));assertTrue(r.getString(2).contains("[REDACTED]"));}
    }
}
