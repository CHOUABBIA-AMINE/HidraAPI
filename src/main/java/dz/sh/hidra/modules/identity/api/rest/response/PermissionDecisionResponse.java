/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : PermissionDecisionResponse
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Record
 * @Layer       : API
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.api.rest.response
 *
 * @Description : REST response representing an identity permission decision.
 *
 */
package dz.sh.hidra.modules.identity.api.rest.response;

/**
 * REST response representing an identity permission decision.
 *
 * <p>Business role: tells API clients whether a user has a requested permission.</p>
 *
 * <p>Architecture role: API-layer response DTO that does not expose platform security
 * internals or persistence entities.</p>
 *
 * <p>Validation responsibility: response values are produced from validated application
 * DTOs.</p>
 *
 * <p>Usage: returned by permission check endpoints.</p>
 */
public record PermissionDecisionResponse(
        String userId,
        String permissionCode,
        boolean granted,
        String reason
) {
}
