/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : WorkflowManufacturerSourceAuthorizationPostgresIntegrationTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-30
 *
 * @Type        : Class
 * @Layer       : Test
 * @Module      : workflow
 * @Package     : dz.sh.hidra.modules.workflow.infrastructure.query
 *
 * @Description : Checks the synthetic manufacturer characteristic contract and denial cases.
 *
 */
package dz.sh.hidra.modules.workflow.infrastructure.query;

import dz.sh.hidra.HidraApplication;
import dz.sh.hidra.modules.workflow.application.port.in.WorkflowQueryUseCase;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import static org.junit.jupiter.api.Assertions.*;

@Testcontainers(disabledWithoutDocker=true)
@SpringBootTest(classes=HidraApplication.class,webEnvironment=SpringBootTest.WebEnvironment.MOCK)
@ActiveProfiles("test")
class WorkflowManufacturerSourceAuthorizationPostgresIntegrationTest {
    @Container static final PostgreSQLContainer<?> PG=new PostgreSQLContainer<>("postgres:16");
    @DynamicPropertySource static void source(DynamicPropertyRegistry registry){
        registry.add("spring.datasource.url",PG::getJdbcUrl);
        registry.add("spring.datasource.username",PG::getUsername);
        registry.add("spring.datasource.password",PG::getPassword);
        registry.add("spring.jpa.hibernate.ddl-auto",()->"validate");
        registry.add("spring.flyway.enabled",()->"true");
    }
    @Autowired JdbcTemplate jdbc;
    @Autowired WorkflowQueryUseCase queries;
    private static String id(){return "w-"+UUID.randomUUID();}
    private record Fixture(String instance,String task,String actor) {}
    private Fixture fixture(String module,String targetType,String purposeCode) {
        String suffix=id(),actor=id(),definition=id(),type=id(),purpose=id();
        String from=id(),to=id(),instance=id(),task=id();
        jdbc.update("INSERT INTO hidra_identity_user(id,username,display_name,user_type,status,failed_login_count,created_at,activated_at,updated_at)"
                +" VALUES (?,?,'Synthetic Reviewer','HUMAN','ACTIVE',0,now(),now(),now())",actor,actor);
        for(var row:new String[][]{{type,"WORKFLOW_TARGET_TYPE",targetType},
                {purpose,"WORKFLOW_PURPOSE",purposeCode}})
            jdbc.update("INSERT INTO hidra_workflow_type_catalog(id,catalog_name,code,active,sort_order,system_defined,created_at,updated_at)"
                    +" VALUES (?,?,?,true,0,false,now(),now())",row[0],row[1],row[2]);
        jdbc.update("INSERT INTO hidra_workflow_definition(id,code,name_fr,type_id,status,version,created_at,updated_at)"
                +" VALUES (?,?,'Synthetic review',?,'ACTIVE',1,now(),now())",definition,definition,type);
        for(String step:new String[]{from,to})
            jdbc.update("INSERT INTO hidra_workflow_step(id,definition_id,code,name_fr,step_order,mandatory,allow_claim,allow_delegation,allow_escalation,created_at,updated_at)"
                    +" VALUES (?,?,?,'Review',?,true,true,false,false,now(),now())",step,definition,step,step.equals(from)?1:2);
        jdbc.update("INSERT INTO hidra_workflow_definition_target_binding(id,definition_id,target_module,target_type_id,workflow_purpose_id,active,created_at,updated_at)"
                +" VALUES (?,?,?,?,?,true,now(),now())",id(),definition,module,type,purpose);
        String target=module.equals("simulation")?"a".repeat(64):id();
        jdbc.update("INSERT INTO hidra_workflow_instance(id,definition_id,definition_version,workflow_purpose_id,target_module,target_type_id,target_id,status,current_step_id,started_by_actor_id,started_by_display_name_snapshot,started_at,created_at,updated_at)"
                +" VALUES (?, ?,1, ?, ?, ?, ?, 'STARTED', ?, ?, 'Synthetic Reviewer',now(),now(),now())",
                instance,definition,purpose,module,type,target,from,actor);
        jdbc.update("INSERT INTO hidra_workflow_task(id,instance_id,step_id,status,assigned_actor_id,created_at,updated_at)"
                +" VALUES (?,?,?,'OPEN',?,now(),now())",task,instance,from,actor);
        return new Fixture(instance,task,actor);
    }
    @Test void manufacturerInstanceAndTimelineAreNonDisclosingWithoutNativeSourceAuthorization(){
        var m=fixture("simulation","MANUFACTURER_CHARACTERISTIC_REVISION",
                "MANUFACTURER_CHARACTERISTIC_QUALIFICATION");
        assertThrows(java.util.NoSuchElementException.class,()->queries.instance(m.instance()));
        assertThrows(java.util.NoSuchElementException.class,()->queries.timeline(m.instance()));
        assertThrows(java.util.NoSuchElementException.class,()->queries.task(m.task()));
        assertThrows(java.util.NoSuchElementException.class,()->queries.availableActions(
                m.task(),m.actor(),java.util.Set.of()));
        assertThrows(java.util.NoSuchElementException.class,()->queries.currentTask(m.instance()));
    }
    @Test void workflowNonManufacturerInstanceRetainsExistingVisibility(){
        var m=fixture("planning","PLAN_REVISION","VALIDATION");
        assertEquals(m.instance(),queries.instance(m.instance()).id());
        assertEquals(m.task(),queries.task(m.task()).id());
        assertTrue(queries.timeline(m.instance()).isEmpty());
    }
}
