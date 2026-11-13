/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AuthoritativeDeviationController
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-13
 *
 * @Type        : Class
 * @Layer       : API
 * @Module      : monitoring
 * @Package     : dz.sh.hidra.modules.monitoring.api.rest.controller
 *
 * @Description : Exposes native protected evaluation, detail and history routes.
 *
 */
package dz.sh.hidra.modules.monitoring.api.rest.controller;

import dz.sh.hidra.modules.monitoring.api.rest.request.AuthoritativeDeviationRequest;
import dz.sh.hidra.modules.monitoring.api.rest.response.AuthoritativeDeviationResponse;
import dz.sh.hidra.modules.monitoring.application.port.in.AuthoritativeDeviationUseCase;
import dz.sh.hidra.modules.monitoring.domain.model.AuthoritativeDeviationEvaluation;
import dz.sh.hidra.platform.permissions.NativeProtectedOperation;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import java.util.List;
import java.util.NoSuchElementException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/monitoring/rules/{id}/evaluations")
public class AuthoritativeDeviationController {
    private final AuthoritativeDeviationUseCase evaluations;

    public AuthoritativeDeviationController(AuthoritativeDeviationUseCase evaluations) {
        this.evaluations = evaluations;
    }

    @Operation(operationId = "evaluateMonitoringPlanDeviation")
    @PostMapping
    @NativeProtectedOperation
    public AuthoritativeDeviationResponse evaluate(@PathVariable String id,
            @Valid @RequestBody AuthoritativeDeviationRequest request) {
        return response(evaluations.evaluate(id, new AuthoritativeDeviationUseCase.Request(
                request.targetId(), request.trustedReadingId(), request.evaluatedAt())));
    }

    @Operation(operationId = "readMonitoringPlanDeviation")
    @GetMapping("/{evaluationId}")
    @NativeProtectedOperation
    public AuthoritativeDeviationResponse exact(@PathVariable String id, @PathVariable String evaluationId) {
        return response(evaluations.find(id, evaluationId));
    }

    @Operation(operationId = "listMonitoringPlanDeviations")
    @GetMapping
    @NativeProtectedOperation
    public List<AuthoritativeDeviationResponse> history(@PathVariable String id,
            @RequestParam(defaultValue = "20") int limit) {
        return evaluations.history(id, limit).stream().map(AuthoritativeDeviationController::response).toList();
    }

    @ExceptionHandler(IllegalArgumentException.class)
    ProblemDetail invalid(IllegalArgumentException exception) {
        return ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, "Invalid Monitoring evaluation request.");
    }

    @ExceptionHandler(NoSuchElementException.class)
    ProblemDetail unavailable(NoSuchElementException exception) {
        return ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, "Protected Monitoring evaluation unavailable.");
    }

    private static AuthoritativeDeviationResponse response(AuthoritativeDeviationEvaluation value) {
        var target = value.target();
        var reading = value.observation();
        return new AuthoritativeDeviationResponse(value.id(), value.ruleId(), value.ruleRevision(),
                value.evaluatedAt(), value.recordedAt(), value.actorId(), value.unitId(), value.outcome().name(),
                value.actual(), value.expected(), value.difference(),
                target == null ? null : target.id(), target == null ? null : target.revisionId(),
                target == null ? null : target.approvalId(), target == null ? null : target.approvedAt(),
                reading == null ? null : reading.id(), reading == null ? null : reading.rawId(),
                reading == null ? null : reading.assessmentId(), reading == null ? null : reading.bindingId(),
                reading == null ? null : reading.qualityCodeId(), reading == null ? null : reading.trustLevel(),
                reading == null ? null : reading.observedAt(), reading == null ? null : reading.trustedAt(),
                value.outcome() == AuthoritativeDeviationEvaluation.Outcome.POLICY_UNAVAILABLE
                        ? null : value.ruleId() + ":" + value.ruleRevision());
    }
}
