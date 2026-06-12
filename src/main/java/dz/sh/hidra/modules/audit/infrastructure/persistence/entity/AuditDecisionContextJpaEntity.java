/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AuditDecisionContextJpaEntity
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : JpaEntity
 * @Layer       : Infrastructure
 * @Module      : audit
 * @Package     : dz.sh.hidra.modules.audit.infrastructure.persistence.entity
 *
 * @Description : Database-backed JPA entity for AuditDecisionContext.
 *
 */
package dz.sh.hidra.modules.audit.infrastructure.persistence.entity;

import java.time.Instant;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

    /**
     * Database-backed JPA entity for AuditDecisionContext.
     */
    @Entity
    @Table(name = "hidra_audit_decision_context")
    public class AuditDecisionContextJpaEntity {

        @Id
    @Column(name = "id", nullable = false, length = 80)
    private String id;

    @Column(name = "audit_event_id", nullable = false, length = 80)
    private String auditEventId;

    @Column(name = "decision_code", nullable = false, length = 120)
    private String decisionCode;

    @Column(name = "decision_type_id", nullable = true, length = 80)
    private String decisionTypeId;

    @Column(name = "reason_id", nullable = true, length = 80)
    private String reasonId;

    @Column(name = "reason_text", nullable = true, length = 1000)
    private String reasonText;

    @Column(name = "comment_text", nullable = true, length = 2000)
    private String commentText;

    @Column(name = "policy_code", nullable = true, length = 120)
    private String policyCode;

    @Column(name = "workflow_instance_id", nullable = true, length = 120)
    private String workflowInstanceId;

    @Column(name = "workflow_task_id", nullable = true, length = 120)
    private String workflowTaskId;

    @Column(name = "workflow_action_id", nullable = true, length = 120)
    private String workflowActionId;

    @Column(name = "from_state", nullable = true, length = 80)
    private String fromState;

    @Column(name = "to_state", nullable = true, length = 80)
    private String toState;

    @Column(name = "decided_at", nullable = false)
    private Instant decidedAt;

        protected AuditDecisionContextJpaEntity() {
            // Required by JPA.
        }

        public AuditDecisionContextJpaEntity(
                String id,
            String auditEventId,
            String decisionCode,
            String decisionTypeId,
            String reasonId,
            String reasonText,
            String commentText,
            String policyCode,
            String workflowInstanceId,
            String workflowTaskId,
            String workflowActionId,
            String fromState,
            String toState,
            Instant decidedAt
        ) {
            this.id = id;
        this.auditEventId = auditEventId;
        this.decisionCode = decisionCode;
        this.decisionTypeId = decisionTypeId;
        this.reasonId = reasonId;
        this.reasonText = reasonText;
        this.commentText = commentText;
        this.policyCode = policyCode;
        this.workflowInstanceId = workflowInstanceId;
        this.workflowTaskId = workflowTaskId;
        this.workflowActionId = workflowActionId;
        this.fromState = fromState;
        this.toState = toState;
        this.decidedAt = decidedAt;
        }


    public String id() {
        return id;
    }


    public String auditEventId() {
        return auditEventId;
    }


    public String decisionCode() {
        return decisionCode;
    }


    public String decisionTypeId() {
        return decisionTypeId;
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


    public String policyCode() {
        return policyCode;
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


    public String fromState() {
        return fromState;
    }


    public String toState() {
        return toState;
    }


    public Instant decidedAt() {
        return decidedAt;
    }

    }
