/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : WorkflowAuditOutboxReferenceJpaEntity
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : workflow
 * @Package     : dz.sh.hidra.modules.workflow.infrastructure.persistence.entity
 *
 * @Description : Database-backed JPA entity for WorkflowAuditOutboxReference.
 *
 */
package dz.sh.hidra.modules.workflow.infrastructure.persistence.entity;

import dz.sh.hidra.modules.workflow.domain.value.*;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.Instant;

    /**
     * Database-backed JPA entity for WorkflowAuditOutboxReference.
     */
    @Entity
    @Table(name = "hidra_workflow_audit_outbox_reference")
    public class WorkflowAuditOutboxReferenceJpaEntity {

        @Id
    @Column(name = "id", nullable = false, length = 80)
    private String id;

    @Column(name = "instance_id", nullable = false, length = 80)
    private String instanceId;

    @Column(name = "task_id", nullable = true, length = 80)
    private String taskId;

    @Column(name = "action_id", nullable = true, length = 80)
    private String actionId;

    @Column(name = "event_type", nullable = false, length = 120)
    private String eventType;

    @Column(name = "outbox_event_id", nullable = true, length = 120)
    private String outboxEventId;

    @Column(name = "emitted_at", nullable = false)
    private Instant emittedAt;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 40)
    private WorkflowAuditOutboxStatus status;

    @Column(name = "failure_reason", nullable = true, length = 1000)
    private String failureReason;

        protected WorkflowAuditOutboxReferenceJpaEntity() {
            // Required by JPA.
        }

        public WorkflowAuditOutboxReferenceJpaEntity(
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
            this.id = id;
        this.instanceId = instanceId;
        this.taskId = taskId;
        this.actionId = actionId;
        this.eventType = eventType;
        this.outboxEventId = outboxEventId;
        this.emittedAt = emittedAt;
        this.status = status;
        this.failureReason = failureReason;
        }


    public String id() {
        return id;
    }


    public String instanceId() {
        return instanceId;
    }


    public String taskId() {
        return taskId;
    }


    public String actionId() {
        return actionId;
    }


    public String eventType() {
        return eventType;
    }


    public String outboxEventId() {
        return outboxEventId;
    }


    public Instant emittedAt() {
        return emittedAt;
    }


    public WorkflowAuditOutboxStatus status() {
        return status;
    }


    public String failureReason() {
        return failureReason;
    }

    }
