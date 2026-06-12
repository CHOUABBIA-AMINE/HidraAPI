/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AnalyticsDatasetVersion
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : analytics
 * @Package     : dz.sh.hidra.modules.analytics.domain.model
 *
 * @Description : Versioned dataset release.
 *
 */
package dz.sh.hidra.modules.analytics.domain.model;

import java.math.BigDecimal;
import java.time.Instant;

    /**
     * Versioned dataset release.
     *
         * @param id id
     * @param datasetId datasetId
     * @param versionNumber versionNumber
     * @param schemaHash schemaHash
     * @param dataHash dataHash
     * @param rowCount rowCount
     * @param periodStart periodStart
     * @param periodEnd periodEnd
     * @param qualityScore qualityScore
     * @param published published
     * @param publishedAt publishedAt
     * @param publishedByActorId publishedByActorId
     * @param createdAt createdAt
     */
    public record AnalyticsDatasetVersion(
            String id,
        String datasetId,
        int versionNumber,
        String schemaHash,
        String dataHash,
        Long rowCount,
        Instant periodStart,
        Instant periodEnd,
        BigDecimal qualityScore,
        boolean published,
        Instant publishedAt,
        String publishedByActorId,
        Instant createdAt
    ) {

        public AnalyticsDatasetVersion {
        id = normalize(id);
        datasetId = normalize(datasetId);
        schemaHash = normalize(schemaHash);
        dataHash = normalize(dataHash);
        publishedByActorId = normalize(publishedByActorId);
        }
        public boolean immutableAfterPublication() {
            return published;
        }
        private static String normalize(String value) {
            if (value == null || value.isBlank()) {
                return null;
            }
            return value.trim();
        }
    }
