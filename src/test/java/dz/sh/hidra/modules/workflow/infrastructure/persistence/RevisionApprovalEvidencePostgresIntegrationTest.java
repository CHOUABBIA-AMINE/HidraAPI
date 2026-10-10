/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : RevisionApprovalEvidencePostgresIntegrationTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-10
 *
 * @Type        : Class
 * @Layer       : Test
 * @Module      : workflow
 * @Package     : dz.sh.hidra.modules.workflow.infrastructure.persistence
 *
 * @Description : Uses actual full-chain PostgreSQL, owner targets and authenticated configured Workflow transitions.
 *
 */
package dz.sh.hidra.modules.workflow.infrastructure.persistence;

import dz.sh.hidra.HidraApplication;
import dz.sh.hidra.modules.workflow.application.contract.target.RevisionApprovalEvidenceContract;
import dz.sh.hidra.modules.workflow.application.command.*;
import dz.sh.hidra.modules.workflow.application.service.WorkflowApplicationService;
import dz.sh.hidra.modules.workflow.application.port.in.ExecuteWorkflowTransitionUseCase;
import dz.sh.hidra.modules.workflow.application.port.out.WorkflowTaskRepositoryPort;
import dz.sh.hidra.modules.workflow.application.dto.WorkflowTransitionExecutionDto;
import dz.sh.hidra.modules.custody.application.port.out.CustodyGasFluidRevisionRepositoryPort;
import dz.sh.hidra.modules.custody.application.contract.simulation.SimulationProductCandidateContract;
import dz.sh.hidra.modules.custody.domain.model.CustodyGasFluidRevision;
import dz.sh.hidra.modules.custody.domain.model.CustodyGasFluidRevisionTest;
import dz.sh.hidra.modules.custody.domain.model.CustodyGasFluidRevision.*;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import static org.junit.jupiter.api.Assertions.*;

