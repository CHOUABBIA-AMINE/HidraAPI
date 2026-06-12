/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : RegisterMaintainableAssetUseCase
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : assets
 * @Package     : dz.sh.hidra.modules.assets.application.port.in
 *
 * @Description : Use case for registering maintainable assets.
 *
 */
package dz.sh.hidra.modules.assets.application.port.in;

import dz.sh.hidra.modules.assets.application.command.RegisterMaintainableAssetCommand;
import dz.sh.hidra.modules.assets.application.dto.MaintainableAssetSummaryDto;

/**
 * Use case for registering maintainable assets.
 */
public interface RegisterMaintainableAssetUseCase {

    MaintainableAssetSummaryDto registerMaintainableAsset(RegisterMaintainableAssetCommand command);
}
