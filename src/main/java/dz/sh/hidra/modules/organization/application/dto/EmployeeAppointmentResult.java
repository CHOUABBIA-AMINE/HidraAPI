/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : EmployeeAppointmentResult
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-21
 *
 * @Type        : Class
 * @Layer       : Application
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization.application.dto
 *
 * @Description : Carries authorized immutable appointment results and redacted occupancy history without granting authority.
 *
 */
package dz.sh.hidra.modules.organization.application.dto;

import dz.sh.hidra.modules.organization.application.command.EmployeeAppointmentCommand;
import dz.sh.hidra.modules.organization.domain.exception.InvalidOrganizationValueException;
import dz.sh.hidra.modules.organization.domain.model.CompanyBoundEmployeeAppointment;
import dz.sh.hidra.modules.organization.domain.model.EmployeeAssignment;
import dz.sh.hidra.modules.organization.domain.policy.EmployeeAppointmentPolicy;
import dz.sh.hidra.modules.organization.domain.value.AssignmentStatus;
import dz.sh.hidra.modules.organization.domain.value.AssignmentType;
import java.time.Instant;
import java.util.List;
import java.util.Objects;

/**
 * Application output shapes, not authorization evidence or REST DTOs. A caller may
 * materialize one only AFTER the coordinator has authorized the full closure and
 * the database has committed guarded event/receipt evidence. Construction alone
 * never proves any such check.
 */
public final class EmployeeAppointmentResult {
    private EmployeeAppointmentResult() { }

    /** Full internal snapshot, preserving the ORIGINAL binding rather than a mutable head. */
    public record PinnedVersion(CompanyBoundEmployeeAppointment snapshot,
                                String policyId, long policyRevision) {
        public PinnedVersion {
            require(snapshot != null, "Pinned appointment and binding are required.");
            policyId = id(policyId, "Policy");
            require(policyRevision > 0, "Pinned policy revision must be positive.");
        }
        public String companyPartyId() { return snapshot.binding().companyPartyId(); }
        public String assignmentId() { return snapshot.appointment().assignment().id(); }
        public long appointmentVersion() { return snapshot.appointment().version(); }
    }

    /**
     * Unit/position occupant history deliberately carries NO employee identifier,
     * personal name, contact, actor, or permission. Never substitute PinnedVersion
     * for this shape at the position-history transport boundary.
     */
    public record PositionVersion(String assignmentId, String companyPartyId,
                                  String organizationUnitId, String positionId,
                                  AssignmentType assignmentType, AssignmentStatus status,
                                  Instant validFrom, Instant validTo, long appointmentVersion,
                                  String bindingId, long bindingRevision,
                                  String policyId, long policyRevision) {
        public PositionVersion {
            assignmentId = id(assignmentId, "Assignment");
            companyPartyId = id(companyPartyId, "Company");
            organizationUnitId = id(organizationUnitId, "Unit");
            positionId = id(positionId, "Position");
            bindingId = id(bindingId, "Binding");
            policyId = id(policyId, "Policy");
            require(assignmentType != null && status != null, "Position type and status are required.");
            require(validFrom != null && (validTo == null || validTo.isAfter(validFrom)),
                    "Position period must be a valid half-open interval.");
            require(appointmentVersion > 0 && bindingRevision > 0 && policyRevision > 0,
                    "Positive appointment and revision numbers are required.");
        }
        public static PositionVersion redact(PinnedVersion source) {
            require(source != null, "A pinned source is required for redaction.");
            EmployeeAssignment value = source.snapshot().appointment().assignment();
            var binding = source.snapshot().binding();
            return new PositionVersion(value.id(), binding.companyPartyId(),
                    value.organizationUnitId(), value.positionId(), value.assignmentType(),
                    value.status(), value.validFrom(), value.validTo(),
                    source.snapshot().appointment().version(), binding.id(),
                    binding.revision(), source.policyId(), source.policyRevision());
        }
    }

