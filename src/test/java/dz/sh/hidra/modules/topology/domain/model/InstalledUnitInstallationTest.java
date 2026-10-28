/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : InstalledUnitInstallationTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-28
 *
 * @Type        : Class
 * @Layer       : Test
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.domain.model
 *
 * @Description : Checks synthetic half-open installation intervals and conflicting effective occupancy.
 *
 */
package dz.sh.hidra.modules.topology.domain.model;

import dz.sh.hidra.modules.topology.domain.exception.InvalidTopologyValueException;
import java.time.Instant;
import java.util.List;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class InstalledUnitInstallationTest {
    private static final Instant T = Instant.parse("2026-01-01T00:00:00Z");
    private static InstalledUnitInstallation row(String id, String unit, String position, long start, Long end) {
        return new InstalledUnitInstallation(id, unit, position, "company-1",
                T.plusSeconds(start * 86400), end == null ? null : T.plusSeconds(end * 86400),
                T, "synthetic-evidence", 1);
    }
    @Test void adjacentPhysicalReplacementPreservesDistinctInstallations() {
        var a = row("inst-a", "unit-a", "position", 0, 5L);
        var b = row("inst-b", "unit-b", "position", 5, null);
        InstalledUnitInstallation.requireNonOverlappingSnapshot(List.of(a, b));
        assertEquals("unit-a", a.installedUnitId());
        assertEquals("unit-b", b.installedUnitId());
    }
    @Test void rejectsOnePhysicalUnitAtSimultaneousDifferentPositions() {
        assertThrows(InvalidTopologyValueException.class,
                () -> InstalledUnitInstallation.requireNonOverlappingSnapshot(List.of(
                        row("i1", "u", "position-a", 0, 5L),
                        row("i2", "u", "position-b", 4, null))));
    }
    @Test void rejectsTwoPhysicalUnitsAtOneSingleOccupancyPosition() {
        assertThrows(InvalidTopologyValueException.class,
                () -> InstalledUnitInstallation.requireNonOverlappingSnapshot(List.of(
                        row("i1", "unit-a", "p", 0, null),
                        row("i2", "unit-b", "p", 2, null))));
        InstalledUnitInstallation.requireNonOverlappingSnapshot(List.of(
                row("i1", "unit-a", "p1", 0, null),
                row("i2", "unit-b", "p2", 0, null)));
    }
    @Test void rejectsInvalidEndRevisionAndMissingEvidence() {
        assertThrows(InvalidTopologyValueException.class,
                () -> new InstalledUnitInstallation("i", "u", "p", "company", T, T, T, "e", 1));
        assertThrows(InvalidTopologyValueException.class,
                () -> new InstalledUnitInstallation("i", "u", "p", "company", T, null, T, "e", 0));
        assertThrows(InvalidTopologyValueException.class,
                () -> new InstalledUnitInstallation("i", "u", "p", "company", T, null, T, " ", 1));
    }
    @Test void rejectsDuplicateInstallationIdentityAndNullSnapshots() {
        assertThrows(InvalidTopologyValueException.class,
                () -> InstalledUnitInstallation.requireNonOverlappingSnapshot(List.of(
                        row("i", "u1", "p1", 0, 1L),
                        row("i", "u2", "p2", 2, 3L))));
        assertThrows(InvalidTopologyValueException.class,
                () -> InstalledUnitInstallation.requireNonOverlappingSnapshot(null));
    }
}
