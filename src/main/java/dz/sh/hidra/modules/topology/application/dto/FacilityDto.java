/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : FacilityDto
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Record
 * @Layer       : Application
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.application.dto
 *
 * @Description : Application DTO representing a physical facility.
 *
 */
package dz.sh.hidra.modules.topology.application.dto;

import java.time.Instant;

/**
 * Represents physical facility data returned by topology use cases.
 *
 * <p>Business role:
 * This DTO describes a physical topology facility such as a compression station, pumping station,
 * metering station, valve station, terminal, processing plant, production field interface, gathering
 * center, storage facility, delivery facility, receipt facility, or dispatching center.
 *
 * <p>Architecture role:
 * This application DTO is framework-independent. It may expose a neutral organization unit
 * reference but does not import organization implementation classes.
 *
 * <p>Validation:
 * This DTO is an output projection. Facility validation is enforced by topology domain models,
 * values, policies, and services.
 *
 * <p>Usage:
 * Use this DTO as an application output and map it to REST responses in the API layer later.
 *
 * @param facilityId facility identifier
 * @param code business code
 * @param name display name
 * @param facilityType facility type
 * @param productType hydrocarbon product type
 * @param status lifecycle status
 * @param coordinate optional geographical coordinate
 * @param organizationUnitReference optional neutral organization unit reference
 * @param createdAt creation instant
 * @param updatedAt last update instant
 */
public record FacilityDto(
        String facilityId,
        String code,
        String name,
        String facilityType,
        String productType,
        String status,
        GeoCoordinateDto coordinate,
        OrganizationUnitReferenceDto organizationUnitReference,
        Instant createdAt,
        Instant updatedAt) {
}
