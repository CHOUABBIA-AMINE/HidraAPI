/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : PipelineJpaRepository
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Interface
 * @Layer       : Infrastructure
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.infrastructure.persistence.repository
 *
 * @Description : Spring Data repository for pipeline persistence entities.
 *
 */
package dz.sh.hidra.modules.topology.infrastructure.persistence.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import dz.sh.hidra.modules.topology.infrastructure.persistence.entity.PipelineJpaEntity;

/**
 * Spring Data repository for pipeline persistence entities.
 */
public interface PipelineJpaRepository extends JpaRepository<PipelineJpaEntity, String> {

    Optional<PipelineJpaEntity> findByCode(String code);

    boolean existsByCode(String code);

    List<PipelineJpaEntity> findByPipelineSystemId(String pipelineSystemId);

    List<PipelineJpaEntity> findByProductTypeId(String productTypeId);

    List<PipelineJpaEntity> findByStatus(String status);
}
