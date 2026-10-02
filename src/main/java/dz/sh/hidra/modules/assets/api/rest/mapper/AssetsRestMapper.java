/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AssetsRestMapper
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-28
 *
 * @Type        : Class
 * @Layer       : API
 * @Module      : assets
 * @Package     : dz.sh.hidra.modules.assets.api.rest.mapper
 *
 * @Description : Maps assets REST models to application models.
 *
 */
package dz.sh.hidra.modules.assets.api.rest.mapper;

import dz.sh.hidra.modules.assets.api.rest.request.CreateMaintenanceWorkOrderRequest;
import dz.sh.hidra.modules.assets.api.rest.request.RecordAssetConditionRequest;
import dz.sh.hidra.modules.assets.api.rest.request.RegisterMaintainableAssetRequest;
import dz.sh.hidra.modules.assets.api.rest.request.UpdateMaintainableAssetRequest;
import dz.sh.hidra.modules.assets.api.rest.response.AssetConditionResponse;
import dz.sh.hidra.modules.assets.api.rest.response.MaintainableAssetResponse;
import dz.sh.hidra.modules.assets.api.rest.response.MaintenanceWorkOrderResponse;
import dz.sh.hidra.modules.assets.application.command.CreateMaintenanceWorkOrderCommand;
import dz.sh.hidra.modules.assets.application.command.RecordAssetConditionCommand;
import dz.sh.hidra.modules.assets.application.command.RegisterMaintainableAssetCommand;
import dz.sh.hidra.modules.assets.application.dto.AssetConditionSummaryDto;
import dz.sh.hidra.modules.assets.application.dto.MaintainableAssetSummaryDto;
import dz.sh.hidra.modules.assets.application.dto.MaintenanceWorkOrderSummaryDto;
import dz.sh.hidra.modules.assets.application.port.in.UpdateMaintainableAssetUseCase;
import java.util.Objects;

/**
 * Maps assets REST models to application models.
 */
public final class AssetsRestMapper {

    private static final AssetsGeneratedRestMapper GENERATED = AssetsGeneratedRestMapper.INSTANCE;

    private AssetsRestMapper() {
        throw new UnsupportedOperationException("Utility class must not be instantiated.");
    }

    public static CreateMaintenanceWorkOrderCommand toCommand(CreateMaintenanceWorkOrderRequest request) {
        return GENERATED.toCommand(Objects.requireNonNull(request, "CreateMaintenanceWorkOrderRequest must not be null."));
    }

    public static RecordAssetConditionCommand toCommand(RecordAssetConditionRequest request) {
        return GENERATED.toCommand(Objects.requireNonNull(request, "RecordAssetConditionRequest must not be null."));
    }

    public static RegisterMaintainableAssetCommand toCommand(RegisterMaintainableAssetRequest request) {
        return GENERATED.toCommand(Objects.requireNonNull(request, "RegisterMaintainableAssetRequest must not be null."));
    }

    public static UpdateMaintainableAssetUseCase.Command toCommand(UpdateMaintainableAssetRequest request) {
        return new UpdateMaintainableAssetUseCase.Command(
                request.expectedUpdatedAt(),
                request.assetName()
        );
    }

    public static MaintenanceWorkOrderResponse toResponse(MaintenanceWorkOrderSummaryDto dto) {
        return GENERATED.toResponse(Objects.requireNonNull(dto, "MaintenanceWorkOrderSummaryDto must not be null."));
    }

    public static AssetConditionResponse toResponse(AssetConditionSummaryDto dto) {
        return GENERATED.toResponse(Objects.requireNonNull(dto, "AssetConditionSummaryDto must not be null."));
    }

    public static MaintainableAssetResponse toResponse(MaintainableAssetSummaryDto dto) {
        return GENERATED.toResponse(Objects.requireNonNull(dto, "MaintainableAssetSummaryDto must not be null."));
    }
}
