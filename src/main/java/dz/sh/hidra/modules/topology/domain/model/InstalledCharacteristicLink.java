/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : InstalledCharacteristicLink
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-01
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.domain.model
 *
 * @Description : Immutable proposed characteristic selection for a physical unit; neither source authority nor installation proof.
 *
 */
package dz.sh.hidra.modules.topology.domain.model;

import dz.sh.hidra.modules.topology.domain.exception.InvalidTopologyValueException;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * Documentary link candidate only. A source identifier, digest, kind or installation
 * witness supplied here is not an authorized read or manufacturer qualification.
 * The future protected owner must validate and lock all original sources.
 */
public record InstalledCharacteristicLink(
        String linkId, long linkRevision, String companyPartyId, String installedUnitId,
        String sourceId, String sourceRevisionId, String sourceSha256, Kind kind,
        Instant effectiveFrom, Instant effectiveUntil, Instant recordedAt,
        String evidenceReference, String installationId) {

    public enum Kind { COMPRESSOR_MAP, VALVE_MAP, PARAMETER_TABLE, OPERATING_LIMITS }

    public InstalledCharacteristicLink {
        linkId = exactId(linkId, "Link identity");
        if (linkRevision < 1) throw invalid("Positive link revision required.");
        companyPartyId = exactId(companyPartyId, "Company identity");
        installedUnitId = exactId(installedUnitId, "Physical unit identity");
        sourceId = exactId(sourceId, "Manufacturer source identity");
        sourceRevisionId = exactId(sourceRevisionId, "Manufacturer source revision");
        if (sourceSha256 == null || !sourceSha256.matches("[0-9a-f]{64}"))
            throw invalid("Exact lowercase canonical source SHA-256 required.");
        if (kind == null) throw invalid("Explicit source characteristic kind required.");
        if (effectiveFrom == null || recordedAt == null)
            throw invalid("Explicit effective and record times required.");
        microsecond(effectiveFrom);
        microsecond(recordedAt);
        if (effectiveUntil != null) {
            microsecond(effectiveUntil);
            if (!effectiveUntil.isAfter(effectiveFrom))
                throw invalid("Effective end must follow start.");
        }
        if (evidenceReference == null || evidenceReference.isBlank()
                || !evidenceReference.equals(evidenceReference.strip())
                || evidenceReference.getBytes(StandardCharsets.UTF_8).length > 65_536
                || evidenceReference.codePoints().anyMatch(Character::isISOControl))
            throw invalid("Exact bounded evidence reference required.");
        if (installationId != null) installationId = exactId(installationId, "Unverified installation reference");
    }

    /**
     * Validates an entire trusted candidate snapshot, not persistent state or
     * authorization. Physical SQL exclusion/unique and CAS remain mandatory.
     */
    public static void requireNonOverlappingSelections(List<InstalledCharacteristicLink> snapshot) {
        if (snapshot == null || snapshot.stream().anyMatch(Objects::isNull))
            throw invalid("Complete non-null link snapshot required.");
        var rows = List.copyOf(snapshot);
        Set<LinkRevisionKey> revisions = new HashSet<>();
        for (var row : rows) {
            if (!revisions.add(new LinkRevisionKey(row.linkId(), row.linkRevision())))
                throw invalid("Duplicate link revision in snapshot.");
        }
        for (int i = 0; i < rows.size(); i++) {
            var left = rows.get(i);
            for (int j = i + 1; j < rows.size(); j++) {
                var right = rows.get(j);
                if (left.companyPartyId().equals(right.companyPartyId())
                        && left.installedUnitId().equals(right.installedUnitId())
                        && left.kind() == right.kind() && overlaps(left, right))
                    throw invalid("Conflicting effective characteristic selection.");
            }
        }
    }

    private record LinkRevisionKey(String linkId, long revision) {}

    private static boolean overlaps(InstalledCharacteristicLink a, InstalledCharacteristicLink b) {
        return (a.effectiveUntil() == null || b.effectiveFrom().isBefore(a.effectiveUntil()))
                && (b.effectiveUntil() == null || a.effectiveFrom().isBefore(b.effectiveUntil()));
    }

    private static String exactId(String value, String field) {
        if (value == null || value.isBlank() || !value.equals(value.strip())
                || value.codePointCount(0, value.length()) > 80
                || value.codePoints().anyMatch(c -> Character.isISOControl(c)
                    || c >= Character.MIN_SURROGATE && c <= Character.MAX_SURROGATE))
            throw invalid(field + " must be an exact bounded identifier.");
        return value;
    }

    private static void microsecond(Instant instant) {
        if (instant.getNano() % 1_000 != 0)
            throw invalid("Time precision must not exceed one microsecond.");
    }

    private static InvalidTopologyValueException invalid(String detail) {
        return new InvalidTopologyValueException(detail);
    }
}
