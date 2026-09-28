/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : PlanningWorkflowContract
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-28
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
