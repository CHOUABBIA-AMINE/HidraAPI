/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AssetTechnicalAttributeDefinitionRepositoryPort
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : assets
 * @Package     : dz.sh.hidra.modules.assets.application.port.out
 *
 * @Description : Repository port for AssetTechnicalAttributeDefinition.
 *
 */
package dz.sh.hidra.modules.assets.application.port.out;

import dz.sh.hidra.modules.assets.domain.model.AssetTechnicalAttributeDefinition;

import java.util.Optional;

/**
 * Repository port for AssetTechnicalAttributeDefinition.
 */
public interface AssetTechnicalAttributeDefinitionRepositoryPort {

    AssetTechnicalAttributeDefinition save(AssetTechnicalAttributeDefinition model);

    Optional<AssetTechnicalAttributeDefinition> findById(String id);
}
