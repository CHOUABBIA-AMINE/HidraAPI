/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : DocumentRetentionRecord
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
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
