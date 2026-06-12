/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AssetSerialIdentityRepositoryPort
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : assets
 * @Package     : dz.sh.hidra.modules.assets.application.port.out
 *
 * @Description : Repository port for AssetSerialIdentity.
 *
 */
package dz.sh.hidra.modules.assets.application.port.out;

import dz.sh.hidra.modules.assets.domain.model.AssetSerialIdentity;

import java.util.Optional;

/**
 * Repository port for AssetSerialIdentity.
 */
public interface AssetSerialIdentityRepositoryPort {

    AssetSerialIdentity save(AssetSerialIdentity model);

    Optional<AssetSerialIdentity> findById(String id);
}
