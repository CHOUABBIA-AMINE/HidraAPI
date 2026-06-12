/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : WorkflowActionJpaEntity
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : JpaEntity
 * @Layer       : Infrastructure
 * @Module      : workflow
 * @Package     : dz.sh.hidra.modules.workflow.infrastructure.persistence.entity
 *
 * @Description : Database-backed JPA entity for WorkflowAction.
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
     * Database-backed JPA entity for WorkflowAction.
     */
    @Entity
    @Table(name = "hidra_workflow_action")
    public class WorkflowActionJpaEntity {

        @Id
    @Column(name = "id", nullable = false, length = 80)
    private String id;

    @Column(name = "instance_id", nullable = false, length = 80)
    private String instanceId;

    @Column(name = "task_id", nullable = true, length = 80)
    private String taskId;

    @Enumerated(EnumType.STRING)
    @Column(name = "action_type", nullable = false, length = 40)
    private WorkflowActionType actionType;

    @Enumerated(EnumType.STRING)
    @Column(name = "decision", nullable = true, length = 40)
    private WorkflowDecision decision;

    @Column(name = "reason_id", nullable = true, length = 80)
    private String reasonId;

    @Column(name = "decision_note", nullable = true, length = 2000)
    private String decisionNote;

    @Column(name = "comment_text", nullable = true, length = 2000)
    private String commentText;

    @Column(name = "actor_id", nullable = false, length = 80)
    private String actorId;

    @Column(name = "actor_username_snapshot", nullable = true, length = 120)
    private String actorUsernameSnapshot;

    @Column(name = "actor_display_name_snapshot", nullable = false, length = 160)
    private String actorDisplayNameSnapshot;

    @Column(name = "actor_role_code_snapshot", nullable = true, length = 80)
    private String actorRoleCodeSnapshot;

    @Column(name = "organization_unit_id", nullable = true, length = 80)
    private String organizationUnitId;

    @Column(name = "organization_unit_name_snapshot", nullable = true, length = 160)
    private String organizationUnitNameSnapshot;

    @Column(name = "organization_role_code_snapshot", nullable = true, length = 80)
    private String organizationRoleCodeSnapshot;

    @Column(name = "correlation_id", nullable = true, length = 120)
    private String correlationId;

    @Column(name = "action_sequence", nullable = false)
    private long actionSequence;

    @Column(name = "source_system", nullable = true, length = 80)
    private String sourceSystem;

    @Column(name = "ip_address_hash", nullable = true, length = 128)
    private String ipAddressHash;

    @Column(name = "user_agent_hash", nullable = true, length = 128)
    private String userAgentHash;

    @Column(name = "acted_at", nullable = false)
    private Instant actedAt;

        protected WorkflowActionJpaEntity() {
            // Required by JPA.
        }

        public WorkflowActionJpaEntity(
                String id,
            String instanceId,
            String taskId,
            WorkflowActionType actionType,
            WorkflowDecision decision,
            String reasonId,
            String decisionNote,
            String commentText,
            String actorId,
            String actorUsernameSnapshot,
            String actorDisplayNameSnapshot,
            String actorRoleCodeSnapshot,
            String organizationUnitId,
            String organizationUnitNameSnapshot,
            String organizationRoleCodeSnapshot,
            String correlationId,
            long actionSequence,
            String sourceSystem,
            String ipAddressHash,
            String userAgentHash,
            Instant actedAt
        ) {
            this.id = id;
        this.instanceId = instanceId;
        this.taskId = taskId;
        this.actionType = actionType;
        this.decision = decision;
        this.reasonId = reasonId;
        this.decisionNote = decisionNote;
        this.commentText = commentText;
        this.actorId = actorId;
        this.actorUsernameSnapshot = actorUsernameSnapshot;
        this.actorDisplayNameSnapshot = actorDisplayNameSnapshot;
        this.actorRoleCodeSnapshot = actorRoleCodeSnapshot;
        this.organizationUnitId = organizationUnitId;
        this.organizationUnitNameSnapshot = organizationUnitNameSnapshot;
        this.organizationRoleCodeSnapshot = organizationRoleCodeSnapshot;
        this.correlationId = correlationId;
        this.actionSequence = actionSequence;
        this.sourceSystem = sourceSystem;
        this.ipAddressHash = ipAddressHash;
        this.userAgentHash = userAgentHash;
        this.actedAt = actedAt;
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


    public WorkflowActionType actionType() {
        return actionType;
    }


    public WorkflowDecision decision() {
        return decision;
    }


    public String reasonId() {
        return reasonId;
    }


    public String decisionNote() {
        return decisionNote;
    }


    public String commentText() {
        return commentText;
    }


    public String actorId() {
        return actorId;
    }


    public String actorUsernameSnapshot() {
        return actorUsernameSnapshot;
    }


    public String actorDisplayNameSnapshot() {
        return actorDisplayNameSnapshot;
    }


    public String actorRoleCodeSnapshot() {
        return actorRoleCodeSnapshot;
    }


    public String organizationUnitId() {
        return organizationUnitId;
    }


    public String organizationUnitNameSnapshot() {
        return organizationUnitNameSnapshot;
    }


    public String organizationRoleCodeSnapshot() {
        return organizationRoleCodeSnapshot;
    }


    public String correlationId() {
        return correlationId;
    }


    public long actionSequence() {
        return actionSequence;
    }


    public String sourceSystem() {
        return sourceSystem;
    }


    public String ipAddressHash() {
        return ipAddressHash;
    }


    public String userAgentHash() {
        return userAgentHash;
    }


    public Instant actedAt() {
        return actedAt;
    }

    }
