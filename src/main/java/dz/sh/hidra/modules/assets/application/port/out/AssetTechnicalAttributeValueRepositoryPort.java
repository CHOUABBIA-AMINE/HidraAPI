/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AssetTechnicalAttributeValueRepositoryPort
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : assets
 * @Package     : dz.sh.hidra.modules.assets.application.port.out
 *
 * @Description : Repository port for AssetTechnicalAttributeValue.
 *
 */
package dz.sh.hidra.modules.assets.application.port.out;

import dz.sh.hidra.modules.assets.domain.model.AssetTechnicalAttributeValue;

import java.util.Optional;

/**
 * Repository port for AssetTechnicalAttributeValue.
 */
public interface AssetTechnicalAttributeValueRepositoryPort {

    AssetTechnicalAttributeValue save(AssetTechnicalAttributeValue model);

    Optional<AssetTechnicalAttributeValue> findById(String id);
}
