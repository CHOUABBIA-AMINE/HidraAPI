/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : WorkflowInstanceJpaEntity
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : JpaEntity
 * @Layer       : Infrastructure
 * @Module      : workflow
 * @Package     : dz.sh.hidra.modules.workflow.infrastructure.persistence.entity
 *
 * @Description : JPA entity for workflow instances.
 *
 */
package dz.sh.hidra.modules.workflow.infrastructure.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.Instant;

/**
 * JPA entity for workflow instances.
 *
 * <p>Architecture role:
 * Persistence-only workflow representation. It must not be exposed to domain, application, or REST
 * layers and must not contain business behavior.
 */
@Entity
@Table(name = "hidra_workflow_instance")
public class WorkflowInstanceJpaEntity {

    @Id
    @Column(name = "id", nullable = false)
    private String id;

    @Column(name = "definition_id", nullable = false)
    private String definitionId;

    @Column(name = "definition_version", nullable = false)
    private Integer definitionVersion;

    @Column(name = "target_module", nullable = false)
    private String targetModule;

    @Column(name = "target_type_id", nullable = false)
    private String targetTypeId;

    @Column(name = "target_id", nullable = false)
    private String targetId;

    @Column(name = "target_code_snapshot", nullable = true)
    private String targetCodeSnapshot;

    @Column(name = "target_label_snapshot", nullable = true)
    private String targetLabelSnapshot;

    @Column(name = "status", nullable = false)
    private String status;

    @Column(name = "current_step_id", nullable = true)
    private String currentStepId;

    @Column(name = "started_by_actor_id", nullable = false)
    private String startedByActorId;

    @Column(name = "started_by_username_snapshot", nullable = true)
    private String startedByUsernameSnapshot;

    @Column(name = "started_by_display_name_snapshot", nullable = false)
    private String startedByDisplayNameSnapshot;

    @Column(name = "started_by_role_code_snapshot", nullable = true)
    private String startedByRoleCodeSnapshot;

    @Column(name = "started_at", nullable = false)
    private Instant startedAt;

    @Column(name = "completed_at", nullable = true)
    private Instant completedAt;

    @Column(name = "cancelled_at", nullable = true)
    private Instant cancelledAt;

    @Column(name = "correlation_id", nullable = true)
    private String correlationId;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    protected WorkflowInstanceJpaEntity() {
        // Required by JPA.
    }

    public WorkflowInstanceJpaEntity(
            String id,
            String definitionId,
            Integer definitionVersion,
            String targetModule,
            String targetTypeId,
            String targetId,
            String targetCodeSnapshot,
            String targetLabelSnapshot,
            String status,
            String currentStepId,
            String startedByActorId,
            String startedByUsernameSnapshot,
            String startedByDisplayNameSnapshot,
            String startedByRoleCodeSnapshot,
            Instant startedAt,
            Instant completedAt,
            Instant cancelledAt,
            String correlationId,
            Instant createdAt,
            Instant updatedAt) {
        this.id = id;
        this.definitionId = definitionId;
        this.definitionVersion = definitionVersion;
        this.targetModule = targetModule;
        this.targetTypeId = targetTypeId;
        this.targetId = targetId;
        this.targetCodeSnapshot = targetCodeSnapshot;
        this.targetLabelSnapshot = targetLabelSnapshot;
        this.status = status;
        this.currentStepId = currentStepId;
        this.startedByActorId = startedByActorId;
        this.startedByUsernameSnapshot = startedByUsernameSnapshot;
        this.startedByDisplayNameSnapshot = startedByDisplayNameSnapshot;
        this.startedByRoleCodeSnapshot = startedByRoleCodeSnapshot;
        this.startedAt = startedAt;
        this.completedAt = completedAt;
        this.cancelledAt = cancelledAt;
        this.correlationId = correlationId;
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

    public Integer getDefinitionVersion() {
        return definitionVersion;
    }

    public void setDefinitionVersion(Integer definitionVersion) {
        this.definitionVersion = definitionVersion;
    }

    public String getTargetModule() {
        return targetModule;
    }

    public void setTargetModule(String targetModule) {
        this.targetModule = targetModule;
    }

    public String getTargetTypeId() {
        return targetTypeId;
    }

    public void setTargetTypeId(String targetTypeId) {
        this.targetTypeId = targetTypeId;
    }

    public String getTargetId() {
        return targetId;
    }

    public void setTargetId(String targetId) {
        this.targetId = targetId;
    }

    public String getTargetCodeSnapshot() {
        return targetCodeSnapshot;
    }

    public void setTargetCodeSnapshot(String targetCodeSnapshot) {
        this.targetCodeSnapshot = targetCodeSnapshot;
    }

    public String getTargetLabelSnapshot() {
        return targetLabelSnapshot;
    }

    public void setTargetLabelSnapshot(String targetLabelSnapshot) {
        this.targetLabelSnapshot = targetLabelSnapshot;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCurrentStepId() {
        return currentStepId;
    }

    public void setCurrentStepId(String currentStepId) {
        this.currentStepId = currentStepId;
    }

    public String getStartedByActorId() {
        return startedByActorId;
    }

    public void setStartedByActorId(String startedByActorId) {
        this.startedByActorId = startedByActorId;
    }

    public String getStartedByUsernameSnapshot() {
        return startedByUsernameSnapshot;
    }

    public void setStartedByUsernameSnapshot(String startedByUsernameSnapshot) {
        this.startedByUsernameSnapshot = startedByUsernameSnapshot;
    }

    public String getStartedByDisplayNameSnapshot() {
        return startedByDisplayNameSnapshot;
    }

    public void setStartedByDisplayNameSnapshot(String startedByDisplayNameSnapshot) {
        this.startedByDisplayNameSnapshot = startedByDisplayNameSnapshot;
    }

    public String getStartedByRoleCodeSnapshot() {
        return startedByRoleCodeSnapshot;
    }

    public void setStartedByRoleCodeSnapshot(String startedByRoleCodeSnapshot) {
        this.startedByRoleCodeSnapshot = startedByRoleCodeSnapshot;
    }

    public Instant getStartedAt() {
        return startedAt;
    }

    public void setStartedAt(Instant startedAt) {
        this.startedAt = startedAt;
    }

    public Instant getCompletedAt() {
        return completedAt;
    }

    public void setCompletedAt(Instant completedAt) {
        this.completedAt = completedAt;
    }

    public Instant getCancelledAt() {
        return cancelledAt;
    }

    public void setCancelledAt(Instant cancelledAt) {
        this.cancelledAt = cancelledAt;
    }

    public String getCorrelationId() {
        return correlationId;
    }

    public void setCorrelationId(String correlationId) {
        this.correlationId = correlationId;
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
