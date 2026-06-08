/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : WorkflowAssignmentDomainService
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Class
 * @Layer       : Domain
 * @Module      : workflow
 * @Package     : dz.sh.hidra.modules.workflow.domain.service
 *
 * @Description : Domain service for workflow assignment orchestration.
 *
 */
package dz.sh.hidra.modules.workflow.domain.service;

import java.util.Objects;

import dz.sh.hidra.modules.workflow.domain.model.WorkflowAssignment;
import dz.sh.hidra.modules.workflow.domain.model.WorkflowTask;
import dz.sh.hidra.modules.workflow.domain.policy.WorkflowAssignmentPolicy;
import dz.sh.hidra.modules.workflow.domain.value.WorkflowActorReference;
import dz.sh.hidra.modules.workflow.domain.value.WorkflowOrganizationReference;

/**
 * Domain service for workflow assignment orchestration.
 *
 * <p>Business role:
 * Creates assignment records and validates reassignment operations.
 */
public final class WorkflowAssignmentDomainService {

    private final WorkflowAssignmentPolicy assignmentPolicy;

    public WorkflowAssignmentDomainService(WorkflowAssignmentPolicy assignmentPolicy) {
        this.assignmentPolicy = Objects.requireNonNull(assignmentPolicy, "WorkflowAssignmentPolicy must not be null.");
    }

    public WorkflowAssignment assignTask(
            WorkflowTask task,
            WorkflowActorReference actor,
            WorkflowOrganizationReference organization) {

        Objects.requireNonNull(task, "Workflow task must not be null.");
        assignmentPolicy.requireValidAssignmentTarget(actor, organization);

        return WorkflowAssignment.assign(task.id(), actor, organization);
    }

    public WorkflowAssignment reassignTask(
            WorkflowTask task,
            WorkflowActorReference nextActor,
            WorkflowOrganizationReference nextOrganization) {

        Objects.requireNonNull(task, "Workflow task must not be null.");
        assignmentPolicy.requireTaskCanBeReassigned(task, nextActor, nextOrganization);

        return WorkflowAssignment.assign(task.id(), nextActor, nextOrganization);
    }
}
