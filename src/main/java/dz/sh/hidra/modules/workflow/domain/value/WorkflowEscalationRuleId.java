/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : WorkflowEscalationRuleId
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : workflow
 * @Package     : dz.sh.hidra.modules.workflow.domain.value
 *
 * @Description : WorkflowEscalationRuleId value object.
 *
 */
package dz.sh.hidra.modules.workflow.domain.value;

import java.util.UUID;

import dz.sh.hidra.kernel.domain.exception.InvalidValueObjectException;
import dz.sh.hidra.kernel.domain.model.ValueObject;

/**
 * WorkflowEscalationRuleId value object.
 *
 * <p>Business role:
 * Provides stable workflow identity without exposing persistence details.
 *
 * <p>Architecture role:
 * Pure workflow domain value object. It must not depend on Spring, JPA, REST, telemetry, topology,
 * organization, identity, planning, monitoring, incidents, audit, integration, analytics, reporting,
 * or notification implementation packages.
 *
 * @param value stable identifier value
 */
public record WorkflowEscalationRuleId(String value) implements ValueObject {

    public WorkflowEscalationRuleId {
        value = normalize(value);
    }

    public static WorkflowEscalationRuleId of(String value) {
        return new WorkflowEscalationRuleId(value);
    }

    public static WorkflowEscalationRuleId newId() {
        return new WorkflowEscalationRuleId(UUID.randomUUID().toString());
    }

    private static String normalize(String value) {
        if (value == null || value.isBlank()) {
            throw new InvalidValueObjectException("WorkflowEscalationRuleId must not be null or blank.");
        }

        String normalized = value.trim();

        if (normalized.length() > 80) {
            throw new InvalidValueObjectException("WorkflowEscalationRuleId length must not exceed 80 characters.");
        }

        return normalized;
    }
}
