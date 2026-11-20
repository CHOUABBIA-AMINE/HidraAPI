/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : RegionalResourceAccessPolicyTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-20
 *
 * @Type        : Class
 * @Layer       : Test
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.domain.service
 *
 * @Description : Verifies T33 regional read scope, deny precedence and owner evidence.
 *
 */
package dz.sh.hidra.modules.identity.domain.service;

import java.util.List;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class RegionalResourceAccessPolicyTest {
    private final RegionalResourceAccessPolicy policy = new RegionalResourceAccessPolicy();
    private static RegionalResourceAccessPolicy.Candidate candidate(
            RegionalResourceAccessPolicy.Grant grant, boolean responsibility) {
        return new RegionalResourceAccessPolicy.Candidate(grant, responsibility);
    }

    @Test void currentMatchingGrantAndResponsibilityPermit() {
        assertTrue(policy.permits(true, List.of(
                candidate(RegionalResourceAccessPolicy.Grant.NO_GRANT, false),
                candidate(RegionalResourceAccessPolicy.Grant.PERMIT, true))));
    }

    @Test void implicitMembershipOrPermissionWithoutResponsibilityNeverPermits() {
        assertFalse(policy.permits(true, List.of(candidate(RegionalResourceAccessPolicy.Grant.PERMIT, false))));
        assertFalse(policy.permits(true, List.of(candidate(RegionalResourceAccessPolicy.Grant.NO_GRANT, true))));
    }

    @Test void exactExplicitDenyAlwaysWinsOverInheritedPermit() {
        assertFalse(policy.permits(true, List.of(
                candidate(RegionalResourceAccessPolicy.Grant.PERMIT, true),
                candidate(RegionalResourceAccessPolicy.Grant.EXPLICIT_DENY, false))));
    }

    @Test void indeterminateOrMissingCurrentOwnerFailsClosed() {
        assertFalse(policy.permits(true, List.of(
                candidate(RegionalResourceAccessPolicy.Grant.PERMIT, true),
                candidate(RegionalResourceAccessPolicy.Grant.INDETERMINATE, false))));
        assertFalse(policy.permits(false, List.of(candidate(RegionalResourceAccessPolicy.Grant.PERMIT, true))));
        assertFalse(policy.permits(true, List.of()));
        assertFalse(policy.permits(true, null));
    }
}
