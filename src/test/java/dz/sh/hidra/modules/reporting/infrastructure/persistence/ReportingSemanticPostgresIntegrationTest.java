/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ReportingSemanticPostgresIntegrationTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-08
 *
 * @Type        : Class
 * @Layer       : Test
 * @Module      : reporting
 * @Package     : dz.sh.hidra.modules.reporting.infrastructure.persistence
 *
 * @Description : Enforces Reporting execution and output integrity through explicit owner boundaries.
 *
 */
package dz.sh.hidra.modules.reporting.infrastructure.persistence;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.*;
import java.nio.file.*;
import java.sql.*;
import java.util.concurrent.*;
@Testcontainers(disabledWithoutDocker=true)
class ReportingSemanticPostgresIntegrationTest {
    @Container static final PostgreSQLContainer<?> POSTGRES=new PostgreSQLContainer<>("postgres:16-alpine");
    static final Path MIGRATIONS=Path.of("src/main/resources/db/migration");
    Connection connection()throws SQLException{return DriverManager.getConnection(POSTGRES.getJdbcUrl(),POSTGRES.getUsername(),POSTGRES.getPassword());}
    void sql(String text)throws SQLException{try(var c=connection();var s=c.createStatement()){s.execute(text);}}
    void file(String name)throws Exception{sql(Files.readString(MIGRATIONS.resolve(name)));}
    void oldSchema()throws Exception{
        sql("DROP SCHEMA public CASCADE; CREATE SCHEMA public");file("V20260611_024__create_reporting_tables.sql");
        String hra=Files.readString(MIGRATIONS.resolve("V20260929_002__enforce_same_module_reference_integrity_b.sql"));
        for(String table:new String[]{"run","parameter_value","output_artifact"}){
            String field=table.equals("output_artifact")?"report_run_id":"report_request_id";
            int start=hra.indexOf("-- hidra_reporting_"+table+"."+field+" ->");int end=hra.indexOf(";",hra.indexOf("VALIDATE CONSTRAINT",start))+1;sql(hra.substring(start,end));
        }
        file("V20261004_013__hmr_013_reporting_report_definition.sql");
    }
    void migrate()throws Exception{file("V20261007_012__hmr_057_reporting_report_run.sql");file("V20261007_013__hmr_093_reporting_report_output_artifact.sql");}
    void definition(String id)throws SQLException{sql("INSERT INTO hidra_reporting_report_definition(id,code,name_fr,report_category_id,owner_module,status,requires_approval,restricted,created_at,updated_at) VALUES ('"+id+"','"+id+"','Rapport','category','reporting','ACTIVE',false,false,now(),now())");}
    void seeds()throws SQLException{
        sql("INSERT INTO hidra_reporting_catalog_entry(id,catalog_name,code,active,sort_order,system_defined,created_at,updated_at) VALUES ('category','REPORT_CATEGORY','category',true,0,false,now(),now())");
        definition("definition");definition("other");
        sql("INSERT INTO hidra_reporting_request(id,report_definition_id,requested_by_actor_id,requested_at,status,created_at,updated_at) VALUES ('request','definition','actor',now(),'SUBMITTED',now(),now())");
        sql("INSERT INTO hidra_reporting_report_template(id,report_definition_id,code,name_fr,template_engine,active,created_at,updated_at) VALUES ('template','definition','template','Template','engine',true,now(),now())");
        sql("INSERT INTO hidra_reporting_report_template_version(id,report_template_id,version_number,status,layout_content_reference,checksum,created_at) VALUES ('version','template',1,'ACTIVE','layout','hash',now())");
    }
    @BeforeEach void setup()throws Exception{oldSchema();migrate();seeds();}
    String run(String id){return "INSERT INTO hidra_reporting_run(id,report_request_id,report_definition_id,template_version_id,status,run_mode,queued_at,created_at,updated_at) VALUES ('"+id+"','request','definition','version','QUEUED','MANUAL',now(),now(),now())";}
    void parameter()throws SQLException{sql("INSERT INTO hidra_reporting_parameter_definition(id,report_definition_id,code,label_fr,parameter_type,required,sort_order,active,created_at,updated_at) VALUES ('parameter','definition','code','Parameter','TEXT',true,0,true,now(),now())");}
    String value(String type,String column,String literal){return "INSERT INTO hidra_reporting_parameter_value(id,report_request_id,parameter_definition_id,parameter_code,value_type,"+column+",created_at) VALUES ('value','request','parameter','code','"+type+"',"+literal+",now())";}
    @Test void correctedRequestForeignKeysAcceptRealRequestsAndDenyCatalogOnlyIds()throws Exception{
        sql(run("run"));parameter();sql(value("TEXT","value_text","'value'"));
        assertThrows(SQLException.class,()->sql(run("orphan").replace("'request'","'missing'")));
        assertThrows(SQLException.class,()->sql(value("TEXT","value_text","'value'").replace("'value','request'","'bad','missing'")));
        assertThrows(SQLException.class,()->sql("DELETE FROM hidra_reporting_request WHERE id='request'"));
    }
    @Test void definitionAndTemplateLineageCannotDiverge()throws Exception{
        assertThrows(SQLException.class,()->sql(run("wrong").replace("'definition'","'other'")));
        sql(run("run"));assertThrows(SQLException.class,()->sql("UPDATE hidra_reporting_request SET report_definition_id='other' WHERE id='request'"));
        assertThrows(SQLException.class,()->sql("UPDATE hidra_reporting_report_template SET report_definition_id='other' WHERE id='template'"));
    }
    @Test void retiredTemplateAllowsHistoricalUpdatesButNotNewQueues()throws Exception{
        sql(run("history"));sql("UPDATE hidra_reporting_report_template_version SET status='RETIRED' WHERE id='version'");
        sql("UPDATE hidra_reporting_run SET report_request_id=report_request_id,template_version_id=template_version_id,status='RUNNING' WHERE id='history'");
        assertThrows(SQLException.class,()->sql(run("new")));
    }
    @Test void requestLifecycleApprovalAndRestrictionGateNewQueues()throws Exception{
        for(String status:new String[]{"DRAFT","REJECTED","CANCELLED","QUEUED","RUNNING","COMPLETED"}){
            sql("UPDATE hidra_reporting_request SET status='"+status+"' WHERE id='request'");assertThrows(SQLException.class,()->sql(run("denied")));
        }
        sql("UPDATE hidra_reporting_request SET status='SUBMITTED' WHERE id='request'");sql("UPDATE hidra_reporting_report_definition SET requires_approval=true WHERE id='definition'");
        assertThrows(SQLException.class,()->sql(run("approval")));
        sql("UPDATE hidra_reporting_request SET status='APPROVED',workflow_reference_id='workflow' WHERE id='request'");sql(run("approved"));
        sql("INSERT INTO hidra_reporting_access_policy(id,report_definition_id,scope_type,scope_reference_id,permission_code,restricted,mask_sensitive_values,created_at,updated_at) VALUES ('policy','definition','ACTOR','other-actor','REPORT_READ',true,true,now(),now())");
        sql("UPDATE hidra_reporting_report_definition SET restricted=true WHERE id='definition'");assertThrows(SQLException.class,()->sql(run("restricted")));
    }
    @Test void requiredConcreteFieldsRejectMissingBlankWrongAndMultipleValues()throws Exception{
        parameter();assertThrows(SQLException.class,()->sql(run("missing")));sql(value("TEXT","value_text","' '"));assertThrows(SQLException.class,()->sql(run("blank")));
        sql("UPDATE hidra_reporting_parameter_value SET value_text='value',parameter_code='wrong'");assertThrows(SQLException.class,()->sql(run("wrong")));
        sql("UPDATE hidra_reporting_parameter_value SET parameter_code='code',value_number=0");assertThrows(SQLException.class,()->sql(run("multiple")));
        sql("UPDATE hidra_reporting_parameter_value SET value_number=null");sql(run("valid"));
    }
    @Test void numericZeroAndBooleanFalseAreConcreteEvidence()throws Exception{
        parameter();sql(value("NUMBER","value_number","0"));sql(run("zero"));
        sql("UPDATE hidra_reporting_parameter_value SET value_number=null,value_type='BOOLEAN',value_boolean=false");sql(run("false"));
    }
    @Test void terminalEvidenceIsCheckedOnDirectUpdates()throws Exception{
        sql(run("run"));assertThrows(SQLException.class,()->sql("UPDATE hidra_reporting_run SET status='COMPLETED' WHERE id='run'"));
        assertThrows(SQLException.class,()->sql("UPDATE hidra_reporting_run SET status='FAILED',failure_reason=' ' WHERE id='run'"));
        sql("UPDATE hidra_reporting_run SET status='COMPLETED',completed_at=now() WHERE id='run'");
    }
    @Test void invalidLegacyRequestAbortsMigrationWithoutRepair()throws Exception{
        oldSchema();seeds();sql("ALTER TABLE hidra_reporting_run DISABLE TRIGGER USER");
        sql("INSERT INTO hidra_reporting_catalog_entry(id,catalog_name,code,active,sort_order,system_defined,created_at,updated_at) VALUES ('catalog','OTHER','catalog',true,0,false,now(),now())");
        sql(run("legacy").replace("'request'","'catalog'"));assertThrows(SQLException.class,()->migrate());
        try(var c=connection();var s=c.createStatement();var r=s.executeQuery("SELECT report_request_id FROM hidra_reporting_run WHERE id='legacy'")){assertTrue(r.next());assertEquals("catalog",r.getString(1));}
    }
    @Test void concurrentTemplateReparentAndQueueCannotBothCommit()throws Exception{
        var start=new CountDownLatch(1);var pool=Executors.newFixedThreadPool(2);
        try{var queue=pool.submit(()->attempt(start,run("concurrent")));var reparent=pool.submit(()->attempt(start,"UPDATE hidra_reporting_report_template SET report_definition_id='other' WHERE id='template'"));start.countDown();
            assertEquals(1,(queue.get(15,TimeUnit.SECONDS)?1:0)+(reparent.get(15,TimeUnit.SECONDS)?1:0));
        }finally{pool.shutdownNow();}
    }
    boolean attempt(CountDownLatch start,String statement)throws Exception{
        start.await();try(var c=connection();var s=c.createStatement()){c.setAutoCommit(false);s.execute("SET LOCAL lock_timeout='5s'");
            try{s.execute(statement);c.commit();return true;}catch(SQLException e){c.rollback();if(!"23514".equals(e.getSQLState()) && !"23503".equals(e.getSQLState()))throw e;return false;}}
    }

