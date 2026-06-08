/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : WorkflowCatalogTranslationId
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : workflow
 * @Package     : dz.sh.hidra.modules.workflow.domain.value
 *
 * @Description : WorkflowCatalogTranslationId value object.
 *
 */
package dz.sh.hidra.modules.workflow.domain.value;

import java.util.UUID;

import dz.sh.hidra.kernel.domain.exception.InvalidValueObjectException;
import dz.sh.hidra.kernel.domain.model.ValueObject;

/**
 * WorkflowCatalogTranslationId value object.
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
public record WorkflowCatalogTranslationId(String value) implements ValueObject {

    public WorkflowCatalogTranslationId {
        value = normalize(value);
    }

    public static WorkflowCatalogTranslationId of(String value) {
        return new WorkflowCatalogTranslationId(value);
    }

    public static WorkflowCatalogTranslationId newId() {
        return new WorkflowCatalogTranslationId(UUID.randomUUID().toString());
    }

    private static String normalize(String value) {
        if (value == null || value.isBlank()) {
            throw new InvalidValueObjectException("WorkflowCatalogTranslationId must not be null or blank.");
        }

        String normalized = value.trim();

        if (normalized.length() > 80) {
            throw new InvalidValueObjectException("WorkflowCatalogTranslationId length must not exceed 80 characters.");
        }

        return normalized;
    }
}
