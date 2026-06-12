/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AnalyticsDatasetSummaryDto
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Application
 * @Module      : analytics
 * @Package     : dz.sh.hidra.modules.analytics.application.dto
 *
 * @Description : Analytics dataset summary DTO.
 *
 */
package dz.sh.hidra.modules.analytics.application.dto;

import dz.sh.hidra.modules.analytics.domain.value.AnalyticsDatasetType;
import dz.sh.hidra.modules.analytics.domain.value.AnalyticsLineageStatus;
import dz.sh.hidra.modules.analytics.domain.value.AnalyticsQualityStatus;
import dz.sh.hidra.modules.analytics.domain.value.AnalyticsRefreshMode;

/**
 * Analytics dataset summary DTO.
 */
public record AnalyticsDatasetSummaryDto(
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
