/**
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 * @Name        : ReportingDeviationMetricContract
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-18
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : analytics
 * @Package     : dz.sh.hidra.modules.analytics.application.contract.reporting
 * @Description : Scoped, versioned T25 report delivery.
 */
package dz.sh.hidra.modules.analytics.application.contract.reporting;
import java.math.BigDecimal;
import java.time.Instant;

public interface ReportingDeviationMetricContract {
    record Metric(String runId, String definitionVersionId, String methodVersion, BigDecimal value,
            String unitId, String evaluationId, String ruleId, long ruleRevision, String targetId,
            String targetRevisionId, String approvalId, String trustedReadingId, String rawReadingId,
            String assessmentId, String bindingId, Instant evaluatedAt, Instant recordedAt) {}
    Metric completed(String company, String facility, String runId);
}
