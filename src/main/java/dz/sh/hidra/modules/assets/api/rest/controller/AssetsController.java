/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AssetsController
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
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

import dz.sh.hidra.modules.assets.api.rest.request.CreateMaintenanceWorkOrderRequest;
import dz.sh.hidra.modules.assets.api.rest.request.RegisterMaintainableAssetRequest;
import dz.sh.hidra.modules.assets.api.rest.response.MaintainableAssetResponse;
import dz.sh.hidra.modules.assets.api.rest.response.MaintenanceWorkOrderResponse;

/**
 * Framework-neutral assets controller contract.
 */
public interface AssetsController {

    MaintainableAssetResponse registerMaintainableAsset(RegisterMaintainableAssetRequest request);

    MaintenanceWorkOrderResponse createMaintenanceWorkOrder(CreateMaintenanceWorkOrderRequest request);
}
