/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AuditEventJpaEntity
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : audit
 * @Package     : dz.sh.hidra.modules.audit.infrastructure.persistence.entity
 *
 * @Description : Database-backed JPA entity for AuditEvent.
 *
 */
package dz.sh.hidra.modules.audit.infrastructure.persistence.entity;

import dz.sh.hidra.modules.audit.domain.value.*;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.Instant;

    /**
     * Database-backed JPA entity for AuditEvent.
     */
    @Entity
    @Table(name = "hidra_audit_event")
    public class AuditEventJpaEntity {

        @Id
    @Column(name = "id", nullable = false, length = 80)
    private String id;

    @Column(name = "event_type_id", nullable = false, length = 80)
    private String eventTypeId;

    @Column(name = "event_category_id", nullable = false, length = 80)
    private String eventCategoryId;

    @Column(name = "severity_id", nullable = true, length = 80)
    private String severityId;

    @Column(name = "source_module", nullable = false, length = 80)
    private String sourceModule;

    @Column(name = "source_component", nullable = true, length = 120)
    private String sourceComponent;

    @Column(name = "source_event_id", nullable = true, length = 120)
    private String sourceEventId;

    @Column(name = "action_code", nullable = false, length = 120)
    private String actionCode;

    @Column(name = "action_label_snapshot", nullable = true, length = 240)
    private String actionLabelSnapshot;

    @Enumerated(EnumType.STRING)
    @Column(name = "event_status", nullable = false, length = 40)
    private AuditEventStatus eventStatus;

    @Column(name = "actor_id", nullable = true, length = 120)
    private String actorId;

    @Enumerated(EnumType.STRING)
    @Column(name = "actor_type", nullable = false, length = 40)
    private AuditActorType actorType;

    @Column(name = "actor_display_name_snapshot", nullable = true, length = 160)
    private String actorDisplayNameSnapshot;

    @Column(name = "actor_username_snapshot", nullable = true, length = 120)
    private String actorUsernameSnapshot;

    @Column(name = "actor_role_code_snapshot", nullable = true, length = 120)
    private String actorRoleCodeSnapshot;

    @Column(name = "organization_unit_id", nullable = true, length = 120)
    private String organizationUnitId;

    @Column(name = "organization_unit_code_snapshot", nullable = true, length = 120)
    private String organizationUnitCodeSnapshot;

    @Column(name = "organization_unit_name_snapshot", nullable = true, length = 160)
    private String organizationUnitNameSnapshot;

    @Column(name = "target_module", nullable = false, length = 80)
    private String targetModule;

    @Column(name = "target_type", nullable = false, length = 120)
    private String targetType;

    @Column(name = "target_id", nullable = false, length = 120)
    private String targetId;

    @Column(name = "target_code_snapshot", nullable = true, length = 120)
    private String targetCodeSnapshot;

    @Column(name = "target_label_snapshot", nullable = true, length = 240)
    private String targetLabelSnapshot;

    @Enumerated(EnumType.STRING)
    @Column(name = "operation", nullable = false, length = 60)
    private AuditOperation operation;

    @Column(name = "decision_code", nullable = true, length = 120)
    private String decisionCode;

    @Column(name = "reason_id", nullable = true, length = 80)
    private String reasonId;

    @Column(name = "reason_text", nullable = true, length = 1000)
    private String reasonText;

    @Column(name = "comment_text", nullable = true, length = 2000)
    private String commentText;

    @Column(name = "workflow_instance_id", nullable = true, length = 120)
    private String workflowInstanceId;

    @Column(name = "workflow_task_id", nullable = true, length = 120)
    private String workflowTaskId;

    @Column(name = "workflow_action_id", nullable = true, length = 120)
    private String workflowActionId;

    @Column(name = "workflow_from_state", nullable = true, length = 80)
    private String workflowFromState;

    @Column(name = "workflow_to_state", nullable = true, length = 80)
    private String workflowToState;

    @Column(name = "request_id", nullable = true, length = 120)
    private String requestId;

    @Column(name = "correlation_id", nullable = true, length = 120)
    private String correlationId;

    @Column(name = "causation_id", nullable = true, length = 120)
    private String causationId;

    @Column(name = "ip_address_masked", nullable = true, length = 80)
    private String ipAddressMasked;

    @Column(name = "user_agent_snapshot", nullable = true, length = 500)
    private String userAgentSnapshot;

    @Column(name = "source_system_code", nullable = true, length = 120)
    private String sourceSystemCode;

    @Column(name = "occurred_at", nullable = false)
    private Instant occurredAt;

    @Column(name = "recorded_at", nullable = false)
    private Instant recordedAt;

    @Column(name = "retention_policy_id", nullable = true, length = 80)
    private String retentionPolicyId;

    @Column(name = "hash_value", nullable = true, length = 256)
    private String hashValue;

    @Column(name = "previous_hash_value", nullable = true, length = 256)
    private String previousHashValue;

    @Column(name = "payload_json", nullable = true, columnDefinition = "jsonb")
    private String payloadJson;

        protected AuditEventJpaEntity() {
            // Required by JPA.
        }

        public AuditEventJpaEntity(
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
            this.id = id;
        this.eventTypeId = eventTypeId;
        this.eventCategoryId = eventCategoryId;
        this.severityId = severityId;
        this.sourceModule = sourceModule;
        this.sourceComponent = sourceComponent;
        this.sourceEventId = sourceEventId;
        this.actionCode = actionCode;
        this.actionLabelSnapshot = actionLabelSnapshot;
        this.eventStatus = eventStatus;
        this.actorId = actorId;
        this.actorType = actorType;
        this.actorDisplayNameSnapshot = actorDisplayNameSnapshot;
        this.actorUsernameSnapshot = actorUsernameSnapshot;
        this.actorRoleCodeSnapshot = actorRoleCodeSnapshot;
        this.organizationUnitId = organizationUnitId;
        this.organizationUnitCodeSnapshot = organizationUnitCodeSnapshot;
        this.organizationUnitNameSnapshot = organizationUnitNameSnapshot;
        this.targetModule = targetModule;
        this.targetType = targetType;
        this.targetId = targetId;
        this.targetCodeSnapshot = targetCodeSnapshot;
        this.targetLabelSnapshot = targetLabelSnapshot;
        this.operation = operation;
        this.decisionCode = decisionCode;
        this.reasonId = reasonId;
        this.reasonText = reasonText;
        this.commentText = commentText;
        this.workflowInstanceId = workflowInstanceId;
        this.workflowTaskId = workflowTaskId;
        this.workflowActionId = workflowActionId;
        this.workflowFromState = workflowFromState;
        this.workflowToState = workflowToState;
        this.requestId = requestId;
        this.correlationId = correlationId;
        this.causationId = causationId;
        this.ipAddressMasked = ipAddressMasked;
        this.userAgentSnapshot = userAgentSnapshot;
        this.sourceSystemCode = sourceSystemCode;
        this.occurredAt = occurredAt;
        this.recordedAt = recordedAt;
        this.retentionPolicyId = retentionPolicyId;
        this.hashValue = hashValue;
        this.previousHashValue = previousHashValue;
        this.payloadJson = payloadJson;
        }


    public String id() {
        return id;
    }


    public String eventTypeId() {
        return eventTypeId;
    }


    public String eventCategoryId() {
        return eventCategoryId;
    }


    public String severityId() {
        return severityId;
    }


    public String sourceModule() {
        return sourceModule;
    }


    public String sourceComponent() {
        return sourceComponent;
    }


    public String sourceEventId() {
        return sourceEventId;
    }


    public String actionCode() {
        return actionCode;
    }


    public String actionLabelSnapshot() {
        return actionLabelSnapshot;
    }


    public AuditEventStatus eventStatus() {
        return eventStatus;
    }


    public String actorId() {
        return actorId;
    }


    public AuditActorType actorType() {
        return actorType;
    }


    public String actorDisplayNameSnapshot() {
        return actorDisplayNameSnapshot;
    }


    public String actorUsernameSnapshot() {
        return actorUsernameSnapshot;
    }


    public String actorRoleCodeSnapshot() {
        return actorRoleCodeSnapshot;
    }


    public String organizationUnitId() {
        return organizationUnitId;
    }


    public String organizationUnitCodeSnapshot() {
        return organizationUnitCodeSnapshot;
    }


    public String organizationUnitNameSnapshot() {
        return organizationUnitNameSnapshot;
    }


    public String targetModule() {
        return targetModule;
    }


    public String targetType() {
        return targetType;
    }


    public String targetId() {
        return targetId;
    }


    public String targetCodeSnapshot() {
        return targetCodeSnapshot;
    }


    public String targetLabelSnapshot() {
        return targetLabelSnapshot;
    }


    public AuditOperation operation() {
        return operation;
    }


    public String decisionCode() {
        return decisionCode;
    }


    public String reasonId() {
        return reasonId;
    }


    public String reasonText() {
        return reasonText;
    }


    public String commentText() {
        return commentText;
    }


    public String workflowInstanceId() {
        return workflowInstanceId;
    }


    public String workflowTaskId() {
        return workflowTaskId;
    }


    public String workflowActionId() {
        return workflowActionId;
    }


    public String workflowFromState() {
        return workflowFromState;
    }


    public String workflowToState() {
        return workflowToState;
    }


    public String requestId() {
        return requestId;
    }


    public String correlationId() {
        return correlationId;
    }


    public String causationId() {
        return causationId;
    }


    public String ipAddressMasked() {
        return ipAddressMasked;
    }


    public String userAgentSnapshot() {
        return userAgentSnapshot;
    }


    public String sourceSystemCode() {
        return sourceSystemCode;
    }


    public Instant occurredAt() {
        return occurredAt;
    }


    public Instant recordedAt() {
        return recordedAt;
    }


    public String retentionPolicyId() {
        return retentionPolicyId;
    }


    public String hashValue() {
        return hashValue;
    }


    public String previousHashValue() {
        return previousHashValue;
    }


    public String payloadJson() {
        return payloadJson;
    }

    }
