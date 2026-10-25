/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : EmployeeAppointmentLifecycleController
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-25
 *
 * @Type        : Class
 * @Layer       : API
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization.api.rest.controller
 *
 * @Description : Exposes protected appointment lifecycle and separate scoped history representations.
 *
 */
package dz.sh.hidra.modules.organization.api.rest.controller;


import dz.sh.hidra.modules.organization.api.rest.request.EmployeeAppointmentLifecycleRequest.*;
import dz.sh.hidra.modules.organization.api.rest.response.EmployeeAppointmentResponse.*;
import dz.sh.hidra.modules.organization.api.rest.mapper.EmployeeAppointmentRestMapper;
import dz.sh.hidra.modules.organization.api.rest.EmployeeAppointmentApiExceptionHandler;
import dz.sh.hidra.modules.organization.application.command.EmployeeAppointmentCommand;
import dz.sh.hidra.modules.organization.application.query.EmployeeAppointmentQuery;
import dz.sh.hidra.modules.organization.application.port.in.EmployeeAppointmentLifecycleUseCase;
import org.springframework.web.bind.annotation.*;
import java.util.Objects;
import java.util.function.Supplier;

@RestController
@RequestMapping("/api/v1/organization")
public final class EmployeeAppointmentLifecycleController {
    private final EmployeeAppointmentLifecycleUseCase useCase;
    public EmployeeAppointmentLifecycleController(EmployeeAppointmentLifecycleUseCase useCase) {this.useCase=Objects.requireNonNull(useCase);}
    private static <T> T selector(Supplier<T> constructor) {return EmployeeAppointmentApiExceptionHandler.selector(constructor);}
    @io.swagger.v3.oas.annotations.Operation(operationId="endEmployeeAppointment")
    @PostMapping("/assignments/{id}/end")
    public Mutation end(@PathVariable("id") String id,@RequestParam("companyPartyId") String company,@RequestBody End body) {
        EmployeeAppointmentApiExceptionHandler.authenticated();
        var c=selector(()->new EmployeeAppointmentCommand.End(body.requestId(),company,id,body.expectedVersion(),body.endAt(),body.reason()));
        return EmployeeAppointmentRestMapper.mutation(useCase.end(c));
    }
    @io.swagger.v3.oas.annotations.Operation(operationId="transferEmployeeAppointment")
    @PostMapping("/assignments/{id}/transfer")
    public Mutation transfer(@PathVariable("id") String id,@RequestParam("companyPartyId") String company,@RequestBody Transfer b) {
        EmployeeAppointmentApiExceptionHandler.authenticated();
        var c=selector(()->new EmployeeAppointmentCommand.Transfer(b.requestId(),company,id,b.expectedVersion(),b.destinationOrganizationUnitId(),
                b.destinationPositionId(),b.destinationType(),b.effectiveAt(),b.successorValidTo(),b.expectedDestinationBindingRevision(),b.expectedDestinationPolicyRevision(),b.reason()));
        return EmployeeAppointmentRestMapper.mutation(useCase.transfer(c));
    }
    @io.swagger.v3.oas.annotations.Operation(operationId="correctEmployeeAppointment")
    @PostMapping("/assignments/{id}/corrections")
    public Mutation correct(@PathVariable("id") String id,@RequestParam("companyPartyId") String company,@RequestBody Correct b) {
        EmployeeAppointmentApiExceptionHandler.authenticated();
        var c=selector(()->new EmployeeAppointmentCommand.Correct(b.requestId(),company,id,b.expectedVersion(),b.correctedOrganizationUnitId(),b.correctedPositionId(),
                b.correctedType(),b.correctedStatus(),b.correctedValidFrom(),b.correctedValidTo(),b.expectedDestinationBindingRevision(),b.expectedDestinationPolicyRevision(),b.reason()));
        return EmployeeAppointmentRestMapper.mutation(useCase.correct(c));
    }
    @io.swagger.v3.oas.annotations.Operation(operationId="readEmployeeAssignmentAppointmentHistory")
    @GetMapping("/assignments/{id}/history")
    public PrivateHistory assignmentHistory(@PathVariable("id") String id,@RequestParam("companyPartyId") String company,
            @io.swagger.v3.oas.annotations.Parameter(schema=@io.swagger.v3.oas.annotations.media.Schema(minimum="1",maximum="100")) @RequestParam(value="limit",defaultValue="100") int limit,@RequestParam(value="cursor",required=false) String cursor) {
        EmployeeAppointmentApiExceptionHandler.authenticated();
        return EmployeeAppointmentRestMapper.privateHistory(useCase.assignmentHistory(selector(()->EmployeeAppointmentQuery.History.assignment(company,id,limit,cursor))));
    }
    @io.swagger.v3.oas.annotations.Operation(operationId="readExactEmployeeAppointmentVersion")
    @GetMapping("/assignments/{id}/versions/{version}")
    public Assignment exactVersion(@PathVariable("id") String id,@PathVariable("version") long version,@RequestParam("companyPartyId") String company) {
        EmployeeAppointmentApiExceptionHandler.authenticated();
        return EmployeeAppointmentRestMapper.assignment(useCase.exactVersion(selector(()->new EmployeeAppointmentQuery.ExactVersion(company,id,version))));
    }
    @io.swagger.v3.oas.annotations.Operation(operationId="readEmployeeAppointmentHistory")
    @GetMapping("/employees/{employeeId}/appointment-history")
    public PrivateHistory employeeHistory(@PathVariable("employeeId") String employee,@RequestParam("companyPartyId") String company,
            @io.swagger.v3.oas.annotations.Parameter(schema=@io.swagger.v3.oas.annotations.media.Schema(minimum="1",maximum="100")) @RequestParam(value="limit",defaultValue="100") int limit,@RequestParam(value="cursor",required=false) String cursor) {
        EmployeeAppointmentApiExceptionHandler.authenticated();
        return EmployeeAppointmentRestMapper.privateHistory(useCase.employeeHistory(selector(()->EmployeeAppointmentQuery.History.employee(company,employee,limit,cursor))));
    }
    @io.swagger.v3.oas.annotations.Operation(operationId="readPositionAppointmentHistory")
    @GetMapping("/units/{unitId}/positions/{positionId}/appointment-history")
    public PositionHistory positionHistory(@PathVariable("unitId") String unit,@PathVariable("positionId") String position,
            @RequestParam("companyPartyId") String company,@io.swagger.v3.oas.annotations.Parameter(schema=@io.swagger.v3.oas.annotations.media.Schema(minimum="1",maximum="100")) @RequestParam(value="limit",defaultValue="100") int limit,
            @RequestParam(value="cursor",required=false) String cursor) {
        EmployeeAppointmentApiExceptionHandler.authenticated();
        return EmployeeAppointmentRestMapper.positionHistory(useCase.positionHistory(selector(()->EmployeeAppointmentQuery.History.position(company,unit,position,limit,cursor))));
    }
    @io.swagger.v3.oas.annotations.Operation(operationId="readEmployeeAppointmentPolicy")
    @GetMapping("/units/{unitId}/appointment-policy")
    public Policy readPolicy(@PathVariable("unitId") String unit,@RequestParam("companyPartyId") String company) {
        EmployeeAppointmentApiExceptionHandler.authenticated();
        return EmployeeAppointmentRestMapper.policy(useCase.readPolicy(selector(()->new EmployeeAppointmentQuery.Policy(company,unit))));
    }
}
