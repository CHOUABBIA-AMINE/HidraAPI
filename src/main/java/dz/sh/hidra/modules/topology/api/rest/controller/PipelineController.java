/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : PipelineController
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-06
 *
 * @Type        : Class
 * @Layer       : API
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.api.rest.controller
 *
 * @Description : REST controller for topology pipelines.
 *
 */
package dz.sh.hidra.modules.topology.api.rest.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import dz.sh.hidra.kernel.application.pagination.PageResult;
import dz.sh.hidra.modules.topology.api.rest.mapper.TopologyRestMapper;
import dz.sh.hidra.modules.topology.api.rest.request.CreatePipelineRequest;
import dz.sh.hidra.modules.topology.api.rest.response.PipelineResponse;
import dz.sh.hidra.modules.topology.application.port.in.CreatePipelineUseCase;
import dz.sh.hidra.modules.topology.application.port.in.GetPipelineUseCase;
import dz.sh.hidra.modules.topology.application.port.in.ListPipelinesUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

@RestController
@RequestMapping("/api/v1/topology/pipelines")
@Tag(name = "Topology Pipelines", description = "Topology pipeline endpoints.")
public class PipelineController {

    private final CreatePipelineUseCase createPipelineUseCase;
    private final GetPipelineUseCase getPipelineUseCase;
    private final ListPipelinesUseCase listPipelinesUseCase;
    private final TopologyRestMapper mapper;

    public PipelineController(
            CreatePipelineUseCase createPipelineUseCase,
            GetPipelineUseCase getPipelineUseCase,
            ListPipelinesUseCase listPipelinesUseCase,
            TopologyRestMapper mapper) {

        this.createPipelineUseCase = createPipelineUseCase;
        this.getPipelineUseCase = getPipelineUseCase;
        this.listPipelinesUseCase = listPipelinesUseCase;
        this.mapper = mapper;
    }

    @PostMapping
    @Operation(summary = "Create pipeline", description = "Creates a topology pipeline under a pipeline system.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Pipeline created."),
            @ApiResponse(responseCode = "400", description = "Invalid pipeline creation request."),
            @ApiResponse(responseCode = "404", description = "Parent pipeline system not found."),
            @ApiResponse(responseCode = "409", description = "Pipeline code conflicts with an existing pipeline."),
            @ApiResponse(responseCode = "500", description = "Unexpected server error.")
    })
    public ResponseEntity<PipelineResponse> createPipeline(
            @Parameter(description = "Preferred response language using BCP 47 language tags.", example = "fr")
            @RequestHeader(name = "Accept-Language", required = false) String acceptLanguage,
            @Valid @RequestBody CreatePipelineRequest request) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(mapper.toResponse(
                        createPipelineUseCase.createPipeline(mapper.toCommand(request, acceptLanguage)),
                        acceptLanguage));
    }

    @GetMapping("/{pipelineId}")
    @Operation(summary = "Get pipeline", description = "Retrieves one topology pipeline by identifier.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Pipeline found."),
            @ApiResponse(responseCode = "400", description = "Invalid pipeline identifier."),
            @ApiResponse(responseCode = "404", description = "Pipeline not found."),
            @ApiResponse(responseCode = "500", description = "Unexpected server error.")
    })
    public ResponseEntity<PipelineResponse> getPipeline(
            @Parameter(description = "Preferred response language using BCP 47 language tags.", example = "fr")
            @RequestHeader(name = "Accept-Language", required = false) String acceptLanguage,
            @Parameter(description = "Pipeline identifier.", required = true, example = "pipeline_550e8400-e29b-41d4-a716-446655440000")
            @PathVariable String pipelineId) {

        return ResponseEntity.ok(mapper.toResponse(
                getPipelineUseCase.getPipeline(mapper.toGetPipelineByIdQuery(pipelineId)),
                acceptLanguage));
    }

    @GetMapping
    @Operation(summary = "List pipelines", description = "Lists topology pipelines with optional search, pipeline system, product type code, and status filters.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Pipelines listed."),
            @ApiResponse(responseCode = "400", description = "Invalid list pipelines query parameters."),
            @ApiResponse(responseCode = "500", description = "Unexpected server error.")
    })
    public PageResult<PipelineResponse> listPipelines(
            @Parameter(description = "Preferred response language using BCP 47 language tags.", example = "fr")
            @RequestHeader(name = "Accept-Language", required = false) String acceptLanguage,
            @Parameter(description = "Optional text filter applied to pipeline code or labels.", example = "GZ")
            @RequestParam(required = false) String searchText,
            @Parameter(description = "Optional parent pipeline system identifier filter.", example = "ps_550e8400-e29b-41d4-a716-446655440000")
            @RequestParam(required = false) String pipelineSystemId,
            @Parameter(description = "Optional product type code filter.", example = "GAS")
            @RequestParam(required = false) String productTypeCode,
            @Parameter(description = "Optional pipeline lifecycle status filter.", example = "ACTIVE")
            @RequestParam(required = false) String status,
            @Parameter(description = "Zero-based page index.", example = "0")
            @RequestParam(defaultValue = "0") @Min(0) int page,
            @Parameter(description = "Page size between 1 and 200.", example = "20")
            @RequestParam(defaultValue = "20") @Min(1) @Max(200) int size) {

        return mapper.toPipelineResponsePage(
                listPipelinesUseCase.listPipelines(
                        mapper.toListPipelinesQuery(searchText, pipelineSystemId, productTypeCode, status, page, size, acceptLanguage)),
                acceptLanguage);
    }
}
