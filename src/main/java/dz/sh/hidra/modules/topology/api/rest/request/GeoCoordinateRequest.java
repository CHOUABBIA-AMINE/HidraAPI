/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : GeoCoordinateRequest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Record
 * @Layer       : API
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.api.rest.request
 *
 * @Description : REST request body fragment for geographical coordinates.
 *
 */
package dz.sh.hidra.modules.topology.api.rest.request;

import java.math.BigDecimal;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

/**
 * Request fragment carrying geographical coordinates for topology assets.
 *
 * <p>Business role:
 * Captures the optional physical location of facilities, topology nodes, and pipeline appurtenances.
 *
 * <p>Architecture role:
 * This is a REST input contract. Controllers map it to topology application/domain values through
 * TopologyRestMapper.
 *
 * <p>Validation:
 * Latitude and longitude are required when the coordinate object is present. Latitude must be between
 * -90 and 90. Longitude must be between -180 and 180.
 *
 * @param latitude latitude in decimal degrees
 * @param longitude longitude in decimal degrees
 */
@Schema(name = "GeoCoordinateRequest", description = "Geographical coordinate request fragment.")
public record GeoCoordinateRequest(
        @Schema(description = "Latitude in decimal degrees.", example = "31.6167", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotNull
        @DecimalMin("-90.0")
        @DecimalMax("90.0")
        BigDecimal latitude,

        @Schema(description = "Longitude in decimal degrees.", example = "2.2167", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotNull
        @DecimalMin("-180.0")
        @DecimalMax("180.0")
        BigDecimal longitude) {
}
