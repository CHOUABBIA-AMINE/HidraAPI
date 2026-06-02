/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : OrganizationUnitRepository
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization.application.port.out
 *
 * @Description : Outbound organization unit persistence port.
 *
 */
package dz.sh.hidra.modules.organization.application.port.out;

import java.util.List;
import java.util.Optional;

import dz.sh.hidra.modules.organization.domain.model.OperationalScopeReference;
import dz.sh.hidra.modules.organization.domain.model.OrganizationUnit;
import dz.sh.hidra.modules.organization.domain.value.OrganizationUnitCode;
import dz.sh.hidra.modules.organization.domain.value.OrganizationUnitId;
import dz.sh.hidra.modules.organization.domain.value.OrganizationUnitType;

/**
 * Defines the outbound persistence contract for organization units.
 *
 * <p>Business role:
 * This port persists and retrieves organization units, including station-as-organization-unit
 * structures that represent people and responsibility.
 *
 * <p>Architecture role:
 * This is an application outbound port implemented later by infrastructure. It must not depend on
 * Spring Data, JPA entities, REST DTOs, identity domain objects, or topology domain objects.
 *
 * <p>Validation:
 * Implementations must preserve organization unit code uniqueness and keep operational scope
 * references neutral.
 *
 * <p>Usage:
 * Application services depend on this interface when loading or saving organization units.
 */
public interface OrganizationUnitRepository {

    /**
     * Saves an organization unit aggregate.
     *
     * @param organizationUnit organization unit to save
     * @return saved organization unit
     */
    OrganizationUnit save(OrganizationUnit organizationUnit);

    /**
     * Finds an organization unit by identifier.
     *
     * @param id organization unit identifier
     * @return organization unit when found
     */
    Optional<OrganizationUnit> findById(OrganizationUnitId id);

    /**
     * Finds an organization unit by business code.
     *
     * @param code organization unit code
     * @return organization unit when found
     */
    Optional<OrganizationUnit> findByCode(OrganizationUnitCode code);

    /**
     * Checks whether an organization unit code already exists.
     *
     * @param code organization unit code
     * @return true when the code exists
     */
    boolean existsByCode(OrganizationUnitCode code);

    /**
     * Lists direct child organization units.
     *
     * @param parentId parent organization unit identifier
     * @return direct child organization units
     */
    List<OrganizationUnit> findChildrenOf(OrganizationUnitId parentId);

    /**
     * Lists organization units by type.
     *
     * @param type organization unit type
     * @return matching organization units
     */
    List<OrganizationUnit> findByType(OrganizationUnitType type);

    /**
     * Finds organization units by neutral operational scope.
     *
     * @param operationalScopeReference neutral operational scope reference
     * @return matching organization units
     */
    List<OrganizationUnit> findByOperationalScope(OperationalScopeReference operationalScopeReference);
}
