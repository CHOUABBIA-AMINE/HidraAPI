/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : InstalledUnitInstallation
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-28
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.domain.model
 *
 * @Description : Defines immutable effective installation evidence and candidate occupancy invariants.
 *
 */
package dz.sh.hidra.modules.topology.domain.model;

import dz.sh.hidra.modules.topology.domain.exception.InvalidTopologyValueException;
import java.time.Instant;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * Physical-unit occupancy of an identified equipment position, using
 * start-inclusive/end-exclusive intervals with null end meaning open-ended.
 * Stored temporal constraints and authorization must be implemented separately.
 */
public record InstalledUnitInstallation(
        String installationId, String installedUnitId, String equipmentPositionId,
        String companyPartyId, Instant effectiveFrom, Instant effectiveUntil,
        Instant recordedAt, String evidenceReference, long revision) {

    public InstalledUnitInstallation {
        installationId = required(installationId, "Installation id");
        installedUnitId = required(installedUnitId, "Physical unit id");
        equipmentPositionId = required(equipmentPositionId, "Equipment position id");
        companyPartyId = required(companyPartyId, "Company Party id");
        evidenceReference = required(evidenceReference, "Evidence reference");
        if (effectiveFrom == null || recordedAt == null)
            throw invalid("Effective start and record time required.");
        if (effectiveUntil != null && !effectiveUntil.isAfter(effectiveFrom))
            throw invalid("Effective end must follow the effective start.");
        if (revision < 1) throw invalid("Evidence revision must be positive.");
    }

    /** Validates one effective snapshot, never historical evidence revisions or database races. */
    public static void requireNonOverlappingSnapshot(List<InstalledUnitInstallation> snapshot) {
        if (snapshot == null || snapshot.stream().anyMatch(Objects::isNull))
            throw invalid("Installation snapshot must have no null entries.");
        List<InstalledUnitInstallation> rows = List.copyOf(snapshot);
        Set<String> ids = new HashSet<>();
        for (var row : rows) {
            if (!ids.add(row.installationId()))
                throw invalid("Duplicate installation identity in an effective snapshot.");
        }
        for (int i = 0; i < rows.size(); i++) {
            var a = rows.get(i);
            for (int j = i + 1; j < rows.size(); j++) {
                var b = rows.get(j);
                if (overlaps(a, b) && (a.installedUnitId().equals(b.installedUnitId())
                        || a.equipmentPositionId().equals(b.equipmentPositionId())))
                    throw invalid("Overlapping unit or single-position occupancy.");
            }
        }
    }

    private static boolean overlaps(InstalledUnitInstallation a, InstalledUnitInstallation b) {
        return (a.effectiveUntil() == null || b.effectiveFrom().isBefore(a.effectiveUntil()))
                && (b.effectiveUntil() == null || a.effectiveFrom().isBefore(b.effectiveUntil()));
    }
    private static String required(String value, String label) {
        if (value == null || value.isBlank()) throw invalid(label + " required.");
        return value.trim();
    }
    private static InvalidTopologyValueException invalid(String message) {
        return new InvalidTopologyValueException(message);
    }
}
