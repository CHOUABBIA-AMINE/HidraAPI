/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : OrganizationUnitReferenceDto
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Record
 * @Layer       : Application
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.application.dto
 *
 * @Description : Application DTO representing a neutral organization unit reference.
 *
 */
package dz.sh.hidra.modules.topology.application.dto;

/**
 * Represents a neutral organization unit reference returned by topology use cases.
 *
 * <p>Business role:
 * This DTO describes the organization unit that owns or operates a physical topology asset without
 * making topology own employees, positions, assignments, or reporting lines.
 *
 * <p>Architecture role:
 * This application DTO is framework-independent and does not import organization implementation
 * classes. It exposes only neutral scalar reference fields.
 *
 * <p>Validation:
 * This DTO is an output projection. Reference validation is enforced by topology domain value
 * objects before this DTO is created.
 *
 * <p>Usage:
 * Use this DTO as an application output and map it to REST responses in the API layer later.
 *
 * @param referenceType neutral reference type
 * @param referenceId external organization unit identifier
 * @param referenceCode external organization unit code
 * @param referenceName external organization unit name
 */
public record OrganizationUnitReferenceDto(
        String referenceType,
        String referenceId,
        String referenceCode,
        String referenceName) {
}
