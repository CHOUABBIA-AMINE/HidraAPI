/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : EmployeeAppointmentChange
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-20
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization.domain.model
 *
 * @Description : Validates detached versioned appointment changes without persistence or authorization.
 *
 */
package dz.sh.hidra.modules.organization.domain.model;

import dz.sh.hidra.modules.organization.domain.exception.InvalidOrganizationValueException;
import dz.sh.hidra.modules.organization.domain.value.AssignmentStatus;
import dz.sh.hidra.modules.organization.domain.value.AssignmentType;
import java.time.Instant;
import java.util.Objects;

/**
 * Immutable change proposal, not a permission, persisted event or transaction receipt.
 * Recorded time is explicit input; a runtime owner must supply its trusted sample.
 */
public record EmployeeAppointmentChange(
        Kind kind,
        VersionedAssignment before,
        VersionedAssignment after,
        VersionedAssignment successor,
        String reason,
        Instant recordedAt
) {
    public enum Kind { END, TRANSFER, CORRECT }

    public record VersionedAssignment(long version, EmployeeAssignment assignment) {
        public VersionedAssignment {
            require(version > 0, "Appointment version must be positive.");
            require(assignment != null, "Appointment snapshot is required.");
        }
    }

    public EmployeeAppointmentChange {
        require(kind != null && before != null && after != null,
                "Change kind and before/after snapshots are required.");
        reason = text(reason, "A change reason is required.");
        require(recordedAt != null, "Recorded time is required.");
        require(before.version() < Long.MAX_VALUE, "Appointment version overflow.");
        require(after.version() == before.version() + 1, "Next appointment version must advance by one.");
        EmployeeAssignment old = before.assignment();
        EmployeeAssignment changed = after.assignment();
        require(old.id().equals(changed.id()) && old.employeeId().equals(changed.employeeId()),
                "Correction or transition cannot replace assignment or employee identity.");
        require(Objects.equals(old.createdAt(), changed.createdAt()), "Creation time must be retained.");
        require(recordedAt.equals(changed.updatedAt()), "Changed snapshot must use recorded time.");
        require(old.updatedAt() == null || !recordedAt.isBefore(old.updatedAt()),
                "Recorded time cannot precede the observed snapshot.");
        require(changed.status() != AssignmentStatus.ENDED
                        || (changed.validTo() != null && !changed.validTo().isAfter(recordedAt)),
                "A future or open ending cannot already be ENDED.");
        if (kind == Kind.CORRECT) {
            require(successor == null, "Correction has no transfer successor.");
        } else {
            require(old.status() == AssignmentStatus.ACTIVE, "Only an ACTIVE source can end or transfer.");
            require(old.organizationUnitId().equals(changed.organizationUnitId())
                            && old.positionId().equals(changed.positionId())
                            && old.assignmentType() == changed.assignmentType()
                            && old.validFrom().equals(changed.validFrom()),
                    "End and transfer must retain source membership and start.");
            Instant end = changed.validTo();
            require(end != null && end.isAfter(old.validFrom()), "End must be after the source start.");
            require(old.validTo() == null || !end.isAfter(old.validTo()), "End cannot extend the source.");
            require(changed.status() == endingStatus(end, recordedAt),
                    "Source status must reflect the effective ending boundary.");
            if (kind == Kind.END) {
                require(successor == null, "End has no transfer successor.");
            } else {
                require(old.validTo() == null || end.isBefore(old.validTo()),
                        "Transfer must shorten a finite source interval.");
                require(successor != null && successor.version() == 1, "Transfer successor starts at version one.");
                EmployeeAssignment next = successor.assignment();
                require(!old.id().equals(next.id()) && old.employeeId().equals(next.employeeId()),
                        "Transfer needs a new assignment for the same employee.");
                require(end.equals(next.validFrom()), "Transfer intervals must be exactly adjacent.");
                require(recordedAt.equals(next.createdAt()) && recordedAt.equals(next.updatedAt()),
                        "Transfer successor must use recorded creation/update time.");
                require(next.status() == (next.validTo() == null ? AssignmentStatus.ACTIVE
                                : endingStatus(next.validTo(), recordedAt)),
                        "Successor status must reflect its effective ending boundary.");
            }
        }
    }

    public static EmployeeAppointmentChange end(
            VersionedAssignment source, Instant endAt, Instant recordedAt, String reason) {
        require(source != null, "Source is required.");
        require(endAt != null && recordedAt != null, "End and recorded time are required.");
        EmployeeAssignment old = source.assignment();
        EmployeeAssignment changed = copy(old, old.organizationUnitId(), old.positionId(), old.assignmentType(),
                old.validFrom(), endAt, endingStatus(endAt, recordedAt), recordedAt);
        return new EmployeeAppointmentChange(Kind.END, source, next(source, changed), null, reason, recordedAt);
    }

    public static EmployeeAppointmentChange transfer(
            VersionedAssignment source, String successorId, String unitId, String positionId,
            AssignmentType type, Instant at, Instant successorEnd, Instant recordedAt, String reason) {
        require(source != null, "Source is required.");
        require(at != null && recordedAt != null, "Transfer and recorded time are required.");
        EmployeeAssignment old = source.assignment();
        EmployeeAssignment changed = copy(old, old.organizationUnitId(), old.positionId(), old.assignmentType(),
                old.validFrom(), at, endingStatus(at, recordedAt), recordedAt);
        EmployeeAssignment successor = new EmployeeAssignment(successorId, old.employeeId(), unitId, positionId,
                type, at, successorEnd, successorEnd == null ? AssignmentStatus.ACTIVE
                        : endingStatus(successorEnd, recordedAt), recordedAt, recordedAt);
        return new EmployeeAppointmentChange(Kind.TRANSFER, source, next(source, changed),
                new VersionedAssignment(1, successor), reason, recordedAt);
    }

    public static EmployeeAppointmentChange correct(
            VersionedAssignment source, EmployeeAssignment replacement, Instant recordedAt, String reason) {
        require(source != null, "Source is required.");
        return new EmployeeAppointmentChange(Kind.CORRECT, source, next(source, replacement), null, reason, recordedAt);
    }

    private static VersionedAssignment next(VersionedAssignment source, EmployeeAssignment changed) {
        require(source.version() < Long.MAX_VALUE, "Appointment version overflow.");
        return new VersionedAssignment(source.version() + 1, changed);
    }

    private static EmployeeAssignment copy(EmployeeAssignment old, String unit, String position, AssignmentType type,
            Instant from, Instant to, AssignmentStatus status, Instant recordedAt) {
        return new EmployeeAssignment(old.id(), old.employeeId(), unit, position, type, from, to, status,
                old.createdAt(), recordedAt);
    }

    private static AssignmentStatus endingStatus(Instant end, Instant recordedAt) {
        return end.isAfter(recordedAt) ? AssignmentStatus.ACTIVE : AssignmentStatus.ENDED;
    }

    private static String text(String value, String message) {
        require(value != null && !value.isBlank(), message);
        return value.trim();
    }

    private static void require(boolean condition, String message) {
        if (!condition) throw new InvalidOrganizationValueException(message);
    }
}
