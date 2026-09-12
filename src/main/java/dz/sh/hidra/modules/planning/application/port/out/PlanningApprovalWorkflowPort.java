/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : PlanningApprovalWorkflowPort
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-12
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : planning
 * @Package     : dz.sh.hidra.modules.planning.application.port.out
 *
 * @Description : Isolates planning approval logic from workflow implementation details.
 *
 */
package dz.sh.hidra.modules.planning.application.port.out;

import java.time.Instant;
import java.util.List;
import java.util.Set;

public interface PlanningApprovalWorkflowPort {

    ApprovalContext context(
            String workflowInstanceId,
            String revisionId,
            String actorId,
            Set<String> effectivePermissions
    );

    Execution execute(ExecuteCommand command);

    record ApprovalContext(
            String workflowInstanceId,
            String workflowInstanceStatus,
            String taskId,
            String taskStatus,
            Instant taskUpdatedAt,
            List<Action> actions
    ) { }

    record Action(
            String transitionId,
            String decision,
            boolean reasonRequired,
            boolean commentRequired,
            String requiredPermissionCode,
            boolean permitted
    ) { }

    record ExecuteCommand(
            String workflowInstanceId,
            String revisionId,
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

    record Execution(
            String actionId,
            String taskId,
            String taskStatus,
            String workflowInstanceId,
            String workflowInstanceStatus,
            String transitionId,
            String decision,
            Instant executedAt
    ) { }
}
