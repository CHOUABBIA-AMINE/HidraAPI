/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : PartyCatalogTranslationRepositoryPort
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : party
 * @Package     : dz.sh.hidra.modules.party.application.port.out
 *
 * @Description : Repository port for PartyCatalogTranslation.
 *
 */
package dz.sh.hidra.modules.party.application.port.out;

import dz.sh.hidra.modules.party.domain.model.PartyCatalogTranslation;

import java.util.Optional;

/**
 * Repository port for PartyCatalogTranslation.
 */
public interface PartyCatalogTranslationRepositoryPort {

    PartyCatalogTranslation save(PartyCatalogTranslation model);

    Optional<PartyCatalogTranslation> findById(String id);
}
