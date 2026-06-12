/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : PermissionDecisionDto
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Application
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.application.dto
 *
 * @Description : DTO representing permission evaluation result.
 *
 */
package dz.sh.hidra.modules.identity.application.dto;

import dz.sh.hidra.modules.identity.domain.value.AuthorizationDecisionValue;

import java.time.Instant;

/**
 * Permission evaluation result.
 *
 * @param permitted whether access is permitted
 * @param decision decision value
 * @param reasonCode machine-readable reason
 * @param reasonMessage human-readable reason
 * @param evaluatedAt evaluation timestamp
 */
public record PermissionDecisionDto(
        boolean permitted,
        AuthorizationDecisionValue decision,
        String reasonCode,
        String reasonMessage,
        Instant evaluatedAt
) {
}
