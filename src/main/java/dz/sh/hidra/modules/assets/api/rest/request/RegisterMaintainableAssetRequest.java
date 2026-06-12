/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : RegisterMaintainableAssetRequest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : API
 * @Module      : assets
 * @Package     : dz.sh.hidra.modules.assets.api.rest.request
 *
 * @Description : REST request to register maintainable asset.
 *
 */
package dz.sh.hidra.modules.assets.api.rest.request;

import java.time.Instant;

/**
 * REST request to register maintainable asset.
 */
public record RegisterMaintainableAssetRequest(
        String assetNumber,
        String assetCode,
        String assetName,
        String assetTypeId,
        String topologyAssetTypeCode,
        String topologyAssetId,
        String topologyAssetCodeSnapshot,
        String topologyAssetNameSnapshot,
        String criticalityId,
        String ownerOrganizationUnitId,
        String ownerOrganizationUnitNameSnapshot,
        String manufacturerPartyId,
        String manufacturerNameSnapshot,
        String modelId,
        String serialIdentityId,
        Instant installedAt,
        Instant commissionedAt,
        String createdByActorId
) {
}
