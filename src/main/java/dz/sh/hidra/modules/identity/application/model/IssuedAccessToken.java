/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : IssuedAccessToken
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-15
 *
 * @Type        : Record
 * @Layer       : Application
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.application.model
 *
 * @Description : Carries a Hidra-issued bearer access token and its lifecycle metadata.
 *
 */
package dz.sh.hidra.modules.identity.application.model;

import java.time.Instant;

/**
 * Provider-neutral result of Hidra access-token issuance.
 */
public record IssuedAccessToken(
        String tokenValue,
        String tokenType,
        String jti,
        Instant issuedAt,
        Instant expiresAt
) {

    public IssuedAccessToken {
        if (tokenValue == null || tokenValue.isBlank()) {
            throw new IllegalArgumentException("tokenValue must not be blank.");
        }
        if (tokenType == null || tokenType.isBlank()) {
            throw new IllegalArgumentException("tokenType must not be blank.");
        }
        if (jti == null || jti.isBlank()) {
            throw new IllegalArgumentException("jti must not be blank.");
        }
        if (issuedAt == null || expiresAt == null || !expiresAt.isAfter(issuedAt)) {
            throw new IllegalArgumentException("Access-token timestamps are invalid.");
        }
    }
}
