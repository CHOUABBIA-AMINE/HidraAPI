/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AccessTokenIssuanceContext
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-14
 *
 * @Type        : Record
 * @Layer       : Application
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.application.model
 *
 * @Description : Defines server-chosen JWT identity and times for bound issuance.
 *
 */
package dz.sh.hidra.modules.identity.application.model;

import java.time.Instant;
import java.util.UUID;

/** Immutable server-chosen identity and finite whole-second lifetime for bound issuance. */
public record AccessTokenIssuanceContext(String jti, Instant issuedAt, Instant expiresAt) {
    public AccessTokenIssuanceContext {
        if (issuedAt == null
                || expiresAt == null
                || issuedAt.getEpochSecond() < -210866803200L
                || expiresAt.getEpochSecond() >= 9224318016000L
                || jti == null
                || !canonical(jti)
                || issuedAt.getNano() != 0
                || expiresAt.getNano() != 0
                || !issuedAt.isBefore(expiresAt)) {
            throw new IllegalArgumentException("Invalid bound issuance context");
        }
    }

    private static boolean canonical(String value) {
        try {
            return UUID.fromString(value).toString().equals(value);
        } catch (IllegalArgumentException exception) {
            return false;
        }
    }
}
