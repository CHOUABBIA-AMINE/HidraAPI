/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : WorkflowTransitionJpaEntity
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : JpaEntity
 * @Layer       : Infrastructure
 * @Module      : workflow
 * @Package     : dz.sh.hidra.modules.workflow.infrastructure.persistence.entity
 *
 * @Description : JPA entity for workflow definition transitions.
 *
 */
package dz.sh.hidra.modules.workflow.infrastructure.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.Instant;

/**
 * JPA entity for workflow definition transitions.
 *
 * <p>Architecture role:
 * Persistence-only workflow representation. It must not be exposed to domain, application, or REST
 * layers and must not contain business behavior.
 */
@Entity
@Table(name = "hidra_workflow_transition")
public class WorkflowTransitionJpaEntity {

    @Id
    @Column(name = "id", nullable = false)
    private String id;

    @Column(name = "definition_id", nullable = false)
    private String definitionId;

    @Column(name = "from_step_id", nullable = false)
    private String fromStepId;

    @Column(name = "to_step_id", nullable = false)
    private String toStepId;

    @Column(name = "decision", nullable = false)
    private String decision;

    @Column(name = "reason_required", nullable = false)
    private Boolean reasonRequired;

    @Column(name = "comment_required", nullable = false)
    private Boolean commentRequired;

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
            String decision,
            Boolean reasonRequired,
            Boolean commentRequired,
            Instant createdAt,
            Instant updatedAt) {
        this.id = id;
        this.definitionId = definitionId;
        this.fromStepId = fromStepId;
        this.toStepId = toStepId;
        this.decision = decision;
        this.reasonRequired = reasonRequired;
        this.commentRequired = commentRequired;
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

    public String getFromStepId() {
        return fromStepId;
    }

    public void setFromStepId(String fromStepId) {
        this.fromStepId = fromStepId;
    }

    public String getToStepId() {
        return toStepId;
    }

    public void setToStepId(String toStepId) {
        this.toStepId = toStepId;
    }

    public String getDecision() {
        return decision;
    }

    public void setDecision(String decision) {
        this.decision = decision;
    }

    public Boolean getReasonRequired() {
        return reasonRequired;
    }

    public void setReasonRequired(Boolean reasonRequired) {
        this.reasonRequired = reasonRequired;
    }

    public Boolean getCommentRequired() {
        return commentRequired;
    }

    public void setCommentRequired(Boolean commentRequired) {
        this.commentRequired = commentRequired;
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
