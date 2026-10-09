/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : CustodyProductCandidateEvidenceQueryAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-09
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : simulation
 * @Package     : dz.sh.hidra.modules.simulation.infrastructure.integration
 *
 * @Description : Copies catalogue candidate evidence through the public owner contract.
 *
 */
package dz.sh.hidra.modules.simulation.infrastructure.integration;

import dz.sh.hidra.modules.simulation.application.port.out.SimulationProductCandidateEvidencePort;
import dz.sh.hidra.modules.custody.application.contract.simulation.SimulationProductCandidateContract;
import java.util.Objects;
import java.util.Optional;
import org.springframework.stereotype.Component;

@Component
public final class CustodyProductCandidateEvidenceQueryAdapter implements SimulationProductCandidateEvidencePort {
    private final SimulationProductCandidateContract catalog;

    public CustodyProductCandidateEvidenceQueryAdapter(SimulationProductCandidateContract catalog) {
        this.catalog = Objects.requireNonNull(catalog, "Custody candidate contract must not be null.");
    }

    @Override
    public Optional<CandidateEvidence> resolve(String id) {
        if (id == null || id.isBlank()) return Optional.empty();
        String key = id.trim();
        return catalog.resolve(key).filter(entry -> key.equals(entry.id()))
                .map(entry -> new CandidateEvidence(entry.id(), entry.catalogName(), entry.code(),
                        entry.active(), entry.createdAt(), entry.updatedAt()));
    }
}
