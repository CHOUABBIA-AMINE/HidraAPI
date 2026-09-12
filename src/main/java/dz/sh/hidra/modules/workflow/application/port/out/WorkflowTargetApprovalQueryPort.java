/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : WorkflowTargetApprovalQueryPort
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-12
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : workflow
 * @Package     : dz.sh.hidra.modules.workflow.application.port.out
 *
 * @Description : Resolves one workflow target to its authoritative current approval task and actions.
 *
 */
package dz.sh.hidra.modules.workflow.application.port.out;

import java.time.Instant;
import java.util.List;
import java.util.Set;

public interface WorkflowTargetApprovalQueryPort {

    Context load(
            String instanceId,
            String targetModule,
            String targetTypeId,
            String targetId,
            String actorReference,
            Set<String> effectivePermissions
    );

    record Context(
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

    final class QueryException extends RuntimeException {

        private final Kind kind;

        public QueryException(Kind kind, String message) {
            super(message);
            this.kind = kind;
        }

        public Kind kind() {
            return kind;
        }

        public enum Kind {
            NOT_FOUND,
            BOUNDARY,
            CONFLICT
        }
    }
}
