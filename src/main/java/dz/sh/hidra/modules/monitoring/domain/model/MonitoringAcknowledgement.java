/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : MonitoringAcknowledgement
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : monitoring
 * @Package     : dz.sh.hidra.modules.monitoring.domain.model
 *
 * @Description : Operator acknowledgement of monitoring result.
 *
 */
package dz.sh.hidra.modules.monitoring.domain.model;

import dz.sh.hidra.modules.monitoring.domain.value.*;
import java.time.Instant;

    /**
     * Operator acknowledgement of monitoring result.
     *
         * @param id id
     * @param targetType targetType
     * @param targetId targetId
     * @param acknowledgementStatus acknowledgementStatus
     * @param acknowledgedByActorId acknowledgedByActorId
     * @param acknowledgedAt acknowledgedAt
     * @param comment comment
     * @param workflowInstanceId workflowInstanceId
     * @param correlationId correlationId
     */
    public record MonitoringAcknowledgement(
            String id,
        String targetType,
        String targetId,
        AcknowledgementStatus acknowledgementStatus,
        String acknowledgedByActorId,
        Instant acknowledgedAt,
        String comment,
        String workflowInstanceId,
        String correlationId
    ) {

        public MonitoringAcknowledgement {
        id = normalize(id);
        targetType = normalize(targetType);
        targetId = normalize(targetId);
        acknowledgedByActorId = normalize(acknowledgedByActorId);
        comment = normalize(comment);
        workflowInstanceId = normalize(workflowInstanceId);
        correlationId = normalize(correlationId);
        }

        private static String normalize(String value) {
            if (value == null || value.isBlank()) {
                return null;
            }
            return value.trim();
        }
    }
