/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AssetsApplicationMapper
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Utility
 * @Layer       : Application
 * @Module      : assets
 * @Package     : dz.sh.hidra.modules.assets.application.mapper
 *
 * @Description : Maps assets domain models to DTOs.
 *
 */
package dz.sh.hidra.modules.assets.application.mapper;

import dz.sh.hidra.modules.assets.application.dto.AssetConditionSummaryDto;
import dz.sh.hidra.modules.assets.application.dto.MaintainableAssetSummaryDto;
import dz.sh.hidra.modules.assets.application.dto.MaintenanceWorkOrderSummaryDto;
import dz.sh.hidra.modules.assets.domain.model.AssetConditionRecord;
import dz.sh.hidra.modules.assets.domain.model.MaintainableAsset;
import dz.sh.hidra.modules.assets.domain.model.MaintenanceWorkOrder;

/**
 * Maps assets domain models to DTOs.
 */
public final class AssetsApplicationMapper {

    private AssetsApplicationMapper() {
        throw new UnsupportedOperationException("Utility class must not be instantiated.");
    }

    public static MaintainableAssetSummaryDto toSummary(MaintainableAsset asset) {
        return new MaintainableAssetSummaryDto(
                asset.id(),
                asset.assetNumber(),
                asset.assetCode(),
                asset.assetName(),
                asset.assetTypeId(),
                asset.topologyAssetTypeCode(),
                asset.topologyAssetId(),
                asset.status(),
                asset.criticalityId(),
                asset.registeredAt()
        );
    }

    public static MaintenanceWorkOrderSummaryDto toSummary(MaintenanceWorkOrder workOrder) {
        return new MaintenanceWorkOrderSummaryDto(
                workOrder.id(),
                workOrder.workOrderNumber(),
                workOrder.maintainableAssetId(),
                workOrder.sourceRecommendationId(),
                workOrder.workOrderTypeId(),
                workOrder.status(),
                workOrder.title(),
                workOrder.plannedStartAt(),
                workOrder.completedAt()
        );
    }

    public static AssetConditionSummaryDto toSummary(AssetConditionRecord conditionRecord) {
        return new AssetConditionSummaryDto(
                conditionRecord.id(),
                conditionRecord.maintainableAssetId(),
                conditionRecord.conditionStatus(),
                conditionRecord.conditionScore(),
                conditionRecord.observedAt()
        );
    }
}
