/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : WorkflowTaskDomainService
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Class
 * @Layer       : Domain
 * @Module      : workflow
 * @Package     : dz.sh.hidra.modules.workflow.domain.service
 *
 * @Description : Domain service for workflow task lifecycle orchestration.
 *
 */
package dz.sh.hidra.modules.workflow.domain.service;

import java.util.Objects;

import dz.sh.hidra.modules.workflow.domain.model.WorkflowComment;
import dz.sh.hidra.modules.workflow.domain.model.WorkflowInstance;
import dz.sh.hidra.modules.workflow.domain.model.WorkflowStep;
import dz.sh.hidra.modules.workflow.domain.model.WorkflowTask;
import dz.sh.hidra.modules.workflow.domain.policy.WorkflowAssignmentPolicy;
import dz.sh.hidra.modules.workflow.domain.value.WorkflowActorReference;
import dz.sh.hidra.modules.workflow.domain.value.WorkflowCommentText;
import dz.sh.hidra.modules.workflow.domain.value.WorkflowDueDate;
import dz.sh.hidra.modules.workflow.domain.value.WorkflowOrganizationReference;
import dz.sh.hidra.modules.workflow.domain.value.WorkflowPriorityReference;
import dz.sh.hidra.modules.workflow.domain.value.WorkflowTaskStatus;

/**
 * Domain service for workflow task lifecycle orchestration.
 *
 * <p>Business role:
 * Opens, claims, completes, delegates, and comments workflow tasks.
 */
public final class WorkflowTaskDomainService {

    private final WorkflowAssignmentPolicy assignmentPolicy;

    public WorkflowTaskDomainService(WorkflowAssignmentPolicy assignmentPolicy) {
        this.assignmentPolicy = Objects.requireNonNull(assignmentPolicy, "WorkflowAssignmentPolicy must not be null.");
    }

    public WorkflowTask openTask(
            WorkflowInstance instance,
            WorkflowStep step,
            WorkflowActorReference assignedActor,
            WorkflowOrganizationReference assignedOrganization,
            WorkflowPriorityReference priority,
            WorkflowDueDate dueDate) {

        Objects.requireNonNull(instance, "Workflow instance must not be null.");
        Objects.requireNonNull(step, "Workflow step must not be null.");

        assignmentPolicy.requireValidAssignmentTarget(assignedActor, assignedOrganization);

        return WorkflowTask.open(
                instance.id(),
                step.id(),
                assignedActor,
                assignedOrganization,
                priority,
                dueDate);
    }

    public WorkflowTask claimTask(
            WorkflowTask task,
            WorkflowActorReference actor) {

        assignmentPolicy.requireTaskCanBeClaimed(task, actor);
        return task.claim(actor);
    }

    public WorkflowTask approveTask(
            WorkflowTask task,
            WorkflowActorReference actor) {

        assignmentPolicy.requireTaskCanBeCompleted(task, actor, WorkflowTaskStatus.APPROVED);
        return task.complete(actor, WorkflowTaskStatus.APPROVED);
    }

    public WorkflowTask rejectTask(
            WorkflowTask task,
            WorkflowActorReference actor) {

        assignmentPolicy.requireTaskCanBeCompleted(task, actor, WorkflowTaskStatus.REJECTED);
        return task.complete(actor, WorkflowTaskStatus.REJECTED);
    }

    public WorkflowTask cancelTask(
            WorkflowTask task,
            WorkflowActorReference actor) {

        assignmentPolicy.requireTaskCanBeCompleted(task, actor, WorkflowTaskStatus.CANCELLED);
        return task.complete(actor, WorkflowTaskStatus.CANCELLED);
    }

    public WorkflowTask delegateTask(
            WorkflowTask task,
            WorkflowActorReference nextActor,
            WorkflowOrganizationReference nextOrganization) {

        assignmentPolicy.requireTaskCanBeReassigned(task, nextActor, nextOrganization);
        return task.delegateTo(nextActor, nextOrganization);
    }

    public WorkflowComment addComment(
            WorkflowInstance instance,
            WorkflowTask task,
            WorkflowActorReference actor,
            WorkflowCommentText text) {

        Objects.requireNonNull(instance, "Workflow instance must not be null.");
        Objects.requireNonNull(actor, "Workflow comment actor must not be null.");
        Objects.requireNonNull(text, "Workflow comment text must not be null.");

        return WorkflowComment.add(
                instance.id(),
                task == null ? null : task.id(),
                actor,
                text);
    }
}
