/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : GeoCoordinateResponse
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Record
 * @Layer       : API
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.api.rest.response
 *
 * @Description : REST response representing geographical coordinates.
 *
 */
package dz.sh.hidra.modules.topology.api.rest.response;

import java.math.BigDecimal;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * REST response representing geographical coordinates.
 *
 * <p>Business role:
 * Returns the optional physical location of facilities, topology nodes, and pipeline appurtenances.
 *
 * <p>Architecture role:
 * This is a REST output contract. It is mapped from application DTOs through TopologyRestMapper.
 *
 * <p>Validation:
 * Coordinate validity is enforced by topology domain value objects before this response is produced.
 *
 * @param latitude latitude in decimal degrees
 * @param longitude longitude in decimal degrees
 */
@Schema(name = "GeoCoordinateResponse", description = "REST response representing geographical coordinates.")
public record GeoCoordinateResponse(
        @Schema(description = "Latitude in decimal degrees.", example = "31.6167")
        BigDecimal latitude,

        @Schema(description = "Longitude in decimal degrees.", example = "2.2167")
        BigDecimal longitude) {
}
