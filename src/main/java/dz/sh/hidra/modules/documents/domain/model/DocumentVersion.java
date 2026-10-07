/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : DocumentVersion
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-07
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

import dz.sh.hidra.modules.documents.domain.exception.InvalidDocumentValueException;
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
        // HRA-051 required: id
        if (id == null || id.isBlank()) {
            throw new InvalidDocumentValueException("DocumentVersion id must not be blank.");
        }
        // HRA-051 required: documentId
        if (documentId == null || documentId.isBlank()) {
            throw new InvalidDocumentValueException("DocumentVersion document id must not be blank.");
        }
        // HRA-051 required: storageObjectId
        if (storageObjectId == null || storageObjectId.isBlank()) {
            throw new InvalidDocumentValueException("DocumentVersion storage object id must not be blank.");
        }
        // HRA-051 required: versionStatus
        if (versionStatus == null) {
            throw new InvalidDocumentValueException("DocumentVersion version status must not be null.");
        }
        // HRA-051 required: uploadedByActorId
        if (uploadedByActorId == null || uploadedByActorId.isBlank()) {
            throw new InvalidDocumentValueException("DocumentVersion uploaded by actor id must not be blank.");
        }
        // HRA-051 required: uploadedAt
        if (uploadedAt == null) {
            throw new InvalidDocumentValueException("DocumentVersion uploaded at must not be null.");
        }
        // HRA-051 order: effectiveFrom <= effectiveTo
        if (effectiveFrom != null && effectiveTo != null && effectiveTo.isBefore(effectiveFrom)) {
            throw new InvalidDocumentValueException("DocumentVersion effective to must not be before effective from.");
        }
        // HRA-051 self-reference: supersededByVersionId != id
        if (id != null && supersededByVersionId != null && supersededByVersionId.equals(id)) {
            throw new InvalidDocumentValueException("DocumentVersion superseded by version id must not reference itself.");
        }

        if(versionNumber<1)throw new InvalidDocumentValueException("Version number must be positive.");
        for(String value:new String[]{mimeType,originalFilename,checksumAlgorithm,checksumValue,uploadedByDisplayNameSnapshot})
            if(value==null || value.isBlank())throw new InvalidDocumentValueException("Required upload metadata must not be blank.");
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
        if(id.equals(supersededByVersionId))throw new InvalidDocumentValueException("Version cannot supersede itself.");
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
