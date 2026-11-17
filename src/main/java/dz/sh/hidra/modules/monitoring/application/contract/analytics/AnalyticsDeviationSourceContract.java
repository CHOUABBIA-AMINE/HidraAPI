/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AnalyticsDeviationSourceContract
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-17
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : monitoring
 * @Package     : dz.sh.hidra.modules.monitoring.application.contract.analytics
 *
 * @Description : Only authorized and exact Monitoring receipt scalar export to Analytics.
 *
 */
package dz.sh.hidra.modules.monitoring.application.contract.analytics;

import java.math.BigDecimal;
import java.time.Instant;

public interface AnalyticsDeviationSourceContract {
    record Evaluation(
            String evaluationId,String ruleId,long revision,String companyId,
            String assetType,String assetId,String telemetryPointId,String unitId,
            String outcome,BigDecimal actual,BigDecimal expected,
            String targetId,String targetRevisionId,String approvalId,
            String trustedReadingId,String rawReadingId,String assessmentId,String bindingId,
            Instant evaluatedAt,Instant recordedAt) { }
    Evaluation authorized(String evaluationId);
}
