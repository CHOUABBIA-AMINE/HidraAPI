/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : RiskMitigationMeasure
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-28
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : risk
 * @Package     : dz.sh.hidra.modules.risk.domain.model
 *
 * @Description : Reusable mitigation/control pattern.
 *
 */
package dz.sh.hidra.modules.risk.domain.model;

import dz.sh.hidra.modules.risk.domain.exception.InvalidRiskValueException;
import java.math.BigDecimal;
import java.time.Instant;

    /**
     * Reusable mitigation/control pattern.
     *
         * @param id id
     * @param code code
     * @param nameAr nameAr
     * @param nameFr nameFr
     * @param nameEn nameEn
     * @param description description
     * @param mitigationTypeId mitigationTypeId
     * @param applicableThreatTypeId applicableThreatTypeId
     * @param applicableAssetTypeId applicableAssetTypeId
     * @param expectedEffectOnLikelihood expectedEffectOnLikelihood
     * @param expectedEffectOnConsequence expectedEffectOnConsequence
     * @param active active
     * @param createdAt createdAt
     * @param updatedAt updatedAt
     */
    public record RiskMitigationMeasure(
            String id,
        String code,
        String nameAr,
        String nameFr,
        String nameEn,
        String description,
        String mitigationTypeId,
        String applicableThreatTypeId,
        String applicableAssetTypeId,
        BigDecimal expectedEffectOnLikelihood,
        BigDecimal expectedEffectOnConsequence,
        boolean active,
        Instant createdAt,
        Instant updatedAt
    ) {

        public RiskMitigationMeasure {
        // HRA-051 required: id
        if (id == null || id.isBlank()) {
            throw new InvalidRiskValueException("RiskMitigationMeasure id must not be blank.");
        }
        // HRA-051 required: code
        if (code == null || code.isBlank()) {
            throw new InvalidRiskValueException("RiskMitigationMeasure code must not be blank.");
        }
        // HRA-051 required: mitigationTypeId
        if (mitigationTypeId == null || mitigationTypeId.isBlank()) {
            throw new InvalidRiskValueException("RiskMitigationMeasure mitigation type id must not be blank.");
        }

        id = normalize(id);
        code = normalize(code);
        nameAr = normalize(nameAr);
        nameFr = normalize(nameFr);
        nameEn = normalize(nameEn);
        description = normalize(description);
        mitigationTypeId = normalize(mitigationTypeId);
        applicableThreatTypeId = normalize(applicableThreatTypeId);
        applicableAssetTypeId = normalize(applicableAssetTypeId);
        }

        private static String normalize(String value) {
            if (value == null || value.isBlank()) {
                return null;
            }
            return value.trim();
        }
    }
