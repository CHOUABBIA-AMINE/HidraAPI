/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaWorkflowQueryAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : workflow
 * @Package     : dz.sh.hidra.modules.workflow.infrastructure.query
 *
 * @Description : JPA-backed workflow task inbox, history, and available-transition query adapter.
 *
 */
package dz.sh.hidra.modules.workflow.infrastructure.query;

import dz.sh.hidra.modules.workflow.application.port.in.WorkflowQueryUseCase;
import dz.sh.hidra.modules.workflow.infrastructure.persistence.entity.WorkflowActionJpaEntity;
import dz.sh.hidra.modules.workflow.infrastructure.persistence.entity.WorkflowInstanceJpaEntity;
import dz.sh.hidra.modules.workflow.infrastructure.persistence.entity.WorkflowTaskJpaEntity;
import dz.sh.hidra.modules.workflow.infrastructure.persistence.entity.WorkflowTransitionJpaEntity;
import jakarta.persistence.EntityManager;
import java.util.List;
import java.util.Locale;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Set;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional(readOnly = true)
public class JpaWorkflowQueryAdapter implements WorkflowQueryUseCase {

    private static final String ALL_PERMISSIONS = "*";
    private static final Set<String> ACTIONABLE_TASK_STATES = Set.of("OPEN", "CLAIMED", "IN_REVIEW");
    private static final Set<String> NON_TERMINAL_INSTANCE_STATES = Set.of("DRAFT", "STARTED", "IN_PROGRESS", "WAITING");
    private static final Set<String> COMPLETED_TASK_STATES = Set.of(
            "APPROVED", "REJECTED", "RETURNED", "DELEGATED", "ESCALATED", "CANCELLED", "EXPIRED"
    );

    private final EntityManager entityManager;

    public JpaWorkflowQueryAdapter(EntityManager entityManager) {
        this.entityManager = Objects.requireNonNull(entityManager, "EntityManager must not be null.");
    }

    @Override
    public Page<TaskView> tasks(String actorReference, String view, int page, int size) {
        String normalizedView = view == null ? "assigned" : view.trim().toLowerCase(Locale.ROOT);
        List<TaskView> all = entityManager
                .createQuery("select e from WorkflowTaskJpaEntity e order by e.updatedAt desc", WorkflowTaskJpaEntity.class)
                .getResultList().stream()
                .filter(entity -> belongsToActor(entity, actorReference))
                .filter(entity -> matchesView(entity, normalizedView))
                .map(this::taskView)
                .toList();
        return page(all, page, size);
    }

    @Override
    public TaskView task(String id) {
        WorkflowTaskJpaEntity entity = entityManager.find(WorkflowTaskJpaEntity.class, id);
        if (entity == null) {
            throw new NoSuchElementException("Unknown workflow task: " + id);
        }
        return taskView(entity);
    }

    @Override
    public InstanceView instance(String id) {
        WorkflowInstanceJpaEntity entity = entityManager.find(WorkflowInstanceJpaEntity.class, id);
        if (entity == null) {
            throw new NoSuchElementException("Unknown workflow instance: " + id);
        }
        return instanceView(entity);
    }

    @Override
    public List<TimelineEntry> timeline(String instanceId) {
        if (entityManager.find(WorkflowInstanceJpaEntity.class, instanceId) == null) {
            throw new NoSuchElementException("Unknown workflow instance: " + instanceId);
        }
        return entityManager
                .createQuery(
                        "select e from WorkflowActionJpaEntity e where e.instanceId = :instance order by e.actionSequence",
                        WorkflowActionJpaEntity.class
                )
                .setParameter("instance", instanceId)
                .getResultList().stream()
                .map(this::timelineEntry)
                .toList();
    }

    @Override
    public List<AvailableActionView> availableActions(
            String taskId,
            String actorReference,
            Set<String> effectivePermissions
    ) {
        WorkflowTaskJpaEntity task = entityManager.find(WorkflowTaskJpaEntity.class, taskId);
        if (task == null) {
            throw new NoSuchElementException("Unknown workflow task: " + taskId);
        }
        WorkflowInstanceJpaEntity instance = entityManager.find(WorkflowInstanceJpaEntity.class, task.instanceId());
        if (instance == null) {
            throw new NoSuchElementException("Unknown workflow instance: " + task.instanceId());
        }
        boolean assigned = belongsToActiveActor(task, actorReference);
        boolean actionable = ACTIONABLE_TASK_STATES.contains(String.valueOf(task.status()))
                && task.completedAt() == null
                && NON_TERMINAL_INSTANCE_STATES.contains(String.valueOf(instance.status()))
                && Objects.equals(instance.currentStepId(), task.stepId());
        Set<String> permissions = effectivePermissions == null ? Set.of() : effectivePermissions;
        return entityManager
                .createQuery(
                        "select e from WorkflowTransitionJpaEntity e where e.definitionId = :definition and e.fromStepId = :step order by e.id",
                        WorkflowTransitionJpaEntity.class
                )
                .setParameter("definition", instance.definitionId())
                .setParameter("step", task.stepId())
                .getResultList().stream()
                .map(transition -> actionView(transition, assigned && actionable, permissions))
                .toList();
    }

