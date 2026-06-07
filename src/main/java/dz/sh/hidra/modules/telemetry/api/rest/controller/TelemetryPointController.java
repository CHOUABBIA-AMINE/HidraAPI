/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TelemetryPointController
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : RestController
 * @Layer       : API
 * @Module      : telemetry
 * @Package     : dz.sh.hidra.modules.telemetry.api.rest.controller
 *
 * @Description : REST controller for telemetry point endpoints.
 *
 */
package dz.sh.hidra.modules.telemetry.api.rest.controller;

import java.time.Instant;
import java.util.Objects;

import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import dz.sh.hidra.modules.telemetry.api.rest.mapper.TelemetryRestMapper;

import dz.sh.hidra.modules.telemetry.api.rest.request.RegisterTelemetryPointRequest;
import dz.sh.hidra.modules.telemetry.api.rest.response.TelemetryPageResponse;
import dz.sh.hidra.modules.telemetry.api.rest.response.TelemetryPointResponse;
import dz.sh.hidra.modules.telemetry.application.port.in.ActivateTelemetryPointUseCase;
import dz.sh.hidra.modules.telemetry.application.port.in.GetTelemetryPointUseCase;
import dz.sh.hidra.modules.telemetry.application.port.in.ListTelemetryPointsUseCase;
import dz.sh.hidra.modules.telemetry.application.port.in.RegisterTelemetryPointUseCase;
import dz.sh.hidra.modules.telemetry.application.port.in.RetireTelemetryPointUseCase;
import dz.sh.hidra.modules.telemetry.application.port.in.SuspendTelemetryPointUseCase;

/**
 * REST controller for telemetry point endpoints.
 *
 * <p>Architecture role:
 * Thin REST controller for the telemetry API. It delegates all command/query creation to
 * TelemetryRestMapper and all business work to application inbound ports.
 */
@RestController
@Validated
@RequestMapping("/api/v1/telemetry")
@Tag(name = "Telemetry")
public class TelemetryPointController {

    private final RegisterTelemetryPointUseCase registerTelemetryPointUseCase;
    private final ActivateTelemetryPointUseCase activateTelemetryPointUseCase;
    private final SuspendTelemetryPointUseCase suspendTelemetryPointUseCase;
    private final RetireTelemetryPointUseCase retireTelemetryPointUseCase;
    private final GetTelemetryPointUseCase getTelemetryPointUseCase;
    private final ListTelemetryPointsUseCase listTelemetryPointsUseCase;
    private final TelemetryRestMapper mapper;

    public TelemetryPointController(
            RegisterTelemetryPointUseCase registerTelemetryPointUseCase,
            ActivateTelemetryPointUseCase activateTelemetryPointUseCase,
            SuspendTelemetryPointUseCase suspendTelemetryPointUseCase,
            RetireTelemetryPointUseCase retireTelemetryPointUseCase,
            GetTelemetryPointUseCase getTelemetryPointUseCase,
            ListTelemetryPointsUseCase listTelemetryPointsUseCase,
            TelemetryRestMapper mapper) {

        this.registerTelemetryPointUseCase = Objects.requireNonNull(registerTelemetryPointUseCase, "RegisterTelemetryPointUseCase must not be null.");
        this.activateTelemetryPointUseCase = Objects.requireNonNull(activateTelemetryPointUseCase, "ActivateTelemetryPointUseCase must not be null.");
        this.suspendTelemetryPointUseCase = Objects.requireNonNull(suspendTelemetryPointUseCase, "SuspendTelemetryPointUseCase must not be null.");
        this.retireTelemetryPointUseCase = Objects.requireNonNull(retireTelemetryPointUseCase, "RetireTelemetryPointUseCase must not be null.");
        this.getTelemetryPointUseCase = Objects.requireNonNull(getTelemetryPointUseCase, "GetTelemetryPointUseCase must not be null.");
        this.listTelemetryPointsUseCase = Objects.requireNonNull(listTelemetryPointsUseCase, "ListTelemetryPointsUseCase must not be null.");
        this.mapper = Objects.requireNonNull(mapper, "TelemetryRestMapper must not be null.");
    }

    @PostMapping("/points")
    @Operation(summary = "Register telemetry point")
    public ResponseEntity<TelemetryPointResponse> registerPoint(@Valid @RequestBody RegisterTelemetryPointRequest request) {
        return ResponseEntity.ok(mapper.toResponse(registerTelemetryPointUseCase.registerTelemetryPoint(mapper.toCommand(request))));
    }

    @PostMapping("/devices/{deviceId}/points/{pointId}/activate")
    @Operation(summary = "Activate telemetry point")
    public ResponseEntity<TelemetryPointResponse> activatePoint(@PathVariable String deviceId, @PathVariable String pointId) {
        return ResponseEntity.ok(mapper.toResponse(activateTelemetryPointUseCase.activateTelemetryPoint(mapper.toActivateTelemetryPointCommand(deviceId, pointId))));
    }

    @PostMapping("/points/{pointId}/suspend")
    @Operation(summary = "Suspend telemetry point")
    public ResponseEntity<TelemetryPointResponse> suspendPoint(@PathVariable String pointId) {
        return ResponseEntity.ok(mapper.toResponse(suspendTelemetryPointUseCase.suspendTelemetryPoint(mapper.toSuspendTelemetryPointCommand(pointId))));
    }

    @PostMapping("/points/{pointId}/retire")
    @Operation(summary = "Retire telemetry point")
    public ResponseEntity<TelemetryPointResponse> retirePoint(@PathVariable String pointId) {
        return ResponseEntity.ok(mapper.toResponse(retireTelemetryPointUseCase.retireTelemetryPoint(mapper.toRetireTelemetryPointCommand(pointId))));
    }

    @GetMapping("/points/{pointId}")
    @Operation(summary = "Get telemetry point by id")
    public ResponseEntity<TelemetryPointResponse> getPoint(@PathVariable String pointId) {
        return ResponseEntity.ok(mapper.toResponse(getTelemetryPointUseCase.getTelemetryPoint(mapper.toGetTelemetryPointByIdQuery(pointId))));
    }

    @GetMapping("/points")
    @Operation(summary = "List telemetry points")
    public ResponseEntity<TelemetryPageResponse<TelemetryPointResponse>> listPoints(
            @RequestParam(required = false) String searchText,
            @RequestParam(required = false) String deviceId,
            @RequestParam(required = false) String pointTypeId,
            @RequestParam(required = false) String pointTypeCode,
            @RequestParam(required = false) String signalTypeId,
            @RequestParam(required = false) String signalTypeCode,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size,
            @RequestParam(required = false) String sortField,
            @RequestParam(required = false) String sortDirection) {

        return ResponseEntity.ok(mapper.toPointPageResponse(listTelemetryPointsUseCase.listTelemetryPoints(
                mapper.toListTelemetryPointsQuery(searchText, deviceId, pointTypeId, pointTypeCode, signalTypeId, signalTypeCode, status, page, size, sortField, sortDirection))));
    }

}
