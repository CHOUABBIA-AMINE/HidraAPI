/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AdministrativeState
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-28
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization.domain.model
 *
 * @Description : Algerian state/wilaya reference.
 *
 */
package dz.sh.hidra.modules.organization.domain.model;

import dz.sh.hidra.modules.organization.domain.exception.InvalidOrganizationValueException;
import dz.sh.hidra.modules.organization.domain.value.OrganizationCode;
import java.time.Instant;

    /**
     * Algerian state/wilaya reference.
     *
         * @param id id
     * @param code code
     * @param nameAr nameAr
     * @param nameFr nameFr
     * @param nameEn nameEn
     * @param active active
     * @param createdAt createdAt
     * @param updatedAt updatedAt
     */
    public record AdministrativeState(
            String id,
        String code,
        String nameAr,
        String nameFr,
        String nameEn,
        boolean active,
        Instant createdAt,
        Instant updatedAt
    ) {

        public AdministrativeState {
        id = requireText(id, "Administrative state ID is required.");
        code = OrganizationCode.of(code).value();
        nameAr = normalize(nameAr);
        nameFr = normalize(nameFr);
        nameEn = normalize(nameEn);
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
