/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AssetScopedTrustedObservationController
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-21
 *
 * @Type        : Class
 * @Layer       : API
 * @Module      : telemetry
 * @Package     : dz.sh.hidra.modules.telemetry.api.rest.controller
 *
 * @Description : Exposes protected commissioned point, latest observation and history reads.
 *
 */
package dz.sh.hidra.modules.telemetry.api.rest.controller;

import dz.sh.hidra.modules.telemetry.api.rest.response.AssetScopedTrustedObservationResponses;
import dz.sh.hidra.modules.telemetry.api.rest.response.AssetScopedTrustedObservationResponses.CommissionedPointsView;
import dz.sh.hidra.modules.telemetry.api.rest.response.AssetScopedTrustedObservationResponses.LatestObservationView;
import dz.sh.hidra.modules.telemetry.api.rest.response.AssetScopedTrustedObservationResponses.ObservationHistoryView;
import dz.sh.hidra.modules.telemetry.application.port.in.AssetScopedTrustedObservationReadUseCase;
import dz.sh.hidra.modules.telemetry.application.port.in.AssetScopedTrustedObservationReadUseCase.Asset;
import dz.sh.hidra.modules.telemetry.application.port.in.AssetScopedTrustedObservationReadUseCase.AssetType;
import dz.sh.hidra.modules.telemetry.application.port.in.AssetScopedTrustedObservationReadUseCase.DiscoveryQuery;
import dz.sh.hidra.modules.telemetry.application.port.in.AssetScopedTrustedObservationReadUseCase.HistoryCursor;
import dz.sh.hidra.modules.telemetry.application.port.in.AssetScopedTrustedObservationReadUseCase.HistoryQuery;
import dz.sh.hidra.modules.telemetry.application.port.in.AssetScopedTrustedObservationReadUseCase.LatestQuery;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.time.Instant;
import java.time.format.DateTimeParseException;
import java.util.NoSuchElementException;
import java.util.Objects;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@RestController
@RequestMapping("/api/v1/telemetry/assets/{assetType}/{assetId}/points")
@Tag(name = "Asset Telemetry", description = "Authorized commissioned points and trusted observation reads")
@ApiResponse(responseCode = "401", description = "Authentication required")
@ApiResponse(responseCode = "403", description = "Ordinary route permission denied")
@ApiResponse(responseCode = "404", description = "Target missing or outside authorized scope")
@ApiResponse(responseCode = "400", description = "Invalid asset observation query")
public final class AssetScopedTrustedObservationController {
    private final AssetScopedTrustedObservationReadUseCase read;

    public AssetScopedTrustedObservationController(AssetScopedTrustedObservationReadUseCase read) {
        this.read = Objects.requireNonNull(read);
    }

    @GetMapping
    @Operation(operationId = "listAssetScopedTelemetryPoints", summary = "Discover authorized commissioned asset points")
    public ResponseEntity<CommissionedPointsView> discover(@PathVariable AssetType assetType, @PathVariable String assetId,
            @RequestParam String companyPartyId,
            @Parameter(schema = @Schema(type = "string", format = "date-time")) @RequestParam(required = false) String at,
            @Parameter(schema = @Schema(minimum = "1", maximum = "50")) @RequestParam(defaultValue = "20") int limit,
            @RequestParam(required = false) String afterPointId) {
        var query = new DiscoveryQuery(new Asset(companyPartyId, assetType, assetId), optionalInstant(at),
                limit, afterPointId);
        return ok(AssetScopedTrustedObservationResponses.discovery(read.discover(query)));
    }

    @GetMapping("/{pointId}/trusted-readings/latest")
    @Operation(operationId = "readLatestAssetScopedTrustedObservation", summary = "Read the latest trusted current-binding observation")
    public ResponseEntity<LatestObservationView> latest(@PathVariable AssetType assetType, @PathVariable String assetId,
            @PathVariable String pointId, @RequestParam String companyPartyId,
            @Parameter(schema = @Schema(minimum = "1", maximum = "604800")) @RequestParam int maxAgeSeconds) {
        var query = new LatestQuery(new Asset(companyPartyId, assetType, assetId), pointId, maxAgeSeconds);
        return ok(AssetScopedTrustedObservationResponses.latest(read.latest(query)));
    }

    @GetMapping("/{pointId}/trusted-readings/history")
    @Operation(operationId = "listAssetScopedTrustedObservationHistory", summary = "Read trusted observation history using the recorded binding")
    public ResponseEntity<ObservationHistoryView> history(@PathVariable AssetType assetType, @PathVariable String assetId,
            @PathVariable String pointId, @RequestParam String companyPartyId,
            @Parameter(schema = @Schema(type = "string", format = "date-time")) @RequestParam String from,
            @Parameter(schema = @Schema(type = "string", format = "date-time")) @RequestParam String until,
            @Parameter(schema = @Schema(minimum = "1", maximum = "50")) @RequestParam(defaultValue = "20") int limit,
            @Parameter(schema = @Schema(type = "string", format = "date-time")) @RequestParam(required = false) String afterObservedAt,
            @RequestParam(required = false) String afterTrustedId,
            @Parameter(schema = @Schema(minimum = "1", maximum = "604800")) @RequestParam int maxAgeSeconds) {
        if ((afterObservedAt == null) != (afterTrustedId == null)) throw new IllegalArgumentException("Incomplete cursor");
        var cursor = afterObservedAt == null ? null : new HistoryCursor(instant(afterObservedAt), afterTrustedId);
        var query = new HistoryQuery(new Asset(companyPartyId, assetType, assetId), pointId, instant(from), instant(until),
                limit, cursor, maxAgeSeconds);
        return ok(AssetScopedTrustedObservationResponses.history(read.history(query)));
    }

    private static Instant optionalInstant(String value) {
        return value == null ? null : instant(value);
    }

    private static Instant instant(String value) {
        if (!value.endsWith("Z")) throw new IllegalArgumentException("Explicit UTC required");
        try {
            Instant instant = Instant.parse(value);
            if (instant.getNano() % 1000 != 0) throw new IllegalArgumentException("Microsecond precision required");
            return instant;
        } catch (DateTimeParseException invalid) {
            throw new IllegalArgumentException("Invalid observation timestamp", invalid);
        }
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<ProblemDetail> unavailable() {
        return problem(HttpStatus.NOT_FOUND, "Resource unavailable");
    }

    @ExceptionHandler({IllegalArgumentException.class, MethodArgumentTypeMismatchException.class})
    public ResponseEntity<ProblemDetail> invalid() {
        return problem(HttpStatus.BAD_REQUEST, "Invalid asset observation query");
    }

    private static <T> ResponseEntity<T> ok(T body) {
        return ResponseEntity.ok().header(HttpHeaders.CACHE_CONTROL, "private, no-store").body(body);
    }

    private static ResponseEntity<ProblemDetail> problem(HttpStatus status, String detail) {
        return ResponseEntity.status(status).header(HttpHeaders.CACHE_CONTROL, "private, no-store")
                .body(ProblemDetail.forStatusAndDetail(status, detail));
    }
}
