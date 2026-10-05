/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : PipelineTypeJpaRepository
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-05
 *
 * @Type        : Interface
 * @Layer       : Infrastructure
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.infrastructure.persistence.repository
 *
 * @Description : Spring Data JPA repository for Pipeline classification catalog entries.
 *
 */
package dz.sh.hidra.modules.topology.infrastructure.persistence.repository;

import dz.sh.hidra.modules.topology.infrastructure.persistence.entity.PipelineTypeJpaEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PipelineTypeJpaRepository extends JpaRepository<PipelineTypeJpaEntity, String> {

    Optional<PipelineTypeJpaEntity> findByCode(String code);
}
