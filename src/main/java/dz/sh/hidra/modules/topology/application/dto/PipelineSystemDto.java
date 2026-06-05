/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : PipelineSystemDto
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Record
 * @Layer       : Application
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.application.dto
 *
 * @Description : Application DTO representing a pipeline system.
 *
 */
package dz.sh.hidra.modules.topology.application.dto;

import java.time.Instant;

/**
 * Represents pipeline system data returned by topology use cases.
 *
 * <p>Business role:
 * This DTO describes a physical hydrocarbon transportation system that groups pipelines.
 *
 * <p>Architecture role:
 * This application DTO is framework-independent. It does not expose persistence entities and does
 * not use API, Swagger, JPA, Spring, identity, organization implementation, measurement, flow, risk,
 * workflow, or infrastructure types.
 *
 * <p>Validation:
 * This DTO is an output projection. Pipeline system validation is enforced by topology domain
 * models, value objects, policies, and services.
 *
 * <p>Usage:
 * Use this DTO as an application output and map it to REST responses in the API layer later.
 *
 * @param pipelineSystemId pipeline system identifier
 * @param code business code
 * @param name display name
 * @param description optional description
 * @param productType hydrocarbon product type
 * @param status lifecycle status
 * @param operationalOwnerReference optional neutral operational owner reference
 * @param createdAt creation instant
 * @param updatedAt last update instant
 */
public record PipelineSystemDto(
        String pipelineSystemId,
        String code,
        String name,
        String description,
        String productType,
        String status,
        OrganizationUnitReferenceDto operationalOwnerReference,
        Instant createdAt,
        Instant updatedAt) {
}
