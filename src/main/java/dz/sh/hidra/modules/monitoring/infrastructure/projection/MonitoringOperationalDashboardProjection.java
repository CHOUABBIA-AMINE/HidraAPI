/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : MonitoringOperationalDashboardProjection
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Infrastructure
 * @Module      : monitoring
 * @Package     : dz.sh.hidra.modules.monitoring.infrastructure.projection
 *
 * @Description : Operational monitoring dashboard projection.
 *
 */
package dz.sh.hidra.modules.monitoring.infrastructure.projection;

import dz.sh.hidra.modules.monitoring.domain.value.DeviationSeverity;
import dz.sh.hidra.modules.monitoring.domain.value.OperationalStateValue;

import java.time.Instant;

/**
 * Operational monitoring dashboard projection.
 */
public record MonitoringOperationalDashboardProjection(
        String topologyAssetType,
        String topologyAssetId,
        String topologyAssetCode,
        OperationalStateValue stateValue,
        DeviationSeverity highestSeverity,
        int openDeviationCount,
        int alertCandidateCount,
        Instant updatedAt
) {
}
