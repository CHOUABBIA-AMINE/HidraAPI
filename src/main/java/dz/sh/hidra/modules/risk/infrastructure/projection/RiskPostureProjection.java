/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : RiskPostureProjection
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Infrastructure
 * @Module      : risk
 * @Package     : dz.sh.hidra.modules.risk.infrastructure.projection
 *
 * @Description : Risk posture projection.
 *
 */
package dz.sh.hidra.modules.risk.infrastructure.projection;

import java.math.BigDecimal;
import java.time.Instant;

/**
 * Risk posture projection.
 */
public record RiskPostureProjection(
        String scopeType,
        String scopeId,
        String scopeCodeSnapshot,
        int totalRiskCount,
        int criticalRiskCount,
        int highRiskCount,
        BigDecimal maximumRiskScore,
        int openTreatmentCount,
        Instant snapshotDate
) {
}
