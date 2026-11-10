/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : PlanningDraftController
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-10
 *
 * @Type        : Class
 * @Layer       : API
 * @Module      : planning
 * @Package     : dz.sh.hidra.modules.planning.api.rest.controller
 *
 * @Description : Exposes Planning draft revision and target authoring through explicit DTOs.
 *
 */
package dz.sh.hidra.modules.planning.api.rest.controller;

import dz.sh.hidra.modules.planning.application.port.in.AuthorPlanningDraftUseCase;
import dz.sh.hidra.modules.planning.application.port.in.PlanningQueryUseCase.PlanRevisionView;
import dz.sh.hidra.modules.planning.application.port.in.PlanningQueryUseCase.PlanTargetView;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.Instant;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PlanningDraftController {

    private final AuthorPlanningDraftUseCase drafts;

    public PlanningDraftController(AuthorPlanningDraftUseCase drafts) {
        this.drafts = drafts;
    }

    @PostMapping("/api/v1/planning/operational-plans/{planId}/revisions")
    @ApiResponse(responseCode = "200", description = "Draft revision created.", useReturnTypeSchema = true)
    @ApiResponse(responseCode = "409", description = "The expected plan version is stale or another draft is current.")
    public RevisionResponse createRevision(@PathVariable String planId, @Valid @RequestBody RevisionRequest request) {
        return RevisionResponse.from(drafts.createRevision(planId, new AuthorPlanningDraftUseCase.RevisionCommand(
                request.expectedPlanUpdatedAt(), request.changeReasonCodeId(), request.changeReasonText())));
    }

    @PostMapping("/api/v1/planning/revisions/{revisionId}/targets")
    public TargetResponse createTarget(@PathVariable String revisionId, @Valid @RequestBody TargetRequest request) {
        return TargetResponse.from(drafts.createTarget(revisionId, request.toCommand()));
    }

    @PutMapping("/api/v1/planning/targets/{targetId}")
    public TargetResponse replaceTarget(@PathVariable String targetId, @Valid @RequestBody TargetRequest request) {
        return TargetResponse.from(drafts.replaceTarget(targetId, request.toCommand()));
    }

    public record RevisionRequest(@NotNull Instant expectedPlanUpdatedAt,
            String changeReasonCodeId, String changeReasonText) { }

    public record TargetRequest(@NotNull Instant expectedRevisionUpdatedAt, Instant expectedTargetUpdatedAt,
            String scenarioId, String nominationId, @NotBlank String targetTypeId,
            @NotBlank String topologyAssetType, @NotBlank String topologyAssetId,
            String telemetryPointId, BigDecimal targetValue, String targetTextValue,
            String unitId, BigDecimal toleranceLow, BigDecimal toleranceHigh,
            @NotNull Instant validFrom, @NotNull Instant validTo, Integer priority) {

        private AuthorPlanningDraftUseCase.TargetCommand toCommand() {
            return new AuthorPlanningDraftUseCase.TargetCommand(expectedRevisionUpdatedAt, expectedTargetUpdatedAt,
                    scenarioId, nominationId, targetTypeId, topologyAssetType, topologyAssetId, telemetryPointId,
                    targetValue, targetTextValue, unitId, toleranceLow, toleranceHigh, validFrom, validTo, priority);
        }
    }

    public record RevisionResponse(String id, String planId, int revisionNumber, String revisionCode,
            String status, String changeReasonCodeId, String changeReasonText, String baseRevisionId,
            Instant createdAt, Instant updatedAt) {

        private static RevisionResponse from(PlanRevisionView value) {
            return new RevisionResponse(value.id(), value.planId(), value.revisionNumber(), value.revisionCode(),
                    value.status(), value.changeReasonCodeId(), value.changeReasonText(), value.baseRevisionId(),
                    value.createdAt(), value.updatedAt());
        }
    }

    public record TargetResponse(TargetView target, Instant revisionUpdatedAt) {
        private static TargetResponse from(AuthorPlanningDraftUseCase.TargetResult result) {
            return new TargetResponse(TargetView.from(result.target()), result.revisionUpdatedAt());
        }
    }

    public record TargetView(String id, String revisionId, String scenarioId, String nominationId,
            String targetTypeId, String topologyAssetType, String topologyAssetId, String topologyAssetCode,
            String topologyAssetNameSnapshot, String telemetryPointId, String telemetryPointCodeSnapshot,
            BigDecimal targetValue, String targetTextValue, String unitId, BigDecimal toleranceLow,
            BigDecimal toleranceHigh, Instant validFrom, Instant validTo, Integer priority, String status,
            Instant createdAt, Instant updatedAt) {

        private static TargetView from(PlanTargetView value) {
            return new TargetView(value.id(), value.revisionId(), value.scenarioId(), value.nominationId(),
                    value.targetTypeId(), value.topologyAssetType(), value.topologyAssetId(), value.topologyAssetCode(),
                    value.topologyAssetNameSnapshot(), value.telemetryPointId(), value.telemetryPointCodeSnapshot(),
                    value.targetValue(), value.targetTextValue(), value.unitId(), value.toleranceLow(), value.toleranceHigh(),
                    value.validFrom(), value.validTo(), value.priority(), value.status(), value.createdAt(), value.updatedAt());
        }
    }
}
