/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AssetsController
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-12
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

import dz.sh.hidra.modules.assets.api.rest.request.AssetConditionResponse;
import dz.sh.hidra.modules.assets.api.rest.request.CreateMaintenanceWorkOrderRequest;
import dz.sh.hidra.modules.assets.api.rest.request.RecordAssetConditionRequest;
import dz.sh.hidra.modules.assets.api.rest.request.RegisterMaintainableAssetRequest;
import dz.sh.hidra.modules.assets.api.rest.request.UpdateMaintainableAssetRequest;
import dz.sh.hidra.modules.assets.api.rest.response.MaintainableAssetResponse;
import dz.sh.hidra.modules.assets.api.rest.response.MaintenanceWorkOrderResponse;

/**
 * Framework-neutral assets controller contract.
 */
public interface AssetsController {
    MaintenanceWorkOrderResponse createMaintenanceWorkOrder(CreateMaintenanceWorkOrderRequest request);
    dz.sh.hidra.modules.assets.api.rest.response.AssetConditionResponse recordAssetCondition(RecordAssetConditionRequest request);
    MaintainableAssetResponse registerMaintainableAsset(RegisterMaintainableAssetRequest request);
    MaintainableAssetResponse updateMaintainableAsset(String assetId, UpdateMaintainableAssetRequest request);
}
