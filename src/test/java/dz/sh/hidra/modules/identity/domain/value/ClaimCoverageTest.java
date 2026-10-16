/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ClaimCoverageTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-16
 *
 * @Type        : Class
 * @Layer       : Identity Test
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.domain.value
 *
 * @Description : Tests non-authoritative bounded and immutable claim coverage semantics.
 *
 */
package dz.sh.hidra.modules.identity.domain.value;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import org.junit.jupiter.api.Test;

class ClaimCoverageTest {
    @Test
    void presentValuesRetainExactCaseAndStableOrdering() {
        var coverage = ClaimCoverage.of(Map.of(
                "roles", ClaimCoverage.Entry.present(Set.of("operator", "Admin")),
                "Groups", ClaimCoverage.Entry.present(Set.of("dispatch"))));
        assertThat(coverage.entries().keySet()).containsExactly("Groups", "roles");
        assertThat(coverage.values("roles")).containsExactly("Admin", "operator");
        assertThat(coverage.state("roles")).isEqualTo(ClaimCoverage.State.PRESENT);
        assertThat(coverage.state("Roles")).isEqualTo(ClaimCoverage.State.UNKNOWN);
        assertThat(coverage).isEqualTo(ClaimCoverage.of(Map.of(
                "Groups", ClaimCoverage.Entry.present(Set.of("dispatch")),
                "roles", ClaimCoverage.Entry.present(Set.of("Admin", "operator")))));
        assertThat(coverage.hashCode()).isEqualTo(ClaimCoverage.of(coverage.entries()).hashCode());
    }

