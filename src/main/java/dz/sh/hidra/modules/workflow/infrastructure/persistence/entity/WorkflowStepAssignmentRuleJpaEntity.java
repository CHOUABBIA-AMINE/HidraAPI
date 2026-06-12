/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : WorkflowStepAssignmentRuleJpaEntity
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : JpaEntity
 * @Layer       : Infrastructure
 * @Module      : workflow
 * @Package     : dz.sh.hidra.modules.workflow.infrastructure.persistence.entity
 *
 * @Description : Database-backed JPA entity for WorkflowStepAssignmentRule.
 *
 */
package dz.sh.hidra.modules.workflow.infrastructure.persistence.entity;

import java.time.Instant;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

    /**
     * Database-backed JPA entity for WorkflowStepAssignmentRule.
     */
    @Entity
    @Table(name = "hidra_workflow_step_assignment_rule")
    public class WorkflowStepAssignmentRuleJpaEntity {

        @Id
    @Column(name = "id", nullable = false, length = 80)
    private String id;

    @Column(name = "definition_id", nullable = false, length = 80)
    private String definitionId;

    @Column(name = "step_id", nullable = false, length = 80)
    private String stepId;

    @Column(name = "assignment_mode_id", nullable = false, length = 80)
    private String assignmentModeId;

    @Column(name = "actor_id", nullable = true, length = 80)
    private String actorId;

    @Column(name = "role_code", nullable = true, length = 80)
    private String roleCode;

    @Column(name = "organization_unit_id", nullable = true, length = 80)
    private String organizationUnitId;

    @Column(name = "organization_role_code", nullable = true, length = 80)
    private String organizationRoleCode;

    @Column(name = "target_owner_mode", nullable = true, length = 80)
    private String targetOwnerMode;

    @Column(name = "active", nullable = false)
    private boolean active;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

        protected WorkflowStepAssignmentRuleJpaEntity() {
            // Required by JPA.
        }

        public WorkflowStepAssignmentRuleJpaEntity(
                String id,
            String definitionId,
            String stepId,
            String assignmentModeId,
            String actorId,
            String roleCode,
            String organizationUnitId,
            String organizationRoleCode,
            String targetOwnerMode,
            boolean active,
            Instant createdAt,
            Instant updatedAt
        ) {
            this.id = id;
        this.definitionId = definitionId;
        this.stepId = stepId;
        this.assignmentModeId = assignmentModeId;
        this.actorId = actorId;
        this.roleCode = roleCode;
        this.organizationUnitId = organizationUnitId;
        this.organizationRoleCode = organizationRoleCode;
        this.targetOwnerMode = targetOwnerMode;
        this.active = active;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        }


    public String id() {
        return id;
    }


    public String definitionId() {
        return definitionId;
    }


    public String stepId() {
        return stepId;
    }


    public String assignmentModeId() {
        return assignmentModeId;
    }


    public String actorId() {
        return actorId;
    }


    public String roleCode() {
        return roleCode;
    }


    public String organizationUnitId() {
        return organizationUnitId;
    }


    public String organizationRoleCode() {
        return organizationRoleCode;
    }


    public String targetOwnerMode() {
        return targetOwnerMode;
    }


    public boolean active() {
        return active;
    }


    public Instant createdAt() {
        return createdAt;
    }


    public Instant updatedAt() {
        return updatedAt;
    }

    }
