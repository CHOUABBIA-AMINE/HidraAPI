/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : OrganizationAdministrationQueryController
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-25
 *
 * @Type        : Class
 * @Layer       : API
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization.api.rest.controller
 *
 * @Description : Exposes dedicated organization hierarchy, directory, and assignment read APIs.
 *
 */
package dz.sh.hidra.modules.organization.api.rest.controller;

import dz.sh.hidra.modules.organization.application.port.in.OrganizationAdministrationQueryUseCase;
import dz.sh.hidra.modules.organization.application.port.in.OrganizationAdministrationQueryUseCase.EmployeeAssignmentView;
import dz.sh.hidra.modules.organization.application.port.in.OrganizationAdministrationQueryUseCase.EmployeeView;
import dz.sh.hidra.modules.organization.application.port.in.OrganizationAdministrationQueryUseCase.OrganizationNodeView;
import dz.sh.hidra.modules.organization.application.port.in.OrganizationAdministrationQueryUseCase.OrganizationUnitView;
import dz.sh.hidra.modules.organization.application.port.in.OrganizationAdministrationQueryUseCase.Page;
import java.util.List;
import java.util.Objects;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/organization")
public class OrganizationAdministrationQueryController {

    private final OrganizationAdministrationQueryUseCase useCase;

    public OrganizationAdministrationQueryController(OrganizationAdministrationQueryUseCase useCase) {
        this.useCase = Objects.requireNonNull(useCase, "OrganizationAdministrationQueryUseCase must not be null.");
    }

    @GetMapping("/units")
    public Page<OrganizationUnitView> units(
            @RequestParam(required = false) String q,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "50") int size
    ) {
        return useCase.units(q, page, size);
    }

    @GetMapping("/units/{id}")
    public OrganizationUnitView unit(@PathVariable String id) {
        return useCase.unit(id);
    }

    @GetMapping("/units/{id}/children")
    public List<OrganizationUnitView> children(@PathVariable String id) {
        return useCase.children(id);
    }

    @GetMapping("/hierarchy")
    public List<OrganizationNodeView> hierarchy() {
        return useCase.hierarchy();
    }

    @GetMapping("/employees")
    public Page<EmployeeView> employees(
            @RequestParam(required = false) String q,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "50") int size
    ) {
        return useCase.employees(q, page, size);
    }

    @GetMapping("/employees/{id}")
    public EmployeeView employee(@PathVariable String id) {
        return useCase.employee(id);
    }

    @GetMapping("/employees/{id}/assignments")
    public List<EmployeeAssignmentView> employeeAssignments(@PathVariable String id) {
        dz.sh.hidra.modules.organization.api.rest.EmployeeAppointmentApiExceptionHandler.authenticated();
        dz.sh.hidra.modules.organization.api.rest.EmployeeAppointmentApiExceptionHandler.selector(()->
            new dz.sh.hidra.modules.organization.application.query.EmployeeAppointmentQuery.Current(null,id,null,null,0,100,true));
        return useCase.employeeAssignments(id);
    }

    @GetMapping("/assignments")
    public Page<EmployeeAssignmentView> assignments(
            @RequestParam(required = false) String employeeId,
            @RequestParam(required = false) String organizationUnitId,
            @io.swagger.v3.oas.annotations.Parameter(schema=@io.swagger.v3.oas.annotations.media.Schema(implementation=dz.sh.hidra.modules.organization.domain.value.AssignmentStatus.class))
            @RequestParam(required = false) String status,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "50") int size
    ) {
        dz.sh.hidra.modules.organization.api.rest.EmployeeAppointmentApiExceptionHandler.authenticated();
        // Validate transport before entering protected execution; failures disclose no row state.
        dz.sh.hidra.modules.organization.api.rest.EmployeeAppointmentApiExceptionHandler.selector(()->
            new dz.sh.hidra.modules.organization.application.query.EmployeeAppointmentQuery.Current(null,employeeId,organizationUnitId,
                status==null?null:dz.sh.hidra.modules.organization.domain.value.AssignmentStatus.valueOf(status),page,size,false));
        return useCase.assignments(employeeId, organizationUnitId, status, page, size);
    }
}
