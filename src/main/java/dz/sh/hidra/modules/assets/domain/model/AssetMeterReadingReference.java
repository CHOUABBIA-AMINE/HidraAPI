/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AssetMeterReadingReference
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-28
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : assets
 * @Package     : dz.sh.hidra.modules.assets.domain.model
 *
 * @Description : Reference to meter/telemetry reading.
 *
 */
package dz.sh.hidra.modules.assets.domain.model;

import dz.sh.hidra.modules.assets.domain.exception.InvalidAssetsValueException;
import dz.sh.hidra.modules.assets.domain.value.*;
import java.time.Instant;
import java.math.BigDecimal;

    /**
     * Reference to meter/telemetry reading.
     *
         * @param id id
     * @param maintainableAssetId maintainableAssetId
     * @param readingType readingType
     * @param readingReferenceId readingReferenceId
     * @param readingCodeSnapshot readingCodeSnapshot
     * @param readingValueSnapshot readingValueSnapshot
     * @param unitId unitId
     * @param readingAt readingAt
     * @param createdAt createdAt
     */
    public record AssetMeterReadingReference(
            String id,
        String maintainableAssetId,
        MeterReadingReferenceType readingType,
        String readingReferenceId,
        String readingCodeSnapshot,
        BigDecimal readingValueSnapshot,
        String unitId,
        Instant readingAt,
        Instant createdAt
    ) {

        public AssetMeterReadingReference {
        // HRA-051 required: id
        if (id == null || id.isBlank()) {
            throw new InvalidAssetsValueException("AssetMeterReadingReference id must not be blank.");
        }
        // HRA-051 required: maintainableAssetId
        if (maintainableAssetId == null || maintainableAssetId.isBlank()) {
            throw new InvalidAssetsValueException("AssetMeterReadingReference maintainable asset id must not be blank.");
        }
        // HRA-051 required: readingType
        if (readingType == null) {
            throw new InvalidAssetsValueException("AssetMeterReadingReference reading type must not be null.");
        }
        // HRA-051 required: readingReferenceId
        if (readingReferenceId == null || readingReferenceId.isBlank()) {
            throw new InvalidAssetsValueException("AssetMeterReadingReference reading reference id must not be blank.");
        }

        id = normalize(id);
        maintainableAssetId = normalize(maintainableAssetId);
        readingReferenceId = normalize(readingReferenceId);
        readingCodeSnapshot = normalize(readingCodeSnapshot);
        unitId = normalize(unitId);
        }

        private static String normalize(String value) {
            if (value == null || value.isBlank()) {
                return null;
            }
            return value.trim();
        }
    }