    private boolean belongsToActor(WorkflowTaskJpaEntity entity, String actorReference) {
        return belongsToActiveActor(entity, actorReference)
                || (actorReference != null && actorReference.equals(entity.completedByActorId()));
    }

    private boolean belongsToActiveActor(WorkflowTaskJpaEntity entity, String actorReference) {
        if (actorReference == null || actorReference.isBlank()) {
            return false;
        }
        return actorReference.equals(entity.assignedActorId())
                || actorReference.equalsIgnoreCase(String.valueOf(entity.assignedActorUsernameSnapshot()))
                || actorReference.equals(entity.claimedByActorId());
    }

    private boolean matchesView(WorkflowTaskJpaEntity entity, String view) {
        String status = String.valueOf(entity.status());
        return switch (view) {
            case "all" -> true;
            case "delegated" -> "DELEGATED".equals(status) || entity.delegatedAt() != null;
            case "escalated" -> "ESCALATED".equals(status) || entity.escalatedAt() != null;
            case "completed" -> COMPLETED_TASK_STATES.contains(status) || entity.completedAt() != null;
            case "assigned" -> !COMPLETED_TASK_STATES.contains(status) && entity.completedAt() == null;
            default -> throw new IllegalArgumentException("Unsupported workflow task view: " + view);
        };
    }

    private AvailableActionView actionView(
            WorkflowTransitionJpaEntity transition,
            boolean executableContext,
            Set<String> effectivePermissions
    ) {
        String requiredPermission = transition.requiredPermissionCode();
        boolean permissionSatisfied = effectivePermissions.contains(ALL_PERMISSIONS)
                || requiredPermission == null
                || requiredPermission.isBlank()
                || effectivePermissions.contains(requiredPermission);
        boolean executionMechanismAvailable = (transition.conditionExpression() == null || transition.conditionExpression().isBlank())
                && (transition.targetModuleCallback() == null || transition.targetModuleCallback().isBlank());
        boolean permitted = executableContext && permissionSatisfied && executionMechanismAvailable;
        return new AvailableActionView(
                transition.id(),
                String.valueOf(transition.decision()),
                transition.fromStepId(),
                transition.toStepId(),
                transition.reasonRequired(),
                transition.commentRequired(),
                requiredPermission,
                transition.targetModuleCallback(),
                permitted
        );
    }

    private TaskView taskView(WorkflowTaskJpaEntity entity) {
        return new TaskView(
                entity.id(), entity.instanceId(), entity.stepId(), String.valueOf(entity.status()),
                entity.assignedActorId(), entity.assignedActorUsernameSnapshot(), entity.assignedActorDisplayNameSnapshot(),
                entity.assignedOrganizationUnitId(), entity.assignedOrganizationUnitNameSnapshot(),
                entity.assignedRoleCodeSnapshot(), entity.priorityId(), entity.dueAt(), entity.claimedByActorId(),
                entity.claimedAt(), entity.completedByActorId(), entity.completedAt(), entity.taskLabelSnapshot(),
                entity.slaStatus() == null ? null : String.valueOf(entity.slaStatus()), entity.escalatedAt(),
                entity.delegatedAt(), entity.expiresAt(), entity.createdAt(), entity.updatedAt()
        );
    }

    private InstanceView instanceView(WorkflowInstanceJpaEntity entity) {
        return new InstanceView(
                entity.id(), entity.definitionId(), entity.definitionVersion(), entity.workflowPurposeId(),
                entity.targetModule(), entity.targetTypeId(), entity.targetId(), entity.targetCodeSnapshot(),
                entity.targetLabelSnapshot(), String.valueOf(entity.status()), entity.currentStepId(),
                entity.startedByActorId(), entity.startedByUsernameSnapshot(), entity.startedByDisplayNameSnapshot(),
                entity.startedAt(), entity.completedAt(), entity.cancelledAt(), entity.correlationId()
        );
    }

    private TimelineEntry timelineEntry(WorkflowActionJpaEntity entity) {
        return new TimelineEntry(
                entity.id(), entity.instanceId(), entity.taskId(), String.valueOf(entity.actionType()),
                entity.decision() == null ? null : String.valueOf(entity.decision()), entity.actorId(),
                entity.actorDisplayNameSnapshot(), entity.decisionNote(), entity.commentText(),
                entity.actionSequence(), entity.actedAt()
        );
    }

    private static <T> Page<T> page(List<T> all, int requestedPage, int requestedSize) {
        int page = Math.max(0, requestedPage);
        int size = Math.min(200, Math.max(1, requestedSize));
        int from = Math.min(all.size(), page * size);
        int to = Math.min(all.size(), from + size);
        int totalPages = all.isEmpty() ? 0 : (all.size() + size - 1) / size;
        return new Page<>(List.copyOf(all.subList(from, to)), page, size, all.size(), totalPages, to < all.size());
    }
}
