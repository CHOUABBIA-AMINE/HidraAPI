/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : IntegrityDashboardProjection
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Infrastructure
 * @Module      : integrity
 * @Package     : dz.sh.hidra.modules.integrity.infrastructure.projection
 *
 * @Description : Integrity dashboard projection.
 *
 */
package dz.sh.hidra.modules.integrity.infrastructure.projection;

import dz.sh.hidra.modules.integrity.domain.value.DefectStatus;
import dz.sh.hidra.modules.integrity.domain.value.FindingSeverity;

import java.time.Instant;

/**
 * Integrity dashboard projection.
 */
public record IntegrityDashboardProjection(
        String topologyAssetTypeCode,
        String topologyAssetId,
        String topologyAssetCodeSnapshot,
        int openDefectCount,
        FindingSeverity highestSeverity,
        DefectStatus mostCriticalDefectStatus,
        Instant lastAssessmentAt
) {
}
