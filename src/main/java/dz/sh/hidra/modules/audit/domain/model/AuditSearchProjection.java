/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AuditSearchProjection
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
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
