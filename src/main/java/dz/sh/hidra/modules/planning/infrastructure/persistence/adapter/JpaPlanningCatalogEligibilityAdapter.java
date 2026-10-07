/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaPlanningCatalogEligibilityAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-07
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : planning
 * @Package     : dz.sh.hidra.modules.planning.infrastructure.persistence.adapter
 *
 * @Description : Enforces Planning-owned semantic integrity through explicit owner boundaries.
 *
 */
package dz.sh.hidra.modules.planning.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.planning.application.port.out.PlanningCatalogEligibilityPort;
import dz.sh.hidra.modules.planning.infrastructure.persistence.repository.PlanningCatalogEntryJpaRepository;
import java.util.Objects;
import org.springframework.stereotype.Component;

@Component
public class JpaPlanningCatalogEligibilityAdapter implements PlanningCatalogEligibilityPort {
    private final PlanningCatalogEntryJpaRepository repository;
    public JpaPlanningCatalogEligibilityAdapter(PlanningCatalogEntryJpaRepository repository) {
        this.repository = Objects.requireNonNull(repository);
    }
    @Override
    public void requireActive(String entryId, String family) {
        if (entryId == null || entryId.isBlank() || family == null || family.isBlank()) {
            throw new IllegalArgumentException("Catalog identity and family are required.");
        }
        repository.findById(entryId.trim()).filter(c -> c.active() && family.equals(c.catalogName()))
                .orElseThrow(() -> new IllegalArgumentException("Active Planning " + family + " entry required: " + entryId));
    }
}
