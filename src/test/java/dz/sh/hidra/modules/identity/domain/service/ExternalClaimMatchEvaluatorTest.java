/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ExternalClaimMatchEvaluatorTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-17
 *
 * @Type        : Class
 * @Layer       : Identity Test
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.domain.service
 *
 * @Description : Tests exact positive, negative and uncertain bounded claim comparisons.
 *
 */
package dz.sh.hidra.modules.identity.domain.service;

import static org.assertj.core.api.Assertions.assertThat;

import dz.sh.hidra.modules.identity.domain.value.ClaimCoverage;
import dz.sh.hidra.modules.identity.domain.value.ClaimCoverage.Entry;
import org.junit.jupiter.api.Test;
import java.util.List;
import java.util.Map;
import java.util.Set;

class ExternalClaimMatchEvaluatorTest {
    private final ExternalClaimMatchEvaluator matcher = new ExternalClaimMatchEvaluator();

    @Test
    void oidcExactPresentCanMatchWithoutNormalization() {
        ClaimCoverage c = ClaimCoverage.of(Map.of("groups", Entry.present(Set.of("Operator", "équipe"))));
        assertThat(matcher.match(c, "OIDC", "groups", List.of("Operator")))
                .isEqualTo(ExternalClaimMatchEvaluator.Outcome.MATCH);
        assertThat(matcher.match(c, "OIDC", "groups", List.of("operator")))
                .isEqualTo(ExternalClaimMatchEvaluator.Outcome.NO_MATCH);
        assertThat(matcher.match(c, "OIDC", "groups", List.of("Oper")))
                .isEqualTo(ExternalClaimMatchEvaluator.Outcome.NO_MATCH);
    }

    @Test
    void oidcAuthenticatedAbsentAndCompleteNonmatchAreNegative() {
        ClaimCoverage c = ClaimCoverage.of(Map.of("groups", Entry.absent(),
                "roles", Entry.present(Set.of("viewer"))));
        assertThat(matcher.match(c, "OIDC", "groups", List.of("root")))
                .isEqualTo(ExternalClaimMatchEvaluator.Outcome.NO_MATCH);
        assertThat(matcher.match(c, "OIDC", "roles", List.of("operator")))
                .isEqualTo(ExternalClaimMatchEvaluator.Outcome.NO_MATCH);
    }

    @Test
    void unknownAndMalformedCannotBeUsedAsNegativeProof() {
        ClaimCoverage c = ClaimCoverage.of(Map.of("malformed", Entry.malformed(),
                "unknown", Entry.unknown()));
        for (String type : List.of("OIDC", "LDAP", "ACTIVE_DIRECTORY")) {
            for (String key : List.of("missing", "malformed", "unknown")) {
                assertThat(matcher.match(c, type, key, Set.of("group")))
                        .isEqualTo(ExternalClaimMatchEvaluator.Outcome.INDETERMINATE);
            }
        }
    }

    @Test
    void directoryPositiveObservationsDoNotConferCompleteSetSemantics() {
        ClaimCoverage c = ClaimCoverage.of(Map.of("memberOf", Entry.present(Set.of("CN=Ops,OU=Groups"))));
        for (String type : List.of("LDAP", "ACTIVE_DIRECTORY")) {
            assertThat(matcher.match(c, type, "memberOf", Set.of("CN=Ops,OU=Groups")))
                    .isEqualTo(ExternalClaimMatchEvaluator.Outcome.MATCH);
            assertThat(matcher.match(c, type, "memberOf", Set.of("cn=ops,ou=groups")))
                    .isEqualTo(ExternalClaimMatchEvaluator.Outcome.INDETERMINATE);
            assertThat(matcher.match(c, type, "memberOf", Set.of("Ops")))
                    .isEqualTo(ExternalClaimMatchEvaluator.Outcome.INDETERMINATE);
            assertThat(matcher.match(ClaimCoverage.of(Map.of("memberOf", Entry.absent())),
                    type, "memberOf", Set.of("CN=Ops,OU=Groups")))
                    .isEqualTo(ExternalClaimMatchEvaluator.Outcome.INDETERMINATE);
        }
    }

    @Test
    void localOrUnsupportedSourceNeverProvesExternalMembership() {
        ClaimCoverage c = ClaimCoverage.of(Map.of("roles", Entry.present(Set.of("administrator"))));
        for (String type : List.of("LOCAL", "unknown", "")) {
            assertThat(matcher.match(c, type, "roles", Set.of("administrator")))
                    .isEqualTo(ExternalClaimMatchEvaluator.Outcome.INDETERMINATE);
        }
    }

    @Test
    void invalidUnrequestedOrEmptyExpectedValuesAreUncertain() {
        ClaimCoverage c = ClaimCoverage.of(Map.of("roles", Entry.present(Set.of("operator"))));
        assertThat(matcher.match(c, "OIDC", "roles", List.of()))
                .isEqualTo(ExternalClaimMatchEvaluator.Outcome.INDETERMINATE);
        assertThat(matcher.match(c, "OIDC", "roles", null))
                .isEqualTo(ExternalClaimMatchEvaluator.Outcome.INDETERMINATE);
        assertThat(matcher.match(c, "OIDC", null, Set.of("operator")))
                .isEqualTo(ExternalClaimMatchEvaluator.Outcome.INDETERMINATE);
        assertThat(matcher.match(c, "OIDC", "roles", List.of(" operator")))
                .isEqualTo(ExternalClaimMatchEvaluator.Outcome.NO_MATCH);
    }
}
