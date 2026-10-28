/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : InstalledUnitTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-28
 *
 * @Type        : Class
 * @Layer       : Test
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.domain.model
 *
 * @Description : Checks independently identified synthetic units and sourced metadata classifications.
 *
 */
package dz.sh.hidra.modules.topology.domain.model;

import dz.sh.hidra.modules.topology.domain.exception.InvalidTopologyValueException;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static dz.sh.hidra.modules.topology.domain.model.InstalledUnit.*;

class InstalledUnitTest {
    private static Evidence unknown() { return Evidence.unknown(); }
    private static Evidence evidence(String value, Verification status) {
        return new Evidence(value, status, "synthetic-source");
    }
    private static InstalledUnit unit(String id, Evidence maker, Evidence model, Evidence serial) {
        return new InstalledUnit(id, "company-1", "compressor", maker, model, serial, null);
    }
    @Test void duplicateSerialDoesNotMergeIndependentPhysicalUnits() {
        var maker = evidence("party-maker", Verification.UNVERIFIED);
        var serial = evidence("serial-a", Verification.UNVERIFIED);
        var a = unit("physical-a", maker, unknown(), serial);
        var b = unit("physical-b", maker, unknown(), serial);
        assertNotEquals(a.id(), b.id());
        assertEquals(a.serialNumber(), b.serialNumber());
        assertNull(a.maintainableAssetId());
    }
    @Test void unknownEvidenceCannotCarryAnInventedValueOrSource() {
        assertNull(unit("physical-a", unknown(), unknown(), unknown()).serialNumber().value());
        assertThrows(InvalidTopologyValueException.class,
                () -> new Evidence("serial", Verification.UNKNOWN, null));
        assertThrows(InvalidTopologyValueException.class,
                () -> new Evidence(null, Verification.UNKNOWN, "e"));
    }
    @Test void knownEvidenceRequiresValueAndProvenance() {
        assertThrows(InvalidTopologyValueException.class,
                () -> new Evidence("serial", Verification.UNVERIFIED, null));
        assertThrows(InvalidTopologyValueException.class,
                () -> new Evidence(" ", Verification.VERIFIED, "source"));
        assertThrows(InvalidTopologyValueException.class,
                () -> new Evidence("maker", null, "source"));
    }
    @Test void manufacturerModelCannotOutrankTheManufacturerEvidence() {
        assertThrows(InvalidTopologyValueException.class, () ->
                unit("physical", unknown(), evidence("model", Verification.UNVERIFIED), unknown()));
        assertThrows(InvalidTopologyValueException.class, () ->
                unit("physical", evidence("maker", Verification.UNVERIFIED),
                        evidence("model", Verification.VERIFIED), unknown()));
        assertEquals(Verification.VERIFIED, unit("physical", evidence("maker", Verification.VERIFIED),
                evidence("model", Verification.VERIFIED), unknown()).manufacturerModel().verification());
    }
    @Test void requiresPhysicalIdentityCompanyKindAndEvidenceSlots() {
        assertThrows(InvalidTopologyValueException.class, () -> unit(" ", unknown(), unknown(), unknown()));
        assertThrows(InvalidTopologyValueException.class,
                () -> new InstalledUnit("u", " ", "compressor", unknown(), unknown(), unknown(), null));
        assertThrows(InvalidTopologyValueException.class,
                () -> new InstalledUnit("u", "company", " ", unknown(), unknown(), unknown(), null));
        assertThrows(InvalidTopologyValueException.class,
                () -> new InstalledUnit("u", "company", "kind", null, unknown(), unknown(), null));
        assertEquals("maint-1", new InstalledUnit("u", "company", "kind", unknown(), unknown(),
                unknown(), " maint-1 ").maintainableAssetId());
    }
}
