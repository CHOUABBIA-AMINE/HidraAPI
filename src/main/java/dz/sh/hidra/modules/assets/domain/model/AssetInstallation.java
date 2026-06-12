/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AssetInstallation
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : assets
 * @Package     : dz.sh.hidra.modules.assets.domain.model
 *
 * @Description : Installation and commissioning record.
 *
 */
package dz.sh.hidra.modules.assets.domain.model;

import java.time.Instant;

    /**
     * Installation and commissioning record.
     *
         * @param id id
     * @param maintainableAssetId maintainableAssetId
     * @param installationNumber installationNumber
     * @param topologyAssetTypeCode topologyAssetTypeCode
     * @param topologyAssetId topologyAssetId
     * @param topologyAssetCodeSnapshot topologyAssetCodeSnapshot
     * @param installedAt installedAt
     * @param commissionedAt commissionedAt
     * @param installedByPartyId installedByPartyId
     * @param installedByNameSnapshot installedByNameSnapshot
     * @param commissioningDocumentId commissioningDocumentId
     * @param notes notes
     * @param createdAt createdAt
     * @param updatedAt updatedAt
     */
    public record AssetInstallation(
            String id,
        String maintainableAssetId,
        String installationNumber,
        String topologyAssetTypeCode,
        String topologyAssetId,
        String topologyAssetCodeSnapshot,
        Instant installedAt,
        Instant commissionedAt,
        String installedByPartyId,
        String installedByNameSnapshot,
        String commissioningDocumentId,
        String notes,
        Instant createdAt,
        Instant updatedAt
    ) {

        public AssetInstallation {
        id = normalize(id);
        maintainableAssetId = normalize(maintainableAssetId);
        installationNumber = normalize(installationNumber);
        topologyAssetTypeCode = normalize(topologyAssetTypeCode);
        topologyAssetId = normalize(topologyAssetId);
        topologyAssetCodeSnapshot = normalize(topologyAssetCodeSnapshot);
        installedByPartyId = normalize(installedByPartyId);
        installedByNameSnapshot = normalize(installedByNameSnapshot);
        commissioningDocumentId = normalize(commissioningDocumentId);
        notes = normalize(notes);
        }

        private static String normalize(String value) {
            if (value == null || value.isBlank()) {
                return null;
            }
            return value.trim();
        }
    }