    String artifact(String id,String run,String storage,String document){return "INSERT INTO hidra_reporting_output_artifact(id,report_run_id,artifact_type,format,file_name,mime_type,storage_object_reference_id,document_reference_id,checksum,generated_at,created_at) VALUES ('"+id+"','"+run+"','PRIMARY_REPORT','PDF','report.pdf','application/pdf',"+(storage==null?"null":"'"+storage+"'")+","+(document==null?"null":"'"+document+"'")+",'hash',now(),now())";}
    @Test void artifactRunForeignKeyAcceptsQueuedRunAndRejectsOrphans()throws Exception{
        sql(run("run"));sql(artifact("artifact","run","storage",null));assertThrows(SQLException.class,()->sql(artifact("orphan","missing","storage",null)));
        assertThrows(SQLException.class,()->sql("DELETE FROM hidra_reporting_run WHERE id='run'"));
    }
    @Test void artifactRequiresNonblankDocumentOrStorageShapeWithoutForeignModuleFk()throws Exception{
        sql(run("run"));assertThrows(SQLException.class,()->sql(artifact("none","run",null,null)));assertThrows(SQLException.class,()->sql(artifact("blank","run"," ","document")));
        sql(artifact("document","run",null,"document"));sql(artifact("storage","run","storage",null));sql(artifact("both","run","storage","document"));
        try(var c=connection();var s=c.createStatement();var r=s.executeQuery("SELECT count(*) FROM pg_constraint WHERE contype='f' AND conrelid='hidra_reporting_output_artifact'::regclass AND confrelid<>'hidra_reporting_run'::regclass")){assertTrue(r.next());assertEquals(0,r.getInt(1));}
    }
    @Test void legacyArtifactOrphanAbortsForwardMigration()throws Exception{
        oldSchema();file("V20261007_012__hmr_057_reporting_report_run.sql");seeds();
        sql("INSERT INTO hidra_reporting_catalog_entry(id,catalog_name,code,active,sort_order,system_defined,created_at,updated_at) VALUES ('catalog','OTHER','catalog',true,0,false,now(),now())");
        sql(artifact("legacy","catalog","storage",null));assertThrows(SQLException.class,()->file("V20261007_013__hmr_093_reporting_report_output_artifact.sql"));
        try(var c=connection();var s=c.createStatement();var r=s.executeQuery("SELECT report_run_id FROM hidra_reporting_output_artifact WHERE id='legacy'")){assertTrue(r.next());assertEquals("catalog",r.getString(1));}
    }
    @Test void legacyArtifactWithoutDocumentsEvidenceAbortsWithoutInventingReference()throws Exception{
        oldSchema();file("V20261007_012__hmr_057_reporting_report_run.sql");seeds();sql(run("run"));
        sql("INSERT INTO hidra_reporting_catalog_entry(id,catalog_name,code,active,sort_order,system_defined,created_at,updated_at) VALUES ('run','OTHER','run',true,0,false,now(),now())");
        sql(artifact("legacy","run",null,null));assertThrows(SQLException.class,()->file("V20261007_013__hmr_093_reporting_report_output_artifact.sql"));
        try(var c=connection();var s=c.createStatement();var r=s.executeQuery("SELECT storage_object_reference_id,document_reference_id FROM hidra_reporting_output_artifact WHERE id='legacy'")){assertTrue(r.next());assertNull(r.getString(1));assertNull(r.getString(2));}
    }
    @Test void concurrentRunDeleteAndArtifactInsertCannotBothCommit()throws Exception{
        sql(run("run"));var start=new CountDownLatch(1);var pool=Executors.newFixedThreadPool(2);
        try{var output=pool.submit(()->attempt(start,artifact("concurrent","run","storage",null)));var deletion=pool.submit(()->attempt(start,"DELETE FROM hidra_reporting_run WHERE id='run'"));start.countDown();
            assertEquals(1,(output.get(15,TimeUnit.SECONDS)?1:0)+(deletion.get(15,TimeUnit.SECONDS)?1:0));
        }finally{pool.shutdownNow();}
    }
}
