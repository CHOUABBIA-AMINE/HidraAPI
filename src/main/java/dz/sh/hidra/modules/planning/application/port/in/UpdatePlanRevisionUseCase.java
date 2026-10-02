/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : UpdatePlanRevisionUseCase
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-28
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : planning
 * @Package     : dz.sh.hidra.modules.planning.application.port.in
 *
 * @Description : Updates current plan-revision metadata with an explicit stale-write precondition.
 *
 */
package dz.sh.hidra.modules.planning.application.port.in;

import java.time.Instant;

public interface UpdatePlanRevisionUseCase {

    Result update(String revisionId, Command command);

    record Command(
            Instant expectedUpdatedAt,
            String changeReasonCodeId,
            String changeReasonText
    ) {
        public Command {
            if (expectedUpdatedAt == null) {
                throw new IllegalArgumentException("expectedUpdatedAt must not be null.");
            }
        }
    }

    record Result(
            String id,
            String planId,
            int revisionNumber,
            String revisionCode,
            String status,
            String changeReasonCodeId,
            String changeReasonText,
            String baseRevisionId,
            String submittedByActorId,
            Instant submittedAt,
            String approvedByActorId,
            Instant approvedAt,
            String workflowInstanceId,
            Instant createdAt,
            Instant updatedAt
    ) { }
}
