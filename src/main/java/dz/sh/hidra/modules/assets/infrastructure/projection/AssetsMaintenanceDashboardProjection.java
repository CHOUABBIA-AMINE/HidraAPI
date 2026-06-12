/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AssetsMaintenanceDashboardProjection
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Infrastructure
 * @Module      : assets
 * @Package     : dz.sh.hidra.modules.assets.infrastructure.projection
 *
 * @Description : Assets maintenance dashboard projection.
 *
 */
package dz.sh.hidra.modules.assets.infrastructure.projection;

import dz.sh.hidra.modules.assets.domain.value.AssetConditionStatus;
import dz.sh.hidra.modules.assets.domain.value.MaintenanceWorkOrderStatus;

import java.time.Instant;

/**
 * Assets maintenance dashboard projection.
 */
public record AssetsMaintenanceDashboardProjection(
        String maintainableAssetId,
        String assetCode,
        String assetName,
        AssetConditionStatus conditionStatus,
        int openWorkOrderCount,
        MaintenanceWorkOrderStatus mostCriticalWorkOrderStatus,
        Instant nextMaintenanceDueAt
) {
}
