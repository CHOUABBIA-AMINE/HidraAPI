/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : OperationalScopeReferenceTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Class
 * @Layer       : Domain
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization.domain.model
 *
 * @Description : Unit tests for OperationalScopeReference validation.
 *
 */
package dz.sh.hidra.modules.organization.domain.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.Test;

import dz.sh.hidra.kernel.domain.exception.InvalidValueObjectException;
import dz.sh.hidra.modules.organization.domain.value.OperationalScopeType;

/**
 * Tests the neutral operational scope reference.
 *
 * <p>Business role:
 * Verifies that organization can reference future topology or operational scopes without importing
 * topology domain objects.
 *
 * <p>Architecture role:
 * This is a domain unit test and has no Spring, JPA, API, identity, or topology dependency.
 */
class OperationalScopeReferenceTest {

    @Test
    void shouldCreateStationOperationalScopeReference() {
        OperationalScopeReference reference = new OperationalScopeReference(
                OperationalScopeType.TOPOLOGY_COMPRESSION_STATION,
                " station-001 ",
                " CS-EAST-01 ",
                " Compression Station East 01 ");

        assertTrue(reference.isStationScope());
        assertEquals("station-001", reference.scopeId());
        assertEquals("CS-EAST-01", reference.scopeCode());
        assertEquals("Compression Station East 01", reference.scopeName());
    }

    @Test
    void shouldSupportReferenceWithOnlyTypeAndCode() {
        OperationalScopeReference reference = OperationalScopeReference.of(
                OperationalScopeType.TOPOLOGY_PIPELINE,
                "PIPE-01");

        assertFalse(reference.isStationScope());
        assertNull(reference.scopeId());
        assertNull(reference.scopeName());
    }

    @Test
    void shouldRejectMissingScopeType() {
        assertThrows(
                InvalidValueObjectException.class,
                () -> new OperationalScopeReference(null, null, "CS-EAST-01", null));
    }

    @Test
    void shouldRejectBlankScopeCode() {
        assertThrows(
                InvalidValueObjectException.class,
                () -> new OperationalScopeReference(OperationalScopeType.TOPOLOGY_STATION, null, " ", null));
    }
}
