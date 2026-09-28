/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AssetManufacturerReference
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-28
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : assets
 * @Package     : dz.sh.hidra.modules.assets.domain.model
 *
 * @Description : Neutral reference to manufacturer/party.
 *
 */
package dz.sh.hidra.modules.assets.domain.model;

import dz.sh.hidra.modules.assets.domain.exception.InvalidAssetsValueException;
import java.time.Instant;

    /**
     * Neutral reference to manufacturer/party.
     *
         * @param id id
     * @param maintainableAssetId maintainableAssetId
     * @param manufacturerPartyId manufacturerPartyId
     * @param manufacturerCodeSnapshot manufacturerCodeSnapshot
     * @param manufacturerNameSnapshot manufacturerNameSnapshot
     * @param manufacturerRoleCodeSnapshot manufacturerRoleCodeSnapshot
     * @param manufacturerReferenceNumber manufacturerReferenceNumber
     * @param createdAt createdAt
     * @param updatedAt updatedAt
     */
    public record AssetManufacturerReference(
            String id,
        String maintainableAssetId,
        String manufacturerPartyId,
        String manufacturerCodeSnapshot,
        String manufacturerNameSnapshot,
        String manufacturerRoleCodeSnapshot,
        String manufacturerReferenceNumber,
        Instant createdAt,
        Instant updatedAt
    ) {

        public AssetManufacturerReference {
        // HRA-051 required: id
        if (id == null || id.isBlank()) {
            throw new InvalidAssetsValueException("AssetManufacturerReference id must not be blank.");
        }
        // HRA-051 required: manufacturerPartyId
        if (manufacturerPartyId == null || manufacturerPartyId.isBlank()) {
            throw new InvalidAssetsValueException("AssetManufacturerReference manufacturer party id must not be blank.");
        }

        id = normalize(id);
        maintainableAssetId = normalize(maintainableAssetId);
        manufacturerPartyId = normalize(manufacturerPartyId);
        manufacturerCodeSnapshot = normalize(manufacturerCodeSnapshot);
        manufacturerNameSnapshot = normalize(manufacturerNameSnapshot);
        manufacturerRoleCodeSnapshot = normalize(manufacturerRoleCodeSnapshot);
        manufacturerReferenceNumber = normalize(manufacturerReferenceNumber);
        }

        private static String normalize(String value) {
            if (value == null || value.isBlank()) {
                return null;
            }
            return value.trim();
        }
    }
