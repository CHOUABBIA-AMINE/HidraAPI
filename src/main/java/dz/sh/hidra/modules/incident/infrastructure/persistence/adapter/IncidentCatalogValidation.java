/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : IncidentCatalogValidation
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-08
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : incident
 * @Package     : dz.sh.hidra.modules.incident.infrastructure.persistence.adapter
 *
 * @Description : Enforces Incident lifecycle integrity and owner-controlled evidence.
 *
 */
package dz.sh.hidra.modules.incident.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.incident.infrastructure.persistence.repository.IncidentCatalogEntryJpaRepository;
import java.util.Objects;
import org.springframework.stereotype.Component;
@Component
public final class IncidentCatalogValidation {
    private final IncidentCatalogEntryJpaRepository catalog;
    public IncidentCatalogValidation(IncidentCatalogEntryJpaRepository catalog) {this.catalog=Objects.requireNonNull(catalog);}
    public void require(String id,String family,boolean eligible) {
        var row=catalog.findById(id).orElseThrow(() -> new IllegalArgumentException("Missing Incident catalog: "+family));
        if(!family.equals(row.catalogName()) || (eligible && !row.active())) throw new IllegalArgumentException("Invalid or inactive Incident catalog: "+family);
    }
}
