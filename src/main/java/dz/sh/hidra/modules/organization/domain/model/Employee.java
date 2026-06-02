/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : Employee
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Class
 * @Layer       : Domain
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization.domain.model
 *
 * @Description : Employee aggregate representing a real operational person.
 *
 */
package dz.sh.hidra.modules.organization.domain.model;

import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import dz.sh.hidra.kernel.domain.exception.BusinessRuleViolationException;
import dz.sh.hidra.kernel.domain.model.AggregateRoot;
import dz.sh.hidra.modules.organization.domain.value.EmployeeEmail;
import dz.sh.hidra.modules.organization.domain.value.EmployeeFullName;
import dz.sh.hidra.modules.organization.domain.value.EmployeeId;
import dz.sh.hidra.modules.organization.domain.value.EmployeeNumber;
import dz.sh.hidra.modules.organization.domain.value.EmploymentStatus;
import dz.sh.hidra.modules.organization.domain.value.IdentityUserReference;

/**
 * Represents a real operational person in the Hidra organization model.
 *
 * <p>Business role:
 * The employee aggregate models the real person who belongs to operational organization units,
 * receives assignments, may be linked to an identity user reference, and participates in reporting
 * lines. It is not a login account and does not own identity roles or permissions.
 *
 * <p>Architecture role:
 * This is the organization domain aggregate root for employees. It is independent from Spring,
 * JPA, REST DTOs, identity implementation, topology assets, platform security, and persistence
 * infrastructure.
 *
 * <p>Validation:
 * Employee number, full name, status, creation instant, and update instant are mandatory. New
 * operational assignments and reporting lines are allowed only when the employee is active.
 * Duplicate active assignments to the same unit and position are rejected. Active primary LINE
 * reporting lines are limited to one.
 *
 * <p>Usage:
 * Use this aggregate to represent operational personnel and accountability. Use identity.User for
 * authentication/security accounts and topology.Station for physical station assets.
 */
public final class Employee implements AggregateRoot<EmployeeId> {

    /**
     * Stable identifier of the employee.
     */
    private final EmployeeId id;

    /**
     * Unique business employee number.
     */
    private final EmployeeNumber employeeNumber;

    /**
     * Full name of the employee.
     */
    private final EmployeeFullName fullName;

    /**
     * Optional professional email address.
     */
    private final EmployeeEmail email;

    /**
     * Current lifecycle status of the employee.
     */
    private final EmploymentStatus status;

    /**
     * Optional neutral reference to an identity user.
     */
    private final IdentityUserReference identityUserReference;

    /**
     * Employee assignments to units and positions.
     */
    private final List<EmployeeAssignment> assignments;

    /**
     * Employee reporting lines, including matrix reporting.
     */
    private final List<ReportingLine> reportingLines;

    /**
     * Instant when the employee was created.
     */
    private final Instant createdAt;

    /**
     * Instant when the employee became active.
     */
    private final Instant activatedAt;

    /**
     * Instant when the employee was suspended.
     */
    private final Instant suspendedAt;

    /**
     * Instant when the employee was disabled.
     */
    private final Instant disabledAt;

    /**
     * Instant when the employee was last updated.
     */
    private final Instant updatedAt;

