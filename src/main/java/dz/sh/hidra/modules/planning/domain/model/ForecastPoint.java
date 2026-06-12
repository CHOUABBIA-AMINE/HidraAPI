/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ForecastPoint
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
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
