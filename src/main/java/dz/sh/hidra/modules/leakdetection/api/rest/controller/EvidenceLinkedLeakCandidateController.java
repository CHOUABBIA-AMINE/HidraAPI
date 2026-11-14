/**
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 * @Name        : EvidenceLinkedLeakCandidateController
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-14
 * @Type        : Class
 * @Layer       : API
 * @Module      : leakdetection
 * @Package     : dz.sh.hidra.modules.leakdetection.api.rest.controller
 * @Description : T13 governed source-linked suspected leak candidate delivery.
 */
package dz.sh.hidra.modules.leakdetection.api.rest.controller;

import dz.sh.hidra.modules.leakdetection.api.rest.request.LeakCandidateFromEvaluationRequest;
import dz.sh.hidra.modules.leakdetection.api.rest.response.LeakCandidateEvaluationResponse;
import dz.sh.hidra.modules.leakdetection.application.port.in.EvidenceLinkedLeakCandidateUseCase;
import dz.sh.hidra.modules.leakdetection.domain.exception.LeakEvaluationTriggerConflictException;
import dz.sh.hidra.platform.permissions.NativeProtectedOperation;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/leakdetection/candidates")
public class EvidenceLinkedLeakCandidateController {
    private final EvidenceLinkedLeakCandidateUseCase candidates;
    public EvidenceLinkedLeakCandidateController(EvidenceLinkedLeakCandidateUseCase candidates) {
        this.candidates = java.util.Objects.requireNonNull(candidates);
    }

    @Operation(operationId = "createSuspectedLeakFromAuthorizedDeviation")
    @PostMapping("/from-evaluation")
    @NativeProtectedOperation
    public ResponseEntity<LeakCandidateEvaluationResponse> create(
            @Valid @RequestBody LeakCandidateFromEvaluationRequest request) {
        var r = candidates.create(new EvidenceLinkedLeakCandidateUseCase.Command(
            request.evaluationId(), request.profileId(), request.declaredConfidence(), request.reason()));
        return ResponseEntity.status(r.created() ? HttpStatus.CREATED : HttpStatus.OK)
            .body(LeakCandidateEvaluationResponse.of(r));
    }

    @Operation(operationId = "readSuspectedLeakAuthorizedMonitoringEvidence")
    @GetMapping("/{id}/evidence")
    @NativeProtectedOperation
    public LeakCandidateEvaluationResponse evidence(@PathVariable String id) {
        return LeakCandidateEvaluationResponse.of(candidates.evidence(id));
    }

    @ExceptionHandler(LeakEvaluationTriggerConflictException.class)
    ProblemDetail conflict(LeakEvaluationTriggerConflictException ex) {
        return ProblemDetail.forStatusAndDetail(HttpStatus.CONFLICT, ex.getMessage());
    }

    @ExceptionHandler(java.util.NoSuchElementException.class)
    ProblemDetail hidden(java.util.NoSuchElementException ignored) {
        return ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND,
            "Protected Leak candidate unavailable.");
    }

    @ExceptionHandler(IllegalArgumentException.class)
    ProblemDetail invalid(IllegalArgumentException ignored) {
        return ProblemDetail.forStatusAndDetail(HttpStatus.UNPROCESSABLE_ENTITY,
            "Invalid or non-qualifying suspected Leak candidate declaration.");
    }
}
