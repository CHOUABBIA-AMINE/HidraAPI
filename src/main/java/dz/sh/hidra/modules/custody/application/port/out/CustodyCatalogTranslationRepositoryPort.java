/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : CustodyCatalogTranslationRepositoryPort
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : custody
 * @Package     : dz.sh.hidra.modules.custody.application.port.out
 *
 * @Description : Repository port for CustodyCatalogTranslation.
 *
 */
package dz.sh.hidra.modules.custody.application.port.out;

import dz.sh.hidra.modules.custody.domain.model.CustodyCatalogTranslation;

import java.util.Optional;

/**
 * Repository port for CustodyCatalogTranslation.
 */
public interface CustodyCatalogTranslationRepositoryPort {

    CustodyCatalogTranslation save(CustodyCatalogTranslation model);

    Optional<CustodyCatalogTranslation> findById(String id);
}
