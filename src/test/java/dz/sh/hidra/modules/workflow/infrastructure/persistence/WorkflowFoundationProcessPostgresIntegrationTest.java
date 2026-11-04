/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : WorkflowFoundationProcessPostgresIntegrationTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-04
 *
 * @Type        : Class
 * @Layer       : Test
 * @Module      : workflow
 * @Package     : dz.sh.hidra.modules.workflow.infrastructure.persistence
 *
 * @Description : Qualifies real Workflow discovery, REST transitions and evidence on fresh PostgreSQL.
 *
 */
package dz.sh.hidra.modules.workflow.infrastructure.persistence;

import dz.sh.hidra.HidraApplication;
import dz.sh.hidra.modules.identity.application.contract.workflow.WorkflowActorContract;
import dz.sh.hidra.modules.identity.application.contract.workflow.WorkflowHumanActorContract;
import dz.sh.hidra.modules.workflow.application.contract.target.WorkflowOwnedTargetLookup;
import dz.sh.hidra.modules.workflow.infrastructure.configuration.FoundationWorkflowDevelopmentProvisioner;
import java.util.*;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.*;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.*;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test-only target probe is NOT physical owner acceptance. The native Topology
 * protected-transaction suite independently verifies the actual source and grants.
 * This suite proves Workflow's real migrated records, HTTP actor control and transitions.
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Testcontainers(disabledWithoutDocker=true)
@SpringBootTest(classes=HidraApplication.class,webEnvironment=SpringBootTest.WebEnvironment.MOCK)
@ActiveProfiles("test")
@org.springframework.test.annotation.DirtiesContext(
        classMode=org.springframework.test.annotation.DirtiesContext.ClassMode.AFTER_CLASS)
class WorkflowFoundationProcessPostgresIntegrationTest {
    @Container static final PostgreSQLContainer<?> PG=new PostgreSQLContainer<>("postgres:16")
            .withDatabaseName("workflow_foundation_process");
    @DynamicPropertySource static void db(DynamicPropertyRegistry r) {
        r.add("spring.datasource.url",PG::getJdbcUrl);
        r.add("spring.datasource.username",PG::getUsername);
        r.add("spring.datasource.password",PG::getPassword);
        r.add("spring.jpa.hibernate.ddl-auto",()->"validate");
        r.add("spring.flyway.enabled",()->"true");
        r.add("hidra.platform.security.jwt.token-issuer",()->"https://workflow-process.synthetic.invalid");
    }
    @MockitoBean(name="topologyInstalledCharacteristicLinkWorkflowTargetLookup",enforceOverride=true)
    WorkflowOwnedTargetLookup sourceProbe;
    @Autowired JdbcTemplate jdbc;
    @Autowired WebApplicationContext context;
    @Autowired PasswordEncoder passwords;
    @Autowired WorkflowActorContract actors;
    @Autowired WorkflowHumanActorContract humans;
    MockMvc http;
    String starter, reviewer, observer, starterToken, reviewerToken, observerToken;
    String definition, review, accepted, rejected, approve, reject, mode, reason, type, purpose, digest, locator;
    private static final String BASE="/api/v1/workflow";
    private static final String TYPE="INSTALLED_CHARACTERISTIC_LINK_REVISION";
    private static final String PASSWORD="synthetic-workflow-process-password";
    private static final String SHA="a".repeat(64);
    private String id(){return UUID.randomUUID().toString();}

