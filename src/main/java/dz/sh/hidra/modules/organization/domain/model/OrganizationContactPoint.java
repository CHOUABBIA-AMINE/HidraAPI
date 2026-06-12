/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : OrganizationContactPoint
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization.domain.model
 *
 * @Description : Operational contact information.
 *
 */
package dz.sh.hidra.modules.organization.domain.model;

import dz.sh.hidra.modules.organization.domain.value.*;
import java.time.Instant;

    /**
     * Operational contact information.
     *
         * @param id id
     * @param contactPointType contactPointType
     * @param targetType targetType
     * @param targetId targetId
     * @param label label
     * @param value value
     * @param primaryContact primaryContact
     * @param emergencyContact emergencyContact
     * @param active active
     * @param createdAt createdAt
     * @param updatedAt updatedAt
     */
    public record OrganizationContactPoint(
            String id,
        ContactPointType contactPointType,
        String targetType,
        String targetId,
        String label,
        String value,
        boolean primaryContact,
        boolean emergencyContact,
        boolean active,
        Instant createdAt,
        Instant updatedAt
    ) {

        public OrganizationContactPoint {
        id = normalize(id);
        targetType = normalize(targetType);
        targetId = normalize(targetId);
        label = normalize(label);
        value = normalize(value);
        }

        private static String normalize(String value) {
            if (value == null || value.isBlank()) {
                return null;
            }
            return value.trim();
        }
    }
