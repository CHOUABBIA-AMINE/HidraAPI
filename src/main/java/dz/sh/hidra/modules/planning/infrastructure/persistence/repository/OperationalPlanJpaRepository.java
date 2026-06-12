/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : OperationalPlanJpaRepository
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Interface
 * @Layer       : Infrastructure
 * @Module      : planning
 * @Package     : dz.sh.hidra.modules.planning.infrastructure.persistence.repository
 *
 * @Description : Spring Data JPA repository for OperationalPlan.
 *
 */
package dz.sh.hidra.modules.planning.infrastructure.persistence.repository;

import dz.sh.hidra.modules.planning.infrastructure.persistence.entity.OperationalPlanJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for OperationalPlan.
 */
@Repository
public interface OperationalPlanJpaRepository extends JpaRepository<OperationalPlanJpaEntity, String> {
}
