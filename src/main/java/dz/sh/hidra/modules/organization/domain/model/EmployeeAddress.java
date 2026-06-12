/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : EmployeeAddress
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
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
        id = normalize(id);
        employeeId = normalize(employeeId);
        localityId = normalize(localityId);
        streetLine1 = normalize(streetLine1);
        streetLine2 = normalize(streetLine2);
        postalCodeSnapshot = normalize(postalCodeSnapshot);
        }

        private static String normalize(String value) {
            if (value == null || value.isBlank()) {
                return null;
            }
            return value.trim();
        }
    }
