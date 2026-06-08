/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : WorkflowDefinitionControllerTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Test
 * @Layer       : Test
 * @Module      : workflow
 * @Package     : dz.sh.hidra.modules.workflow.api.rest
 *
 * @Description : Tests workflow definition REST controller delegation.
 *
 */
package dz.sh.hidra.modules.workflow.api.rest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

import java.util.concurrent.atomic.AtomicReference;

import org.junit.jupiter.api.Test;

import dz.sh.hidra.modules.workflow.api.rest.controller.WorkflowDefinitionController;
import dz.sh.hidra.modules.workflow.api.rest.mapper.WorkflowRestMapper;
import dz.sh.hidra.modules.workflow.api.rest.response.WorkflowDefinitionResponse;
import dz.sh.hidra.modules.workflow.application.command.CreateWorkflowDefinitionCommand;
import dz.sh.hidra.modules.workflow.application.port.in.ActivateWorkflowDefinitionUseCase;
import dz.sh.hidra.modules.workflow.application.port.in.CreateWorkflowDefinitionUseCase;
import dz.sh.hidra.modules.workflow.application.port.in.CreateWorkflowStepUseCase;
import dz.sh.hidra.modules.workflow.application.port.in.CreateWorkflowTransitionUseCase;
import dz.sh.hidra.modules.workflow.application.port.in.DeactivateWorkflowDefinitionUseCase;
import dz.sh.hidra.modules.workflow.application.port.in.GetWorkflowDefinitionUseCase;
import dz.sh.hidra.modules.workflow.application.port.in.ListWorkflowDefinitionsUseCase;

/**
 * Tests workflow definition REST controller delegation.
 */
class WorkflowDefinitionControllerTest {

    @Test
    void createDefinitionDelegatesMappedCommandToUseCaseAndReturnsResponse() {
        AtomicReference<Object> captured = new AtomicReference<>();

        WorkflowDefinitionController controller = controller(
                WorkflowRestApiTestFixtures.recordingUseCase(
                        CreateWorkflowDefinitionUseCase.class,
                        WorkflowRestApiTestFixtures.definitionDto(),
                        captured));

        WorkflowDefinitionResponse body = controller
                .createDefinition(WorkflowRestApiTestFixtures.createDefinitionRequest())
                .getBody();

        assertInstanceOf(CreateWorkflowDefinitionCommand.class, captured.get());
        assertEquals("TELEMETRY_READING_VALIDATION", ((CreateWorkflowDefinitionCommand) captured.get()).code().value());
        assertEquals("definition-001", body.id());
    }

    @Test
    void listDefinitionsReturnsMappedPageResponse() {
        WorkflowDefinitionController controller = controller(
                WorkflowRestApiTestFixtures.fixedUseCase(
                        CreateWorkflowDefinitionUseCase.class,
                        WorkflowRestApiTestFixtures.definitionDto()));

        var body = controller
                .listDefinitions(
                        "workflow-type-telemetry-validation",
                        "TELEMETRY_VALIDATION",
                        "ACTIVE",
                        "telemetry",
                        0,
                        20,
                        "code",
                        "ASC")
                .getBody();

        assertEquals(1, body.items().size());
        assertEquals("definition-001", body.items().get(0).id());
    }

    private WorkflowDefinitionController controller(CreateWorkflowDefinitionUseCase createUseCase) {
        return new WorkflowDefinitionController(
                createUseCase,
                WorkflowRestApiTestFixtures.fixedUseCase(ActivateWorkflowDefinitionUseCase.class, WorkflowRestApiTestFixtures.definitionDto()),
                WorkflowRestApiTestFixtures.fixedUseCase(DeactivateWorkflowDefinitionUseCase.class, WorkflowRestApiTestFixtures.definitionDto()),
                WorkflowRestApiTestFixtures.fixedUseCase(GetWorkflowDefinitionUseCase.class, WorkflowRestApiTestFixtures.definitionDto()),
                WorkflowRestApiTestFixtures.fixedUseCase(ListWorkflowDefinitionsUseCase.class, WorkflowRestApiTestFixtures.definitionPage()),
                WorkflowRestApiTestFixtures.fixedUseCase(CreateWorkflowStepUseCase.class, WorkflowRestApiTestFixtures.definitionDto()),
                WorkflowRestApiTestFixtures.fixedUseCase(CreateWorkflowTransitionUseCase.class, WorkflowRestApiTestFixtures.definitionDto()),
                new WorkflowRestMapper());
    }
}