    @BeforeEach void setup() throws Exception {
        http=MockMvcBuilders.webAppContextSetup(context)
                .apply(org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity()).build();
        jdbc.update("UPDATE public.hidra_identity_provider SET status='INACTIVE' WHERE status='ACTIVE'");
        String provider=id();jdbc.update("INSERT INTO public.hidra_identity_provider"
                +"(id,code,name,provider_type,sync_enabled,just_in_time_provisioning_enabled,status,created_at,updated_at)"
                +" VALUES (?,?,'Synthetic LOCAL','LOCAL',false,false,'ACTIVE',now(),now())",provider,provider);
        starter=id();reviewer=id();observer=id();
        starterToken=login(starter);reviewerToken=login(reviewer);observerToken=login(observer);
        type=jdbc.queryForObject("SELECT id FROM public.hidra_workflow_type_catalog WHERE "
                +"catalog_name='WORKFLOW_TARGET_TYPE' AND code='INSTALLED_CHARACTERISTIC_LINK_REVISION'",String.class);
        purpose=jdbc.queryForObject("SELECT id FROM public.hidra_workflow_type_catalog WHERE "
                +"catalog_name='WORKFLOW_PURPOSE' AND code='INSTALLED_CHARACTERISTIC_LINK_APPROVAL'",String.class);
        digest=SHA;locator="v1.synthetic.review.locator";
        reset(sourceProbe);
        when(sourceProbe.module()).thenReturn("topology");
        when(sourceProbe.targetTypeCodes()).thenReturn(Set.of(TYPE));
        when(sourceProbe.eligibleTarget(eq(TYPE),eq(digest),eq(locator)))
                .thenReturn(Optional.of(new WorkflowOwnedTargetLookup.Target(digest,locator,"Synthetic guarded target")));
        when(sourceProbe.protectedReadAllowed(eq(TYPE),eq(digest),eq(locator),
                any(WorkflowOwnedTargetLookup.ProtectedRead.class))).thenReturn(true);
        configure();
    }
    private String login(String user) throws Exception {
        jdbc.update("INSERT INTO public.hidra_identity_user"
                +"(id,username,display_name,user_type,status,failed_login_count,created_at,updated_at)"
                +" VALUES (?,?,'Synthetic Reviewer','HUMAN','ACTIVE',0,now(),now())",user,user);
        jdbc.update("INSERT INTO public.hidra_identity_local_credential"
                +"(id,user_id,password_hash,credential_status,created_at,updated_at)"
                +" VALUES (?,?,?,'ACTIVE',now(),now())",id(),user,passwords.encode(PASSWORD));
        String role=jdbc.queryForList("SELECT id FROM public.hidra_identity_role WHERE code='HIDRA_ADMIN'",
                String.class).stream().findFirst().orElse(null);
        if(role==null){role=id();jdbc.update("INSERT INTO public.hidra_identity_role"
                +"(id,code,role_type,status,created_at,updated_at) VALUES "
                +"(?,'HIDRA_ADMIN','ADMIN','ACTIVE',now(),now())",role);}
        jdbc.update("INSERT INTO public.hidra_identity_user_role_grant"
                +"(id,user_id,role_id,scope_type,valid_from,status,created_at)"
                +" VALUES (?,?,?,'GLOBAL',now()-interval '1 hour','ACTIVE',now())",id(),user,role);
        String response=http.perform(post("/api/v1/identity/authentication/login")
                .contentType("application/json")
                .content(json("providerType","LOCAL","principal",user,"credentials",PASSWORD)))
                .andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
        return com.jayway.jsonpath.JsonPath.read(response,"$.accessToken");
    }
    private String json(Object... pairs) throws Exception {
        var values=new LinkedHashMap<String,Object>();
        for(int i=0;i<pairs.length;i+=2)values.put((String)pairs[i],pairs[i+1]);
        return tools.jackson.databind.json.JsonMapper.builder().build().writeValueAsString(values);
    }
    private ResultActions call(String verb,String path,String token,String body) throws Exception {
        var request=org.springframework.test.web.servlet.request.MockMvcRequestBuilders
                .request(org.springframework.http.HttpMethod.valueOf(verb),path);
        if(token!=null)request.header("Authorization","Bearer "+token);
        if(body!=null)request.contentType("application/json").content(body);
        return http.perform(request);
    }
    private String value(ResultActions r,String field) throws Exception {
        return com.jayway.jsonpath.JsonPath.read(r.andReturn().getResponse().getContentAsString(),"$."+field);
    }
    private void configure(){
        definition=id();review=id();accepted=id();rejected=id();approve=id();reject=id();mode=id();reason=id();
        jdbc.update("INSERT INTO public.hidra_workflow_type_catalog"
                +"(id,catalog_name,code,active,sort_order,system_defined,created_at,updated_at)"
                +" VALUES (?,'WORKFLOW_ASSIGNMENT_MODE',?,true,0,false,now(),now())",mode,mode);
        jdbc.update("INSERT INTO public.hidra_workflow_type_catalog"
                +"(id,catalog_name,code,active,sort_order,system_defined,created_at,updated_at)"
                +" VALUES (?,'WORKFLOW_REASON',?,true,0,false,now(),now())",reason,reason);
        jdbc.update("INSERT INTO public.hidra_workflow_definition"
                +"(id,code,name_fr,type_id,status,version,created_at,updated_at)"
                +" VALUES (?,?,'Synthetic independent review',?,'ACTIVE',1,now(),now())",
                definition,definition,type);
        for(var s:List.of(new String[] {review,"REVIEW","1"},
                new String[]{accepted,"APPROVED","2"},new String[]{rejected,"REJECTED","3"}))
            jdbc.update("INSERT INTO public.hidra_workflow_step"
                    +"(id,definition_id,code,name_fr,step_order,mandatory,allow_claim,allow_delegation,"
                    +"allow_escalation,created_at,updated_at)"
                    +" VALUES (?,?,?,'Synthetic step',?,true,false,false,false,now(),now())",
                    s[0],definition,s[1],Integer.parseInt(s[2]));
        jdbc.update("INSERT INTO public.hidra_workflow_definition_target_binding"
                +"(id,definition_id,target_module,target_type_id,workflow_purpose_id,active,created_at,updated_at)"
                +" VALUES (? ,?,'topology',?,?,true,now(),now())",id(),definition,type,purpose);
        jdbc.update("INSERT INTO public.hidra_workflow_step_assignment_rule"
                +"(id,definition_id,step_id,assignment_mode_id,actor_id,active,created_at,updated_at)"
                +" VALUES (?,?,?,?,?,true,now(),now())",id(),definition,review,mode,reviewer);
        jdbc.update("INSERT INTO public.hidra_workflow_transition"
                +"(id,definition_id,from_step_id,to_step_id,decision,reason_required,comment_required,created_at,updated_at)"
                +" VALUES (?,?,?,?, 'APPROVE',false,false,now(),now())",approve,definition,review,accepted);
        jdbc.update("INSERT INTO public.hidra_workflow_transition"
                +"(id,definition_id,from_step_id,to_step_id,decision,reason_required,comment_required,created_at,updated_at)"
                +" VALUES (?,?,?,?, 'REJECT',true,false,now(),now())",reject,definition,review,rejected);
    }
    private String discover() {
        return BASE+"/definitions?targetModule=topology&targetTypeId="+type+"&workflowPurposeId="+purpose
                +"&targetId="+digest+"&targetLocator="+locator;
    }
    private String start() throws Exception {
        return value(call("POST",BASE+"/instances",starterToken,
                json("definitionId",definition,"definitionVersion",1,"workflowPurposeId",purpose,
                        "targetModule","topology","targetTypeId",type,"targetId",digest,
                        "targetCodeSnapshot",locator,"currentStepId",review,
                        "startedByActorId",starter,"correlationId",id()))
                .andExpect(status().isOk()),"id");
    }
    private String create(String instance,String assigned) throws Exception {
        return value(call("POST",BASE+"/tasks",starterToken,
                json("instanceId",instance,"stepId",review,"assignedActorId",assigned,
                        "taskLabelSnapshot","Independent review")).andExpect(status().isOk()),"id");
    }
    private String taskVersion(String task) throws Exception {
        return value(call("GET",BASE+"/tasks/"+task,reviewerToken,null).andExpect(status().isOk()),
                "updatedAt");
    }
    private ResultActions decide(String task,String transition,String at,String reasonCode) throws Exception {
        return call("POST",BASE+"/tasks/"+task+"/transitions/"+transition+"/execute",reviewerToken,
                json("expectedTaskUpdatedAt",at,"reasonId",reasonCode,"correlationId",id()));
    }

