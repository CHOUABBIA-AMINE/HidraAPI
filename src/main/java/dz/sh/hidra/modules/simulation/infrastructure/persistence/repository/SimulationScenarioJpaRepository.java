/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : SimulationScenarioJpaRepository
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-04
 *
 * @Type        : Interface
 * @Layer       : Infrastructure
 * @Module      : simulation
 * @Package     : dz.sh.hidra.modules.simulation.infrastructure.persistence.repository
 *
 * @Description : Spring Data JPA repository for SimulationScenario.
 *
 */
package dz.sh.hidra.modules.simulation.infrastructure.persistence.repository;

import dz.sh.hidra.modules.simulation.infrastructure.persistence.entity.SimulationScenarioJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for SimulationScenario.
 */
@Repository
public interface SimulationScenarioJpaRepository extends JpaRepository<SimulationScenarioJpaEntity, String> {

    boolean existsByCode(String code);

    @Query(value = """
            SELECT CASE WHEN COUNT(*) > 0 THEN TRUE ELSE FALSE END
            FROM hidra_simulation_catalog_entry
            WHERE id = :scenarioTypeId
              AND catalog_name = 'SIMULATION_SCENARIO_TYPE'
            """, nativeQuery = true)
    boolean isScenarioType(@Param("scenarioTypeId") String scenarioTypeId);

    @Query(value = """
            SELECT CASE WHEN COUNT(*) > 0 THEN TRUE ELSE FALSE END
            FROM hidra_simulation_model_version
            WHERE id = :modelVersionId
              AND model_id = :modelId
            """, nativeQuery = true)
    boolean modelVersionBelongsToModel(
            @Param("modelVersionId") String modelVersionId,
            @Param("modelId") String modelId
    );
}
