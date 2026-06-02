/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : PermissionResponse
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Record
 * @Layer       : API
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.api.rest.response
 *
 * @Description : REST response representing an identity permission.
 *
 */
package dz.sh.hidra.modules.identity.api.rest.response;

/**
 * REST response representing an identity permission.
 *
 * <p>Business role: exposes permission catalog information to API clients.</p>
 *
 * <p>Architecture role: API-layer response DTO. It is not a JPA entity, domain model, or
 * platform security type.</p>
 *
 * <p>Validation responsibility: response values are produced from validated application
 * DTOs.</p>
 *
 * <p>Usage: returned by identity permission and role endpoints.</p>
 */
public record PermissionResponse(
        String id,
        String code,
        String name,
        String description
) {
}
