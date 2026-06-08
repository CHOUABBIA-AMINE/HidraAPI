/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : WorkflowDescription
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : workflow
 * @Package     : dz.sh.hidra.modules.workflow.domain.value
 *
 * @Description : Workflow description value object.
 *
 */
package dz.sh.hidra.modules.workflow.domain.value;

import dz.sh.hidra.kernel.domain.exception.InvalidValueObjectException;
import dz.sh.hidra.kernel.domain.model.ValueObject;

/**
 * Optional workflow description value object.
 *
 * @param value description value, or null when absent
 */
public record WorkflowDescription(String value) implements ValueObject {

    public WorkflowDescription {
        value = normalize(value);
    }

    public static WorkflowDescription of(String value) {
        return new WorkflowDescription(value);
    }

    public boolean isPresent() {
        return value != null;
    }

    private static String normalize(String value) {
        if (value == null || value.isBlank()) {
            return null;
        }

        String normalized = value.trim();

        if (normalized.length() > 500) {
            throw new InvalidValueObjectException("WorkflowDescription length must not exceed 500 characters.");
        }

        return normalized;
    }
}
