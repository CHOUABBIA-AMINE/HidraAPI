/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : WorkflowActionJpaRepository
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Repository
 * @Layer       : Infrastructure
 * @Module      : workflow
 * @Package     : dz.sh.hidra.modules.workflow.infrastructure.persistence.repository
 *
 * @Description : Spring Data repository for workflow actions.
 *
 */
package dz.sh.hidra.modules.workflow.infrastructure.persistence.repository;

import dz.sh.hidra.modules.workflow.infrastructure.persistence.entity.WorkflowActionJpaEntity;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data repository for workflow actions.
 */
public interface WorkflowActionJpaRepository extends JpaRepository<WorkflowActionJpaEntity, String> {

    List<WorkflowActionJpaEntity> findByInstanceIdOrderByActedAtAsc(String instanceId);

    List<WorkflowActionJpaEntity> findByTaskIdOrderByActedAtAsc(String taskId);

    Page<WorkflowActionJpaEntity> findByActorId(String actorId, Pageable pageable);

    Page<WorkflowActionJpaEntity> findByActionType(String actionType, Pageable pageable);

    Page<WorkflowActionJpaEntity> findByDecision(String decision, Pageable pageable);
}
