/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : IdentityLifecyclePolicyTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-03
 *
 * @Type        : Class
 * @Layer       : Domain Test
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.domain.service
 *
 * @Description : Verifies state-transition, revocation and version invariants.
 *
 */
package dz.sh.hidra.modules.identity.domain.service;

import dz.sh.hidra.modules.identity.domain.value.UserStatus;
import dz.sh.hidra.modules.identity.domain.value.GrantStatus;
import java.time.Instant;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class IdentityLifecyclePolicyTest {
    @Test void onlyRegisteredToActiveAndActiveToDisabledAreSupported() {
        assertDoesNotThrow(() -> IdentityLifecyclePolicy.transition(UserStatus.REGISTERED, UserStatus.ACTIVE));
        assertDoesNotThrow(() -> IdentityLifecyclePolicy.transition(UserStatus.ACTIVE, UserStatus.DISABLED));
        for (var s : UserStatus.values()) {
            if (s != UserStatus.REGISTERED) assertThrows(IllegalStateException.class,
                    () -> IdentityLifecyclePolicy.transition(s, UserStatus.ACTIVE));
        }
        assertThrows(IllegalStateException.class,
                () -> IdentityLifecyclePolicy.transition(UserStatus.DISABLED, UserStatus.ACTIVE));
    }
    @Test void revokedOrSuspendedGrantsCannotBeRevokedTwice() {
        IdentityLifecyclePolicy.revoke(GrantStatus.ACTIVE);
        assertThrows(IllegalStateException.class, () -> IdentityLifecyclePolicy.revoke(GrantStatus.REVOKED));
        assertThrows(IllegalStateException.class, () -> IdentityLifecyclePolicy.revoke(GrantStatus.SUSPENDED));
    }
    @Test void exactCasVersionRejectsStaleMicrosecondAndMissingInput() {
        var at = Instant.parse("2026-11-03T14:00:00.123456Z");
        IdentityLifecyclePolicy.expected(at,at);
        assertThrows(IllegalStateException.class,
                () -> IdentityLifecyclePolicy.expected(at.minusNanos(1000),at));
        assertThrows(IllegalStateException.class, () -> IdentityLifecyclePolicy.expected(null,at));
    }
    @Test void reasonCannotBeBlankOrUnbounded() {
        IdentityLifecyclePolicy.reason("Approved by authorized operator");
        assertThrows(IllegalArgumentException.class, () -> IdentityLifecyclePolicy.reason("  "));
        assertThrows(IllegalArgumentException.class, () -> IdentityLifecyclePolicy.reason("x".repeat(2001)));
    }
}
