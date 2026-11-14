/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AlarmMonitoringEvaluationContract
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-14
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : monitoring
 * @Package     : dz.sh.hidra.modules.monitoring.application.contract.alarm
 *
 * @Description : Exports authorized scalar Monitoring deviation receipts to Alarm.
 *
 */
package dz.sh.hidra.modules.monitoring.application.contract.alarm;

import java.math.BigDecimal;
import java.time.Instant;

/** Only an authorized caller receives source identifiers or numeric evidence. */
public interface AlarmMonitoringEvaluationContract {
    record Evaluation(
            String evaluationId, String ruleId, long ruleRevision,
            String companyPartyId, String assetType, String assetId, String telemetryPointId,
            String unitId, String outcome, BigDecimal actual, BigDecimal expected,
            BigDecimal difference, String targetId, String approvalId, String trustedReadingId,
            Instant evaluatedAt, Instant recordedAt) { }

    Evaluation authorized(String evaluationId);
}
