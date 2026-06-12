/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : WorkflowTransitionJpaEntity
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : JpaEntity
 * @Layer       : Infrastructure
 * @Module      : workflow
 * @Package     : dz.sh.hidra.modules.workflow.infrastructure.persistence.entity
 *
 * @Description : Database-backed JPA entity for WorkflowTransition.
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
     * Database-backed JPA entity for WorkflowTransition.
     */
    @Entity
    @Table(name = "hidra_workflow_transition")
    public class WorkflowTransitionJpaEntity {

        @Id
    @Column(name = "id", nullable = false, length = 80)
    private String id;

    @Column(name = "definition_id", nullable = false, length = 80)
    private String definitionId;

    @Column(name = "from_step_id", nullable = false, length = 80)
    private String fromStepId;

    @Column(name = "to_step_id", nullable = false, length = 80)
    private String toStepId;

    @Enumerated(EnumType.STRING)
    @Column(name = "decision", nullable = false, length = 40)
    private WorkflowDecision decision;

    @Column(name = "reason_required", nullable = false)
    private boolean reasonRequired;

    @Column(name = "comment_required", nullable = false)
    private boolean commentRequired;

    @Column(name = "condition_expression", nullable = true, columnDefinition = "text")
    private String conditionExpression;

    @Column(name = "required_permission_code", nullable = true, length = 120)
    private String requiredPermissionCode;

    @Column(name = "target_module_callback", nullable = true, length = 120)
    private String targetModuleCallback;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

        protected WorkflowTransitionJpaEntity() {
            // Required by JPA.
        }

        public WorkflowTransitionJpaEntity(
                String id,
            String definitionId,
            String fromStepId,
            String toStepId,
            WorkflowDecision decision,
            boolean reasonRequired,
            boolean commentRequired,
            String conditionExpression,
            String requiredPermissionCode,
            String targetModuleCallback,
            Instant createdAt,
            Instant updatedAt
        ) {
            this.id = id;
        this.definitionId = definitionId;
        this.fromStepId = fromStepId;
        this.toStepId = toStepId;
        this.decision = decision;
        this.reasonRequired = reasonRequired;
        this.commentRequired = commentRequired;
        this.conditionExpression = conditionExpression;
        this.requiredPermissionCode = requiredPermissionCode;
        this.targetModuleCallback = targetModuleCallback;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        }


    public String id() {
        return id;
    }


    public String definitionId() {
        return definitionId;
    }


    public String fromStepId() {
        return fromStepId;
    }


    public String toStepId() {
        return toStepId;
    }


    public WorkflowDecision decision() {
        return decision;
    }


    public boolean reasonRequired() {
        return reasonRequired;
    }


    public boolean commentRequired() {
        return commentRequired;
    }


    public String conditionExpression() {
        return conditionExpression;
    }


    public String requiredPermissionCode() {
        return requiredPermissionCode;
    }


    public String targetModuleCallback() {
        return targetModuleCallback;
    }


    public Instant createdAt() {
        return createdAt;
    }


    public Instant updatedAt() {
        return updatedAt;
    }

    }
