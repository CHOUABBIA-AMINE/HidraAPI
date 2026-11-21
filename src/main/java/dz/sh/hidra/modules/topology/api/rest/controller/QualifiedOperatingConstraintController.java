/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : QualifiedOperatingConstraintController
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-21
 *
 * @Type        : Class
 * @Layer       : API
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.api.rest.controller
 *
 * @Description : Enforces explicit point-in-time ordinary protected Topology operating constraint reads.
 *
 */
package dz.sh.hidra.modules.topology.api.rest.controller;

import dz.sh.hidra.modules.topology.application.port.in.QualifiedOperatingConstraintReadUseCase;
import dz.sh.hidra.modules.topology.application.port.in.QualifiedOperatingConstraintReadUseCase.Query;
import dz.sh.hidra.modules.topology.api.rest.response.QualifiedOperatingConstraintResponse;
import dz.sh.hidra.modules.topology.api.rest.response.QualifiedOperatingConstraintResponse.QualifiedOperatingConstraintView;
import io.swagger.v3.oas.annotations.Operation;
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

@RestController
@Tag(name = "Qualified Operating Constraints", description = "Owner-qualified read-only pressure restrictions; never design pressure")
@RequestMapping("/api/v1/topology/operating-constraints/pipelines/{pipelineId}")
public final class QualifiedOperatingConstraintController {
    private static final org.slf4j.Logger LOG = org.slf4j.LoggerFactory
            .getLogger(QualifiedOperatingConstraintController.class);
    private final QualifiedOperatingConstraintReadUseCase read;
    public QualifiedOperatingConstraintController(QualifiedOperatingConstraintReadUseCase read) {
        this.read = Objects.requireNonNull(read);
    }
    @GetMapping
    @Operation(summary = "Resolve a qualified upper operating-pressure restriction for one authorized pipeline")
    @ApiResponse(responseCode = "200", description = "QUALIFIED, UNAVAILABLE or disclosure-safe CONFLICT")
    @ApiResponse(responseCode = "404", description = "Target missing or unauthorized")
    public ResponseEntity<QualifiedOperatingConstraintView> pipeline(@PathVariable String pipelineId,
            @RequestParam String companyPartyId, @RequestParam String effectiveAt,
            @RequestParam String conditionCode) {
        return response(new Query(companyPartyId, pipelineId, null, conditionCode, instant(effectiveAt)));
    }
    @GetMapping("/sections/{sectionId}")
    @Operation(summary = "Resolve a qualified restriction for an exact authorized section, without inheritance")
    @ApiResponse(responseCode = "200", description = "QUALIFIED, UNAVAILABLE or disclosure-safe CONFLICT")
    @ApiResponse(responseCode = "404", description = "Target missing, wrong parent or unauthorized")
    public ResponseEntity<QualifiedOperatingConstraintView> section(@PathVariable String pipelineId,
            @PathVariable String sectionId, @RequestParam String companyPartyId,
            @RequestParam String effectiveAt, @RequestParam String conditionCode) {
        return response(new Query(companyPartyId, pipelineId, sectionId, conditionCode, instant(effectiveAt)));
    }
    private ResponseEntity<QualifiedOperatingConstraintView> response(Query request) {
        return ResponseEntity.ok().header(HttpHeaders.CACHE_CONTROL, "private, no-store")
                .body(QualifiedOperatingConstraintResponse.of(read.read(request)));
    }
    private static Instant instant(String raw) {
        if (raw == null || !raw.endsWith("Z") || raw.contains("+"))
            throw new IllegalArgumentException("Explicit UTC effectiveAt required");
        try {
            Instant instant = Instant.parse(raw);
            if (instant.getNano() % 1000 != 0)
                throw new IllegalArgumentException("PostgreSQL microsecond-aligned effectiveAt required");
            return instant;
        } catch (DateTimeParseException invalid) {
            throw new IllegalArgumentException("Invalid effectiveAt", invalid);
        }
    }
    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<ProblemDetail> missing(NoSuchElementException ignored) {
        return error(HttpStatus.NOT_FOUND, "Resource unavailable");
    }
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ProblemDetail> invalid(IllegalArgumentException failure) {
        var origin = java.util.Arrays.stream(failure.getStackTrace()).limit(10)
                .map(StackTraceElement::toString).toList();
        LOG.debug("T36 constraint read rejected ({}): {}", failure.getClass().getName(), origin);
        return error(HttpStatus.BAD_REQUEST, "Invalid qualified operating constraint request");
    }
    private static ResponseEntity<ProblemDetail> error(HttpStatus status, String message) {
        ProblemDetail detail = ProblemDetail.forStatus(status);
        detail.setDetail(message);
        return ResponseEntity.status(status).header(HttpHeaders.CACHE_CONTROL, "private, no-store").body(detail);
    }
}
