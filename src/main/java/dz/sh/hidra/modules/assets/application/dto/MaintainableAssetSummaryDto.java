/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : MaintainableAssetSummaryDto
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Application
 * @Module      : assets
 * @Package     : dz.sh.hidra.modules.assets.application.dto
 *
 * @Description : Maintainable asset summary DTO.
 *
 */
package dz.sh.hidra.modules.assets.application.dto;

import dz.sh.hidra.modules.assets.domain.value.AssetLifecycleStatus;

import java.time.Instant;

/**
 * Maintainable asset summary DTO.
 */
public record MaintainableAssetSummaryDto(
        String id,
        String assetNumber,
        String assetCode,
        String assetName,
        String assetTypeId,
        String topologyAssetTypeCode,
        String topologyAssetId,
        AssetLifecycleStatus status,
        String criticalityId,
        Instant registeredAt
) {
}
