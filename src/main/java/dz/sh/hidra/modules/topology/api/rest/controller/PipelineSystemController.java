/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : PipelineSystemController
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-06
 *
 * @Type        : Class
 * @Layer       : API
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.api.rest.controller
 *
 * @Description : REST controller for topology pipeline systems.
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
import dz.sh.hidra.modules.topology.api.rest.request.CreatePipelineSystemRequest;
import dz.sh.hidra.modules.topology.api.rest.response.PipelineSystemResponse;
import dz.sh.hidra.modules.topology.application.port.in.CreatePipelineSystemUseCase;
import dz.sh.hidra.modules.topology.application.port.in.GetPipelineSystemUseCase;
import dz.sh.hidra.modules.topology.application.port.in.ListPipelineSystemsUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

@RestController
@RequestMapping("/api/v1/topology/pipeline-systems")
@Tag(name = "Topology Pipeline Systems", description = "Topology pipeline system endpoints.")
public class PipelineSystemController {

    private final CreatePipelineSystemUseCase createPipelineSystemUseCase;
    private final GetPipelineSystemUseCase getPipelineSystemUseCase;
    private final ListPipelineSystemsUseCase listPipelineSystemsUseCase;
    private final TopologyRestMapper mapper;

    public PipelineSystemController(
            CreatePipelineSystemUseCase createPipelineSystemUseCase,
            GetPipelineSystemUseCase getPipelineSystemUseCase,
            ListPipelineSystemsUseCase listPipelineSystemsUseCase,
            TopologyRestMapper mapper) {

        this.createPipelineSystemUseCase = createPipelineSystemUseCase;
        this.getPipelineSystemUseCase = getPipelineSystemUseCase;
        this.listPipelineSystemsUseCase = listPipelineSystemsUseCase;
        this.mapper = mapper;
    }

    @PostMapping
    @Operation(summary = "Create pipeline system", description = "Creates a topology pipeline system.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Pipeline system created."),
            @ApiResponse(responseCode = "400", description = "Invalid pipeline system creation request."),
            @ApiResponse(responseCode = "409", description = "Pipeline system code conflicts with an existing pipeline system."),
            @ApiResponse(responseCode = "500", description = "Unexpected server error.")
    })
    public ResponseEntity<PipelineSystemResponse> createPipelineSystem(
            @Parameter(description = "Preferred response language using BCP 47 language tags.", example = "fr")
            @RequestHeader(name = "Accept-Language", required = false) String acceptLanguage,
            @Valid @RequestBody CreatePipelineSystemRequest request) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(mapper.toResponse(
                        createPipelineSystemUseCase.createPipelineSystem(mapper.toCommand(request, acceptLanguage)),
                        acceptLanguage));
    }

    @GetMapping("/{pipelineSystemId}")
    @Operation(summary = "Get pipeline system", description = "Retrieves one topology pipeline system by identifier.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Pipeline system found."),
            @ApiResponse(responseCode = "400", description = "Invalid pipeline system identifier."),
            @ApiResponse(responseCode = "404", description = "Pipeline system not found."),
            @ApiResponse(responseCode = "500", description = "Unexpected server error.")
    })
    public ResponseEntity<PipelineSystemResponse> getPipelineSystem(
            @Parameter(description = "Preferred response language using BCP 47 language tags.", example = "fr")
            @RequestHeader(name = "Accept-Language", required = false) String acceptLanguage,
            @Parameter(description = "Pipeline system identifier.", required = true, example = "ps_550e8400-e29b-41d4-a716-446655440000")
            @PathVariable String pipelineSystemId) {

        return ResponseEntity.ok(mapper.toResponse(
                getPipelineSystemUseCase.getPipelineSystem(mapper.toGetPipelineSystemByIdQuery(pipelineSystemId)),
                acceptLanguage));
    }

    @GetMapping
    @Operation(summary = "List pipeline systems", description = "Lists topology pipeline systems with optional search, product type code, and status filters.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Pipeline systems listed."),
            @ApiResponse(responseCode = "400", description = "Invalid list pipeline systems query parameters."),
            @ApiResponse(responseCode = "500", description = "Unexpected server error.")
    })
    public PageResult<PipelineSystemResponse> listPipelineSystems(
            @Parameter(description = "Preferred response language using BCP 47 language tags.", example = "fr")
            @RequestHeader(name = "Accept-Language", required = false) String acceptLanguage,
            @Parameter(description = "Optional text filter applied to pipeline system code or labels.", example = "TRUNK")
            @RequestParam(required = false) String searchText,
            @Parameter(description = "Optional product type code filter.", example = "GAS")
            @RequestParam(required = false) String productTypeCode,
            @Parameter(description = "Optional pipeline system lifecycle status filter.", example = "ACTIVE")
            @RequestParam(required = false) String status,
            @Parameter(description = "Zero-based page index.", example = "0")
            @RequestParam(defaultValue = "0") @Min(0) int page,
            @Parameter(description = "Page size between 1 and 200.", example = "20")
            @RequestParam(defaultValue = "20") @Min(1) @Max(200) int size) {

        return mapper.toPipelineSystemResponsePage(
                listPipelineSystemsUseCase.listPipelineSystems(
                        mapper.toListPipelineSystemsQuery(searchText, productTypeCode, status, page, size, acceptLanguage)),
                acceptLanguage);
    }
}
