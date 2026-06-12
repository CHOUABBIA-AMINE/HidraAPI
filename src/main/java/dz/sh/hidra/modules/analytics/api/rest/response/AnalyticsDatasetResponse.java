/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AnalyticsDatasetResponse
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : API
 * @Module      : analytics
 * @Package     : dz.sh.hidra.modules.analytics.api.rest.response
 *
 * @Description : REST response for analytics dataset.
 *
 */
package dz.sh.hidra.modules.analytics.api.rest.response;

import dz.sh.hidra.modules.analytics.domain.value.AnalyticsDatasetType;
import dz.sh.hidra.modules.analytics.domain.value.AnalyticsLineageStatus;
import dz.sh.hidra.modules.analytics.domain.value.AnalyticsQualityStatus;
import dz.sh.hidra.modules.analytics.domain.value.AnalyticsRefreshMode;

/**
 * REST response for analytics dataset.
 */
public record AnalyticsDatasetResponse(
        String id,
        String code,
        String nameFr,
        String subjectAreaId,
        AnalyticsDatasetType datasetType,
        AnalyticsRefreshMode refreshMode,
        AnalyticsLineageStatus lineageStatus,
        AnalyticsQualityStatus qualityStatus
) {
}
