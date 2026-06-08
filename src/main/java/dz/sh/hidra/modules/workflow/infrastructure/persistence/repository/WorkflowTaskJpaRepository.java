/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : WorkflowTaskJpaRepository
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Repository
 * @Layer       : Infrastructure
 * @Module      : workflow
 * @Package     : dz.sh.hidra.modules.workflow.infrastructure.persistence.repository
 *
 * @Description : Spring Data repository for workflow tasks.
 *
 */
package dz.sh.hidra.modules.workflow.infrastructure.persistence.repository;

import dz.sh.hidra.modules.workflow.infrastructure.persistence.entity.WorkflowTaskJpaEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data repository for workflow tasks.
 */
public interface WorkflowTaskJpaRepository extends JpaRepository<WorkflowTaskJpaEntity, String> {

    Page<WorkflowTaskJpaEntity> findByInstanceId(String instanceId, Pageable pageable);

    Page<WorkflowTaskJpaEntity> findByStatus(String status, Pageable pageable);

    Page<WorkflowTaskJpaEntity> findByAssignedActorId(String assignedActorId, Pageable pageable);

    Page<WorkflowTaskJpaEntity> findByAssignedOrganizationUnitId(String assignedOrganizationUnitId, Pageable pageable);

    Page<WorkflowTaskJpaEntity> findByAssignedActorIdAndStatus(String assignedActorId, String status, Pageable pageable);

    Page<WorkflowTaskJpaEntity> findByAssignedOrganizationUnitIdAndStatus(String assignedOrganizationUnitId, String status, Pageable pageable);

    Page<WorkflowTaskJpaEntity> findByInstanceIdAndStatus(String instanceId, String status, Pageable pageable);
}
