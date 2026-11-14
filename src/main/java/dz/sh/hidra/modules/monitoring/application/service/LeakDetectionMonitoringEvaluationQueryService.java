/**
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 * @Name        : LeakDetectionMonitoringEvaluationQueryService
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-14
 * @Type        : Class
 * @Layer       : Application
 * @Module      : monitoring
 * @Package     : dz.sh.hidra.modules.monitoring.application.service
 * @Description : T13 exact owner contract and fail-closed source/asset authority.
 */
package dz.sh.hidra.modules.monitoring.application.service;

import dz.sh.hidra.modules.monitoring.application.contract.leakdetection.LeakDetectionMonitoringEvaluationContract;
import dz.sh.hidra.modules.monitoring.application.contract.alarm.AlarmMonitoringEvaluationContract;
import org.springframework.stereotype.Service;
import java.util.Objects;

/** Reuses the existing independently scoped T11 owner read, without duplicating authorization. */
@Service
public class LeakDetectionMonitoringEvaluationQueryService implements LeakDetectionMonitoringEvaluationContract {
    private final AlarmMonitoringEvaluationContract source;
    public LeakDetectionMonitoringEvaluationQueryService(AlarmMonitoringEvaluationContract source) {
        this.source = Objects.requireNonNull(source);
    }
    @Override
    public Evaluation authorized(String id) {
        var e = source.authorized(id);
        return new Evaluation(e.evaluationId(),e.ruleId(),e.ruleRevision(),e.companyPartyId(),
                e.assetType(),e.assetId(),e.unitId(),e.outcome(),e.difference(),
                e.targetId(),e.approvalId(),e.trustedReadingId(),e.evaluatedAt());
    }
}
