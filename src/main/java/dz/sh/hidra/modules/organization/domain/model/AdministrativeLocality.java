/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AdministrativeLocality
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
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
        id = normalize(id);
        districtId = normalize(districtId);
        code = normalize(code);
        nameAr = normalize(nameAr);
        nameFr = normalize(nameFr);
        nameEn = normalize(nameEn);
        postalCode = normalize(postalCode);
        }

        private static String normalize(String value) {
            if (value == null || value.isBlank()) {
                return null;
            }
            return value.trim();
        }
    }
