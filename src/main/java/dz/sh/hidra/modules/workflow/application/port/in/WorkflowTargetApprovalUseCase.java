/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : WorkflowTargetApprovalUseCase
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-12
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : workflow
 * @Package     : dz.sh.hidra.modules.workflow.application.port.in
 *
 * @Description : Publishes exact target-scoped workflow approval context and execution semantics.
 *
 */
package dz.sh.hidra.modules.workflow.application.port.in;

import java.time.Instant;
import java.util.List;
import java.util.Set;

public interface WorkflowTargetApprovalUseCase {

    ApprovalContext context(
            String instanceId,
            String targetModule,
            String targetTypeId,
            String targetId,
            String actorReference,
            Set<String> effectivePermissions
    );

    Execution execute(ExecuteCommand command);

    record ApprovalContext(
            String instanceId,
            String instanceStatus,
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
            String instanceId,
            String targetModule,
            String targetTypeId,
            String targetId,
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
            String instanceId,
            String instanceStatus,
            String transitionId,
            String decision,
            Instant executedAt
    ) { }

    final class TargetApprovalException extends RuntimeException {

        private final Kind kind;

        public TargetApprovalException(Kind kind, String message) {
            super(message);
            this.kind = kind;
        }

        public Kind kind() {
            return kind;
        }

        public enum Kind {
            NOT_FOUND,
            BOUNDARY,
            DENIED,
            CONFLICT
        }
    }
}
