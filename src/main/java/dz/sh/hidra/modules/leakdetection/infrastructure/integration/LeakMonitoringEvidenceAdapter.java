/**
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 * @Name        : LeakMonitoringEvidenceAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-14
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : leakdetection
 * @Package     : dz.sh.hidra.modules.leakdetection.infrastructure.integration
 * @Description : T13 exact owner contract and fail-closed source/asset authority.
 */
package dz.sh.hidra.modules.leakdetection.infrastructure.integration;

import dz.sh.hidra.modules.leakdetection.application.port.out.LeakMonitoringEvidencePort;
import dz.sh.hidra.modules.monitoring.application.contract.leakdetection.LeakDetectionMonitoringEvaluationContract;
import java.util.Objects;
import org.springframework.stereotype.Component;

@Component
public class LeakMonitoringEvidenceAdapter implements LeakMonitoringEvidencePort {
    private final LeakDetectionMonitoringEvaluationContract monitoring;
    public LeakMonitoringEvidenceAdapter(LeakDetectionMonitoringEvaluationContract monitoring) {
        this.monitoring=Objects.requireNonNull(monitoring);
    }
    @Override
    public Snapshot authorized(String id) {
        var s=monitoring.authorized(id);
        return new Snapshot(s.evaluationId(),s.ruleId(),s.revision(),s.company(),
            s.assetType(),s.assetId(),s.unitId(),s.outcome(),s.difference(),
            s.targetId(),s.approvalId(),s.trustedReadingId(),s.evaluatedAt());
    }
}
