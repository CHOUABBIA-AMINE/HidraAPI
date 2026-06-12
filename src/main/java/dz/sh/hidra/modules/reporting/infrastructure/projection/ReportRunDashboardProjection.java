/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ReportRunDashboardProjection
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Infrastructure
 * @Module      : reporting
 * @Package     : dz.sh.hidra.modules.reporting.infrastructure.projection
 *
 * @Description : Reporting run dashboard projection.
 *
 */
package dz.sh.hidra.modules.reporting.infrastructure.projection;

import dz.sh.hidra.modules.reporting.domain.value.ReportRunStatus;

import java.time.Instant;

/**
 * Reporting run dashboard projection.
 */
public record ReportRunDashboardProjection(
        String reportRunId,
        String reportDefinitionId,
        String reportRequestId,
        ReportRunStatus status,
        Long outputCount,
        Long executionDurationMs,
        Instant queuedAt,
        Instant completedAt
) {
}
