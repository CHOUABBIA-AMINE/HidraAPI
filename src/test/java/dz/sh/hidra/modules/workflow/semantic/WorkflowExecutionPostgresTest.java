/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : WorkflowExecutionPostgresTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-06
 *
 * @Type        : Class
 * @Layer       : Test
 * @Module      : workflow
 * @Package     : dz.sh.hidra.modules.workflow.semantic
 *
 * @Description : Enforces owner-validated Workflow execution and immutable evidence.
 *
 */
package dz.sh.hidra.modules.workflow.semantic;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.*;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import jakarta.persistence.EntityManager;
import dz.sh.hidra.modules.workflow.infrastructure.persistence.entity.*;
import dz.sh.hidra.modules.workflow.infrastructure.persistence.adapter.JpaWorkflowStateHistoryRepositoryAdapter;
import dz.sh.hidra.modules.workflow.infrastructure.persistence.repository.WorkflowStateHistoryJpaRepository;
import dz.sh.hidra.modules.workflow.domain.model.WorkflowStateHistory;
import java.nio.file.*;
import java.sql.*;
import java.time.Instant;
import java.util.*;
import java.util.concurrent.*;
import static org.mockito.Mockito.mock;

@Testcontainers(disabledWithoutDocker=true)
class WorkflowExecutionPostgresTest {
    @Container static final PostgreSQLContainer<?> POSTGRES=new PostgreSQLContainer<>("postgres:16-alpine");
    static SessionFactory factory;
    static final Path MIGRATIONS=Path.of("src/main/resources/db/migration");
    static final List<String> FORWARD=List.of("V20261006_014__hmr_055_workflow_instance.sql",
        "V20261006_015__hmr_061_workflow_transition.sql","V20261006_016__hmr_066_workflow_task.sql",
        "V20261006_017__hmr_081_workflow_action.sql","V20261006_018__hmr_099_workflow_state_history.sql");
    @BeforeAll static void factory(){
        var cfg=new Configuration().setProperty("hibernate.connection.url",POSTGRES.getJdbcUrl())
            .setProperty("hibernate.connection.username",POSTGRES.getUsername())
            .setProperty("hibernate.connection.password",POSTGRES.getPassword()).setProperty("hibernate.hbm2ddl.auto","none");
        for(Class<?> type:List.of(WorkflowInstanceJpaEntity.class,WorkflowTaskJpaEntity.class,WorkflowActionJpaEntity.class,
            WorkflowStateHistoryJpaEntity.class,WorkflowStepJpaEntity.class,WorkflowCatalogEntryJpaEntity.class))cfg.addAnnotatedClass(type);
        factory=cfg.buildSessionFactory();
    }
    @AfterAll static void close(){if(factory!=null)factory.close();}
    Connection connection()throws SQLException{return DriverManager.getConnection(POSTGRES.getJdbcUrl(),POSTGRES.getUsername(),POSTGRES.getPassword());}
    void sql(String value)throws SQLException{try(var c=connection();var s=c.createStatement()){s.execute(value);}}
    long number(String query)throws SQLException{try(var c=connection();var s=c.createStatement();var r=s.executeQuery(query)){assertTrue(r.next());return r.getLong(1);}}
    void emptySchema()throws Exception{
        sql("DROP SCHEMA public CASCADE; CREATE SCHEMA public");
        sql(Files.readString(MIGRATIONS.resolve("V20260611_016__create_workflow_tables.sql")));
    }
    @BeforeEach void setup()throws Exception{
        emptySchema();for(String name:FORWARD)sql(Files.readString(MIGRATIONS.resolve(name)));baseRows();
    }
    void baseRows()throws Exception{
        for(String[] c:List.of(new String[]{"purpose","WORKFLOW_PURPOSE","VALIDATION"},new String[]{"type","WORKFLOW_TARGET_TYPE","PLAN_REVISION"},
            new String[]{"mode","WORKFLOW_ASSIGNMENT_MODE","DIRECT"},new String[]{"reason","WORKFLOW_REASON","REVIEW"},new String[]{"priority","WORKFLOW_PRIORITY","NORMAL"}))
            sql("INSERT INTO hidra_workflow_type_catalog(id,catalog_name,code,active,sort_order,system_defined,created_at,updated_at) VALUES ('"+c[0]+"','"+c[1]+"','"+c[2]+"',true,0,false,now(),now())");
        definition("def","ACTIVE");step("step","def",1);step("next","def",2);
        sql("INSERT INTO hidra_workflow_definition_target_binding(id,definition_id,target_module,target_type_id,workflow_purpose_id,active,created_at,updated_at) VALUES ('binding','def','planning','type','purpose',true,now(),now())");
        sql(instance("instance","plan"));
        sql("INSERT INTO hidra_workflow_task(id,instance_id,step_id,status,assigned_actor_id,assignment_mode_id,created_at,updated_at) VALUES ('task','instance','step','OPEN','actor','mode',now(),now())");
    }
    void definition(String id,String status)throws SQLException{
        sql("INSERT INTO hidra_workflow_definition(id,code,name_fr,type_id,status,version,created_at,updated_at) VALUES ('"+id+"','"+id+"','Definition','type','"+status+"',1,now(),now())");
    }
    void step(String id,String def,int order)throws SQLException{
        sql("INSERT INTO hidra_workflow_step(id,definition_id,code,name_fr,step_order,mandatory,allow_claim,allow_delegation,allow_escalation,created_at,updated_at) VALUES ('"+id+"','"+def+"','"+id+"','Review',"+order+",true,true,false,false,now(),now())");
    }
    String instance(String id,String target){return "INSERT INTO hidra_workflow_instance(id,definition_id,definition_version,workflow_purpose_id,target_module,target_type_id,target_id,status,current_step_id,started_by_actor_id,started_by_display_name_snapshot,started_at,created_at,updated_at) VALUES ('"+id+"','def',1,'purpose','planning','type','"+target+"','STARTED','step','actor','Alice',now(),now(),now())";}
    String action(String id,long sequence){return "INSERT INTO hidra_workflow_action(id,instance_id,task_id,action_type,actor_id,actor_display_name_snapshot,action_sequence,acted_at) VALUES ('"+id+"','instance','task','COMMENT','actor','Alice',"+sequence+",now())";}
    String history(String id){return "INSERT INTO hidra_workflow_state_history(id,instance_id,to_status,actor_id,actor_display_name_snapshot,changed_at) VALUES ('"+id+"','instance','COMPLETED','actor','Alice',now())";}
    @Test void concurrentStartsAllowExactlyOneNonterminalTuple()throws Exception{
        var pool=Executors.newFixedThreadPool(2);var ready=new CountDownLatch(2);var go=new CountDownLatch(1);
        try{
            List<Future<String>> futures=new ArrayList<>();
            for(int index=0;index<2;index++){final int n=index;futures.add(pool.submit(()->{
                try(var c=connection();var s=c.createStatement()){ready.countDown();go.await();s.setQueryTimeout(10);s.execute(instance("race"+n,"race-target"));return "saved";}
                catch(SQLException ex){return ex.getSQLState();}
            }));}
            assertTrue(ready.await(10,TimeUnit.SECONDS));go.countDown();
            var results=List.of(futures.get(0).get(15,TimeUnit.SECONDS),futures.get(1).get(15,TimeUnit.SECONDS));
            assertEquals(1L,results.stream().filter("saved"::equals).count());assertEquals(1L,results.stream().filter("23505"::equals).count());
        }finally{go.countDown();pool.shutdownNow();}
    }
    @Test void concurrentActionWritersAllocateContiguousServerSequences()throws Exception{
        var pool=Executors.newFixedThreadPool(2);var go=new CountDownLatch(1);
        try{
            List<Future<Long>> futures=new ArrayList<>();
            for(int index=0;index<2;index++){final int n=index;futures.add(pool.submit(()->{
                try(var c=connection();var s=c.createStatement()){
                    c.setAutoCommit(false);go.await();s.setQueryTimeout(10);
                    try(var lock=s.executeQuery("SELECT id FROM hidra_workflow_instance WHERE id='instance' FOR UPDATE")){assertTrue(lock.next());}
                    long seq;try(var r=s.executeQuery("SELECT COALESCE(MAX(action_sequence),0)+1 FROM hidra_workflow_action WHERE instance_id='instance'")){r.next();seq=r.getLong(1);}
                    s.execute(action("race-action"+n,seq));c.commit();return seq;
                }
            }));}
            go.countDown();var sequences=new TreeSet<Long>();for(var f:futures)sequences.add(f.get(15,TimeUnit.SECONDS));
            assertEquals(Set.of(1L,2L),sequences);assertEquals(2L,number("SELECT count(*) FROM hidra_workflow_action"));
        }finally{go.countDown();pool.shutdownNow();}
    }
    @Test void rejectsSkippedSequenceAndActionMutation()throws Exception{
        assertThrows(SQLException.class,()->sql(action("skip",9)));sql(action("a",1));
        assertThrows(SQLException.class,()->sql("UPDATE hidra_workflow_action SET actor_display_name_snapshot='Forged'"));
        assertThrows(SQLException.class,()->sql("DELETE FROM hidra_workflow_action"));
        assertThrows(SQLException.class,()->sql("TRUNCATE hidra_workflow_action"));
    }
    @Test void transitionCompositionAndDecisionUniquenessAreDatabaseSafe()throws Exception{
        String prefix="INSERT INTO hidra_workflow_transition(id,definition_id,from_step_id,to_step_id,decision,reason_required,comment_required,created_at,updated_at) VALUES ";
        sql(prefix+"('transition','def','step','next','APPROVE',false,false,now(),now())");
        assertThrows(SQLException.class,()->sql(prefix+"('duplicate','def','step','next','APPROVE',false,false,now(),now())"));
        assertThrows(SQLException.class,()->sql(prefix+"('same','def','step','step','REJECT',true,false,now(),now())"));
        definition("other","ACTIVE");step("foreign","other",1);
        assertThrows(SQLException.class,()->sql(prefix+"('cross','def','step','foreign','REJECT',true,false,now(),now())"));
    }
    @Test void unsupportedConfigurationCannotAttachOrActivate()throws Exception{
        String prefix="INSERT INTO hidra_workflow_transition(id,definition_id,from_step_id,to_step_id,decision,reason_required,comment_required,condition_expression,created_at,updated_at) VALUES ";
        assertThrows(SQLException.class,()->sql(prefix+"('active','def','step','next','APPROVE',false,false,'true',now(),now())"));
        definition("draft","DRAFT");step("draft-from","draft",1);step("draft-to","draft",2);
        sql(prefix+"('draft-transition','draft','draft-from','draft-to','APPROVE',false,false,'true',now(),now())");
        assertThrows(SQLException.class,()->sql("UPDATE hidra_workflow_definition SET status='ACTIVE' WHERE id='draft'"));
    }
    @Test void taskPairsCatalogsAndTerminalEvidenceAreProtected()throws Exception{
        assertThrows(SQLException.class,()->sql("UPDATE hidra_workflow_task SET claimed_by_actor_id='actor'"));
        assertThrows(SQLException.class,()->sql("UPDATE hidra_workflow_task SET assignment_mode_id='reason'"));
        assertThrows(SQLException.class,()->sql("UPDATE hidra_workflow_task SET priority_id='mode'"));
        sql("UPDATE hidra_workflow_task SET status='APPROVED',completed_by_actor_id='actor',completed_at=now(),updated_at=now()");
        assertThrows(SQLException.class,()->sql("UPDATE hidra_workflow_task SET status='OPEN',completed_by_actor_id=NULL,completed_at=NULL"));
        assertThrows(SQLException.class,()->sql("DELETE FROM hidra_workflow_task"));
        assertThrows(SQLException.class,()->sql("TRUNCATE hidra_workflow_task"));
    }
    @Test void optionalHistoryLinksRemainOptionalAndImmutable()throws Exception{
        sql(history("history"));
        assertThrows(SQLException.class,()->sql("UPDATE hidra_workflow_state_history SET to_status='FORGED'"));
        assertThrows(SQLException.class,()->sql("DELETE FROM hidra_workflow_state_history"));
        assertThrows(SQLException.class,()->sql("TRUNCATE hidra_workflow_state_history"));
        assertEquals(1L,number("SELECT count(*) FROM hidra_workflow_state_history"));
    }
    @Test void incoherentHistoryReferencesAndBlankEvidenceAreRejected()throws Exception{
        assertThrows(SQLException.class,()->sql("INSERT INTO hidra_workflow_state_history(id,instance_id,task_id,to_status,actor_id,actor_display_name_snapshot,changed_at) VALUES ('h','instance','unknown','COMPLETED','actor','Alice',now())"));
        assertThrows(SQLException.class,()->sql("INSERT INTO hidra_workflow_state_history(id,instance_id,to_status,actor_id,actor_display_name_snapshot,changed_at) VALUES ('blank','instance',' ','actor','Alice',now())"));
        sql(action("a",1));
        assertThrows(SQLException.class,()->sql("INSERT INTO hidra_workflow_state_history(id,instance_id,action_id,to_status,actor_id,actor_display_name_snapshot,changed_at) VALUES ('h','instance','a','COMPLETED','other','Other',now())"));
    }
    @Test void jpaHistorySaveInsertsAndCannotOverwriteExistingId()throws Exception{
        var original=new WorkflowStateHistory("jpa-history","instance",null,null,null,null,"COMPLETED","actor","alice","Alice",null,null,null,null,Instant.now());
        try(EntityManager em=factory.createEntityManager()){
            em.getTransaction().begin();new JpaWorkflowStateHistoryRepositoryAdapter(mock(WorkflowStateHistoryJpaRepository.class),em).save(original);em.getTransaction().commit();
        }
        try(EntityManager em=factory.createEntityManager()){
            em.getTransaction().begin();
            assertThrows(RuntimeException.class,()->new JpaWorkflowStateHistoryRepositoryAdapter(mock(WorkflowStateHistoryJpaRepository.class),em).save(new WorkflowStateHistory("jpa-history","instance",null,null,null,null,"FORGED","actor","alice","Alice",null,null,null,null,Instant.now())));
            em.getTransaction().rollback();
        }
        assertEquals(1L,number("SELECT count(*) FROM hidra_workflow_state_history WHERE id='jpa-history' AND to_status='COMPLETED'"));
    }
    @Test void legacyPurposeMismatchAbortsForwardMigrationWithoutRetagging()throws Exception{
        emptySchema();baseRows();sql("UPDATE hidra_workflow_type_catalog SET catalog_name='GENERIC' WHERE id='purpose'");
        assertThrows(SQLException.class,()->sql(Files.readString(MIGRATIONS.resolve(FORWARD.get(0)))));
        assertEquals(1L,number("SELECT count(*) FROM hidra_workflow_type_catalog WHERE id='purpose' AND catalog_name='GENERIC'"));
    }
}