    @Test void discoveredIndependentReviewerStartsAndApprovesDurableWorkflow() throws Exception {
        call("GET",discover(),starterToken,null).andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(definition))
                .andExpect(jsonPath("$[0].assignmentChoices[0].actorId").value(reviewer));
        String instance=start(), task=create(instance,reviewer),at=taskVersion(task);
        call("GET",BASE+"/tasks?view=assigned",reviewerToken,null).andExpect(status().isOk())
                .andExpect(content().string(org.hamcrest.Matchers.containsString(task)));
        call("GET",BASE+"/tasks/"+task+"/available-actions",reviewerToken,null)
                .andExpect(status().isOk()).andExpect(content().string(org.hamcrest.Matchers.containsString(approve)));
        decide(task,approve,at,null).andExpect(status().isOk())
                .andExpect(jsonPath("$.decision").value("APPROVE"));
        assertEquals("APPROVED",jdbc.queryForObject("SELECT status FROM hidra_workflow_task WHERE id=?",String.class,task));
        assertEquals("COMPLETED",jdbc.queryForObject("SELECT status FROM hidra_workflow_instance WHERE id=?",String.class,instance));
        assertEquals(1,jdbc.queryForObject("SELECT count(*) FROM hidra_workflow_action WHERE instance_id=?",Integer.class,instance));
        assertEquals(1,jdbc.queryForObject("SELECT count(*) FROM hidra_workflow_state_history WHERE instance_id=?",Integer.class,instance));
        call("GET",BASE+"/instances/"+instance+"/timeline",reviewerToken,null).andExpect(status().isOk())
                .andExpect(jsonPath("$[0].decision").value("APPROVE"));
        decide(task,approve,at,null).andExpect(status().is4xxClientError());
        assertEquals(1,jdbc.queryForObject("SELECT count(*) FROM hidra_workflow_action WHERE instance_id=?",Integer.class,instance));
    }
    @Test void configuredRejectRequiresOwnerReasonAndRetainsTerminalEvidence() throws Exception {
        String instance=start(),task=create(instance,reviewer),at=taskVersion(task);
        decide(task,reject,at,null).andExpect(status().is4xxClientError());
        assertEquals(0,jdbc.queryForObject("SELECT count(*) FROM hidra_workflow_action WHERE instance_id=?",Integer.class,instance));
        decide(task,reject,at,reason).andExpect(status().isOk())
                .andExpect(jsonPath("$.decision").value("REJECT"));
        assertEquals("REJECTED",jdbc.queryForObject("SELECT status FROM hidra_workflow_task WHERE id=?",String.class,task));
    }
    @Test void starterCannotAssignItselfAsIndependentCharacteristicApprover() throws Exception {
        String instance=start();
        call("POST",BASE+"/tasks",starterToken,
                json("instanceId",instance,"stepId",review,"assignedActorId",starter))
                .andExpect(status().is4xxClientError());
        assertEquals(0,jdbc.queryForObject("SELECT count(*) FROM hidra_workflow_task WHERE instance_id=?",
                Integer.class,instance));
    }
    @Test void missingProtectedOwnerAdmissionHidesDiscoveryAndDetailEvenFromStarter() throws Exception {
        when(sourceProbe.protectedReadAllowed(eq(TYPE),eq(digest),eq(locator),
                any(WorkflowOwnedTargetLookup.ProtectedRead.class))).thenReturn(false);
        when(sourceProbe.eligibleTarget(eq(TYPE),eq(digest),eq(locator))).thenReturn(Optional.empty());
        call("GET",discover(),starterToken,null).andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(0));
        call("POST",BASE+"/instances",starterToken,
                json("definitionId",definition,"definitionVersion",1,"workflowPurposeId",purpose,
                        "targetModule","topology","targetTypeId",type,"targetId",digest,
                        "targetCodeSnapshot",locator,"currentStepId",review,
                        "startedByActorId",starter)).andExpect(status().is4xxClientError());
    }
    @Test void unsupportedConditionAndCallbackFailClosedBeforeDecisionEvidence() throws Exception {
        for(String expression:new String[]{"condition_expression","target_module_callback"}) {
            jdbc.update("UPDATE public.hidra_workflow_transition SET "+expression+"='unsupported' WHERE id=?",approve);
            call("GET",discover(),starterToken,null).andExpect(status().isOk())
                    .andExpect(jsonPath("$.length()").value(0));
            String instance=start(),task=create(instance,reviewer),at=taskVersion(task);
            decide(task,approve,at,null).andExpect(status().is4xxClientError());
            assertEquals(0,jdbc.queryForObject("SELECT count(*) FROM hidra_workflow_action WHERE instance_id=?",
                    Integer.class,instance));
            jdbc.update("UPDATE public.hidra_workflow_transition SET "+expression+"=null WHERE id=?",approve);
        }
    }
    @Test void unrelatedActorCannotExecuteAssignedApprovalOrReadProtectedHistory() throws Exception {
        String instance=start(),task=create(instance,reviewer),at=taskVersion(task);
        call("GET",BASE+"/tasks/"+task,observerToken,null).andExpect(status().is4xxClientError());
        call("GET",BASE+"/instances/"+instance+"/timeline",observerToken,null)
                .andExpect(status().is4xxClientError());
        call("POST",BASE+"/tasks/"+task+"/transitions/"+approve+"/execute",observerToken,
                json("expectedTaskUpdatedAt",at)).andExpect(status().is4xxClientError());
        assertEquals(0,jdbc.queryForObject("SELECT count(*) FROM hidra_workflow_action WHERE instance_id=?",
                Integer.class,instance));
    }
    @Test @Order(Integer.MAX_VALUE) void developmentProvisionerRequiresExplicitExistingHumanAndIsNeverAutoEnabledInTest() throws Exception {
        assertFalse(context.containsBean("foundationWorkflowDevelopmentProvisioner"));
        var provisioner=new FoundationWorkflowDevelopmentProvisioner(jdbc,actors,humans,reviewer);
        provisioner.run(null);
        provisioner.run(null);
        assertEquals(1,jdbc.queryForObject("SELECT count(*) FROM hidra_workflow_definition "
                +"WHERE id='foundation-dev-link-approval-v1' AND status='ACTIVE'",Integer.class));
        assertEquals(reviewer,jdbc.queryForObject("SELECT actor_id FROM hidra_workflow_step_assignment_rule "
                +"WHERE id='foundation-dev-link-assign-v1'",String.class));
        assertThrows(RuntimeException.class,()->new FoundationWorkflowDevelopmentProvisioner(
                jdbc,actors,humans,observer).run(null));
    }
    @Test void commentCannotMasqueradeAsDecision() throws Exception {
        String instance=start(),task=create(instance,reviewer);
        call("POST",BASE+"/actions",reviewerToken,
                json("instanceId",instance,"taskId",task,"actionType","COMMENT",
                        "decision","APPROVE","commentText","fake decision","actorId",reviewer))
                .andExpect(status().is4xxClientError());
        assertEquals(0,jdbc.queryForObject("SELECT count(*) FROM hidra_workflow_action WHERE instance_id=?",
                Integer.class,instance));
    }
}
