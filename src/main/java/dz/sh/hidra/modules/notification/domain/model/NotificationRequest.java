/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : NotificationRequest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : notification
 * @Package     : dz.sh.hidra.modules.notification.domain.model
 *
 * @Description : Request to notify recipients about a source event or target object.
 *
 */
package dz.sh.hidra.modules.notification.domain.model;

import dz.sh.hidra.modules.notification.domain.value.*;
import java.time.Instant;

    /**
     * Request to notify recipients about a source event or target object.
     *
         * @param id id
     * @param sourceModule sourceModule
     * @param sourceEventType sourceEventType
     * @param sourceEventId sourceEventId
     * @param targetType targetType
     * @param targetId targetId
     * @param targetCodeSnapshot targetCodeSnapshot
     * @param targetLabelSnapshot targetLabelSnapshot
     * @param categoryId categoryId
     * @param priorityId priorityId
     * @param policyId policyId
     * @param templateId templateId
     * @param templateVersionId templateVersionId
     * @param requestedByActorId requestedByActorId
     * @param requestedByDisplayNameSnapshot requestedByDisplayNameSnapshot
     * @param requestedAt requestedAt
     * @param correlationId correlationId
     * @param requestId requestId
     * @param status status
     * @param expiresAt expiresAt
     * @param createdAt createdAt
     * @param updatedAt updatedAt
     */
    public record NotificationRequest(
            String id,
        String sourceModule,
        String sourceEventType,
        String sourceEventId,
        String targetType,
        String targetId,
        String targetCodeSnapshot,
        String targetLabelSnapshot,
        String categoryId,
        String priorityId,
        String policyId,
        String templateId,
        String templateVersionId,
        String requestedByActorId,
        String requestedByDisplayNameSnapshot,
        Instant requestedAt,
        String correlationId,
        String requestId,
        NotificationRequestStatus status,
        Instant expiresAt,
        Instant createdAt,
        Instant updatedAt
    ) {

        public NotificationRequest {
        id = normalize(id);
        sourceModule = normalize(sourceModule);
        sourceEventType = normalize(sourceEventType);
        sourceEventId = normalize(sourceEventId);
        targetType = normalize(targetType);
        targetId = normalize(targetId);
        targetCodeSnapshot = normalize(targetCodeSnapshot);
        targetLabelSnapshot = normalize(targetLabelSnapshot);
        categoryId = normalize(categoryId);
        priorityId = normalize(priorityId);
        policyId = normalize(policyId);
        templateId = normalize(templateId);
        templateVersionId = normalize(templateVersionId);
        requestedByActorId = normalize(requestedByActorId);
        requestedByDisplayNameSnapshot = normalize(requestedByDisplayNameSnapshot);
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
