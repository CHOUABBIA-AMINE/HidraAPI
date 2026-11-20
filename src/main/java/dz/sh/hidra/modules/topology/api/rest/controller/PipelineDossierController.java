/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : PipelineDossierController
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-20
 *
 * @Type        : Class
 * @Layer       : API
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.api.rest.controller
 *
 * @Description : Exposes six ordinary permission-protected, non-cacheable regional dossier reads.
 *
 */
package dz.sh.hidra.modules.topology.api.rest.controller;

import dz.sh.hidra.modules.topology.api.rest.response.PipelineDossierResponses;
import dz.sh.hidra.modules.topology.api.rest.response.PipelineDossierResponses.*;
import dz.sh.hidra.modules.topology.application.port.in.PipelineDossierReadUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Pipeline Dossiers", description = "Current-scope Topology facts, not operating limits")
@RequestMapping("/api/v1/topology/pipeline-dossiers/{pipelineId}")
public final class PipelineDossierController {
    private final PipelineDossierReadUseCase useCase;
    public PipelineDossierController(PipelineDossierReadUseCase useCase) {
        this.useCase = Objects.requireNonNull(useCase);
    }

    @GetMapping
    @Operation(summary = "Read authorized pipeline identity, design and current source metadata")
    @ApiResponse(responseCode = "200", description = "Current live catalog facts only")
    @ApiResponse(responseCode = "404", description = "Unavailable or unauthorized pipeline")
    public ResponseEntity<PipelineView> pipeline(@PathVariable String pipelineId,
            @RequestParam("companyPartyId") String companyPartyId) {
        return ok(PipelineDossierResponses.pipeline(useCase.pipeline(companyPartyId, pipelineId)));
    }

    @GetMapping("/sections")
    @Operation(summary = "Read authorized pipeline sections, including sections without geometry")
    @ApiResponse(responseCode = "200", description = "Post-authorization paginated sections")
    public ResponseEntity<SectionsPage> sections(@PathVariable String pipelineId,
            @RequestParam("companyPartyId") String companyPartyId,
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size) {
        return ok(PipelineDossierResponses.sections(useCase.sections(companyPartyId, pipelineId, page, size)));
    }

    @GetMapping("/sections/{sectionId}")
    @Operation(summary = "Read a permitted section belonging to the exact selected pipeline")
    @ApiResponse(responseCode = "404", description = "Absent, foreign or unauthorized section")
    public ResponseEntity<SectionView> section(@PathVariable String pipelineId,
            @PathVariable String sectionId, @RequestParam("companyPartyId") String companyPartyId) {
        return ok(PipelineDossierResponses.section(useCase.section(companyPartyId, pipelineId, sectionId)));
    }

    @GetMapping("/connections")
    @Operation(summary = "Read only authorized and structurally coherent segment connections")
    public ResponseEntity<ConnectionsPage> connections(@PathVariable String pipelineId,
            @RequestParam("companyPartyId") String companyPartyId,
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size) {
        return ok(PipelineDossierResponses.connections(
                useCase.connections(companyPartyId, pipelineId, page, size)));
    }

    @GetMapping("/equipment")
    @Operation(summary = "Read installed equipment explicitly linked to permitted sections")
    public ResponseEntity<EquipmentPage> equipment(@PathVariable String pipelineId,
            @RequestParam("companyPartyId") String companyPartyId,
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size) {
        return ok(PipelineDossierResponses.equipment(
                useCase.equipment(companyPartyId, pipelineId, page, size)));
    }

    @GetMapping("/baseline")
    @Operation(summary = "Read the separately authorized current pipeline-system publication receipt")
    @ApiResponse(responseCode = "200", description = "Exact system receipt or explicit UNAVAILABLE")
    @ApiResponse(responseCode = "404", description = "Current owner or source scope unavailable")
    public ResponseEntity<BaselineView> baseline(@PathVariable String pipelineId,
            @RequestParam("companyPartyId") String companyPartyId) {
        return ok(PipelineDossierResponses.baseline(useCase.baseline(companyPartyId, pipelineId)));
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<ProblemDetail> missing(NoSuchElementException ignored) {
        ProblemDetail detail = ProblemDetail.forStatus(HttpStatus.NOT_FOUND);
        detail.setDetail("Resource unavailable");
        return response(HttpStatus.NOT_FOUND, detail);
    }
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ProblemDetail> invalid(IllegalArgumentException ignored) {
        ProblemDetail detail = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        detail.setDetail("Invalid pipeline dossier request");
        return response(HttpStatus.BAD_REQUEST, detail);
    }
    private static <T> ResponseEntity<T> ok(T body) {
        return ResponseEntity.ok().header(HttpHeaders.CACHE_CONTROL, "private, no-store").body(body);
    }
    private static <T> ResponseEntity<T> response(HttpStatus status, T body) {
        return ResponseEntity.status(status).header(HttpHeaders.CACHE_CONTROL, "private, no-store").body(body);
    }
}
