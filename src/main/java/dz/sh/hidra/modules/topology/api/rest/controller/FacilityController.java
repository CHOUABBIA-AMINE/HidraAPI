/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : FacilityController
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-06
 *
 * @Type        : Class
 * @Layer       : API
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.api.rest.controller
 *
 * @Description : REST controller for topology facilities.
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
import dz.sh.hidra.modules.topology.api.rest.request.CreateFacilityRequest;
import dz.sh.hidra.modules.topology.api.rest.response.FacilityResponse;
import dz.sh.hidra.modules.topology.application.port.in.CreateFacilityUseCase;
import dz.sh.hidra.modules.topology.application.port.in.GetFacilityUseCase;
import dz.sh.hidra.modules.topology.application.port.in.ListFacilitiesUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

@RestController
@RequestMapping("/api/v1/topology/facilities")
@Tag(name = "Topology Facilities", description = "Topology facility endpoints.")
public class FacilityController {

    private final CreateFacilityUseCase createFacilityUseCase;
    private final GetFacilityUseCase getFacilityUseCase;
    private final ListFacilitiesUseCase listFacilitiesUseCase;
    private final TopologyRestMapper mapper;

    public FacilityController(
            CreateFacilityUseCase createFacilityUseCase,
            GetFacilityUseCase getFacilityUseCase,
            ListFacilitiesUseCase listFacilitiesUseCase,
            TopologyRestMapper mapper) {

        this.createFacilityUseCase = createFacilityUseCase;
        this.getFacilityUseCase = getFacilityUseCase;
        this.listFacilitiesUseCase = listFacilitiesUseCase;
        this.mapper = mapper;
    }

    @PostMapping
    @Operation(summary = "Create facility", description = "Creates a physical topology facility.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Facility created."),
            @ApiResponse(responseCode = "400", description = "Invalid facility creation request."),
            @ApiResponse(responseCode = "409", description = "Facility code conflicts with an existing facility."),
            @ApiResponse(responseCode = "500", description = "Unexpected server error.")
    })
    public ResponseEntity<FacilityResponse> createFacility(
            @Parameter(description = "Preferred response language using BCP 47 language tags.", example = "fr")
            @RequestHeader(name = "Accept-Language", required = false) String acceptLanguage,
            @Valid @RequestBody CreateFacilityRequest request) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(mapper.toResponse(
                        createFacilityUseCase.createFacility(mapper.toCommand(request, acceptLanguage)),
                        acceptLanguage));
    }

    @GetMapping("/{facilityId}")
    @Operation(summary = "Get facility", description = "Retrieves one physical topology facility by identifier.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Facility found."),
            @ApiResponse(responseCode = "400", description = "Invalid facility identifier."),
            @ApiResponse(responseCode = "404", description = "Facility not found."),
            @ApiResponse(responseCode = "500", description = "Unexpected server error.")
    })
    public ResponseEntity<FacilityResponse> getFacility(
            @Parameter(description = "Preferred response language using BCP 47 language tags.", example = "fr")
            @RequestHeader(name = "Accept-Language", required = false) String acceptLanguage,
            @Parameter(description = "Facility identifier.", required = true, example = "facility_550e8400-e29b-41d4-a716-446655440000")
            @PathVariable String facilityId) {

        return ResponseEntity.ok(mapper.toResponse(
                getFacilityUseCase.getFacility(mapper.toGetFacilityByIdQuery(facilityId)),
                acceptLanguage));
    }

    @GetMapping
    @Operation(summary = "List facilities", description = "Lists physical topology facilities with optional search, facility type code, product type code, and status filters.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Facilities listed."),
            @ApiResponse(responseCode = "400", description = "Invalid list facilities query parameters."),
            @ApiResponse(responseCode = "500", description = "Unexpected server error.")
    })
    public PageResult<FacilityResponse> listFacilities(
            @Parameter(description = "Preferred response language using BCP 47 language tags.", example = "fr")
            @RequestHeader(name = "Accept-Language", required = false) String acceptLanguage,
            @Parameter(description = "Optional text filter applied to facility code or labels.", example = "CPF")
            @RequestParam(required = false) String searchText,
            @Parameter(description = "Optional facility type code filter.", example = "COMPRESSION_STATION")
            @RequestParam(required = false) String facilityTypeCode,
            @Parameter(description = "Optional product type code filter.", example = "GAS")
            @RequestParam(required = false) String productTypeCode,
            @Parameter(description = "Optional facility lifecycle status filter.", example = "ACTIVE")
            @RequestParam(required = false) String status,
            @Parameter(description = "Zero-based page index.", example = "0")
            @RequestParam(defaultValue = "0") @Min(0) int page,
            @Parameter(description = "Page size between 1 and 200.", example = "20")
            @RequestParam(defaultValue = "20") @Min(1) @Max(200) int size) {

        return mapper.toFacilityResponsePage(
                listFacilitiesUseCase.listFacilities(
                        mapper.toListFacilitiesQuery(searchText, facilityTypeCode, productTypeCode, status, page, size, acceptLanguage)),
                acceptLanguage);
    }
}
