package dz.sh.hidra.modules.organization.application.command;

import dz.sh.hidra.modules.organization.domain.exception.InvalidOrganizationValueException;
import dz.sh.hidra.modules.organization.domain.value.AssignmentStatus;
import dz.sh.hidra.modules.organization.domain.value.AssignmentType;
import java.time.Instant;

/**
 * Untrusted, immutable application selectors, never a native authorization, Party
 * eligibility result, policy approval, persisted snapshot or permission ticket.
 * Ownership, versions, conflicts and current physical time must be verified
 * again inside the protected transaction.
 */
public sealed interface EmployeeAppointmentCommand permits
        EmployeeAppointmentCommand.Create, EmployeeAppointmentCommand.End,
        EmployeeAppointmentCommand.Transfer, EmployeeAppointmentCommand.Correct {

    enum Kind { CREATE, END, TRANSFER, CORRECT }

    Kind kind();
    String requestId();
    String companyPartyId();

    /** Legacy selectors only. Defaults that depend on authority are resolved under owner locks. */
    record LegacyCreate(String requestId, String employeeId, String organizationUnitId,
                        String positionId, AssignmentType assignmentType,
                        Instant validFrom, Instant validTo) {
        public LegacyCreate {
            requestId=identifier(requestId==null?java.util.UUID.randomUUID().toString():requestId,"Request");
            employeeId=identifier(employeeId==null?null:employeeId.trim(),"Employee selector");
            organizationUnitId=identifier(organizationUnitId==null?null:organizationUnitId.trim(),"Unit selector");
            positionId=identifier(positionId==null?null:positionId.trim(),"Position selector");
            if(validFrom!=null)timestamp(validFrom,"Start");
            if(validTo!=null)timestamp(validTo,"End");
            if(validFrom!=null && validTo!=null)require(validTo.isAfter(validFrom),"Half-open interval end must follow its start");
        }
    }

    record Create(String requestId, String companyPartyId, String employeeId,
                  String organizationUnitId, String positionId, AssignmentType assignmentType,
                  Instant validFrom, Instant validTo,
                  long expectedBindingRevision, long expectedPolicyRevision)
            implements EmployeeAppointmentCommand {
        public Create {
            requestId = identifier(requestId, "Request");
            companyPartyId = identifier(companyPartyId, "Company selector");
            employeeId = identifier(employeeId, "Employee selector");
            organizationUnitId = identifier(organizationUnitId, "Unit selector");
            positionId = identifier(positionId, "Position selector");
            require(assignmentType != null, "Assignment type is required");
            interval(validFrom, validTo);
            positive(expectedBindingRevision, "Binding revision");
            positive(expectedPolicyRevision, "Policy revision");
        }

        @Override public Kind kind() { return Kind.CREATE; }
    }

    record End(String requestId, String companyPartyId, String assignmentId,
               long expectedVersion, Instant endAt, String reason)
            implements EmployeeAppointmentCommand {
        public End {
            requestId = identifier(requestId, "Request");
            companyPartyId = identifier(companyPartyId, "Company selector");
            assignmentId = identifier(assignmentId, "Assignment selector");
            nextVersion(expectedVersion);
            timestamp(endAt, "Effective end");
            reason = EmployeeAppointmentCommand.reason(reason);
        }

        @Override public Kind kind() { return Kind.END; }
    }

    record Transfer(String requestId, String companyPartyId, String assignmentId,
                    long expectedVersion, String destinationOrganizationUnitId,
                    String destinationPositionId, AssignmentType destinationType,
                    Instant effectiveAt, Instant successorValidTo,
                    long expectedDestinationBindingRevision,
                    long expectedDestinationPolicyRevision, String reason)
            implements EmployeeAppointmentCommand {
        public Transfer {
            requestId = identifier(requestId, "Request");
            companyPartyId = identifier(companyPartyId, "Company selector");
            assignmentId = identifier(assignmentId, "Assignment selector");
            nextVersion(expectedVersion);
            destinationOrganizationUnitId = identifier(destinationOrganizationUnitId, "Destination unit");
            destinationPositionId = identifier(destinationPositionId, "Destination position");
            require(destinationType != null, "Destination assignment type is required");
            interval(effectiveAt, successorValidTo);
            positive(expectedDestinationBindingRevision, "Destination binding revision");
            positive(expectedDestinationPolicyRevision, "Destination policy revision");
            reason = EmployeeAppointmentCommand.reason(reason);
        }

        @Override public Kind kind() { return Kind.TRANSFER; }
    }

    record Correct(String requestId, String companyPartyId, String assignmentId,
                   long expectedVersion, String correctedOrganizationUnitId,
                   String correctedPositionId, AssignmentType correctedType,
                   AssignmentStatus correctedStatus, Instant correctedValidFrom,
                   Instant correctedValidTo, long expectedDestinationBindingRevision,
                   long expectedDestinationPolicyRevision, String reason)
            implements EmployeeAppointmentCommand {
        public Correct {
            requestId = identifier(requestId, "Request");
            companyPartyId = identifier(companyPartyId, "Company selector");
            assignmentId = identifier(assignmentId, "Assignment selector");
            nextVersion(expectedVersion);
            correctedOrganizationUnitId = identifier(correctedOrganizationUnitId, "Corrected unit");
            correctedPositionId = identifier(correctedPositionId, "Corrected position");
            require(correctedType != null && correctedStatus != null,
                    "Corrected type and status are required");
            interval(correctedValidFrom, correctedValidTo);
            require(correctedStatus != AssignmentStatus.ENDED || correctedValidTo != null,
                    "Ended appointment needs an ending boundary");
            positive(expectedDestinationBindingRevision, "Destination binding revision");
            positive(expectedDestinationPolicyRevision, "Destination policy revision");
            reason = EmployeeAppointmentCommand.reason(reason);
        }

        @Override public Kind kind() { return Kind.CORRECT; }
    }

    private static String identifier(String value, String label) {
        require(value != null && !value.isEmpty() && value.length() <= 80,
                label + " identifier must be 1..80 characters");
        require(!value.isBlank() && !edgeSpace(value.codePointAt(0))
                        && !edgeSpace(value.codePointBefore(value.length())),
                label + " identifier has unsafe boundaries");
        require(value.codePoints().noneMatch(cp -> Character.isISOControl(cp)
                        || Character.getType(cp) == Character.SURROGATE),
                label + " identifier contains unsafe characters");
        return value; // No trim, case folding, Unicode normalization or aliases.
    }

    private static boolean edgeSpace(int codepoint) {
        return Character.isWhitespace(codepoint) || Character.isSpaceChar(codepoint);
    }

    private static String reason(String value) {
        require(value != null && !value.isBlank() && value.length() <= 2000,
                "Nonblank change reason must be at most 2000 characters");
        require(value.codePoints().noneMatch(cp -> Character.isISOControl(cp)
                && cp != '\n' && cp != '\t'), "Change reason contains unsafe control");
        return value; // Never silently change bytes used for command digest.
    }

    private static void timestamp(Instant value, String label) {
        require(value != null && value.getNano() % 1_000 == 0,
                label + " requires exact PostgreSQL microsecond precision");
    }

    private static void interval(Instant start, Instant end) {
        timestamp(start, "Start");
        if (end != null) {
            timestamp(end, "End");
            require(end.isAfter(start), "Half-open interval end must follow its start");
        }
    }

    private static void positive(long value, String label) {
        require(value > 0, label + " must be positive");
    }

    private static void nextVersion(long value) {
        require(value > 0 && value < Long.MAX_VALUE,
                "Expected appointment version must be positive and advanceable");
    }

    private static void require(boolean ok, String message) {
        if (!ok) throw new InvalidOrganizationValueException(message);
    }
}