    private Employee(
            EmployeeId id,
            EmployeeNumber employeeNumber,
            EmployeeFullName fullName,
            EmployeeEmail email,
            EmploymentStatus status,
            IdentityUserReference identityUserReference,
            List<EmployeeAssignment> assignments,
            List<ReportingLine> reportingLines,
            Instant createdAt,
            Instant activatedAt,
            Instant suspendedAt,
            Instant disabledAt,
            Instant updatedAt) {

        this.id = Objects.requireNonNull(id, "Employee id must not be null.");
        this.employeeNumber = Objects.requireNonNull(employeeNumber, "Employee number must not be null.");
        this.fullName = Objects.requireNonNull(fullName, "Employee full name must not be null.");
        this.email = email;
        this.status = Objects.requireNonNull(status, "Employee status must not be null.");
        this.identityUserReference = identityUserReference;
        this.assignments = List.copyOf(Objects.requireNonNull(assignments, "Employee assignments must not be null."));
        this.reportingLines = List.copyOf(Objects.requireNonNull(reportingLines, "Employee reporting lines must not be null."));
        this.createdAt = Objects.requireNonNull(createdAt, "Employee creation instant must not be null.");
        this.activatedAt = activatedAt;
        this.suspendedAt = suspendedAt;
        this.disabledAt = disabledAt;
        this.updatedAt = Objects.requireNonNull(updatedAt, "Employee update instant must not be null.");

        ensureUpdatedAtIsValid();
        ensureAssignmentsBelongToEmployee();
        ensureReportingLinesBelongToEmployee();
        ensureNoDuplicateActiveAssignments();
        ensureOnlyOneActivePrimaryLineReportingLine();
    }

    /**
     * Creates a new registered employee.
     *
     * @param employeeNumber unique business employee number
     * @param fullName employee full name
     * @param email optional professional email
     * @param identityUserReference optional neutral identity user reference
     * @return created employee in REGISTERED status
     */
    public static Employee register(
            EmployeeNumber employeeNumber,
            EmployeeFullName fullName,
            EmployeeEmail email,
            IdentityUserReference identityUserReference) {

        Instant now = Instant.now();
        return new Employee(
                EmployeeId.newId(),
                employeeNumber,
                fullName,
                email,
                EmploymentStatus.REGISTERED,
                identityUserReference,
                List.of(),
                List.of(),
                now,
                null,
                null,
                null,
                now);
    }

    /**
     * Rehydrates an existing employee from persistence without importing persistence classes.
     *
     * @param id employee identifier
     * @param employeeNumber business employee number
     * @param fullName employee full name
     * @param email optional professional email
     * @param status lifecycle status
     * @param identityUserReference optional neutral identity user reference
     * @param assignments employee assignments
     * @param reportingLines employee reporting lines
     * @param createdAt creation instant
     * @param activatedAt activation instant
     * @param suspendedAt suspension instant
     * @param disabledAt disabled instant
     * @param updatedAt update instant
     * @return restored employee
     */
    public static Employee restore(
            EmployeeId id,
            EmployeeNumber employeeNumber,
            EmployeeFullName fullName,
            EmployeeEmail email,
            EmploymentStatus status,
            IdentityUserReference identityUserReference,
            List<EmployeeAssignment> assignments,
            List<ReportingLine> reportingLines,
            Instant createdAt,
            Instant activatedAt,
            Instant suspendedAt,
            Instant disabledAt,
            Instant updatedAt) {

        return new Employee(
                id,
                employeeNumber,
                fullName,
                email,
                status,
                identityUserReference,
                assignments,
                reportingLines,
                createdAt,
                activatedAt,
                suspendedAt,
                disabledAt,
                updatedAt);
    }

    @Override
    public EmployeeId id() {
        return id;
    }

    /**
     * Returns the unique employee number.
     *
     * @return employee number
     */
    public EmployeeNumber employeeNumber() {
        return employeeNumber;
    }

    /**
     * Returns the employee full name.
     *
     * @return full name
     */
    public EmployeeFullName fullName() {
        return fullName;
    }

    /**
     * Returns the optional professional email.
     *
     * @return optional email
     */
    public Optional<EmployeeEmail> email() {
        return Optional.ofNullable(email);
    }

    /**
     * Returns the current employee lifecycle status.
     *
     * @return employment status
     */
    public EmploymentStatus status() {
        return status;
    }

    /**
     * Returns the optional identity user reference.
     *
     * @return optional identity user reference
     */
    public Optional<IdentityUserReference> identityUserReference() {
        return Optional.ofNullable(identityUserReference);
    }

    /**
     * Returns immutable employee assignments.
     *
     * @return assignments
     */
    public List<EmployeeAssignment> assignments() {
        return assignments;
    }

