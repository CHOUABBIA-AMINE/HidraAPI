/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : OrganizationUnitType
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization.domain.model
 *
 * @Description : Catalog of organization unit types.
 *
 */
package dz.sh.hidra.modules.organization.domain.model;

import dz.sh.hidra.modules.organization.domain.value.*;
import java.time.Instant;

    /**
     * Catalog of organization unit types.
     *
         * @param id id
     * @param code code
     * @param kind kind
     * @param description description
     * @param active active
     * @param createdAt createdAt
     * @param updatedAt updatedAt
     */
    public record OrganizationUnitType(
            String id,
        String code,
        OrganizationUnitKind kind,
        String description,
        boolean active,
        Instant createdAt,
        Instant updatedAt
    ) {

        public OrganizationUnitType {
        id = normalize(id);
        code = normalize(code);
        description = normalize(description);
        }

        private static String normalize(String value) {
            if (value == null || value.isBlank()) {
                return null;
            }
            return value.trim();
        }
    }
