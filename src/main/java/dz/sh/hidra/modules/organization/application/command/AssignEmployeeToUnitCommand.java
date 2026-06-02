/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AssignEmployeeToUnitCommand
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Record
 * @Layer       : Application
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization.application.command
 *
 * @Description : Application command for assigning an employee to an organization unit.
 *
 */
package dz.sh.hidra.modules.organization.application.command;

import java.time.LocalDate;
import java.util.Objects;

import dz.sh.hidra.kernel.application.command.Command;
import dz.sh.hidra.modules.organization.domain.value.EmployeeId;
import dz.sh.hidra.modules.organization.domain.value.OperationalScopeType;
import dz.sh.hidra.modules.organization.domain.value.OrganizationUnitId;
import dz.sh.hidra.modules.organization.domain.value.PositionId;

/**
 * Carries input required to assign an employee to an organization unit and position.
 *
 * <p>Business role:
 * This command requests assignment of a real operational employee to a unit and position, such as
 * a station team leader assigned to a station organization unit.
 *
 * <p>Architecture role:
 * This is an application command. It carries neutral operational scope fields for future topology
 * references without importing topology domain classes.
 *
 * <p>Validation:
 * Employee id, organization unit id, position id, and effective start date are mandatory. Scope
 * fields are optional and normalized when present.
 *
 * <p>Usage:
 * Use this command from API or orchestration code before invoking the assignment use case.
 *
 * @param employeeId employee identifier
 * @param organizationUnitId target organization unit identifier
 * @param positionId target position identifier
 * @param operationalScopeType optional neutral operational scope type
 * @param operationalScopeId optional neutral operational scope identifier
 * @param operationalScopeCode optional neutral operational scope business code
 * @param operationalScopeName optional neutral operational scope display name
 * @param effectiveFrom assignment start date
 */
public record AssignEmployeeToUnitCommand(
        EmployeeId employeeId,
        OrganizationUnitId organizationUnitId,
        PositionId positionId,
        OperationalScopeType operationalScopeType,
        String operationalScopeId,
        String operationalScopeCode,
        String operationalScopeName,
        LocalDate effectiveFrom) implements Command {

    public AssignEmployeeToUnitCommand {
        Objects.requireNonNull(employeeId, "Employee id must not be null.");
        Objects.requireNonNull(organizationUnitId, "Organization unit id must not be null.");
        Objects.requireNonNull(positionId, "Position id must not be null.");
        Objects.requireNonNull(effectiveFrom, "Assignment effectiveFrom date must not be null.");

        operationalScopeId = normalizeOptional(operationalScopeId, "Operational scope id", 120);
        operationalScopeCode = normalizeOptional(operationalScopeCode, "Operational scope code", 120);
        operationalScopeName = normalizeOptional(operationalScopeName, "Operational scope name", 160);
    }

    private static String normalizeOptional(String value, String label, int maxLength) {
        if (value == null || value.isBlank()) {
            return null;
        }

        String normalized = value.trim();
        if (normalized.length() > maxLength) {
            throw new IllegalArgumentException(label + " must not exceed " + maxLength + " characters.");
        }
        return normalized;
    }
}
