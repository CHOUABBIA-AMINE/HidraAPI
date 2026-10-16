/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : OidcClaimCoverageProjector
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-16
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.infrastructure.security
 *
 * @Description : Projects selected OIDC-shaped claim values into bounded, non-authoritative coverage.
 *
 */
package dz.sh.hidra.modules.identity.infrastructure.security;

import dz.sh.hidra.modules.identity.application.port.out.ClaimNameCatalogPort;
import dz.sh.hidra.modules.identity.domain.value.ClaimCoverage;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;

/**
 * Pure, package-private projection of already supplied claim data. It does not decode,
 * validate, authenticate, attest, persist or authorize a JWT. In particular, the supplied
 * mapping generation is just an untrusted selection hint, never a freshness fence.
 */
final class OidcClaimCoverageProjector {
    private OidcClaimCoverageProjector() {}

    /**
     * Caller-owned claims are inspected only for explicitly selected names. Neither this
     * method nor its returned value may be used as evidence of an authenticated source.
     * No inbound value, claim name or original exception is included in an error.
     */
    static Projection project(
            Map<String, ?> suppliedClaims, ClaimNameCatalogPort.VersionedSelection selected) {
        if (suppliedClaims == null || selected == null) throw invalid();
        try {
            List<ClaimCoverage.NamedClaim> entries = new ArrayList<>();
            for (String name : selected.selection().names()) {
                entries.add(new ClaimCoverage.NamedClaim(name, resolve(suppliedClaims, name)));
            }
            return new Projection(new ClaimCoverage(entries), selected.generation());
        } catch (RuntimeException malformed) {
            throw invalid();
        }
    }

    /** Not an authenticated assertion, an authorization grant or an issuance binding. */
    record Projection(ClaimCoverage coverage, long mappingGeneration) {
        Projection {
            Objects.requireNonNull(coverage, "coverage");
            if (mappingGeneration <= 0) throw invalid();
        }

        @Override
        public String toString() {
            return "Projection[claimCount=" + coverage.entries().size()
                    + ", claims=<redacted>, generation=<redacted>, authority=none]";
        }
    }

    private static ClaimCoverage.Entry resolve(Map<String, ?> claims, String name) {
        // Exact keys take precedence, including literal keys containing a period.
        if (claims.containsKey(name)) return parseValue(claims.get(name));
        if (!name.contains(".")) return ClaimCoverage.Entry.absent();

        Object cursor = claims;
        for (String part : name.split("\\.", -1)) {
            if (part.isEmpty()) return ClaimCoverage.Entry.malformed();
            if (!(cursor instanceof Map<?, ?> values)) return ClaimCoverage.Entry.malformed();
            if (!values.containsKey(part)) return ClaimCoverage.Entry.absent();
            cursor = values.get(part);
        }
        return parseValue(cursor);
    }

    private static ClaimCoverage.Entry parseValue(Object value) {
        if (value instanceof String text) {
            if (text.isBlank()) return ClaimCoverage.Entry.malformed();
            return ClaimCoverage.Entry.present(Set.of(text));
        }
        // Decoded JWT arrays are ordered lists. No coercion of numbers, booleans,
        // arbitrary collections or nested objects to a synthetic claim value.
        if (!(value instanceof List<?> values) || values.isEmpty())
            return ClaimCoverage.Entry.malformed();
        if (values.size() > ClaimCoverage.MAX_VALUES_PER_NAME) throw invalid();

        TreeSet<String> strings = new TreeSet<>();
        for (Object candidate : values) {
            if (!(candidate instanceof String text) || text.isBlank())
                return ClaimCoverage.Entry.malformed();
            if (!strings.add(text)) throw invalid(); // no silent duplicate collapse
        }
        return ClaimCoverage.Entry.present(strings);
    }

    private static IllegalArgumentException invalid() {
        return new IllegalArgumentException("Invalid bounded OIDC claim projection");
    }
}
