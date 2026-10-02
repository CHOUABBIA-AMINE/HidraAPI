/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AuditExportRequest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-28
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : audit
 * @Package     : dz.sh.hidra.modules.audit.domain.model
 *
 * @Description : Controlled audit export request.
 *
 */
package dz.sh.hidra.modules.audit.domain.model;

import dz.sh.hidra.modules.audit.domain.exception.InvalidAuditValueException;
import dz.sh.hidra.modules.audit.domain.value.*;
import java.time.Instant;

    /**
     * Controlled audit export request.
     *
         * @param id id
     * @param requestedByActorId requestedByActorId
     * @param requestedByDisplayNameSnapshot requestedByDisplayNameSnapshot
     * @param purposeId purposeId
     * @param filterJson filterJson
     * @param format format
     * @param status status
     * @param workflowInstanceId workflowInstanceId
     * @param resultDocumentReferenceId resultDocumentReferenceId
     * @param recordCount recordCount
     * @param checksum checksum
     * @param requestedAt requestedAt
     * @param completedAt completedAt
     * @param expiresAt expiresAt
     */
    public record AuditExportRequest(
            String id,
        String requestedByActorId,
        String requestedByDisplayNameSnapshot,
        String purposeId,
        String filterJson,
        String format,
        AuditExportStatus status,
        String workflowInstanceId,
        String resultDocumentReferenceId,
        Integer recordCount,
        String checksum,
        Instant requestedAt,
        Instant completedAt,
        Instant expiresAt
    ) {

        public AuditExportRequest {
        // HRA-051 required: id
        if (id == null || id.isBlank()) {
            throw new InvalidAuditValueException("AuditExportRequest id must not be blank.");
        }
        // HRA-051 required: requestedByActorId
        if (requestedByActorId == null || requestedByActorId.isBlank()) {
            throw new InvalidAuditValueException("AuditExportRequest requested by actor id must not be blank.");
        }
        // HRA-051 required: purposeId
        if (purposeId == null || purposeId.isBlank()) {
            throw new InvalidAuditValueException("AuditExportRequest purpose id must not be blank.");
        }
        // HRA-051 required: status
        if (status == null) {
            throw new InvalidAuditValueException("AuditExportRequest status must not be null.");
        }
        // HRA-051 required: requestedAt
        if (requestedAt == null) {
            throw new InvalidAuditValueException("AuditExportRequest requested at must not be null.");
        }

        id = normalize(id);
        requestedByActorId = normalize(requestedByActorId);
        requestedByDisplayNameSnapshot = normalize(requestedByDisplayNameSnapshot);
        purposeId = normalize(purposeId);
        filterJson = normalize(filterJson);
        format = normalize(format);
        workflowInstanceId = normalize(workflowInstanceId);
        resultDocumentReferenceId = normalize(resultDocumentReferenceId);
        checksum = normalize(checksum);
        }

        private static String normalize(String value) {
            if (value == null || value.isBlank()) {
                return null;
            }
            return value.trim();
        }
    }
