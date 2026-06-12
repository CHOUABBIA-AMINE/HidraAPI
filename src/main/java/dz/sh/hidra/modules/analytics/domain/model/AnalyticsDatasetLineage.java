/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AnalyticsDatasetLineage
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : analytics
 * @Package     : dz.sh.hidra.modules.analytics.domain.model
 *
 * @Description : Source lineage for a dataset version.
 *
 */
package dz.sh.hidra.modules.analytics.domain.model;

import java.time.Instant;

    /**
     * Source lineage for a dataset version.
     *
         * @param id id
     * @param datasetVersionId datasetVersionId
     * @param sourceModule sourceModule
     * @param sourceObjectType sourceObjectType
     * @param sourceObjectId sourceObjectId
     * @param sourceSnapshotId sourceSnapshotId
     * @param sourceVersion sourceVersion
     * @param sourcePeriodStart sourcePeriodStart
     * @param sourcePeriodEnd sourcePeriodEnd
     * @param lineageRole lineageRole
     * @param createdAt createdAt
     */
    public record AnalyticsDatasetLineage(
            String id,
        String datasetVersionId,
        String sourceModule,
        String sourceObjectType,
        String sourceObjectId,
        String sourceSnapshotId,
        String sourceVersion,
        Instant sourcePeriodStart,
        Instant sourcePeriodEnd,
        String lineageRole,
        Instant createdAt
    ) {

        public AnalyticsDatasetLineage {
        id = normalize(id);
        datasetVersionId = normalize(datasetVersionId);
        sourceModule = normalize(sourceModule);
        sourceObjectType = normalize(sourceObjectType);
        sourceObjectId = normalize(sourceObjectId);
        sourceSnapshotId = normalize(sourceSnapshotId);
        sourceVersion = normalize(sourceVersion);
        lineageRole = normalize(lineageRole);
        }

        private static String normalize(String value) {
            if (value == null || value.isBlank()) {
                return null;
            }
            return value.trim();
        }
    }
