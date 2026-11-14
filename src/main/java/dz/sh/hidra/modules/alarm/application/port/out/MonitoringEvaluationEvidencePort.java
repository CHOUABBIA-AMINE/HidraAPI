/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : MonitoringEvaluationEvidencePort
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-14
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : alarm
 * @Package     : dz.sh.hidra.modules.alarm.application.port.out
 *
 * @Description : Obtains protected Monitoring evidence without importing Monitoring internals.
 *
 */
package dz.sh.hidra.modules.alarm.application.port.out;

import java.math.BigDecimal;
import java.time.Instant;

public interface MonitoringEvaluationEvidencePort {
    record Snapshot(String evaluationId, String ruleId, long ruleRevision,
            String companyPartyId, String assetType, String assetId, String telemetryPointId,
            String unitId, String outcome, BigDecimal actual, BigDecimal expected,
            BigDecimal difference, String targetId, String approvalId, String trustedReadingId,
            Instant evaluatedAt, Instant recordedAt) { }

    /** Enforces current Monitoring READ before returning protected receipt contents. */
    Snapshot authorized(String evaluationId);
}
