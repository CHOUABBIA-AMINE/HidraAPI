/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : PositionResponse
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Record
 * @Layer       : API
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization.api.rest.response
 *
 * @Description : REST response representing an organization position.
 *
 */
package dz.sh.hidra.modules.organization.api.rest.response;

import java.time.Instant;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * REST response representing an organization position.
 *
 * @param positionId position identifier
 * @param code position business code
 * @param title position title
 * @param description optional description
 * @param active whether position can be assigned
 * @param createdAt creation instant
 * @param updatedAt last update instant
 */
@Schema(name = "PositionResponse", description = "REST response representing an organization position.")
public record PositionResponse(
        @Schema(description = "Position identifier.", example = "pos_550e8400-e29b-41d4-a716-446655440000")
        String positionId,
        @Schema(description = "Position code.", example = "STATION_TEAM_LEADER")
        String code,
        @Schema(description = "Position title.", example = "Station Team Leader")
        String title,
        @Schema(description = "Optional position description.")
        String description,
        @Schema(description = "Whether this position can be assigned.", example = "true")
        boolean active,
        @Schema(description = "Creation instant.", type = "string", format = "date-time")
        Instant createdAt,
        @Schema(description = "Last update instant.", type = "string", format = "date-time")
        Instant updatedAt) {
}
