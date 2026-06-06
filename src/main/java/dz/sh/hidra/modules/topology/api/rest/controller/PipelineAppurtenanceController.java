/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : PipelineAppurtenanceController
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Class
 * @Layer       : API
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.api.rest.controller
 *
 * @Description : REST controller for topology pipeline appurtenances.
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
import dz.sh.hidra.modules.topology.api.rest.request.CreatePipelineAppurtenanceRequest;
import dz.sh.hidra.modules.topology.api.rest.response.PipelineAppurtenanceResponse;
import dz.sh.hidra.modules.topology.application.port.in.CreatePipelineAppurtenanceUseCase;
import dz.sh.hidra.modules.topology.application.port.in.GetPipelineAppurtenanceUseCase;
import dz.sh.hidra.modules.topology.application.port.in.ListPipelineAppurtenancesUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

@RestController
@RequestMapping("/api/v1/topology/pipeline-appurtenances")
@Tag(name = "Topology Pipeline Appurtenances", description = "Topology pipeline appurtenance endpoints.")
public class PipelineAppurtenanceController {

    private final CreatePipelineAppurtenanceUseCase createPipelineAppurtenanceUseCase;
    private final GetPipelineAppurtenanceUseCase getPipelineAppurtenanceUseCase;
    private final ListPipelineAppurtenancesUseCase listPipelineAppurtenancesUseCase;
    private final TopologyRestMapper mapper;

    public PipelineAppurtenanceController(
            CreatePipelineAppurtenanceUseCase createPipelineAppurtenanceUseCase,
            GetPipelineAppurtenanceUseCase getPipelineAppurtenanceUseCase,
            ListPipelineAppurtenancesUseCase listPipelineAppurtenancesUseCase,
            TopologyRestMapper mapper) {

        this.createPipelineAppurtenanceUseCase = createPipelineAppurtenanceUseCase;
        this.getPipelineAppurtenanceUseCase = getPipelineAppurtenanceUseCase;
        this.listPipelineAppurtenancesUseCase = listPipelineAppurtenancesUseCase;
        this.mapper = mapper;
    }

    @PostMapping
    @Operation(summary = "Create pipeline appurtenance", description = "Creates a point asset installed along a topology pipeline.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Pipeline appurtenance created."),
            @ApiResponse(responseCode = "400", description = "Invalid pipeline appurtenance creation request."),
            @ApiResponse(responseCode = "500", description = "Unexpected server error.")
    })
    public ResponseEntity<PipelineAppurtenanceResponse> createPipelineAppurtenance(
            @RequestHeader(name = "Accept-Language", required = false) String acceptLanguage,
            @Valid @RequestBody CreatePipelineAppurtenanceRequest request) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(mapper.toResponse(
                        createPipelineAppurtenanceUseCase.createPipelineAppurtenance(mapper.toCommand(request, acceptLanguage)),
                        acceptLanguage));
    }

    @GetMapping("/{pipelineAppurtenanceId}")
    @Operation(summary = "Get pipeline appurtenance", description = "Retrieves one pipeline appurtenance by identifier.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Pipeline appurtenance found."),
            @ApiResponse(responseCode = "400", description = "Invalid pipeline appurtenance identifier."),
            @ApiResponse(responseCode = "404", description = "Pipeline appurtenance not found."),
            @ApiResponse(responseCode = "500", description = "Unexpected server error.")
    })
    public ResponseEntity<PipelineAppurtenanceResponse> getPipelineAppurtenance(
            @RequestHeader(name = "Accept-Language", required = false) String acceptLanguage,
            @Parameter(description = "Pipeline appurtenance identifier.", required = true)
            @PathVariable String pipelineAppurtenanceId) {

        return ResponseEntity.ok(mapper.toResponse(
                getPipelineAppurtenanceUseCase.getPipelineAppurtenance(
                        mapper.toGetPipelineAppurtenanceByIdQuery(pipelineAppurtenanceId)),
                acceptLanguage));
    }

    @GetMapping
    @Operation(summary = "List pipeline appurtenances", description = "Lists pipeline appurtenances with optional search, pipeline, appurtenance type code, valve type code, and status filters.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Pipeline appurtenances listed."),
            @ApiResponse(responseCode = "400", description = "Invalid list pipeline appurtenances query parameters."),
            @ApiResponse(responseCode = "500", description = "Unexpected server error.")
    })
    public PageResult<PipelineAppurtenanceResponse> listPipelineAppurtenances(
            @RequestHeader(name = "Accept-Language", required = false) String acceptLanguage,
            @RequestParam(required = false) String searchText,
            @RequestParam(required = false) String pipelineId,
            @RequestParam(required = false) String appurtenanceTypeCode,
            @RequestParam(required = false) String valveTypeCode,
            @RequestParam(required = false) String status,
            @RequestParam(defaultValue = "0") @Min(0) int page,
            @RequestParam(defaultValue = "20") @Min(1) @Max(200) int size) {

        return mapper.toPipelineAppurtenanceResponsePage(
                listPipelineAppurtenancesUseCase.listPipelineAppurtenances(
                        mapper.toListPipelineAppurtenancesQuery(searchText, pipelineId, appurtenanceTypeCode, valveTypeCode, status, page, size, acceptLanguage)),
                acceptLanguage);
    }
}
