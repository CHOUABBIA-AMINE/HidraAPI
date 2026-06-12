/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AnalyticsProjectionUseCase
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : analytics
 * @Package     : dz.sh.hidra.modules.analytics.application.port.in
 *
 * @Description : Use case for analytics projections.
 *
 */
package dz.sh.hidra.modules.analytics.application.port.in;

import dz.sh.hidra.modules.analytics.application.command.RunProjectionCommand;
import dz.sh.hidra.modules.analytics.application.dto.AnalyticsProjectionRunSummaryDto;

/**
 * Use case for analytics projections.
 */
public interface AnalyticsProjectionUseCase {

    AnalyticsProjectionRunSummaryDto runProjection(RunProjectionCommand command);
}
