/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ExecuteWorkflowTargetTransitionUseCase
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-12
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : workflow
 * @Package     : dz.sh.hidra.modules.workflow.application.port.in
 *
 * @Description : Publishes workflow transition execution as a cross-module-safe input contract.
 *
 */
package dz.sh.hidra.modules.workflow.application.port.in;

import java.time.Instant;
import java.util.Set;

public interface ExecuteWorkflowTargetTransitionUseCase {

    Result execute(Command command);

    record Command(
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

    record Result(
            String actionId,
            String taskId,
            String taskStatus,
            String instanceId,
            String instanceStatus,
            String transitionId,
            String decision,
            String currentStepId,
            String nextTaskId,
            Instant executedAt
    ) { }
}
