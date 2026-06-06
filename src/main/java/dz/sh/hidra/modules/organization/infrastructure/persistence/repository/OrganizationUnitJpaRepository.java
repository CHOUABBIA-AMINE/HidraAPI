/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : OrganizationUnitJpaRepository
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Interface
 * @Layer       : Infrastructure
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization.infrastructure.persistence.repository
 *
 * @Description : Spring Data repository for organization unit persistence entities.
 *
 */
package dz.sh.hidra.modules.organization.infrastructure.persistence.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import dz.sh.hidra.modules.organization.infrastructure.persistence.entity.OrganizationUnitJpaEntity;

/**
 * Spring Data repository for organization unit persistence entities.
 */
public interface OrganizationUnitJpaRepository extends JpaRepository<OrganizationUnitJpaEntity, String> {

    Optional<OrganizationUnitJpaEntity> findByCode(String code);

    boolean existsByCode(String code);

    List<OrganizationUnitJpaEntity> findByParentId(String parentId);

    List<OrganizationUnitJpaEntity> findByTypeId(String typeId);

    List<OrganizationUnitJpaEntity> findByOperationalScopeTypeAndOperationalScopeCode(
            String operationalScopeType,
            String operationalScopeCode);
}
