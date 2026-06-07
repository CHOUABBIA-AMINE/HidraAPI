/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TelemetryReadingController
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : RestController
 * @Layer       : API
 * @Module      : telemetry
 * @Package     : dz.sh.hidra.modules.telemetry.api.rest.controller
 *
 * @Description : REST controller for telemetry reading endpoints.
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

import dz.sh.hidra.modules.telemetry.api.rest.request.QuarantineTelemetryReadingRequest;
import dz.sh.hidra.modules.telemetry.api.rest.request.ReceiveTelemetryReadingRequest;
import dz.sh.hidra.modules.telemetry.api.rest.request.RejectTelemetryReadingRequest;
import dz.sh.hidra.modules.telemetry.api.rest.response.TelemetryPageResponse;
import dz.sh.hidra.modules.telemetry.api.rest.response.TelemetryReadingResponse;
import dz.sh.hidra.modules.telemetry.application.port.in.AcceptTelemetryReadingUseCase;
import dz.sh.hidra.modules.telemetry.application.port.in.GetLatestTelemetryReadingUseCase;
import dz.sh.hidra.modules.telemetry.application.port.in.GetTelemetryReadingUseCase;
import dz.sh.hidra.modules.telemetry.application.port.in.ListTelemetryReadingsUseCase;
import dz.sh.hidra.modules.telemetry.application.port.in.MarkTelemetryReadingDuplicateUseCase;
import dz.sh.hidra.modules.telemetry.application.port.in.QuarantineTelemetryReadingUseCase;
import dz.sh.hidra.modules.telemetry.application.port.in.ReceiveTelemetryReadingUseCase;
import dz.sh.hidra.modules.telemetry.application.port.in.RejectTelemetryReadingUseCase;

/**
 * REST controller for telemetry reading endpoints.
 *
 * <p>Architecture role:
 * Thin REST controller for the telemetry API. It delegates all command/query creation to
 * TelemetryRestMapper and all business work to application inbound ports.
 */
@RestController
@Validated
@RequestMapping("/api/v1/telemetry")
@Tag(name = "Telemetry")
public class TelemetryReadingController {

    private final ReceiveTelemetryReadingUseCase receiveTelemetryReadingUseCase;
    private final AcceptTelemetryReadingUseCase acceptTelemetryReadingUseCase;
    private final RejectTelemetryReadingUseCase rejectTelemetryReadingUseCase;
    private final QuarantineTelemetryReadingUseCase quarantineTelemetryReadingUseCase;
    private final MarkTelemetryReadingDuplicateUseCase markTelemetryReadingDuplicateUseCase;
    private final GetTelemetryReadingUseCase getTelemetryReadingUseCase;
    private final GetLatestTelemetryReadingUseCase getLatestTelemetryReadingUseCase;
    private final ListTelemetryReadingsUseCase listTelemetryReadingsUseCase;
    private final TelemetryRestMapper mapper;

    public TelemetryReadingController(
            ReceiveTelemetryReadingUseCase receiveTelemetryReadingUseCase,
            AcceptTelemetryReadingUseCase acceptTelemetryReadingUseCase,
            RejectTelemetryReadingUseCase rejectTelemetryReadingUseCase,
            QuarantineTelemetryReadingUseCase quarantineTelemetryReadingUseCase,
            MarkTelemetryReadingDuplicateUseCase markTelemetryReadingDuplicateUseCase,
            GetTelemetryReadingUseCase getTelemetryReadingUseCase,
            GetLatestTelemetryReadingUseCase getLatestTelemetryReadingUseCase,
            ListTelemetryReadingsUseCase listTelemetryReadingsUseCase,
            TelemetryRestMapper mapper) {

        this.receiveTelemetryReadingUseCase = Objects.requireNonNull(receiveTelemetryReadingUseCase, "ReceiveTelemetryReadingUseCase must not be null.");
        this.acceptTelemetryReadingUseCase = Objects.requireNonNull(acceptTelemetryReadingUseCase, "AcceptTelemetryReadingUseCase must not be null.");
        this.rejectTelemetryReadingUseCase = Objects.requireNonNull(rejectTelemetryReadingUseCase, "RejectTelemetryReadingUseCase must not be null.");
        this.quarantineTelemetryReadingUseCase = Objects.requireNonNull(quarantineTelemetryReadingUseCase, "QuarantineTelemetryReadingUseCase must not be null.");
        this.markTelemetryReadingDuplicateUseCase = Objects.requireNonNull(markTelemetryReadingDuplicateUseCase, "MarkTelemetryReadingDuplicateUseCase must not be null.");
        this.getTelemetryReadingUseCase = Objects.requireNonNull(getTelemetryReadingUseCase, "GetTelemetryReadingUseCase must not be null.");
        this.getLatestTelemetryReadingUseCase = Objects.requireNonNull(getLatestTelemetryReadingUseCase, "GetLatestTelemetryReadingUseCase must not be null.");
        this.listTelemetryReadingsUseCase = Objects.requireNonNull(listTelemetryReadingsUseCase, "ListTelemetryReadingsUseCase must not be null.");
        this.mapper = Objects.requireNonNull(mapper, "TelemetryRestMapper must not be null.");
    }

