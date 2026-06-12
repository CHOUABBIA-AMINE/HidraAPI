/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : RecordAuditEventCommand
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Application
 * @Module      : audit
 * @Package     : dz.sh.hidra.modules.audit.application.command
 *
 * @Description : Command to record an audit event.
 *
 */
package dz.sh.hidra.modules.audit.application.command;

import dz.sh.hidra.modules.audit.domain.value.AuditActorType;
import dz.sh.hidra.modules.audit.domain.value.AuditOperation;

import java.time.Instant;

/**
 * Command to record an audit event.
 */
public record RecordAuditEventCommand(
        String eventTypeId,
        String eventCategoryId,
        String severityId,
        String sourceModule,
        String sourceComponent,
        String sourceEventId,
        String actionCode,
        String actionLabelSnapshot,
        String actorId,
        AuditActorType actorType,
        String actorDisplayNameSnapshot,
        String actorUsernameSnapshot,
        String targetModule,
        String targetType,
        String targetId,
        String targetCodeSnapshot,
        String targetLabelSnapshot,
        AuditOperation operation,
        String decisionCode,
        String reasonId,
        String reasonText,
        String workflowInstanceId,
        String workflowTaskId,
        String workflowActionId,
        String requestId,
        String correlationId,
        String causationId,
        Instant occurredAt,
        String payloadJson
) {
}
