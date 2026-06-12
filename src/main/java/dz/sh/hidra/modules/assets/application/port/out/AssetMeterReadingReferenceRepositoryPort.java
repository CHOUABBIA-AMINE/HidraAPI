/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AssetMeterReadingReferenceRepositoryPort
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : assets
 * @Package     : dz.sh.hidra.modules.assets.application.port.out
 *
 * @Description : Repository port for AssetMeterReadingReference.
 *
 */
package dz.sh.hidra.modules.assets.application.port.out;

import dz.sh.hidra.modules.assets.domain.model.AssetMeterReadingReference;

import java.util.Optional;

/**
 * Repository port for AssetMeterReadingReference.
 */
public interface AssetMeterReadingReferenceRepositoryPort {

    AssetMeterReadingReference save(AssetMeterReadingReference model);

    Optional<AssetMeterReadingReference> findById(String id);
}
