/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ReportInputSnapshot
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : reporting
 * @Package     : dz.sh.hidra.modules.reporting.domain.model
 *
 * @Description : Frozen input context of a generated report.
 *
 */
package dz.sh.hidra.modules.reporting.domain.model;

import dz.sh.hidra.modules.reporting.domain.value.*;
import java.time.Instant;

    /**
     * Frozen input context of a generated report.
     *
         * @param id id
     * @param reportRunId reportRunId
     * @param snapshotType snapshotType
     * @param sourceModule sourceModule
     * @param sourceReferenceId sourceReferenceId
     * @param sourceReferenceCode sourceReferenceCode
     * @param sourceReferenceLabel sourceReferenceLabel
     * @param sourceVersion sourceVersion
     * @param snapshotHash snapshotHash
     * @param capturedAt capturedAt
     * @param metadataJson metadataJson
     */
    public record ReportInputSnapshot(
            String id,
        String reportRunId,
        ReportSnapshotType snapshotType,
        String sourceModule,
        String sourceReferenceId,
        String sourceReferenceCode,
        String sourceReferenceLabel,
        String sourceVersion,
        String snapshotHash,
        Instant capturedAt,
        String metadataJson
    ) {

        public ReportInputSnapshot {
        id = normalize(id);
        reportRunId = normalize(reportRunId);
        sourceModule = normalize(sourceModule);
        sourceReferenceId = normalize(sourceReferenceId);
        sourceReferenceCode = normalize(sourceReferenceCode);
        sourceReferenceLabel = normalize(sourceReferenceLabel);
        sourceVersion = normalize(sourceVersion);
        snapshotHash = normalize(snapshotHash);
        metadataJson = normalize(metadataJson);
        }

        private static String normalize(String value) {
            if (value == null || value.isBlank()) {
                return null;
            }
            return value.trim();
        }
    }
