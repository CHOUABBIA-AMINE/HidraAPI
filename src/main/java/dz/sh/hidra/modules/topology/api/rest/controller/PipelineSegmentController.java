/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : PipelineSegmentController
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Class
 * @Layer       : API
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.api.rest.controller
 *
 * @Description : REST controller for topology pipeline segments.
 *
 */
package dz.sh.hidra.modules.topology.api.rest.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import dz.sh.hidra.kernel.application.pagination.PageResult;
import dz.sh.hidra.modules.topology.api.rest.mapper.TopologyRestMapper;
import dz.sh.hidra.modules.topology.api.rest.request.CreatePipelineSegmentRequest;
import dz.sh.hidra.modules.topology.api.rest.response.PipelineSegmentResponse;
import dz.sh.hidra.modules.topology.application.port.in.CreatePipelineSegmentUseCase;
import dz.sh.hidra.modules.topology.application.port.in.ListPipelineSegmentsUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

/**
 * REST controller exposing topology pipeline segment endpoints.
 *
 * <p>Business role:
 * Exposes physical pipe section creation and listing between topology nodes.
 *
 * <p>Architecture role:
 * This controller depends only on pipeline segment inbound ports and TopologyRestMapper. It does not
 * expose get-by-id because no pipeline segment get use case exists in the current application ports.
 *
 * <p>Validation:
 * Request bodies use Bean Validation with @Valid. Query pagination parameters are bounded.
 *
 * <p>Usage:
 * Use endpoints under /api/v1/topology/pipeline-segments.
 */
@RestController
@RequestMapping("/api/v1/topology/pipeline-segments")
@Tag(name = "Topology Pipeline Segments", description = "Topology pipeline segment endpoints.")
public class PipelineSegmentController {

    private final CreatePipelineSegmentUseCase createPipelineSegmentUseCase;
    private final ListPipelineSegmentsUseCase listPipelineSegmentsUseCase;
    private final TopologyRestMapper mapper;

    public PipelineSegmentController(
            CreatePipelineSegmentUseCase createPipelineSegmentUseCase,
            ListPipelineSegmentsUseCase listPipelineSegmentsUseCase,
            TopologyRestMapper mapper) {

        this.createPipelineSegmentUseCase = createPipelineSegmentUseCase;
        this.listPipelineSegmentsUseCase = listPipelineSegmentsUseCase;
        this.mapper = mapper;
    }

    @PostMapping
    @Operation(summary = "Create pipeline segment", description = "Creates a physical pipeline segment between two topology nodes.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Pipeline segment created."),
            @ApiResponse(responseCode = "400", description = "Invalid pipeline segment creation request."),
            @ApiResponse(responseCode = "404", description = "Referenced pipeline or node was not found."),
            @ApiResponse(responseCode = "409", description = "Pipeline segment code conflicts with an existing segment."),
            @ApiResponse(responseCode = "500", description = "Unexpected server error.")
    })
    public ResponseEntity<PipelineSegmentResponse> createPipelineSegment(
            @Valid @RequestBody CreatePipelineSegmentRequest request) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(mapper.toResponse(createPipelineSegmentUseCase.createPipelineSegment(mapper.toCommand(request))));
    }

    @GetMapping
    @Operation(summary = "List pipeline segments", description = "Lists pipeline segments with optional search, pipeline, endpoint, and status filters.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Pipeline segments listed."),
            @ApiResponse(responseCode = "400", description = "Invalid list pipeline segments query parameters."),
            @ApiResponse(responseCode = "500", description = "Unexpected server error.")
    })
    public PageResult<PipelineSegmentResponse> listPipelineSegments(
            @RequestParam(required = false) String searchText,
            @RequestParam(required = false) String pipelineId,
            @RequestParam(required = false) String fromNodeId,
            @RequestParam(required = false) String toNodeId,
            @RequestParam(required = false) String status,
            @RequestParam(defaultValue = "0") @Min(0) int page,
            @RequestParam(defaultValue = "20") @Min(1) @Max(200) int size) {

        return mapper.toPipelineSegmentResponsePage(listPipelineSegmentsUseCase.listPipelineSegments(
                mapper.toListPipelineSegmentsQuery(searchText, pipelineId, fromNodeId, toNodeId, status, page, size)));
    }
}
