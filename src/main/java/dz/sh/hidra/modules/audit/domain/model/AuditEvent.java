/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AuditEvent
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : audit
 * @Package     : dz.sh.hidra.modules.audit.domain.model
 *
 * @Description : Canonical append-only audit record.
 *
 */
package dz.sh.hidra.modules.audit.domain.model;

import dz.sh.hidra.modules.audit.domain.value.*;
import java.time.Instant;

    /**
     * Canonical append-only audit record.
     *
         * @param id id
     * @param eventTypeId eventTypeId
     * @param eventCategoryId eventCategoryId
     * @param severityId severityId
     * @param sourceModule sourceModule
     * @param sourceComponent sourceComponent
     * @param sourceEventId sourceEventId
     * @param actionCode actionCode
     * @param actionLabelSnapshot actionLabelSnapshot
     * @param eventStatus eventStatus
     * @param actorId actorId
     * @param actorType actorType
     * @param actorDisplayNameSnapshot actorDisplayNameSnapshot
     * @param actorUsernameSnapshot actorUsernameSnapshot
     * @param actorRoleCodeSnapshot actorRoleCodeSnapshot
     * @param organizationUnitId organizationUnitId
     * @param organizationUnitCodeSnapshot organizationUnitCodeSnapshot
     * @param organizationUnitNameSnapshot organizationUnitNameSnapshot
     * @param targetModule targetModule
     * @param targetType targetType
     * @param targetId targetId
     * @param targetCodeSnapshot targetCodeSnapshot
     * @param targetLabelSnapshot targetLabelSnapshot
     * @param operation operation
     * @param decisionCode decisionCode
     * @param reasonId reasonId
     * @param reasonText reasonText
     * @param commentText commentText
     * @param workflowInstanceId workflowInstanceId
     * @param workflowTaskId workflowTaskId
     * @param workflowActionId workflowActionId
     * @param workflowFromState workflowFromState
     * @param workflowToState workflowToState
     * @param requestId requestId
     * @param correlationId correlationId
     * @param causationId causationId
     * @param ipAddressMasked ipAddressMasked
     * @param userAgentSnapshot userAgentSnapshot
     * @param sourceSystemCode sourceSystemCode
     * @param occurredAt occurredAt
     * @param recordedAt recordedAt
     * @param retentionPolicyId retentionPolicyId
     * @param hashValue hashValue
     * @param previousHashValue previousHashValue
     * @param payloadJson payloadJson
     */
    public record AuditEvent(
            String id,
        String eventTypeId,
        String eventCategoryId,
        String severityId,
        String sourceModule,
        String sourceComponent,
        String sourceEventId,
        String actionCode,
        String actionLabelSnapshot,
        AuditEventStatus eventStatus,
        String actorId,
        AuditActorType actorType,
        String actorDisplayNameSnapshot,
        String actorUsernameSnapshot,
        String actorRoleCodeSnapshot,
        String organizationUnitId,
        String organizationUnitCodeSnapshot,
        String organizationUnitNameSnapshot,
        String targetModule,
        String targetType,
        String targetId,
        String targetCodeSnapshot,
        String targetLabelSnapshot,
        AuditOperation operation,
        String decisionCode,
        String reasonId,
        String reasonText,
        String commentText,
        String workflowInstanceId,
        String workflowTaskId,
        String workflowActionId,
        String workflowFromState,
        String workflowToState,
        String requestId,
        String correlationId,
        String causationId,
        String ipAddressMasked,
        String userAgentSnapshot,
        String sourceSystemCode,
        Instant occurredAt,
        Instant recordedAt,
        String retentionPolicyId,
        String hashValue,
        String previousHashValue,
        String payloadJson
    ) {

        public AuditEvent {
        id = normalize(id);
        eventTypeId = normalize(eventTypeId);
        eventCategoryId = normalize(eventCategoryId);
        severityId = normalize(severityId);
        sourceModule = normalize(sourceModule);
        sourceComponent = normalize(sourceComponent);
        sourceEventId = normalize(sourceEventId);
        actionCode = normalize(actionCode);
        actionLabelSnapshot = normalize(actionLabelSnapshot);
        actorId = normalize(actorId);
        actorDisplayNameSnapshot = normalize(actorDisplayNameSnapshot);
        actorUsernameSnapshot = normalize(actorUsernameSnapshot);
        actorRoleCodeSnapshot = normalize(actorRoleCodeSnapshot);
        organizationUnitId = normalize(organizationUnitId);
        organizationUnitCodeSnapshot = normalize(organizationUnitCodeSnapshot);
        organizationUnitNameSnapshot = normalize(organizationUnitNameSnapshot);
        targetModule = normalize(targetModule);
        targetType = normalize(targetType);
        targetId = normalize(targetId);
        targetCodeSnapshot = normalize(targetCodeSnapshot);
        targetLabelSnapshot = normalize(targetLabelSnapshot);
        decisionCode = normalize(decisionCode);
        reasonId = normalize(reasonId);
        reasonText = normalize(reasonText);
        commentText = normalize(commentText);
        workflowInstanceId = normalize(workflowInstanceId);
        workflowTaskId = normalize(workflowTaskId);
        workflowActionId = normalize(workflowActionId);
        workflowFromState = normalize(workflowFromState);
        workflowToState = normalize(workflowToState);
        requestId = normalize(requestId);
        correlationId = normalize(correlationId);
        causationId = normalize(causationId);
        ipAddressMasked = normalize(ipAddressMasked);
        userAgentSnapshot = normalize(userAgentSnapshot);
        sourceSystemCode = normalize(sourceSystemCode);
        retentionPolicyId = normalize(retentionPolicyId);
        hashValue = normalize(hashValue);
        previousHashValue = normalize(previousHashValue);
        payloadJson = normalize(payloadJson);
        }
        public boolean appendOnlyRecorded() {
            return eventStatus == AuditEventStatus.RECORDED
                    || eventStatus == AuditEventStatus.SEALED
                    || eventStatus == AuditEventStatus.EXPORT_LOCKED;
        }
        private static String normalize(String value) {
            if (value == null || value.isBlank()) {
                return null;
            }
            return value.trim();
        }
    }
