/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : DeviationMetricRunUseCase
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-17
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : analytics
 * @Package     : dz.sh.hidra.modules.analytics.application.port.in
 *
 * @Description : Scoped metric execution and immutable result retrieval use case.
 *
 */
package dz.sh.hidra.modules.analytics.application.port.in;

import java.math.BigDecimal;
import java.time.Instant;

public interface DeviationMetricRunUseCase {
    record Source(
            String evaluationId, String ruleId, long ruleRevision,
            String companyId, String facilityId, String telemetryPointId,
            String unitId, String outcome, BigDecimal actual, BigDecimal expected,
            String targetId, String targetRevisionId, String approvalId,
            String trustedReadingId, String rawReadingId, String assessmentId, String bindingId,
            Instant evaluatedAt, Instant recordedAt) { }
    record Result(
            String runId, String status, String errorCode,
            String companyId, String facilityId, String metricCode,
            String definitionVersionId, String methodVersion, String unitId,
            BigDecimal value, Instant startedAt, Instant completedAt,
            Source source, boolean replayed) { }
    Result execute(String companyId, String facilityId, String definitionVersionId, String evaluationId);
    Result read(String companyId, String facilityId, String runId);
}
