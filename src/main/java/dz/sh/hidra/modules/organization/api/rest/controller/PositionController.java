/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : PositionController
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Class
 * @Layer       : API
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization.api.rest.controller
 *
 * @Description : REST controller for organization positions.
 *
 */
package dz.sh.hidra.modules.organization.api.rest.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import dz.sh.hidra.kernel.application.pagination.PageResult;
import dz.sh.hidra.modules.organization.api.rest.mapper.OrganizationRestMapper;
import dz.sh.hidra.modules.organization.api.rest.request.CreatePositionRequest;
import dz.sh.hidra.modules.organization.api.rest.response.PositionResponse;
import dz.sh.hidra.modules.organization.application.port.in.CreatePositionUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

/**
 * REST controller exposing organization position endpoints.
 *
 * <p>Business role:
 * Exposes operational position/function endpoints. These positions are not identity roles.
 *
 * <p>Architecture role:
 * This controller depends on application ports and the REST mapper only.
 *
 * <p>Validation:
 * Request bodies use Bean Validation with @Valid. Query pagination parameters are bounded.
 *
 * <p>Usage:
 * Use endpoints under /api/v1/organization/positions.
 */
@RestController
@RequestMapping("/api/v1/organization/positions")
@Tag(name = "Organization Positions", description = "Organization position endpoints.")
public class PositionController {

    private final CreatePositionUseCase createPositionUseCase;
    private final OrganizationRestMapper mapper;

    public PositionController(
            CreatePositionUseCase createPositionUseCase,
            OrganizationRestMapper mapper) {

        this.createPositionUseCase = createPositionUseCase;
        this.mapper = mapper;
    }

    @PostMapping
    @Operation(summary = "Create position", description = "Creates an operational position/function.")
    public ResponseEntity<PositionResponse> createPosition(@Valid @RequestBody CreatePositionRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(mapper.toResponse(createPositionUseCase.createPosition(mapper.toCommand(request))));
    }

    @GetMapping
    @Operation(summary = "List positions", description = "Endpoint contract for listing positions. The list positions use case is not available in the current application ports.")
    public PageResult<PositionResponse> listPositions(
            @RequestParam(required = false) String searchText,
            @RequestParam(defaultValue = "true") boolean activeOnly,
            @RequestParam(defaultValue = "0") @Min(0) int page,
            @RequestParam(defaultValue = "20") @Min(1) @Max(200) int size) {

        mapper.toListPositionsQuery(searchText, activeOnly, page, size);
        throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED, "List positions use case is not available in current organization application ports.");
    }
}
