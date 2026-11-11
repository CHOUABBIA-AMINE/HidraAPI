/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : PlanningWorkflowContract
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-11
 *
 * @Type        : Interface
 * @Layer       : Application Contract
 * @Module      : workflow
 * @Package     : dz.sh.hidra.modules.workflow.application.contract.planning
 *
 * @Description : Deliberate cross-module Workflow contract exported to Planning approval orchestration.
 *
 */
package dz.sh.hidra.modules.workflow.application.contract.planning;

import java.time.Instant;
import java.util.List;
import java.util.Set;

/**
 * Exposes only the workflow capabilities and data required by Planning approval orchestration.
 */
public interface PlanningWorkflowContract {

    InstanceView instance(String id);

    TaskView currentTask(String instanceId);

    List<ActionView> availableActions(
            String taskId,
            String actorReference,
            Set<String> effectivePermissions
    );

    TransitionResult execute(TransitionCommand command);

    default RevisionStartResult startRevisionApproval(RevisionStartCommand command) {
        throw new UnsupportedOperationException("Planning revision approval start requires configured Workflow.");
    }

    /** Internal Workflow CAS failures cross the module boundary as this exported contract type. */
    final class TransitionConflict extends RuntimeException {
        private static final long serialVersionUID = 1L;

        public TransitionConflict(RuntimeException cause) {
            super("Planning approval task version is no longer current.", cause);
        }
    }

    record RevisionStartCommand(String revisionId, String definitionId, int definitionVersion,
            String targetTypeId, String workflowPurposeId, String reviewerActorId,
            String starterActorId, String correlationId) { }

    record RevisionStartResult(String instanceId, String taskId, Instant taskUpdatedAt) { }

    record InstanceView(
            String id,
            String targetModule,
            String targetId,
            String status
    ) { }

    record TaskView(
            String id,
            Instant updatedAt
    ) { }

    record ActionView(
            String transitionId,
            String decision,
            boolean reasonRequired,
            boolean commentRequired,
            String requiredPermissionCode,
            boolean permitted
    ) { }

    record TransitionCommand(
            String taskId,
            String transitionId,
            Instant expectedTaskUpdatedAt,
            String reasonId,
            String decisionNote,
            String commentText,
            String correlationId,
            String actorId,
            String actorUsername,
            String actorDisplayName,
            Set<String> effectivePermissions
    ) { }

    record TransitionResult(
            String instanceId,
            String instanceStatus,
            String transitionId,
            String decision,
            String nextTaskId,
            Instant executedAt
    ) { }
}
