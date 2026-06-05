/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : GeoCoordinateDto
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Record
 * @Layer       : Application
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.application.dto
 *
 * @Description : Application DTO representing geographical coordinates.
 *
 */
package dz.sh.hidra.modules.topology.application.dto;

import java.math.BigDecimal;

/**
 * Represents geographical coordinate data returned by topology use cases.
 *
 * <p>Business role:
 * This DTO describes the optional physical location of facilities, topology nodes, and pipeline
 * appurtenances.
 *
 * <p>Architecture role:
 * This application DTO is framework-independent. It does not use Swagger, REST, JPA, Spring,
 * persistence, identity, organization, measurement, flow, risk, workflow, or infrastructure types.
 *
 * <p>Validation:
 * This DTO is an output projection. Coordinate validation is enforced by topology domain value
 * objects before this DTO is created.
 *
 * <p>Usage:
 * Use this DTO as an application output and map it to REST responses in the API layer later.
 *
 * @param latitude latitude in decimal degrees
 * @param longitude longitude in decimal degrees
 */
public record GeoCoordinateDto(
        BigDecimal latitude,
        BigDecimal longitude) {
}
