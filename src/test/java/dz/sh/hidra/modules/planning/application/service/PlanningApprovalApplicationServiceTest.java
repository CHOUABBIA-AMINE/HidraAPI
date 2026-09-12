/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : PlanningApprovalApplicationServiceTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-12
 *
 * @Type        : Class
 * @Layer       : Application Test
 * @Module      : planning
 * @Package     : dz.sh.hidra.modules.planning.application.service
 *
 * @Description : Verifies authoritative planning/workflow approval relation and lifecycle effects.
 *
 */
package dz.sh.hidra.modules.planning.application.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import dz.sh.hidra.modules.planning.application.port.in.PlanningApprovalUseCase;
import dz.sh.hidra.modules.planning.application.port.out.PlanRevisionRepositoryPort;
import dz.sh.hidra.modules.planning.domain.model.PlanRevision;
import dz.sh.hidra.modules.planning.domain.value.PlanRevisionStatus;
import dz.sh.hidra.modules.workflow.application.port.in.ExecuteWorkflowTargetTransitionUseCase;
import dz.sh.hidra.modules.workflow.application.port.in.WorkflowQueryUseCase;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.junit.jupiter.api.Test;

class PlanningApprovalApplicationServiceTest {

    private static final Instant TASK_UPDATED_AT = Instant.parse("2026-09-12T06:00:00Z");
    private static final Instant EXECUTED_AT = Instant.parse("2026-09-12T06:05:00Z");

    @Test
    void resolvesRevisionScopedCurrentTaskAndActions() {
        InMemoryRevisionRepository revisions = new InMemoryRevisionRepository(revision());
        FakeWorkflowQuery workflow = new FakeWorkflowQuery("planning", "REV-1");
        PlanningApprovalApplicationService service = new PlanningApprovalApplicationService(revisions, workflow, command -> null);

        PlanningApprovalUseCase.ApprovalView view = service.approval("REV-1", "ACTOR-1", Set.of("planning:revisions:approve"));

        assertThat(view.workflowInstanceId()).isEqualTo("WF-1");
        assertThat(view.currentTaskId()).isEqualTo("TASK-1");
        assertThat(view.currentTaskUpdatedAt()).isEqualTo(TASK_UPDATED_AT);
        assertThat(view.actions()).singleElement().satisfies(action -> {
            assertThat(action.transitionId()).isEqualTo("TRANSITION-1");
            assertThat(action.decision()).isEqualTo("APPROVE");
            assertThat(action.permitted()).isTrue();
        });
    }

    @Test
    void rejectsWorkflowInstanceThatTargetsAnotherRevision() {
        PlanningApprovalApplicationService service = new PlanningApprovalApplicationService(
                new InMemoryRevisionRepository(revision()), new FakeWorkflowQuery("planning", "REV-OTHER"), command -> null
        );

        assertThatThrownBy(() -> service.approval("REV-1", "ACTOR-1", Set.of()))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("does not target this planning revision");
    }

    @Test
    void appliesApproveDecisionToPlanningRevisionAfterWorkflowExecution() {
        InMemoryRevisionRepository revisions = new InMemoryRevisionRepository(revision());
        ExecuteWorkflowTargetTransitionUseCase transition = command -> new ExecuteWorkflowTargetTransitionUseCase.Result(
                "ACTION-1", command.taskId(), "APPROVED", "WF-1", "COMPLETED", command.transitionId(),
                "APPROVE", "STEP-END", null, EXECUTED_AT
        );
        PlanningApprovalApplicationService service = new PlanningApprovalApplicationService(
                revisions, new FakeWorkflowQuery("planning", "REV-1"), transition
        );

        PlanningApprovalUseCase.ExecutionView result = service.execute(
                "REV-1", "TRANSITION-1", new PlanningApprovalUseCase.ExecutionCommand(
                        TASK_UPDATED_AT, null, null, null, "CORR-1", "ACTOR-1", "actor", "Actor", Set.of("*")
                )
        );

        assertThat(result.revisionStatus()).isEqualTo("APPROVED");
        assertThat(revisions.value.status()).isEqualTo(PlanRevisionStatus.APPROVED);
        assertThat(revisions.value.approvedByActorId()).isEqualTo("ACTOR-1");
        assertThat(revisions.value.approvedAt()).isEqualTo(EXECUTED_AT);
    }

