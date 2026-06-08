/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : WorkflowName
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : workflow
 * @Package     : dz.sh.hidra.modules.workflow.domain.value
 *
 * @Description : Validated workflow name value object.
 *
 */
package dz.sh.hidra.modules.workflow.domain.value;

import dz.sh.hidra.kernel.domain.exception.InvalidValueObjectException;
import dz.sh.hidra.kernel.domain.model.ValueObject;

/**
 * Workflow display name value object.
 *
 * @param value validated workflow name
 */
public record WorkflowName(String value) implements ValueObject {

    public WorkflowName {
        value = normalize(value);
    }

    public static WorkflowName of(String value) {
        return new WorkflowName(value);
    }

    private static String normalize(String value) {
        if (value == null || value.isBlank()) {
            throw new InvalidValueObjectException("WorkflowName must not be null or blank.");
        }

        String normalized = value.trim();

        if (normalized.length() < 2 || normalized.length() > 160) {
            throw new InvalidValueObjectException("WorkflowName length must be between 2 and 160 characters.");
        }

        return normalized;
    }
}
