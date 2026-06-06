/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : OrganizationUnitDomainRepository
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-06
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
import dz.sh.hidra.modules.organization.domain.value.OrganizationUnitTypeReference;

/**
 * Defines the domain lookup contract for organization units.
 */
public interface OrganizationUnitDomainRepository {

    Optional<OrganizationUnit> findById(OrganizationUnitId id);

    Optional<OrganizationUnit> findByCode(OrganizationUnitCode code);

    boolean existsByCode(OrganizationUnitCode code);

    List<OrganizationUnit> findChildrenOf(OrganizationUnitId parentId);

    List<OrganizationUnit> findByType(OrganizationUnitTypeReference type);

    List<OrganizationUnit> findByOperationalScope(OperationalScopeReference operationalScopeReference);
}
