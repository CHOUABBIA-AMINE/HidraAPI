/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TelemetryIngestionBatchController
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : RestController
 * @Layer       : API
 * @Module      : telemetry
 * @Package     : dz.sh.hidra.modules.telemetry.api.rest.controller
 *
 * @Description : REST controller for telemetry ingestion batch endpoints.
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

import dz.sh.hidra.modules.telemetry.api.rest.request.CompleteTelemetryIngestionBatchRequest;
import dz.sh.hidra.modules.telemetry.api.rest.request.FailTelemetryIngestionBatchRequest;
import dz.sh.hidra.modules.telemetry.api.rest.request.StartTelemetryIngestionBatchRequest;
import dz.sh.hidra.modules.telemetry.api.rest.response.TelemetryIngestionBatchResponse;
import dz.sh.hidra.modules.telemetry.api.rest.response.TelemetryPageResponse;
import dz.sh.hidra.modules.telemetry.application.port.in.CompleteTelemetryIngestionBatchUseCase;
import dz.sh.hidra.modules.telemetry.application.port.in.FailTelemetryIngestionBatchUseCase;
import dz.sh.hidra.modules.telemetry.application.port.in.GetTelemetryIngestionBatchUseCase;
import dz.sh.hidra.modules.telemetry.application.port.in.ListTelemetryIngestionBatchesUseCase;
import dz.sh.hidra.modules.telemetry.application.port.in.MarkTelemetryIngestionBatchProcessingUseCase;
import dz.sh.hidra.modules.telemetry.application.port.in.StartTelemetryIngestionBatchUseCase;

/**
 * REST controller for telemetry ingestion batch endpoints.
 *
 * <p>Architecture role:
 * Thin REST controller for the telemetry API. It delegates all command/query creation to
 * TelemetryRestMapper and all business work to application inbound ports.
 */
@RestController
@Validated
@RequestMapping("/api/v1/telemetry")
@Tag(name = "Telemetry")
public class TelemetryIngestionBatchController {

    private final StartTelemetryIngestionBatchUseCase startTelemetryIngestionBatchUseCase;
    private final MarkTelemetryIngestionBatchProcessingUseCase markTelemetryIngestionBatchProcessingUseCase;
    private final CompleteTelemetryIngestionBatchUseCase completeTelemetryIngestionBatchUseCase;
    private final FailTelemetryIngestionBatchUseCase failTelemetryIngestionBatchUseCase;
    private final GetTelemetryIngestionBatchUseCase getTelemetryIngestionBatchUseCase;
    private final ListTelemetryIngestionBatchesUseCase listTelemetryIngestionBatchesUseCase;
    private final TelemetryRestMapper mapper;

    public TelemetryIngestionBatchController(
            StartTelemetryIngestionBatchUseCase startTelemetryIngestionBatchUseCase,
            MarkTelemetryIngestionBatchProcessingUseCase markTelemetryIngestionBatchProcessingUseCase,
            CompleteTelemetryIngestionBatchUseCase completeTelemetryIngestionBatchUseCase,
            FailTelemetryIngestionBatchUseCase failTelemetryIngestionBatchUseCase,
            GetTelemetryIngestionBatchUseCase getTelemetryIngestionBatchUseCase,
            ListTelemetryIngestionBatchesUseCase listTelemetryIngestionBatchesUseCase,
            TelemetryRestMapper mapper) {

        this.startTelemetryIngestionBatchUseCase = Objects.requireNonNull(startTelemetryIngestionBatchUseCase, "StartTelemetryIngestionBatchUseCase must not be null.");
        this.markTelemetryIngestionBatchProcessingUseCase = Objects.requireNonNull(markTelemetryIngestionBatchProcessingUseCase, "MarkTelemetryIngestionBatchProcessingUseCase must not be null.");
        this.completeTelemetryIngestionBatchUseCase = Objects.requireNonNull(completeTelemetryIngestionBatchUseCase, "CompleteTelemetryIngestionBatchUseCase must not be null.");
        this.failTelemetryIngestionBatchUseCase = Objects.requireNonNull(failTelemetryIngestionBatchUseCase, "FailTelemetryIngestionBatchUseCase must not be null.");
        this.getTelemetryIngestionBatchUseCase = Objects.requireNonNull(getTelemetryIngestionBatchUseCase, "GetTelemetryIngestionBatchUseCase must not be null.");
        this.listTelemetryIngestionBatchesUseCase = Objects.requireNonNull(listTelemetryIngestionBatchesUseCase, "ListTelemetryIngestionBatchesUseCase must not be null.");
        this.mapper = Objects.requireNonNull(mapper, "TelemetryRestMapper must not be null.");
    }

