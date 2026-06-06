/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : PipelineSystemJpaRepository
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Interface
 * @Layer       : Infrastructure
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.infrastructure.persistence.repository
 *
 * @Description : Spring Data repository for pipeline system persistence entities.
 *
 */
package dz.sh.hidra.modules.topology.infrastructure.persistence.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import dz.sh.hidra.modules.topology.infrastructure.persistence.entity.PipelineSystemJpaEntity;

/**
 * Spring Data repository for pipeline system persistence entities.
 */
public interface PipelineSystemJpaRepository extends JpaRepository<PipelineSystemJpaEntity, String> {

    Optional<PipelineSystemJpaEntity> findByCode(String code);

    boolean existsByCode(String code);

    List<PipelineSystemJpaEntity> findByProductTypeId(String productTypeId);

    List<PipelineSystemJpaEntity> findByStatus(String status);
}
