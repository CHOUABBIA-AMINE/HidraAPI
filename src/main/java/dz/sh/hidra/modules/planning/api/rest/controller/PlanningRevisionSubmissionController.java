/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : PlanningRevisionSubmissionController
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-11
 *
 * @Type        : Class
 * @Layer       : API
 * @Module      : planning
 * @Package     : dz.sh.hidra.modules.planning.api.rest.controller
 *
 * @Description : Accepts a signed scoped revision submission through explicit transport DTOs.
 *
 */
package dz.sh.hidra.modules.planning.api.rest.controller;

import dz.sh.hidra.modules.planning.api.rest.request.SubmitPlanningRevisionRequest;
import dz.sh.hidra.modules.planning.api.rest.response.SubmitPlanningRevisionResponse;
import dz.sh.hidra.modules.planning.application.port.in.SubmitPlanningRevisionUseCase;
import jakarta.validation.Valid;
import java.util.Objects;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public final class PlanningRevisionSubmissionController {
    private final SubmitPlanningRevisionUseCase submissions;

    public PlanningRevisionSubmissionController(SubmitPlanningRevisionUseCase submissions) {
        this.submissions = Objects.requireNonNull(submissions);
    }

    @PostMapping("/api/v1/planning/revisions/{revisionId}/submit")
    public SubmitPlanningRevisionResponse submit(@PathVariable String revisionId,
            @Valid @RequestBody SubmitPlanningRevisionRequest request) {
        var value = submissions.submit(revisionId, new SubmitPlanningRevisionUseCase.SubmissionCommand(
                request.expectedPlanUpdatedAt(), request.expectedRevisionUpdatedAt(),
                request.definitionId(), request.definitionVersion(), request.targetTypeId(),
                request.workflowPurposeId(), request.reviewerActorId(), request.correlationId()));
        return new SubmitPlanningRevisionResponse(value.revisionId(), value.revisionStatus(),
                value.revisionUpdatedAt(), value.planUpdatedAt(), value.workflowInstanceId(),
                value.workflowTaskId(), value.workflowTaskUpdatedAt());
    }
}
