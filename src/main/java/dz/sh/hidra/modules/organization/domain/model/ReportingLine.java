/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ReportingLine
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization.domain.model
 *
 * @Description : Matrix-capable reporting line for employee-to-manager relationships.
 *
 */
package dz.sh.hidra.modules.organization.domain.model;

import java.time.LocalDate;
import java.util.Objects;
import java.util.Optional;

import dz.sh.hidra.kernel.domain.exception.BusinessRuleViolationException;
import dz.sh.hidra.kernel.domain.exception.InvalidValueObjectException;
import dz.sh.hidra.kernel.domain.model.Entity;
import dz.sh.hidra.modules.organization.domain.value.EmployeeId;
import dz.sh.hidra.modules.organization.domain.value.ReportingLineId;
import dz.sh.hidra.modules.organization.domain.value.ReportingLineType;

/**
 * Represents an employee reporting line to a manager.
 *
 * <p>Business role:
 * This model supports both simple hierarchical reporting and matrix reporting. Examples include
 * a station team leader reporting to a station boss, a station boss reporting to an operational
 * region director, and a region director also reporting functionally to a gas flux director.
 *
 * <p>Architecture role:
 * This is a pure organization domain model. It replaces a simple supervisor-only model and must
 * not be named or implemented as SupervisorAssignment.
 *
 * <p>Validation:
 * Employee id, manager employee id, reporting line type, and effective start date are mandatory.
 * An employee cannot report to themselves. Effective end date, when provided, must not be before
 * effective start date. Description is optional and bounded.
 *
 * <p>Usage:
 * Use this model for line, operational, functional, administrative, technical, and dotted-line
 * reporting relationships. Do not use it for identity roles or permissions.
 *
 * @param id stable reporting line identifier
 * @param employeeId employee that reports to the manager
 * @param managerEmployeeId manager employee identifier
 * @param type reporting line type
 * @param primaryLine whether this is the primary reporting line
 * @param effectiveFrom date when the reporting line starts
 * @param effectiveTo optional date when the reporting line ends
 * @param description optional business description
 */
public record ReportingLine(
        ReportingLineId id,
        EmployeeId employeeId,
        EmployeeId managerEmployeeId,
        ReportingLineType type,
        boolean primaryLine,
        LocalDate effectiveFrom,
        LocalDate effectiveTo,
        String description) implements Entity<ReportingLineId> {

    private static final int DESCRIPTION_MAX_LENGTH = 500;

    public ReportingLine {
        Objects.requireNonNull(id, "Reporting line id must not be null.");
        Objects.requireNonNull(employeeId, "Reporting line employee id must not be null.");
        Objects.requireNonNull(managerEmployeeId, "Reporting line manager employee id must not be null.");
        Objects.requireNonNull(type, "Reporting line type must not be null.");
        Objects.requireNonNull(effectiveFrom, "Reporting line effectiveFrom date must not be null.");

        if (employeeId.equals(managerEmployeeId)) {
            throw new BusinessRuleViolationException("An employee cannot report to themselves.");
        }

        if (effectiveTo != null && effectiveTo.isBefore(effectiveFrom)) {
            throw new BusinessRuleViolationException("Reporting line effectiveTo must not be before effectiveFrom.");
        }

        description = normalizeDescription(description);
    }

    /**
     * Creates a new active reporting line.
     *
     * @param employeeId employee that reports to the manager
     * @param managerEmployeeId manager employee identifier
     * @param type reporting line type
     * @param primaryLine whether this is the primary reporting line
     * @param effectiveFrom date when the reporting line starts
     * @param description optional business description
     * @return created reporting line
     */
    public static ReportingLine create(
            EmployeeId employeeId,
            EmployeeId managerEmployeeId,
            ReportingLineType type,
            boolean primaryLine,
            LocalDate effectiveFrom,
            String description) {

        return new ReportingLine(
                ReportingLineId.newId(),
                employeeId,
                managerEmployeeId,
                type,
                primaryLine,
                effectiveFrom,
                null,
                description);
    }

    /**
     * Ends this reporting line at the supplied date.
     *
     * @param endDate reporting line end date
     * @return ended reporting line
     */
    public ReportingLine endOn(LocalDate endDate) {
        return new ReportingLine(
                id,
                employeeId,
                managerEmployeeId,
                type,
                primaryLine,
                effectiveFrom,
                endDate,
                description);
    }

    /**
     * Returns the optional business description.
     *
     * @return optional description
     */
    public Optional<String> optionalDescription() {
        return Optional.ofNullable(description);
    }

    /**
     * Indicates whether this reporting line is active on a given date.
     *
     * @param date date to evaluate
     * @return true when active on the supplied date
     */
    public boolean isActiveOn(LocalDate date) {
        Objects.requireNonNull(date, "Reporting line evaluation date must not be null.");
        return !date.isBefore(effectiveFrom) && (effectiveTo == null || !date.isAfter(effectiveTo));
    }

    /**
     * Indicates whether this is an active primary line reporting relationship.
     *
     * @param date date to evaluate
     * @return true when active, primary, and of LINE type
     */
    public boolean isActivePrimaryLineOn(LocalDate date) {
        return primaryLine && type == ReportingLineType.LINE && isActiveOn(date);
    }

    private static String normalizeDescription(String description) {
        if (description == null || description.isBlank()) {
            return null;
        }

        String normalized = description.trim();
        if (normalized.length() > DESCRIPTION_MAX_LENGTH) {
            throw new InvalidValueObjectException("Reporting line description must not exceed 500 characters.");
        }
        return normalized;
    }
}
