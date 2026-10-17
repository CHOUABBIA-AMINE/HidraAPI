/**
 * @Project : HidraAPI @Product : Hidra - Hydrocarbon Intelligence for Data, Risk, and
 * Analytics @Author : Abir MEDJERAB @Owner : Sonatrach / TRC : Digitalization Initiative @Name :
 * BoundSourceClaimEvidenceCodec @CreatedOn : 2025-06-26 @UpdatedOn : 2026-10-17 @Type :
 * Class @Layer : Infrastructure @Module : identity @Package :
 * dz.sh.hidra.modules.identity.infrastructure.persistence.adapter @Description : Encodes and
 * validates canonical bounded claim payloads without asserting their origin.
 */
package dz.sh.hidra.modules.identity.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.identity.domain.value.ClaimCoverage;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.nio.ByteBuffer;
import java.nio.charset.CodingErrorAction;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import java.util.TreeSet;

/** Canonical bounded format; decoded values remain untrusted structural data. */
public final class BoundSourceClaimEvidenceCodec {
    public static final int MAX_ENCODED_BYTES = 98304;

    private BoundSourceClaimEvidenceCodec() {}

    public static byte[] encode(ClaimCoverage coverage) {
        Objects.requireNonNull(coverage);
        try {
            var bytes = new ByteArrayOutputStream();
            var out = new DataOutputStream(bytes);
            out.writeInt(coverage.entries().size());
            for (var pair : coverage.entries().entrySet()) {
                write(out, pair.getKey());
                out.writeByte(
                        switch (pair.getValue().state()) {
                            case PRESENT -> 1;
                            case ABSENT -> 2;
                            case MALFORMED -> 3;
                            case UNKNOWN -> 4;
                        });
                out.writeInt(pair.getValue().values().size());
                for (String value : pair.getValue().values()) write(out, value);
            }
            out.flush();
            if (bytes.size() > MAX_ENCODED_BYTES) throw invalid();
            return bytes.toByteArray();
        } catch (java.io.IOException failure) {
            throw invalid();
        }
    }

    public static ClaimCoverage decode(byte[] payload) {
        try {
            if (payload == null || payload.length < 4 || payload.length > MAX_ENCODED_BYTES)
                throw invalid();
            var in = ByteBuffer.wrap(payload);
            int names = count(in.getInt(), ClaimCoverage.MAX_NAMES);
            var entries = new ArrayList<ClaimCoverage.NamedClaim>(names);
            String previous = null;
            for (int i = 0; i < names; i++) {
                String name = read(in, ClaimCoverage.MAX_NAME_BYTES);
                if (previous != null && previous.compareTo(name) >= 0) throw invalid();
                previous = name;
                ClaimCoverage.State state =
                        switch (in.get()) {
                            case 1 -> ClaimCoverage.State.PRESENT;
                            case 2 -> ClaimCoverage.State.ABSENT;
                            case 3 -> ClaimCoverage.State.MALFORMED;
                            case 4 -> ClaimCoverage.State.UNKNOWN;
                            default -> throw invalid();
                        };
                int n = count(in.getInt(), ClaimCoverage.MAX_VALUES_PER_NAME);
                var values = new TreeSet<String>();
                String last = null;
                for (int j = 0; j < n; j++) {
                    String value = read(in, ClaimCoverage.MAX_VALUE_BYTES);
                    if (last != null && last.compareTo(value) >= 0) throw invalid();
                    last = value;
                    values.add(value);
                }
                entries.add(
                        new ClaimCoverage.NamedClaim(name, new ClaimCoverage.Entry(state, values)));
            }
            if (in.hasRemaining()) throw invalid();
            var result = new ClaimCoverage(entries);
            if (!Arrays.equals(payload, encode(result))) throw invalid();
            return result;
        } catch (RuntimeException | java.nio.charset.CharacterCodingException failure) {
            throw invalid();
        }
    }

    private static int count(int n, int max) {
        if (n < 0 || n > max) throw invalid();
        return n;
    }

    private static void write(DataOutputStream out, String text) throws java.io.IOException {
        byte[] bytes = text.getBytes(StandardCharsets.UTF_8);
        out.writeInt(bytes.length);
        out.write(bytes);
    }

    private static String read(ByteBuffer in, int max)
            throws java.nio.charset.CharacterCodingException {
        int size = in.getInt();
        if (size <= 0 || size > max || size > in.remaining()) throw invalid();
        var slice = in.slice();
        slice.limit(size);
        in.position(in.position() + size);
        return StandardCharsets.UTF_8
                .newDecoder()
                .onMalformedInput(CodingErrorAction.REPORT)
                .onUnmappableCharacter(CodingErrorAction.REPORT)
                .decode(slice)
                .toString();
    }

    private static IllegalArgumentException invalid() {
        return new IllegalArgumentException("Invalid bounded source-claim payload.");
    }
}
