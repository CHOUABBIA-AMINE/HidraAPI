/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ReportOutputArtifact
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : reporting
 * @Package     : dz.sh.hidra.modules.reporting.domain.model
 *
 * @Description : Generated report file or export artifact.
 *
 */
package dz.sh.hidra.modules.reporting.domain.model;

import dz.sh.hidra.modules.reporting.domain.value.*;
import java.time.Instant;

    /**
     * Generated report file or export artifact.
     *
         * @param id id
     * @param reportRunId reportRunId
     * @param artifactType artifactType
     * @param format format
     * @param fileName fileName
     * @param mimeType mimeType
     * @param storageObjectReferenceId storageObjectReferenceId
     * @param documentReferenceId documentReferenceId
     * @param checksum checksum
     * @param sizeBytes sizeBytes
     * @param generatedAt generatedAt
     * @param expiresAt expiresAt
     * @param createdAt createdAt
     */
    public record ReportOutputArtifact(
            String id,
        String reportRunId,
        ReportArtifactType artifactType,
        ReportFormat format,
        String fileName,
        String mimeType,
        String storageObjectReferenceId,
        String documentReferenceId,
        String checksum,
        Long sizeBytes,
        Instant generatedAt,
        Instant expiresAt,
        Instant createdAt
    ) {

        public ReportOutputArtifact {
        id = normalize(id);
        reportRunId = normalize(reportRunId);
        fileName = normalize(fileName);
        mimeType = normalize(mimeType);
        storageObjectReferenceId = normalize(storageObjectReferenceId);
        documentReferenceId = normalize(documentReferenceId);
        checksum = normalize(checksum);
        }
        public boolean reproducibleArtifact() {
            return checksum != null && !checksum.isBlank()
                    && (storageObjectReferenceId != null || documentReferenceId != null);
        }
        private static String normalize(String value) {
            if (value == null || value.isBlank()) {
                return null;
            }
            return value.trim();
        }
    }