@Testcontainers(disabledWithoutDocker = true)
@SpringBootTest(classes = HidraApplication.class, webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@ActiveProfiles("test")
public class RevisionApprovalEvidencePostgresIntegrationTest {
    @Container public static final PostgreSQLContainer<?> POSTGRES = new PostgreSQLContainer<>("postgres:16");
    @DynamicPropertySource static void database(DynamicPropertyRegistry r) {
        r.add("spring.datasource.url", POSTGRES::getJdbcUrl); r.add("spring.datasource.username", POSTGRES::getUsername);
        r.add("spring.datasource.password", POSTGRES::getPassword); r.add("spring.jpa.hibernate.ddl-auto", () -> "validate");
        r.add("spring.flyway.enabled", () -> "true");
    }
    @Autowired JdbcTemplate jdbc;
    @Autowired WorkflowApplicationService application;
    @Autowired ExecuteWorkflowTransitionUseCase transitions;
    @Autowired WorkflowTaskRepositoryPort tasks;
    @Autowired RevisionApprovalEvidenceContract evidence;
    @Autowired CustodyGasFluidRevisionRepositoryPort revisions;
    @Autowired SimulationProductCandidateContract products;
    @AfterEach void clearSecurity() { SecurityContextHolder.clearContext(); }

    public static CustodyGasFluidRevision fluid(JdbcTemplate jdbc, SimulationProductCandidateContract products, String revision,
            String definition, String type, String purpose) {
        String product = "product-" + UUID.randomUUID(); Instant at = CustodyGasFluidRevisionTest.AT;
        jdbc.update("INSERT INTO hidra_custody_catalog_entry(id,catalog_name,code,active,sort_order,system_defined,created_at,updated_at) "
                + "VALUES (?,'PRODUCT',?,true,0,false,?,?)", product, product, Timestamp.from(at), Timestamp.from(at));
        var p = products.resolve(product).orElseThrow(); var v = CustodyGasFluidRevisionTest.fixture(revision);
        return new CustodyGasFluidRevision("gas-" + UUID.randomUUID(), revision, v.recordedAt(), v.effectiveFrom(), null,
                v.origin(), v.evidenceReference(), new ProductSnapshot(p.id(), p.catalogName(), p.code(), p.active(), p.createdAt(), p.updatedAt()),
                v.productKind(), v.method(), v.components(), new GovernanceBinding(definition, 1, type, purpose));
    }
    public static WorkflowTransitionExecutionDto approve(JdbcTemplate jdbc, WorkflowApplicationService application,
            ExecuteWorkflowTransitionUseCase transitions, WorkflowTaskRepositoryPort tasks, String module,
            String definition, String type, String purpose, String digest) {
        String actor = "actor-" + UUID.randomUUID();
        jdbc.update("INSERT INTO hidra_identity_user(id,username,display_name,user_type,status,failed_login_count,created_at,activated_at,updated_at) "
                + "VALUES (?,?,'Synthetic Reviewer','HUMAN','ACTIVE',0,now(),now(),now())", actor, actor);
        String typeCode = module.equals("custody") ? "GAS_FLUID_REVISION" : "EQUIPMENT_PARAMETER_REVISION";
        String purposeCode = module.equals("custody") ? "GAS_FLUID_INPUT_QUALIFICATION" : "EQUIPMENT_PARAMETER_QUALIFICATION";
        for (var c : List.of(new String[]{type, "WORKFLOW_TARGET_TYPE", typeCode}, new String[]{purpose, "WORKFLOW_PURPOSE", purposeCode}))
            jdbc.update("INSERT INTO hidra_workflow_type_catalog(id,catalog_name,code,active,sort_order,system_defined,created_at,updated_at) "
                    + "VALUES (?,?,?,true,0,false,now(),now()) ON CONFLICT(id) DO NOTHING", c[0], c[1], c[2]);
        jdbc.update("INSERT INTO hidra_workflow_definition(id,code,name_fr,type_id,status,version,created_at,updated_at) "
                + "VALUES (?,?,'Synthetic qualification fixture',?,'ACTIVE',1,now(),now()) ON CONFLICT(id) DO NOTHING", definition, definition, type);
        String from = definition + "-from"; String to = definition + "-to";
        for (var s : List.of(new Object[]{from, 1}, new Object[]{to, 2}))
            jdbc.update("INSERT INTO hidra_workflow_step(id,definition_id,code,name_fr,step_order,mandatory,allow_claim,allow_delegation,allow_escalation,created_at,updated_at) "
                    + "VALUES (?,?,?,'Synthetic review',?,true,false,false,false,now(),now()) ON CONFLICT(id) DO NOTHING", s[0], definition, s[0], s[1]);
        jdbc.update("INSERT INTO hidra_workflow_definition_target_binding(id,definition_id,target_module,target_type_id,workflow_purpose_id,active,created_at,updated_at) "
                + "VALUES (?,?,?,?,?,true,now(),now()) ON CONFLICT(id) DO NOTHING", definition + "-binding", definition, module, type, purpose);
        jdbc.update("INSERT INTO hidra_workflow_transition(id,definition_id,from_step_id,to_step_id,decision,reason_required,comment_required,created_at,updated_at) "
                + "VALUES (?,?,?,?,'APPROVE',false,false,now(),now()) ON CONFLICT(id) DO NOTHING", definition + "-approve", definition, from, to);
        SecurityContextHolder.getContext().setAuthentication(UsernamePasswordAuthenticationToken.authenticated(actor, "synthetic test principal", List.of()));
        var i = application.startWorkflowInstance(new StartWorkflowInstanceCommand(definition, 1, purpose, module, type, digest,
                null, null, from, actor, null, "ignored caller display", null, "synthetic test"));
        var t = application.createWorkflowTask(new CreateWorkflowTaskCommand(i.id(), from, actor, null, null, null, null, null, null, null, null, "Synthetic review"));
        var persisted = tasks.findById(t.id()).orElseThrow();
        return transitions.execute(new ExecuteWorkflowTransitionCommand(t.id(), definition + "-approve", persisted.updatedAt(),
                null, null, null, "synthetic test", actor, null, "ignored caller display", Set.of()));
    }
    @Test void actualTransitionQualifiesOnlyExactDigestAndWithdrawalMakesItUnavailable() {
        String suffix = UUID.randomUUID().toString(); String def = "def-" + suffix; String type = "type-" + suffix; String purpose = "purpose-" + suffix;
        var v = fluid(jdbc, products, "r1", def, type, purpose); revisions.append(v);
        var s = revisions.findStored(v.sourceId(), v.revisionId()).orElseThrow();
        var a = approve(jdbc, application, transitions, tasks, "custody", def, type, purpose, s.sha256());
        var request = new RevisionApprovalEvidenceContract.Request("custody", s.sha256(), def, 1, type, purpose,
                a.instanceId(), a.taskId(), a.actionId(), Instant.now());
        var actual = evidence.resolve(request).orElseThrow(); assertEquals("Synthetic Reviewer", actual.actorDisplayName());
        assertEquals(tasks.findById(a.taskId()).orElseThrow().completedAt(), actual.actedAt());
        assertTrue(evidence.resolve(new RevisionApprovalEvidenceContract.Request("custody", "b".repeat(64), def, 1, type, purpose,
                a.instanceId(), a.taskId(), a.actionId(), Instant.now())).isEmpty());
        jdbc.update("UPDATE hidra_workflow_definition_target_binding SET active=false WHERE id=?", def + "-binding");
        assertTrue(evidence.resolve(request).isEmpty());
    }
}
