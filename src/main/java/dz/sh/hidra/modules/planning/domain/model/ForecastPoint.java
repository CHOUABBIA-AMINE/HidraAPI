/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ForecastPoint
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-28
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : planning
 * @Package     : dz.sh.hidra.modules.planning.domain.model
 *
 * @Description : One time-sliced forecast value.
 *
 */
package dz.sh.hidra.modules.planning.domain.model;

import dz.sh.hidra.modules.planning.domain.exception.InvalidPlanningValueException;
import java.math.BigDecimal;
import java.time.Instant;

    /**
     * One time-sliced forecast value.
     *
         * @param id id
     * @param forecastSeriesId forecastSeriesId
     * @param forecastAt forecastAt
     * @param validFrom validFrom
     * @param validTo validTo
     * @param value value
     * @param unitId unitId
     * @param confidenceLevel confidenceLevel
     * @param createdAt createdAt
     */
    public record ForecastPoint(
            String id,
        String forecastSeriesId,
        Instant forecastAt,
        Instant validFrom,
        Instant validTo,
        BigDecimal value,
        String unitId,
        BigDecimal confidenceLevel,
        Instant createdAt
    ) {

        public ForecastPoint {
        // HRA-051 required: id
        if (id == null || id.isBlank()) {
            throw new InvalidPlanningValueException("ForecastPoint id must not be blank.");
        }
        // HRA-051 required: forecastSeriesId
        if (forecastSeriesId == null || forecastSeriesId.isBlank()) {
            throw new InvalidPlanningValueException("ForecastPoint forecast series id must not be blank.");
        }
        // HRA-051 required: forecastAt
        if (forecastAt == null) {
            throw new InvalidPlanningValueException("ForecastPoint forecast at must not be null.");
        }
        // HRA-051 required: validFrom
        if (validFrom == null) {
            throw new InvalidPlanningValueException("ForecastPoint valid from must not be null.");
        }
        // HRA-051 required: validTo
        if (validTo == null) {
            throw new InvalidPlanningValueException("ForecastPoint valid to must not be null.");
        }
        // HRA-051 required: value
        if (value == null) {
            throw new InvalidPlanningValueException("ForecastPoint value must not be null.");
        }
        // HRA-051 required: unitId
        if (unitId == null || unitId.isBlank()) {
            throw new InvalidPlanningValueException("ForecastPoint unit id must not be blank.");
        }
        // HRA-051 order: validFrom <= validTo
        if (validFrom != null && validTo != null && validTo.isBefore(validFrom)) {
            throw new InvalidPlanningValueException("ForecastPoint valid to must not be before valid from.");
        }

        id = normalize(id);
        forecastSeriesId = normalize(forecastSeriesId);
        unitId = normalize(unitId);
        }

        private static String normalize(String value) {
            if (value == null || value.isBlank()) {
                return null;
            }
            return value.trim();
        }
    }
