/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : RiskRating
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : risk
 * @Package     : dz.sh.hidra.modules.risk.domain.model
 *
 * @Description : Catalog-backed rating value.
 *
 */
package dz.sh.hidra.modules.risk.domain.model;

import java.time.Instant;

    /**
     * Catalog-backed rating value.
     *
         * @param id id
     * @param code code
     * @param nameAr nameAr
     * @param nameFr nameFr
     * @param nameEn nameEn
     * @param description description
     * @param severityOrder severityOrder
     * @param requiresTreatment requiresTreatment
     * @param requiresApproval requiresApproval
     * @param active active
     * @param createdAt createdAt
     * @param updatedAt updatedAt
     */
    public record RiskRating(
            String id,
        String code,
        String nameAr,
        String nameFr,
        String nameEn,
        String description,
        int severityOrder,
        boolean requiresTreatment,
        boolean requiresApproval,
        boolean active,
        Instant createdAt,
        Instant updatedAt
    ) {

        public RiskRating {
        id = normalize(id);
        code = normalize(code);
        nameAr = normalize(nameAr);
        nameFr = normalize(nameFr);
        nameEn = normalize(nameEn);
        description = normalize(description);
        }

        private static String normalize(String value) {
            if (value == null || value.isBlank()) {
                return null;
            }
            return value.trim();
        }
    }
