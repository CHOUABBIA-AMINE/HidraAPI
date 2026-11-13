/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : MonitoringDeviationEvidenceAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-13
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : monitoring
 * @Package     : dz.sh.hidra.modules.monitoring.infrastructure.integration
 *
 * @Description : Maps authorized owner evidence into Monitoring scalar inputs.
 *
 */
package dz.sh.hidra.modules.monitoring.infrastructure.integration;

import dz.sh.hidra.modules.monitoring.application.port.out.AuthoritativeDeviationEvidencePort;
import dz.sh.hidra.modules.monitoring.domain.model.AuthoritativeDeviationEvaluation.Observation;
import dz.sh.hidra.modules.monitoring.domain.model.AuthoritativeDeviationEvaluation.Target;
import dz.sh.hidra.modules.monitoring.domain.model.MonitoringRuleRevision;
import dz.sh.hidra.modules.planning.application.contract.monitoring.MonitoringPlanTargetReferenceContract;
import dz.sh.hidra.modules.telemetry.application.contract.monitoring.MonitoringTrustedReadingReferenceContract;
import java.time.Instant;
import java.util.Optional;
import org.springframework.stereotype.Component;

@Component
public class MonitoringDeviationEvidenceAdapter implements AuthoritativeDeviationEvidencePort {
    private final MonitoringPlanTargetReferenceContract planning;
    private final MonitoringTrustedReadingReferenceContract telemetry;

    public MonitoringDeviationEvidenceAdapter(MonitoringPlanTargetReferenceContract planning,
            MonitoringTrustedReadingReferenceContract telemetry) {
        this.planning = planning;
        this.telemetry = telemetry;
    }

    @Override
    public Optional<Expected> target(String id, MonitoringRuleRevision rule, Instant at) {
        return planning.forEvaluation(id, rule.companyPartyId(), rule.assetType(), rule.assetId(), at)
                .map(t -> new Expected(new Target(t.id(), t.revisionId(), t.approvalId(), t.approvedAt(), t.value()),
                        t.pointId(), t.unitId()));
    }

    @Override
    public Optional<Actual> observation(String id, MonitoringRuleRevision rule) {
        return telemetry.forEvaluation(id, rule.telemetryPointId(), rule.assetType(), rule.assetId())
                .map(r -> new Actual(new Observation(r.id(), r.rawId(), r.assessmentId(), r.bindingId(),
                        r.qualityCodeId(), r.trustLevel(), r.observedAt(), r.trustedAt(), r.value()),
                        r.unitId(), r.qualified()));
    }
}
