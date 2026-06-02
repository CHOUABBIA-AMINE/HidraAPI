/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : OrganizationUnitDomainRepository
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Interface
 * @Layer       : Domain
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization.domain.repository
 *
 * @Description : Domain repository contract for organization unit lookup.
 *
 */
package dz.sh.hidra.modules.organization.domain.repository;

import java.util.List;
import java.util.Optional;

import dz.sh.hidra.modules.organization.domain.model.OperationalScopeReference;
import dz.sh.hidra.modules.organization.domain.model.OrganizationUnit;
import dz.sh.hidra.modules.organization.domain.value.OrganizationUnitCode;
import dz.sh.hidra.modules.organization.domain.value.OrganizationUnitId;
import dz.sh.hidra.modules.organization.domain.value.OrganizationUnitType;

/**
 * Defines the domain lookup contract for organization units.
 *
 * <p>Business role:
 * This contract allows organization domain/application logic to look up organization units,
 * including regions, departments, station-as-organization-unit structures, and teams.
 *
 * <p>Architecture role:
 * This is a domain repository contract. It is not a Spring Data repository and must not import
 * JPA, Spring, REST DTOs, identity, topology, platform, or infrastructure adapter code.
 *
 * <p>Validation:
 * Implementations must preserve unique organization unit codes and must not return persistence
 * entities to domain callers. Hierarchy and cycle checks remain domain policy responsibilities.
 *
 * <p>Usage:
 * Implement this contract later through infrastructure adapters or bridge it through application
 * outbound ports when persistence is introduced.
 */
public interface OrganizationUnitDomainRepository {

    /**
     * Finds an organization unit by stable identifier.
     *
     * @param id organization unit identifier
     * @return matching organization unit when present
     */
    Optional<OrganizationUnit> findById(OrganizationUnitId id);

    /**
     * Finds an organization unit by unique business code.
     *
     * @param code organization unit code
     * @return matching organization unit when present
     */
    Optional<OrganizationUnit> findByCode(OrganizationUnitCode code);

    /**
     * Checks whether an organization unit exists for the given business code.
     *
     * @param code organization unit code
     * @return true when an organization unit exists for the code
     */
    boolean existsByCode(OrganizationUnitCode code);

    /**
     * Lists direct children of the given parent organization unit.
     *
     * @param parentId parent organization unit identifier
     * @return direct child organization units
     */
    List<OrganizationUnit> findChildrenOf(OrganizationUnitId parentId);

    /**
     * Lists organization units by type.
     *
     * @param type organization unit type
     * @return organization units matching the type
     */
    List<OrganizationUnit> findByType(OrganizationUnitType type);

    /**
     * Finds organization units linked to the same neutral operational scope reference.
     *
     * @param operationalScopeReference neutral operational scope reference
     * @return organization units linked to the scope
     */
    List<OrganizationUnit> findByOperationalScope(OperationalScopeReference operationalScopeReference);
}
