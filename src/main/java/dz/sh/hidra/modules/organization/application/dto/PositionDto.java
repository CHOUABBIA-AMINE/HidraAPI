/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : PositionDto
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Record
 * @Layer       : Application
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization.application.dto
 *
 * @Description : Application DTO representing an organization position.
 *
 */
package dz.sh.hidra.modules.organization.application.dto;

import java.time.Instant;

/**
 * Represents organization position data returned by organization use cases.
 *
 * <p>Business role:
 * This DTO describes an operational position or function such as Station Team Leader, Station
 * Boss, Region Director, Gas Flux Director, or Department Chief. It is not an identity role.
 *
 * <p>Architecture role:
 * This application DTO is returned by position use cases and must not depend on REST, JPA,
 * identity, topology, platform, Spring, or infrastructure code.
 *
 * <p>Validation:
 * This DTO is an output projection. Position validation is performed by position value objects and
 * the position domain model.
 *
 * <p>Usage:
 * Use this DTO as an application output and convert it to API response DTOs later.
 *
 * @param positionId position identifier
 * @param code position business code
 * @param title position display title
 * @param description optional business description
 * @param active whether the position can be assigned
 * @param createdAt creation instant
 * @param updatedAt last update instant
 */
public record PositionDto(
        String positionId,
        String code,
        String title,
        String description,
        boolean active,
        Instant createdAt,
        Instant updatedAt) {
}
