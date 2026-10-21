package dz.sh.hidra.modules.organization.application.query;

import dz.sh.hidra.modules.organization.domain.exception.InvalidOrganizationValueException;

/**
 * Selector-only read contracts. Successful validation does not authorize
 * existence, counts, history, policy, or cursor disclosure. Runtime owner must
 * resolve and authorize the complete descriptor closure before returning data.
 */
public sealed interface EmployeeAppointmentQuery permits
        EmployeeAppointmentQuery.History, EmployeeAppointmentQuery.ExactVersion,
        EmployeeAppointmentQuery.Policy {

    String companyPartyId();

    enum Scope { ASSIGNMENT, EMPLOYEE, POSITION }

    record History(Scope scope, String companyPartyId, String assignmentId,
                   String employeeId, String unitId, String positionId,
                   int limit, String cursor) implements EmployeeAppointmentQuery {
        public History {
            require(scope != null, "History selector scope is required");
            companyPartyId = exact(companyPartyId, "Company");
            require(limit >= 1 && limit <= 100, "History page limit must be 1..100");
            require(cursor == null || (!cursor.isBlank() && cursor.length() <= 2048
                    && cursor.codePoints().noneMatch(Character::isISOControl)),
                    "Invalid opaque history cursor");
            switch (scope) {
                case ASSIGNMENT -> {
                    assignmentId = exact(assignmentId, "Assignment");
                    require(employeeId == null && unitId == null && positionId == null,
                            "Unexpected assignment-history selector");
                }
                case EMPLOYEE -> {
                    employeeId = exact(employeeId, "Employee");
                    require(assignmentId == null && unitId == null && positionId == null,
                            "Unexpected employee-history selector");
                }
                case POSITION -> {
                    unitId = exact(unitId, "Unit");
                    positionId = exact(positionId, "Position");
                    require(assignmentId == null && employeeId == null,
                            "Unexpected position-history selector");
                }
            }
        }

        public static History assignment(String company, String assignment, int limit, String cursor) {
            return new History(Scope.ASSIGNMENT, company, assignment, null, null, null, limit, cursor);
        }

        public static History employee(String company, String employee, int limit, String cursor) {
            return new History(Scope.EMPLOYEE, company, null, employee, null, null, limit, cursor);
        }

        public static History position(String company, String unit, String position,
                                       int limit, String cursor) {
            return new History(Scope.POSITION, company, null, null, unit, position, limit, cursor);
        }
    }

    record ExactVersion(String companyPartyId, String assignmentId, long version)
            implements EmployeeAppointmentQuery {
        public ExactVersion {
            companyPartyId = exact(companyPartyId, "Company");
            assignmentId = exact(assignmentId, "Assignment");
            require(version > 0, "Exact appointment version must be positive");
        }
    }

    record Policy(String companyPartyId, String unitId) implements EmployeeAppointmentQuery {
        public Policy {
            companyPartyId = exact(companyPartyId, "Company");
            unitId = exact(unitId, "Unit");
        }
    }

    private static String exact(String value, String label) {
        require(value != null && !value.isEmpty() && value.length() <= 80
                && !value.isBlank(), label + " identifier must be 1..80 characters");
        require(!Character.isWhitespace(value.codePointAt(0))
                && !Character.isSpaceChar(value.codePointAt(0))
                && !Character.isWhitespace(value.codePointBefore(value.length()))
                && !Character.isSpaceChar(value.codePointBefore(value.length()))
                && value.codePoints().noneMatch(cp -> Character.isISOControl(cp)
                    || Character.getType(cp) == Character.SURROGATE),
                label + " selector has unsafe characters or boundaries");
        return value; // Caller input is not a trusted normalized owner identifier.
    }

    private static void require(boolean ok, String message) {
        if (!ok) throw new InvalidOrganizationValueException(message);
    }
}
