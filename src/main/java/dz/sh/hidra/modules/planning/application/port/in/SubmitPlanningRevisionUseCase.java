/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : SubmitPlanningRevisionUseCase
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-11
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : planning
 * @Package     : dz.sh.hidra.modules.planning.application.port.in
 *
 * @Description : Submits a scoped Planning revision to a configured independent approval workflow.
 *
 */
package dz.sh.hidra.modules.planning.application.port.in;

import java.time.Instant;

public interface SubmitPlanningRevisionUseCase {
    SubmissionView submit(String revisionId, SubmissionCommand command);

    record SubmissionCommand(Instant expectedPlanUpdatedAt, Instant expectedRevisionUpdatedAt,
            String definitionId, int definitionVersion, String targetTypeId, String workflowPurposeId,
            String reviewerActorId, String correlationId) { }

    record SubmissionView(String revisionId, String revisionStatus, Instant revisionUpdatedAt,
            Instant planUpdatedAt, String workflowInstanceId, String workflowTaskId,
            Instant workflowTaskUpdatedAt) { }
}
