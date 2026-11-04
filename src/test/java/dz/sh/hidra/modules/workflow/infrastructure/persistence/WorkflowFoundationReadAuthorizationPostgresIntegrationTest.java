/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : WorkflowFoundationReadAuthorizationPostgresIntegrationTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-04
 *
 * @Type        : Class
 * @Layer       : Test
 * @Module      : workflow
 * @Package     : dz.sh.hidra.modules.workflow.infrastructure.persistence
 *
 * @Description : Proves actor-scoped Workflow read disclosure against real JWT HTTP and migrated PostgreSQL.
 *
 */
package dz.sh.hidra.modules.workflow.infrastructure.persistence;

import dz.sh.hidra.HidraApplication;
import java.util.UUID;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.*;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Testcontainers(disabledWithoutDocker=true)
@SpringBootTest(classes=HidraApplication.class,webEnvironment=SpringBootTest.WebEnvironment.MOCK)
@ActiveProfiles("test")
@org.springframework.test.annotation.DirtiesContext(
        classMode=org.springframework.test.annotation.DirtiesContext.ClassMode.AFTER_CLASS)
class WorkflowFoundationReadAuthorizationPostgresIntegrationTest {
    @Container static final PostgreSQLContainer<?> PG = new PostgreSQLContainer<>("postgres:16")
            .withDatabaseName("workflow_foundation_read");
    @DynamicPropertySource static void db(DynamicPropertyRegistry r) {
        r.add("spring.datasource.url",PG::getJdbcUrl);
        r.add("spring.datasource.username",PG::getUsername);
        r.add("spring.datasource.password",PG::getPassword);
        r.add("spring.jpa.hibernate.ddl-auto",()->"validate");
        r.add("spring.flyway.enabled",()->"true");
        r.add("hidra.platform.security.jwt.token-issuer",()->"https://workflow-read.synthetic.invalid");
    }
    @Autowired JdbcTemplate jdbc;
    @Autowired PasswordEncoder passwords;
    @Autowired WebApplicationContext context;
    MockMvc http;
    String starter, reviewer, outsider, starterToken, reviewerToken, outsiderToken;
    String definition, step, instance, task;
    private static final String BASE="/api/v1/workflow";
    private static final String PASSWORD="synthetic-workflow-read-password";
    private String id(){return UUID.randomUUID().toString();}

