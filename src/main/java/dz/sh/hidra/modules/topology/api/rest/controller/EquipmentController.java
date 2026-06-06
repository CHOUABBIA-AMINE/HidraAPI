/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : EquipmentController
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Class
 * @Layer       : API
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.api.rest.controller
 *
 * @Description : REST controller for topology equipment.
 *
 */
package dz.sh.hidra.modules.topology.api.rest.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dz.sh.hidra.modules.topology.api.rest.mapper.TopologyRestMapper;
import dz.sh.hidra.modules.topology.api.rest.request.RegisterEquipmentRequest;
import dz.sh.hidra.modules.topology.api.rest.response.EquipmentResponse;
import dz.sh.hidra.modules.topology.application.port.in.RegisterEquipmentUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/topology/equipment")
@Tag(name = "Topology Equipment", description = "Topology equipment endpoints.")
public class EquipmentController {

    private final RegisterEquipmentUseCase registerEquipmentUseCase;
    private final TopologyRestMapper mapper;

    public EquipmentController(
            RegisterEquipmentUseCase registerEquipmentUseCase,
            TopologyRestMapper mapper) {

        this.registerEquipmentUseCase = registerEquipmentUseCase;
        this.mapper = mapper;
    }

    @PostMapping
    @Operation(summary = "Register equipment", description = "Registers equipment attached to a topology asset.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Equipment registered."),
            @ApiResponse(responseCode = "400", description = "Invalid equipment registration request."),
            @ApiResponse(responseCode = "500", description = "Unexpected server error.")
    })
    public ResponseEntity<EquipmentResponse> registerEquipment(
            @RequestHeader(name = "Accept-Language", required = false) String acceptLanguage,
            @Valid @RequestBody RegisterEquipmentRequest request) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(mapper.toResponse(
                        registerEquipmentUseCase.registerEquipment(mapper.toCommand(request, acceptLanguage)),
                        acceptLanguage));
    }
}
