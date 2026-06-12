/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : EmployeeAssignmentApplicationService
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
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

import dz.sh.hidra.modules.organization.application.command.AssignEmployeeCommand;
import dz.sh.hidra.modules.organization.application.port.in.AssignEmployeeUseCase;
import dz.sh.hidra.modules.organization.application.port.out.EmployeeAssignmentRepositoryPort;
import dz.sh.hidra.modules.organization.domain.model.EmployeeAssignment;
import dz.sh.hidra.modules.organization.domain.value.AssignmentStatus;
import dz.sh.hidra.modules.organization.domain.value.AssignmentType;
import dz.sh.hidra.modules.organization.domain.value.OrganizationId;

import java.time.Instant;
import java.util.Objects;

/**
 * Application service for employee assignments.
 */
public class EmployeeAssignmentApplicationService implements AssignEmployeeUseCase {

    private final EmployeeAssignmentRepositoryPort employeeAssignmentRepositoryPort;

    public EmployeeAssignmentApplicationService(EmployeeAssignmentRepositoryPort employeeAssignmentRepositoryPort) {
        this.employeeAssignmentRepositoryPort = Objects.requireNonNull(employeeAssignmentRepositoryPort, "Employee assignment repository port must not be null.");
    }

    @Override
    public String assignEmployee(AssignEmployeeCommand command) {
        Objects.requireNonNull(command, "Assign employee command must not be null.");
        Instant now = Instant.now();
        EmployeeAssignment assignment = new EmployeeAssignment(
                OrganizationId.newId().value(),
                command.employeeId(),
                command.organizationUnitId(),
                command.positionId(),
                command.assignmentType() == null ? AssignmentType.PRIMARY : command.assignmentType(),
                command.operationalScopeType(),
                command.operationalScopeId(),
                command.operationalScopeCode(),
                command.operationalScopeName(),
                command.validFrom() == null ? now : command.validFrom(),
                command.validTo(),
                AssignmentStatus.ACTIVE,
                now,
                now
        );
        return employeeAssignmentRepositoryPort.save(assignment).id();
    }
}
