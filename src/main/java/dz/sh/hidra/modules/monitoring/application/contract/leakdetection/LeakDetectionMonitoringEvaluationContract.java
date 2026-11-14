/**
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 * @Name        : LeakDetectionMonitoringEvaluationContract
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-14
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : monitoring
 * @Package     : dz.sh.hidra.modules.monitoring.application.contract.leakdetection
 * @Description : T13 exact owner contract and fail-closed source/asset authority.
 */
package dz.sh.hidra.modules.monitoring.application.contract.leakdetection;

import java.math.BigDecimal;
import java.time.Instant;

/** LeakDetection receives only already-authorized immutable Monitoring evidence. */
public interface LeakDetectionMonitoringEvaluationContract {
    record Evaluation(String evaluationId, String ruleId, long revision, String company,
            String assetType, String assetId, String unitId, String outcome,
            BigDecimal difference, String targetId, String approvalId, String trustedReadingId,
            Instant evaluatedAt) { }
    Evaluation authorized(String evaluationId);
}
