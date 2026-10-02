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
import dz.sh.hidra.modules.organization.api.rest.request.AssignEmployeeRequest;
import dz.sh.hidra.modules.organization.api.rest.request.CreateOrganizationUnitRequest;
import dz.sh.hidra.modules.organization.api.rest.request.RegisterEmployeeRequest;
import dz.sh.hidra.modules.organization.api.rest.response.EmployeeResponse;
import dz.sh.hidra.modules.organization.api.rest.response.OrganizationUnitResponse;
import dz.sh.hidra.modules.organization.application.port.in.AssignEmployeeUseCase;
import dz.sh.hidra.modules.organization.application.port.in.CreateOrganizationUnitUseCase;
import dz.sh.hidra.modules.organization.application.port.in.RegisterEmployeeUseCase;
import jakarta.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Spring MVC adapter exposing organization REST endpoints.
 */
@RestController
@Validated
@RequestMapping("/api/v1/organization")
public class SpringOrganizationController implements OrganizationController {

    private final AssignEmployeeUseCase assignEmployeeUseCase;
    private final CreateOrganizationUnitUseCase createOrganizationUnitUseCase;
    private final RegisterEmployeeUseCase registerEmployeeUseCase;

    public SpringOrganizationController(
            AssignEmployeeUseCase assignEmployeeUseCase,
            CreateOrganizationUnitUseCase createOrganizationUnitUseCase,
            RegisterEmployeeUseCase registerEmployeeUseCase
    ) {
        this.assignEmployeeUseCase = Objects.requireNonNull(assignEmployeeUseCase, "AssignEmployeeUseCase must not be null.");
        this.createOrganizationUnitUseCase = Objects.requireNonNull(createOrganizationUnitUseCase, "CreateOrganizationUnitUseCase must not be null.");
        this.registerEmployeeUseCase = Objects.requireNonNull(registerEmployeeUseCase, "RegisterEmployeeUseCase must not be null.");
    }

    @GetMapping("/capabilities")
    public Map<String, Object> capabilities() {
        return Map.of(
                "module", "organization",
                "mission", "Represent organization units, employees, and operational assignment structures.",
                "objectives", List.of(
                "Create organization units.",
                "Register employees.",
                "Assign employees to operational structures."
        ),
                "operations", List.of(
                "assignEmployee",
                "createOrganizationUnit",
                "registerEmployee"
        ),
                "resourceEndpoints", List.of(
                "POST /api/v1/organization/employees/assignments",
                "POST /api/v1/organization/units",
                "POST /api/v1/organization/employees"
        )
        );
    }

    @Override
    @PostMapping({"/assign-employee", "/employees/assignments"})
    public String assignEmployee(@Valid @RequestBody AssignEmployeeRequest request) {
        Objects.requireNonNull(request, "AssignEmployeeRequest must not be null.");
        return assignEmployeeUseCase.assignEmployee(OrganizationRestMapper.toCommand(request));
    }

    @Override
    @PostMapping({"/create-organization-unit", "/units"})
    public OrganizationUnitResponse createOrganizationUnit(@Valid @RequestBody CreateOrganizationUnitRequest request) {
        Objects.requireNonNull(request, "CreateOrganizationUnitRequest must not be null.");
        return OrganizationRestMapper.toResponse(createOrganizationUnitUseCase.createOrganizationUnit(OrganizationRestMapper.toCommand(request)));
    }

    @Override
    @PostMapping({"/register-employee", "/employees"})
    public EmployeeResponse registerEmployee(@Valid @RequestBody RegisterEmployeeRequest request) {
        Objects.requireNonNull(request, "RegisterEmployeeRequest must not be null.");
        return OrganizationRestMapper.toResponse(registerEmployeeUseCase.registerEmployee(OrganizationRestMapper.toCommand(request)));
    }

}
