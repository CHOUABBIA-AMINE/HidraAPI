/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : WorkflowCommentText
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : workflow
 * @Package     : dz.sh.hidra.modules.workflow.domain.value
 *
 * @Description : Workflow comment text value object.
 *
 */
package dz.sh.hidra.modules.workflow.domain.value;

import dz.sh.hidra.kernel.domain.exception.InvalidValueObjectException;
import dz.sh.hidra.kernel.domain.model.ValueObject;

/**
 * Workflow comment text value object.
 *
 * @param value normalized text value
 */
public record WorkflowCommentText(String value) implements ValueObject {

    public WorkflowCommentText {
        value = normalize(value);
    }

    public static WorkflowCommentText of(String value) {
        return new WorkflowCommentText(value);
    }

    private static String normalize(String value) {
        if (value == null || value.isBlank()) {
            throw new InvalidValueObjectException("WorkflowCommentText must not be null or blank.");
        }

        String normalized = value.trim();

        if (normalized.length() > 2000) {
            throw new InvalidValueObjectException("WorkflowCommentText length must not exceed 2000 characters.");
        }

        return normalized;
    }
}
