/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AssetInstallation
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-28
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

import dz.sh.hidra.modules.assets.domain.exception.InvalidAssetsValueException;
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
        // HRA-051 required: id
        if (id == null || id.isBlank()) {
            throw new InvalidAssetsValueException("AssetInstallation id must not be blank.");
        }
        // HRA-051 required: maintainableAssetId
        if (maintainableAssetId == null || maintainableAssetId.isBlank()) {
            throw new InvalidAssetsValueException("AssetInstallation maintainable asset id must not be blank.");
        }
        // HRA-051 required: installationNumber
        if (installationNumber == null || installationNumber.isBlank()) {
            throw new InvalidAssetsValueException("AssetInstallation installation number must not be blank.");
        }
        // HRA-051 required: topologyAssetTypeCode
        if (topologyAssetTypeCode == null || topologyAssetTypeCode.isBlank()) {
            throw new InvalidAssetsValueException("AssetInstallation topology asset type code must not be blank.");
        }
        // HRA-051 required: topologyAssetId
        if (topologyAssetId == null || topologyAssetId.isBlank()) {
            throw new InvalidAssetsValueException("AssetInstallation topology asset id must not be blank.");
        }
        // HRA-051 required: installedAt
        if (installedAt == null) {
            throw new InvalidAssetsValueException("AssetInstallation installed at must not be null.");
        }

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
