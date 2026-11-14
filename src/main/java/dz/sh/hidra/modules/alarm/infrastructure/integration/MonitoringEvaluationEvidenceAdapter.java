/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : MonitoringEvaluationEvidenceAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-14
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : alarm
 * @Package     : dz.sh.hidra.modules.alarm.infrastructure.integration
 *
 * @Description : Maps authorized Monitoring scalar projections into Alarm outbound port.
 *
 */
package dz.sh.hidra.modules.alarm.infrastructure.integration;

import dz.sh.hidra.modules.alarm.application.port.out.MonitoringEvaluationEvidencePort;
import dz.sh.hidra.modules.monitoring.application.contract.alarm.AlarmMonitoringEvaluationContract;
import java.util.Objects;
import org.springframework.stereotype.Component;

@Component
public final class MonitoringEvaluationEvidenceAdapter implements MonitoringEvaluationEvidencePort {
    private final AlarmMonitoringEvaluationContract monitoring;

    public MonitoringEvaluationEvidenceAdapter(AlarmMonitoringEvaluationContract monitoring) {
        this.monitoring = Objects.requireNonNull(monitoring);
    }

    @Override
    public Snapshot authorized(String evaluationId) {
        var source = monitoring.authorized(evaluationId);
        return new Snapshot(source.evaluationId(), source.ruleId(), source.ruleRevision(),
                source.companyPartyId(), source.assetType(), source.assetId(), source.telemetryPointId(),
                source.unitId(), source.outcome(), source.actual(), source.expected(), source.difference(),
                source.targetId(), source.approvalId(), source.trustedReadingId(),
                source.evaluatedAt(), source.recordedAt());
    }
}