    @PostMapping("/ingestion-batches")
    @Operation(summary = "Start telemetry ingestion batch")
    public ResponseEntity<TelemetryIngestionBatchResponse> startBatch(@Valid @RequestBody StartTelemetryIngestionBatchRequest request) {
        return ResponseEntity.ok(mapper.toResponse(startTelemetryIngestionBatchUseCase.startTelemetryIngestionBatch(mapper.toCommand(request))));
    }

    @PostMapping("/ingestion-batches/{batchId}/processing")
    @Operation(summary = "Mark telemetry ingestion batch processing")
    public ResponseEntity<TelemetryIngestionBatchResponse> markProcessing(@PathVariable String batchId) {
        return ResponseEntity.ok(mapper.toResponse(markTelemetryIngestionBatchProcessingUseCase.markTelemetryIngestionBatchProcessing(mapper.toMarkTelemetryIngestionBatchProcessingCommand(batchId))));
    }

    @PostMapping("/ingestion-batches/{batchId}/complete")
    @Operation(summary = "Complete telemetry ingestion batch")
    public ResponseEntity<TelemetryIngestionBatchResponse> completeBatch(@PathVariable String batchId, @Valid @RequestBody CompleteTelemetryIngestionBatchRequest request) {
        return ResponseEntity.ok(mapper.toResponse(completeTelemetryIngestionBatchUseCase.completeTelemetryIngestionBatch(mapper.toCommand(batchId, request))));
    }

    @PostMapping("/ingestion-batches/{batchId}/fail")
    @Operation(summary = "Fail telemetry ingestion batch")
    public ResponseEntity<TelemetryIngestionBatchResponse> failBatch(@PathVariable String batchId, @Valid @RequestBody FailTelemetryIngestionBatchRequest request) {
        return ResponseEntity.ok(mapper.toResponse(failTelemetryIngestionBatchUseCase.failTelemetryIngestionBatch(mapper.toCommand(batchId, request))));
    }

    @GetMapping("/ingestion-batches/{batchId}")
    @Operation(summary = "Get telemetry ingestion batch by id")
    public ResponseEntity<TelemetryIngestionBatchResponse> getBatch(@PathVariable String batchId) {
        return ResponseEntity.ok(mapper.toResponse(getTelemetryIngestionBatchUseCase.getTelemetryIngestionBatch(mapper.toGetTelemetryIngestionBatchByIdQuery(batchId))));
    }

    @GetMapping("/ingestion-batches")
    @Operation(summary = "List telemetry ingestion batches")
    public ResponseEntity<TelemetryPageResponse<TelemetryIngestionBatchResponse>> listBatches(
            @RequestParam(required = false) String sourceId,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) Instant startedFrom,
            @RequestParam(required = false) Instant startedTo,
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size,
            @RequestParam(required = false) String sortField,
            @RequestParam(required = false) String sortDirection) {

        return ResponseEntity.ok(mapper.toIngestionBatchPageResponse(listTelemetryIngestionBatchesUseCase.listTelemetryIngestionBatches(
                mapper.toListTelemetryIngestionBatchesQuery(sourceId, status, startedFrom, startedTo, page, size, sortField, sortDirection))));
    }

}
