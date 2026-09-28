/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : MonitoringAcknowledgement
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-28
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

import dz.sh.hidra.modules.monitoring.domain.exception.InvalidMonitoringValueException;
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
        // HRA-051 required: id
        if (id == null || id.isBlank()) {
            throw new InvalidMonitoringValueException("MonitoringAcknowledgement id must not be blank.");
        }
        // HRA-051 required: targetId
        if (targetId == null || targetId.isBlank()) {
            throw new InvalidMonitoringValueException("MonitoringAcknowledgement target id must not be blank.");
        }
        // HRA-051 required: acknowledgementStatus
        if (acknowledgementStatus == null) {
            throw new InvalidMonitoringValueException("MonitoringAcknowledgement acknowledgement status must not be null.");
        }
        // HRA-051 required: acknowledgedByActorId
        if (acknowledgedByActorId == null || acknowledgedByActorId.isBlank()) {
            throw new InvalidMonitoringValueException("MonitoringAcknowledgement acknowledged by actor id must not be blank.");
        }
        // HRA-051 required: acknowledgedAt
        if (acknowledgedAt == null) {
            throw new InvalidMonitoringValueException("MonitoringAcknowledgement acknowledged at must not be null.");
        }

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
