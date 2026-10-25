/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : EmployeeAssignmentApplicationService
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-25
 *
 * @Type        : Class
 * @Layer       : Application
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization.application.service
 *
 * @Description : Application service for employee assignments.
 *
 */
package dz.sh.hidra.modules.organization.application.service;

import org.springframework.stereotype.Service;
import dz.sh.hidra.modules.organization.application.command.AssignEmployeeCommand;
import dz.sh.hidra.modules.organization.application.port.in.AssignEmployeeUseCase;
import java.util.Objects;

/**
 * Application service for employee assignments.
 */
@Service
public final class EmployeeAssignmentApplicationService implements AssignEmployeeUseCase {


    private final dz.sh.hidra.modules.organization.application.port.in.EmployeeAppointmentLifecycleUseCase appointments;
    public EmployeeAssignmentApplicationService(dz.sh.hidra.modules.organization.application.port.in.EmployeeAppointmentLifecycleUseCase appointments) {
        this.appointments=Objects.requireNonNull(appointments);
    }
    @Override public String assignEmployee(AssignEmployeeCommand command) {
        Objects.requireNonNull(command);
        var selectors=new dz.sh.hidra.modules.organization.application.command.EmployeeAppointmentCommand.LegacyCreate(
                command.requestId(),command.employeeId(),command.organizationUnitId(),command.positionId(),command.assignmentType(),command.validFrom(),command.validTo());
        return appointments.createLegacy(selectors).after().assignmentId();
    }
}
