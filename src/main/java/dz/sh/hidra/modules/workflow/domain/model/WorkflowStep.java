/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : WorkflowStep
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Class
 * @Layer       : Domain
 * @Module      : workflow
 * @Package     : dz.sh.hidra.modules.workflow.domain.model
 *
 * @Description : Workflow definition step domain entity.
 *
 */
package dz.sh.hidra.modules.workflow.domain.model;

import java.time.Instant;
import java.util.Objects;

import dz.sh.hidra.kernel.domain.exception.BusinessRuleViolationException;
import dz.sh.hidra.kernel.domain.model.Entity;
import dz.sh.hidra.modules.workflow.domain.value.WorkflowDefinitionId;
import dz.sh.hidra.modules.workflow.domain.value.WorkflowLocalizedName;
import dz.sh.hidra.modules.workflow.domain.value.WorkflowStepId;

/**
 * Workflow definition step domain entity.
 *
 * <p>Business role:
 * Represents one ordered step in a workflow definition.
 */
public final class WorkflowStep implements Entity<WorkflowStepId> {

    private final WorkflowStepId id;
    private final WorkflowDefinitionId definitionId;
    private final String code;
    private final WorkflowLocalizedName name;
    private final int stepOrder;
    private final boolean mandatory;
    private final Instant createdAt;
    private final Instant updatedAt;

    private WorkflowStep(
            WorkflowStepId id,
            WorkflowDefinitionId definitionId,
            String code,
            WorkflowLocalizedName name,
            int stepOrder,
            boolean mandatory,
            Instant createdAt,
            Instant updatedAt) {

        this.id = Objects.requireNonNull(id, "Workflow step id must not be null.");
        this.definitionId = Objects.requireNonNull(definitionId, "Workflow step definitionId must not be null.");
        this.code = normalizeCode(code);
        this.name = Objects.requireNonNull(name, "Workflow step name must not be null.");
        this.stepOrder = requireNonNegative(stepOrder, "Workflow step order");
        this.mandatory = mandatory;
        this.createdAt = Objects.requireNonNull(createdAt, "Workflow step createdAt must not be null.");
        this.updatedAt = Objects.requireNonNull(updatedAt, "Workflow step updatedAt must not be null.");

        if (this.updatedAt.isBefore(this.createdAt)) {
            throw new BusinessRuleViolationException("Workflow step updatedAt must not be before createdAt.");
        }
    }

    public static WorkflowStep create(
            WorkflowDefinitionId definitionId,
            String code,
            WorkflowLocalizedName name,
            int stepOrder,
            boolean mandatory) {

        Instant now = Instant.now();
        return new WorkflowStep(
                WorkflowStepId.newId(),
                definitionId,
                code,
                name,
                stepOrder,
                mandatory,
                now,
                now);
    }

    public static WorkflowStep restore(
            WorkflowStepId id,
            WorkflowDefinitionId definitionId,
            String code,
            WorkflowLocalizedName name,
            int stepOrder,
            boolean mandatory,
            Instant createdAt,
            Instant updatedAt) {

        return new WorkflowStep(id, definitionId, code, name, stepOrder, mandatory, createdAt, updatedAt);
    }

    @Override
    public WorkflowStepId id() {
        return id;
    }

    public WorkflowDefinitionId definitionId() {
        return definitionId;
    }

    public String code() {
        return code;
    }

    public WorkflowLocalizedName name() {
        return name;
    }

    public int stepOrder() {
        return stepOrder;
    }

    public boolean mandatory() {
        return mandatory;
    }

    public Instant createdAt() {
        return createdAt;
    }

    public Instant updatedAt() {
        return updatedAt;
    }

    private static String normalizeCode(String value) {
        if (value == null || value.isBlank()) {
            throw new BusinessRuleViolationException("Workflow step code must not be null or blank.");
        }
        String normalized = value.trim().toUpperCase();
        if (normalized.length() > 80) {
            throw new BusinessRuleViolationException("Workflow step code length must not exceed 80 characters.");
        }
        return normalized;
    }

    private static int requireNonNegative(int value, String fieldName) {
        if (value < 0) {
            throw new BusinessRuleViolationException(fieldName + " must be greater than or equal to zero.");
        }
        return value;
    }
}
