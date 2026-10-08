/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : PlanScenarioJpaRepository
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Interface
 * @Layer       : Infrastructure
 * @Module      : planning
 * @Package     : dz.sh.hidra.modules.planning.infrastructure.persistence.repository
 *
 * @Description : Spring Data JPA repository for PlanScenario.
 *
 */
package dz.sh.hidra.modules.planning.infrastructure.persistence.repository;

import dz.sh.hidra.modules.planning.infrastructure.persistence.entity.PlanScenarioJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.Optional;

/**
 * Spring Data JPA repository for PlanScenario.
 */
@Repository
public interface PlanScenarioJpaRepository extends JpaRepository<PlanScenarioJpaEntity, String> {
    @Lock(LockModeType.PESSIMISTIC_READ)
    @Query("select reference from PlanScenarioJpaEntity reference where reference.id = :id")
    Optional<PlanScenarioJpaEntity> findByIdForShare(@Param("id") String id);
}
