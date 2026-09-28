/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AdministrativeLocality
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-28
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization.domain.model
 *
 * @Description : Locality/commune reference under district.
 *
 */
package dz.sh.hidra.modules.organization.domain.model;

import dz.sh.hidra.modules.organization.domain.exception.InvalidOrganizationValueException;
import dz.sh.hidra.modules.organization.domain.value.OrganizationCode;
import java.time.Instant;

    /**
     * Locality/commune reference under district.
     *
         * @param id id
     * @param districtId districtId
     * @param code code
     * @param nameAr nameAr
     * @param nameFr nameFr
     * @param nameEn nameEn
     * @param postalCode postalCode
     * @param active active
     * @param createdAt createdAt
     * @param updatedAt updatedAt
     */
    public record AdministrativeLocality(
            String id,
        String districtId,
        String code,
        String nameAr,
        String nameFr,
        String nameEn,
        String postalCode,
        boolean active,
        Instant createdAt,
        Instant updatedAt
    ) {

        public AdministrativeLocality {
        // HRA-051 required: code
        if (code == null || code.isBlank()) {
            throw new InvalidOrganizationValueException("AdministrativeLocality code must not be blank.");
        }

        id = requireText(id, "Administrative locality ID is required.");
        districtId = requireText(districtId, "Administrative locality district ID is required.");
        code = OrganizationCode.of(code).value();
        nameAr = normalize(nameAr);
        nameFr = normalize(nameFr);
        nameEn = normalize(nameEn);
        postalCode = normalize(postalCode);
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
