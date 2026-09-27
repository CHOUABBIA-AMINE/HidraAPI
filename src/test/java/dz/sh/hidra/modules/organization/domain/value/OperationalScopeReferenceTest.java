/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : OperationalScopeReferenceTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-27
 *
 * @Type        : Class
 * @Layer       : Organization Test
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization.domain.value
 *
 * @Description : Verifies canonical operational-scope owner-reference invariants.
 *
 */
package dz.sh.hidra.modules.organization.domain.value;

import dz.sh.hidra.modules.organization.domain.exception.InvalidOrganizationValueException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class OperationalScopeReferenceTest {

    @Test
    void acceptsGlobalWithoutTargetId() {
        OperationalScopeReference reference =
                new OperationalScopeReference(OperationalScopeType.GLOBAL, null);

        assertEquals(OperationalScopeType.GLOBAL, reference.type());
        assertNull(reference.targetId());
        assertTrue(reference.type().isGlobal());
        assertFalse(reference.type().requiresTargetId());
    }

    @Test
    void rejectsGlobalWithTargetId() {
        assertThrows(
                InvalidOrganizationValueException.class,
                () -> new OperationalScopeReference(
                        OperationalScopeType.GLOBAL,
                        "pipeline-009"
                )
        );
    }

    @Test
    void normalizesEntityBackedTargetId() {
        OperationalScopeReference reference =
                new OperationalScopeReference(
                        OperationalScopeType.PIPELINE,
                        " pipeline-009 "
                );

        assertEquals(OperationalScopeType.PIPELINE, reference.type());
        assertEquals("pipeline-009", reference.targetId());
        assertTrue(reference.type().requiresTargetId());
        assertTrue(reference.type().isGoverned());
    }

    @Test
    void rejectsMissingEntityBackedTargetId() {
        assertThrows(
                InvalidOrganizationValueException.class,
                () -> new OperationalScopeReference(
                        OperationalScopeType.FACILITY,
                        " "
                )
        );
    }

    @Test
    void rejectsUngovernedCustomScope() {
        assertFalse(OperationalScopeType.CUSTOM.isGoverned());

        assertThrows(
                InvalidOrganizationValueException.class,
                () -> new OperationalScopeReference(
                        OperationalScopeType.CUSTOM,
                        "custom-001"
                )
        );
    }

    @Test
    void rejectsMissingType() {
        assertThrows(
                InvalidOrganizationValueException.class,
                () -> new OperationalScopeReference(null, null)
        );
    }
}
