/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaDocumentsCatalogEligibilityAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-07
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : documents
 * @Package     : dz.sh.hidra.modules.documents.infrastructure.persistence.adapter
 *
 * @Description : Enforces Documents semantic integrity through explicit owner boundaries.
 *
 */
package dz.sh.hidra.modules.documents.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.documents.application.port.out.DocumentsCatalogEligibilityPort;
import dz.sh.hidra.modules.documents.infrastructure.persistence.repository.DocumentCatalogEntryJpaRepository;
import java.util.Objects;
import org.springframework.stereotype.Component;

@Component
public class JpaDocumentsCatalogEligibilityAdapter implements DocumentsCatalogEligibilityPort {
    private final DocumentCatalogEntryJpaRepository repository;
    public JpaDocumentsCatalogEligibilityAdapter(DocumentCatalogEntryJpaRepository repository) {
        this.repository = Objects.requireNonNull(repository);
    }
    @Override
    public void requireActive(String entryId, String family) {
        if (entryId == null || entryId.isBlank() || family == null || family.isBlank()) {
            throw new IllegalArgumentException("Catalog identity and family are required.");
        }
        repository.findById(entryId.trim()).filter(c -> c.active() && family.equals(c.catalogName()))
                .orElseThrow(() -> new IllegalArgumentException("Active Documents " + family + " entry required: " + entryId));
    }
}
