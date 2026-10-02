/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : WorkflowId
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : workflow
 * @Package     : dz.sh.hidra.modules.workflow.domain.value
 *
 * @Description : Stable workflow identifier.
 *
 */
package dz.sh.hidra.modules.workflow.domain.value;

import dz.sh.hidra.modules.workflow.domain.exception.InvalidWorkflowValueException;

import java.util.UUID;

/**
 * Stable workflow identifier.
 *
 * @param value identifier value
 */
public record WorkflowId(String value) {

    public WorkflowId {
        value = requireText(value, "Workflow ID must not be null or blank.");
    }

    public static WorkflowId of(String value) {
        return new WorkflowId(value);
    }

    public static WorkflowId newId() {
        return new WorkflowId(UUID.randomUUID().toString());
    }

    private static String requireText(String value, String message) {
        if (value == null || value.isBlank()) {
            throw new InvalidWorkflowValueException(message);
        }
        return value.trim();
    }
}