    @PostMapping("/readings")
    @Operation(summary = "Receive telemetry reading")
    public ResponseEntity<TelemetryReadingResponse> receiveReading(@Valid @RequestBody ReceiveTelemetryReadingRequest request) {
        return ResponseEntity.ok(mapper.toResponse(receiveTelemetryReadingUseCase.receiveTelemetryReading(mapper.toCommand(request))));
    }

    @PostMapping("/readings/{readingId}/accept")
    @Operation(summary = "Accept telemetry reading")
    public ResponseEntity<TelemetryReadingResponse> acceptReading(@PathVariable String readingId) {
        return ResponseEntity.ok(mapper.toResponse(acceptTelemetryReadingUseCase.acceptTelemetryReading(mapper.toAcceptTelemetryReadingCommand(readingId))));
    }

    @PostMapping("/readings/{readingId}/reject")
    @Operation(summary = "Reject telemetry reading")
    public ResponseEntity<TelemetryReadingResponse> rejectReading(@PathVariable String readingId, @Valid @RequestBody RejectTelemetryReadingRequest request) {
        return ResponseEntity.ok(mapper.toResponse(rejectTelemetryReadingUseCase.rejectTelemetryReading(mapper.toCommand(readingId, request))));
    }

    @PostMapping("/readings/{readingId}/quarantine")
    @Operation(summary = "Quarantine telemetry reading")
    public ResponseEntity<TelemetryReadingResponse> quarantineReading(@PathVariable String readingId, @Valid @RequestBody QuarantineTelemetryReadingRequest request) {
        return ResponseEntity.ok(mapper.toResponse(quarantineTelemetryReadingUseCase.quarantineTelemetryReading(mapper.toCommand(readingId, request))));
    }

    @PostMapping("/readings/{readingId}/duplicate")
    @Operation(summary = "Mark telemetry reading duplicate")
    public ResponseEntity<TelemetryReadingResponse> markDuplicate(@PathVariable String readingId) {
        return ResponseEntity.ok(mapper.toResponse(markTelemetryReadingDuplicateUseCase.markTelemetryReadingDuplicate(mapper.toMarkTelemetryReadingDuplicateCommand(readingId))));
    }

    @GetMapping("/readings/{readingId}")
    @Operation(summary = "Get telemetry reading by id")
    public ResponseEntity<TelemetryReadingResponse> getReading(@PathVariable String readingId) {
        return ResponseEntity.ok(mapper.toResponse(getTelemetryReadingUseCase.getTelemetryReading(mapper.toGetTelemetryReadingByIdQuery(readingId))));
    }

    @GetMapping("/points/{pointId}/readings/latest")
    @Operation(summary = "Get latest telemetry reading for a point")
    public ResponseEntity<TelemetryReadingResponse> getLatestReading(@PathVariable String pointId) {
        return ResponseEntity.ok(mapper.toResponse(getLatestTelemetryReadingUseCase.getLatestTelemetryReading(mapper.toGetLatestTelemetryReadingQuery(pointId))));
    }

    @GetMapping("/readings")
    @Operation(summary = "List telemetry readings")
    public ResponseEntity<TelemetryPageResponse<TelemetryReadingResponse>> listReadings(
            @RequestParam(required = false) String pointId,
            @RequestParam(required = false) String qualityCodeId,
            @RequestParam(required = false) String qualityCode,
            @RequestParam(required = false) String state,
            @RequestParam(required = false) Instant fromSourceTimestamp,
            @RequestParam(required = false) Instant toSourceTimestamp,
            @RequestParam(required = false) String ingestionBatchId,
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size,
            @RequestParam(required = false) String sortField,
            @RequestParam(required = false) String sortDirection) {

        return ResponseEntity.ok(mapper.toReadingPageResponse(listTelemetryReadingsUseCase.listTelemetryReadings(
                mapper.toListTelemetryReadingsQuery(pointId, qualityCodeId, qualityCode, state, fromSourceTimestamp, toSourceTimestamp, ingestionBatchId, page, size, sortField, sortDirection))));
    }

}
