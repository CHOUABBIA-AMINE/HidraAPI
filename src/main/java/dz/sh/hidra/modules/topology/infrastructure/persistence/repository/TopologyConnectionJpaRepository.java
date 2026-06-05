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
 *
 * <p>Business role:
 * Provides storage access for explicit topology graph connections.
 *
 * <p>Architecture role:
 * This is an infrastructure repository used only by topology persistence adapters. Application
 * services must depend on outbound ports, not on this interface.
 *
 * <p>Validation:
 * Domain validation occurs before mapping. Database constraints enforce required fields and code
 * uniqueness.
 *
 * <p>Usage:
 * Use only from topology repository adapters created in TOP-012.
 */
public interface TopologyConnectionJpaRepository extends JpaRepository<TopologyConnectionJpaEntity, String> {

    Optional<TopologyConnectionJpaEntity> findByCode(String code);

    boolean existsByCode(String code);

    List<TopologyConnectionJpaEntity> findByFromNodeId(String fromNodeId);

    List<TopologyConnectionJpaEntity> findByToNodeId(String toNodeId);

    List<TopologyConnectionJpaEntity> findByConnectionType(String connectionType);

    List<TopologyConnectionJpaEntity> findByLinkedAssetTypeAndLinkedAssetId(String linkedAssetType, String linkedAssetId);

    List<TopologyConnectionJpaEntity> findByStatus(String status);
}
