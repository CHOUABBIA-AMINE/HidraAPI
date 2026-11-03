/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : IdentityLifecyclePolicy
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-03
 *
 * @Type        : Class
 * @Layer       : Domain
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.domain.service
 *
 * @Description : Enforces ordinary account and grant lifecycle invariants.
 *
 */
package dz.sh.hidra.modules.identity.domain.service;

import dz.sh.hidra.modules.identity.domain.value.UserStatus;
import dz.sh.hidra.modules.identity.domain.value.GrantStatus;
import java.time.Instant;
import java.util.Objects;

/** Pure domain rules; transaction and authorization belong to application/ports. */
public final class IdentityLifecyclePolicy {
    private IdentityLifecyclePolicy() { }

    public static void transition(UserStatus current, UserStatus next) {
        Objects.requireNonNull(current, "current status");
        boolean allowed = (current == UserStatus.REGISTERED && next == UserStatus.ACTIVE)
                || (current == UserStatus.ACTIVE && next == UserStatus.DISABLED);
        if (!allowed) throw new IllegalStateException("Unsupported user lifecycle transition.");
    }

    public static void revoke(GrantStatus current) {
        if (current != GrantStatus.ACTIVE) throw new IllegalStateException("Only ACTIVE grants may be revoked.");
    }

    public static void reason(String reason) {
        if (reason == null || reason.isBlank() || reason.strip().length() > 2000)
            throw new IllegalArgumentException("A bounded reason is required.");
    }

    public static void expected(Instant supplied, Instant actual) {
        if (supplied == null || actual == null || !supplied.equals(actual))
            throw new IllegalStateException("Stale lifecycle version.");
    }
}
