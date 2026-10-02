/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : RunProjectionCommand
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Application
 * @Module      : analytics
 * @Package     : dz.sh.hidra.modules.analytics.application.command
 *
 * @Description : Command to run an analytics projection.
 *
 */
package dz.sh.hidra.modules.analytics.application.command;

import dz.sh.hidra.modules.analytics.domain.value.AnalyticsRunMode;

import java.time.Instant;

/**
 * Command to run an analytics projection.
 */
public record RunProjectionCommand(
        String projectionDefinitionId,
        AnalyticsRunMode runMode,
        Instant periodStart,
        Instant periodEnd,
        String correlationId
) {
}
