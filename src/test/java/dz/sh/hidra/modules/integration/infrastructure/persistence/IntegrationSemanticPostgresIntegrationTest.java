/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : IntegrationSemanticPostgresIntegrationTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-07
 *
 * @Type        : Class
 * @Layer       : Test
 * @Module      : integration
 * @Package     : dz.sh.hidra.modules.integration.infrastructure.persistence
 *
 * @Description : Enforces Integration evidence integrity through explicit owner boundaries.
 *
 */
package dz.sh.hidra.modules.integration.infrastructure.persistence;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.*;
import java.nio.file.*;
import java.sql.*;
import java.util.concurrent.*;
@Testcontainers(disabledWithoutDocker=true)
class IntegrationSemanticPostgresIntegrationTest {
    @Container static final PostgreSQLContainer<?> POSTGRES=new PostgreSQLContainer<>("postgres:16-alpine");
    static final Path MIGRATIONS=Path.of("src/main/resources/db/migration");
    Connection connection()throws SQLException{return DriverManager.getConnection(POSTGRES.getJdbcUrl(),POSTGRES.getUsername(),POSTGRES.getPassword());}
    void sql(String value)throws SQLException{try(var c=connection();var s=c.createStatement()){s.execute(value);}}
    void emptySchema()throws Exception{sql("DROP SCHEMA public CASCADE; CREATE SCHEMA public");sql(Files.readString(MIGRATIONS.resolve("V20260611_019__create_integration_tables.sql")));}
    void migrate()throws Exception{sql(Files.readString(MIGRATIONS.resolve("V20261007_010__hmr_056_integration_exchange_message.sql")));}
    void catalog(String id,String family)throws SQLException{sql("INSERT INTO hidra_integration_catalog_entry(id,catalog_name,code,active,sort_order,system_defined,created_at,updated_at) VALUES ('"+id+"','"+family+"','"+id+"',true,0,false,now(),now())");}
    void endpoint(String id,String system)throws SQLException{sql("INSERT INTO hidra_integration_external_endpoint(id,external_system_id,code,endpoint_type_id,direction,protocol_id,tls_required,active,created_at,updated_at) VALUES ('"+id+"','"+system+"','"+id+"','endpoint-type','INBOUND','protocol',true,true,now(),now())");}
    void run(String id)throws SQLException{sql("INSERT INTO hidra_integration_job_run(id,job_definition_id,run_number,trigger_type,status,started_at,received_count,mapped_count,accepted_count,rejected_count,dead_letter_count,retry_count,created_at,updated_at) VALUES ('"+id+"','definition',1,'MANUAL','QUEUED',now(),0,0,0,0,0,0,now(),now())");}
    @BeforeEach void setup()throws Exception{emptySchema();migrate();catalog("type","MESSAGE_TYPE");catalog("format","PAYLOAD_FORMAT");catalog("wrong","JOB_TYPE");endpoint("endpoint","system");endpoint("foreign","other");run("run");}
    String message(String id,String run,String endpoint){return "INSERT INTO hidra_integration_exchange_message(id,job_run_id,external_system_id,endpoint_id,direction,message_type_id,payload_format_id,payload_storage_mode,payload_hash,received_or_sent_at,status,created_at) VALUES ('"+id+"',"+(run==null?"null":"'"+run+"'")+",'system',"+(endpoint==null?"null":"'"+endpoint+"'")+",'INBOUND','type','format','HASH_ONLY','hash',now(),'RECEIVED',now())";}
    @Test void optionalRunAndEndpointOwnershipAreProtected()throws Exception{
        sql(message("none",null,null));sql(message("known","run","endpoint"));
        assertThrows(SQLException.class,()->sql(message("run-missing","missing",null)));
        assertThrows(SQLException.class,()->sql(message("endpoint-missing",null,"missing")));
        assertThrows(SQLException.class,()->sql(message("foreign",null,"foreign")));
        assertThrows(SQLException.class,()->sql("UPDATE hidra_integration_external_endpoint SET external_system_id='other' WHERE id='endpoint'"));
        assertThrows(SQLException.class,()->sql("DELETE FROM hidra_integration_job_run WHERE id='run'"));
    }
    @Test void catalogsRequireActiveExactFamilies()throws Exception{
        assertThrows(SQLException.class,()->sql(message("wrong-type",null,null).replace("'type','format'","'wrong','format'")));
        assertThrows(SQLException.class,()->sql(message("wrong-format",null,null).replace("'type','format'","'type','type'")));
        sql("UPDATE hidra_integration_catalog_entry SET active=false WHERE id='format'");
        assertThrows(SQLException.class,()->sql(message("inactive",null,null)));
    }
    @Test void legacyMismatchedEndpointAbortsWithoutReassignment()throws Exception{
        emptySchema();catalog("type","MESSAGE_TYPE");catalog("format","PAYLOAD_FORMAT");endpoint("foreign","other");sql(message("legacy",null,"foreign"));
        assertThrows(SQLException.class,()->migrate());
        try(var c=connection();var s=c.createStatement();var r=s.executeQuery("SELECT endpoint_id FROM hidra_integration_exchange_message WHERE id='legacy'")){assertTrue(r.next());assertEquals("foreign",r.getString(1));}
    }
    @Test void legacyWrongFamilyAbortsWithoutRetagging()throws Exception{
        emptySchema();catalog("type","JOB_TYPE");catalog("format","PAYLOAD_FORMAT");sql(message("legacy",null,null));
        assertThrows(SQLException.class,()->migrate());
        try(var c=connection();var s=c.createStatement();var r=s.executeQuery("SELECT catalog_name FROM hidra_integration_catalog_entry WHERE id='type'")){assertTrue(r.next());assertEquals("JOB_TYPE",r.getString(1));}
    }
    int competing(CountDownLatch go,String query)throws Exception{go.await(10,TimeUnit.SECONDS);try{sql(query);return 1;}catch(SQLException e){assertEquals("23503",e.getSQLState());return 0;}}
    @Test void concurrentEndpointReparentAndMessageCannotBothCommit()throws Exception{
        var pool=Executors.newFixedThreadPool(2);var go=new CountDownLatch(1);
        try{var a=pool.submit(()->competing(go,message("race",null,"endpoint")));var b=pool.submit(()->competing(go,"UPDATE hidra_integration_external_endpoint SET external_system_id='other' WHERE id='endpoint'"));go.countDown();assertEquals(1,a.get(20,TimeUnit.SECONDS)+b.get(20,TimeUnit.SECONDS));}finally{pool.shutdownNow();}
    }
}
