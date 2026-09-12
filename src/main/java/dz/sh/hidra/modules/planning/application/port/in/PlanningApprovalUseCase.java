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
 * @Description : Exposes authoritative planning revision approval context and action execution.
 *
 */
package dz.sh.hidra.modules.planning.application.port.in;

import java.time.Instant;
import java.util.List;
import java.util.Set;

public interface PlanningApprovalUseCase {

    ApprovalView approval(String revisionId, String actorId, Set<String> effectivePermissions);

    ExecutionView execute(String revisionId, String transitionId, ExecuteCommand command);

    record ApprovalView(
            String revisionId,
            String revisionStatus,
            boolean underApproval,
            String workflowInstanceId,
            String workflowInstanceStatus,
            String taskId,
            String taskStatus,
            Instant taskUpdatedAt,
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

    record ExecuteCommand(
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
            String taskId,
            String taskStatus,
            String transitionId,
            String decision,
            Instant executedAt
    ) { }
}
