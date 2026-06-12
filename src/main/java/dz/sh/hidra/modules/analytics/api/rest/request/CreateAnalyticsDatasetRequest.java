/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : CreateAnalyticsDatasetRequest
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : API
 * @Module      : analytics
 * @Package     : dz.sh.hidra.modules.analytics.api.rest.request
 *
 * @Description : REST request to create analytics dataset.
 *
 */
package dz.sh.hidra.modules.analytics.api.rest.request;

import dz.sh.hidra.modules.analytics.domain.value.AnalyticsDatasetType;
import dz.sh.hidra.modules.analytics.domain.value.AnalyticsRefreshMode;

/**
 * REST request to create analytics dataset.
 */
public record CreateAnalyticsDatasetRequest(
        String code,
        String nameAr,
        String nameFr,
        String nameEn,
        String subjectAreaId,
        AnalyticsDatasetType datasetType,
        AnalyticsRefreshMode refreshMode,
        String schemaVersion,
        String createdFrom
) {
}
