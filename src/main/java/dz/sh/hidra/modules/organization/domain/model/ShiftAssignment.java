/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ShiftAssignment
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
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
        id = normalize(id);
        employeeId = normalize(employeeId);
        shiftId = normalize(shiftId);
        organizationUnitId = normalize(organizationUnitId);
        }

        private static String normalize(String value) {
            if (value == null || value.isBlank()) {
                return null;
            }
            return value.trim();
        }
    }
