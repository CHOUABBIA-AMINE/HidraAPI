/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : OperationalScopeJpaRepository
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-27
 *
 * @Type        : Interface
 * @Layer       : Infrastructure
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization.infrastructure.persistence.repository
 *
 * @Description : Spring Data repository for the operational-scope registry.
 *
 */
package dz.sh.hidra.modules.organization.infrastructure.persistence.repository;

import dz.sh.hidra.modules.organization.domain.value.OperationalScopeType;
import dz.sh.hidra.modules.organization.infrastructure.persistence.entity.OperationalScopeJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Spring Data repository for canonical operational-scope registry rows.
 */
@Repository
public interface OperationalScopeJpaRepository extends JpaRepository<OperationalScopeJpaEntity, Long> {

    Optional<OperationalScopeJpaEntity> findByScopeTypeAndTargetId(
            OperationalScopeType scopeType,
            String targetId
    );

    Optional<OperationalScopeJpaEntity> findByScopeType(OperationalScopeType scopeType);
}
