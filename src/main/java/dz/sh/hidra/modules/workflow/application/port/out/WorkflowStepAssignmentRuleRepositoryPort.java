/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : WorkflowStepAssignmentRuleRepositoryPort
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : workflow
 * @Package     : dz.sh.hidra.modules.workflow.application.port.out
 *
 * @Description : Repository port for WorkflowStepAssignmentRule.
 *
 */
package dz.sh.hidra.modules.workflow.application.port.out;

import dz.sh.hidra.modules.workflow.domain.model.WorkflowStepAssignmentRule;

import java.util.Optional;

/**
 * Repository port for WorkflowStepAssignmentRule.
 */
public interface WorkflowStepAssignmentRuleRepositoryPort {

    WorkflowStepAssignmentRule save(WorkflowStepAssignmentRule model);

    Optional<WorkflowStepAssignmentRule> findById(String id);
}
