/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : OidcClaimCoverageProjectorTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-16
 *
 * @Type        : Class
 * @Layer       : Identity Test
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.infrastructure.security
 *
 * @Description : Exercises synthetic non-authoritative OIDC claim-tree projection boundaries.
 *
 */
package dz.sh.hidra.modules.identity.infrastructure.security;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import dz.sh.hidra.modules.identity.application.port.out.ClaimNameCatalogPort;
import dz.sh.hidra.modules.identity.domain.value.ClaimCoverage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.junit.jupiter.api.Test;

/** Source-shaped synthetic data only; no original-token validation or authorization. */
class OidcClaimCoverageProjectorTest {
    private static ClaimNameCatalogPort.VersionedSelection selected(long revision, String... names) {
        return new ClaimNameCatalogPort.VersionedSelection(
                new ClaimNameCatalogPort.Selection(List.of(names)), revision);
    }

    @Test
    void exactCasePreservedWithOrderedDistinctStringList() {
        var result = OidcClaimCoverageProjector.project(
                Map.of("Roles", List.of("operator", "Admin"), "roles", "different"),
                selected(7, "Roles", "roles"));
        assertThat(result.mappingGeneration()).isEqualTo(7);
        assertThat(result.coverage().entries().keySet()).containsExactly("Roles", "roles");
        assertThat(result.coverage().values("Roles")).containsExactly("Admin", "operator");
        assertThat(result.coverage().values("roles")).containsExactly("different");
        assertThat(result.coverage().state("ROLES")).isEqualTo(ClaimCoverage.State.UNKNOWN);
    }

    @Test
    void nestedDottedPathTraversesExactSegments() {
        var result = OidcClaimCoverageProjector.project(
                Map.of("realm", Map.of("access", Map.of("roles", List.of("dispatcher")))),
                selected(9, "realm.access.roles"));
        assertThat(result.coverage().state("realm.access.roles"))
                .isEqualTo(ClaimCoverage.State.PRESENT);
        assertThat(result.coverage().values("realm.access.roles"))
                .containsExactly("dispatcher");
    }

    @Test
    void literalDottedKeyRetainsExactTopLevelValue() {
        var result = OidcClaimCoverageProjector.project(
                Map.of("realm.roles", "literal", "realm", Map.of("roles", "nested")),
                selected(3, "realm.roles"));
        assertThat(result.coverage().values("realm.roles")).containsExactly("literal");
    }

    @Test
    void missingSelectedPathIsAbsentButUnrequestedIsUnknown() {
        var result = OidcClaimCoverageProjector.project(
                Map.of("realm", Map.of("access", Map.of())),
                selected(2, "realm.access.roles", "groups"));
        assertThat(result.coverage().state("realm.access.roles"))
                .isEqualTo(ClaimCoverage.State.ABSENT);
        assertThat(result.coverage().state("groups")).isEqualTo(ClaimCoverage.State.ABSENT);
        assertThat(result.coverage().state("permissions"))
                .isEqualTo(ClaimCoverage.State.UNKNOWN);
    }

    @Test
    void invalidIntermediateNodeAndEmptyPathSegmentAreMalformed() {
        var result = OidcClaimCoverageProjector.project(
                Map.of("realm", "not-an-object"),
                selected(4, "realm.roles", "realm..roles"));
        assertThat(result.coverage().state("realm.roles"))
                .isEqualTo(ClaimCoverage.State.MALFORMED);
        assertThat(result.coverage().state("realm..roles"))
                .isEqualTo(ClaimCoverage.State.MALFORMED);
    }

    @Test
    void nullBlankAndEmptySelectedValuesAreMalformed() {
        Map<String, Object> raw = new HashMap<>();
        raw.put("null", null);
        raw.put("blank", "  ");
        raw.put("empty", List.of());
        var result = OidcClaimCoverageProjector.project(
                raw, selected(4, "null", "blank", "empty"));
        for (String name : List.of("null", "blank", "empty")) {
            assertThat(result.coverage().state(name)).isEqualTo(ClaimCoverage.State.MALFORMED);
            assertThat(result.coverage().values(name)).isEmpty();
        }
    }

