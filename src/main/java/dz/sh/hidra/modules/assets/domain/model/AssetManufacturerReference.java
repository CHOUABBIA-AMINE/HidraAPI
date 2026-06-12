/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AssetManufacturerReference
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
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
