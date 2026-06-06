/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : OrganizationHierarchyPolicyTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Class
 * @Layer       : Domain
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization.domain.policy
 *
 * @Description : Unit tests for OrganizationHierarchyPolicy rules.
 *
 */
package dz.sh.hidra.modules.organization.domain.policy;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;

import org.junit.jupiter.api.Test;

import dz.sh.hidra.modules.organization.domain.exception.OrganizationHierarchyException;
import dz.sh.hidra.modules.organization.domain.model.OrganizationUnit;
import dz.sh.hidra.modules.organization.domain.value.OrganizationUnitCode;
import dz.sh.hidra.modules.organization.domain.value.OrganizationUnitId;
import dz.sh.hidra.modules.organization.domain.value.OrganizationUnitName;
import dz.sh.hidra.modules.organization.domain.value.OrganizationUnitTypeReference;

/**
 * Tests organization hierarchy policy.
 */
class OrganizationHierarchyPolicyTest {

    private final OrganizationHierarchyPolicy policy = new OrganizationHierarchyPolicy();

    @Test
    void shouldAllowValidParentAssignment() {
        OrganizationUnit child = organizationUnit("TEAM_HIERARCHY", "Hierarchy Team", OrganizationUnitTypeReference.TEAM);
        OrganizationUnit parent = organizationUnit("DEPT_HIERARCHY", "Hierarchy Department", OrganizationUnitTypeReference.DEPARTMENT);

        assertDoesNotThrow(() -> policy.ensureCanAssignParent(child, parent, List.of()));
    }

    @Test
    void shouldRejectSelfParenting() {
        OrganizationUnit organizationUnit = organizationUnit("SELF_PARENT", "Self Parent", OrganizationUnitTypeReference.TEAM);

        assertThrows(
                OrganizationHierarchyException.class,
                () -> policy.ensureDifferentUnits(organizationUnit, organizationUnit));
    }

    @Test
    void shouldRejectCycleWhenChildAppearsInParentAncestors() {
        OrganizationUnitId childId = OrganizationUnitId.newId();

        assertThrows(
                OrganizationHierarchyException.class,
                () -> policy.ensureNoCycle(childId, List.of(OrganizationUnitId.newId(), childId)));
    }

    @Test
    void shouldRejectDuplicateAncestorReference() {
        OrganizationUnitId ancestorId = OrganizationUnitId.newId();

        assertThrows(
                OrganizationHierarchyException.class,
                () -> policy.ensureNoCycle(OrganizationUnitId.newId(), List.of(ancestorId, ancestorId)));
    }

    private static OrganizationUnit organizationUnit(String code, String name, OrganizationUnitTypeReference type) {
        return OrganizationUnit.create(
                OrganizationUnitCode.of(code),
                OrganizationUnitName.of(name),
                type,
                null,
                null);
    }
}
