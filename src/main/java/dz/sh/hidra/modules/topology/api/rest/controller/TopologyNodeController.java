/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TopologyNodeController
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-06
 *
 * @Type        : Class
 * @Layer       : API
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.api.rest.controller
 *
 * @Description : REST controller for topology nodes.
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
import dz.sh.hidra.modules.topology.api.rest.request.CreateTopologyNodeRequest;
import dz.sh.hidra.modules.topology.api.rest.response.TopologyNodeResponse;
import dz.sh.hidra.modules.topology.application.port.in.CreateTopologyNodeUseCase;
import dz.sh.hidra.modules.topology.application.port.in.GetTopologyNodeUseCase;
import dz.sh.hidra.modules.topology.application.port.in.ListTopologyNodesUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

@RestController
@RequestMapping("/api/v1/topology/nodes")
@Tag(name = "Topology Nodes", description = "Topology node endpoints.")
public class TopologyNodeController {

    private final CreateTopologyNodeUseCase createTopologyNodeUseCase;
    private final GetTopologyNodeUseCase getTopologyNodeUseCase;
    private final ListTopologyNodesUseCase listTopologyNodesUseCase;
    private final TopologyRestMapper mapper;

    public TopologyNodeController(
            CreateTopologyNodeUseCase createTopologyNodeUseCase,
            GetTopologyNodeUseCase getTopologyNodeUseCase,
            ListTopologyNodesUseCase listTopologyNodesUseCase,
            TopologyRestMapper mapper) {

        this.createTopologyNodeUseCase = createTopologyNodeUseCase;
        this.getTopologyNodeUseCase = getTopologyNodeUseCase;
        this.listTopologyNodesUseCase = listTopologyNodesUseCase;
        this.mapper = mapper;
    }

    @PostMapping
    @Operation(summary = "Create topology node", description = "Creates a physical topology graph node.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Topology node created."),
            @ApiResponse(responseCode = "400", description = "Invalid topology node creation request."),
            @ApiResponse(responseCode = "500", description = "Unexpected server error.")
    })
    public ResponseEntity<TopologyNodeResponse> createTopologyNode(
            @Parameter(description = "Preferred response language using BCP 47 language tags.", example = "fr")
            @RequestHeader(name = "Accept-Language", required = false) String acceptLanguage,
            @Valid @RequestBody CreateTopologyNodeRequest request) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(mapper.toResponse(
                        createTopologyNodeUseCase.createTopologyNode(mapper.toCommand(request, acceptLanguage)),
                        acceptLanguage));
    }

    @GetMapping("/{topologyNodeId}")
    @Operation(summary = "Get topology node", description = "Retrieves one topology node by identifier.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Topology node found."),
            @ApiResponse(responseCode = "400", description = "Invalid topology node identifier."),
            @ApiResponse(responseCode = "404", description = "Topology node not found."),
            @ApiResponse(responseCode = "500", description = "Unexpected server error.")
    })
    public ResponseEntity<TopologyNodeResponse> getTopologyNode(
            @Parameter(description = "Preferred response language using BCP 47 language tags.", example = "fr")
            @RequestHeader(name = "Accept-Language", required = false) String acceptLanguage,
            @Parameter(description = "Topology node identifier.", required = true, example = "node_550e8400-e29b-41d4-a716-446655440000")
            @PathVariable String topologyNodeId) {

        return ResponseEntity.ok(mapper.toResponse(
                getTopologyNodeUseCase.getTopologyNode(mapper.toGetTopologyNodeByIdQuery(topologyNodeId)),
                acceptLanguage));
    }

    @GetMapping
    @Operation(summary = "List topology nodes", description = "Lists topology nodes with optional search, node type code, facility, appurtenance, and status filters.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Topology nodes listed."),
            @ApiResponse(responseCode = "400", description = "Invalid list topology nodes query parameters."),
            @ApiResponse(responseCode = "500", description = "Unexpected server error.")
    })
    public PageResult<TopologyNodeResponse> listTopologyNodes(
            @Parameter(description = "Preferred response language using BCP 47 language tags.", example = "fr")
            @RequestHeader(name = "Accept-Language", required = false) String acceptLanguage,
            @Parameter(description = "Optional text filter applied to node code or labels.", example = "NODE")
            @RequestParam(required = false) String searchText,
            @Parameter(description = "Optional node type code filter.", example = "JUNCTION")
            @RequestParam(required = false) String nodeTypeCode,
            @Parameter(description = "Optional facility identifier filter.", example = "facility_550e8400-e29b-41d4-a716-446655440000")
            @RequestParam(required = false) String facilityId,
            @Parameter(description = "Optional pipeline appurtenance identifier filter.", example = "app_550e8400-e29b-41d4-a716-446655440000")
            @RequestParam(required = false) String pipelineAppurtenanceId,
            @Parameter(description = "Optional topology node lifecycle status filter.", example = "ACTIVE")
            @RequestParam(required = false) String status,
            @Parameter(description = "Zero-based page index.", example = "0")
            @RequestParam(defaultValue = "0") @Min(0) int page,
            @Parameter(description = "Page size between 1 and 200.", example = "20")
            @RequestParam(defaultValue = "20") @Min(1) @Max(200) int size) {

        return mapper.toTopologyNodeResponsePage(
                listTopologyNodesUseCase.listTopologyNodes(
                        mapper.toListTopologyNodesQuery(searchText, nodeTypeCode, facilityId, pipelineAppurtenanceId, status, page, size, acceptLanguage)),
                acceptLanguage);
    }
}
