/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : PipelineAppurtenanceJpaRepository
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Interface
 * @Layer       : Infrastructure
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.infrastructure.persistence.repository
 *
 * @Description : Spring Data repository for pipeline appurtenance persistence entities.
 *
 */
package dz.sh.hidra.modules.topology.infrastructure.persistence.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import dz.sh.hidra.modules.topology.infrastructure.persistence.entity.PipelineAppurtenanceJpaEntity;

/**
 * Spring Data repository for pipeline appurtenance persistence entities.
 */
public interface PipelineAppurtenanceJpaRepository extends JpaRepository<PipelineAppurtenanceJpaEntity, String> {

    Optional<PipelineAppurtenanceJpaEntity> findByCode(String code);

    boolean existsByCode(String code);

    List<PipelineAppurtenanceJpaEntity> findByPipelineId(String pipelineId);

    List<PipelineAppurtenanceJpaEntity> findByNodeId(String nodeId);

    List<PipelineAppurtenanceJpaEntity> findByAppurtenanceTypeId(String appurtenanceTypeId);

    List<PipelineAppurtenanceJpaEntity> findByValveTypeId(String valveTypeId);

    List<PipelineAppurtenanceJpaEntity> findByStatus(String status);
}
