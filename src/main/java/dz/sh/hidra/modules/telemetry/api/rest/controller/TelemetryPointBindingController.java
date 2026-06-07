/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TelemetryPointBindingController
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : RestController
 * @Layer       : API
 * @Module      : telemetry
 * @Package     : dz.sh.hidra.modules.telemetry.api.rest.controller
 *
 * @Description : REST controller for telemetry point topology binding endpoints.
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

import dz.sh.hidra.modules.telemetry.api.rest.request.BindTelemetryPointRequest;
import dz.sh.hidra.modules.telemetry.api.rest.request.CloseTelemetryPointBindingRequest;
import dz.sh.hidra.modules.telemetry.api.rest.response.TelemetryPageResponse;
import dz.sh.hidra.modules.telemetry.api.rest.response.TelemetryPointBindingResponse;
import dz.sh.hidra.modules.telemetry.application.port.in.BindTelemetryPointUseCase;
import dz.sh.hidra.modules.telemetry.application.port.in.CloseTelemetryPointBindingUseCase;
import dz.sh.hidra.modules.telemetry.application.port.in.GetTelemetryPointBindingUseCase;
import dz.sh.hidra.modules.telemetry.application.port.in.ListTelemetryPointBindingsUseCase;

/**
 * REST controller for telemetry point topology binding endpoints.
 *
 * <p>Architecture role:
 * Thin REST controller for the telemetry API. It delegates all command/query creation to
 * TelemetryRestMapper and all business work to application inbound ports.
 */
@RestController
@Validated
@RequestMapping("/api/v1/telemetry")
@Tag(name = "Telemetry")
public class TelemetryPointBindingController {

    private final BindTelemetryPointUseCase bindTelemetryPointUseCase;
    private final CloseTelemetryPointBindingUseCase closeTelemetryPointBindingUseCase;
    private final GetTelemetryPointBindingUseCase getTelemetryPointBindingUseCase;
    private final ListTelemetryPointBindingsUseCase listTelemetryPointBindingsUseCase;
    private final TelemetryRestMapper mapper;

    public TelemetryPointBindingController(
            BindTelemetryPointUseCase bindTelemetryPointUseCase,
            CloseTelemetryPointBindingUseCase closeTelemetryPointBindingUseCase,
            GetTelemetryPointBindingUseCase getTelemetryPointBindingUseCase,
            ListTelemetryPointBindingsUseCase listTelemetryPointBindingsUseCase,
            TelemetryRestMapper mapper) {

        this.bindTelemetryPointUseCase = Objects.requireNonNull(bindTelemetryPointUseCase, "BindTelemetryPointUseCase must not be null.");
        this.closeTelemetryPointBindingUseCase = Objects.requireNonNull(closeTelemetryPointBindingUseCase, "CloseTelemetryPointBindingUseCase must not be null.");
        this.getTelemetryPointBindingUseCase = Objects.requireNonNull(getTelemetryPointBindingUseCase, "GetTelemetryPointBindingUseCase must not be null.");
        this.listTelemetryPointBindingsUseCase = Objects.requireNonNull(listTelemetryPointBindingsUseCase, "ListTelemetryPointBindingsUseCase must not be null.");
        this.mapper = Objects.requireNonNull(mapper, "TelemetryRestMapper must not be null.");
    }

    @PostMapping("/point-bindings")
    @Operation(summary = "Bind telemetry point to topology asset")
    public ResponseEntity<TelemetryPointBindingResponse> bindPoint(@Valid @RequestBody BindTelemetryPointRequest request) {
        return ResponseEntity.ok(mapper.toResponse(bindTelemetryPointUseCase.bindTelemetryPoint(mapper.toCommand(request))));
    }

    @PostMapping("/point-bindings/{bindingId}/close")
    @Operation(summary = "Close telemetry point topology binding")
    public ResponseEntity<TelemetryPointBindingResponse> closeBinding(
            @PathVariable String bindingId,
            @RequestBody(required = false) CloseTelemetryPointBindingRequest request) {

        return ResponseEntity.ok(mapper.toResponse(closeTelemetryPointBindingUseCase.closeTelemetryPointBinding(mapper.toCommand(bindingId, request))));
    }

    @GetMapping("/point-bindings/{bindingId}")
    @Operation(summary = "Get telemetry point binding by id")
    public ResponseEntity<TelemetryPointBindingResponse> getBinding(@PathVariable String bindingId) {
        return ResponseEntity.ok(mapper.toResponse(getTelemetryPointBindingUseCase.getTelemetryPointBinding(mapper.toGetTelemetryPointBindingByIdQuery(bindingId))));
    }

    @GetMapping("/point-bindings")
    @Operation(summary = "List telemetry point bindings")
    public ResponseEntity<TelemetryPageResponse<TelemetryPointBindingResponse>> listBindings(
            @RequestParam(required = false) String pointId,
            @RequestParam(required = false) String assetTypeCode,
            @RequestParam(required = false) String assetId,
            @RequestParam(required = false) String bindingRoleId,
            @RequestParam(required = false) String bindingRoleCode,
            @RequestParam(required = false) Boolean active,
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size,
            @RequestParam(required = false) String sortField,
            @RequestParam(required = false) String sortDirection) {

        return ResponseEntity.ok(mapper.toPointBindingPageResponse(listTelemetryPointBindingsUseCase.listTelemetryPointBindings(
                mapper.toListTelemetryPointBindingsQuery(pointId, assetTypeCode, assetId, bindingRoleId, bindingRoleCode, active, page, size, sortField, sortDirection))));
    }

}