    /**
     * Returns immutable employee reporting lines.
     *
     * @return reporting lines
     */
    public List<ReportingLine> reportingLines() {
        return reportingLines;
    }

    /**
     * Returns the creation instant.
     *
     * @return creation instant
     */
    public Instant createdAt() {
        return createdAt;
    }

    /**
     * Returns the activation instant when available.
     *
     * @return optional activation instant
     */
    public Optional<Instant> activatedAt() {
        return Optional.ofNullable(activatedAt);
    }

    /**
     * Returns the suspension instant when available.
     *
     * @return optional suspension instant
     */
    public Optional<Instant> suspendedAt() {
        return Optional.ofNullable(suspendedAt);
    }

    /**
     * Returns the disabled instant when available.
     *
     * @return optional disabled instant
     */
    public Optional<Instant> disabledAt() {
        return Optional.ofNullable(disabledAt);
    }

    /**
     * Returns the last update instant.
     *
     * @return update instant
     */
    public Instant updatedAt() {
        return updatedAt;
    }

    /**
     * Activates a registered or suspended employee.
     *
     * @return active employee
     */
    public Employee activate() {
        if (status == EmploymentStatus.ACTIVE) {
            return this;
        }

        if (status == EmploymentStatus.DISABLED) {
            throw new BusinessRuleViolationException("Disabled employee cannot be activated.");
        }

        Instant now = Instant.now();
        return copyWithStatus(EmploymentStatus.ACTIVE, now, suspendedAt, disabledAt, now);
    }

    /**
     * Suspends an active employee.
     *
     * @return suspended employee
     */
    public Employee suspend() {
        if (status == EmploymentStatus.SUSPENDED) {
            return this;
        }

        if (status != EmploymentStatus.ACTIVE) {
            throw new BusinessRuleViolationException("Only active employee can be suspended.");
        }

        Instant now = Instant.now();
        return copyWithStatus(EmploymentStatus.SUSPENDED, activatedAt, now, disabledAt, now);
    }

    /**
     * Disables the employee.
     *
     * @return disabled employee
     */
    public Employee disable() {
        if (status == EmploymentStatus.DISABLED) {
            return this;
        }

        Instant now = Instant.now();
        return copyWithStatus(EmploymentStatus.DISABLED, activatedAt, suspendedAt, now, now);
    }

    /**
     * Changes the employee full name.
     *
     * @param newFullName new full name
     * @return updated employee
     */
    public Employee rename(EmployeeFullName newFullName) {
        return new Employee(
                id,
                employeeNumber,
                newFullName,
                email,
                status,
                identityUserReference,
                assignments,
                reportingLines,
                createdAt,
                activatedAt,
                suspendedAt,
                disabledAt,
                Instant.now());
    }

    /**
     * Changes the optional professional email.
     *
     * @param newEmail new optional email
     * @return updated employee
     */
    public Employee changeEmail(EmployeeEmail newEmail) {
        return new Employee(
                id,
                employeeNumber,
                fullName,
                newEmail,
                status,
                identityUserReference,
                assignments,
                reportingLines,
                createdAt,
                activatedAt,
                suspendedAt,
                disabledAt,
                Instant.now());
    }

    /**
     * Links the employee to a neutral identity user reference.
     *
     * @param newIdentityUserReference neutral identity user reference
     * @return updated employee
     */
    public Employee linkIdentityUser(IdentityUserReference newIdentityUserReference) {
        return new Employee(
                id,
                employeeNumber,
                fullName,
                email,
                status,
                newIdentityUserReference,
                assignments,
                reportingLines,
                createdAt,
                activatedAt,
                suspendedAt,
                disabledAt,
                Instant.now());
    }

