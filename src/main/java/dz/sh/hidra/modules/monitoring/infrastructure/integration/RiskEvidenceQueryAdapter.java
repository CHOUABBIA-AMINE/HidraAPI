/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : RiskEvidenceQueryAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-08
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : monitoring
 * @Package     : dz.sh.hidra.modules.monitoring.infrastructure.integration
 *
 * @Description : Enforces governed Risk owner boundaries.
 *
 */
package dz.sh.hidra.modules.monitoring.infrastructure.integration;

import dz.sh.hidra.modules.risk.application.contract.evidence.RiskOwnedEvidenceLookup;
import dz.sh.hidra.modules.monitoring.infrastructure.persistence.repository.MonitoringEvaluationJpaRepository;
import dz.sh.hidra.modules.monitoring.infrastructure.persistence.repository.RiskSignalJpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;
import java.util.Set;

@Component
public class RiskEvidenceQueryAdapter implements RiskOwnedEvidenceLookup {
    private final MonitoringEvaluationJpaRepository repository0;
    private final RiskSignalJpaRepository repository1;
    public RiskEvidenceQueryAdapter(MonitoringEvaluationJpaRepository repository0, RiskSignalJpaRepository repository1) {
        this.repository0 = java.util.Objects.requireNonNull(repository0);
        this.repository1 = java.util.Objects.requireNonNull(repository1);
    }
    @Override public String module() { return "monitoring"; }
    @Override public Set<String> evidenceTypes() { return Set.of("MonitoringEvaluation", "RiskSignal"); }
    @Override @Transactional(readOnly = true)
    public Optional<Evidence> resolve(String type, String id) {
        if (type == null || id == null || id.isBlank()) return Optional.empty();
        return switch (type) {
            case "MonitoringEvaluation" -> repository0.findById(id).map(e -> new Evidence(e.id(), null, null, e.createdAt(), null));
            case "RiskSignal" -> repository1.findById(id).map(e -> new Evidence(e.id(), null, null, e.raisedAt(), null));
            default -> Optional.empty();
        };
    }
}
