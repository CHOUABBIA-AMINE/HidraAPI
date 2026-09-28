/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : RiskThreat
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-28
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : risk
 * @Package     : dz.sh.hidra.modules.risk.domain.model
 *
 * @Description : Threat source or hazard mechanism.
 *
 */
package dz.sh.hidra.modules.risk.domain.model;

import dz.sh.hidra.modules.risk.domain.exception.InvalidRiskValueException;
import java.time.Instant;

    /**
     * Threat source or hazard mechanism.
     *
         * @param id id
     * @param code code
     * @param nameAr nameAr
     * @param nameFr nameFr
     * @param nameEn nameEn
     * @param description description
     * @param threatCategoryId threatCategoryId
     * @param active active
     * @param createdAt createdAt
     * @param updatedAt updatedAt
     */
    public record RiskThreat(
            String id,
        String code,
        String nameAr,
        String nameFr,
        String nameEn,
        String description,
        String threatCategoryId,
        boolean active,
        Instant createdAt,
        Instant updatedAt
    ) {

        public RiskThreat {
        // HRA-051 required: id
        if (id == null || id.isBlank()) {
            throw new InvalidRiskValueException("RiskThreat id must not be blank.");
        }
        // HRA-051 required: code
        if (code == null || code.isBlank()) {
            throw new InvalidRiskValueException("RiskThreat code must not be blank.");
        }
        // HRA-051 required: threatCategoryId
        if (threatCategoryId == null || threatCategoryId.isBlank()) {
            throw new InvalidRiskValueException("RiskThreat threat category id must not be blank.");
        }

        id = normalize(id);
        code = normalize(code);
        nameAr = normalize(nameAr);
        nameFr = normalize(nameFr);
        nameEn = normalize(nameEn);
        description = normalize(description);
        threatCategoryId = normalize(threatCategoryId);
        }

        private static String normalize(String value) {
            if (value == null || value.isBlank()) {
                return null;
            }
            return value.trim();
        }
    }
