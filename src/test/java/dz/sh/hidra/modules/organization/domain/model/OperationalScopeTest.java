/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : OperationalScopeTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-24
 *
 * @Type        : Class
 * @Layer       : Organization Test
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization.domain.model
 *
 * @Description : Tests local invariants of a registered operational scope identity.
 *
 */
package dz.sh.hidra.modules.organization.domain.model;

import dz.sh.hidra.modules.organization.domain.exception.InvalidOrganizationValueException;
import dz.sh.hidra.modules.organization.domain.value.OperationalScopeType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Checks registry identity constraints without accessing a database or another module.
 */
class OperationalScopeTest {

    @Test
    void registeredPipelinePreservesOwningModuleIdentifier() {
        OperationalScope scope = new OperationalScope(41L, OperationalScopeType.PIPELINE, " pipeline-001 ");
        assertEquals(41L, scope.id());
        assertEquals("pipeline-001", scope.targetId());
        assertEquals(OperationalScopeType.PIPELINE, scope.type());
    }

    @Test
    void globalRegistryScopeHasNoTarget() {
        OperationalScope scope = new OperationalScope(1L, OperationalScopeType.GLOBAL, null);
        assertNull(scope.targetId());
        assertThrows(InvalidOrganizationValueException.class,
                () -> new OperationalScope(1L, OperationalScopeType.GLOBAL, "invented-global-id"));
    }

    @Test
    void rejectsUnassignedOrInvalidRegistryIds() {
        assertThrows(InvalidOrganizationValueException.class,
                () -> new OperationalScope(null, OperationalScopeType.PIPELINE, "pipeline-001"));
        assertThrows(InvalidOrganizationValueException.class,
                () -> new OperationalScope(0L, OperationalScopeType.PIPELINE, "pipeline-001"));
    }

    @Test
    void rejectsMissingTypesAndTargetIds() {
        assertThrows(InvalidOrganizationValueException.class,
                () -> new OperationalScope(1L, null, "pipeline-001"));
        assertThrows(InvalidOrganizationValueException.class,
                () -> new OperationalScope(1L, OperationalScopeType.PIPELINE, null));
        assertThrows(InvalidOrganizationValueException.class,
                () -> new OperationalScope(1L, OperationalScopeType.FACILITY, "  "));
    }

    @Test
    void rejectsUnregisteredCustomScopes() {
        assertThrows(InvalidOrganizationValueException.class,
                () -> new OperationalScope(1L, OperationalScopeType.CUSTOM, "unknown-owner"));
    }
}
