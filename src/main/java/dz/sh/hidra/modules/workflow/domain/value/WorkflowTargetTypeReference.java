/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : WorkflowTargetTypeReference
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : workflow
 * @Package     : dz.sh.hidra.modules.workflow.domain.value
 *
 * @Description : Workflow target type catalog reference.
 *
 */
package dz.sh.hidra.modules.workflow.domain.value;

import java.util.Objects;

import dz.sh.hidra.kernel.domain.exception.InvalidValueObjectException;
import dz.sh.hidra.kernel.domain.model.ValueObject;

/**
 * Workflow target type catalog reference.
 *
 * <p>Business rule:
 * Business taxonomy values are configurable catalog references, not Java enums. Localized labels are
 * resolved by catalog services and are not embedded in this reference.
 *
 * @param id catalog entry identifier
 * @param code language-neutral catalog code
 */
public record WorkflowTargetTypeReference(String id, WorkflowCode code) implements ValueObject {

    public WorkflowTargetTypeReference {
        id = requireText(id, "WorkflowTargetTypeReference id");
        code = Objects.requireNonNull(code, "WorkflowTargetTypeReference code must not be null.");
    }

    public static WorkflowTargetTypeReference of(String id, WorkflowCode code) {
        return new WorkflowTargetTypeReference(id, code);
    }

    public static WorkflowTargetTypeReference of(String id, String code) {
        return new WorkflowTargetTypeReference(id, WorkflowCode.of(code));
    }

    public String name() {
        return code.value();
    }

    public boolean is(String expectedCode) {
        return code.value().equals(WorkflowCode.of(expectedCode).value());
    }

    private static String requireText(String value, String fieldName) {
        if (value == null || value.isBlank()) {
            throw new InvalidValueObjectException(fieldName + " must not be null or blank.");
        }

        String normalized = value.trim();

        if (normalized.length() > 80) {
            throw new InvalidValueObjectException(fieldName + " length must not exceed 80 characters.");
        }

        return normalized;
    }
}
