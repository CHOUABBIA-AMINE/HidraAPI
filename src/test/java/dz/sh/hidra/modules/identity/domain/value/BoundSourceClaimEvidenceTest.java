/**
 * @Project : HidraAPI @Product : Hidra - Hydrocarbon Intelligence for Data, Risk, and
 * Analytics @Author : Abir MEDJERAB @Owner : Sonatrach / TRC : Digitalization Initiative @Name :
 * BoundSourceClaimEvidenceTest @CreatedOn : 2025-06-26 @UpdatedOn : 2026-10-17 @Type : Class @Layer
 * : Identity Test @Module : identity @Package :
 * dz.sh.hidra.modules.identity.domain.value @Description : Verifies structural source-claim
 * invariants and defensive representation without asserting origin.
 */
package dz.sh.hidra.modules.identity.domain.value;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.Set;

class BoundSourceClaimEvidenceTest {
    private static final String ID = "00000000-0000-4000-8000-000000000001";

    @Test
    void arraysAndCoverageAreDefensiveAndStructuralEqualityIsStable() {
        var coverage =
                ClaimCoverage.of(
                        Map.of("groups", ClaimCoverage.Entry.present(Set.of("private-value"))));
        byte[] tag = new byte[32];
        var a = new BoundSourceClaimEvidence(ID, (short) 1, 7, 9, coverage, tag);
        var b = new BoundSourceClaimEvidence(ID, (short) 1, 7, 9, coverage, tag);
        tag[0] = 1;
        a.producerAuthenticator()[0] = 2;
        assertThat(a).isEqualTo(b).hasSameHashCodeAs(b);
        assertThat(a.producerAuthenticator()).containsOnly((byte) 0);
        assertThat(a.toString()).doesNotContain(ID, "private-value", "groups");
        assertThatThrownBy(() -> coverage.entries().clear())
                .isInstanceOf(UnsupportedOperationException.class);
    }

    @Test
    void exactVersionUuidGenerationsAndTagLengthAreRequired() {
        for (String id : new String[] {"bad", "00000000-0000-4000-8000-00000000000A", "0-0-0-0-0"})
            assertThatThrownBy(
                            () ->
                                    new BoundSourceClaimEvidence(
                                            id,
                                            (short) 1,
                                            1,
                                            1,
                                            ClaimCoverage.empty(),
                                            new byte[32]))
                    .isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(
                        () ->
                                new BoundSourceClaimEvidence(
                                        ID, (short) 2, 1, 1, ClaimCoverage.empty(), new byte[32]))
                .isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(
                        () ->
                                new BoundSourceClaimEvidence(
                                        ID, (short) 1, 0, 1, ClaimCoverage.empty(), new byte[32]))
                .isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(
                        () ->
                                new BoundSourceClaimEvidence(
                                        ID, (short) 1, 1, 0, ClaimCoverage.empty(), new byte[32]))
                .isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(
                        () ->
                                new BoundSourceClaimEvidence(
                                        ID, (short) 1, 1, 1, ClaimCoverage.empty(), new byte[31]))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void structurallyValidConstructionDoesNotCreateProducerAuthority() {
        var a =
                new BoundSourceClaimEvidence(
                        ID, (short) 1, 1, 1, ClaimCoverage.empty(), new byte[32]);
        var b =
                new BoundSourceClaimEvidence(
                        ID, (short) 1, 2, 1, ClaimCoverage.empty(), new byte[32]);
        assertThat(a).isNotEqualTo(b);
        assertThat(a.toString()).contains("redacted");
    }
}
