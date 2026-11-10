/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TelemetryReadingIngestionController
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-10
 *
 * @Type        : Class
 * @Layer       : API
 * @Module      : telemetry
 * @Package     : dz.sh.hidra.modules.telemetry.api.rest.controller
 *
 * @Description : Exposes authenticated raw observation ingestion without trust promotion.
 *
 */
package dz.sh.hidra.modules.telemetry.api.rest.controller;


import dz.sh.hidra.modules.telemetry.api.rest.mapper.TelemetryReadingIngestionRestMapper;
import dz.sh.hidra.modules.telemetry.api.rest.request.IngestTelemetryReadingRequest;
import dz.sh.hidra.modules.telemetry.api.rest.response.IngestTelemetryReadingResponse;
import dz.sh.hidra.modules.telemetry.application.port.in.IngestTelemetryReadingUseCase;
import dz.sh.hidra.modules.telemetry.domain.exception.TelemetryReadingReplayConflictException;
import dz.sh.hidra.modules.telemetry.domain.exception.InvalidTelemetryValueException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import java.util.Objects;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/telemetry")
public final class TelemetryReadingIngestionController {
    private final IngestTelemetryReadingUseCase ingest;
    public TelemetryReadingIngestionController(IngestTelemetryReadingUseCase ingest) {
        this.ingest=Objects.requireNonNull(ingest);
    }

    @Operation(operationId="ingestTelemetryReading", summary="Append one scoped synthetic raw observation")
    @ApiResponse(responseCode="201",description="Accepted raw receipt; no trust qualification")
    @ApiResponse(responseCode="200",description="Authorized identical durable replay")
    @ApiResponse(responseCode="409",description="Conflicting canonical observation for existing sequence",content=@Content)
    @ApiResponse(responseCode="403",description="No current native source/point/company permission",content=@Content)
    @PostMapping("/points/{pointId}/readings")
    public ResponseEntity<IngestTelemetryReadingResponse> ingest(
            @PathVariable String pointId, @Valid @RequestBody IngestTelemetryReadingRequest request) {
        var result=ingest.ingest(TelemetryReadingIngestionRestMapper.toCommand(pointId,request));
        return ResponseEntity.status(result.replayed()?HttpStatus.OK:HttpStatus.CREATED)
                .body(TelemetryReadingIngestionRestMapper.toResponse(result));
    }

    @ExceptionHandler(TelemetryReadingReplayConflictException.class)
    public ResponseEntity<ProblemDetail> replayConflict(TelemetryReadingReplayConflictException failure) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(ProblemDetail.forStatusAndDetail(HttpStatus.CONFLICT,"Conflicting source sequence."));
    }
    @ExceptionHandler(InvalidTelemetryValueException.class)
    public ResponseEntity<ProblemDetail> invalidReading(InvalidTelemetryValueException failure) {
        return ResponseEntity.badRequest().body(
                ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST,"Invalid raw telemetry observation."));
    }
}
