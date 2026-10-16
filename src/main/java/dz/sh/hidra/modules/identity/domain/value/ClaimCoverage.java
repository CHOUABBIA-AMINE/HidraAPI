/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ClaimCoverage
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-16
 *
 * @Type        : Class
 * @Layer       : Domain
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.domain.value
 *
 * @Description : Represents bounded source-claim coverage without authenticating or authorizing it.
 *
 */
package dz.sh.hidra.modules.identity.domain.value;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.TreeSet;

/**
 * An immutable description of supplied claim coverage, never proof of verified origin.
 *
 * <p>Only the later trusted producer/issuance protocol may attest that ABSENT means an
 * authenticated source actually checked the requested claim. A caller can construct this
 * value object; therefore it MUST NOT be used as an authorization credential or grant.</p>
 */
public final class ClaimCoverage {
    public static final int MAX_NAMES = 64;
    public static final int MAX_NAME_CODE_POINTS = 120;
    public static final int MAX_NAME_BYTES = 480;
    public static final int MAX_VALUES_PER_NAME = 256;
    public static final int MAX_VALUE_BYTES = 1024;
    public static final int MAX_TOTAL_BYTES = 16384;

    public enum State {
        PRESENT, ABSENT, MALFORMED, UNKNOWN
    }

    /** A state and its exact immutable values; only PRESENT can carry any values. */
    public record Entry(State state, Set<String> values) {
        public Entry {
            Objects.requireNonNull(state, "state");
            Objects.requireNonNull(values, "values");
            if (values.size() > MAX_VALUES_PER_NAME) throw invalid();
            TreeSet<String> sorted = new TreeSet<>();
            for (String value : values) {
                validateText(value, MAX_VALUE_BYTES);
                sorted.add(value);
            }
            if ((state == State.PRESENT) != !sorted.isEmpty()) throw invalid();
            values = Collections.unmodifiableSortedSet(sorted);
        }

        public static Entry present(Set<String> values) {
            return new Entry(State.PRESENT, values);
        }

        public static Entry absent() {
            return new Entry(State.ABSENT, Set.of());
        }

        public static Entry malformed() {
            return new Entry(State.MALFORMED, Set.of());
        }

        public static Entry unknown() {
            return new Entry(State.UNKNOWN, Set.of());
        }

        @Override
        public String toString() {
            return "Entry[state=" + state + ", values=<redacted>]";
        }
    }

    /** Used when duplicate claim names must be detected before assembling a map. */
    public record NamedClaim(String name, Entry entry) {
        public NamedClaim {
            validateName(name);
            Objects.requireNonNull(entry, "entry");
        }

        @Override
        public String toString() {
            return "NamedClaim[<redacted>]";
        }
    }

    private final SortedMap<String, Entry> entries;

    public ClaimCoverage(Collection<NamedClaim> claims) {
        Objects.requireNonNull(claims, "claims");
        if (claims.size() > MAX_NAMES) throw invalid();
        TreeMap<String, Entry> copy = new TreeMap<>();
        long totalBytes = 0;
        for (NamedClaim claim : claims) {
            if (claim == null || copy.putIfAbsent(claim.name(), claim.entry()) != null)
                throw invalid();
            totalBytes += bytes(claim.name());
            for (String value : claim.entry().values()) totalBytes += bytes(value);
            if (totalBytes > MAX_TOTAL_BYTES) throw invalid();
        }
        entries = Collections.unmodifiableSortedMap(copy);
    }

    /** Convenience for already unique map keys; use the collection constructor for duplicate detection. */
    public static ClaimCoverage of(Map<String, Entry> input) {
        Objects.requireNonNull(input, "input");
        if (input.size() > MAX_NAMES) throw invalid();
        List<NamedClaim> claims = new ArrayList<>(input.size());
        input.forEach((name, entry) -> claims.add(new NamedClaim(name, entry)));
        return new ClaimCoverage(claims);
    }

    public static ClaimCoverage empty() {
        return new ClaimCoverage(List.of());
    }

    /** Missing or unrequested names are UNKNOWN, never presumed ABSENT. */
    public State state(String name) {
        validateName(name);
        Entry entry = entries.get(name);
        return entry == null ? State.UNKNOWN : entry.state();
    }

    /** Never supplies claim values for absent, malformed, unknown or unrequested names. */
    public Set<String> values(String name) {
        validateName(name);
        Entry entry = entries.get(name);
        return entry == null ? Set.of() : entry.values();
    }

    /** Immutable, lexicographically ordered, exact name-to-state snapshot. */
    public SortedMap<String, Entry> entries() {
        return entries;
    }

    @Override
    public boolean equals(Object other) {
        return other instanceof ClaimCoverage coverage && entries.equals(coverage.entries);
    }

    @Override
    public int hashCode() {
        return entries.hashCode();
    }

    @Override
    public String toString() {
        return "ClaimCoverage[count=" + entries.size() + ", claims=<redacted>]";
    }

    private static void validateName(String name) {
        validateText(name, MAX_NAME_BYTES);
        if (name.codePointCount(0, name.length()) > MAX_NAME_CODE_POINTS) throw invalid();
    }

    private static void validateText(String value, int maxBytes) {
        if (value == null || value.isBlank()) throw invalid();
        for (int index = 0; index < value.length(); index++) {
            char ch = value.charAt(index);
            if (Character.isISOControl(ch)) throw invalid();
            if (Character.isHighSurrogate(ch)) {
                if (++index >= value.length()
                        || !Character.isLowSurrogate(value.charAt(index))) throw invalid();
            } else if (Character.isLowSurrogate(ch)) {
                throw invalid();
            }
        }
        if (bytes(value) > maxBytes) throw invalid();
    }

    private static int bytes(String value) {
        return value.getBytes(StandardCharsets.UTF_8).length;
    }

    private static IllegalArgumentException invalid() {
        return new IllegalArgumentException("Invalid bounded claim coverage");
    }
}