    @BeforeEach void setup() throws Exception {
        http=MockMvcBuilders.webAppContextSetup(context)
                .apply(org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity()).build();
        jdbc.update("UPDATE public.hidra_identity_provider SET status='INACTIVE' WHERE status='ACTIVE'");
        String provider=id();
        jdbc.update("INSERT INTO public.hidra_identity_provider"
                +"(id,code,name,provider_type,sync_enabled,just_in_time_provisioning_enabled,status,created_at,updated_at)"
                +" VALUES (?,?,'Synthetic LOCAL','LOCAL',false,false,'ACTIVE',now(),now())",provider,provider);
        starter=id();reviewer=id();outsider=id();
        starterToken=login(starter);reviewerToken=login(reviewer);outsiderToken=login(outsider);
        definition=id();step=id();instance=id();task=id();
        jdbc.update("INSERT INTO public.hidra_workflow_definition"
                +"(id,code,name_fr,type_id,status,version,created_at,updated_at)"
                +" VALUES (?,?,'Synthetic private process',?,'ACTIVE',1,now(),now())",
                definition,definition,id());
        jdbc.update("INSERT INTO public.hidra_workflow_step"
                +"(id,definition_id,code,name_fr,step_order,mandatory,allow_claim,allow_delegation,allow_escalation,created_at,updated_at)"
                +" VALUES (?,?,'REVIEW','Review',1,true,false,false,false,now(),now())",
                step,definition);
        jdbc.update("INSERT INTO public.hidra_workflow_instance"
                +"(id,definition_id,definition_version,target_module,target_type_id,target_id,status,current_step_id,"
                +"started_by_actor_id,started_by_display_name_snapshot,started_at,created_at,updated_at)"
                +" VALUES (?, ?,1,'incident',?,'synthetic-target','STARTED',?,?, 'Synthetic Starter',now(),now(),now())",
                instance,definition,id(),step,starter);
        jdbc.update("INSERT INTO public.hidra_workflow_task"
                +"(id,instance_id,step_id,status,assigned_actor_id,created_at,updated_at)"
                +" VALUES (?,?,?,'OPEN',?,now(),now())",task,instance,step,reviewer);
        jdbc.update("INSERT INTO public.hidra_workflow_action"
                +"(id,instance_id,task_id,action_type,actor_id,actor_display_name_snapshot,action_sequence,source_system,acted_at)"
                +" VALUES (?,?,?,'COMMENT',?,'Synthetic Starter',1,'HIDRA_API',now())",
                id(),instance,task,starter);
    }
    private String login(String user) throws Exception {
        jdbc.update("INSERT INTO public.hidra_identity_user"
                +"(id,username,display_name,user_type,status,failed_login_count,created_at,updated_at)"
                +" VALUES (?,?,'Synthetic Actor','HUMAN','ACTIVE',0,now(),now())",user,user);
        jdbc.update("INSERT INTO public.hidra_identity_local_credential"
                +"(id,user_id,password_hash,credential_status,created_at,updated_at)"
                +" VALUES (?,?,?,'ACTIVE',now(),now())",id(),user,passwords.encode(PASSWORD));
        String role=jdbc.queryForList("SELECT id FROM public.hidra_identity_role WHERE code='HIDRA_ADMIN'",
                String.class).stream().findFirst().orElse(null);
        if(role==null){
            role=id();
            jdbc.update("INSERT INTO public.hidra_identity_role"
                    +"(id,code,role_type,status,created_at,updated_at)"
                    +" VALUES (?,'HIDRA_ADMIN','ADMIN','ACTIVE',now(),now())",role);
        }
        jdbc.update("INSERT INTO public.hidra_identity_user_role_grant"
                +"(id,user_id,role_id,scope_type,valid_from,status,created_at)"
                +" VALUES (?,?,?,'GLOBAL',now()-interval '1 hour','ACTIVE',now())",id(),user,role);
        return com.jayway.jsonpath.JsonPath.read(http.perform(post("/api/v1/identity/authentication/login")
                .contentType("application/json")
                .content("{\"providerType\":\"LOCAL\",\"principal\":\""+user
                        +"\",\"credentials\":\""+PASSWORD+"\"}"))
                .andExpect(status().isOk()).andReturn().getResponse().getContentAsString(),"$.accessToken");
    }
    private ResultActions get(String path,String token) throws Exception {
        return http.perform(org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get(path)
                .header("Authorization","Bearer "+token));
    }
    @Test void starterAndAssignedReviewerReadOnlyTheirAuthorizedWorkflow() throws Exception {
        get(BASE+"/instances/"+instance,starterToken).andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(instance));
        get(BASE+"/tasks/"+task,reviewerToken).andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(task));
        get(BASE+"/instances/"+instance+"/timeline",reviewerToken).andExpect(status().isOk())
                .andExpect(jsonPath("$[0].actionType").value("COMMENT"));
        get(BASE+"/tasks?view=assigned&page=0&size=1",reviewerToken).andExpect(status().isOk())
                .andExpect(jsonPath("$.totalElements").value(1));
        get(BASE+"/tasks?view=assigned",starterToken).andExpect(status().isOk())
                .andExpect(jsonPath("$.totalElements").value(0));
    }
    @Test void unrelatedAdministratorCannotReadDetailHistoryActionsOrCounts() throws Exception {
        for(String path:new String[]{BASE+"/tasks/"+task,BASE+"/instances/"+instance,
                BASE+"/instances/"+instance+"/timeline",BASE+"/tasks/"+task+"/available-actions"})
            get(path,outsiderToken).andExpect(status().is4xxClientError());
        get(BASE+"/tasks?view=all",outsiderToken).andExpect(status().isOk())
                .andExpect(jsonPath("$.totalElements").value(0));
        get(BASE+"/tasks?view=all&page=20&size=1",outsiderToken).andExpect(status().isOk())
                .andExpect(jsonPath("$.totalElements").value(0));
    }
    @Test void currentActorRevocationImmediatelyRemovesReadDisclosure() throws Exception {
        get(BASE+"/tasks/"+task,reviewerToken).andExpect(status().isOk());
        jdbc.update("UPDATE public.hidra_identity_user SET status='INACTIVE' WHERE id=?",reviewer);
        get(BASE+"/tasks/"+task,reviewerToken).andExpect(status().is4xxClientError());
        get(BASE+"/instances/"+instance+"/timeline",reviewerToken).andExpect(status().is4xxClientError());
        get(BASE+"/tasks?view=all",reviewerToken).andExpect(status().is4xxClientError());
    }
    @Test void recognizedNativeTopologyTargetRequiresActualOwnerAdmission() throws Exception {
        String type=jdbc.queryForObject("SELECT id FROM public.hidra_workflow_type_catalog "
                +"WHERE catalog_name='WORKFLOW_TARGET_TYPE' AND code='INSTALLED_CHARACTERISTIC_LINK_REVISION'",
                String.class);
        String nativeInstance=id();
        jdbc.update("INSERT INTO public.hidra_workflow_instance"
                +"(id,definition_id,definition_version,target_module,target_type_id,target_id,target_code_snapshot,"
                +"status,current_step_id,started_by_actor_id,started_by_display_name_snapshot,started_at,created_at,updated_at)"
                +" VALUES (?,?,1,'topology',?,?,'nonexistent-source-locator','STARTED',?,?,"
                +"'Synthetic Starter',now(),now(),now())",
                nativeInstance,definition,type,"b".repeat(64),step,starter);
        get(BASE+"/instances/"+nativeInstance,starterToken).andExpect(status().is4xxClientError());
        get(BASE+"/instances/"+nativeInstance+"/timeline",starterToken).andExpect(status().is4xxClientError());
    }
    @Test void currentTaskAndKnownUnknownIdsNeverBypassMembership() throws Exception {
        get(BASE+"/instances/"+instance,reviewerToken).andExpect(status().isOk());
        get(BASE+"/instances/"+id(),reviewerToken).andExpect(status().is4xxClientError());
        get(BASE+"/tasks/"+id(),reviewerToken).andExpect(status().is4xxClientError());
        // The domain-referenced current task is constrained to the same reader membership.
        var query=context.getBean(dz.sh.hidra.modules.workflow.application.port.in.WorkflowQueryUseCase.class);
        org.springframework.security.core.context.SecurityContextHolder.clearContext();
        assertThrows(java.util.NoSuchElementException.class,()->query.authorizedCurrentTask(instance));
        assertEquals(task,query.currentTask(instance).id());
    }
}
