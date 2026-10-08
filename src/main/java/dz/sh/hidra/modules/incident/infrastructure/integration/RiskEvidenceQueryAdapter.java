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
 * @Module      : incident
 * @Package     : dz.sh.hidra.modules.incident.infrastructure.integration
 *
 * @Description : Enforces governed Risk owner boundaries.
 *
 */
package dz.sh.hidra.modules.incident.infrastructure.integration;

import dz.sh.hidra.modules.risk.application.contract.evidence.RiskOwnedEvidenceLookup;
import dz.sh.hidra.modules.incident.infrastructure.persistence.repository.IncidentJpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;
import java.util.Set;

@Component("incidentRiskEvidenceQueryAdapter")
public class RiskEvidenceQueryAdapter implements RiskOwnedEvidenceLookup {
    private final IncidentJpaRepository repository0;
    public RiskEvidenceQueryAdapter(IncidentJpaRepository repository0) {
        this.repository0 = java.util.Objects.requireNonNull(repository0);
    }
    @Override public String module() { return "incident"; }
    @Override public Set<String> evidenceTypes() { return Set.of("Incident"); }
    @Override @Transactional(readOnly = true)
    public Optional<Evidence> resolve(String type, String id) {
        if (type == null || id == null || id.isBlank()) return Optional.empty();
        return switch (type) {
            case "Incident" -> repository0.findById(id).map(e -> new Evidence(e.id(), e.incidentNumber(), e.title(), e.occurredAt(), null));
            default -> Optional.empty();
        };
    }
}
