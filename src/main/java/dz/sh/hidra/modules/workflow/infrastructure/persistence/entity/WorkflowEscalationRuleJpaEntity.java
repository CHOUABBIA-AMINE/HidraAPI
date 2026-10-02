/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : WorkflowEscalationRuleJpaEntity
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : workflow
 * @Package     : dz.sh.hidra.modules.workflow.infrastructure.persistence.entity
 *
 * @Description : Database-backed JPA entity for WorkflowEscalationRule.
 *
 */
package dz.sh.hidra.modules.workflow.infrastructure.persistence.entity;

import java.time.Instant;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

    /**
     * Database-backed JPA entity for WorkflowEscalationRule.
     */
    @Entity
    @Table(name = "hidra_workflow_escalation_rule")
    public class WorkflowEscalationRuleJpaEntity {

        @Id
    @Column(name = "id", nullable = false, length = 80)
    private String id;

    @Column(name = "definition_id", nullable = false, length = 80)
    private String definitionId;

    @Column(name = "step_id", nullable = false, length = 80)
    private String stepId;

    @Column(name = "after_duration_seconds", nullable = false)
    private int afterDurationSeconds;

    @Column(name = "escalate_to_actor_id", nullable = true, length = 80)
    private String escalateToActorId;

    @Column(name = "escalate_to_actor_username_snapshot", nullable = true, length = 120)
    private String escalateToActorUsernameSnapshot;

    @Column(name = "escalate_to_actor_display_name_snapshot", nullable = true, length = 160)
    private String escalateToActorDisplayNameSnapshot;

    @Column(name = "escalate_to_actor_role_code_snapshot", nullable = true, length = 80)
    private String escalateToActorRoleCodeSnapshot;

    @Column(name = "escalate_to_organization_unit_id", nullable = true, length = 80)
    private String escalateToOrganizationUnitId;

    @Column(name = "escalate_to_organization_unit_name_snapshot", nullable = true, length = 160)
    private String escalateToOrganizationUnitNameSnapshot;

    @Column(name = "escalate_to_organization_role_code_snapshot", nullable = true, length = 80)
    private String escalateToOrganizationRoleCodeSnapshot;

    @Column(name = "escalation_reason_id", nullable = true, length = 80)
    private String escalationReasonId;

    @Column(name = "repeatable", nullable = false)
    private boolean repeatable;

    @Column(name = "max_repeat_count", nullable = true)
    private Integer maxRepeatCount;

    @Column(name = "escalation_level", nullable = true)
    private Integer escalationLevel;

    @Column(name = "business_hours_calendar_id", nullable = true, length = 80)
    private String businessHoursCalendarId;

    @Column(name = "active", nullable = false)
    private boolean active;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

        protected WorkflowEscalationRuleJpaEntity() {
            // Required by JPA.
        }

        public WorkflowEscalationRuleJpaEntity(
                String id,
            String definitionId,
            String stepId,
            int afterDurationSeconds,
            String escalateToActorId,
            String escalateToActorUsernameSnapshot,
            String escalateToActorDisplayNameSnapshot,
            String escalateToActorRoleCodeSnapshot,
            String escalateToOrganizationUnitId,
            String escalateToOrganizationUnitNameSnapshot,
            String escalateToOrganizationRoleCodeSnapshot,
            String escalationReasonId,
            boolean repeatable,
            Integer maxRepeatCount,
            Integer escalationLevel,
            String businessHoursCalendarId,
            boolean active,
            Instant createdAt,
            Instant updatedAt
        ) {
            this.id = id;
        this.definitionId = definitionId;
        this.stepId = stepId;
        this.afterDurationSeconds = afterDurationSeconds;
        this.escalateToActorId = escalateToActorId;
        this.escalateToActorUsernameSnapshot = escalateToActorUsernameSnapshot;
        this.escalateToActorDisplayNameSnapshot = escalateToActorDisplayNameSnapshot;
        this.escalateToActorRoleCodeSnapshot = escalateToActorRoleCodeSnapshot;
        this.escalateToOrganizationUnitId = escalateToOrganizationUnitId;
        this.escalateToOrganizationUnitNameSnapshot = escalateToOrganizationUnitNameSnapshot;
        this.escalateToOrganizationRoleCodeSnapshot = escalateToOrganizationRoleCodeSnapshot;
        this.escalationReasonId = escalationReasonId;
        this.repeatable = repeatable;
        this.maxRepeatCount = maxRepeatCount;
        this.escalationLevel = escalationLevel;
        this.businessHoursCalendarId = businessHoursCalendarId;
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


    public int afterDurationSeconds() {
        return afterDurationSeconds;
    }


    public String escalateToActorId() {
        return escalateToActorId;
    }


    public String escalateToActorUsernameSnapshot() {
        return escalateToActorUsernameSnapshot;
    }


    public String escalateToActorDisplayNameSnapshot() {
        return escalateToActorDisplayNameSnapshot;
    }


    public String escalateToActorRoleCodeSnapshot() {
        return escalateToActorRoleCodeSnapshot;
    }


    public String escalateToOrganizationUnitId() {
        return escalateToOrganizationUnitId;
    }


    public String escalateToOrganizationUnitNameSnapshot() {
        return escalateToOrganizationUnitNameSnapshot;
    }


    public String escalateToOrganizationRoleCodeSnapshot() {
        return escalateToOrganizationRoleCodeSnapshot;
    }


    public String escalationReasonId() {
        return escalationReasonId;
    }


    public boolean repeatable() {
        return repeatable;
    }


    public Integer maxRepeatCount() {
        return maxRepeatCount;
    }


    public Integer escalationLevel() {
        return escalationLevel;
    }


    public String businessHoursCalendarId() {
        return businessHoursCalendarId;
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
