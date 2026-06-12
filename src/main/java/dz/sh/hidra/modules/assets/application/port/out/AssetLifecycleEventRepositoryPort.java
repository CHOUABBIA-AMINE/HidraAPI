/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AssetLifecycleEventRepositoryPort
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : assets
 * @Package     : dz.sh.hidra.modules.assets.application.port.out
 *
 * @Description : Repository port for AssetLifecycleEvent.
 *
 */
package dz.sh.hidra.modules.assets.application.port.out;

import dz.sh.hidra.modules.assets.domain.model.AssetLifecycleEvent;

import java.util.Optional;

/**
 * Repository port for AssetLifecycleEvent.
 */
public interface AssetLifecycleEventRepositoryPort {

    AssetLifecycleEvent save(AssetLifecycleEvent model);

    Optional<AssetLifecycleEvent> findById(String id);
}
