/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : SimulationConstraintEvaluationJpaRepository
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Interface
 * @Layer       : Infrastructure
 * @Module      : simulation
 * @Package     : dz.sh.hidra.modules.simulation.infrastructure.persistence.repository
 *
 * @Description : Spring Data JPA repository for SimulationConstraintEvaluation.
 *
 */
package dz.sh.hidra.modules.simulation.infrastructure.persistence.repository;

import dz.sh.hidra.modules.simulation.infrastructure.persistence.entity.SimulationConstraintEvaluationJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for SimulationConstraintEvaluation.
 */
@Repository
public interface SimulationConstraintEvaluationJpaRepository extends JpaRepository<SimulationConstraintEvaluationJpaEntity, String> {
}