    /**
     * Adds a new employee assignment.
     *
     * @param assignment assignment to add
     * @return updated employee
     */
    public Employee addAssignment(EmployeeAssignment assignment) {
        Objects.requireNonNull(assignment, "Employee assignment must not be null.");
        ensureCanReceiveOperationalResponsibility();

        if (!id.equals(assignment.employeeId())) {
            throw new BusinessRuleViolationException("Assignment employee id must match aggregate employee id.");
        }

        List<EmployeeAssignment> updatedAssignments = new ArrayList<>(assignments);
        updatedAssignments.add(assignment);

        return new Employee(
                id,
                employeeNumber,
                fullName,
                email,
                status,
                identityUserReference,
                updatedAssignments,
                reportingLines,
                createdAt,
                activatedAt,
                suspendedAt,
                disabledAt,
                Instant.now());
    }

    /**
     * Adds a new reporting line.
     *
     * @param reportingLine reporting line to add
     * @return updated employee
     */
    public Employee addReportingLine(ReportingLine reportingLine) {
        Objects.requireNonNull(reportingLine, "Reporting line must not be null.");
        ensureCanReceiveOperationalResponsibility();

        if (!id.equals(reportingLine.employeeId())) {
            throw new BusinessRuleViolationException("Reporting line employee id must match aggregate employee id.");
        }

        List<ReportingLine> updatedReportingLines = new ArrayList<>(reportingLines);
        updatedReportingLines.add(reportingLine);

        return new Employee(
                id,
                employeeNumber,
                fullName,
                email,
                status,
                identityUserReference,
                assignments,
                updatedReportingLines,
                createdAt,
                activatedAt,
                suspendedAt,
                disabledAt,
                Instant.now());
    }

    /**
     * Ensures this employee can receive a new assignment or reporting line.
     */
    public void ensureCanReceiveOperationalResponsibility() {
        if (!status.allowsOperationalAssignment()) {
            throw new BusinessRuleViolationException("Employee cannot receive new operational responsibility in status " + status + ".");
        }
    }

    private Employee copyWithStatus(
            EmploymentStatus newStatus,
            Instant newActivatedAt,
            Instant newSuspendedAt,
            Instant newDisabledAt,
            Instant newUpdatedAt) {

        return new Employee(
                id,
                employeeNumber,
                fullName,
                email,
                newStatus,
                identityUserReference,
                assignments,
                reportingLines,
                createdAt,
                newActivatedAt,
                newSuspendedAt,
                newDisabledAt,
                newUpdatedAt);
    }

    private void ensureUpdatedAtIsValid() {
        if (updatedAt.isBefore(createdAt)) {
            throw new BusinessRuleViolationException("Employee updatedAt must not be before createdAt.");
        }
    }

    private void ensureAssignmentsBelongToEmployee() {
        for (EmployeeAssignment assignment : assignments) {
            if (!id.equals(assignment.employeeId())) {
                throw new BusinessRuleViolationException("All assignments must belong to the employee aggregate.");
            }
        }
    }

    private void ensureReportingLinesBelongToEmployee() {
        for (ReportingLine reportingLine : reportingLines) {
            if (!id.equals(reportingLine.employeeId())) {
                throw new BusinessRuleViolationException("All reporting lines must belong to the employee aggregate.");
            }
        }
    }

    private void ensureNoDuplicateActiveAssignments() {
        LocalDate today = LocalDate.now();

        for (int i = 0; i < assignments.size(); i++) {
            EmployeeAssignment first = assignments.get(i);
            if (!first.isActiveOn(today)) {
                continue;
            }

            for (int j = i + 1; j < assignments.size(); j++) {
                EmployeeAssignment second = assignments.get(j);
                if (second.isActiveOn(today) && first.targetsSameUnitAndPosition(second)) {
                    throw new BusinessRuleViolationException("Employee cannot have duplicate active assignment to the same unit and position.");
                }
            }
        }
    }

    private void ensureOnlyOneActivePrimaryLineReportingLine() {
        LocalDate today = LocalDate.now();
        long activePrimaryLineCount = reportingLines.stream()
                .filter(reportingLine -> reportingLine.isActivePrimaryLineOn(today))
                .count();

        if (activePrimaryLineCount > 1) {
            throw new BusinessRuleViolationException("Employee cannot have more than one active primary LINE reporting line.");
        }
    }
}
