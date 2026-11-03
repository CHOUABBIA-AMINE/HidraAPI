/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : EngineeringBaselineCompletenessPolicy
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-03
 *
 * @Type        : Class
 * @Layer       : Domain
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.domain.service
 *
 * @Description : Reconciles exact selected source observations and owner-derived required membership.
 *
 */
package dz.sh.hidra.modules.topology.domain.service;

import dz.sh.hidra.modules.topology.domain.model.EngineeringBaselineManifest;
import dz.sh.hidra.modules.topology.domain.model.EngineeringBaselineManifest.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/** All inputs are data. The application must obtain and authorize them through the actual source owners. */
public final class EngineeringBaselineCompletenessPolicy {
    public record Requirement(Kind kind, String ownerId) {
        public Requirement { Objects.requireNonNull(kind); ownerId = EngineeringBaselineManifest.identity(ownerId); }
    }
    public record SourceFacts(Pin pin, Instant effectiveFrom, Instant effectiveUntil,
            Set<Requirement> requiredSources, List<Finding> findings) {
        public SourceFacts {
            Objects.requireNonNull(pin);
            if (effectiveFrom == null || effectiveFrom.getNano() % 1000 != 0
                    || effectiveUntil != null && (effectiveUntil.getNano() % 1000 != 0
                        || !effectiveUntil.isAfter(effectiveFrom))) throw invalid();
            requiredSources = Set.copyOf(Objects.requireNonNull(requiredSources));
            findings = List.copyOf(Objects.requireNonNull(findings));
            if (new HashSet<>(findings).size() != findings.size()) throw invalid();
            for (Requirement requirement : requiredSources)
                if (!(pin.kind() == Kind.NETWORK_PUBLICATION && requirement.kind() == Kind.STATION_CONFIGURATION
                        || pin.kind() == Kind.STATION_CONFIGURATION && requirement.kind() == Kind.CHARACTERISTIC_LINK))
                    throw invalid();
        }
        @Override public String toString() { return "EngineeringSourceFacts[redacted]"; }
    }

    public Completeness inspect(EngineeringBaselineManifest manifest, List<SourceFacts> observations) {
        Objects.requireNonNull(manifest);
        observations = List.copyOf(Objects.requireNonNull(observations));
        var byKey = new HashMap<Key, SourceFacts>();
        var required = new HashSet<Requirement>();
        for (SourceFacts facts : observations) {
            if (byKey.putIfAbsent(facts.pin().key(), facts) != null) throw invalid();
            required.addAll(facts.requiredSources());
        }
        if (byKey.size() != manifest.pins().size()) throw invalid();
        var selected = new HashSet<Requirement>();
        var diagnostics = new HashSet<Diagnostic>();
        for (Pin pin : manifest.pins()) {
            SourceFacts facts = byKey.get(pin.key());
            if (facts == null || !facts.pin().equals(pin)) throw invalid();
            Requirement selection = new Requirement(pin.kind(), pin.ownerId());
            selected.add(selection);
            if ((pin.kind() == Kind.STATION_CONFIGURATION || pin.kind() == Kind.CHARACTERISTIC_LINK)
                    && !required.contains(selection)) throw invalid();
            if (!manifest.coveredBy(facts.effectiveFrom(), facts.effectiveUntil()))
                diagnostics.add(new Diagnostic(Finding.SOURCE_INTERVAL_NOT_COVERED, pin.kind(), pin.ownerId()));
            for (Finding finding : facts.findings())
                diagnostics.add(new Diagnostic(finding, pin.kind(), pin.ownerId()));
        }
        if (manifest.pins().stream().noneMatch(p -> p.kind() == Kind.NETWORK_PUBLICATION))
            diagnostics.add(new Diagnostic(Finding.NETWORK_NOT_SELECTED, null, null));
        if (manifest.pins().stream().noneMatch(p -> p.kind() == Kind.PRODUCT_PUBLICATION))
            diagnostics.add(new Diagnostic(Finding.PRODUCT_NOT_SELECTED, null, null));
        for (Requirement requirement : required)
            if (!selected.contains(requirement)) {
                Finding finding = switch (requirement.kind()) {
                    case STATION_CONFIGURATION -> Finding.STATION_CONFIGURATION_NOT_SELECTED;
                    case CHARACTERISTIC_LINK -> Finding.CHARACTERISTIC_NOT_SELECTED;
                    default -> throw invalid();
                };
                diagnostics.add(new Diagnostic(finding, requirement.kind(), requirement.ownerId()));
            }
        var result = new ArrayList<>(diagnostics);
        result.sort(Comparator.comparing(Diagnostic::finding)
                .thenComparing(d -> d.kind() == null ? "" : d.kind().name())
                .thenComparing(d -> d.ownerId() == null ? "" : d.ownerId()));
        return new Completeness(result);
    }
    private static IllegalArgumentException invalid() { return new IllegalArgumentException("Inconsistent owner source observations"); }
}
