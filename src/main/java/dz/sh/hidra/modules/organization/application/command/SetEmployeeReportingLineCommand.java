/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : SetEmployeeReportingLineCommand
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Record
 * @Layer       : Application
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization.application.command
 *
 * @Description : Application command for setting an employee reporting line.
 *
 */
package dz.sh.hidra.modules.organization.application.command;

import java.time.LocalDate;
import java.util.Objects;

import dz.sh.hidra.kernel.application.command.Command;
import dz.sh.hidra.modules.organization.domain.value.EmployeeId;
import dz.sh.hidra.modules.organization.domain.value.ReportingLineType;

/**
 * Carries input required to create or update an employee reporting line.
 *
 * <p>Business role:
 * This command requests a matrix-capable reporting relationship between an employee and a manager,
 * including LINE, OPERATIONAL, FUNCTIONAL, ADMINISTRATIVE, TECHNICAL, or DOTTED_LINE reporting.
 *
 * <p>Architecture role:
 * This is an application command. It must not depend on identity roles, topology assets, REST DTOs,
 * persistence entities, Spring, JPA, platform, or infrastructure code.
 *
 * <p>Validation:
 * Employee id, manager employee id, reporting line type, and effective start date are mandatory.
 * Description is optional and bounded. Domain policies later enforce self-reporting and uniqueness.
 *
 * <p>Usage:
 * Use this command when API or orchestration code requests reporting-line changes.
 *
 * @param employeeId employee that reports to a manager
 * @param managerEmployeeId manager employee identifier
 * @param reportingLineType reporting line type
 * @param primaryLine whether this is the primary reporting line
 * @param effectiveFrom reporting line start date
 * @param description optional business description
 */
public record SetEmployeeReportingLineCommand(
        EmployeeId employeeId,
        EmployeeId managerEmployeeId,
        ReportingLineType reportingLineType,
        boolean primaryLine,
        LocalDate effectiveFrom,
        String description) implements Command {

    private static final int DESCRIPTION_MAX_LENGTH = 500;

    public SetEmployeeReportingLineCommand {
        Objects.requireNonNull(employeeId, "Employee id must not be null.");
        Objects.requireNonNull(managerEmployeeId, "Manager employee id must not be null.");
        Objects.requireNonNull(reportingLineType, "Reporting line type must not be null.");
        Objects.requireNonNull(effectiveFrom, "Reporting line effectiveFrom date must not be null.");
        description = normalizeOptional(description);
    }

    private static String normalizeOptional(String value) {
        if (value == null || value.isBlank()) {
            return null;
        }

        String normalized = value.trim();
        if (normalized.length() > DESCRIPTION_MAX_LENGTH) {
            throw new IllegalArgumentException("Reporting line description must not exceed 500 characters.");
        }
        return normalized;
    }
}
