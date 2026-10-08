/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : HseWorkflowReferenceContractTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-08
 *
 * @Type        : Class
 * @Layer       : Test
 * @Module      : workflow
 * @Package     : dz.sh.hidra.modules.workflow.semantic
 *
 * @Description : Enforces HSE-owned lifecycle integrity through owner-controlled references.
 *
 */
package dz.sh.hidra.modules.workflow.semantic;

import dz.sh.hidra.modules.workflow.application.service.HseWorkflowReferenceQueryService;
import dz.sh.hidra.modules.workflow.application.port.out.*;
import dz.sh.hidra.modules.workflow.domain.model.WorkflowInstance;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
class HseWorkflowReferenceContractTest {
    final WorkflowTaskRepositoryPort tasks=mock(WorkflowTaskRepositoryPort.class);
    final WorkflowInstanceRepositoryPort instances=mock(WorkflowInstanceRepositoryPort.class);
    final WorkflowConfigurationPort configuration=mock(WorkflowConfigurationPort.class);
    final WorkflowInstance instance=mock(WorkflowInstance.class);
    final HseWorkflowReferenceQueryService service=new HseWorkflowReferenceQueryService(instances,configuration,tasks);
    void valid() {
        when(instances.findById("workflow")).thenReturn(Optional.of(instance));when(instance.id()).thenReturn("workflow");
        when(instance.targetModule()).thenReturn("hse");when(instance.targetId()).thenReturn("target");when(instance.targetTypeId()).thenReturn("type");
        when(instance.workflowPurposeId()).thenReturn("purpose");when(instance.definitionId()).thenReturn("definition");
        when(configuration.requireActiveCatalog("type","WORKFLOW_TARGET_TYPE")).thenReturn(new WorkflowConfigurationPort.Catalog("type","WORKFLOW_TARGET_TYPE","HSE_CASE",true));
        when(configuration.requireActiveCatalog("purpose","WORKFLOW_PURPOSE")).thenReturn(new WorkflowConfigurationPort.Catalog("purpose","WORKFLOW_PURPOSE","CONFIGURED_PURPOSE",true));
        when(configuration.activeBinding("definition","hse","type","purpose")).thenReturn(true);
    }
    @Test void validExactContextIsAccepted() {valid();assertTrue(service.caseMatches("workflow","target"));}
    @Test void mereExistenceAndWrongModuleAreInsufficient() {valid();when(instance.targetModule()).thenReturn("other");assertFalse(service.caseMatches("workflow","target"));}
    @Test void differentTargetIsRejected() {valid();assertFalse(service.caseMatches("workflow","other"));}
    @Test void wrongTypeOrInactiveCatalogIsRejected() {valid();when(configuration.requireActiveCatalog("type","WORKFLOW_TARGET_TYPE")).thenReturn(new WorkflowConfigurationPort.Catalog("type","WORKFLOW_TARGET_TYPE","OTHER",true));assertFalse(service.caseMatches("workflow","target"));
        when(configuration.requireActiveCatalog("type","WORKFLOW_TARGET_TYPE")).thenReturn(new WorkflowConfigurationPort.Catalog("type","WORKFLOW_TARGET_TYPE","HSE_CASE",false));assertFalse(service.caseMatches("workflow","target"));}
    @Test void missingBindingIsRejected() {valid();when(configuration.activeBinding("definition","hse","type","purpose")).thenReturn(false);assertFalse(service.caseMatches("workflow","target"));}
    @Test void unavailableConfigurationFailsClosed() {valid();when(configuration.requireActiveCatalog("type","WORKFLOW_TARGET_TYPE")).thenThrow(new IllegalStateException("unavailable"));assertThrows(IllegalStateException.class,() -> service.caseMatches("workflow","target"));}
    @Test void missingInstanceIsRejected() {when(instances.findById("missing")).thenReturn(Optional.empty());assertFalse(service.caseMatches("missing","target"));}
    @Test void taskMustResolveItsActualHseContext() {
        valid();var task=mock(dz.sh.hidra.modules.workflow.domain.model.WorkflowTask.class);
        when(task.id()).thenReturn("task");when(task.instanceId()).thenReturn("workflow");when(tasks.findById("task")).thenReturn(Optional.of(task));
        assertTrue(service.taskMatches("task","target","capa"));assertFalse(service.taskMatches("task","wrong","capa"));
        when(instance.targetId()).thenReturn("capa");when(configuration.requireActiveCatalog("type","WORKFLOW_TARGET_TYPE")).thenReturn(new WorkflowConfigurationPort.Catalog("type","WORKFLOW_TARGET_TYPE","HSE_CAPA",true));
        assertTrue(service.taskMatches("task","target","capa"));when(configuration.activeBinding("definition","hse","type","purpose")).thenReturn(false);assertFalse(service.taskMatches("task","target","capa"));
    }
    @Test void missingTaskIsRejected() {when(tasks.findById("missing")).thenReturn(Optional.empty());assertFalse(service.taskMatches("missing","case","capa"));}
}
