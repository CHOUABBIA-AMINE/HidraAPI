/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ExternalClaimMatchEvaluator
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-17
 *
 * @Type        : Class
 * @Layer       : Domain
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.domain.service
 *
 * @Description : Interprets explicitly supplied bounded source-claim values without authenticating them.
 *
 */
package dz.sh.hidra.modules.identity.domain.service;

import dz.sh.hidra.modules.identity.domain.value.ClaimCoverage;
import java.util.Collection;
import java.util.Objects;

/**
 * Pure deterministic three-way comparison. Caller-provided data is never trusted evidence:
 * the Identity adapter alone must validate the private issuance MAC, current mapping epoch,
 * physical transaction and complete selected-name coverage before interpreting this value.
 */
public final class ExternalClaimMatchEvaluator {
    public enum Outcome { MATCH, NO_MATCH, INDETERMINATE }

    public Outcome match(
            ClaimCoverage claims, String authenticationType, String name,
            Collection<String> expectedValues) {
        if (claims == null || name == null || expectedValues == null
                || expectedValues.isEmpty()) return Outcome.INDETERMINATE;
        if (!"OIDC".equals(authenticationType)
                && !"LDAP".equals(authenticationType)
                && !"ACTIVE_DIRECTORY".equals(authenticationType))
            return Outcome.INDETERMINATE;
        if (expectedValues.stream().anyMatch(Objects::isNull))
            return Outcome.INDETERMINATE;
        try {
            ClaimCoverage.State state = claims.state(name);
            if (state == ClaimCoverage.State.PRESENT) {
                for (String expected : expectedValues) {
                    if (claims.values(name).contains(expected)) return Outcome.MATCH;
                }
                // LDAP/AD positively observed attributes are not a complete directory set.
                return "OIDC".equals(authenticationType)
                        ? Outcome.NO_MATCH : Outcome.INDETERMINATE;
            }
            if (state == ClaimCoverage.State.ABSENT && "OIDC".equals(authenticationType))
                return Outcome.NO_MATCH;
            return Outcome.INDETERMINATE;
        } catch (IllegalArgumentException malformed) {
            return Outcome.INDETERMINATE;
        }
    }
}
