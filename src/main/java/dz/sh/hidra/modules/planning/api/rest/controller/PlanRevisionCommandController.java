/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : PlanRevisionCommandController
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-12
 *
 * @Type        : Class
 * @Layer       : API
 * @Module      : planning
 * @Package     : dz.sh.hidra.modules.planning.api.rest.controller
 *
 * @Description : Publishes the concurrency-protected current-revision metadata update contract.
 *
 */
package dz.sh.hidra.modules.planning.api.rest.controller;

import dz.sh.hidra.modules.planning.application.port.in.UpdatePlanRevisionUseCase;
import dz.sh.hidra.modules.planning.domain.model.PlanRevision;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.Objects;

@RestController
public class PlanRevisionCommandController {

    private final UpdatePlanRevisionUseCase useCase;

    public PlanRevisionCommandController(UpdatePlanRevisionUseCase useCase) {
        this.useCase = Objects.requireNonNull(useCase, "UpdatePlanRevisionUseCase must not be null.");
    }

    @PatchMapping("/api/v1/planning/revisions/{revisionId}")
    public Response update(
            @PathVariable String revisionId,
            @Valid @RequestBody Request request
    ) {
        PlanRevision revision = useCase.update(revisionId, new UpdatePlanRevisionUseCase.Command(
                request.expectedUpdatedAt(), request.changeReasonCodeId(), request.changeReasonText()
        ));
        return Response.from(revision);
    }

    public record Request(
            @NotNull Instant expectedUpdatedAt,
            String changeReasonCodeId,
            String changeReasonText
    ) {
    }

    public record Response(
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
    ) {
        private static Response from(PlanRevision revision) {
            return new Response(
                    revision.id(), revision.planId(), revision.revisionNumber(), revision.revisionCode(),
                    revision.status() == null ? null : revision.status().name(), revision.changeReasonCodeId(),
                    revision.changeReasonText(), revision.baseRevisionId(), revision.submittedByActorId(),
                    revision.submittedAt(), revision.approvedByActorId(), revision.approvedAt(),
                    revision.workflowInstanceId(), revision.createdAt(), revision.updatedAt()
            );
        }
    }
}
