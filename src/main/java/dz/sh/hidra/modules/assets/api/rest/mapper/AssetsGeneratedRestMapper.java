/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AssetsGeneratedRestMapper
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-28
 *
 * @Type        : Interface
 * @Layer       : API
 * @Module      : assets
 * @Package     : dz.sh.hidra.modules.assets.api.rest.mapper
 *
 * @Description : Generates exact assets API/application boundary mappings at compile time.
 *
 */
package dz.sh.hidra.modules.assets.api.rest.mapper;

import dz.sh.hidra.modules.assets.api.rest.request.CreateMaintenanceWorkOrderRequest;
import dz.sh.hidra.modules.assets.api.rest.request.RecordAssetConditionRequest;
import dz.sh.hidra.modules.assets.api.rest.request.RegisterMaintainableAssetRequest;
import dz.sh.hidra.modules.assets.api.rest.response.AssetConditionResponse;
import dz.sh.hidra.modules.assets.api.rest.response.MaintainableAssetResponse;
import dz.sh.hidra.modules.assets.api.rest.response.MaintenanceWorkOrderResponse;
import dz.sh.hidra.modules.assets.application.command.CreateMaintenanceWorkOrderCommand;
import dz.sh.hidra.modules.assets.application.command.RecordAssetConditionCommand;
import dz.sh.hidra.modules.assets.application.command.RegisterMaintainableAssetCommand;
import dz.sh.hidra.modules.assets.application.dto.AssetConditionSummaryDto;
import dz.sh.hidra.modules.assets.application.dto.MaintainableAssetSummaryDto;
import dz.sh.hidra.modules.assets.application.dto.MaintenanceWorkOrderSummaryDto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

/**
 * Generates only the HRA-070-approved exact assets boundary mappings.
 */
@Mapper(
        unmappedSourcePolicy = ReportingPolicy.ERROR,
        unmappedTargetPolicy = ReportingPolicy.ERROR
)
public interface AssetsGeneratedRestMapper {

    AssetsGeneratedRestMapper INSTANCE = Mappers.getMapper(AssetsGeneratedRestMapper.class);

    CreateMaintenanceWorkOrderCommand toCommand(CreateMaintenanceWorkOrderRequest request);

    RecordAssetConditionCommand toCommand(RecordAssetConditionRequest request);

    RegisterMaintainableAssetCommand toCommand(RegisterMaintainableAssetRequest request);

    AssetConditionResponse toResponse(AssetConditionSummaryDto dto);

    MaintainableAssetResponse toResponse(MaintainableAssetSummaryDto dto);

    MaintenanceWorkOrderResponse toResponse(MaintenanceWorkOrderSummaryDto dto);
}
