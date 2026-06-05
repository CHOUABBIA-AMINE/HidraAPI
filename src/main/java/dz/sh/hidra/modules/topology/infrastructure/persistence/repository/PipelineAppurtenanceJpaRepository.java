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
 *
 * <p>Business role:
 * Provides storage access for valves, injection points, extraction points, purge points, vents, drains, scraper points, hot taps, bypass points, metering points, sampling points, and connection points.
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
public interface PipelineAppurtenanceJpaRepository extends JpaRepository<PipelineAppurtenanceJpaEntity, String> {

    Optional<PipelineAppurtenanceJpaEntity> findByCode(String code);

    boolean existsByCode(String code);

    List<PipelineAppurtenanceJpaEntity> findByPipelineId(String pipelineId);

    List<PipelineAppurtenanceJpaEntity> findByNodeId(String nodeId);

    List<PipelineAppurtenanceJpaEntity> findByAppurtenanceType(String appurtenanceType);

    List<PipelineAppurtenanceJpaEntity> findByValveType(String valveType);

    List<PipelineAppurtenanceJpaEntity> findByStatus(String status);
}
