/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TopologyCatalogTranslationDto
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Record
 * @Layer       : Application
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.application.dto
 *
 * @Description : Application DTO exposing a localized topology catalog translation.
 *
 */
package dz.sh.hidra.modules.topology.application.dto;

import java.time.Instant;

/**
 * Application DTO exposing a localized topology catalog translation.
 *
 * <p>Business role:
 * Carries one localized label and optional description for a configurable topology type catalog
 * entry.
 *
 * <p>Architecture role:
 * This is an application-layer output contract. It must not depend on REST, JPA, Spring, identity,
 * organization implementation, measurement, flow, risk, workflow, or infrastructure code.
 *
 * <p>Validation:
 * Values are already validated by topology domain catalog models before this DTO is produced.
 *
 * <p>Usage:
 * Return this DTO from topology catalog use cases when callers need all available translations.
 *
 * @param id translation identifier
 * @param typeId catalog entry identifier
 * @param locale locale tag
 * @param name localized display name
 * @param description optional localized description
 * @param createdAt creation instant
 * @param updatedAt update instant
 */
public record TopologyCatalogTranslationDto(
        String id,
        String typeId,
        String locale,
        String name,
        String description,
        Instant createdAt,
        Instant updatedAt) {
}
