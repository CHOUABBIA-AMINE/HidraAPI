/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AnalyticsController
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Interface
 * @Layer       : API
 * @Module      : analytics
 * @Package     : dz.sh.hidra.modules.analytics.api.rest.controller
 *
 * @Description : Framework-neutral analytics controller contract.
 *
 */
package dz.sh.hidra.modules.analytics.api.rest.controller;

import dz.sh.hidra.modules.analytics.api.rest.request.CreateAnalyticsDatasetRequest;
import dz.sh.hidra.modules.analytics.api.rest.request.CreateAnalyticsInsightRequest;
import dz.sh.hidra.modules.analytics.api.rest.response.AnalyticsDatasetResponse;
import dz.sh.hidra.modules.analytics.api.rest.response.AnalyticsInsightResponse;

/**
 * Framework-neutral analytics controller contract.
 */
public interface AnalyticsController {

    AnalyticsDatasetResponse createAnalyticsDataset(CreateAnalyticsDatasetRequest request);

    AnalyticsInsightResponse createAnalyticsInsight(CreateAnalyticsInsightRequest request);
}
