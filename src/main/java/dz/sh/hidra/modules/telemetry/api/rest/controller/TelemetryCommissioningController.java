/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TelemetryCommissioningController
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-12
 *
 * @Type        : Class
 * @Layer       : API
 * @Module      : telemetry
 * @Package     : dz.sh.hidra.modules.telemetry.api.rest.controller
 *
 * @Description : Native-scoped commissioning and effective historical lookup endpoints.
 *
 */
package dz.sh.hidra.modules.telemetry.api.rest.controller;

import dz.sh.hidra.modules.telemetry.api.rest.mapper.TelemetryCommissioningRestMapper;
import dz.sh.hidra.modules.telemetry.api.rest.request.TelemetryCommissioningRequests;
import dz.sh.hidra.modules.telemetry.api.rest.response.TelemetryCommissioningResponses;
import dz.sh.hidra.modules.telemetry.application.port.in.TelemetryCommissioningUseCase;
import dz.sh.hidra.modules.telemetry.domain.exception.InvalidTelemetryValueException;
import dz.sh.hidra.modules.telemetry.domain.exception.TelemetryCommissioningConflictException;
import dz.sh.hidra.platform.permissions.NativeProtectedOperation;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import java.time.Instant;
import java.util.Objects;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/telemetry/commissioning")
public final class TelemetryCommissioningController {
    private final TelemetryCommissioningUseCase owner;
    public TelemetryCommissioningController(TelemetryCommissioningUseCase owner) {
        this.owner=Objects.requireNonNull(owner);
    }

    @PostMapping
    @NativeProtectedOperation
    @Operation(operationId="commissionTelemetrySourcePointBinding",
            summary="Commission one source, device, point, direct mapping and asset binding")
    @ApiResponse(responseCode="200",description="Authorized commissioned resource receipt")
    @ApiResponse(responseCode="403",description="Current native scope or asset admission denied")
    @ApiResponse(responseCode="409",description="Conflicting commissioning identity or time window")
    public TelemetryCommissioningResponses.Snapshot enroll(
            @Valid @RequestBody TelemetryCommissioningRequests.Enroll request) {
        return TelemetryCommissioningRestMapper.toResponse(owner.enroll(
                TelemetryCommissioningRestMapper.toCommand(request)));
    }

    @PostMapping("/points/{pointId}/bindings")
    @NativeProtectedOperation
    @Operation(operationId="supersedeTelemetryPointBinding",
            summary="Close the previous half-open binding and commission a successor")
    public TelemetryCommissioningResponses.Snapshot rebind(@PathVariable String pointId,
            @Valid @RequestBody TelemetryCommissioningRequests.Rebind request) {
        return TelemetryCommissioningRestMapper.toResponse(owner.rebind(
                TelemetryCommissioningRestMapper.toCommand(pointId,request)));
    }

    @GetMapping("/points/{pointId}/effective")
    @NativeProtectedOperation
    @Operation(operationId="readEffectiveTelemetryCommissioning",
            summary="Read exact effective source, point, mapping and asset binding under current access")
    public TelemetryCommissioningResponses.Snapshot effective(
            @PathVariable String pointId,
            @RequestParam String companyPartyId,@RequestParam Instant asOf) {
        return TelemetryCommissioningRestMapper.toResponse(
                owner.effective(companyPartyId,pointId,asOf));
    }

    @GetMapping("/points/{pointId}/history")
    @NativeProtectedOperation
    @Operation(operationId="readTelemetryCommissioningHistory",
            summary="Read bounded effective-binding history with no unscoped counts")
    public TelemetryCommissioningResponses.History history(
            @PathVariable String pointId,@RequestParam String companyPartyId,
            @RequestParam(defaultValue="20") int limit) {
        return TelemetryCommissioningRestMapper.toResponse(owner.history(
                companyPartyId,pointId,limit));
    }

    @ExceptionHandler(InvalidTelemetryValueException.class)
    public ResponseEntity<ProblemDetail> invalid(InvalidTelemetryValueException ignored) {
        return ResponseEntity.badRequest().body(ProblemDetail.forStatusAndDetail(
                HttpStatus.BAD_REQUEST,"Invalid telemetry commissioning request."));
    }
    @ExceptionHandler({TelemetryCommissioningConflictException.class,
                       org.springframework.dao.DataIntegrityViolationException.class})
    public ResponseEntity<ProblemDetail> conflict(RuntimeException ignored) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ProblemDetail.forStatusAndDetail(
                HttpStatus.CONFLICT,"Conflicting telemetry commissioning request."));
    }
}
