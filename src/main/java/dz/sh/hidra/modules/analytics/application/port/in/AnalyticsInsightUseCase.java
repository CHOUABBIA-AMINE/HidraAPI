/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AnalyticsInsightUseCase
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : analytics
 * @Package     : dz.sh.hidra.modules.analytics.application.port.in
 *
 * @Description : Use case for analytics insights.
 *
 */
package dz.sh.hidra.modules.analytics.application.port.in;

import dz.sh.hidra.modules.analytics.application.command.CreateAnalyticsInsightCommand;
import dz.sh.hidra.modules.analytics.application.dto.AnalyticsInsightSummaryDto;

/**
 * Use case for analytics insights.
 */
public interface AnalyticsInsightUseCase {

    AnalyticsInsightSummaryDto createAnalyticsInsight(CreateAnalyticsInsightCommand command);
}
