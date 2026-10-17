/**
 * @Project : HidraAPI @Product : Hidra - Hydrocarbon Intelligence for Data, Risk, and
 * Analytics @Author : Abir MEDJERAB @Owner : Sonatrach / TRC : Digitalization Initiative @Name :
 * LdapClaimCoverageProjector @CreatedOn : 2025-06-26 @UpdatedOn : 2026-10-17 @Type : Class @Layer :
 * Infrastructure @Module : identity @Package :
 * dz.sh.hidra.modules.identity.infrastructure.security @Description : Projects bounded
 * authenticated-entry observations without claiming directory completeness or authority.
 */
package dz.sh.hidra.modules.identity.infrastructure.security;

import dz.sh.hidra.modules.identity.application.port.out.ClaimNameCatalogPort;
import dz.sh.hidra.modules.identity.domain.value.ClaimCoverage;

import java.util.*;

import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;

/** Structural projection only. Omission is UNKNOWN, never an authoritative directory absence. */
final class LdapClaimCoverageProjector {
    private LdapClaimCoverageProjector() {}

    record Projection(ClaimCoverage coverage, long mappingGeneration) {
        Projection {
            Objects.requireNonNull(coverage);
            if (mappingGeneration <= 0) throw invalid();
        }

        @Override
        public String toString() {
            return "DirectoryProjection[<redacted>, authority=none]";
        }
    }

    static void validateSelection(ClaimNameCatalogPort.VersionedSelection selected) {
        if (selected == null) throw invalid();
        Set<String> folded = new HashSet<>();
        for (String name : selected.selection().names()) {
            if (!(name.matches("[A-Za-z][A-Za-z0-9-]*")
                            || name.matches("(?:0|[1-9][0-9]*)(?:\\.(?:0|[1-9][0-9]*))+"))
                    || !folded.add(name.toLowerCase(Locale.ROOT))) throw invalid();
        }
    }

    static Projection project(
            Attributes attributes, ClaimNameCatalogPort.VersionedSelection selected) {
        validateSelection(selected);
        if (attributes == null) throw invalid();
        try {
            Map<String, Attribute> exact = new HashMap<>();
            Set<String> partial = new HashSet<>();
            Map<String, String> requested = new HashMap<>();
            for (String name : selected.selection().names())
                requested.put(name.toLowerCase(Locale.ROOT), name);
            NamingEnumeration<? extends Attribute> all = attributes.getAll();
            try {
                int count = 0;
                while (all.hasMore()) {
                    if (++count > 2 * (ClaimCoverage.MAX_NAMES + 9)) throw invalid();
                    Attribute attribute = all.next();
                    String id = attribute.getID();
                    if (id == null || id.length() > 2 * ClaimCoverage.MAX_NAME_CODE_POINTS)
                        throw invalid();
                    new ClaimCoverage.NamedClaim(id, ClaimCoverage.Entry.unknown());
                    String folded = id.toLowerCase(Locale.ROOT);
                    int option = folded.indexOf(';');
                    String base = option < 0 ? folded : folded.substring(0, option);
                    if (!requested.containsKey(base)) continue;
                    if (option >= 0) {
                        partial.add(base);
                        continue;
                    }
                    if (exact.putIfAbsent(base, attribute) != null) throw invalid();
                }
            } finally {
                all.close();
            }
            List<ClaimCoverage.NamedClaim> entries = new ArrayList<>();
            long[] budget = {
                selected.selection().names().stream()
                        .mapToLong(n -> n.getBytes(java.nio.charset.StandardCharsets.UTF_8).length)
                        .sum()
            };
            for (String name : selected.selection().names()) {
                String folded = name.toLowerCase(Locale.ROOT);
                ClaimCoverage.Entry entry =
                        partial.contains(folded) || !exact.containsKey(folded)
                                ? ClaimCoverage.Entry.unknown()
                                : values(exact.get(folded), budget);
                entries.add(new ClaimCoverage.NamedClaim(name, entry));
            }
            return new Projection(new ClaimCoverage(entries), selected.generation());
        } catch (NamingException | RuntimeException failure) {
            throw invalid();
        }
    }

    private static ClaimCoverage.Entry values(Attribute attribute, long[] budget)
            throws NamingException {
        Set<String> values = new TreeSet<>();
        boolean malformed = false;
        NamingEnumeration<?> all = attribute.getAll();
        try {
            int count = 0;
            while (all.hasMore()) {
                if (++count > ClaimCoverage.MAX_VALUES_PER_NAME) throw invalid();
                Object raw = all.next();
                if (!(raw instanceof String text)) {
                    malformed = true;
                    continue;
                }
                // Bound even blank input before inspection; no oversize scalar is normalized away.
                if (text.length() > ClaimCoverage.MAX_VALUE_BYTES) throw invalid();
                if (text.isBlank()) {
                    malformed = true;
                    continue;
                }
                // Invalid Unicode/control characters are malformed; envelope overflow is fatal.
                int bytes = text.getBytes(java.nio.charset.StandardCharsets.UTF_8).length;
                if (bytes > ClaimCoverage.MAX_VALUE_BYTES
                        || (budget[0] += bytes) > ClaimCoverage.MAX_TOTAL_BYTES) throw invalid();
                try {
                    ClaimCoverage.Entry.present(Set.of(text));
                } catch (IllegalArgumentException badText) {
                    malformed = true;
                    continue;
                }
                if (!values.add(text)) throw invalid();
            }
        } finally {
            all.close();
        }
        return malformed || values.isEmpty()
                ? ClaimCoverage.Entry.malformed()
                : ClaimCoverage.Entry.present(values);
    }

    private static IllegalArgumentException invalid() {
        return new IllegalArgumentException("Invalid bounded directory claim projection");
    }
}
