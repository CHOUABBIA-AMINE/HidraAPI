/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : SimulationRunJpaRepository
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-05
 *
 * @Type        : Interface
 * @Layer       : Infrastructure
 * @Module      : simulation
 * @Package     : dz.sh.hidra.modules.simulation.infrastructure.persistence.repository
 *
 * @Description : Spring Data JPA repository for SimulationRun.
 *
 */
package dz.sh.hidra.modules.simulation.infrastructure.persistence.repository;

import dz.sh.hidra.modules.simulation.infrastructure.persistence.entity.SimulationRunJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for SimulationRun.
 */
@Repository
public interface SimulationRunJpaRepository extends JpaRepository<SimulationRunJpaEntity, String> {

    @Query(value = """
            SELECT CASE WHEN COUNT(*) > 0 THEN TRUE ELSE FALSE END
            FROM hidra_simulation_catalog_entry
            WHERE id = :runTypeId
              AND catalog_name = 'SIMULATION_RUN_TYPE'
            """, nativeQuery = true)
    boolean isRunType(@Param("runTypeId") String runTypeId);

    @Query(value = """
            SELECT CASE WHEN COUNT(*) > 0 THEN TRUE ELSE FALSE END
            FROM hidra_simulation_catalog_entry
            WHERE id = :solverProfileId
              AND catalog_name = 'SIMULATION_SOLVER_PROFILE'
            """, nativeQuery = true)
    boolean isSolverProfile(@Param("solverProfileId") String solverProfileId);
}
