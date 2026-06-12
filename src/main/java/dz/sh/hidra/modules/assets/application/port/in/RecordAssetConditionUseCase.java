/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : RecordAssetConditionUseCase
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : assets
 * @Package     : dz.sh.hidra.modules.assets.application.port.in
 *
 * @Description : Use case for recording asset condition.
 *
 */
package dz.sh.hidra.modules.assets.application.port.in;

import dz.sh.hidra.modules.assets.application.command.RecordAssetConditionCommand;
import dz.sh.hidra.modules.assets.application.dto.AssetConditionSummaryDto;

/**
 * Use case for recording asset condition.
 */
public interface RecordAssetConditionUseCase {

    AssetConditionSummaryDto recordAssetCondition(RecordAssetConditionCommand command);
}
