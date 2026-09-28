/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AssetSerialIdentity
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-28
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : assets
 * @Package     : dz.sh.hidra.modules.assets.domain.model
 *
 * @Description : Serial/manufacturer identity.
 *
 */
package dz.sh.hidra.modules.assets.domain.model;

import dz.sh.hidra.modules.assets.domain.exception.InvalidAssetsValueException;
import java.time.Instant;

    /**
     * Serial/manufacturer identity.
     *
         * @param id id
     * @param maintainableAssetId maintainableAssetId
     * @param serialNumber serialNumber
     * @param batchNumber batchNumber
     * @param manufacturerPartNumber manufacturerPartNumber
     * @param nameplateDataJson nameplateDataJson
     * @param manufacturedAt manufacturedAt
     * @param createdAt createdAt
     * @param updatedAt updatedAt
     */
    public record AssetSerialIdentity(
            String id,
        String maintainableAssetId,
        String serialNumber,
        String batchNumber,
        String manufacturerPartNumber,
        String nameplateDataJson,
        Instant manufacturedAt,
        Instant createdAt,
        Instant updatedAt
    ) {

        public AssetSerialIdentity {
        // HRA-051 required: id
        if (id == null || id.isBlank()) {
            throw new InvalidAssetsValueException("AssetSerialIdentity id must not be blank.");
        }
        // HRA-051 required: maintainableAssetId
        if (maintainableAssetId == null || maintainableAssetId.isBlank()) {
            throw new InvalidAssetsValueException("AssetSerialIdentity maintainable asset id must not be blank.");
        }
        // HRA-051 required: serialNumber
        if (serialNumber == null || serialNumber.isBlank()) {
            throw new InvalidAssetsValueException("AssetSerialIdentity serial number must not be blank.");
        }

        id = normalize(id);
        maintainableAssetId = normalize(maintainableAssetId);
        serialNumber = normalize(serialNumber);
        batchNumber = normalize(batchNumber);
        manufacturerPartNumber = normalize(manufacturerPartNumber);
        nameplateDataJson = normalize(nameplateDataJson);
        }

        private static String normalize(String value) {
            if (value == null || value.isBlank()) {
                return null;
            }
            return value.trim();
        }
    }
