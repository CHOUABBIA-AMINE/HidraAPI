/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AuditSearchProjection
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-28
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : audit
 * @Package     : dz.sh.hidra.modules.audit.domain.model
 *
 * @Description : Query-optimized rebuildable audit projection.
 *
 */
package dz.sh.hidra.modules.audit.domain.model;

import dz.sh.hidra.modules.audit.domain.exception.InvalidAuditValueException;
import java.time.Instant;

    /**
     * Query-optimized rebuildable audit projection.
     *
         * @param id id
     * @param auditEventId auditEventId
     * @param sourceModule sourceModule
     * @param eventCategoryCode eventCategoryCode
     * @param eventTypeCode eventTypeCode
     * @param actionCode actionCode
     * @param actorId actorId
     * @param actorDisplayNameSearch actorDisplayNameSearch
     * @param organizationUnitId organizationUnitId
     * @param targetModule targetModule
     * @param targetType targetType
     * @param targetId targetId
     * @param targetSearchText targetSearchText
     * @param decisionCode decisionCode
     * @param correlationId correlationId
     * @param requestId requestId
     * @param occurredAt occurredAt
     * @param recordedAt recordedAt
     * @param indexedAt indexedAt
     */
    public record AuditSearchProjection(
            String id,
        String auditEventId,
        String sourceModule,
        String eventCategoryCode,
        String eventTypeCode,
        String actionCode,
        String actorId,
        String actorDisplayNameSearch,
        String organizationUnitId,
        String targetModule,
        String targetType,
        String targetId,
        String targetSearchText,
        String decisionCode,
        String correlationId,
        String requestId,
        Instant occurredAt,
        Instant recordedAt,
        Instant indexedAt
    ) {

        public AuditSearchProjection {
        // HRA-051 required: id
        if (id == null || id.isBlank()) {
            throw new InvalidAuditValueException("AuditSearchProjection id must not be blank.");
        }
        // HRA-051 required: auditEventId
        if (auditEventId == null || auditEventId.isBlank()) {
            throw new InvalidAuditValueException("AuditSearchProjection audit event id must not be blank.");
        }
        // HRA-051 required: eventCategoryCode
        if (eventCategoryCode == null || eventCategoryCode.isBlank()) {
            throw new InvalidAuditValueException("AuditSearchProjection event category code must not be blank.");
        }
        // HRA-051 required: eventTypeCode
        if (eventTypeCode == null || eventTypeCode.isBlank()) {
            throw new InvalidAuditValueException("AuditSearchProjection event type code must not be blank.");
        }
        // HRA-051 required: actionCode
        if (actionCode == null || actionCode.isBlank()) {
            throw new InvalidAuditValueException("AuditSearchProjection action code must not be blank.");
        }
        // HRA-051 required: targetId
        if (targetId == null || targetId.isBlank()) {
            throw new InvalidAuditValueException("AuditSearchProjection target id must not be blank.");
        }
        // HRA-051 required: occurredAt
        if (occurredAt == null) {
            throw new InvalidAuditValueException("AuditSearchProjection occurred at must not be null.");
        }
        // HRA-051 required: recordedAt
        if (recordedAt == null) {
            throw new InvalidAuditValueException("AuditSearchProjection recorded at must not be null.");
        }
        // HRA-051 required: indexedAt
        if (indexedAt == null) {
            throw new InvalidAuditValueException("AuditSearchProjection indexed at must not be null.");
        }

        id = normalize(id);
        auditEventId = normalize(auditEventId);
        sourceModule = normalize(sourceModule);
        eventCategoryCode = normalize(eventCategoryCode);
        eventTypeCode = normalize(eventTypeCode);
        actionCode = normalize(actionCode);
        actorId = normalize(actorId);
        actorDisplayNameSearch = normalize(actorDisplayNameSearch);
        organizationUnitId = normalize(organizationUnitId);
        targetModule = normalize(targetModule);
        targetType = normalize(targetType);
        targetId = normalize(targetId);
        targetSearchText = normalize(targetSearchText);
        decisionCode = normalize(decisionCode);
        correlationId = normalize(correlationId);
        requestId = normalize(requestId);
        }

        private static String normalize(String value) {
            if (value == null || value.isBlank()) {
                return null;
            }
            return value.trim();
        }
    }
