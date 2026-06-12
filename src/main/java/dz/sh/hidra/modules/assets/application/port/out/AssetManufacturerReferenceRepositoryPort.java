/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AssetManufacturerReferenceRepositoryPort
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : assets
 * @Package     : dz.sh.hidra.modules.assets.application.port.out
 *
 * @Description : Repository port for AssetManufacturerReference.
 *
 */
package dz.sh.hidra.modules.assets.application.port.out;

import dz.sh.hidra.modules.assets.domain.model.AssetManufacturerReference;

import java.util.Optional;

/**
 * Repository port for AssetManufacturerReference.
 */
public interface AssetManufacturerReferenceRepositoryPort {

    AssetManufacturerReference save(AssetManufacturerReference model);

    Optional<AssetManufacturerReference> findById(String id);
}
