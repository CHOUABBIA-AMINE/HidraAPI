/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : DeviationMetricRunController
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-17
 *
 * @Type        : Class
 * @Layer       : API
 * @Module      : analytics
 * @Package     : dz.sh.hidra.modules.analytics.api.rest.controller
 *
 * @Description : Native protected Analytics deviation execution and scoped retrieval API.
 *
 */
package dz.sh.hidra.modules.analytics.api.rest.controller;

import dz.sh.hidra.modules.analytics.application.port.in.DeviationMetricRunUseCase;
import dz.sh.hidra.modules.analytics.api.rest.request.ExecuteDeviationMetricRunRequest;
import dz.sh.hidra.modules.analytics.api.rest.response.DeviationMetricRunResponse;
import dz.sh.hidra.platform.permissions.NativeProtectedOperation;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.Objects;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/analytics/companies/{companyId}/facilities/{facilityId}/deviation-metric-runs")
@Tag(name="Analytics deviation metric")
public class DeviationMetricRunController {
    private final DeviationMetricRunUseCase metrics;
    public DeviationMetricRunController(DeviationMetricRunUseCase metrics) {
        this.metrics=Objects.requireNonNull(metrics);
    }

    @NativeProtectedOperation
    @Operation(operationId="executeDeviationMetricRun")
    @PostMapping
    public ResponseEntity<DeviationMetricRunResponse> execute(
            @PathVariable String companyId,@PathVariable String facilityId,
            @Valid @RequestBody ExecuteDeviationMetricRunRequest request) {
        var result=metrics.execute(companyId,facilityId,request.definitionVersionId(),request.evaluationId());
        return ResponseEntity.status(result.replayed()?HttpStatus.OK:HttpStatus.CREATED)
                .body(DeviationMetricRunResponse.from(result));
    }

    @NativeProtectedOperation
    @Operation(operationId="readDeviationMetricRun")
    @GetMapping("/{runId}")
    public DeviationMetricRunResponse read(@PathVariable String companyId,
            @PathVariable String facilityId,@PathVariable String runId) {
        return DeviationMetricRunResponse.from(metrics.read(companyId,facilityId,runId));
    }

    @ExceptionHandler(UnsupportedOperationException.class)
    public ResponseEntity<ProblemDetail> invalidConfiguration(UnsupportedOperationException invalid) {
        return ResponseEntity.status(422).body(ProblemDetail.forStatusAndDetail(
                HttpStatus.UNPROCESSABLE_CONTENT,"Authorized metric configuration is unavailable."));
    }
}
