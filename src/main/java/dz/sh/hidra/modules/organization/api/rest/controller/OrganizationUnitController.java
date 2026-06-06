/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : OrganizationUnitController
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-06
 *
 * @Type        : Class
 * @Layer       : API
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization.api.rest.controller
 *
 * @Description : REST controller for organization units.
 *
 */
package dz.sh.hidra.modules.organization.api.rest.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import dz.sh.hidra.kernel.application.pagination.PageResult;
import dz.sh.hidra.modules.organization.api.rest.mapper.OrganizationRestMapper;
import dz.sh.hidra.modules.organization.api.rest.request.CreateOrganizationUnitRequest;
import dz.sh.hidra.modules.organization.api.rest.request.UpdateOrganizationUnitRequest;
import dz.sh.hidra.modules.organization.api.rest.response.OrganizationUnitResponse;
import dz.sh.hidra.modules.organization.application.port.in.CreateOrganizationUnitUseCase;
import dz.sh.hidra.modules.organization.application.port.in.GetOrganizationUnitUseCase;
import dz.sh.hidra.modules.organization.application.port.in.ListOrganizationUnitsUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

@RestController
@RequestMapping("/api/v1/organization/units")
@Tag(name = "Organization Units", description = "Organization unit endpoints.")
public class OrganizationUnitController {

    private final CreateOrganizationUnitUseCase createOrganizationUnitUseCase;
    private final GetOrganizationUnitUseCase getOrganizationUnitUseCase;
    private final ListOrganizationUnitsUseCase listOrganizationUnitsUseCase;
    private final OrganizationRestMapper mapper;

    public OrganizationUnitController(
            CreateOrganizationUnitUseCase createOrganizationUnitUseCase,
            GetOrganizationUnitUseCase getOrganizationUnitUseCase,
            ListOrganizationUnitsUseCase listOrganizationUnitsUseCase,
            OrganizationRestMapper mapper) {

        this.createOrganizationUnitUseCase = createOrganizationUnitUseCase;
        this.getOrganizationUnitUseCase = getOrganizationUnitUseCase;
        this.listOrganizationUnitsUseCase = listOrganizationUnitsUseCase;
        this.mapper = mapper;
    }

    @PostMapping
    @Operation(summary = "Create organization unit", description = "Creates an organization unit, including station-as-organization-unit when requested.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Organization unit created."),
            @ApiResponse(responseCode = "400", description = "Invalid organization unit creation request."),
            @ApiResponse(responseCode = "409", description = "Organization unit code conflicts with an existing organization unit."),
            @ApiResponse(responseCode = "500", description = "Unexpected server error.")
    })
    public ResponseEntity<OrganizationUnitResponse> createOrganizationUnit(
            @Parameter(description = "Preferred response language using BCP 47 language tags.", example = "fr")
            @RequestHeader(name = "Accept-Language", required = false) String acceptLanguage,
            @Valid @RequestBody CreateOrganizationUnitRequest request) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(mapper.toResponse(createOrganizationUnitUseCase.createOrganizationUnit(mapper.toCommand(request)), acceptLanguage));
    }

    @GetMapping("/{unitId}")
    @Operation(summary = "Get organization unit", description = "Retrieves one organization unit by identifier.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Organization unit found."),
            @ApiResponse(responseCode = "400", description = "Invalid organization unit identifier."),
            @ApiResponse(responseCode = "404", description = "Organization unit not found."),
            @ApiResponse(responseCode = "500", description = "Unexpected server error.")
    })
    public ResponseEntity<OrganizationUnitResponse> getOrganizationUnit(
            @Parameter(description = "Preferred response language using BCP 47 language tags.", example = "fr")
            @RequestHeader(name = "Accept-Language", required = false) String acceptLanguage,
            @Parameter(description = "Organization unit identifier.", required = true, example = "ou_550e8400-e29b-41d4-a716-446655440000")
            @PathVariable String unitId) {

        return getOrganizationUnitUseCase.getOrganizationUnit(mapper.toGetOrganizationUnitByIdQuery(unitId))
                .map(dto -> mapper.toResponse(dto, acceptLanguage))
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    @Operation(summary = "List organization units", description = "Lists organization units with optional search, type code, status, and parent filters.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Organization units listed."),
            @ApiResponse(responseCode = "400", description = "Invalid list organization units query parameters."),
            @ApiResponse(responseCode = "500", description = "Unexpected server error.")
    })
    public PageResult<OrganizationUnitResponse> listOrganizationUnits(
            @Parameter(description = "Preferred response language using BCP 47 language tags.", example = "fr")
            @RequestHeader(name = "Accept-Language", required = false) String acceptLanguage,
            @Parameter(description = "Optional text filter applied to organization unit code or name.", example = "station")
            @RequestParam(required = false) String searchText,
            @Parameter(description = "Optional organization unit type code filter.", example = "STATION")
            @RequestParam(required = false) String typeCode,
            @Parameter(description = "Optional organization unit lifecycle status filter.", example = "ACTIVE")
            @RequestParam(required = false) String status,
            @Parameter(description = "Optional parent organization unit identifier filter.", example = "ou_550e8400-e29b-41d4-a716-446655440000")
            @RequestParam(required = false) String parentId,
            @Parameter(description = "Zero-based page index.", example = "0")
            @RequestParam(defaultValue = "0") @Min(0) int page,
            @Parameter(description = "Page size between 1 and 200.", example = "20")
            @RequestParam(defaultValue = "20") @Min(1) @Max(200) int size) {

        return mapper.toOrganizationUnitResponsePage(listOrganizationUnitsUseCase.listOrganizationUnits(
                mapper.toListOrganizationUnitsQuery(searchText, typeCode, status, parentId, page, size)), acceptLanguage);
    }

    @PutMapping("/{unitId}")
    @Operation(summary = "Update organization unit", description = "Endpoint contract for updating organization unit information. The update use case is not available in the current application ports.")
    @ApiResponses({
            @ApiResponse(responseCode = "501", description = "Update organization unit use case is not implemented in current application ports."),
            @ApiResponse(responseCode = "400", description = "Invalid organization unit update request."),
            @ApiResponse(responseCode = "500", description = "Unexpected server error.")
    })
    public OrganizationUnitResponse updateOrganizationUnit(
            @Parameter(description = "Organization unit identifier.", required = true, example = "ou_550e8400-e29b-41d4-a716-446655440000")
            @PathVariable String unitId,
            @Valid @RequestBody UpdateOrganizationUnitRequest request) {

        mapper.toCommand(unitId, request);
        throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED, "Update organization unit use case is not available in current organization application ports.");
    }
}
