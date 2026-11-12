/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TelemetryQualityController
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-12
 *
 * @Type        : Class
 * @Layer       : API
 * @Module      : telemetry
 * @Package     : dz.sh.hidra.modules.telemetry.api.rest.controller
 *
 * @Description : Publishes only source-qualified observations behind native scoped permission checks.
 *
 */
package dz.sh.hidra.modules.telemetry.api.rest.controller;

import dz.sh.hidra.modules.telemetry.api.rest.mapper.TelemetryQualityRestMapper;
import dz.sh.hidra.modules.telemetry.api.rest.request.TelemetryQualityRequests;
import dz.sh.hidra.modules.telemetry.api.rest.response.TelemetryQualityResponses;
import dz.sh.hidra.modules.telemetry.application.port.in.QualifyTelemetryReadingUseCase;
import dz.sh.hidra.modules.telemetry.application.port.in.TrustedTelemetryReadingQueryUseCase;
import dz.sh.hidra.modules.telemetry.domain.exception.InvalidTelemetryValueException;
import dz.sh.hidra.platform.permissions.NativeProtectedOperation;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import java.util.List;
import java.util.Objects;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/telemetry/points/{pointId}")
public final class TelemetryQualityController {
    private final QualifyTelemetryReadingUseCase quality;
    private final TrustedTelemetryReadingQueryUseCase trusted;
    public TelemetryQualityController(QualifyTelemetryReadingUseCase quality,
            TrustedTelemetryReadingQueryUseCase trusted) {
        this.quality = Objects.requireNonNull(quality);
        this.trusted = Objects.requireNonNull(trusted);
    }

    @PostMapping("/readings/{readingId}/assessment")
    @NativeProtectedOperation
    @Operation(operationId = "assessTelemetryRawQuality")
    public ResponseEntity<TelemetryQualityResponses.Assessment> assess(@PathVariable String pointId,
            @PathVariable String readingId, @Valid @RequestBody TelemetryQualityRequests.Command body) {
        var response = TelemetryQualityRestMapper.assessment(
                quality.qualify(body.companyPartyId(), pointId, readingId));
        return ResponseEntity.status(response.replayed() ? HttpStatus.OK : HttpStatus.CREATED)
                .body(response);
    }

    @GetMapping("/readings/{readingId}/assessment")
    @NativeProtectedOperation
    @Operation(operationId = "readTelemetryRawQualityAssessment")
    public TelemetryQualityResponses.Assessment assessment(@PathVariable String pointId,
            @PathVariable String readingId, @RequestParam String companyPartyId) {
        return TelemetryQualityRestMapper.assessment(quality.find(companyPartyId, pointId, readingId));
    }

    @PostMapping("/readings/{readingId}/trust")
    @NativeProtectedOperation
    @Operation(operationId = "publishTrustedTelemetryReading")
    public ResponseEntity<TelemetryQualityResponses.Trusted> publish(@PathVariable String pointId,
            @PathVariable String readingId, @Valid @RequestBody TelemetryQualityRequests.Command body) {
        var response = TelemetryQualityRestMapper.trusted(
                trusted.publish(body.companyPartyId(), pointId, readingId));
        return ResponseEntity.status(response.replayed() ? HttpStatus.OK : HttpStatus.CREATED)
                .body(response);
    }

    @GetMapping("/trusted-readings/{trustedId}")
    @NativeProtectedOperation
    @Operation(operationId = "readTrustedTelemetryReading")
    public TelemetryQualityResponses.Trusted exact(@PathVariable String pointId,
            @PathVariable String trustedId, @RequestParam String companyPartyId) {
        return TelemetryQualityRestMapper.trusted(trusted.find(companyPartyId, pointId, trustedId));
    }

    @GetMapping("/trusted-readings")
    @NativeProtectedOperation
    @Operation(operationId = "listTrustedTelemetryReadings")
    public List<TelemetryQualityResponses.Trusted> history(@PathVariable String pointId,
            @RequestParam String companyPartyId, @RequestParam(defaultValue = "20") int limit) {
        return trusted.history(companyPartyId, pointId, limit).stream()
                .map(TelemetryQualityRestMapper::trusted).toList();
    }

    @ExceptionHandler(InvalidTelemetryValueException.class)
    public ResponseEntity<ProblemDetail> invalid(InvalidTelemetryValueException ex) {
        return ResponseEntity.badRequest().body(ProblemDetail.forStatusAndDetail(
                HttpStatus.BAD_REQUEST, "Protected telemetry evidence is invalid or unavailable."));
    }
    @ExceptionHandler(java.util.NoSuchElementException.class)
    public ResponseEntity<ProblemDetail> missing(java.util.NoSuchElementException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ProblemDetail.forStatusAndDetail(
                HttpStatus.NOT_FOUND, "Protected telemetry evidence unavailable."));
    }
    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<ProblemDetail> unsupported(IllegalStateException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ProblemDetail.forStatusAndDetail(
                HttpStatus.CONFLICT, "Quality policy or trusted publication is not eligible."));
    }
}
