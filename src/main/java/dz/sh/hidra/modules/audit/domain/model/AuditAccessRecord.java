/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AuditAccessRecord
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-28
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : audit
 * @Package     : dz.sh.hidra.modules.audit.domain.model
 *
 * @Description : Append-only access record for audit evidence.
 *
 */
package dz.sh.hidra.modules.audit.domain.model;

import dz.sh.hidra.modules.audit.domain.exception.InvalidAuditValueException;
import dz.sh.hidra.modules.audit.domain.value.*;
import java.time.Instant;

    /**
     * Append-only access record for audit evidence.
     *
         * @param id id
     * @param actorId actorId
     * @param actorDisplayNameSnapshot actorDisplayNameSnapshot
     * @param accessType accessType
     * @param auditEventId auditEventId
     * @param searchFilterHash searchFilterHash
     * @param exportRequestId exportRequestId
     * @param resultCount resultCount
     * @param purposeText purposeText
     * @param accessedAt accessedAt
     * @param correlationId correlationId
     */
    public record AuditAccessRecord(
            String id,
        String actorId,
        String actorDisplayNameSnapshot,
        AuditAccessType accessType,
        String auditEventId,
        String searchFilterHash,
        String exportRequestId,
        Integer resultCount,
        String purposeText,
        Instant accessedAt,
        String correlationId
    ) {

        public AuditAccessRecord {
        // HRA-051 required: id
        if (id == null || id.isBlank()) {
            throw new InvalidAuditValueException("AuditAccessRecord id must not be blank.");
        }
        // HRA-051 required: actorId
        if (actorId == null || actorId.isBlank()) {
            throw new InvalidAuditValueException("AuditAccessRecord actor id must not be blank.");
        }
        // HRA-051 required: accessType
        if (accessType == null) {
            throw new InvalidAuditValueException("AuditAccessRecord access type must not be null.");
        }
        // HRA-051 required: accessedAt
        if (accessedAt == null) {
            throw new InvalidAuditValueException("AuditAccessRecord accessed at must not be null.");
        }

        id = normalize(id);
        actorId = normalize(actorId);
        actorDisplayNameSnapshot = normalize(actorDisplayNameSnapshot);
        auditEventId = normalize(auditEventId);
        searchFilterHash = normalize(searchFilterHash);
        exportRequestId = normalize(exportRequestId);
        purposeText = normalize(purposeText);
        correlationId = normalize(correlationId);
        }

        private static String normalize(String value) {
            if (value == null || value.isBlank()) {
                return null;
            }
            return value.trim();
        }
    }
