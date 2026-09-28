/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : MaintainableAsset
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-28
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : assets
 * @Package     : dz.sh.hidra.modules.assets.domain.model
 *
 * @Description : Asset maintained by the organization, linked to topology by neutral reference.
 *
 */
package dz.sh.hidra.modules.assets.domain.model;

import dz.sh.hidra.modules.assets.domain.exception.InvalidAssetsValueException;
import dz.sh.hidra.modules.assets.domain.value.*;
import java.time.Instant;

    /**
     * Asset maintained by the organization, linked to topology by neutral reference.
     *
         * @param id id
     * @param assetNumber assetNumber
     * @param assetCode assetCode
     * @param assetName assetName
     * @param assetTypeId assetTypeId
     * @param topologyAssetTypeCode topologyAssetTypeCode
     * @param topologyAssetId topologyAssetId
     * @param topologyAssetCodeSnapshot topologyAssetCodeSnapshot
     * @param topologyAssetNameSnapshot topologyAssetNameSnapshot
     * @param parentAssetId parentAssetId
     * @param status status
     * @param criticalityId criticalityId
     * @param ownerOrganizationUnitId ownerOrganizationUnitId
     * @param ownerOrganizationUnitNameSnapshot ownerOrganizationUnitNameSnapshot
     * @param manufacturerPartyId manufacturerPartyId
     * @param manufacturerNameSnapshot manufacturerNameSnapshot
     * @param modelId modelId
     * @param serialIdentityId serialIdentityId
     * @param registeredAt registeredAt
     * @param installedAt installedAt
     * @param commissionedAt commissionedAt
     * @param retiredAt retiredAt
     * @param createdByActorId createdByActorId
     * @param createdAt createdAt
     * @param updatedAt updatedAt
     */
    public record MaintainableAsset(
            String id,
        String assetNumber,
        String assetCode,
        String assetName,
        String assetTypeId,
        String topologyAssetTypeCode,
        String topologyAssetId,
        String topologyAssetCodeSnapshot,
        String topologyAssetNameSnapshot,
        String parentAssetId,
        AssetLifecycleStatus status,
        String criticalityId,
        String ownerOrganizationUnitId,
        String ownerOrganizationUnitNameSnapshot,
        String manufacturerPartyId,
        String manufacturerNameSnapshot,
        String modelId,
        String serialIdentityId,
        Instant registeredAt,
        Instant installedAt,
        Instant commissionedAt,
        Instant retiredAt,
        String createdByActorId,
        Instant createdAt,
        Instant updatedAt
    ) {

        public MaintainableAsset {
        // HRA-051 required: id
        if (id == null || id.isBlank()) {
            throw new InvalidAssetsValueException("MaintainableAsset id must not be blank.");
        }
        // HRA-051 required: assetNumber
        if (assetNumber == null || assetNumber.isBlank()) {
            throw new InvalidAssetsValueException("MaintainableAsset asset number must not be blank.");
        }
        // HRA-051 required: assetCode
        if (assetCode == null || assetCode.isBlank()) {
            throw new InvalidAssetsValueException("MaintainableAsset asset code must not be blank.");
        }
        // HRA-051 required: assetTypeId
        if (assetTypeId == null || assetTypeId.isBlank()) {
            throw new InvalidAssetsValueException("MaintainableAsset asset type id must not be blank.");
        }
        // HRA-051 required: topologyAssetTypeCode
        if (topologyAssetTypeCode == null || topologyAssetTypeCode.isBlank()) {
            throw new InvalidAssetsValueException("MaintainableAsset topology asset type code must not be blank.");
        }
        // HRA-051 required: topologyAssetId
        if (topologyAssetId == null || topologyAssetId.isBlank()) {
            throw new InvalidAssetsValueException("MaintainableAsset topology asset id must not be blank.");
        }
        // HRA-051 required: status
        if (status == null) {
            throw new InvalidAssetsValueException("MaintainableAsset status must not be null.");
        }
        // HRA-051 required: registeredAt
        if (registeredAt == null) {
            throw new InvalidAssetsValueException("MaintainableAsset registered at must not be null.");
        }
        // HRA-051 self-reference: parentAssetId != id
        if (id != null && parentAssetId != null && parentAssetId.equals(id)) {
            throw new InvalidAssetsValueException("MaintainableAsset parent asset id must not reference itself.");
        }

        id = normalize(id);
        assetNumber = normalize(assetNumber);
        assetCode = normalize(assetCode);
        assetName = normalize(assetName);
        assetTypeId = normalize(assetTypeId);
        topologyAssetTypeCode = normalize(topologyAssetTypeCode);
        topologyAssetId = normalize(topologyAssetId);
        topologyAssetCodeSnapshot = normalize(topologyAssetCodeSnapshot);
        topologyAssetNameSnapshot = normalize(topologyAssetNameSnapshot);
        parentAssetId = normalize(parentAssetId);
        criticalityId = normalize(criticalityId);
        ownerOrganizationUnitId = normalize(ownerOrganizationUnitId);
        ownerOrganizationUnitNameSnapshot = normalize(ownerOrganizationUnitNameSnapshot);
        manufacturerPartyId = normalize(manufacturerPartyId);
        manufacturerNameSnapshot = normalize(manufacturerNameSnapshot);
        modelId = normalize(modelId);
        serialIdentityId = normalize(serialIdentityId);
        createdByActorId = normalize(createdByActorId);
        }
        public boolean activeLifecycle() {
            return status != AssetLifecycleStatus.RETIRED
                    && status != AssetLifecycleStatus.DISPOSED
                    && status != AssetLifecycleStatus.CANCELLED;
        }
        private static String normalize(String value) {
            if (value == null || value.isBlank()) {
                return null;
            }
            return value.trim();
        }
    }
