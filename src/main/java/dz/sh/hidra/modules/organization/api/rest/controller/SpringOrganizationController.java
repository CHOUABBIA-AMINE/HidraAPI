/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : SpringOrganizationController
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-13
 *
 * @Type        : Class
 * @Layer       : API
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization.api.rest.controller
 *
 * @Description : Spring MVC adapter exposing organization REST endpoints.
 *
 */
package dz.sh.hidra.modules.organization.api.rest.controller;

import dz.sh.hidra.modules.organization.api.rest.mapper.OrganizationRestMapper;
import dz.sh.hidra.modules.organization.api.rest.request.CreateOrganizationUnitRequest;
import dz.sh.hidra.modules.organization.api.rest.request.RegisterEmployeeRequest;
import dz.sh.hidra.modules.organization.api.rest.response.EmployeeResponse;
import dz.sh.hidra.modules.organization.api.rest.response.OrganizationUnitResponse;
import dz.sh.hidra.modules.organization.application.port.in.CreateOrganizationUnitUseCase;
import dz.sh.hidra.modules.organization.application.port.in.RegisterEmployeeUseCase;
import jakarta.validation.Valid;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Objects;

/**
 * Spring MVC adapter exposing organization REST endpoints.
 */
@RestController
@Validated
@RequestMapping("/api/v1/organization")
public class SpringOrganizationController implements OrganizationController {

    private final CreateOrganizationUnitUseCase createOrganizationUnitUseCase;
    private final RegisterEmployeeUseCase registerEmployeeUseCase;

    public SpringOrganizationController(
            CreateOrganizationUnitUseCase createOrganizationUnitUseCase,
            RegisterEmployeeUseCase registerEmployeeUseCase
    ) {
        this.createOrganizationUnitUseCase = Objects.requireNonNull(createOrganizationUnitUseCase, "CreateOrganizationUnitUseCase must not be null.");
        this.registerEmployeeUseCase = Objects.requireNonNull(registerEmployeeUseCase, "RegisterEmployeeUseCase must not be null.");
    }


    @Override
    @PostMapping("/create-organization-unit")
    public OrganizationUnitResponse createOrganizationUnit(@Valid @RequestBody CreateOrganizationUnitRequest request) {
        Objects.requireNonNull(request, "CreateOrganizationUnitRequest must not be null.");
        return OrganizationRestMapper.toResponse(createOrganizationUnitUseCase.createOrganizationUnit(OrganizationRestMapper.toCommand(request)));
    }

    @Override
    @PostMapping("/register-employee")
    public EmployeeResponse registerEmployee(@Valid @RequestBody RegisterEmployeeRequest request) {
        Objects.requireNonNull(request, "RegisterEmployeeRequest must not be null.");
        return OrganizationRestMapper.toResponse(registerEmployeeUseCase.registerEmployee(OrganizationRestMapper.toCommand(request)));
    }

}
