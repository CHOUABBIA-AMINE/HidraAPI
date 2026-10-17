/**
 * @Project : HidraAPI @Product : Hidra - Hydrocarbon Intelligence for Data, Risk, and
 * Analytics @Author : Abir MEDJERAB @Owner : Sonatrach / TRC : Digitalization Initiative @Name :
 * BoundSourceClaimEvidenceCodecTest @CreatedOn : 2025-06-26 @UpdatedOn : 2026-10-17 @Type :
 * Class @Layer : Identity Test @Module : identity @Package :
 * dz.sh.hidra.modules.identity.infrastructure.persistence.adapter @Description : Verifies canonical
 * bounded payloads and rejects malformed or noncanonical representations.
 */
package dz.sh.hidra.modules.identity.infrastructure.persistence.adapter;

import static org.assertj.core.api.Assertions.*;

import dz.sh.hidra.modules.identity.domain.value.ClaimCoverage;

import org.junit.jupiter.api.Test;

import java.nio.ByteBuffer;
import java.util.*;

class BoundSourceClaimEvidenceCodecTest {
    @Test
    void emptySelectionHasCanonicalFourBytePayload() {
        assertThat(BoundSourceClaimEvidenceCodec.encode(ClaimCoverage.empty()))
                .containsExactly(0, 0, 0, 0);
        assertThat(BoundSourceClaimEvidenceCodec.decode(new byte[4]))
                .isEqualTo(ClaimCoverage.empty());
    }

    @Test
    void allStatesExactUnicodeDottedNamesAndSortedValuesRoundTrip() {
        var coverage =
                ClaimCoverage.of(
                        Map.of(
                                "a.missing",
                                ClaimCoverage.Entry.absent(),
                                "Roles",
                                ClaimCoverage.Entry.present(Set.of(" é ", "😀")),
                                "bad",
                                ClaimCoverage.Entry.malformed(),
                                "unknown",
                                ClaimCoverage.Entry.unknown()));
        byte[] payload = BoundSourceClaimEvidenceCodec.encode(coverage);
        assertThat(BoundSourceClaimEvidenceCodec.decode(payload)).isEqualTo(coverage);
        assertThat(
                        BoundSourceClaimEvidenceCodec.encode(
                                BoundSourceClaimEvidenceCodec.decode(payload)))
                .isEqualTo(payload);
        assertThat(coverage.state("unselected")).isEqualTo(ClaimCoverage.State.UNKNOWN);
    }

    @Test
    void truncationTrailingCountsAndUnsupportedStatesRejectWithoutPayloadDiagnostics() {
        byte[] valid =
                BoundSourceClaimEvidenceCodec.encode(
                        ClaimCoverage.of(Map.of("a", ClaimCoverage.Entry.absent())));
        for (int n = 0; n < valid.length; n++) {
            byte[] prefix = Arrays.copyOf(valid, n);
            denied(prefix);
        }
        denied(Arrays.copyOf(valid, valid.length + 1));
        for (int n : new int[] {-1, 65, Integer.MAX_VALUE})
            denied(ByteBuffer.allocate(4).putInt(n).array());
        byte[] state = valid.clone();
        state[9] = 99;
        denied(state);
        byte[] count = valid.clone();
        ByteBuffer.wrap(count).putInt(10, 1);
        denied(count);
    }

    @Test
    void invalidUtf8ControlsDuplicateAndUnsortedEntriesReject() {
        byte[] valid =
                BoundSourceClaimEvidenceCodec.encode(
                        ClaimCoverage.of(Map.of("a", ClaimCoverage.Entry.absent())));
        for (byte b : new byte[] {(byte) 0x80, 0, 10}) {
            byte[] bad = valid.clone();
            bad[8] = b;
            denied(bad);
        }
        byte[] second = Arrays.copyOfRange(valid, 4, valid.length);
        ByteBuffer dup =
                ByteBuffer.allocate(4 + 2 * second.length).putInt(2).put(second).put(second);
        denied(dup.array());
        byte[] pair =
                BoundSourceClaimEvidenceCodec.encode(
                        ClaimCoverage.of(
                                Map.of(
                                        "a",
                                        ClaimCoverage.Entry.absent(),
                                        "b",
                                        ClaimCoverage.Entry.absent())));
        pair[8] = 'b';
        pair[18] = 'a';
        denied(pair);
    }

    @Test
    void semanticAndEncodedLimitsAreIndependentAndBoundedBeforeAllocation() {
        denied(new byte[BoundSourceClaimEvidenceCodec.MAX_ENCODED_BYTES + 1]);
        var map = new TreeMap<String, ClaimCoverage.Entry>();
        for (int i = 0; i < 64; i++) map.put("n" + i, ClaimCoverage.Entry.absent());
        var coverage = ClaimCoverage.of(map);
        assertThat(
                        BoundSourceClaimEvidenceCodec.decode(
                                BoundSourceClaimEvidenceCodec.encode(coverage)))
                .isEqualTo(coverage);
        var values = new TreeSet<String>();
        for (int i = 0; i < 256; i++) values.add("v" + i);
        var many = ClaimCoverage.of(Map.of("a", ClaimCoverage.Entry.present(values)));
        assertThat(BoundSourceClaimEvidenceCodec.decode(BoundSourceClaimEvidenceCodec.encode(many)))
                .isEqualTo(many);
        byte[] bad =
                BoundSourceClaimEvidenceCodec.encode(
                        ClaimCoverage.of(Map.of("a", ClaimCoverage.Entry.absent())));
        ByteBuffer.wrap(bad).putInt(4, Integer.MAX_VALUE);
        denied(bad);
    }

    private void denied(byte[] data) {
        assertThatThrownBy(() -> BoundSourceClaimEvidenceCodec.decode(data))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Invalid bounded source-claim payload.")
                .hasNoCause();
    }
}
