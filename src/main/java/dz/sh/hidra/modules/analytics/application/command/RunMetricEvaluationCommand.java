/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : RunMetricEvaluationCommand
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Application
 * @Module      : analytics
 * @Package     : dz.sh.hidra.modules.analytics.application.command
 *
 * @Description : Command to run metric evaluation.
 *
 */
package dz.sh.hidra.modules.analytics.application.command;

import java.time.Instant;

/**
 * Command to run metric evaluation.
 */
public record RunMetricEvaluationCommand(
        String metricDefinitionVersionId,
        Instant periodStart,
        Instant periodEnd,
        String scopeType,
        String scopeId,
        String correlationId
) {
}
