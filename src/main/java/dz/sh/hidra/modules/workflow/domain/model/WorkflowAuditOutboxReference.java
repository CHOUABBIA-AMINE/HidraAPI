/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : WorkflowAuditOutboxReference
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : workflow
 * @Package     : dz.sh.hidra.modules.workflow.domain.model
 *
 * @Description : Reference to audit-ready event emission.
 *
 */
package dz.sh.hidra.modules.workflow.domain.model;

import dz.sh.hidra.modules.workflow.domain.value.*;
import java.time.Instant;

    /**
     * Reference to audit-ready event emission.
     *
         * @param id id
     * @param instanceId instanceId
     * @param taskId taskId
     * @param actionId actionId
     * @param eventType eventType
     * @param outboxEventId outboxEventId
     * @param emittedAt emittedAt
     * @param status status
     * @param failureReason failureReason
     */
    public record WorkflowAuditOutboxReference(
            String id,
        String instanceId,
        String taskId,
        String actionId,
        String eventType,
        String outboxEventId,
        Instant emittedAt,
        WorkflowAuditOutboxStatus status,
        String failureReason
    ) {

        public WorkflowAuditOutboxReference {
        id = normalize(id);
        instanceId = normalize(instanceId);
        taskId = normalize(taskId);
        actionId = normalize(actionId);
        eventType = normalize(eventType);
        outboxEventId = normalize(outboxEventId);
        failureReason = normalize(failureReason);
        }

        private static String normalize(String value) {
            if (value == null || value.isBlank()) {
                return null;
            }
            return value.trim();
        }
    }
