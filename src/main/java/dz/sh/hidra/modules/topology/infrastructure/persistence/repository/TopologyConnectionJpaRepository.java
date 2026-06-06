/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TopologyConnectionJpaRepository
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Interface
 * @Layer       : Infrastructure
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.infrastructure.persistence.repository
 *
 * @Description : Spring Data repository for topology connection persistence entities.
 *
 */
package dz.sh.hidra.modules.topology.infrastructure.persistence.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import dz.sh.hidra.modules.topology.infrastructure.persistence.entity.TopologyConnectionJpaEntity;

/**
 * Spring Data repository for topology connection persistence entities.
 */
public interface TopologyConnectionJpaRepository extends JpaRepository<TopologyConnectionJpaEntity, String> {

    Optional<TopologyConnectionJpaEntity> findByCode(String code);

    boolean existsByCode(String code);

    List<TopologyConnectionJpaEntity> findByFromNodeId(String fromNodeId);

    List<TopologyConnectionJpaEntity> findByToNodeId(String toNodeId);

    List<TopologyConnectionJpaEntity> findByConnectionTypeId(String connectionTypeId);

    List<TopologyConnectionJpaEntity> findByLinkedAssetTypeAndLinkedAssetId(String linkedAssetType, String linkedAssetId);

    List<TopologyConnectionJpaEntity> findByStatus(String status);
}
