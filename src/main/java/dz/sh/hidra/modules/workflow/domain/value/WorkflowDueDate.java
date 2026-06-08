/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : WorkflowDueDate
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : workflow
 * @Package     : dz.sh.hidra.modules.workflow.domain.value
 *
 * @Description : Workflow due date value object.
 *
 */
package dz.sh.hidra.modules.workflow.domain.value;

import java.time.Instant;
import java.util.Objects;

import dz.sh.hidra.kernel.domain.model.ValueObject;

/**
 * Workflow task due date.
 *
 * @param value due instant
 */
public record WorkflowDueDate(Instant value) implements ValueObject {

    public WorkflowDueDate {
        value = Objects.requireNonNull(value, "WorkflowDueDate value must not be null.");
    }

    public static WorkflowDueDate of(Instant value) {
        return new WorkflowDueDate(value);
    }

    public boolean isOverdueAt(Instant referenceTime) {
        Objects.requireNonNull(referenceTime, "referenceTime must not be null.");
        return value.isBefore(referenceTime);
    }

    public boolean isOverdue() {
        return isOverdueAt(Instant.now());
    }
}
