/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : WorkflowDecisionNote
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : workflow
 * @Package     : dz.sh.hidra.modules.workflow.domain.value
 *
 * @Description : Workflow decision note value object.
 *
 */
package dz.sh.hidra.modules.workflow.domain.value;

import dz.sh.hidra.kernel.domain.exception.InvalidValueObjectException;
import dz.sh.hidra.kernel.domain.model.ValueObject;

/**
 * Workflow decision note value object.
 *
 * @param value normalized text value
 */
public record WorkflowDecisionNote(String value) implements ValueObject {

    public WorkflowDecisionNote {
        value = normalize(value);
    }

    public static WorkflowDecisionNote of(String value) {
        return new WorkflowDecisionNote(value);
    }

    private static String normalize(String value) {
        if (value == null || value.isBlank()) {
            throw new InvalidValueObjectException("WorkflowDecisionNote must not be null or blank.");
        }

        String normalized = value.trim();

        if (normalized.length() > 2000) {
            throw new InvalidValueObjectException("WorkflowDecisionNote length must not exceed 2000 characters.");
        }

        return normalized;
    }
}
