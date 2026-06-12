/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : LeakDetectionDashboardProjection
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Infrastructure
 * @Module      : leakdetection
 * @Package     : dz.sh.hidra.modules.leakdetection.infrastructure.projection
 *
 * @Description : Leak detection dashboard projection.
 *
 */
package dz.sh.hidra.modules.leakdetection.infrastructure.projection;

import dz.sh.hidra.modules.leakdetection.domain.value.LeakCandidateStatus;
import dz.sh.hidra.modules.leakdetection.domain.value.LeakSeverityLevel;

import java.math.BigDecimal;
import java.time.Instant;

/**
 * Leak detection dashboard projection.
 */
public record LeakDetectionDashboardProjection(
        String candidateId,
        String candidateNumber,
        String topologyAssetType,
        String topologyAssetId,
        String topologyAssetCode,
        BigDecimal confidenceScore,
        LeakSeverityLevel severityLevel,
        LeakCandidateStatus status,
        Instant suspectedAt
) {
}
