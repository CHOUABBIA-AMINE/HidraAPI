/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TelemetryDeviceController
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : RestController
 * @Layer       : API
 * @Module      : telemetry
 * @Package     : dz.sh.hidra.modules.telemetry.api.rest.controller
 *
 * @Description : REST controller for telemetry device endpoints.
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

import dz.sh.hidra.modules.telemetry.api.rest.request.RegisterTelemetryDeviceRequest;
import dz.sh.hidra.modules.telemetry.api.rest.response.TelemetryDeviceResponse;
import dz.sh.hidra.modules.telemetry.api.rest.response.TelemetryPageResponse;
import dz.sh.hidra.modules.telemetry.application.port.in.ActivateTelemetryDeviceUseCase;
import dz.sh.hidra.modules.telemetry.application.port.in.DeactivateTelemetryDeviceUseCase;
import dz.sh.hidra.modules.telemetry.application.port.in.GetTelemetryDeviceUseCase;
import dz.sh.hidra.modules.telemetry.application.port.in.ListTelemetryDevicesUseCase;
import dz.sh.hidra.modules.telemetry.application.port.in.RegisterTelemetryDeviceUseCase;
import dz.sh.hidra.modules.telemetry.application.port.in.RetireTelemetryDeviceUseCase;

/**
 * REST controller for telemetry device endpoints.
 *
 * <p>Architecture role:
 * Thin REST controller for the telemetry API. It delegates all command/query creation to
 * TelemetryRestMapper and all business work to application inbound ports.
 */
@RestController
@Validated
@RequestMapping("/api/v1/telemetry")
@Tag(name = "Telemetry")
public class TelemetryDeviceController {

    private final RegisterTelemetryDeviceUseCase registerTelemetryDeviceUseCase;
    private final ActivateTelemetryDeviceUseCase activateTelemetryDeviceUseCase;
    private final DeactivateTelemetryDeviceUseCase deactivateTelemetryDeviceUseCase;
    private final RetireTelemetryDeviceUseCase retireTelemetryDeviceUseCase;
    private final GetTelemetryDeviceUseCase getTelemetryDeviceUseCase;
    private final ListTelemetryDevicesUseCase listTelemetryDevicesUseCase;
    private final TelemetryRestMapper mapper;

    public TelemetryDeviceController(
            RegisterTelemetryDeviceUseCase registerTelemetryDeviceUseCase,
            ActivateTelemetryDeviceUseCase activateTelemetryDeviceUseCase,
            DeactivateTelemetryDeviceUseCase deactivateTelemetryDeviceUseCase,
            RetireTelemetryDeviceUseCase retireTelemetryDeviceUseCase,
            GetTelemetryDeviceUseCase getTelemetryDeviceUseCase,
            ListTelemetryDevicesUseCase listTelemetryDevicesUseCase,
            TelemetryRestMapper mapper) {

        this.registerTelemetryDeviceUseCase = Objects.requireNonNull(registerTelemetryDeviceUseCase, "RegisterTelemetryDeviceUseCase must not be null.");
        this.activateTelemetryDeviceUseCase = Objects.requireNonNull(activateTelemetryDeviceUseCase, "ActivateTelemetryDeviceUseCase must not be null.");
        this.deactivateTelemetryDeviceUseCase = Objects.requireNonNull(deactivateTelemetryDeviceUseCase, "DeactivateTelemetryDeviceUseCase must not be null.");
        this.retireTelemetryDeviceUseCase = Objects.requireNonNull(retireTelemetryDeviceUseCase, "RetireTelemetryDeviceUseCase must not be null.");
        this.getTelemetryDeviceUseCase = Objects.requireNonNull(getTelemetryDeviceUseCase, "GetTelemetryDeviceUseCase must not be null.");
        this.listTelemetryDevicesUseCase = Objects.requireNonNull(listTelemetryDevicesUseCase, "ListTelemetryDevicesUseCase must not be null.");
        this.mapper = Objects.requireNonNull(mapper, "TelemetryRestMapper must not be null.");
    }

    @PostMapping("/devices")
    @Operation(summary = "Register telemetry device")
    public ResponseEntity<TelemetryDeviceResponse> registerDevice(@Valid @RequestBody RegisterTelemetryDeviceRequest request) {
        return ResponseEntity.ok(mapper.toResponse(registerTelemetryDeviceUseCase.registerTelemetryDevice(mapper.toCommand(request))));
    }

    @PostMapping("/sources/{sourceId}/devices/{deviceId}/activate")
    @Operation(summary = "Activate telemetry device")
    public ResponseEntity<TelemetryDeviceResponse> activateDevice(@PathVariable String sourceId, @PathVariable String deviceId) {
        return ResponseEntity.ok(mapper.toResponse(activateTelemetryDeviceUseCase.activateTelemetryDevice(mapper.toActivateTelemetryDeviceCommand(sourceId, deviceId))));
    }

    @PostMapping("/devices/{deviceId}/deactivate")
    @Operation(summary = "Deactivate telemetry device")
    public ResponseEntity<TelemetryDeviceResponse> deactivateDevice(@PathVariable String deviceId) {
        return ResponseEntity.ok(mapper.toResponse(deactivateTelemetryDeviceUseCase.deactivateTelemetryDevice(mapper.toDeactivateTelemetryDeviceCommand(deviceId))));
    }

    @PostMapping("/devices/{deviceId}/retire")
    @Operation(summary = "Retire telemetry device")
    public ResponseEntity<TelemetryDeviceResponse> retireDevice(@PathVariable String deviceId) {
        return ResponseEntity.ok(mapper.toResponse(retireTelemetryDeviceUseCase.retireTelemetryDevice(mapper.toRetireTelemetryDeviceCommand(deviceId))));
    }

    @GetMapping("/devices/{deviceId}")
    @Operation(summary = "Get telemetry device by id")
    public ResponseEntity<TelemetryDeviceResponse> getDevice(@PathVariable String deviceId) {
        return ResponseEntity.ok(mapper.toResponse(getTelemetryDeviceUseCase.getTelemetryDevice(mapper.toGetTelemetryDeviceByIdQuery(deviceId))));
    }

    @GetMapping("/devices")
    @Operation(summary = "List telemetry devices")
    public ResponseEntity<TelemetryPageResponse<TelemetryDeviceResponse>> listDevices(
            @RequestParam(required = false) String searchText,
            @RequestParam(required = false) String sourceId,
            @RequestParam(required = false) String deviceTypeId,
            @RequestParam(required = false) String deviceTypeCode,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size,
            @RequestParam(required = false) String sortField,
            @RequestParam(required = false) String sortDirection) {

        return ResponseEntity.ok(mapper.toDevicePageResponse(listTelemetryDevicesUseCase.listTelemetryDevices(
                mapper.toListTelemetryDevicesQuery(searchText, sourceId, deviceTypeId, deviceTypeCode, status, page, size, sortField, sortDirection))));
    }

}
