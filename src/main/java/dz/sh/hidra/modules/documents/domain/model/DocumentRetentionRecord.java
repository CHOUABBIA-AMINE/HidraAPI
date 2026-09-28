/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : DocumentRetentionRecord
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-28
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : documents
 * @Package     : dz.sh.hidra.modules.documents.domain.model
 *
 * @Description : Retention and archival control for documents.
 *
 */
package dz.sh.hidra.modules.documents.domain.model;

import dz.sh.hidra.modules.documents.domain.exception.InvalidDocumentValueException;
import java.time.Instant;
import java.time.LocalDate;

    /**
     * Retention and archival control for documents.
     *
         * @param id id
     * @param documentId documentId
     * @param retentionPolicyId retentionPolicyId
     * @param retentionClassId retentionClassId
     * @param retainUntil retainUntil
     * @param legalHold legalHold
     * @param legalHoldReason legalHoldReason
     * @param archivedAt archivedAt
     * @param archiveStorageObjectId archiveStorageObjectId
     * @param disposalAllowedFrom disposalAllowedFrom
     * @param disposedAt disposedAt
     * @param disposedByActorId disposedByActorId
     */
    public record DocumentRetentionRecord(
            String id,
        String documentId,
        String retentionPolicyId,
        String retentionClassId,
        LocalDate retainUntil,
        boolean legalHold,
        String legalHoldReason,
        Instant archivedAt,
        String archiveStorageObjectId,
        LocalDate disposalAllowedFrom,
        Instant disposedAt,
        String disposedByActorId
    ) {

        public DocumentRetentionRecord {
        // HRA-051 required: id
        if (id == null || id.isBlank()) {
            throw new InvalidDocumentValueException("DocumentRetentionRecord id must not be blank.");
        }
        // HRA-051 required: documentId
        if (documentId == null || documentId.isBlank()) {
            throw new InvalidDocumentValueException("DocumentRetentionRecord document id must not be blank.");
        }
        // HRA-051 required: retentionPolicyId
        if (retentionPolicyId == null || retentionPolicyId.isBlank()) {
            throw new InvalidDocumentValueException("DocumentRetentionRecord retention policy id must not be blank.");
        }
        // HRA-051 required: retentionClassId
        if (retentionClassId == null || retentionClassId.isBlank()) {
            throw new InvalidDocumentValueException("DocumentRetentionRecord retention class id must not be blank.");
        }

        id = normalize(id);
        documentId = normalize(documentId);
        retentionPolicyId = normalize(retentionPolicyId);
        retentionClassId = normalize(retentionClassId);
        legalHoldReason = normalize(legalHoldReason);
        archiveStorageObjectId = normalize(archiveStorageObjectId);
        disposedByActorId = normalize(disposedByActorId);
        }

        private static String normalize(String value) {
            if (value == null || value.isBlank()) {
                return null;
            }
            return value.trim();
        }
    }
