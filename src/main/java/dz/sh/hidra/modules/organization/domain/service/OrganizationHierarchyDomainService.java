/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : OrganizationHierarchyDomainService
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Class
 * @Layer       : Domain
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization.domain.service
 *
 * @Description : Domain service coordinating organization hierarchy validation rules.
 *
 */
package dz.sh.hidra.modules.organization.domain.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import dz.sh.hidra.modules.organization.domain.exception.OrganizationHierarchyException;
import dz.sh.hidra.modules.organization.domain.model.OrganizationUnit;
import dz.sh.hidra.modules.organization.domain.policy.OrganizationHierarchyPolicy;
import dz.sh.hidra.modules.organization.domain.repository.OrganizationUnitDomainRepository;
import dz.sh.hidra.modules.organization.domain.value.OrganizationUnitId;

/**
 * Coordinates organization hierarchy validation rules.
 *
 * <p>Business role:
 * This domain service validates hierarchy changes across multiple organization units, including
 * divisions, regions, station organization units, and teams.
 *
 * <p>Architecture role:
 * This is a domain service. It coordinates domain repository contracts and policies without
 * depending on Spring, JPA, REST DTOs, identity, topology, platform, or infrastructure code.
 *
 * <p>Validation:
 * It resolves parent ancestor ids through the domain repository and delegates invariant checks to
 * <code>OrganizationHierarchyPolicy</code>.
 *
 * <p>Usage:
 * Use this service when hierarchy validation requires traversing already-persisted organization
 * unit relationships.
 */
public final class OrganizationHierarchyDomainService {

    /**
     * Domain repository contract used to read organization unit hierarchy.
     */
    private final OrganizationUnitDomainRepository organizationUnitRepository;

    /**
     * Policy protecting hierarchy invariants.
     */
    private final OrganizationHierarchyPolicy hierarchyPolicy;

    /**
     * Creates the hierarchy domain service.
     *
     * @param organizationUnitRepository organization unit repository contract
     * @param hierarchyPolicy hierarchy validation policy
     */
    public OrganizationHierarchyDomainService(
            OrganizationUnitDomainRepository organizationUnitRepository,
            OrganizationHierarchyPolicy hierarchyPolicy) {

        this.organizationUnitRepository = Objects.requireNonNull(
                organizationUnitRepository,
                "Organization unit repository must not be null.");
        this.hierarchyPolicy = Objects.requireNonNull(hierarchyPolicy, "Organization hierarchy policy must not be null.");
    }

    /**
     * Ensures the child organization unit can be assigned to the supplied parent.
     *
     * @param child child organization unit
     * @param parent parent organization unit, or null for root
     */
    public void ensureCanAssignParent(OrganizationUnit child, OrganizationUnit parent) {
        List<OrganizationUnitId> parentAncestorIds = resolveAncestorIds(parent);
        hierarchyPolicy.ensureCanAssignParent(child, parent, parentAncestorIds);
    }

    private List<OrganizationUnitId> resolveAncestorIds(OrganizationUnit parent) {
        if (parent == null) {
            return List.of();
        }

        List<OrganizationUnitId> ancestorIds = new ArrayList<>();
        OrganizationUnit current = parent;

        while (current.parentId().isPresent()) {
            OrganizationUnitId parentId = current.parentId().orElseThrow();
            if (ancestorIds.contains(parentId)) {
                throw new OrganizationHierarchyException("Organization hierarchy cycle detected while resolving ancestors.");
            }

            ancestorIds.add(parentId);
            current = organizationUnitRepository.findById(parentId)
                    .orElseThrow(() -> new OrganizationHierarchyException("Parent organization unit not found while resolving hierarchy."));
        }

        return ancestorIds;
    }
}
