/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : RiskAssessmentApprovalServiceTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-08
 *
 * @Type        : Class
 * @Layer       : Test
 * @Module      : workflow
 * @Package     : dz.sh.hidra.modules.workflow.application.service
 *
 * @Description : Enforces governed Risk owner boundaries.
 *
 */
package dz.sh.hidra.modules.workflow.application.service;

import dz.sh.hidra.modules.workflow.application.contract.risk.RiskAssessmentApprovalContract;
import dz.sh.hidra.modules.workflow.application.port.out.*;
import dz.sh.hidra.modules.workflow.application.port.in.ExecuteWorkflowTransitionUseCase;
import dz.sh.hidra.modules.workflow.application.command.ExecuteWorkflowTransitionCommand;
import dz.sh.hidra.modules.workflow.application.dto.WorkflowTransitionExecutionDto;
import dz.sh.hidra.modules.workflow.domain.model.*;
import dz.sh.hidra.modules.workflow.domain.value.*;
import dz.sh.hidra.modules.identity.application.contract.risk.RiskActorContract;
import java.time.Instant;
import java.util.*;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

class RiskAssessmentApprovalServiceTest {
    final Instant at=Instant.parse("2026-10-08T00:00:00Z");
    final WorkflowInstanceRepositoryPort instances=mock(WorkflowInstanceRepositoryPort.class);
    final WorkflowTaskRepositoryPort tasks=mock(WorkflowTaskRepositoryPort.class);
    final WorkflowActionRepositoryPort actions=mock(WorkflowActionRepositoryPort.class);
    final WorkflowConfigurationPort config=mock(WorkflowConfigurationPort.class);
    final ExecuteWorkflowTransitionUseCase transitions=mock(ExecuteWorkflowTransitionUseCase.class);
    final RiskActorContract actors=mock(RiskActorContract.class);
    final RiskAssessmentApprovalService service=new RiskAssessmentApprovalService(instances,tasks,actions,config,transitions,actors);
    RiskAssessmentApprovalContract.Request request() {return new RiskAssessmentApprovalContract.Request("a","w","t","transition","review",at,null,null,null,null);}
    void setup(String target,WorkflowDecision reviewDecision) {
        when(actors.currentActor(any())).thenReturn(new RiskActorContract.Actor("actor","username","Actor"));
        var instance=mock(WorkflowInstance.class);when(instance.id()).thenReturn("w");when(instance.targetModule()).thenReturn("risk");when(instance.targetId()).thenReturn(target);when(instance.targetTypeId()).thenReturn("type");
        when(instances.findByIdForUpdate("w")).thenReturn(Optional.of(instance));
        when(config.requireActiveCatalog("type","WORKFLOW_TARGET_TYPE")).thenReturn(new WorkflowConfigurationPort.Catalog("type","WORKFLOW_TARGET_TYPE","RISK_ASSESSMENT",true));
        var task=mock(WorkflowTask.class);when(task.id()).thenReturn("t");when(task.instanceId()).thenReturn("w");when(tasks.findByIdForUpdate("t")).thenReturn(Optional.of(task));
        var review=mock(WorkflowAction.class);when(review.id()).thenReturn("review");when(review.instanceId()).thenReturn("w");when(review.decision()).thenReturn(reviewDecision);when(review.actedAt()).thenReturn(at.minusSeconds(5));when(review.actorId()).thenReturn("reviewer");when(review.actorDisplayNameSnapshot()).thenReturn("Reviewer");when(review.actionSequence()).thenReturn(1L);
        when(actions.findById("review")).thenReturn(Optional.of(review));
    }
    @Test void wrongTargetAndCommentReviewNeverExecuteTransition() {
        setup("another",WorkflowDecision.APPROVE);assertThatThrownBy(() -> service.approve(request())).hasMessageContaining("exact Risk assessment");
        setup("a",WorkflowDecision.COMMENT);assertThatThrownBy(() -> service.approve(request())).hasMessageContaining("actual prior");verifyNoInteractions(transitions);
    }
    @Test void livePermissionDenialPropagatesWithoutInventingPermissions() {
        setup("a",WorkflowDecision.APPROVE);when(transitions.execute(any())).thenThrow(new SecurityException("Configured permission denied"));
        assertThatThrownBy(() -> service.approve(request())).isInstanceOf(SecurityException.class);
        var cap=org.mockito.ArgumentCaptor.forClass(ExecuteWorkflowTransitionCommand.class);verify(transitions).execute(cap.capture());
        assertThat(cap.getValue().effectivePermissions()).isEmpty();assertThat(cap.getValue().actorId()).isEqualTo("actor");
    }
    @Test void actualFinalApprovalProducesCanonicalMetadataAndNonfinalDecisionRollsBack() {
        setup("a",WorkflowDecision.APPROVE);
        when(transitions.execute(any())).thenReturn(new WorkflowTransitionExecutionDto("action","t","COMPLETED","w","COMPLETED","transition","APPROVE",null,null,at));
        var action=mock(WorkflowAction.class);when(action.id()).thenReturn("action");when(action.taskId()).thenReturn("t");when(action.instanceId()).thenReturn("w");when(action.actorId()).thenReturn("actor");when(action.decision()).thenReturn(WorkflowDecision.APPROVE);when(action.actedAt()).thenReturn(at);when(action.actionSequence()).thenReturn(2L);
        when(actions.findById("action")).thenReturn(Optional.of(action));
        var result=service.approve(request());assertThat(result.approvedAt()).isEqualTo(at);assertThat(result.reviewerId()).isEqualTo("reviewer");
        when(transitions.execute(any())).thenReturn(new WorkflowTransitionExecutionDto("action","t","COMPLETED","w","IN_PROGRESS","transition","APPROVE",null,null,at));
        assertThatThrownBy(() -> service.approve(request())).hasMessageContaining("configured final");
    }
}
