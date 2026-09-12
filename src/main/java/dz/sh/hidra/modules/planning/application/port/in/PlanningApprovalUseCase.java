/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : PlanningApprovalUseCase
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-12
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : planning
 * @Package     : dz.sh.hidra.modules.planning.application.port.in
 *
 * @Description : Resolves and executes authoritative workflow approval actions for one plan revision.
 *
 */
package dz.sh.hidra.modules.planning.application.port.in;

import java.time.Instant;
import java.util.List;
import java.util.Set;

public interface PlanningApprovalUseCase {

    ApprovalView approval(String revisionId, String actorReference, Set<String> effectivePermissions);

    ExecutionView execute(String revisionId, String transitionId, ExecutionCommand command);

    record ApprovalView(
            String revisionId,
            String revisionStatus,
            String workflowInstanceId,
            String workflowInstanceStatus,
            String currentTaskId,
            Instant currentTaskUpdatedAt,
            List<ActionView> actions
    ) { }

    record ActionView(
            String transitionId,
            String decision,
            boolean reasonRequired,
            boolean commentRequired,
            String requiredPermissionCode,
            boolean permitted
    ) { }

    record ExecutionCommand(
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

    record ExecutionView(
            String revisionId,
            String revisionStatus,
            String workflowInstanceId,
            String workflowInstanceStatus,
            String transitionId,
            String decision,
            String nextTaskId,
            Instant executedAt
    ) { }
}
