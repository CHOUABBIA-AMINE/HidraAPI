/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : WorkflowStepAssignmentRuleJpaRepository
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-05
 *
 * @Type        : Interface
 * @Layer       : Infrastructure
 * @Module      : workflow
 * @Package     : dz.sh.hidra.modules.workflow.infrastructure.persistence.repository
 *
 * @Description : Spring Data JPA repository for WorkflowStepAssignmentRule.
 *
 */
package dz.sh.hidra.modules.workflow.infrastructure.persistence.repository;

import dz.sh.hidra.modules.workflow.infrastructure.persistence.entity.WorkflowStepAssignmentRuleJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for WorkflowStepAssignmentRule.
 */
@Repository
public interface WorkflowStepAssignmentRuleJpaRepository extends JpaRepository<WorkflowStepAssignmentRuleJpaEntity, String> {

    @Query(
            value = """
                    select count(*) > 0
                    from hidra_workflow_type_catalog
                    where id = :assignmentModeId
                      and catalog_name = 'WORKFLOW_ASSIGNMENT_MODE'
                    """,
            nativeQuery = true
    )
    boolean existsAssignmentModeInRequiredFamily(
            @Param("assignmentModeId") String assignmentModeId
    );
}
