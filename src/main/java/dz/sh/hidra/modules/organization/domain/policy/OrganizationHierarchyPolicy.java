/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : OrganizationHierarchyPolicy
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Class
 * @Layer       : Domain
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization.domain.policy
 *
 * @Description : Domain policy validating organization unit hierarchy constraints.
 *
 */
package dz.sh.hidra.modules.organization.domain.policy;

import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import dz.sh.hidra.modules.organization.domain.exception.OrganizationHierarchyException;
import dz.sh.hidra.modules.organization.domain.model.OrganizationUnit;
import dz.sh.hidra.modules.organization.domain.value.OrganizationUnitId;

/**
 * Validates organization hierarchy invariants.
 *
 * <p>Business role:
 * This policy protects the operational organization tree, including divisions, regions,
 * departments, station-as-organization-unit structures, and teams.
 *
 * <p>Architecture role:
 * This is a pure domain policy. It does not load data itself and does not depend on Spring, JPA,
 * REST DTOs, identity, topology, platform, or infrastructure code.
 *
 * <p>Validation:
 * It rejects self-parenting, duplicate ancestor chains, and hierarchy cycles when ancestor ids are
 * supplied by a domain service or application service.
 *
 * <p>Usage:
 * Use this policy before changing an organization unit parent or creating a hierarchy relation.
 */
public final class OrganizationHierarchyPolicy {

    /**
     * Ensures an organization unit can be assigned to the supplied parent.
     *
     * @param child organization unit being moved or created
     * @param parent optional parent organization unit
     * @param parentAncestorIds ancestor ids of the parent, nearest to farthest
     */
    public void ensureCanAssignParent(
            OrganizationUnit child,
            OrganizationUnit parent,
            Collection<OrganizationUnitId> parentAncestorIds) {

        Objects.requireNonNull(child, "Child organization unit must not be null.");

        if (parent == null) {
            return;
        }

        ensureDifferentUnits(child, parent);
        ensureNoCycle(child.id(), parentAncestorIds);
    }

    /**
     * Ensures a child organization unit is not its own parent.
     *
     * @param child child organization unit
     * @param parent parent organization unit
     */
    public void ensureDifferentUnits(OrganizationUnit child, OrganizationUnit parent) {
        Objects.requireNonNull(child, "Child organization unit must not be null.");
        Objects.requireNonNull(parent, "Parent organization unit must not be null.");

        if (child.id().equals(parent.id())) {
            throw new OrganizationHierarchyException("Organization unit cannot be its own parent.");
        }
    }

    /**
     * Ensures the parent ancestor list does not produce a cycle with the child.
     *
     * @param childId child organization unit identifier
     * @param parentAncestorIds ancestor ids of the selected parent
     */
    public void ensureNoCycle(OrganizationUnitId childId, Collection<OrganizationUnitId> parentAncestorIds) {
        Objects.requireNonNull(childId, "Child organization unit id must not be null.");

        if (parentAncestorIds == null || parentAncestorIds.isEmpty()) {
            return;
        }

        Set<OrganizationUnitId> visited = new HashSet<>();
        for (OrganizationUnitId ancestorId : parentAncestorIds) {
            if (ancestorId == null) {
                throw new OrganizationHierarchyException("Organization hierarchy ancestor id must not be null.");
            }

            if (!visited.add(ancestorId)) {
                throw new OrganizationHierarchyException("Organization hierarchy contains duplicate ancestor reference.");
            }

            if (childId.equals(ancestorId)) {
                throw new OrganizationHierarchyException("Organization hierarchy cycle detected.");
            }
        }
    }
}
