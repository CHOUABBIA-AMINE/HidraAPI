/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : Document
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-28
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : documents
 * @Package     : dz.sh.hidra.modules.documents.domain.model
 *
 * @Description : Stable business document identity independent from file versions.
 *
 */
package dz.sh.hidra.modules.documents.domain.model;

import dz.sh.hidra.modules.documents.domain.exception.InvalidDocumentValueException;
import dz.sh.hidra.modules.documents.domain.value.*;
import java.time.Instant;

    /**
     * Stable business document identity independent from file versions.
     *
         * @param id id
     * @param code code
     * @param titleAr titleAr
     * @param titleFr titleFr
     * @param titleEn titleEn
     * @param documentTypeId documentTypeId
     * @param documentCategoryId documentCategoryId
     * @param classificationId classificationId
     * @param confidentialityLevel confidentialityLevel
     * @param status status
     * @param currentVersionId currentVersionId
     * @param ownerModule ownerModule
     * @param ownerTargetTypeCode ownerTargetTypeCode
     * @param ownerTargetId ownerTargetId
     * @param ownerTargetCodeSnapshot ownerTargetCodeSnapshot
     * @param ownerTargetLabelSnapshot ownerTargetLabelSnapshot
     * @param createdByActorId createdByActorId
     * @param createdByDisplayNameSnapshot createdByDisplayNameSnapshot
     * @param createdAt createdAt
     * @param updatedAt updatedAt
     * @param archivedAt archivedAt
     */
    public record Document(
            String id,
        String code,
        String titleAr,
        String titleFr,
        String titleEn,
        String documentTypeId,
        String documentCategoryId,
        String classificationId,
        int confidentialityLevel,
        DocumentStatus status,
        String currentVersionId,
        String ownerModule,
        String ownerTargetTypeCode,
        String ownerTargetId,
        String ownerTargetCodeSnapshot,
        String ownerTargetLabelSnapshot,
        String createdByActorId,
        String createdByDisplayNameSnapshot,
        Instant createdAt,
        Instant updatedAt,
        Instant archivedAt
    ) {

        public Document {
        // HRA-051 required: id
        if (id == null || id.isBlank()) {
            throw new InvalidDocumentValueException("Document id must not be blank.");
        }
        // HRA-051 required: code
        if (code == null || code.isBlank()) {
            throw new InvalidDocumentValueException("Document code must not be blank.");
        }
        // HRA-051 required: documentTypeId
        if (documentTypeId == null || documentTypeId.isBlank()) {
            throw new InvalidDocumentValueException("Document document type id must not be blank.");
        }
        // HRA-051 required: classificationId
        if (classificationId == null || classificationId.isBlank()) {
            throw new InvalidDocumentValueException("Document classification id must not be blank.");
        }
        // HRA-051 required: status
        if (status == null) {
            throw new InvalidDocumentValueException("Document status must not be null.");
        }
        // HRA-051 required: createdByActorId
        if (createdByActorId == null || createdByActorId.isBlank()) {
            throw new InvalidDocumentValueException("Document created by actor id must not be blank.");
        }

        id = normalize(id);
        code = normalize(code);
        titleAr = normalize(titleAr);
        titleFr = normalize(titleFr);
        titleEn = normalize(titleEn);
        documentTypeId = normalize(documentTypeId);
        documentCategoryId = normalize(documentCategoryId);
        classificationId = normalize(classificationId);
        currentVersionId = normalize(currentVersionId);
        ownerModule = normalize(ownerModule);
        ownerTargetTypeCode = normalize(ownerTargetTypeCode);
        ownerTargetId = normalize(ownerTargetId);
        ownerTargetCodeSnapshot = normalize(ownerTargetCodeSnapshot);
        ownerTargetLabelSnapshot = normalize(ownerTargetLabelSnapshot);
        createdByActorId = normalize(createdByActorId);
        createdByDisplayNameSnapshot = normalize(createdByDisplayNameSnapshot);
        }
        public boolean physicallyProtected() {
            return status == DocumentStatus.APPROVED
                    || status == DocumentStatus.ARCHIVED
                    || status == DocumentStatus.OBSOLETE;
        }
        private static String normalize(String value) {
            if (value == null || value.isBlank()) {
                return null;
            }
            return value.trim();
        }
    }
