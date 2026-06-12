/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : RegisterMaintainableAssetCommand
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Application
 * @Module      : assets
 * @Package     : dz.sh.hidra.modules.assets.application.command
 *
 * @Description : Command to register maintainable asset.
 *
 */
package dz.sh.hidra.modules.assets.application.command;

import java.time.Instant;

/**
 * Command to register maintainable asset.
 */
public record RegisterMaintainableAssetCommand(
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
