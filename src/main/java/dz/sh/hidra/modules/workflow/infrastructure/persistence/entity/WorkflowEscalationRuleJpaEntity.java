/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : WorkflowEscalationRuleJpaEntity
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : JpaEntity
 * @Layer       : Infrastructure
 * @Module      : workflow
 * @Package     : dz.sh.hidra.modules.workflow.infrastructure.persistence.entity
 *
 * @Description : JPA entity for workflow escalation rules.
 *
 */
package dz.sh.hidra.modules.workflow.infrastructure.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.Instant;

/**
 * JPA entity for workflow escalation rules.
 *
 * <p>Architecture role:
 * Persistence-only workflow representation. It must not be exposed to domain, application, or REST
 * layers and must not contain business behavior.
 */
@Entity
@Table(name = "hidra_workflow_escalation_rule")
public class WorkflowEscalationRuleJpaEntity {

    @Id
    @Column(name = "id", nullable = false)
    private String id;

    @Column(name = "definition_id", nullable = false)
    private String definitionId;

    @Column(name = "step_id", nullable = false)
    private String stepId;

    @Column(name = "after_duration_seconds", nullable = false)
    private Integer afterDurationSeconds;

    @Column(name = "escalate_to_actor_id", nullable = true)
    private String escalateToActorId;

    @Column(name = "escalate_to_actor_username_snapshot", nullable = true)
    private String escalateToActorUsernameSnapshot;

    @Column(name = "escalate_to_actor_display_name_snapshot", nullable = true)
    private String escalateToActorDisplayNameSnapshot;

    @Column(name = "escalate_to_actor_role_code_snapshot", nullable = true)
    private String escalateToActorRoleCodeSnapshot;

    @Column(name = "escalate_to_organization_unit_id", nullable = true)
    private String escalateToOrganizationUnitId;

    @Column(name = "escalate_to_organization_unit_name_snapshot", nullable = true)
    private String escalateToOrganizationUnitNameSnapshot;

    @Column(name = "escalate_to_organization_role_code_snapshot", nullable = true)
    private String escalateToOrganizationRoleCodeSnapshot;

    @Column(name = "active", nullable = false)
    private Boolean active;

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
            Integer afterDurationSeconds,
            String escalateToActorId,
            String escalateToActorUsernameSnapshot,
            String escalateToActorDisplayNameSnapshot,
            String escalateToActorRoleCodeSnapshot,
            String escalateToOrganizationUnitId,
            String escalateToOrganizationUnitNameSnapshot,
            String escalateToOrganizationRoleCodeSnapshot,
            Boolean active,
            Instant createdAt,
            Instant updatedAt) {
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
        this.active = active;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDefinitionId() {
        return definitionId;
    }

    public void setDefinitionId(String definitionId) {
        this.definitionId = definitionId;
    }

    public String getStepId() {
        return stepId;
    }

    public void setStepId(String stepId) {
        this.stepId = stepId;
    }

    public Integer getAfterDurationSeconds() {
        return afterDurationSeconds;
    }

    public void setAfterDurationSeconds(Integer afterDurationSeconds) {
        this.afterDurationSeconds = afterDurationSeconds;
    }

    public String getEscalateToActorId() {
        return escalateToActorId;
    }

    public void setEscalateToActorId(String escalateToActorId) {
        this.escalateToActorId = escalateToActorId;
    }

    public String getEscalateToActorUsernameSnapshot() {
        return escalateToActorUsernameSnapshot;
    }

    public void setEscalateToActorUsernameSnapshot(String escalateToActorUsernameSnapshot) {
        this.escalateToActorUsernameSnapshot = escalateToActorUsernameSnapshot;
    }

    public String getEscalateToActorDisplayNameSnapshot() {
        return escalateToActorDisplayNameSnapshot;
    }

    public void setEscalateToActorDisplayNameSnapshot(String escalateToActorDisplayNameSnapshot) {
        this.escalateToActorDisplayNameSnapshot = escalateToActorDisplayNameSnapshot;
    }

    public String getEscalateToActorRoleCodeSnapshot() {
        return escalateToActorRoleCodeSnapshot;
    }

    public void setEscalateToActorRoleCodeSnapshot(String escalateToActorRoleCodeSnapshot) {
        this.escalateToActorRoleCodeSnapshot = escalateToActorRoleCodeSnapshot;
    }

    public String getEscalateToOrganizationUnitId() {
        return escalateToOrganizationUnitId;
    }

    public void setEscalateToOrganizationUnitId(String escalateToOrganizationUnitId) {
        this.escalateToOrganizationUnitId = escalateToOrganizationUnitId;
    }

    public String getEscalateToOrganizationUnitNameSnapshot() {
        return escalateToOrganizationUnitNameSnapshot;
    }

    public void setEscalateToOrganizationUnitNameSnapshot(String escalateToOrganizationUnitNameSnapshot) {
        this.escalateToOrganizationUnitNameSnapshot = escalateToOrganizationUnitNameSnapshot;
    }

    public String getEscalateToOrganizationRoleCodeSnapshot() {
        return escalateToOrganizationRoleCodeSnapshot;
    }

    public void setEscalateToOrganizationRoleCodeSnapshot(String escalateToOrganizationRoleCodeSnapshot) {
        this.escalateToOrganizationRoleCodeSnapshot = escalateToOrganizationRoleCodeSnapshot;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }
}
