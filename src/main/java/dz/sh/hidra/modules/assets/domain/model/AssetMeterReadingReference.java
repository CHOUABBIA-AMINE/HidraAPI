/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AssetMeterReadingReference
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
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
