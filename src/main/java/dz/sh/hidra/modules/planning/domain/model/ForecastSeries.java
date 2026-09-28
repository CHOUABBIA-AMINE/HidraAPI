/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ForecastSeries
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-28
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : planning
 * @Package     : dz.sh.hidra.modules.planning.domain.model
 *
 * @Description : Forecast input series used during plan preparation.
 *
 */
package dz.sh.hidra.modules.planning.domain.model;

import dz.sh.hidra.modules.planning.domain.exception.InvalidPlanningValueException;
import dz.sh.hidra.modules.planning.domain.value.*;
import java.time.Instant;

    /**
     * Forecast input series used during plan preparation.
     *
         * @param id id
     * @param periodId periodId
     * @param code code
     * @param forecastTypeId forecastTypeId
     * @param topologyAssetType topologyAssetType
     * @param topologyAssetId topologyAssetId
     * @param productTypeId productTypeId
     * @param sourceModule sourceModule
     * @param sourceReferenceId sourceReferenceId
     * @param status status
     * @param createdAt createdAt
     * @param updatedAt updatedAt
     */
    public record ForecastSeries(
            String id,
        String periodId,
        String code,
        String forecastTypeId,
        String topologyAssetType,
        String topologyAssetId,
        String productTypeId,
        String sourceModule,
        String sourceReferenceId,
        ForecastSeriesStatus status,
        Instant createdAt,
        Instant updatedAt
    ) {

        public ForecastSeries {
        // HRA-051 required: id
        if (id == null || id.isBlank()) {
            throw new InvalidPlanningValueException("ForecastSeries id must not be blank.");
        }
        // HRA-051 required: periodId
        if (periodId == null || periodId.isBlank()) {
            throw new InvalidPlanningValueException("ForecastSeries period id must not be blank.");
        }
        // HRA-051 required: code
        if (code == null || code.isBlank()) {
            throw new InvalidPlanningValueException("ForecastSeries code must not be blank.");
        }
        // HRA-051 required: forecastTypeId
        if (forecastTypeId == null || forecastTypeId.isBlank()) {
            throw new InvalidPlanningValueException("ForecastSeries forecast type id must not be blank.");
        }
        // HRA-051 required: status
        if (status == null) {
            throw new InvalidPlanningValueException("ForecastSeries status must not be null.");
        }

        id = normalize(id);
        periodId = normalize(periodId);
        code = normalize(code);
        forecastTypeId = normalize(forecastTypeId);
        topologyAssetType = normalize(topologyAssetType);
        topologyAssetId = normalize(topologyAssetId);
        productTypeId = normalize(productTypeId);
        sourceModule = normalize(sourceModule);
        sourceReferenceId = normalize(sourceReferenceId);
        }

        private static String normalize(String value) {
            if (value == null || value.isBlank()) {
                return null;
            }
            return value.trim();
        }
    }
