/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AnalyticsDatasetVersion
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-28
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

import dz.sh.hidra.modules.analytics.domain.exception.InvalidAnalyticsValueException;
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
        // HRA-051 required: id
        if (id == null || id.isBlank()) {
            throw new InvalidAnalyticsValueException("AnalyticsDatasetVersion id must not be blank.");
        }
        // HRA-051 required: datasetId
        if (datasetId == null || datasetId.isBlank()) {
            throw new InvalidAnalyticsValueException("AnalyticsDatasetVersion dataset id must not be blank.");
        }
        // HRA-051 required: schemaHash
        if (schemaHash == null || schemaHash.isBlank()) {
            throw new InvalidAnalyticsValueException("AnalyticsDatasetVersion schema hash must not be blank.");
        }
        // HRA-051 required: dataHash
        if (dataHash == null || dataHash.isBlank()) {
            throw new InvalidAnalyticsValueException("AnalyticsDatasetVersion data hash must not be blank.");
        }
        // HRA-051 order: periodStart <= periodEnd
        if (periodStart != null && periodEnd != null && periodEnd.isBefore(periodStart)) {
            throw new InvalidAnalyticsValueException("AnalyticsDatasetVersion period end must not be before period start.");
        }

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
