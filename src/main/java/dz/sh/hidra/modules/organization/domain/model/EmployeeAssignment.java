/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : EmployeeAssignment
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization.domain.model
 *
 * @Description : Domain model representing an employee assignment to an organization unit and position.
 *
 */
package dz.sh.hidra.modules.organization.domain.model;

import java.time.LocalDate;
import java.util.Objects;
import java.util.Optional;

import dz.sh.hidra.kernel.domain.exception.BusinessRuleViolationException;
import dz.sh.hidra.kernel.domain.model.Entity;
import dz.sh.hidra.modules.organization.domain.value.AssignmentId;
import dz.sh.hidra.modules.organization.domain.value.EmployeeId;
import dz.sh.hidra.modules.organization.domain.value.OrganizationUnitId;
import dz.sh.hidra.modules.organization.domain.value.PositionId;

/**
 * Represents an employee assignment to an organization unit and position.
 *
 * <p>Business role:
 * This model captures that an employee works in an organization unit, such as a station,
 * region, department, or team, with a specific operational position. It may optionally carry
 * an operational scope reference when the assignment concerns a future topology scope.
 *
 * <p>Architecture role:
 * This is a pure organization domain model. It does not depend on REST DTOs, JPA entities,
 * identity roles, platform security, or topology assets.
 *
 * <p>Validation:
 * Employee, organization unit, position, and effective start date are mandatory. Effective end
 * date, when provided, must not be before the effective start date. The optional operational
 * scope reference must remain neutral and must not import topology classes.
 *
 * <p>Usage:
 * Use this model inside the employee aggregate to represent operational responsibility. Do not
 * use it to model identity permissions or physical station ownership.
 *
 * @param id stable assignment identifier
 * @param employeeId assigned employee identifier
 * @param organizationUnitId target organization unit identifier
 * @param positionId target position identifier
 * @param operationalScopeReference optional neutral operational scope reference
 * @param effectiveFrom date when the assignment starts
 * @param effectiveTo optional date when the assignment ends
 */
public record EmployeeAssignment(
        AssignmentId id,
        EmployeeId employeeId,
        OrganizationUnitId organizationUnitId,
        PositionId positionId,
        OperationalScopeReference operationalScopeReference,
        LocalDate effectiveFrom,
        LocalDate effectiveTo) implements Entity<AssignmentId> {

    public EmployeeAssignment {
        Objects.requireNonNull(id, "Assignment id must not be null.");
        Objects.requireNonNull(employeeId, "Assignment employee id must not be null.");
        Objects.requireNonNull(organizationUnitId, "Assignment organization unit id must not be null.");
        Objects.requireNonNull(positionId, "Assignment position id must not be null.");
        Objects.requireNonNull(effectiveFrom, "Assignment effectiveFrom date must not be null.");

        if (effectiveTo != null && effectiveTo.isBefore(effectiveFrom)) {
            throw new BusinessRuleViolationException("Assignment effectiveTo must not be before effectiveFrom.");
        }
    }

    /**
     * Creates a new active employee assignment starting from the supplied date.
     *
     * @param employeeId employee identifier
     * @param organizationUnitId organization unit identifier
     * @param positionId position identifier
     * @param operationalScopeReference optional operational scope reference
     * @param effectiveFrom assignment start date
     * @return created employee assignment
     */
    public static EmployeeAssignment create(
            EmployeeId employeeId,
            OrganizationUnitId organizationUnitId,
            PositionId positionId,
            OperationalScopeReference operationalScopeReference,
            LocalDate effectiveFrom) {

        return new EmployeeAssignment(
                AssignmentId.newId(),
                employeeId,
                organizationUnitId,
                positionId,
                operationalScopeReference,
                effectiveFrom,
                null);
    }

    /**
     * Ends this assignment at the supplied date.
     *
     * @param endDate assignment end date
     * @return ended assignment
     */
    public EmployeeAssignment endOn(LocalDate endDate) {
        return new EmployeeAssignment(
                id,
                employeeId,
                organizationUnitId,
                positionId,
                operationalScopeReference,
                effectiveFrom,
                endDate);
    }

    /**
     * Returns the optional operational scope reference.
     *
     * @return optional operational scope reference
     */
    public Optional<OperationalScopeReference> optionalOperationalScopeReference() {
        return Optional.ofNullable(operationalScopeReference);
    }

    /**
     * Indicates whether the assignment is active on a given date.
     *
     * @param date date to evaluate
     * @return true when active on the supplied date
     */
    public boolean isActiveOn(LocalDate date) {
        Objects.requireNonNull(date, "Assignment evaluation date must not be null.");
        return !date.isBefore(effectiveFrom) && (effectiveTo == null || !date.isAfter(effectiveTo));
    }

    /**
     * Indicates whether this assignment targets the same unit and position.
     *
     * @param other other assignment to compare
     * @return true when both assignments target the same organization unit and position
     */
    public boolean targetsSameUnitAndPosition(EmployeeAssignment other) {
        Objects.requireNonNull(other, "Other assignment must not be null.");
        return organizationUnitId.equals(other.organizationUnitId)
                && positionId.equals(other.positionId);
    }
}
