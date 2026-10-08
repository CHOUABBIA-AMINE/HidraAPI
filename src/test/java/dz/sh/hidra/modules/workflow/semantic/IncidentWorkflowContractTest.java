/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : IncidentWorkflowContractTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-08
 *
 * @Type        : Class
 * @Layer       : Test
 * @Module      : workflow
 * @Package     : dz.sh.hidra.modules.workflow.semantic
 *
 * @Description : Enforces Incident lifecycle integrity and owner-controlled evidence.
 *
 */
package dz.sh.hidra.modules.workflow.semantic;

import dz.sh.hidra.modules.workflow.application.service.IncidentWorkflowQueryService;
import dz.sh.hidra.modules.workflow.application.port.out.*;
import dz.sh.hidra.modules.workflow.application.port.in.WorkflowQueryUseCase;
import dz.sh.hidra.modules.workflow.domain.model.*;
import dz.sh.hidra.modules.workflow.domain.value.*;
import dz.sh.hidra.modules.identity.application.contract.workflow.WorkflowActorContract;
import java.time.Instant;
import java.util.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
class IncidentWorkflowContractTest {
    final WorkflowInstanceRepositoryPort instances=mock(WorkflowInstanceRepositoryPort.class);
    final WorkflowConfigurationPort config=mock(WorkflowConfigurationPort.class);
    final WorkflowQueryUseCase query=mock(WorkflowQueryUseCase.class);
    final WorkflowActionRepositoryPort actions=mock(WorkflowActionRepositoryPort.class);
    final WorkflowTaskRepositoryPort tasks=mock(WorkflowTaskRepositoryPort.class);
    final WorkflowActorContract actors=mock(WorkflowActorContract.class);
    final IncidentWorkflowQueryService owner=new IncidentWorkflowQueryService(instances,config,query,actions,tasks,actors);
    final Instant at=Instant.parse("2026-10-08T10:00:00Z");
    WorkflowInstance approvedInstance() {
        var instance=mock(WorkflowInstance.class);when(instance.id()).thenReturn("workflow");when(instance.status()).thenReturn(WorkflowInstanceStatus.COMPLETED);
        when(instance.targetModule()).thenReturn("incident");when(instance.targetId()).thenReturn("incident");when(instance.targetTypeId()).thenReturn("type");
        when(instance.workflowPurposeId()).thenReturn("purpose");when(instance.definitionId()).thenReturn("definition");when(instance.completedAt()).thenReturn(at);
        when(instances.findByIdForUpdate("workflow")).thenReturn(Optional.of(instance));
        when(actors.eligibleActor("actor",at)).thenReturn(Optional.of(new WorkflowActorContract.Actor("actor","actor","Actor",null)));
        when(config.requireActiveCatalog("type","WORKFLOW_TARGET_TYPE")).thenReturn(new WorkflowConfigurationPort.Catalog("type","WORKFLOW_TARGET_TYPE","INCIDENT",true));
        when(config.requireActiveCatalog("purpose","WORKFLOW_PURPOSE")).thenReturn(new WorkflowConfigurationPort.Catalog("purpose","WORKFLOW_PURPOSE","INCIDENT_CLOSURE",true));
        when(config.activeBinding("definition","incident","type","purpose")).thenReturn(true);return instance;
    }
    @Test void existenceOrUnrelatedTargetDoesNotAttestClosure() {
        assertFalse(owner.closureApproved("workflow","incident","actor",at));
        var instance=approvedInstance();when(instance.targetId()).thenReturn("other");assertFalse(owner.closureApproved("workflow","incident","actor",at));
    }
    @Test void onlyActualFinalConfiguredApprovalByCloserAttests() {
        approvedInstance();var action=mock(WorkflowAction.class);var task=mock(WorkflowTask.class);
        when(query.timeline("workflow")).thenReturn(List.of(new WorkflowQueryUseCase.TimelineEntry("action","workflow","task","APPROVE","APPROVE","actor","Actor",null,null,1,at)));
        when(actions.findById("action")).thenReturn(Optional.of(action));when(action.taskId()).thenReturn("task");when(action.instanceId()).thenReturn("workflow");
        when(action.actionType()).thenReturn(WorkflowActionType.APPROVE);when(action.decision()).thenReturn(WorkflowDecision.APPROVE);when(action.actorId()).thenReturn("actor");when(action.actedAt()).thenReturn(at);
        when(tasks.findById("task")).thenReturn(Optional.of(task));when(task.instanceId()).thenReturn("workflow");when(task.status()).thenReturn(WorkflowTaskStatus.APPROVED);
        when(task.completedByActorId()).thenReturn("actor");when(task.completedAt()).thenReturn(at);
        assertTrue(owner.closureApproved("workflow","incident","actor",at));
        when(action.actorId()).thenReturn("other");assertFalse(owner.closureApproved("workflow","incident","actor",at));
        when(action.actorId()).thenReturn("actor");when(action.decision()).thenReturn(WorkflowDecision.REJECT);assertFalse(owner.closureApproved("workflow","incident","actor",at));
    }
}
