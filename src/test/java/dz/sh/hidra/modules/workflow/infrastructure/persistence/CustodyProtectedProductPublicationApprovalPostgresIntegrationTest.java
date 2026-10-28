/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : CustodyProtectedProductPublicationApprovalPostgresIntegrationTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-27
 *
 * @Type        : Class
 * @Layer       : Test
 * @Module      : workflow
 * @Package     : dz.sh.hidra.modules.workflow.infrastructure.persistence
 *
 * @Description : Provides bounded product source publication governance.
 *
 */
package dz.sh.hidra.modules.workflow.infrastructure.persistence;

import dz.sh.hidra.HidraApplication;
import dz.sh.hidra.modules.workflow.application.contract.custody.CustodyProtectedProductPublicationApprovalContract;
import dz.sh.hidra.modules.workflow.application.contract.custody.CustodyProtectedProductPublicationApprovalContract.*;
import dz.sh.hidra.modules.workflow.application.contract.target.WorkflowOwnedTargetLookup;
import dz.sh.hidra.modules.workflow.application.service.WorkflowApplicationService;
import dz.sh.hidra.modules.workflow.application.port.in.ExecuteWorkflowTransitionUseCase;
import dz.sh.hidra.modules.workflow.application.port.out.WorkflowTaskRepositoryPort;
import dz.sh.hidra.modules.workflow.application.command.*;
import dz.sh.hidra.modules.workflow.application.dto.WorkflowTransitionExecutionDto;
import java.sql.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.*;
import org.springframework.transaction.*;
import org.springframework.transaction.support.TransactionTemplate;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.*;
import static org.junit.jupiter.api.Assertions.*;

