/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : PartyCatalogEntryRepositoryPort
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : party
 * @Package     : dz.sh.hidra.modules.party.application.port.out
 *
 * @Description : Repository port for PartyCatalogEntry.
 *
 */
package dz.sh.hidra.modules.party.application.port.out;

import dz.sh.hidra.modules.party.domain.model.PartyCatalogEntry;

import java.util.Optional;

/**
 * Repository port for PartyCatalogEntry.
 */
public interface PartyCatalogEntryRepositoryPort {

    PartyCatalogEntry save(PartyCatalogEntry model);

    Optional<PartyCatalogEntry> findById(String id);
}
