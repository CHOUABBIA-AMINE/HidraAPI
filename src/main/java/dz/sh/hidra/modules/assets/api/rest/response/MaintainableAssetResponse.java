/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : MaintainableAssetResponse
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : API
 * @Module      : assets
 * @Package     : dz.sh.hidra.modules.assets.api.rest.response
 *
 * @Description : REST response for maintainable asset.
 *
 */
package dz.sh.hidra.modules.assets.api.rest.response;

import dz.sh.hidra.modules.assets.domain.value.AssetLifecycleStatus;

import java.time.Instant;

/**
 * REST response for maintainable asset.
 */
public record MaintainableAssetResponse(
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
