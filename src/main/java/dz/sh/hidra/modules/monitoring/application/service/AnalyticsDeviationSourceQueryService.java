/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AnalyticsDeviationSourceQueryService
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-17
 *
 * @Type        : Class
 * @Layer       : Application
 * @Module      : monitoring
 * @Package     : dz.sh.hidra.modules.monitoring.application.service
 *
 * @Description : Reuses Monitoring owner authorization while exporting frozen evidence only.
 *
 */
package dz.sh.hidra.modules.monitoring.application.service;

import dz.sh.hidra.modules.monitoring.application.contract.analytics.AnalyticsDeviationSourceContract;
import dz.sh.hidra.modules.monitoring.application.contract.alarm.AlarmMonitoringEvaluationContract;
import dz.sh.hidra.modules.monitoring.application.port.in.AuthoritativeDeviationUseCase;
import java.util.Objects;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AnalyticsDeviationSourceQueryService implements AnalyticsDeviationSourceContract {
    private final AlarmMonitoringEvaluationContract authorized;
    private final AuthoritativeDeviationUseCase receipts;
    public AnalyticsDeviationSourceQueryService(AlarmMonitoringEvaluationContract authorized,
            AuthoritativeDeviationUseCase receipts) {
        this.authorized=Objects.requireNonNull(authorized);
        this.receipts=Objects.requireNonNull(receipts);
    }
    @Override @Transactional
    public Evaluation authorized(String evaluationId) {
        var projection=authorized.authorized(evaluationId);
        var stored=receipts.find(projection.ruleId(),evaluationId);
        var target=stored.target();
        var observation=stored.observation();
        return new Evaluation(projection.evaluationId(),projection.ruleId(),
                projection.ruleRevision(),projection.companyPartyId(),projection.assetType(),
                projection.assetId(),projection.telemetryPointId(),projection.unitId(),
                projection.outcome(),projection.actual(),projection.expected(),
                projection.targetId(),target==null?null:target.revisionId(),
                projection.approvalId(),projection.trustedReadingId(),
                observation==null?null:observation.rawId(),
                observation==null?null:observation.assessmentId(),
                observation==null?null:observation.bindingId(),
                projection.evaluatedAt(),projection.recordedAt());
    }
}
