/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AssetSparePartCompatibilityRepositoryPort
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : assets
 * @Package     : dz.sh.hidra.modules.assets.application.port.out
 *
 * @Description : Repository port for AssetSparePartCompatibility.
 *
 */
package dz.sh.hidra.modules.assets.application.port.out;

import dz.sh.hidra.modules.assets.domain.model.AssetSparePartCompatibility;

import java.util.Optional;

/**
 * Repository port for AssetSparePartCompatibility.
 */
public interface AssetSparePartCompatibilityRepositoryPort {

    AssetSparePartCompatibility save(AssetSparePartCompatibility model);

    Optional<AssetSparePartCompatibility> findById(String id);
}
