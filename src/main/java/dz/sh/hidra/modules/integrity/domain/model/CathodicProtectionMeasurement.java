/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : CathodicProtectionMeasurement
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : integrity
 * @Package     : dz.sh.hidra.modules.integrity.domain.model
 *
 * @Description : CP measurement.
 *
 */
package dz.sh.hidra.modules.integrity.domain.model;

import java.math.BigDecimal;
import java.time.Instant;

    /**
     * CP measurement.
     *
         * @param id id
     * @param surveyId surveyId
     * @param kilometerPoint kilometerPoint
     * @param pipeToSoilPotential pipeToSoilPotential
     * @param potentialUnitId potentialUnitId
     * @param currentDensity currentDensity
     * @param currentUnitId currentUnitId
     * @param measurementMethodId measurementMethodId
     * @param measuredAt measuredAt
     * @param notes notes
     */
    public record CathodicProtectionMeasurement(
            String id,
        String surveyId,
        BigDecimal kilometerPoint,
        BigDecimal pipeToSoilPotential,
        String potentialUnitId,
        BigDecimal currentDensity,
        String currentUnitId,
        String measurementMethodId,
        Instant measuredAt,
        String notes
    ) {

        public CathodicProtectionMeasurement {
        id = normalize(id);
        surveyId = normalize(surveyId);
        potentialUnitId = normalize(potentialUnitId);
        currentUnitId = normalize(currentUnitId);
        measurementMethodId = normalize(measurementMethodId);
        notes = normalize(notes);
        }

        private static String normalize(String value) {
            if (value == null || value.isBlank()) {
                return null;
            }
            return value.trim();
        }
    }
