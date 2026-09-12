/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaWorkflowTargetApprovalQueryAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-12
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : workflow
 * @Package     : dz.sh.hidra.modules.workflow.infrastructure.query
 *
 * @Description : Resolves one verified workflow target to its current actionable task and transition metadata.
 *
 */
package dz.sh.hidra.modules.workflow.infrastructure.query;

import dz.sh.hidra.modules.workflow.application.port.out.WorkflowTargetApprovalQueryPort;
import dz.sh.hidra.modules.workflow.infrastructure.persistence.entity.WorkflowInstanceJpaEntity;
import dz.sh.hidra.modules.workflow.infrastructure.persistence.entity.WorkflowTaskJpaEntity;
import dz.sh.hidra.modules.workflow.infrastructure.persistence.entity.WorkflowTransitionJpaEntity;
import jakarta.persistence.EntityManager;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional(readOnly = true)
public class JpaWorkflowTargetApprovalQueryAdapter implements WorkflowTargetApprovalQueryPort {

    private static final String ALL_PERMISSIONS = "*";
    private static final Set<String> ACTIONABLE_TASK_STATES = Set.of("OPEN", "CLAIMED", "IN_REVIEW");
    private static final Set<String> NON_TERMINAL_INSTANCE_STATES = Set.of("DRAFT", "STARTED", "IN_PROGRESS", "WAITING");

    private final EntityManager entityManager;

    public JpaWorkflowTargetApprovalQueryAdapter(EntityManager entityManager) {
        this.entityManager = Objects.requireNonNull(entityManager, "EntityManager must not be null.");
    }

    @Override
    public Context load(
            String instanceId,
            String targetModule,
            String targetTypeId,
            String targetId,
            String actorReference,
            Set<String> effectivePermissions
    ) {
        WorkflowInstanceJpaEntity instance = entityManager.find(WorkflowInstanceJpaEntity.class, requireText(instanceId));
        if (instance == null) {
            throw new QueryException(QueryException.Kind.NOT_FOUND, "Unknown workflow instance: " + instanceId);
        }
        if (!Objects.equals(normalize(targetModule), normalize(instance.targetModule()))
                || !Objects.equals(normalize(targetTypeId), normalize(instance.targetTypeId()))
                || !Objects.equals(normalize(targetId), normalize(instance.targetId()))) {
            throw new QueryException(QueryException.Kind.BOUNDARY, "Workflow instance does not belong to the requested target.");
        }
        if (!NON_TERMINAL_INSTANCE_STATES.contains(String.valueOf(instance.status()))) {
            throw new QueryException(QueryException.Kind.CONFLICT, "Workflow instance is no longer actionable: " + instance.id());
        }

        List<WorkflowTaskJpaEntity> actionableTasks = entityManager
                .createQuery(
                        "select e from WorkflowTaskJpaEntity e where e.instanceId = :instance and e.stepId = :step order by e.updatedAt desc",
                        WorkflowTaskJpaEntity.class
                )
                .setParameter("instance", instance.id())
                .setParameter("step", instance.currentStepId())
                .getResultList().stream()
                .filter(task -> ACTIONABLE_TASK_STATES.contains(String.valueOf(task.status())))
                .filter(task -> task.completedAt() == null)
                .toList();
        if (actionableTasks.isEmpty()) {
            throw new QueryException(QueryException.Kind.CONFLICT, "Workflow instance has no actionable task for its current step.");
        }
        if (actionableTasks.size() > 1) {
            throw new QueryException(QueryException.Kind.CONFLICT, "Workflow instance has multiple actionable tasks for its current step.");
        }

        WorkflowTaskJpaEntity task = actionableTasks.getFirst();
        boolean assigned = belongsToActiveActor(task, actorReference);
        Set<String> permissions = effectivePermissions == null ? Set.of() : effectivePermissions;
        List<Action> actions = entityManager
                .createQuery(
                        "select e from WorkflowTransitionJpaEntity e where e.definitionId = :definition and e.fromStepId = :step order by e.id",
                        WorkflowTransitionJpaEntity.class
                )
                .setParameter("definition", instance.definitionId())
                .setParameter("step", task.stepId())
                .getResultList().stream()
                .map(transition -> action(transition, assigned, permissions))
                .toList();
        return new Context(
                instance.id(), String.valueOf(instance.status()), task.id(), String.valueOf(task.status()), task.updatedAt(), actions
        );
    }

    private Action action(WorkflowTransitionJpaEntity transition, boolean assigned, Set<String> permissions) {
        String requiredPermission = normalize(transition.requiredPermissionCode());
        boolean permissionSatisfied = requiredPermission == null
                || permissions.contains(ALL_PERMISSIONS)
                || permissions.contains(requiredPermission);
        boolean executionMechanismAvailable = normalize(transition.conditionExpression()) == null
                && normalize(transition.targetModuleCallback()) == null;
        return new Action(
                transition.id(),
                String.valueOf(transition.decision()),
                transition.reasonRequired(),
                transition.commentRequired(),
                requiredPermission,
                assigned && permissionSatisfied && executionMechanismAvailable
        );
    }

    private static boolean belongsToActiveActor(WorkflowTaskJpaEntity task, String actorReference) {
        if (actorReference == null || actorReference.isBlank()) {
            return false;
        }
        return actorReference.equals(task.assignedActorId())
                || actorReference.equalsIgnoreCase(String.valueOf(task.assignedActorUsernameSnapshot()))
                || actorReference.equals(task.claimedByActorId());
    }

    private static String requireText(String value) {
        String normalized = normalize(value);
        if (normalized == null) {
            throw new QueryException(QueryException.Kind.BOUNDARY, "Workflow instance ID must not be blank.");
        }
        return normalized;
    }

    private static String normalize(String value) {
        return value == null || value.isBlank() ? null : value.trim();
    }
}
