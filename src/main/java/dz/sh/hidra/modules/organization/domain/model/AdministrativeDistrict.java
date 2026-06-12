/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AdministrativeDistrict
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization.domain.model
 *
 * @Description : District/daira reference under state.
 *
 */
package dz.sh.hidra.modules.organization.domain.model;

import java.time.Instant;

    /**
     * District/daira reference under state.
     *
         * @param id id
     * @param stateId stateId
     * @param code code
     * @param nameAr nameAr
     * @param nameFr nameFr
     * @param nameEn nameEn
     * @param active active
     * @param createdAt createdAt
     * @param updatedAt updatedAt
     */
    public record AdministrativeDistrict(
            String id,
        String stateId,
        String code,
        String nameAr,
        String nameFr,
        String nameEn,
        boolean active,
        Instant createdAt,
        Instant updatedAt
    ) {

        public AdministrativeDistrict {
        id = normalize(id);
        stateId = normalize(stateId);
        code = normalize(code);
        nameAr = normalize(nameAr);
        nameFr = normalize(nameFr);
        nameEn = normalize(nameEn);
        }

        private static String normalize(String value) {
            if (value == null || value.isBlank()) {
                return null;
            }
            return value.trim();
        }
    }