    @Test
    void propagatesWorkflowStaleTaskConflictWithoutChangingPlanningRevision() {
        InMemoryRevisionRepository revisions = new InMemoryRevisionRepository(revision());
        ExecuteWorkflowTargetTransitionUseCase transition = command -> {
            throw new IllegalStateException("Workflow task changed after it was loaded.");
        };
        PlanningApprovalApplicationService service = new PlanningApprovalApplicationService(
                revisions, new FakeWorkflowQuery("planning", "REV-1"), transition
        );

        assertThatThrownBy(() -> service.execute(
                "REV-1", "TRANSITION-1", new PlanningApprovalUseCase.ExecutionCommand(
                        TASK_UPDATED_AT.minusSeconds(1), null, null, null, null,
                        "ACTOR-1", "actor", "Actor", Set.of("*")
                )
        )).hasMessageContaining("changed after it was loaded");
        assertThat(revisions.value.status()).isEqualTo(PlanRevisionStatus.SUBMITTED);
    }

    private static PlanRevision revision() {
        return new PlanRevision(
                "REV-1", "PLAN-1", 1, "R1", PlanRevisionStatus.SUBMITTED, null, null, null,
                "ACTOR-0", Instant.parse("2026-09-12T05:00:00Z"), null, null, "WF-1",
                Instant.parse("2026-09-12T04:00:00Z"), Instant.parse("2026-09-12T05:00:00Z")
        );
    }

    private static final class InMemoryRevisionRepository implements PlanRevisionRepositoryPort {
        private PlanRevision value;

        private InMemoryRevisionRepository(PlanRevision value) {
            this.value = value;
        }

        @Override
        public PlanRevision save(PlanRevision model) {
            value = model;
            return model;
        }

        @Override
        public Optional<PlanRevision> findById(String id) {
            return Optional.ofNullable(value).filter(revision -> revision.id().equals(id));
        }
    }

    private static final class FakeWorkflowQuery implements WorkflowQueryUseCase {
        private final String targetModule;
        private final String targetId;

        private FakeWorkflowQuery(String targetModule, String targetId) {
            this.targetModule = targetModule;
            this.targetId = targetId;
        }

        @Override
        public Page<TaskView> tasks(String actorReference, String view, int page, int size) {
            throw new UnsupportedOperationException();
        }

        @Override
        public TaskView task(String id) {
            throw new UnsupportedOperationException();
        }

        @Override
        public TaskView currentTask(String instanceId) {
            return new TaskView(
                    "TASK-1", "WF-1", "STEP-1", "OPEN", "ACTOR-1", "actor", "Actor",
                    null, null, null, null, null, null, null, null, null, "Review", "NORMAL",
                    null, null, null, TASK_UPDATED_AT.minusSeconds(60), TASK_UPDATED_AT
            );
        }

        @Override
        public InstanceView instance(String id) {
            return new InstanceView(
                    "WF-1", "DEF-1", 1, "PURPOSE-1", targetModule, "PLAN_REVISION", targetId,
                    "R1", "Revision 1", "IN_PROGRESS", "STEP-1", "ACTOR-0", "starter", "Starter",
                    Instant.parse("2026-09-12T05:00:00Z"), null, null, "CORR-1"
            );
        }

        @Override
        public List<TimelineEntry> timeline(String instanceId) {
            return List.of();
        }

        @Override
        public List<AvailableActionView> availableActions(String taskId, String actorReference, Set<String> effectivePermissions) {
            return List.of(new AvailableActionView(
                    "TRANSITION-1", "APPROVE", "STEP-1", "STEP-END", false, false,
                    "planning:revisions:approve", null, true
            ));
        }
    }
}
