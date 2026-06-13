/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AssetsController
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-13
 *
 * @Type        : Interface
 * @Layer       : API
 * @Module      : assets
 * @Package     : dz.sh.hidra.modules.assets.api.rest.controller
 *
 * @Description : Framework-neutral assets controller contract.
 *
 */
package dz.sh.hidra.modules.assets.api.rest.controller;
import dz.sh.hidra.modules.assets.api.rest.request.*;
import dz.sh.hidra.modules.assets.api.rest.response.*;

/**
 * Framework-neutral assets controller contract.
 */
public interface AssetsController {
    MaintenanceWorkOrderResponse createMaintenanceWorkOrder(CreateMaintenanceWorkOrderRequest request);
    AssetConditionResponse recordAssetCondition(RecordAssetConditionRequest request);
    MaintainableAssetResponse registerMaintainableAsset(RegisterMaintainableAssetRequest request);
}
