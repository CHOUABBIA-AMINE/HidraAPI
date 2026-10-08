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
 * @Module      : alarm
 * @Package     : dz.sh.hidra.modules.alarm.infrastructure.integration
 *
 * @Description : Enforces governed Risk owner boundaries.
 *
 */
package dz.sh.hidra.modules.alarm.infrastructure.integration;

import dz.sh.hidra.modules.risk.application.contract.evidence.RiskOwnedEvidenceLookup;
import dz.sh.hidra.modules.alarm.infrastructure.persistence.repository.AlarmJpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;
import java.util.Set;

@Component
public class RiskEvidenceQueryAdapter implements RiskOwnedEvidenceLookup {
    private final AlarmJpaRepository repository0;
    public RiskEvidenceQueryAdapter(AlarmJpaRepository repository0) {
        this.repository0 = java.util.Objects.requireNonNull(repository0);
    }
    @Override public String module() { return "alarm"; }
    @Override public Set<String> evidenceTypes() { return Set.of("Alarm"); }
    @Override @Transactional(readOnly = true)
    public Optional<Evidence> resolve(String type, String id) {
        if (type == null || id == null || id.isBlank()) return Optional.empty();
        return switch (type) {
            case "Alarm" -> repository0.findById(id).map(e -> new Evidence(e.id(), e.alarmNumber(), e.titleFr(), e.raisedAt(), null));
            default -> Optional.empty();
        };
    }
}