@Testcontainers(disabledWithoutDocker=true)
@SpringBootTest(classes=HidraApplication.class,webEnvironment=SpringBootTest.WebEnvironment.MOCK)
@ActiveProfiles("test")
@Import(CustodyProtectedProductPublicationApprovalPostgresIntegrationTest.TargetFixture.class)
public class CustodyProtectedProductPublicationApprovalPostgresIntegrationTest {
    @Container static final PostgreSQLContainer<?> POSTGRES=new PostgreSQLContainer<>("postgres:16").withCommand("postgres","-c","autovacuum=off");
    @DynamicPropertySource static void database(DynamicPropertyRegistry r){r.add("spring.datasource.url",POSTGRES::getJdbcUrl);r.add("spring.datasource.username",POSTGRES::getUsername);r.add("spring.datasource.password",POSTGRES::getPassword);r.add("spring.jpa.hibernate.ddl-auto",()->"validate");r.add("spring.flyway.enabled",()->"true");}
    // Explicitly admitted Package-A test-only lookup; production candidate lookup belongs to B.
    private static final Set<String> TARGETS=ConcurrentHashMap.newKeySet();
    @TestConfiguration static class TargetFixture {
        @Bean WorkflowOwnedTargetLookup publicationTargetFixture(){return new WorkflowOwnedTargetLookup(){
            public String module(){return "custody";}public Set<String> targetTypeCodes(){return Set.of("PRODUCT_SOURCE_PUBLICATION");}
            public Optional<Target> eligibleTarget(String code,String id){return targetTypeCodes().contains(code) && TARGETS.contains(id)?Optional.of(new Target(id,id,"Synthetic publication envelope")):Optional.empty();}
        };}
    }
    @org.springframework.test.context.bean.override.mockito.MockitoBean(name="custodyProductPublicationWorkflowTargetLookup",enforceOverride=true)
    WorkflowOwnedTargetLookup replacedProductionLookup;
    @Autowired java.util.List<WorkflowOwnedTargetLookup> targetLookups;
    @Autowired JdbcTemplate jdbc;@Autowired PlatformTransactionManager manager;
    @Autowired CustodyProtectedProductPublicationApprovalContract owner;
    @Autowired WorkflowApplicationService application;@Autowired ExecuteWorkflowTransitionUseCase transitions;@Autowired WorkflowTaskRepositoryPort tasks;
    private String company,definition,type,purpose,actor,digest,instance,task,action;
    private TransactionTemplate tx(){var tx=new TransactionTemplate(manager);tx.setIsolationLevel(TransactionDefinition.ISOLATION_READ_COMMITTED);return tx;}
    private static String id(){return UUID.randomUUID().toString();}
    @BeforeEach void fixture(){
        company=id();definition=id();type=id();purpose=id();actor=id();digest=id().replace("-","")+id().replace("-","");TARGETS.add(digest);
        jdbc.execute("REVOKE CREATE ON SCHEMA public FROM PUBLIC");
        jdbc.execute("DO $$ BEGIN IF NOT EXISTS(SELECT 1 FROM pg_roles WHERE rolname='publication_workflow_reader') THEN CREATE ROLE publication_workflow_reader; END IF; END $$");
        jdbc.execute("GRANT USAGE ON SCHEMA public TO publication_workflow_reader");jdbc.execute("GRANT SELECT ON ALL TABLES IN SCHEMA public TO publication_workflow_reader");
        jdbc.execute("DO $$ BEGIN IF NOT EXISTS(SELECT 1 FROM pg_roles WHERE rolname='publication_workflow_publisher') THEN CREATE ROLE publication_workflow_publisher; END IF; END $$");
        jdbc.execute("GRANT USAGE ON SCHEMA public TO publication_workflow_publisher");
        jdbc.execute("GRANT SELECT,UPDATE ON public.hidra_workflow_action,public.hidra_workflow_custody_publication_policy,public.hidra_workflow_definition,public.hidra_workflow_definition_target_binding,public.hidra_workflow_instance,public.hidra_workflow_state_history,public.hidra_workflow_step,public.hidra_workflow_step_assignment_rule,public.hidra_workflow_task,public.hidra_workflow_transition,public.hidra_workflow_type_catalog TO publication_workflow_publisher");
        jdbc.update("INSERT INTO hidra_identity_user(id,username,display_name,user_type,status,failed_login_count,created_at,activated_at,updated_at) VALUES (?,?,'Synthetic Reviewer','HUMAN','ACTIVE',0,now(),now(),now())",actor,actor);
        for(var c:List.of(new String[]{type,"WORKFLOW_TARGET_TYPE","PRODUCT_SOURCE_PUBLICATION"},new String[]{purpose,"WORKFLOW_PURPOSE","PRODUCT_SOURCE_PUBLICATION_APPROVAL"}))
            jdbc.update("INSERT INTO hidra_workflow_type_catalog(id,catalog_name,code,active,sort_order,system_defined,created_at,updated_at) VALUES (?,?,?,true,0,false,now(),now())",c[0],c[1],c[2]);
        jdbc.update("INSERT INTO hidra_workflow_definition(id,code,name_fr,type_id,status,version,created_at,updated_at) VALUES (?,?,'Synthetic publication review',?,'ACTIVE',1,now(),now())",definition,definition,type);
        for(int n=1;n<=2;n++)jdbc.update("INSERT INTO hidra_workflow_step(id,definition_id,code,name_fr,step_order,mandatory,allow_claim,allow_delegation,allow_escalation,created_at,updated_at) VALUES (?,?,?,'Synthetic review',?,true,false,false,false,now(),now())",definition+n,definition,"STEP"+n,n);
        jdbc.update("INSERT INTO hidra_workflow_definition_target_binding(id,definition_id,target_module,target_type_id,workflow_purpose_id,active,created_at,updated_at) VALUES (?,?,'custody',?,?,true,now(),now())",id(),definition,type,purpose);
        jdbc.update("INSERT INTO hidra_workflow_transition(id,definition_id,from_step_id,to_step_id,decision,reason_required,comment_required,created_at,updated_at) VALUES (?,?,?,?,'APPROVE',false,false,now(),now())",definition+"approve",definition,definition+1,definition+2);
        jdbc.update("INSERT INTO hidra_workflow_custody_publication_policy VALUES (?,?,1,?,?,true,now(),now())",company,definition,type,purpose);
        SecurityContextHolder.getContext().setAuthentication(UsernamePasswordAuthenticationToken.authenticated(actor,"synthetic",List.of()));
        var i=application.startWorkflowInstance(new StartWorkflowInstanceCommand(definition,1,purpose,"custody",type,digest,null,null,definition+1,actor,null,"ignored caller",null,"synthetic"));instance=i.id();
        task=application.createWorkflowTask(new CreateWorkflowTaskCommand(instance,definition+1,actor,null,null,null,null,null,null,null,null,"Synthetic review")).id();
        // Warm the catalog/driver path outside each measured admission window.
        within(policy(),a->{assertNull(owner.read(a).approval());owner.requireCurrent(a);});
    }
    @AfterEach void clear(){SecurityContextHolder.clearContext();TARGETS.remove(digest);}
    private void approve(){var t=tasks.findById(task).orElseThrow();WorkflowTransitionExecutionDto done=transitions.execute(new ExecuteWorkflowTransitionCommand(task,definition+"approve",t.updatedAt(),null,null,null,"synthetic",actor,null,"ignored caller",Set.of()));action=done.actionId();}
    private Request policy(){return new Request(company,null,null,null,null);}
    private Request exact(){return new Request(company,digest,instance,task,action);}
    private void within(Request request,Consumer<Admission> work){tx().executeWithoutResult(s->{jdbc.queryForObject("SELECT set_config('role','publication_workflow_publisher',true)",String.class);work.accept(owner.acquire(request));});}
    @Test void policyPreparationHasNoApprovalAndRetainsAllElevenFences(){within(policy(),a->{var c=owner.read(a);assertEquals(definition,c.policy().definitionId());assertNull(c.approval());assertEquals(11,owner.finalFences(a).size());owner.requireCurrent(a);});}
    @Test void actualHumanTerminalTransitionBindsExactDigestAndServerActor(){approve();within(exact(),a->{var c=owner.read(a);assertEquals(digest,c.approval().targetDigest());assertEquals(actor,c.approval().actorId());assertEquals("Synthetic Reviewer",c.approval().actorDisplayName());assertNotNull(c.approval().historyId());owner.requireCurrent(a);});}
    @Test void nonhumanApprovalRejectsBeforeAnyActionHistoryOrTaskEffect(){int histories=jdbc.queryForObject("SELECT count(*) FROM hidra_workflow_state_history WHERE instance_id=?",Integer.class,instance);jdbc.update("UPDATE hidra_identity_user SET user_type='SERVICE' WHERE id=?",actor);assertThrows(RuntimeException.class,this::approve);assertEquals(0,jdbc.queryForObject("SELECT count(*) FROM hidra_workflow_action WHERE instance_id=?",Integer.class,instance));assertEquals("OPEN",tasks.findById(task).orElseThrow().status().name());assertEquals(histories,jdbc.queryForObject("SELECT count(*) FROM hidra_workflow_state_history WHERE instance_id=?",Integer.class,instance));}
    @Test void unapprovedAndSubstitutedApprovalOrCompanyReject(){assertThrows(RuntimeException.class,()->within(new Request(company,digest,instance,task,id()),owner::read));approve();
        for(Request r:List.of(new Request(company,"a".repeat(64),instance,task,action),new Request(id(),digest,instance,task,action),new Request(company,digest,instance,id(),action),new Request(company,digest,instance,task,id())))assertThrows(RuntimeException.class,()->within(r,owner::read));}
    @Test void revokedPolicyDefinitionAndBindingFailClosed(){approve();jdbc.update("UPDATE hidra_workflow_custody_publication_policy SET active=false WHERE company_party_id=?",company);assertThrows(RuntimeException.class,()->within(exact(),owner::read));jdbc.update("UPDATE hidra_workflow_custody_publication_policy SET active=true WHERE company_party_id=?",company);jdbc.update("UPDATE hidra_workflow_definition SET status='DRAFT' WHERE id=?",definition);assertThrows(RuntimeException.class,()->within(exact(),owner::read));jdbc.update("UPDATE hidra_workflow_definition SET status='ACTIVE' WHERE id=?",definition);jdbc.update("UPDATE hidra_workflow_definition_target_binding SET active=false WHERE definition_id=?",definition);assertThrows(RuntimeException.class,()->within(exact(),owner::read));}
    @Test void replayAndCompletedTicketsCannotBeReused(){var saved=new AtomicReference<Admission>();within(policy(),a->{saved.set(a);owner.read(a);});assertThrows(RuntimeException.class,()->owner.read(saved.get()));assertThrows(RuntimeException.class,()->within(policy(),a->{owner.read(a);owner.read(a);}));}
    @Test void forgedCrossThreadAndSwallowedMisusePoisonOriginalTransaction() throws Exception {
        assertThrows(RuntimeException.class,()->owner.read(new Admission(){}));
        assertThrows(RuntimeException.class,()->within(policy(),a->{owner.read(a);var e=Executors.newSingleThreadExecutor();try{e.submit(()->assertThrows(RuntimeException.class,()->owner.requireCurrent(a))).get();}catch(Exception x){throw new IllegalStateException(x);}finally{e.shutdownNow();}}));
    }
    @Test void suspendedTicketCannotResume(){assertThrows(RuntimeException.class,()->within(policy(),a->{owner.read(a);var other=tx();other.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);other.executeWithoutResult(s->{});owner.requireCurrent(a);}));}
    @Test void policyWriterFirstRejectsNowaitAdmission() throws Exception {try(Connection c=DriverManager.getConnection(POSTGRES.getJdbcUrl(),POSTGRES.getUsername(),POSTGRES.getPassword())){c.setAutoCommit(false);try(PreparedStatement p=c.prepareStatement("UPDATE hidra_workflow_custody_publication_policy SET active=false WHERE company_party_id=?")){p.setString(1,company);p.executeUpdate();}assertThrows(RuntimeException.class,()->within(policy(),owner::read));c.rollback();}}
    @Test void retainedOwnerFencesBlockConcurrentPolicyWriter(){within(policy(),a->{owner.read(a);try(Connection c=DriverManager.getConnection(POSTGRES.getJdbcUrl(),POSTGRES.getUsername(),POSTGRES.getPassword());Statement s=c.createStatement()){s.execute("SET lock_timeout='100ms'");try(PreparedStatement p=c.prepareStatement("UPDATE hidra_workflow_custody_publication_policy SET active=false WHERE company_party_id=?")){p.setString(1,company);SQLException failure=assertThrows(SQLException.class,p::executeUpdate);assertEquals("55P03",failure.getSQLState());}}catch(SQLException e){throw new IllegalStateException(e);}owner.requireCurrent(a);});}
    @Test void unsafePrivilegedAndDisabledImmutableTriggerReject(){assertThrows(RuntimeException.class,()->tx().executeWithoutResult(s->owner.read(owner.acquire(policy()))));jdbc.execute("ALTER TABLE hidra_workflow_action DISABLE TRIGGER tr_hmr081_action_immutable");try{assertThrows(RuntimeException.class,()->within(policy(),owner::read));}finally{jdbc.execute("ALTER TABLE hidra_workflow_action ENABLE TRIGGER tr_hmr081_action_immutable");}}
    @Test void ordinaryRoleCannotMutatePolicyAndExistingActionHistoryStaysImmutable(){approve();tx().executeWithoutResult(s->{jdbc.queryForObject("SELECT set_config('role','publication_workflow_reader',true)",String.class);assertThrows(RuntimeException.class,()->jdbc.update("UPDATE hidra_workflow_custody_publication_policy SET active=false WHERE company_party_id=?",company));s.setRollbackOnly();});assertThrows(RuntimeException.class,()->jdbc.update("UPDATE hidra_workflow_action SET actor_id='other' WHERE id=?",action));assertThrows(RuntimeException.class,()->jdbc.update("DELETE FROM hidra_workflow_state_history WHERE action_id=?",action));}
    @Test void completedCorrectActionCannotMasqueradeAsApprove() {
        jdbc.update("UPDATE hidra_workflow_transition SET decision='CORRECT' WHERE id=?",definition+"approve");approve();
        assertEquals("APPROVED",tasks.findById(task).orElseThrow().status().name());
        assertThrows(RuntimeException.class,()->within(exact(),owner::read));
    }

    @Test void productionLookupDoesNotDuplicateTheIsolatedApprovalFixture(){
        long count=targetLookups.stream().filter(t->"custody".equals(t.module()) && t.targetTypeCodes().contains("PRODUCT_SOURCE_PUBLICATION")).count();assertEquals(1,count);
        org.mockito.Mockito.verify(replacedProductionLookup,org.mockito.Mockito.never()).eligibleTarget(org.mockito.ArgumentMatchers.anyString(),org.mockito.ArgumentMatchers.eq(digest));
        assertTrue(org.mockito.Mockito.mockingDetails(replacedProductionLookup).isMock());
    }
}
