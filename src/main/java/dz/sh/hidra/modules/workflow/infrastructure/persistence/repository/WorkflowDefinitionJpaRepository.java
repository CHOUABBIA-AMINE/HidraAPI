/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : WorkflowDefinitionJpaRepository
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Repository
 * @Layer       : Infrastructure
 * @Module      : workflow
 * @Package     : dz.sh.hidra.modules.workflow.infrastructure.persistence.repository
 *
 * @Description : Spring Data repository for workflow definitions.
 *
 */
package dz.sh.hidra.modules.workflow.infrastructure.persistence.repository;

import dz.sh.hidra.modules.workflow.infrastructure.persistence.entity.WorkflowDefinitionJpaEntity;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data repository for workflow definitions.
 */
public interface WorkflowDefinitionJpaRepository extends JpaRepository<WorkflowDefinitionJpaEntity, String> {

    Optional<WorkflowDefinitionJpaEntity> findByCode(String code);

    boolean existsByCode(String code);

    Page<WorkflowDefinitionJpaEntity> findByStatus(String status, Pageable pageable);

    Page<WorkflowDefinitionJpaEntity> findByTypeId(String typeId, Pageable pageable);

    Page<WorkflowDefinitionJpaEntity> findByTypeIdAndStatus(String typeId, String status, Pageable pageable);

    Page<WorkflowDefinitionJpaEntity> findByCodeContainingIgnoreCase(String code, Pageable pageable);
}
