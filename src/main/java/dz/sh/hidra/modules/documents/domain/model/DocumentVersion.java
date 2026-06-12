/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : DocumentVersion
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : documents
 * @Package     : dz.sh.hidra.modules.documents.domain.model
 *
 * @Description : Immutable version metadata for a document file revision.
 *
 */
package dz.sh.hidra.modules.documents.domain.model;

import dz.sh.hidra.modules.documents.domain.value.*;
import java.time.Instant;
import java.time.LocalDate;

    /**
     * Immutable version metadata for a document file revision.
     *
         * @param id id
     * @param documentId documentId
     * @param versionNumber versionNumber
     * @param versionLabel versionLabel
     * @param titleAr titleAr
     * @param titleFr titleFr
     * @param titleEn titleEn
     * @param description description
     * @param storageObjectId storageObjectId
     * @param mimeType mimeType
     * @param originalFilename originalFilename
     * @param fileExtension fileExtension
     * @param fileSizeBytes fileSizeBytes
     * @param checksumAlgorithm checksumAlgorithm
     * @param checksumValue checksumValue
     * @param languageCode languageCode
     * @param documentDate documentDate
     * @param effectiveFrom effectiveFrom
     * @param effectiveTo effectiveTo
     * @param versionStatus versionStatus
     * @param uploadedByActorId uploadedByActorId
     * @param uploadedByDisplayNameSnapshot uploadedByDisplayNameSnapshot
     * @param uploadedAt uploadedAt
     * @param approvedByWorkflowInstanceId approvedByWorkflowInstanceId
     * @param approvedAt approvedAt
     * @param supersededByVersionId supersededByVersionId
     */
    public record DocumentVersion(
            String id,
        String documentId,
        int versionNumber,
        String versionLabel,
        String titleAr,
        String titleFr,
        String titleEn,
        String description,
        String storageObjectId,
        String mimeType,
        String originalFilename,
        String fileExtension,
        long fileSizeBytes,
        String checksumAlgorithm,
        String checksumValue,
        String languageCode,
        LocalDate documentDate,
        LocalDate effectiveFrom,
        LocalDate effectiveTo,
        DocumentVersionStatus versionStatus,
        String uploadedByActorId,
        String uploadedByDisplayNameSnapshot,
        Instant uploadedAt,
        String approvedByWorkflowInstanceId,
        Instant approvedAt,
        String supersededByVersionId
    ) {

        public DocumentVersion {
        id = normalize(id);
        documentId = normalize(documentId);
        versionLabel = normalize(versionLabel);
        titleAr = normalize(titleAr);
        titleFr = normalize(titleFr);
        titleEn = normalize(titleEn);
        description = normalize(description);
        storageObjectId = normalize(storageObjectId);
        mimeType = normalize(mimeType);
        originalFilename = normalize(originalFilename);
        fileExtension = normalize(fileExtension);
        checksumAlgorithm = normalize(checksumAlgorithm);
        checksumValue = normalize(checksumValue);
        languageCode = normalize(languageCode);
        uploadedByActorId = normalize(uploadedByActorId);
        uploadedByDisplayNameSnapshot = normalize(uploadedByDisplayNameSnapshot);
        approvedByWorkflowInstanceId = normalize(approvedByWorkflowInstanceId);
        supersededByVersionId = normalize(supersededByVersionId);
        }
        public boolean immutableVersion() {
            return versionStatus == DocumentVersionStatus.APPROVED
                    || versionStatus == DocumentVersionStatus.CURRENT
                    || versionStatus == DocumentVersionStatus.SUPERSEDED
                    || versionStatus == DocumentVersionStatus.ARCHIVED;
        }
        private static String normalize(String value) {
            if (value == null || value.isBlank()) {
                return null;
            }
            return value.trim();
        }
    }
