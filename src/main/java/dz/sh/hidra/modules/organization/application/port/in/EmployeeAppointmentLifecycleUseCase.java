/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : EmployeeAppointmentLifecycleUseCase
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-21
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization.application.port.in
 *
 * @Description : Defines typed protected appointment mutations and scope-specific read boundaries.
 *
 */
package dz.sh.hidra.modules.organization.application.port.in;

import dz.sh.hidra.modules.organization.application.command.EmployeeAppointmentCommand;
import dz.sh.hidra.modules.organization.application.dto.EmployeeAppointmentResult;
import dz.sh.hidra.modules.organization.application.query.EmployeeAppointmentQuery;

/**
 * Inbound application contract ONLY. All methods must first establish complete
 * current native authorization and Organization/Party ownership inside the
 * registered protected transaction. A syntactically valid selector or returned
 * record never constitutes admission; no implementation is wired by this stage.
 */
public interface EmployeeAppointmentLifecycleUseCase {
    EmployeeAppointmentResult.MutationReceipt createLegacy(EmployeeAppointmentCommand.LegacyCreate selectors);
    EmployeeAppointmentResult.CurrentPage currentAssignments(EmployeeAppointmentQuery.Current query);
    EmployeeAppointmentResult.MutationReceipt create(EmployeeAppointmentCommand.Create command);

    EmployeeAppointmentResult.MutationReceipt end(EmployeeAppointmentCommand.End command);

    EmployeeAppointmentResult.MutationReceipt transfer(EmployeeAppointmentCommand.Transfer command);

    EmployeeAppointmentResult.MutationReceipt correct(EmployeeAppointmentCommand.Correct command);

    EmployeeAppointmentResult.HistoryPage<EmployeeAppointmentResult.PrivateHistoryEvent>
            assignmentHistory(EmployeeAppointmentQuery.History query);

    EmployeeAppointmentResult.HistoryPage<EmployeeAppointmentResult.PrivateHistoryEvent>
            employeeHistory(EmployeeAppointmentQuery.History query);

    EmployeeAppointmentResult.HistoryPage<EmployeeAppointmentResult.PositionHistoryEvent>
            positionHistory(EmployeeAppointmentQuery.History query);

    EmployeeAppointmentResult.PinnedVersion exactVersion(EmployeeAppointmentQuery.ExactVersion query);

    EmployeeAppointmentResult.PolicyView readPolicy(EmployeeAppointmentQuery.Policy query);
}
