/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AssetModel
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-28
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : assets
 * @Package     : dz.sh.hidra.modules.assets.domain.model
 *
 * @Description : Asset model reference.
 *
 */
package dz.sh.hidra.modules.assets.domain.model;

import dz.sh.hidra.modules.assets.domain.exception.InvalidAssetsValueException;
import java.time.Instant;

    /**
     * Asset model reference.
     *
         * @param id id
     * @param modelCode modelCode
     * @param modelName modelName
     * @param assetTypeId assetTypeId
     * @param manufacturerPartyId manufacturerPartyId
     * @param manufacturerNameSnapshot manufacturerNameSnapshot
     * @param technicalDescription technicalDescription
     * @param active active
     * @param createdAt createdAt
     * @param updatedAt updatedAt
     */
    public record AssetModel(
            String id,
        String modelCode,
        String modelName,
        String assetTypeId,
        String manufacturerPartyId,
        String manufacturerNameSnapshot,
        String technicalDescription,
        boolean active,
        Instant createdAt,
        Instant updatedAt
    ) {

        public AssetModel {
        // HRA-051 required: id
        if (id == null || id.isBlank()) {
            throw new InvalidAssetsValueException("AssetModel id must not be blank.");
        }
        // HRA-051 required: modelCode
        if (modelCode == null || modelCode.isBlank()) {
            throw new InvalidAssetsValueException("AssetModel model code must not be blank.");
        }
        // HRA-051 required: assetTypeId
        if (assetTypeId == null || assetTypeId.isBlank()) {
            throw new InvalidAssetsValueException("AssetModel asset type id must not be blank.");
        }

        id = normalize(id);
        modelCode = normalize(modelCode);
        modelName = normalize(modelName);
        assetTypeId = normalize(assetTypeId);
        manufacturerPartyId = normalize(manufacturerPartyId);
        manufacturerNameSnapshot = normalize(manufacturerNameSnapshot);
        technicalDescription = normalize(technicalDescription);
        }

        private static String normalize(String value) {
            if (value == null || value.isBlank()) {
                return null;
            }
            return value.trim();
        }
    }
