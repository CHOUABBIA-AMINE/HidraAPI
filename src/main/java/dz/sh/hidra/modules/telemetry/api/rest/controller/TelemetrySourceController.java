/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TelemetrySourceController
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : RestController
 * @Layer       : API
 * @Module      : telemetry
 * @Package     : dz.sh.hidra.modules.telemetry.api.rest.controller
 *
 * @Description : REST controller for telemetry source endpoints.
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

import dz.sh.hidra.modules.telemetry.api.rest.request.RegisterTelemetrySourceRequest;
import dz.sh.hidra.modules.telemetry.api.rest.response.TelemetryPageResponse;
import dz.sh.hidra.modules.telemetry.api.rest.response.TelemetrySourceResponse;
import dz.sh.hidra.modules.telemetry.application.port.in.ActivateTelemetrySourceUseCase;
import dz.sh.hidra.modules.telemetry.application.port.in.DeactivateTelemetrySourceUseCase;
import dz.sh.hidra.modules.telemetry.application.port.in.GetTelemetrySourceUseCase;
import dz.sh.hidra.modules.telemetry.application.port.in.ListTelemetrySourcesUseCase;
import dz.sh.hidra.modules.telemetry.application.port.in.RegisterTelemetrySourceUseCase;
import dz.sh.hidra.modules.telemetry.application.port.in.RetireTelemetrySourceUseCase;

/**
 * REST controller for telemetry source endpoints.
 *
 * <p>Architecture role:
 * Thin REST controller for the telemetry API. It delegates all command/query creation to
 * TelemetryRestMapper and all business work to application inbound ports.
 */
@RestController
@Validated
@RequestMapping("/api/v1/telemetry")
@Tag(name = "Telemetry")
public class TelemetrySourceController {

    private final RegisterTelemetrySourceUseCase registerTelemetrySourceUseCase;
    private final ActivateTelemetrySourceUseCase activateTelemetrySourceUseCase;
    private final DeactivateTelemetrySourceUseCase deactivateTelemetrySourceUseCase;
    private final RetireTelemetrySourceUseCase retireTelemetrySourceUseCase;
    private final GetTelemetrySourceUseCase getTelemetrySourceUseCase;
    private final ListTelemetrySourcesUseCase listTelemetrySourcesUseCase;
    private final TelemetryRestMapper mapper;

    public TelemetrySourceController(
            RegisterTelemetrySourceUseCase registerTelemetrySourceUseCase,
            ActivateTelemetrySourceUseCase activateTelemetrySourceUseCase,
            DeactivateTelemetrySourceUseCase deactivateTelemetrySourceUseCase,
            RetireTelemetrySourceUseCase retireTelemetrySourceUseCase,
            GetTelemetrySourceUseCase getTelemetrySourceUseCase,
            ListTelemetrySourcesUseCase listTelemetrySourcesUseCase,
            TelemetryRestMapper mapper) {

        this.registerTelemetrySourceUseCase = Objects.requireNonNull(registerTelemetrySourceUseCase, "RegisterTelemetrySourceUseCase must not be null.");
        this.activateTelemetrySourceUseCase = Objects.requireNonNull(activateTelemetrySourceUseCase, "ActivateTelemetrySourceUseCase must not be null.");
        this.deactivateTelemetrySourceUseCase = Objects.requireNonNull(deactivateTelemetrySourceUseCase, "DeactivateTelemetrySourceUseCase must not be null.");
        this.retireTelemetrySourceUseCase = Objects.requireNonNull(retireTelemetrySourceUseCase, "RetireTelemetrySourceUseCase must not be null.");
        this.getTelemetrySourceUseCase = Objects.requireNonNull(getTelemetrySourceUseCase, "GetTelemetrySourceUseCase must not be null.");
        this.listTelemetrySourcesUseCase = Objects.requireNonNull(listTelemetrySourcesUseCase, "ListTelemetrySourcesUseCase must not be null.");
        this.mapper = Objects.requireNonNull(mapper, "TelemetryRestMapper must not be null.");
    }

    @PostMapping("/sources")
    @Operation(summary = "Register telemetry source")
    public ResponseEntity<TelemetrySourceResponse> registerSource(@Valid @RequestBody RegisterTelemetrySourceRequest request) {
        return ResponseEntity.ok(mapper.toResponse(registerTelemetrySourceUseCase.registerTelemetrySource(mapper.toCommand(request))));
    }

    @PostMapping("/sources/{sourceId}/activate")
    @Operation(summary = "Activate telemetry source")
    public ResponseEntity<TelemetrySourceResponse> activateSource(@PathVariable String sourceId) {
        return ResponseEntity.ok(mapper.toResponse(activateTelemetrySourceUseCase.activateTelemetrySource(mapper.toActivateTelemetrySourceCommand(sourceId))));
    }

    @PostMapping("/sources/{sourceId}/deactivate")
    @Operation(summary = "Deactivate telemetry source")
    public ResponseEntity<TelemetrySourceResponse> deactivateSource(@PathVariable String sourceId) {
        return ResponseEntity.ok(mapper.toResponse(deactivateTelemetrySourceUseCase.deactivateTelemetrySource(mapper.toDeactivateTelemetrySourceCommand(sourceId))));
    }

    @PostMapping("/sources/{sourceId}/retire")
    @Operation(summary = "Retire telemetry source")
    public ResponseEntity<TelemetrySourceResponse> retireSource(@PathVariable String sourceId) {
        return ResponseEntity.ok(mapper.toResponse(retireTelemetrySourceUseCase.retireTelemetrySource(mapper.toRetireTelemetrySourceCommand(sourceId))));
    }

    @GetMapping("/sources/{sourceId}")
    @Operation(summary = "Get telemetry source by id")
    public ResponseEntity<TelemetrySourceResponse> getSource(@PathVariable String sourceId) {
        return ResponseEntity.ok(mapper.toResponse(getTelemetrySourceUseCase.getTelemetrySource(mapper.toGetTelemetrySourceByIdQuery(sourceId))));
    }

    @GetMapping("/sources")
    @Operation(summary = "List telemetry sources")
    public ResponseEntity<TelemetryPageResponse<TelemetrySourceResponse>> listSources(
            @RequestParam(required = false) String searchText,
            @RequestParam(required = false) String sourceTypeId,
            @RequestParam(required = false) String sourceTypeCode,
            @RequestParam(required = false) String protocolId,
            @RequestParam(required = false) String protocolCode,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size,
            @RequestParam(required = false) String sortField,
            @RequestParam(required = false) String sortDirection) {

        return ResponseEntity.ok(mapper.toSourcePageResponse(listTelemetrySourcesUseCase.listTelemetrySources(
                mapper.toListTelemetrySourcesQuery(searchText, sourceTypeId, sourceTypeCode, protocolId, protocolCode, status, page, size, sortField, sortDirection))));
    }

}
