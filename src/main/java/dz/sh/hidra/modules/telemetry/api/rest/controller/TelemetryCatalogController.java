/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TelemetryCatalogController
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : RestController
 * @Layer       : API
 * @Module      : telemetry
 * @Package     : dz.sh.hidra.modules.telemetry.api.rest.controller
 *
 * @Description : REST controller for telemetry controlled vocabulary catalog endpoints.
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

import dz.sh.hidra.modules.telemetry.api.rest.response.TelemetryCatalogResponse;
import dz.sh.hidra.modules.telemetry.api.rest.response.TelemetryPageResponse;
import dz.sh.hidra.modules.telemetry.application.port.in.GetTelemetryCatalogTypeUseCase;
import dz.sh.hidra.modules.telemetry.application.port.in.ListTelemetryCatalogTypesUseCase;
import dz.sh.hidra.modules.telemetry.application.port.in.ResolveTelemetryCatalogTypeUseCase;

/**
 * REST controller for telemetry controlled vocabulary catalog endpoints.
 *
 * <p>Architecture role:
 * Thin REST controller for the telemetry API. It delegates all command/query creation to
 * TelemetryRestMapper and all business work to application inbound ports.
 */
@RestController
@Validated
@RequestMapping("/api/v1/telemetry")
@Tag(name = "Telemetry")
public class TelemetryCatalogController {

    private final GetTelemetryCatalogTypeUseCase getTelemetryCatalogTypeUseCase;
    private final ListTelemetryCatalogTypesUseCase listTelemetryCatalogTypesUseCase;
    private final ResolveTelemetryCatalogTypeUseCase resolveTelemetryCatalogTypeUseCase;
    private final TelemetryRestMapper mapper;

    public TelemetryCatalogController(
            GetTelemetryCatalogTypeUseCase getTelemetryCatalogTypeUseCase,
            ListTelemetryCatalogTypesUseCase listTelemetryCatalogTypesUseCase,
            ResolveTelemetryCatalogTypeUseCase resolveTelemetryCatalogTypeUseCase,
            TelemetryRestMapper mapper) {

        this.getTelemetryCatalogTypeUseCase = Objects.requireNonNull(getTelemetryCatalogTypeUseCase, "GetTelemetryCatalogTypeUseCase must not be null.");
        this.listTelemetryCatalogTypesUseCase = Objects.requireNonNull(listTelemetryCatalogTypesUseCase, "ListTelemetryCatalogTypesUseCase must not be null.");
        this.resolveTelemetryCatalogTypeUseCase = Objects.requireNonNull(resolveTelemetryCatalogTypeUseCase, "ResolveTelemetryCatalogTypeUseCase must not be null.");
        this.mapper = Objects.requireNonNull(mapper, "TelemetryRestMapper must not be null.");
    }

    @GetMapping("/catalog-types/{typeId}")
    @Operation(summary = "Get telemetry catalog entry by id")
    public ResponseEntity<TelemetryCatalogResponse> getCatalogType(@PathVariable String typeId, @RequestParam(required = false) String locale) {
        return ResponseEntity.ok(mapper.toResponse(getTelemetryCatalogTypeUseCase.getTelemetryCatalogType(mapper.toGetTelemetryCatalogTypeQuery(typeId, locale))));
    }

    @GetMapping("/catalog-types")
    @Operation(summary = "List telemetry catalog entries")
    public ResponseEntity<TelemetryPageResponse<TelemetryCatalogResponse>> listCatalogTypes(
            @RequestParam(required = false) String catalogName,
            @RequestParam(required = false) String locale,
            @RequestParam(required = false) Boolean active,
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size,
            @RequestParam(required = false) String sortField,
            @RequestParam(required = false) String sortDirection) {

        return ResponseEntity.ok(mapper.toCatalogPageResponse(listTelemetryCatalogTypesUseCase.listTelemetryCatalogTypes(
                mapper.toListTelemetryCatalogTypesQuery(catalogName, locale, active, page, size, sortField, sortDirection))));
    }

    @GetMapping("/catalog-types/resolve")
    @Operation(summary = "Resolve telemetry catalog entry by catalog name and code")
    public ResponseEntity<TelemetryCatalogResponse> resolveCatalogType(
            @RequestParam String catalogName,
            @RequestParam String code,
            @RequestParam(required = false) Boolean requireActive,
            @RequestParam(required = false) String locale) {

        return ResponseEntity.ok(mapper.toResponse(resolveTelemetryCatalogTypeUseCase.resolveTelemetryCatalogType(
                mapper.toResolveTelemetryCatalogTypeQuery(catalogName, code, requireActive, locale))));
    }

}
