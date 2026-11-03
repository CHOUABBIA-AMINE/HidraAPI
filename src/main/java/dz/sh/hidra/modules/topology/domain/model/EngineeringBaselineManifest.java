/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : EngineeringBaselineManifest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-03
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.domain.model
 *
 * @Description : Immutable draft manifest of exact engineering source selections and completeness findings.
 *
 */
package dz.sh.hidra.modules.topology.domain.model;

import java.time.Instant;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

/** Data invariants only: construction supplies neither owner verification nor permission. */
public record EngineeringBaselineManifest(
        UUID manifestId, String companyPartyId, String pipelineSystemId, long version,
        Instant effectiveFrom, Instant effectiveUntil, List<Pin> pins) {
    public enum Kind { NETWORK_PUBLICATION, STATION_CONFIGURATION, CHARACTERISTIC_LINK, PRODUCT_PUBLICATION }

    /**
     * ownerId selects the owner scope (network context, facility, unit or product draft).
     * sourceId selects its publication context, configuration, link or catalog product.
     * revisionId and sha256 always identify an exact immutable source, never 'latest'.
     */
    public record Pin(Kind kind, String ownerId, String sourceId, String revisionId, String sha256) {
        public Pin {
            Objects.requireNonNull(kind, "Source kind required");
            ownerId = identity(ownerId);
            sourceId = identity(sourceId);
            revisionId = identity(revisionId);
            if (sha256 == null || !sha256.matches("[0-9a-f]{64}"))
                throw invalid("Exact lowercase SHA-256 required");
        }
        public Key key() { return new Key(kind, ownerId, sourceId); }
        @Override public String toString() { return "EngineeringSourcePin[redacted]"; }
    }

    /** A selection has one revision per source in a manifest; separate sources remain distinct. */
    public record Key(Kind kind, String ownerId, String sourceId) {
        public Key { Objects.requireNonNull(kind); ownerId = identity(ownerId); sourceId = identity(sourceId); }
        @Override public String toString() { return "EngineeringSourceKey[redacted]"; }
    }

    /** Stable, machine-readable missing-fact diagnostics; no protected labels or driver messages. */
    public enum Finding {
        NETWORK_NOT_SELECTED, PRODUCT_NOT_SELECTED, GRAPH_NOT_AUTHORED, GRAPH_INCOMPLETE, SOURCE_DATA_INCOMPLETE,
        ROUTE_PROFILE_NOT_SUPPLIED, SECTIONS_NOT_SUPPLIED, STATION_CONFIGURATION_NOT_SELECTED,
        CHARACTERISTIC_NOT_SELECTED, APPROVAL_NOT_ESTABLISHED, METHOD_NOT_SUPPLIED,
        SOURCE_INTERVAL_NOT_COVERED, SOURCE_NOT_CURRENTLY_ELIGIBLE
    }

    public record Diagnostic(Finding finding, Kind kind, String ownerId) {
        public Diagnostic {
            Objects.requireNonNull(finding);
            if ((kind == null) != (ownerId == null)) throw invalid("Complete diagnostic scope required");
            if (ownerId != null) ownerId = identity(ownerId);
        }
        @Override public String toString() { return "EngineeringCompletenessDiagnostic[redacted]"; }
    }

    /** A report concerns the draft only and never confers freeze/engineering/operational authority. */
    public record Completeness(List<Diagnostic> diagnostics) {
        public Completeness {
            diagnostics = List.copyOf(Objects.requireNonNull(diagnostics));
            if (new HashSet<>(diagnostics).size() != diagnostics.size())
                throw invalid("Duplicate completeness finding");
        }
        public boolean complete() { return diagnostics.isEmpty(); }
        public boolean engineeringQualified() { return false; }
        public boolean authoritative() { return false; }
        public boolean frozen() { return false; }
    }

    private static final Comparator<Pin> ORDER = Comparator.comparing(Pin::kind)
            .thenComparing(Pin::ownerId).thenComparing(Pin::sourceId);

    public EngineeringBaselineManifest {
        Objects.requireNonNull(manifestId, "Manifest identity required");
        companyPartyId = identity(companyPartyId);
        pipelineSystemId = identity(pipelineSystemId);
        if (version < 1) throw invalid("Positive manifest version required");
        instant(effectiveFrom);
        if (effectiveUntil != null) {
            instant(effectiveUntil);
            if (!effectiveUntil.isAfter(effectiveFrom)) throw invalid("Nonempty half-open interval required");
        }
        pins = List.copyOf(Objects.requireNonNull(pins, "Explicit source selections required"));
        Set<Key> keys = new HashSet<>();
        Set<String> singularOwners = new HashSet<>();
        for (Pin pin : pins) {
            if (!keys.add(pin.key())) throw invalid("Duplicate source selection");
            if (pin.kind() != Kind.CHARACTERISTIC_LINK
                    && !singularOwners.add(pin.kind().name() + ":" + pin.ownerId()))
                throw invalid("One exact source selection per owner");
        }
        if (pins.stream().filter(p -> p.kind() == Kind.NETWORK_PUBLICATION).count() > 1)
            throw invalid("One exact network publication per draft manifest");
        pins = pins.stream().sorted(ORDER).toList();
    }

    /** No caller-selected version, scope or manifest identity can replace the current draft. */
    public EngineeringBaselineManifest replace(long expectedVersion, Instant from, Instant until, List<Pin> selections) {
        if (expectedVersion != version) throw invalid("Expected draft version mismatch");
        return new EngineeringBaselineManifest(manifestId, companyPartyId, pipelineSystemId,
                Math.addExact(version, 1), from, until, selections);
    }

    /** Temporal coverage is evaluated against exact source intervals, without guessing industrial values. */
    public boolean coveredBy(Instant sourceFrom, Instant sourceUntil) {
        instant(sourceFrom);
        if (sourceUntil != null) {
            instant(sourceUntil);
            if (!sourceUntil.isAfter(sourceFrom)) throw invalid("Invalid source interval");
        }
        return !effectiveFrom.isBefore(sourceFrom)
                && (sourceUntil == null || effectiveUntil != null && !effectiveUntil.isAfter(sourceUntil));
    }

    public boolean engineeringQualified() { return false; }
    public boolean authoritative() { return false; }
    public boolean frozen() { return false; }
    @Override public String toString() { return "EngineeringBaselineManifest[draft,redacted]"; }

    public static String identity(String value) {
        if (value == null || value.isBlank() || !value.equals(value.strip())
                || value.codePointCount(0,value.length()) > 80)
            throw invalid("Exact bounded identity required");
        for (int at = 0; at < value.length();) {
            int cp = value.codePointAt(at);
            if (Character.isISOControl(cp) || cp >= 0xd800 && cp <= 0xdfff)
                throw invalid("Invalid identity Unicode");
            at += Character.charCount(cp);
        }
        return value;
    }
    private static void instant(Instant value) {
        if (value == null || value.getNano() % 1000 != 0)
            throw invalid("Explicit PostgreSQL-microsecond instant required");
    }
    private static IllegalArgumentException invalid(String message) { return new IllegalArgumentException(message); }
}
