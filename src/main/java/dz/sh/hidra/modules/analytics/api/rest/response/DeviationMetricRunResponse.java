/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : DeviationMetricRunResponse
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-17
 *
 * @Type        : Record
 * @Layer       : API
 * @Module      : analytics
 * @Package     : dz.sh.hidra.modules.analytics.api.rest.response
 *
 * @Description : Typed metric result and frozen source-lineage response.
 *
 */
package dz.sh.hidra.modules.analytics.api.rest.response;

import dz.sh.hidra.modules.analytics.application.port.in.DeviationMetricRunUseCase;
import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;
import java.time.Instant;

@Schema(name="DeviationMetricRunResponse")
public record DeviationMetricRunResponse(
        String runId,String status,String errorCode,
        String companyId,String facilityId,String metricCode,
        String definitionVersionId,String methodVersion,String unitId,
        BigDecimal value,Instant startedAt,Instant completedAt,
        Source source) {
    public record Source(String evaluationId,String ruleId,long ruleRevision,
            String telemetryPointId,String outcome,String targetId,String targetRevisionId,
            String approvalId,String trustedReadingId,String rawReadingId,
            String assessmentId,String bindingId,Instant evaluatedAt,Instant recordedAt) { }

    public static DeviationMetricRunResponse from(DeviationMetricRunUseCase.Result run) {
        var s=run.source();
        return new DeviationMetricRunResponse(run.runId(),run.status(),run.errorCode(),
                run.companyId(),run.facilityId(),run.metricCode(),run.definitionVersionId(),
                run.methodVersion(),run.unitId(),run.value(),run.startedAt(),run.completedAt(),
                new Source(s.evaluationId(),s.ruleId(),s.ruleRevision(),s.telemetryPointId(),
                        s.outcome(),s.targetId(),s.targetRevisionId(),s.approvalId(),
                        s.trustedReadingId(),s.rawReadingId(),s.assessmentId(),s.bindingId(),
                        s.evaluatedAt(),s.recordedAt()));
    }
}
