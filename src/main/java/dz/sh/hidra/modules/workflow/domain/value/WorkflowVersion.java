/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : WorkflowVersion
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : workflow
 * @Package     : dz.sh.hidra.modules.workflow.domain.value
 *
 * @Description : Workflow definition version value object.
 *
 */
package dz.sh.hidra.modules.workflow.domain.value;

import dz.sh.hidra.kernel.domain.exception.InvalidValueObjectException;
import dz.sh.hidra.kernel.domain.model.ValueObject;

/**
 * Positive workflow definition version.
 *
 * @param value version number
 */
public record WorkflowVersion(int value) implements ValueObject {

    public WorkflowVersion {
        if (value < 1) {
            throw new InvalidValueObjectException("WorkflowVersion must be greater than or equal to 1.");
        }
    }

    public static WorkflowVersion initial() {
        return new WorkflowVersion(1);
    }

    public static WorkflowVersion of(int value) {
        return new WorkflowVersion(value);
    }

    public WorkflowVersion next() {
        return new WorkflowVersion(value + 1);
    }
}
