/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : WorkflowInstanceJpaRepository
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Repository
 * @Layer       : Infrastructure
 * @Module      : workflow
 * @Package     : dz.sh.hidra.modules.workflow.infrastructure.persistence.repository
 *
 * @Description : Spring Data repository for workflow instances.
 *
 */
package dz.sh.hidra.modules.workflow.infrastructure.persistence.repository;

import dz.sh.hidra.modules.workflow.infrastructure.persistence.entity.WorkflowInstanceJpaEntity;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data repository for workflow instances.
 */
public interface WorkflowInstanceJpaRepository extends JpaRepository<WorkflowInstanceJpaEntity, String> {

    Optional<WorkflowInstanceJpaEntity> findFirstByTargetModuleAndTargetTypeIdAndTargetIdAndStatusNotIn(String targetModule, String targetTypeId, String targetId, java.util.Collection<String> terminalStatuses);

    Page<WorkflowInstanceJpaEntity> findByDefinitionId(String definitionId, Pageable pageable);

    Page<WorkflowInstanceJpaEntity> findByStatus(String status, Pageable pageable);

    Page<WorkflowInstanceJpaEntity> findByStartedByActorId(String startedByActorId, Pageable pageable);

    Page<WorkflowInstanceJpaEntity> findByTargetModuleAndTargetTypeIdAndTargetId(String targetModule, String targetTypeId, String targetId, Pageable pageable);
}