    @Test
    void numberBooleanMapAndMixedArraysNeverBecomeStringAssertions() {
        var result = OidcClaimCoverageProjector.project(
                Map.of("number", 42, "flag", true, "object", Map.of("v", "x"),
                        "mixed", List.of("a", 1)),
                selected(6, "number", "flag", "object", "mixed"));
        for (String name : result.coverage().entries().keySet())
            assertThat(result.coverage().state(name)).isEqualTo(ClaimCoverage.State.MALFORMED);
    }

    @Test
    void duplicateListValuesAreRefusedNotSilentlyDeduplicated() {
        assertThatThrownBy(() -> OidcClaimCoverageProjector.project(
                Map.of("groups", List.of("dispatch", "dispatch")), selected(1, "groups")))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Invalid bounded OIDC claim projection");
    }

    @Test
    void invalidUnicodeAndOversizeValuesRefuseTheWholeProjection() {
        String broken = String.valueOf((char) 0xD800);
        for (String invalid : List.of(broken, "v".repeat(1025))) {
            assertThatThrownBy(() -> OidcClaimCoverageProjector.project(
                    Map.of("groups", invalid), selected(2, "groups")))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("Invalid bounded OIDC claim projection");
        }
    }

    @Test
    void excessivePerNameValueCountIsRejected() {
        List<String> values = new ArrayList<>();
        for (int i = 0; i < ClaimCoverage.MAX_VALUES_PER_NAME + 1; i++)
            values.add("value-" + i);
        assertThatThrownBy(() -> OidcClaimCoverageProjector.project(
                Map.of("groups", values), selected(3, "groups")))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Invalid bounded OIDC claim projection");
    }

    @Test
    void aggregateEnvelopeCannotBeBypassedAcrossSelectedNames() {
        Map<String, Object> supplied = new HashMap<>();
        List<String> names = new ArrayList<>();
        for (int i = 0; i < 18; i++) {
            String name = "claim" + i;
            names.add(name);
            supplied.put(name, "v".repeat(1000));
        }
        assertThatThrownBy(() -> OidcClaimCoverageProjector.project(
                supplied, selected(5, names.toArray(String[]::new))))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Invalid bounded OIDC claim projection");
    }

    @Test
    void projectionIsImmutableAgainstSubsequentSourceMutation() {
        var mutable = new ArrayList<>(List.of("B", "A"));
        var claims = new HashMap<String, Object>();
        claims.put("roles", mutable);
        var projection = OidcClaimCoverageProjector.project(claims, selected(11, "roles"));
        mutable.add("C");
        claims.clear();
        assertThat(projection.coverage().values("roles")).containsExactly("A", "B");
        assertThatThrownBy(() -> projection.coverage().values("roles").add("changed"))
                .isInstanceOf(UnsupportedOperationException.class);
        assertThat(projection.mappingGeneration()).isEqualTo(11);
    }

    @Test
    void emptySelectionDoesNotInspectOrExposeUnrequestedClaims() {
        var result = OidcClaimCoverageProjector.project(
                Map.of("secret", Map.of("private", Set.of("untouched"))),
                selected(12));
        assertThat(result.coverage().entries()).isEmpty();
        assertThat(result.coverage().state("secret")).isEqualTo(ClaimCoverage.State.UNKNOWN);
        assertThat(result.mappingGeneration()).isEqualTo(12);
    }

    @Test
    void nullSourcesAndInvalidRevisionFailWithNoSensitiveText() {
        assertThatThrownBy(() -> OidcClaimCoverageProjector.project(null, selected(1, "secret")))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Invalid bounded OIDC claim projection");
        assertThatThrownBy(() -> OidcClaimCoverageProjector.project(
                Map.of("secret", "value"), null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Invalid bounded OIDC claim projection");
        assertThatThrownBy(() -> selected(0, "secret"))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void resultDoesNotImplyAuthenticatedClaimsOrPermissions() {
        var result = OidcClaimCoverageProjector.project(
                Map.of("groups", List.of("secret-operator")),
                selected(20, "groups"));
        assertThat(result.coverage().values("groups")).containsExactly("secret-operator");
        assertThat(result.toString()).contains("authority=none")
                .doesNotContain("groups", "secret-operator", "20");
        assertThat(OidcClaimCoverageProjector.class.getDeclaredMethods())
                .extracting(java.lang.reflect.Method::getName)
                .doesNotContain("authorize", "permit", "authenticate", "issue", "attest");
    }
}