    /** A detached result of a guarded commit, or an explicitly historical replay. */
    public record MutationReceipt(EmployeeAppointmentCommand.Kind kind, String requestId,
                                  String eventId, Instant sampledAt, boolean historicalReplay,
                                  PinnedVersion before, PinnedVersion after,
                                  PinnedVersion successor) {
        public MutationReceipt {
            requestId = id(requestId, "Request");
            eventId = id(eventId, "Event");
            require(sampledAt != null, "Event sample is required.");
            shape(kind, before, after, successor);
        }
    }

    /** Private employee/assignment history only; never use for position-only reads. */
    public record PrivateHistoryEvent(String eventId, EmployeeAppointmentCommand.Kind kind,
                                      Instant sampledAt, PinnedVersion before,
                                      PinnedVersion after, PinnedVersion successor) {
        public PrivateHistoryEvent {
            eventId = id(eventId, "Event");
            require(sampledAt != null, "History event time is required.");
            shape(kind, before, after, successor);
        }
    }

    /** Redacted occupant history: each contained snapshot lacks employee identity. */
    public record PositionHistoryEvent(String eventId, EmployeeAppointmentCommand.Kind kind,
                                       Instant sampledAt, PositionVersion before,
                                       PositionVersion after, PositionVersion successor) {
        public PositionHistoryEvent {
            eventId = id(eventId, "Event");
            require(sampledAt != null, "History event time is required.");
            shape(kind, before, after, successor);
        }
    }

    /**
     * No total count is exposed, and the opaque cursor is not an authorization
     * credential. The owner must re-derive and authorize its complete scope.
     */
    public record HistoryPage<T>(List<T> entries, String nextCursor, boolean hasNext) {
        public HistoryPage {
            require(entries != null && entries.size() <= 100
                    && entries.stream().noneMatch(Objects::isNull),
                    "History page must have at most 100 non-null entries.");
            require(hasNext == (nextCursor != null), "Cursor and continuation must agree.");
            require(nextCursor == null || (!nextCursor.isBlank() && nextCursor.length() <= 2048
                    && nextCursor.codePoints().noneMatch(Character::isISOControl)),
                    "Malformed history cursor.");
            entries = List.copyOf(entries);
        }
    }

    /** Fully pinned policy read; its presence is not administrator activation. */
    public record PolicyView(String companyPartyId, String organizationUnitId,
                             String policyId, long revision,
                             EmployeeAppointmentPolicy policy) {
        public PolicyView {
            companyPartyId = id(companyPartyId, "Company");
            organizationUnitId = id(organizationUnitId, "Unit");
            policyId = id(policyId, "Policy");
            require(revision > 0 && policy != null, "A complete pinned policy is required.");
        }
    }

    private static void shape(EmployeeAppointmentCommand.Kind kind,
                              Object before, Object after, Object successor) {
        require(kind != null && after != null, "An event kind and resulting version are required.");
        switch (kind) {
            case CREATE -> require(before == null && successor == null,
                    "Creation has one version and no source or successor.");
            case END, CORRECT -> require(before != null && successor == null,
                    "End/correction has a source and one resulting version.");
            case TRANSFER -> require(before != null && successor != null,
                    "Transfer must retain both the source and distinct successor.");
        }
    }

    private static String id(String value, String description) {
        require(value != null && !value.isEmpty() && value.length() <= 80 && !value.isBlank(),
                description + " identifier must be 1..80 characters.");
        require(!Character.isWhitespace(value.codePointAt(0))
                && !Character.isSpaceChar(value.codePointAt(0))
                && !Character.isWhitespace(value.codePointBefore(value.length()))
                && !Character.isSpaceChar(value.codePointBefore(value.length()))
                && value.codePoints().noneMatch(cp -> Character.isISOControl(cp)
                || Character.getType(cp) == Character.SURROGATE),
                description + " identifier has unsafe boundaries or characters.");
        return value;
    }

    private static void require(boolean condition, String message) {
        if (!condition) throw new InvalidOrganizationValueException(message);
    }
}
