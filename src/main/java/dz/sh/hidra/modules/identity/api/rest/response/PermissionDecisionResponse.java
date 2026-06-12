/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : PermissionDecisionResponse
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : API
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.api.rest.response
 *
 * @Description : REST response for permission decision.
 *
 */
package dz.sh.hidra.modules.identity.api.rest.response;

import dz.sh.hidra.modules.identity.domain.value.AuthorizationDecisionValue;

import java.time.Instant;

/**
 * REST response for permission decision.
 *
 * @param permitted whether access is permitted
 * @param decision decision value
 * @param reasonCode machine-readable reason
 * @param reasonMessage human-readable reason
 * @param evaluatedAt evaluation timestamp
 */
public record PermissionDecisionResponse(
        boolean permitted,
        AuthorizationDecisionValue decision,
        String reasonCode,
        String reasonMessage,
        Instant evaluatedAt
) {
}
