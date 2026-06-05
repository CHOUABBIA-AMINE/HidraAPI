/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ReportingLinePolicy
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Class
 * @Layer       : Domain
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization.domain.policy
 *
 * @Description : Domain policy validating matrix reporting line rules.
 *
 */
package dz.sh.hidra.modules.organization.domain.policy;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Objects;

import dz.sh.hidra.modules.organization.domain.exception.ReportingLineException;
import dz.sh.hidra.modules.organization.domain.model.Employee;
import dz.sh.hidra.modules.organization.domain.model.ReportingLine;
import dz.sh.hidra.modules.organization.domain.value.EmployeeId;
import dz.sh.hidra.modules.organization.domain.value.ReportingLineType;

/**
 * Validates matrix reporting line rules.
 *
 * <p>Business role:
 * This policy supports matrix organization structures where one employee has a primary LINE
 * manager and may also have functional, administrative, technical, operational, or dotted-line
 * reporting relationships.
 *
 * <p>Architecture role:
 * This is a pure organization domain policy. It does not import identity, topology, platform,
 * Spring, JPA, REST DTOs, or persistence code.
 *
 * <p>Validation:
 * It rejects self-reporting, manager assignment to disabled employees, multiple active primary
 * LINE reporting relationships, and duplicate active reporting lines.
 *
 * <p>Usage:
 * Use this policy before adding or changing reporting lines.
 */
public final class ReportingLinePolicy {

    /**
     * Ensures a reporting line can be added to an employee.
     *
     * @param employee employee receiving the reporting line
     * @param manager employee acting as manager
     * @param reportingLine reporting line to validate
     * @param existingReportingLines existing reporting lines for the employee
     * @param evaluationDate date used for active-line checks
     */
    public void ensureReportingLineAllowed(
            Employee employee,
            Employee manager,
            ReportingLine reportingLine,
            Collection<ReportingLine> existingReportingLines,
            LocalDate evaluationDate) {

        Objects.requireNonNull(employee, "Employee must not be null.");
        Objects.requireNonNull(manager, "Manager employee must not be null.");
        Objects.requireNonNull(reportingLine, "Reporting line must not be null.");
        Objects.requireNonNull(evaluationDate, "Evaluation date must not be null.");

        ensureNoSelfReporting(employee.id(), manager.id());
        ensureNoSelfReporting(reportingLine.employeeId(), reportingLine.managerEmployeeId());
        ensureReportingLineBelongsToEmployee(employee, reportingLine);
        ensureManagerMatches(manager, reportingLine);
        ensureEmployeeCanReceiveReportingLine(employee);
        ensureManagerCanManage(manager);
        ensurePrimaryLineUniqueness(reportingLine, existingReportingLines, evaluationDate);
        ensureNoDuplicateActiveReportingLine(reportingLine, existingReportingLines, evaluationDate);
    }

    /**
     * Ensures an employee does not report to themselves.
     *
     * @param employeeId employee identifier
     * @param managerEmployeeId manager employee identifier
     */
    public void ensureNoSelfReporting(EmployeeId employeeId, EmployeeId managerEmployeeId) {
        Objects.requireNonNull(employeeId, "Employee id must not be null.");
        Objects.requireNonNull(managerEmployeeId, "Manager employee id must not be null.");

        if (employeeId.equals(managerEmployeeId)) {
            throw new ReportingLineException("An employee cannot report to themselves.");
        }
    }

    /**
     * Ensures the reporting line belongs to the employee aggregate.
     *
     * @param employee employee aggregate
     * @param reportingLine reporting line to validate
     */
    public void ensureReportingLineBelongsToEmployee(Employee employee, ReportingLine reportingLine) {
        Objects.requireNonNull(employee, "Employee must not be null.");
        Objects.requireNonNull(reportingLine, "Reporting line must not be null.");

        if (!employee.id().equals(reportingLine.employeeId())) {
            throw new ReportingLineException("Reporting line employee id must match aggregate employee id.");
        }
    }

    /**
     * Ensures an active primary LINE reporting line is unique.
     *
     * @param reportingLine reporting line to validate
     * @param existingReportingLines existing reporting lines for the employee
     * @param evaluationDate date used for active-line checks
     */
    public void ensurePrimaryLineUniqueness(
            ReportingLine reportingLine,
            Collection<ReportingLine> existingReportingLines,
            LocalDate evaluationDate) {

        Objects.requireNonNull(reportingLine, "Reporting line must not be null.");
        Objects.requireNonNull(evaluationDate, "Evaluation date must not be null.");

        if (!reportingLine.isActivePrimaryLineOn(evaluationDate)) {
            return;
        }

        if (existingReportingLines == null || existingReportingLines.isEmpty()) {
            return;
        }

        boolean alreadyHasPrimaryLine = existingReportingLines.stream()
                .filter(existing -> !existing.id().equals(reportingLine.id()))
                .anyMatch(existing -> existing.isActivePrimaryLineOn(evaluationDate));

        if (alreadyHasPrimaryLine) {
            throw new ReportingLineException("Employee cannot have more than one active primary LINE reporting line.");
        }
    }

    private void ensureManagerMatches(Employee manager, ReportingLine reportingLine) {
        if (!manager.id().equals(reportingLine.managerEmployeeId())) {
            throw new ReportingLineException("Reporting line manager employee id must match manager aggregate id.");
        }
    }

    private void ensureEmployeeCanReceiveReportingLine(Employee employee) {
        if (!employee.status().allowsOperationalAssignment()) {
            throw new ReportingLineException(
                    "Employee cannot receive new reporting line in status " + employee.status() + ".");
        }
    }

    private void ensureManagerCanManage(Employee manager) {
        if (!manager.status().allowsOperationalAssignment()) {
            throw new ReportingLineException(
                    "Manager employee cannot receive reporting responsibility in status " + manager.status() + ".");
        }
    }

    private void ensureNoDuplicateActiveReportingLine(
            ReportingLine reportingLine,
            Collection<ReportingLine> existingReportingLines,
            LocalDate evaluationDate) {

        if (existingReportingLines == null || existingReportingLines.isEmpty()) {
            return;
        }

        boolean duplicate = existingReportingLines.stream()
                .filter(existing -> !existing.id().equals(reportingLine.id()))
                .filter(existing -> existing.isActiveOn(evaluationDate))
                .anyMatch(existing -> existing.managerEmployeeId().equals(reportingLine.managerEmployeeId())
                        && existing.type() == reportingLine.type()
                        && existing.type() != ReportingLineType.FUNCTIONAL);

        if (duplicate) {
            throw new ReportingLineException("Duplicate active reporting line to the same manager and type is not allowed.");
        }
    }
}
