/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TopologyConnectionController
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Class
 * @Layer       : API
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.api.rest.controller
 *
 * @Description : REST controller for topology connections.
 *
 */
package dz.sh.hidra.modules.topology.api.rest.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import dz.sh.hidra.kernel.application.pagination.PageResult;
import dz.sh.hidra.modules.topology.api.rest.mapper.TopologyRestMapper;
import dz.sh.hidra.modules.topology.api.rest.request.CreateTopologyConnectionRequest;
import dz.sh.hidra.modules.topology.api.rest.response.TopologyConnectionResponse;
import dz.sh.hidra.modules.topology.application.port.in.CreateTopologyConnectionUseCase;
import dz.sh.hidra.modules.topology.application.port.in.ListTopologyConnectionsUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

@RestController
@RequestMapping("/api/v1/topology/connections")
@Tag(name = "Topology Connections", description = "Topology connection endpoints.")
public class TopologyConnectionController {

    private final CreateTopologyConnectionUseCase createTopologyConnectionUseCase;
    private final ListTopologyConnectionsUseCase listTopologyConnectionsUseCase;
    private final TopologyRestMapper mapper;

    public TopologyConnectionController(
            CreateTopologyConnectionUseCase createTopologyConnectionUseCase,
            ListTopologyConnectionsUseCase listTopologyConnectionsUseCase,
            TopologyRestMapper mapper) {

        this.createTopologyConnectionUseCase = createTopologyConnectionUseCase;
        this.listTopologyConnectionsUseCase = listTopologyConnectionsUseCase;
        this.mapper = mapper;
    }

    @PostMapping
    @Operation(summary = "Create topology connection", description = "Creates an explicit topology connection between two nodes.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Topology connection created."),
            @ApiResponse(responseCode = "400", description = "Invalid topology connection creation request."),
            @ApiResponse(responseCode = "500", description = "Unexpected server error.")
    })
    public ResponseEntity<TopologyConnectionResponse> createTopologyConnection(
            @RequestHeader(name = "Accept-Language", required = false) String acceptLanguage,
            @Valid @RequestBody CreateTopologyConnectionRequest request) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(mapper.toResponse(
                        createTopologyConnectionUseCase.createTopologyConnection(mapper.toCommand(request, acceptLanguage)),
                        acceptLanguage));
    }

    @GetMapping
    @Operation(summary = "List topology connections", description = "Lists topology connections with optional search, nodes, connection type code, linked asset type, and status filters.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Topology connections listed."),
            @ApiResponse(responseCode = "400", description = "Invalid list topology connections query parameters."),
            @ApiResponse(responseCode = "500", description = "Unexpected server error.")
    })
    public PageResult<TopologyConnectionResponse> listTopologyConnections(
            @RequestHeader(name = "Accept-Language", required = false) String acceptLanguage,
            @RequestParam(required = false) String searchText,
            @RequestParam(required = false) String fromNodeId,
            @RequestParam(required = false) String toNodeId,
            @RequestParam(required = false) String connectionTypeCode,
            @RequestParam(required = false) String linkedAssetType,
            @RequestParam(required = false) String status,
            @RequestParam(defaultValue = "0") @Min(0) int page,
            @RequestParam(defaultValue = "20") @Min(1) @Max(200) int size) {

        return mapper.toTopologyConnectionResponsePage(
                listTopologyConnectionsUseCase.listTopologyConnections(
                        mapper.toListTopologyConnectionsQuery(searchText, fromNodeId, toNodeId, connectionTypeCode, linkedAssetType, status, page, size, acceptLanguage)),
                acceptLanguage);
    }
}
