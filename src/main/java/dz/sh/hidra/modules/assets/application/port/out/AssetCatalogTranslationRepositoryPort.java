/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AssetCatalogTranslationRepositoryPort
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : assets
 * @Package     : dz.sh.hidra.modules.assets.application.port.out
 *
 * @Description : Repository port for AssetCatalogTranslation.
 *
 */
package dz.sh.hidra.modules.assets.application.port.out;

import dz.sh.hidra.modules.assets.domain.model.AssetCatalogTranslation;

import java.util.Optional;

/**
 * Repository port for AssetCatalogTranslation.
 */
public interface AssetCatalogTranslationRepositoryPort {

    AssetCatalogTranslation save(AssetCatalogTranslation model);

    Optional<AssetCatalogTranslation> findById(String id);
}
