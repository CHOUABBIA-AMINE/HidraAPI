/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ClaimNameCatalogPort
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-16
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.application.port.out
 *
 * @Description : Selects only configured claim names without granting trust or authorization.
 *
 */
package dz.sh.hidra.modules.identity.application.port.out;

import dz.sh.hidra.modules.identity.domain.value.ClaimCoverage;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.TreeSet;

/**
 * Identity-private mapping metadata; never a claim assertion, trusted snapshot, stable
 * mapping generation, token evidence, or authorization decision.
 */
public interface ClaimNameCatalogPort {
    /**
     * Read a momentary, NON-AUTHORITATIVE provider claim-name selection. This result is NOT
     * STABLE AGAINST CATALOG MUTATION and can never establish authenticated claim coverage.
     * The caller must not issue a token or permit from this selection alone.
     */
    Selection select(String providerId);

    /** Atomic, non-authoritative mapping name/generation hint. Not verified identity evidence. */
    VersionedSelection selectVersioned(String providerId);

    record VersionedSelection(Selection selection, long generation) {
        public VersionedSelection {
            Objects.requireNonNull(selection, "selection");
            if (generation <= 0) throw new IllegalArgumentException("Invalid mapping generation");
        }

        @Override public String toString() {
            return "VersionedSelection[claimCount=" + selection.names().size()
                    + ", names=<redacted>, generation=<redacted>, authority=none]";
        }
    }

    /** Immutable sorted, distinct exact names, with no values and no authority to grant access. */
    record Selection(List<String> names) {
        public Selection {
            Objects.requireNonNull(names, "names");
            TreeSet<String> unique = new TreeSet<>();
            for (String name : names) {
                if (name == null || !unique.add(name))
                    throw new IllegalArgumentException("Invalid claim-name selection");
            }
            if (unique.size() > ClaimCoverage.MAX_NAMES)
                throw new IllegalArgumentException("Invalid claim-name selection");
            // Reuse domain text, UTF-8 and total-envelope checks without asserting any
            // claim's presence or absence at an identity provider.
            var unknown = new ArrayList<ClaimCoverage.NamedClaim>(unique.size());
            for (String name : unique)
                unknown.add(new ClaimCoverage.NamedClaim(name, ClaimCoverage.Entry.unknown()));
            new ClaimCoverage(unknown);
            names = List.copyOf(unique);
        }

        @Override
        public String toString() {
            return "Selection[claimCount=" + names.size() + ", names=<redacted>, authority=none]";
        }
    }
}
