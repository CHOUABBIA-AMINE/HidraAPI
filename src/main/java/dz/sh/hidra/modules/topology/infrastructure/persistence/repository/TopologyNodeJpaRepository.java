/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TopologyNodeJpaRepository
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Interface
 * @Layer       : Infrastructure
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.infrastructure.persistence.repository
 *
 * @Description : Spring Data repository for topology node persistence entities.
 *
 */
package dz.sh.hidra.modules.topology.infrastructure.persistence.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import dz.sh.hidra.modules.topology.infrastructure.persistence.entity.TopologyNodeJpaEntity;

/**
 * Spring Data repository for topology node persistence entities.
 */
public interface TopologyNodeJpaRepository extends JpaRepository<TopologyNodeJpaEntity, String> {

    Optional<TopologyNodeJpaEntity> findByCode(String code);

    boolean existsByCode(String code);

    List<TopologyNodeJpaEntity> findByFacilityId(String facilityId);

    List<TopologyNodeJpaEntity> findByPipelineAppurtenanceId(String pipelineAppurtenanceId);

    List<TopologyNodeJpaEntity> findByNodeTypeId(String nodeTypeId);

    List<TopologyNodeJpaEntity> findByStatus(String status);
}
