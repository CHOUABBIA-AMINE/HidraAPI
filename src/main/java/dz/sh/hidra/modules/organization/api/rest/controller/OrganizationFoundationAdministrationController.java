/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : OrganizationFoundationAdministrationController
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-04
 *
 * @Type        : Class
 * @Layer       : API
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization.application.port.out
 *
 * @Description : Coordinates the bounded Organization foundation contract.
 *
 */
package dz.sh.hidra.modules.organization.api.rest.controller;

import dz.sh.hidra.modules.organization.application.port.in.OrganizationFoundationAdministrationUseCase;
import dz.sh.hidra.modules.organization.api.rest.request.OrganizationFoundationRequests;
import dz.sh.hidra.modules.organization.api.rest.response.OrganizationFoundationResponses;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import java.util.List;
import java.util.function.Supplier;
@RestController
@RequestMapping("/api/v1/organization")
public class OrganizationFoundationAdministrationController {
    private final OrganizationFoundationAdministrationUseCase service;
    public OrganizationFoundationAdministrationController(OrganizationFoundationAdministrationUseCase service){this.service=java.util.Objects.requireNonNull(service);}
    @GetMapping("/reference/unit-types") @Operation(operationId="organizationFoundationUnitTypes")
    public List<OrganizationFoundationResponses.Choice> unitTypes(){return call(()->service.unitTypes().stream().map(c->new OrganizationFoundationResponses.Choice(c.id(),c.code(),c.nameAr(),c.nameFr(),c.nameEn())).toList());}
    @GetMapping("/reference/positions") @Operation(operationId="organizationFoundationPositions")
    public List<OrganizationFoundationResponses.Choice> positions(){return call(()->service.positions().stream().map(c->new OrganizationFoundationResponses.Choice(c.id(),c.code(),c.nameAr(),c.nameFr(),c.nameEn())).toList());}
    @GetMapping("/employees/{id}/lifecycle") @Operation(operationId="organizationFoundationEmployeeLifecycle")
    public OrganizationFoundationResponses.EmployeeState state(@PathVariable String id){return call(()->view(service.state(id)));}
    @PostMapping("/employees/{id}/activate") @Operation(operationId="organizationFoundationActivateEmployee")
    public OrganizationFoundationResponses.EmployeeState activate(@PathVariable String id,@Valid @RequestBody OrganizationFoundationRequests.Activation request){return call(()->view(service.activate(id,request.expectedUpdatedAt(),request.reason())));}
    @GetMapping("/employees/{id}/lifecycle-history") @Operation(operationId="organizationFoundationEmployeeLifecycleHistory")
    public List<OrganizationFoundationResponses.ActivationEvent> history(@PathVariable String id){return call(()->service.history(id).stream().map(e->new OrganizationFoundationResponses.ActivationEvent(e.id(),e.employeeId(),e.actorId(),e.reason(),e.previousUpdatedAt(),e.updatedAt(),e.occurredAt())).toList());}
    private static OrganizationFoundationResponses.EmployeeState view(OrganizationFoundationAdministrationUseCase.EmployeeState e){return new OrganizationFoundationResponses.EmployeeState(e.id(),e.status(),e.hiredAt(),e.updatedAt());}
    static <T> T call(Supplier<T> action) {
        try{return action.get();}
        catch(java.util.NoSuchElementException missing){throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Organization record unavailable");}
        catch(org.springframework.dao.DataIntegrityViolationException|IllegalStateException conflict){throw new ResponseStatusException(HttpStatus.CONFLICT,"Organization state conflict");}
        catch(IllegalArgumentException invalid){throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Invalid Organization request");}
    }
}
