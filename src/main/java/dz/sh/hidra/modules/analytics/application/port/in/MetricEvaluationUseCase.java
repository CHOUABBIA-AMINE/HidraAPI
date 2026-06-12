/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : MetricEvaluationUseCase
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : analytics
 * @Package     : dz.sh.hidra.modules.analytics.application.port.in
 *
 * @Description : Use case for metric evaluation.
 *
 */
package dz.sh.hidra.modules.analytics.application.port.in;

import dz.sh.hidra.modules.analytics.application.command.RunMetricEvaluationCommand;
import dz.sh.hidra.modules.analytics.application.dto.MetricEvaluationRunSummaryDto;

/**
 * Use case for metric evaluation.
 */
public interface MetricEvaluationUseCase {

    MetricEvaluationRunSummaryDto runMetricEvaluation(RunMetricEvaluationCommand command);
}
