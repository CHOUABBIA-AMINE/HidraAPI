/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AssetsRestMapper
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Utility
 * @Layer       : API
 * @Module      : assets
 * @Package     : dz.sh.hidra.modules.assets.api.rest.mapper
 *
 * @Description : Maps assets REST models to application models.
 *
 */
package dz.sh.hidra.modules.assets.api.rest.mapper;

import dz.sh.hidra.modules.assets.api.rest.request.CreateMaintenanceWorkOrderRequest;
import dz.sh.hidra.modules.assets.api.rest.request.RegisterMaintainableAssetRequest;
import dz.sh.hidra.modules.assets.api.rest.response.MaintainableAssetResponse;
import dz.sh.hidra.modules.assets.api.rest.response.MaintenanceWorkOrderResponse;
import dz.sh.hidra.modules.assets.application.command.CreateMaintenanceWorkOrderCommand;
import dz.sh.hidra.modules.assets.application.command.RegisterMaintainableAssetCommand;
import dz.sh.hidra.modules.assets.application.dto.MaintainableAssetSummaryDto;
import dz.sh.hidra.modules.assets.application.dto.MaintenanceWorkOrderSummaryDto;

/**
 * Maps assets REST models to application models.
 */
public final class AssetsRestMapper {

    private AssetsRestMapper() {
        throw new UnsupportedOperationException("Utility class must not be instantiated.");
    }

    public static RegisterMaintainableAssetCommand toCommand(RegisterMaintainableAssetRequest request) {
        return new RegisterMaintainableAssetCommand(
                request.assetNumber(),
                request.assetCode(),
                request.assetName(),
                request.assetTypeId(),
                request.topologyAssetTypeCode(),
                request.topologyAssetId(),
                request.topologyAssetCodeSnapshot(),
                request.topologyAssetNameSnapshot(),
                request.criticalityId(),
                request.ownerOrganizationUnitId(),
                request.ownerOrganizationUnitNameSnapshot(),
                request.manufacturerPartyId(),
                request.manufacturerNameSnapshot(),
                request.modelId(),
                request.serialIdentityId(),
                request.installedAt(),
                request.commissionedAt(),
                request.createdByActorId()
        );
    }

    public static CreateMaintenanceWorkOrderCommand toCommand(CreateMaintenanceWorkOrderRequest request) {
        return new CreateMaintenanceWorkOrderCommand(
                request.workOrderNumber(),
                request.maintainableAssetId(),
                request.maintenancePlanId(),
                request.sourceRecommendationId(),
                request.workOrderTypeId(),
                request.priorityId(),
                request.title(),
                request.description(),
                request.assignedOrganizationUnitId(),
                request.assignedActorId(),
                request.plannedStartAt(),
                request.plannedEndAt(),
                request.workflowInstanceId(),
                request.createdByActorId()
        );
    }

    public static MaintainableAssetResponse toResponse(MaintainableAssetSummaryDto dto) {
        return new MaintainableAssetResponse(
                dto.id(),
                dto.assetNumber(),
                dto.assetCode(),
                dto.assetName(),
                dto.assetTypeId(),
                dto.topologyAssetTypeCode(),
                dto.topologyAssetId(),
                dto.status(),
                dto.criticalityId(),
                dto.registeredAt()
        );
    }

    public static MaintenanceWorkOrderResponse toResponse(MaintenanceWorkOrderSummaryDto dto) {
        return new MaintenanceWorkOrderResponse(
                dto.id(),
                dto.workOrderNumber(),
                dto.maintainableAssetId(),
                dto.sourceRecommendationId(),
                dto.workOrderTypeId(),
                dto.status(),
                dto.title(),
                dto.plannedStartAt(),
                dto.completedAt()
        );
    }
}
