/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ForecastSeries
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
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
