/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : OrganizationFoundationAdministrationUseCase
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-04
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization.application.port.out
 *
 * @Description : Coordinates the bounded Organization foundation contract.
 *
 */
package dz.sh.hidra.modules.organization.application.port.in;

import dz.sh.hidra.modules.organization.application.command.CreateOrganizationUnitCommand;
import dz.sh.hidra.modules.organization.application.command.RegisterEmployeeCommand;
import dz.sh.hidra.modules.organization.application.dto.EmployeeSummaryDto;
import dz.sh.hidra.modules.organization.application.dto.OrganizationUnitSummaryDto;
import java.time.Instant;
import java.util.List;
public interface OrganizationFoundationAdministrationUseCase {
    OrganizationUnitSummaryDto createUnit(CreateOrganizationUnitCommand command);
    EmployeeSummaryDto registerEmployee(RegisterEmployeeCommand command);
    EmployeeState activate(String employeeId,Instant expectedUpdatedAt,String reason);
    EmployeeState state(String employeeId);
    List<ActivationEvent> history(String employeeId);
    List<Choice> unitTypes();
    List<Choice> positions();
    record EmployeeState(String id,String status,Instant hiredAt,Instant updatedAt) {}
    record Choice(String id,String code,String nameAr,String nameFr,String nameEn) {}
    record ActivationEvent(String id,String employeeId,String actorId,String reason,
            Instant previousUpdatedAt,Instant updatedAt,Instant occurredAt) {}
}
