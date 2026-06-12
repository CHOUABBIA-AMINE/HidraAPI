/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : IntegrityCatalogEntryRepositoryPort
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : integrity
 * @Package     : dz.sh.hidra.modules.integrity.application.port.out
 *
 * @Description : Repository port for IntegrityCatalogEntry.
 *
 */
package dz.sh.hidra.modules.integrity.application.port.out;

import dz.sh.hidra.modules.integrity.domain.model.IntegrityCatalogEntry;

import java.util.Optional;

/**
 * Repository port for IntegrityCatalogEntry.
 */
public interface IntegrityCatalogEntryRepositoryPort {

    IntegrityCatalogEntry save(IntegrityCatalogEntry model);

    Optional<IntegrityCatalogEntry> findById(String id);
}
