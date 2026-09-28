/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ShiftAssignment
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-28
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization.domain.model
 *
 * @Description : Employee assignment to shift.
 *
 */
package dz.sh.hidra.modules.organization.domain.model;

import dz.sh.hidra.modules.organization.domain.exception.InvalidOrganizationValueException;
import dz.sh.hidra.modules.organization.domain.value.*;
import java.time.Instant;

    /**
     * Employee assignment to shift.
     *
         * @param id id
     * @param employeeId employeeId
     * @param shiftId shiftId
     * @param organizationUnitId organizationUnitId
     * @param validFrom validFrom
     * @param validTo validTo
     * @param status status
     * @param createdAt createdAt
     * @param updatedAt updatedAt
     */
    public record ShiftAssignment(
            String id,
        String employeeId,
        String shiftId,
        String organizationUnitId,
        Instant validFrom,
        Instant validTo,
        ShiftAssignmentStatus status,
        Instant createdAt,
        Instant updatedAt
    ) {

        public ShiftAssignment {
        id = requireText(id, "Shift assignment ID is required.");
        employeeId = requireText(employeeId, "Shift assignment employee ID is required.");
        shiftId = requireText(shiftId, "Shift assignment shift ID is required.");
        organizationUnitId = requireText(organizationUnitId, "Shift assignment organization-unit ID is required.");
        if (status == null) {
            throw new InvalidOrganizationValueException("Shift assignment status is required.");
        }
        requireEffectivePeriod(validFrom, validTo, "Shift assignment");
        }
    private static void requireEffectivePeriod(Instant validFrom, Instant validTo, String subject) {
        if (validFrom == null) {
            throw new InvalidOrganizationValueException(subject + " validFrom is required.");
        }
        if (validTo != null && !validTo.isAfter(validFrom)) {
            throw new InvalidOrganizationValueException(subject + " validTo must be after validFrom.");
        }
    }

    private static String requireText(String value, String message) {
        String normalized = normalize(value);
        if (normalized == null) {
            throw new InvalidOrganizationValueException(message);
        }
        return normalized;
    }


        private static String normalize(String value) {
            if (value == null || value.isBlank()) {
                return null;
            }
            return value.trim();
        }
    }
