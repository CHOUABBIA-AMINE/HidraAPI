/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : SimulationModelJpaRepository
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Interface
 * @Layer       : Infrastructure
 * @Module      : simulation
 * @Package     : dz.sh.hidra.modules.simulation.infrastructure.persistence.repository
 *
 * @Description : Spring Data JPA repository for SimulationModel.
 *
 */
package dz.sh.hidra.modules.simulation.infrastructure.persistence.repository;

import dz.sh.hidra.modules.simulation.infrastructure.persistence.entity.SimulationModelJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for SimulationModel.
 */
@Repository
public interface SimulationModelJpaRepository extends JpaRepository<SimulationModelJpaEntity, String> {

    boolean existsByCode(String code);

    @Query(value = """
            SELECT EXISTS (
                SELECT 1
                FROM hidra_simulation_catalog_entry
                WHERE id = :modelTypeId
                  AND catalog_name = 'SIMULATION_MODEL_TYPE'
                  AND active = TRUE
            )
            """, nativeQuery = true)
    boolean existsActiveModelTypeById(@Param("modelTypeId") String modelTypeId);
}
