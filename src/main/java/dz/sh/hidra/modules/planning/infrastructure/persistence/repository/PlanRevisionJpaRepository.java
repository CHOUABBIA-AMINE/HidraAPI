/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : PlanRevisionJpaRepository
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Interface
 * @Layer       : Infrastructure
 * @Module      : planning
 * @Package     : dz.sh.hidra.modules.planning.infrastructure.persistence.repository
 *
 * @Description : Spring Data JPA repository for PlanRevision.
 *
 */
package dz.sh.hidra.modules.planning.infrastructure.persistence.repository;

import dz.sh.hidra.modules.planning.infrastructure.persistence.entity.PlanRevisionJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for PlanRevision.
 */
@Repository
public interface PlanRevisionJpaRepository extends JpaRepository<PlanRevisionJpaEntity, String> {
}
