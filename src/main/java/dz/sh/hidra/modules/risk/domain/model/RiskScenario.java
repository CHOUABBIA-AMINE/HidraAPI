/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : RiskScenario
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : risk
 * @Package     : dz.sh.hidra.modules.risk.domain.model
 *
 * @Description : Risk event being assessed.
 *
 */
package dz.sh.hidra.modules.risk.domain.model;

import java.time.Instant;

    /**
     * Risk event being assessed.
     *
         * @param id id
     * @param code code
     * @param nameAr nameAr
     * @param nameFr nameFr
     * @param nameEn nameEn
     * @param description description
     * @param scenarioTypeId scenarioTypeId
     * @param threatId threatId
     * @param primaryConsequenceCategoryId primaryConsequenceCategoryId
     * @param active active
     * @param createdAt createdAt
     * @param updatedAt updatedAt
     */
    public record RiskScenario(
            String id,
        String code,
        String nameAr,
        String nameFr,
        String nameEn,
        String description,
        String scenarioTypeId,
        String threatId,
        String primaryConsequenceCategoryId,
        boolean active,
        Instant createdAt,
        Instant updatedAt
    ) {

        public RiskScenario {
        id = normalize(id);
        code = normalize(code);
        nameAr = normalize(nameAr);
        nameFr = normalize(nameFr);
        nameEn = normalize(nameEn);
        description = normalize(description);
        scenarioTypeId = normalize(scenarioTypeId);
        threatId = normalize(threatId);
        primaryConsequenceCategoryId = normalize(primaryConsequenceCategoryId);
        }

        private static String normalize(String value) {
            if (value == null || value.isBlank()) {
                return null;
            }
            return value.trim();
        }
    }
