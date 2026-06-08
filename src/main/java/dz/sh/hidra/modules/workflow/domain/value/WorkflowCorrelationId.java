/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : WorkflowCorrelationId
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : workflow
 * @Package     : dz.sh.hidra.modules.workflow.domain.value
 *
 * @Description : Workflow correlation identifier value object.
 *
 */
package dz.sh.hidra.modules.workflow.domain.value;

import dz.sh.hidra.kernel.domain.exception.InvalidValueObjectException;
import dz.sh.hidra.kernel.domain.model.ValueObject;

/**
 * Workflow correlation identifier value object.
 *
 * @param value normalized text value
 */
public record WorkflowCorrelationId(String value) implements ValueObject {

    public WorkflowCorrelationId {
        value = normalize(value);
    }

    public static WorkflowCorrelationId of(String value) {
        return new WorkflowCorrelationId(value);
    }

    private static String normalize(String value) {
        if (value == null || value.isBlank()) {
            throw new InvalidValueObjectException("WorkflowCorrelationId must not be null or blank.");
        }

        String normalized = value.trim();

        if (normalized.length() > 120) {
            throw new InvalidValueObjectException("WorkflowCorrelationId length must not exceed 120 characters.");
        }

        return normalized;
    }
}
