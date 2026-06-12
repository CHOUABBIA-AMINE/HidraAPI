/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : RiskExposure
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : risk
 * @Package     : dz.sh.hidra.modules.risk.domain.model
 *
 * @Description : Exposed object and period for a risk.
 *
 */
package dz.sh.hidra.modules.risk.domain.model;

import java.math.BigDecimal;
import java.time.Instant;

    /**
     * Exposed object and period for a risk.
     *
         * @param id id
     * @param riskAssessmentId riskAssessmentId
     * @param exposureTypeId exposureTypeId
     * @param exposedObjectType exposedObjectType
     * @param exposedObjectId exposedObjectId
     * @param exposedObjectCodeSnapshot exposedObjectCodeSnapshot
     * @param exposedObjectLabelSnapshot exposedObjectLabelSnapshot
     * @param exposureStart exposureStart
     * @param exposureEnd exposureEnd
     * @param exposureMagnitude exposureMagnitude
     * @param exposureUnitId exposureUnitId
     * @param populationExposure populationExposure
     * @param environmentalSensitivityId environmentalSensitivityId
     * @param productionCriticalityId productionCriticalityId
     * @param createdAt createdAt
     * @param updatedAt updatedAt
     */
    public record RiskExposure(
            String id,
        String riskAssessmentId,
        String exposureTypeId,
        String exposedObjectType,
        String exposedObjectId,
        String exposedObjectCodeSnapshot,
        String exposedObjectLabelSnapshot,
        Instant exposureStart,
        Instant exposureEnd,
        BigDecimal exposureMagnitude,
        String exposureUnitId,
        BigDecimal populationExposure,
        String environmentalSensitivityId,
        String productionCriticalityId,
        Instant createdAt,
        Instant updatedAt
    ) {

        public RiskExposure {
        id = normalize(id);
        riskAssessmentId = normalize(riskAssessmentId);
        exposureTypeId = normalize(exposureTypeId);
        exposedObjectType = normalize(exposedObjectType);
        exposedObjectId = normalize(exposedObjectId);
        exposedObjectCodeSnapshot = normalize(exposedObjectCodeSnapshot);
        exposedObjectLabelSnapshot = normalize(exposedObjectLabelSnapshot);
        exposureUnitId = normalize(exposureUnitId);
        environmentalSensitivityId = normalize(environmentalSensitivityId);
        productionCriticalityId = normalize(productionCriticalityId);
        }

        private static String normalize(String value) {
            if (value == null || value.isBlank()) {
                return null;
            }
            return value.trim();
        }
    }