    @Test
    void allNonPresentStatesHaveNoValues() {
        var coverage = ClaimCoverage.of(Map.of(
                "absent", ClaimCoverage.Entry.absent(),
                "malformed", ClaimCoverage.Entry.malformed(),
                "unknown", ClaimCoverage.Entry.unknown()));
        assertThat(coverage.state("absent")).isEqualTo(ClaimCoverage.State.ABSENT);
        assertThat(coverage.state("malformed")).isEqualTo(ClaimCoverage.State.MALFORMED);
        assertThat(coverage.state("unknown")).isEqualTo(ClaimCoverage.State.UNKNOWN);
        for (String name : coverage.entries().keySet()) assertThat(coverage.values(name)).isEmpty();
        assertThatThrownBy(() -> new ClaimCoverage.Entry(
                ClaimCoverage.State.MALFORMED, Set.of("bad")))
                .isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> new ClaimCoverage.Entry(
                ClaimCoverage.State.UNKNOWN, Set.of("bad")))
                .isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> new ClaimCoverage.Entry(
                ClaimCoverage.State.ABSENT, Set.of("bad")))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void unrequestedNamesAreUnknownNotAbsent() {
        var coverage = ClaimCoverage.empty();
        assertThat(coverage.state("new-claim")).isEqualTo(ClaimCoverage.State.UNKNOWN);
        assertThat(coverage.values("new-claim")).isEmpty();
        assertThat(coverage.entries()).isEmpty();
        assertThat(coverage.state("new-claim")).isNotEqualTo(ClaimCoverage.State.ABSENT);
    }

    @Test
    void knownAbsenceDoesNotInventValues() {
        var coverage = ClaimCoverage.of(Map.of("groups", ClaimCoverage.Entry.absent()));
        assertThat(coverage.entries()).containsKey("groups");
        assertThat(coverage.values("groups")).isEmpty();
        assertThat(coverage.state("groups")).isEqualTo(ClaimCoverage.State.ABSENT);
        assertThat(coverage.state("roles")).isEqualTo(ClaimCoverage.State.UNKNOWN);
    }

    @Test
    void constructorRejectsDuplicateClaimNames() {
        var one = new ClaimCoverage.NamedClaim("roles", ClaimCoverage.Entry.absent());
        var two = new ClaimCoverage.NamedClaim("roles", ClaimCoverage.Entry.unknown());
        assertThatThrownBy(() -> new ClaimCoverage(List.of(one, two)))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void mapInputIsDefensivelyCopied() {
        var mutableValues = new HashSet<>(Set.of("one"));
        var entry = ClaimCoverage.Entry.present(mutableValues);
        mutableValues.add("two");
        var mutableMap = new HashMap<String, ClaimCoverage.Entry>();
        mutableMap.put("groups", entry);
        var coverage = ClaimCoverage.of(mutableMap);
        mutableMap.clear();
        assertThat(coverage.entries()).containsOnlyKeys("groups");
        assertThat(coverage.values("groups")).containsExactly("one");
        assertThatThrownBy(() -> coverage.entries().clear())
                .isInstanceOf(UnsupportedOperationException.class);
        assertThatThrownBy(() -> coverage.values("groups").add("new"))
                .isInstanceOf(UnsupportedOperationException.class);
    }

    @Test
    void nameLimitsEnforceCodePointsAndUtf8Bytes() {
        String codepoint = new String(Character.toChars(0x1F642));
        assertThat(ClaimCoverage.of(Map.of(codepoint.repeat(120),
                ClaimCoverage.Entry.absent())).entries()).hasSize(1);
        assertThatThrownBy(() -> ClaimCoverage.of(Map.of(codepoint.repeat(121),
                ClaimCoverage.Entry.absent())))
                .isInstanceOf(IllegalArgumentException.class);
        assertThat(ClaimCoverage.of(Map.of("x".repeat(120),
                ClaimCoverage.Entry.absent())).entries()).hasSize(1);
        assertThatThrownBy(() -> ClaimCoverage.of(Map.of("x".repeat(121),
                ClaimCoverage.Entry.absent())))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void valueLimitsEnforceUtf8ByteEnvelope() {
        var one = new String(Character.toChars(0x1F642));
        assertThat(ClaimCoverage.Entry.present(Set.of(one.repeat(256))).values())
                .hasSize(1);
        assertThatThrownBy(() -> ClaimCoverage.Entry.present(Set.of(one.repeat(257))))
                .isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> ClaimCoverage.Entry.present(Set.of("v".repeat(1025))))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void entryCountIsLimited() {
        Map<String, ClaimCoverage.Entry> sixtyFour = new HashMap<>();
        for (int i = 0; i < 64; i++)
            sixtyFour.put("claim-" + i, ClaimCoverage.Entry.absent());
        assertThat(ClaimCoverage.of(sixtyFour).entries()).hasSize(64);
        sixtyFour.put("extra", ClaimCoverage.Entry.unknown());
        assertThatThrownBy(() -> ClaimCoverage.of(sixtyFour))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void valuesPerNameAreLimited() {
        Set<String> values = new TreeSet<>();
        for (int i = 0; i < 256; i++) values.add("v-" + i);
        assertThat(ClaimCoverage.Entry.present(values).values()).hasSize(256);
        values.add("overflow");
        assertThatThrownBy(() -> ClaimCoverage.Entry.present(values))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void combinedPayloadEnvelopeIsLimited() {
        Map<String, ClaimCoverage.Entry> claims = new HashMap<>();
        for (int i = 0; i < 15; i++)
            claims.put("c" + i, ClaimCoverage.Entry.present(Set.of("v".repeat(1000))));
        assertThat(ClaimCoverage.of(claims).entries()).hasSize(15);
        for (int i = 15; i < 18; i++)
            claims.put("c" + i, ClaimCoverage.Entry.present(Set.of("v".repeat(1000))));
        assertThatThrownBy(() -> ClaimCoverage.of(claims))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void invalidControlAndUnicodeRejected() {
        String loneHigh = String.valueOf((char) 0xD800);
        String loneLow = String.valueOf((char) 0xDC00);
        for (String invalid : Arrays.asList("has\nbreak", "nul\0here", loneHigh, loneLow)) {
            assertThatThrownBy(() -> ClaimCoverage.of(Map.of(
                    invalid, ClaimCoverage.Entry.absent())))
                    .isInstanceOf(IllegalArgumentException.class);
            assertThatThrownBy(() -> ClaimCoverage.Entry.present(Set.of(invalid)))
                    .isInstanceOf(IllegalArgumentException.class);
        }
        assertThatThrownBy(() -> ClaimCoverage.empty().state(loneHigh))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void nullBlankAndMalformedStateInputsRejected() {
        assertThatThrownBy(() -> new ClaimCoverage.Entry(
                ClaimCoverage.State.PRESENT, Set.of()))
                .isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> new ClaimCoverage.Entry(null, Set.of()))
                .isInstanceOf(NullPointerException.class);
        assertThatThrownBy(() -> new ClaimCoverage.Entry(
                ClaimCoverage.State.ABSENT, null))
                .isInstanceOf(NullPointerException.class);
        assertThatThrownBy(() -> ClaimCoverage.of(Map.of(
                " ", ClaimCoverage.Entry.unknown())))
                .isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> ClaimCoverage.Entry.present(Set.of(" ")))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void claimCoverageHasNoPermissionAuthority() {
        var coverage = ClaimCoverage.of(Map.of(
                "roles", ClaimCoverage.Entry.present(Set.of("top-secret-group"))));
        assertThat(coverage.toString()).doesNotContain("top-secret-group", "roles");
        assertThat(coverage.entries().get("roles").toString()).doesNotContain("top-secret-group");
        assertThat(Arrays.stream(ClaimCoverage.class.getDeclaredMethods())
                .map(java.lang.reflect.Method::getName))
                .doesNotContain("authorize", "grant", "permit", "authenticate");
    }

    @Test
    void nullMapsAndEntriesReject() {
        assertThatThrownBy(() -> ClaimCoverage.of(null))
                .isInstanceOf(NullPointerException.class);
        assertThatThrownBy(() -> new ClaimCoverage(null))
                .isInstanceOf(NullPointerException.class);
        var bad = new HashMap<String, ClaimCoverage.Entry>();
        bad.put("claims", null);
        assertThatThrownBy(() -> ClaimCoverage.of(bad))
                .isInstanceOf(NullPointerException.class);
        List<ClaimCoverage.NamedClaim> entries = new ArrayList<>();
        entries.add(null);
        assertThatThrownBy(() -> new ClaimCoverage(entries))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
