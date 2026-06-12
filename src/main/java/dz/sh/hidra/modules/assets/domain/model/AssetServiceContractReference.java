/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AssetServiceContractReference
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : assets
 * @Package     : dz.sh.hidra.modules.assets.domain.model
 *
 * @Description : Neutral service-contract reference.
 *
 */
package dz.sh.hidra.modules.assets.domain.model;

import java.time.Instant;

    /**
     * Neutral service-contract reference.
     *
         * @param id id
     * @param maintainableAssetId maintainableAssetId
     * @param contractReferenceId contractReferenceId
     * @param contractCodeSnapshot contractCodeSnapshot
     * @param serviceProviderPartyId serviceProviderPartyId
     * @param serviceProviderNameSnapshot serviceProviderNameSnapshot
     * @param validFrom validFrom
     * @param validTo validTo
     * @param active active
     * @param createdAt createdAt
     */
    public record AssetServiceContractReference(
            String id,
        String maintainableAssetId,
        String contractReferenceId,
        String contractCodeSnapshot,
        String serviceProviderPartyId,
        String serviceProviderNameSnapshot,
        Instant validFrom,
        Instant validTo,
        boolean active,
        Instant createdAt
    ) {

        public AssetServiceContractReference {
        id = normalize(id);
        maintainableAssetId = normalize(maintainableAssetId);
        contractReferenceId = normalize(contractReferenceId);
        contractCodeSnapshot = normalize(contractCodeSnapshot);
        serviceProviderPartyId = normalize(serviceProviderPartyId);
        serviceProviderNameSnapshot = normalize(serviceProviderNameSnapshot);
        }

        private static String normalize(String value) {
            if (value == null || value.isBlank()) {
                return null;
            }
            return value.trim();
        }
    }
