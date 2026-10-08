/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : MonitoringPlanTargetReferenceQueryService
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-08
 *
 * @Type        : Class
 * @Layer       : Application
 * @Module      : planning
 * @Package     : dz.sh.hidra.modules.planning.application.service
 *
 * @Description : Resolves historical Planning targets through an owned repository port.
 *
 */
package dz.sh.hidra.modules.planning.application.service;

import dz.sh.hidra.modules.planning.application.contract.monitoring.MonitoringPlanTargetReferenceContract;
import dz.sh.hidra.modules.planning.application.port.out.PlanTargetRepositoryPort;
import java.util.Objects;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public final class MonitoringPlanTargetReferenceQueryService implements MonitoringPlanTargetReferenceContract {
    private final PlanTargetRepositoryPort targets;
    public MonitoringPlanTargetReferenceQueryService(PlanTargetRepositoryPort targets) {
        this.targets = Objects.requireNonNull(targets);
    }
    @Override
    public Optional<Target> resolve(String id) {
        if (id == null || id.isBlank()) return Optional.empty();
        String key = id.trim();
        return targets.findById(key).filter(t -> key.equals(t.id())).map(t -> new Target(
                t.id(), t.revisionId(), t.topologyAssetType(), t.topologyAssetId(), t.telemetryPointId(),
                t.status().name(), t.targetValue(), t.targetTextValue(), t.unitId(), t.validFrom(), t.validTo()));
    }
}
