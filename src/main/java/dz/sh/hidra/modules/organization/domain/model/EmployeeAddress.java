/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : EmployeeAddress
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-28
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization.domain.model
 *
 * @Description : Employee address using locality as normalized anchor.
 *
 */
package dz.sh.hidra.modules.organization.domain.model;

import dz.sh.hidra.modules.organization.domain.exception.InvalidOrganizationValueException;
import dz.sh.hidra.modules.organization.domain.value.*;
import java.time.Instant;

    /**
     * Employee address using locality as normalized anchor.
     *
         * @param id id
     * @param employeeId employeeId
     * @param addressType addressType
     * @param localityId localityId
     * @param streetLine1 streetLine1
     * @param streetLine2 streetLine2
     * @param postalCodeSnapshot postalCodeSnapshot
     * @param primaryAddress primaryAddress
     * @param validFrom validFrom
     * @param validTo validTo
     * @param createdAt createdAt
     * @param updatedAt updatedAt
     */
    public record EmployeeAddress(
            String id,
        String employeeId,
        AddressType addressType,
        String localityId,
        String streetLine1,
        String streetLine2,
        String postalCodeSnapshot,
        boolean primaryAddress,
        Instant validFrom,
        Instant validTo,
        Instant createdAt,
        Instant updatedAt
    ) {

        public EmployeeAddress {
        id = requireText(id, "Employee address ID is required.");
        employeeId = requireText(employeeId, "Employee address employee ID is required.");
        localityId = requireText(localityId, "Employee address locality ID is required.");
        if (addressType == null) {
            throw new InvalidOrganizationValueException("Employee address type is required.");
        }
        requireEffectivePeriod(validFrom, validTo, "Employee address");
        streetLine1 = normalize(streetLine1);
        streetLine2 = normalize(streetLine2);
        postalCodeSnapshot = normalize(postalCodeSnapshot);
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
