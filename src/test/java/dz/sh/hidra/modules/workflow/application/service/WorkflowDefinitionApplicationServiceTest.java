/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : WorkflowDefinitionApplicationServiceTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Test
 * @Layer       : Test
 * @Module      : workflow
 * @Package     : dz.sh.hidra.modules.workflow.application.service
 *
 * @Description : Application service tests for workflow definitions.
 *
 */
package dz.sh.hidra.modules.workflow.application.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import dz.sh.hidra.kernel.domain.exception.BusinessRuleViolationException;
import dz.sh.hidra.modules.workflow.application.command.ActivateWorkflowDefinitionCommand;
import dz.sh.hidra.modules.workflow.application.command.CreateWorkflowDefinitionCommand;
import dz.sh.hidra.modules.workflow.application.command.CreateWorkflowStepCommand;
import dz.sh.hidra.modules.workflow.application.dto.WorkflowDefinitionDto;
import dz.sh.hidra.modules.workflow.application.query.ListWorkflowDefinitionsQuery;
import dz.sh.hidra.modules.workflow.domain.policy.WorkflowDefinitionPolicy;
import dz.sh.hidra.modules.workflow.domain.policy.WorkflowTransitionPolicy;
import dz.sh.hidra.modules.workflow.domain.service.WorkflowDefinitionDomainService;
import dz.sh.hidra.modules.workflow.domain.value.WorkflowCode;
import dz.sh.hidra.modules.workflow.domain.value.WorkflowDefinitionStatus;
import dz.sh.hidra.modules.workflow.domain.value.WorkflowLocalizedName;

/**
 * Application service tests for workflow definitions.
 */
class WorkflowDefinitionApplicationServiceTest {

    @Test
    void createWorkflowDefinitionPersistsAndReturnsDraftDefinitionDto() {
        WorkflowApplicationServiceTestFixtures.InMemoryDefinitionRepository repository = new WorkflowApplicationServiceTestFixtures.InMemoryDefinitionRepository();
        WorkflowDefinitionApplicationService service = service(repository);

        WorkflowDefinitionDto dto = service.createWorkflowDefinition(new CreateWorkflowDefinitionCommand(
                WorkflowCode.of("TELEMETRY_READING_VALIDATION"),
                WorkflowApplicationServiceTestFixtures.localizedName(),
                WorkflowApplicationServiceTestFixtures.workflowType()));

        assertEquals("TELEMETRY_READING_VALIDATION", dto.code());
        assertEquals("DRAFT", dto.status());
        assertEquals(1, dto.version());
        assertEquals(1, repository.findAll(null, null, null, WorkflowApplicationServiceTestFixtures.pageRequest()).items().size());
    }

    @Test
    void createWorkflowDefinitionRejectsDuplicateBusinessCode() {
        WorkflowApplicationServiceTestFixtures.InMemoryDefinitionRepository repository = new WorkflowApplicationServiceTestFixtures.InMemoryDefinitionRepository();
        WorkflowDefinitionApplicationService service = service(repository);

        CreateWorkflowDefinitionCommand command = new CreateWorkflowDefinitionCommand(
                WorkflowCode.of("TELEMETRY_READING_VALIDATION"),
                WorkflowApplicationServiceTestFixtures.localizedName(),
                WorkflowApplicationServiceTestFixtures.workflowType());

        service.createWorkflowDefinition(command);

        assertThrows(BusinessRuleViolationException.class, () -> service.createWorkflowDefinition(command));
    }

    @Test
    void createStepAndActivateDefinitionUpdatesStoredAggregate() {
        WorkflowApplicationServiceTestFixtures.InMemoryDefinitionRepository repository = new WorkflowApplicationServiceTestFixtures.InMemoryDefinitionRepository();
        WorkflowDefinitionApplicationService service = service(repository);

        WorkflowDefinitionDto created = service.createWorkflowDefinition(new CreateWorkflowDefinitionCommand(
                WorkflowCode.of("TELEMETRY_READING_VALIDATION"),
                WorkflowApplicationServiceTestFixtures.localizedName(),
                WorkflowApplicationServiceTestFixtures.workflowType()));

        WorkflowDefinitionDto withStep = service.createWorkflowStep(new CreateWorkflowStepCommand(
                dz.sh.hidra.modules.workflow.domain.value.WorkflowDefinitionId.of(created.id()),
                WorkflowCode.of("REVIEW"),
                WorkflowLocalizedName.of(null, "Revue", "Review"),
                0,
                true));

        WorkflowDefinitionDto activated = service.activateWorkflowDefinition(new ActivateWorkflowDefinitionCommand(
                dz.sh.hidra.modules.workflow.domain.value.WorkflowDefinitionId.of(created.id())));

        assertEquals(1, withStep.steps().size());
        assertEquals(WorkflowDefinitionStatus.ACTIVE.name(), activated.status());
    }

    @Test
    void listWorkflowDefinitionsMapsDomainPageToDtoPage() {
        WorkflowApplicationServiceTestFixtures.InMemoryDefinitionRepository repository = new WorkflowApplicationServiceTestFixtures.InMemoryDefinitionRepository();
        WorkflowDefinitionApplicationService service = service(repository);

        service.createWorkflowDefinition(new CreateWorkflowDefinitionCommand(
                WorkflowCode.of("TELEMETRY_READING_VALIDATION"),
                WorkflowApplicationServiceTestFixtures.localizedName(),
                WorkflowApplicationServiceTestFixtures.workflowType()));

        var page = service.listWorkflowDefinitions(new ListWorkflowDefinitionsQuery(
                null,
                null,
                "TELEMETRY",
                WorkflowApplicationServiceTestFixtures.pageRequest()));

        assertEquals(1, page.items().size());
        assertEquals("TELEMETRY_READING_VALIDATION", page.items().get(0).code());
    }

    private WorkflowDefinitionApplicationService service(WorkflowApplicationServiceTestFixtures.InMemoryDefinitionRepository repository) {
        return new WorkflowDefinitionApplicationService(
                repository,
                new WorkflowDefinitionDomainService(
                        new WorkflowDefinitionPolicy(),
                        new WorkflowTransitionPolicy()));
    }
}
