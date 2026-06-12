/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : MaintainableAssetRepositoryPort
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : assets
 * @Package     : dz.sh.hidra.modules.assets.application.port.out
 *
 * @Description : Repository port for MaintainableAsset.
 *
 */
package dz.sh.hidra.modules.assets.application.port.out;

import dz.sh.hidra.modules.assets.domain.model.MaintainableAsset;

import java.util.Optional;

/**
 * Repository port for MaintainableAsset.
 */
public interface MaintainableAssetRepositoryPort {

    MaintainableAsset save(MaintainableAsset model);

    Optional<MaintainableAsset> findById(String id);
}
