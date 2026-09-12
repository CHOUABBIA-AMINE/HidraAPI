/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : GetMaintainableAssetUseCase
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-12
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : assets
 * @Package     : dz.sh.hidra.modules.assets.application.port.in
 *
 * @Description : Reads one maintainable asset with its authoritative concurrency token.
 *
 */
package dz.sh.hidra.modules.assets.application.port.in;

import dz.sh.hidra.modules.assets.domain.model.MaintainableAsset;

public interface GetMaintainableAssetUseCase {

    MaintainableAsset get(String assetId);
}
