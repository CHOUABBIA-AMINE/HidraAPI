/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : PlanningApprovalApplicationServiceTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-28
 *
 * @Type        : Class
 * @Layer       : Application Test
 * @Module      : planning
 * @Package     : dz.sh.hidra.modules.planning.application.service
 *
 * @Description : Verifies authoritative planning/workflow approval relation and lifecycle effects through the exported Workflow contract.
 *
 */
package dz.sh.hidra.modules.planning.application.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import dz.sh.hidra.modules.planning.application.port.in.PlanningApprovalUseCase;
import dz.sh.hidra.modules.planning.application.port.in.PlanningQueryUseCase;
import dz.sh.hidra.modules.planning.application.port.out.PlanRevisionRepositoryPort;
import dz.sh.hidra.modules.planning.application.port.out.OperationalPlanRepositoryPort;
import dz.sh.hidra.modules.planning.application.port.out.PlanTargetRepositoryPort;
import dz.sh.hidra.modules.planning.domain.model.OperationalPlan;
import dz.sh.hidra.modules.planning.domain.model.PlanTarget;
import dz.sh.hidra.modules.planning.domain.value.OperationalPlanStatus;
import dz.sh.hidra.modules.planning.domain.value.PlanTargetStatus;
import static org.mockito.Mockito.*;
import static org.mockito.ArgumentMatchers.*;
import dz.sh.hidra.modules.planning.domain.model.PlanRevision;
import dz.sh.hidra.modules.planning.domain.value.PlanRevisionStatus;
import dz.sh.hidra.modules.workflow.application.contract.planning.PlanningWorkflowContract;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import org.junit.jupiter.api.Test;

class PlanningApprovalApplicationServiceTest {

    private static final Instant TASK_UPDATED_AT = Instant.parse("2026-09-12T06:00:00Z");
    private static final Instant EXECUTED_AT = Instant.parse("2026-09-12T06:05:00Z");

    @Test
    void resolvesRevisionScopedCurrentTaskAndActions() {
        InMemoryRevisionRepository revisions = new InMemoryRevisionRepository(revision());
        FakePlanningWorkflow workflow = new FakePlanningWorkflow("planning", "REV-1", command -> null);
        PlanningApprovalApplicationService service = service(revisions, workflow);

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
        PlanningApprovalApplicationService service = service(
                new InMemoryRevisionRepository(revision()),
                new FakePlanningWorkflow("planning", "REV-OTHER", command -> null)
        );

        assertThatThrownBy(() -> service.approval("REV-1", "ACTOR-1", Set.of()))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("does not target this planning revision");
    }

    @Test
    void appliesApproveDecisionToPlanningRevisionAfterWorkflowExecution() {
        InMemoryRevisionRepository revisions = new InMemoryRevisionRepository(revision());
        PlanningWorkflowContract workflow = new FakePlanningWorkflow(
                "planning",
                "REV-1",
                command -> new PlanningWorkflowContract.TransitionResult(
                        "WF-1",
                        "COMPLETED",
                        command.transitionId(),
                        "APPROVE",
                        null,
                        EXECUTED_AT
                )
        );
        PlanningApprovalApplicationService service = service(revisions, workflow);

        PlanningApprovalUseCase.ExecutionView result = service.execute(
                "REV-1", "TRANSITION-1", new PlanningApprovalUseCase.ExecutionCommand(
                        TASK_UPDATED_AT, null, null, null, "CORR-1", "ACTOR-1", "actor", "Actor", Set.of("*")
                )
        );

        assertThat(result.revisionStatus()).isEqualTo("APPROVED");
        assertThat(revisions.value.status()).isEqualTo(PlanRevisionStatus.APPROVED);
        assertThat(revisions.value.approvedByActorId()).isEqualTo("ACTOR-1");
        assertThat(revisions.value.approvedAt()).isAfterOrEqualTo(EXECUTED_AT);
    }

    @Test
    void propagatesWorkflowStaleTaskConflictWithoutChangingPlanningRevision() {
        InMemoryRevisionRepository revisions = new InMemoryRevisionRepository(revision());
        PlanningWorkflowContract workflow = new FakePlanningWorkflow(
                "planning",
                "REV-1",
                command -> {
                    throw new IllegalStateException("Workflow task changed after it was loaded.");
                }
        );
        PlanningApprovalApplicationService service = service(revisions, workflow);

        assertThatThrownBy(() -> service.execute(
                "REV-1", "TRANSITION-1", new PlanningApprovalUseCase.ExecutionCommand(
                        TASK_UPDATED_AT.minusSeconds(1), null, null, null, null,
                        "ACTOR-1", "actor", "Actor", Set.of("*")
                )
        )).hasMessageContaining("changed after it was loaded");
        assertThat(revisions.value.status()).isEqualTo(PlanRevisionStatus.SUBMITTED);
    }

    private static PlanningApprovalApplicationService service(
            InMemoryRevisionRepository revisions, PlanningWorkflowContract workflow) {
        var plans = mock(OperationalPlanRepositoryPort.class);
        var targets = mock(PlanTargetRepositoryPort.class);
        var access = mock(PlanningAccessService.class);
        var plan = new OperationalPlan("PLAN-1", "PERIOD-1", "P1", null, "Plan", null,
                "TYPE-1", null, "FACILITY", "ASSET-1", "F1", null, null,
                OperationalPlanStatus.SUBMITTED, "REV-1", null, "ACTOR-0",
                TASK_UPDATED_AT.minusSeconds(7200), TASK_UPDATED_AT.minusSeconds(3000));
        when(plans.findByIdForUpdate("PLAN-1")).thenReturn(Optional.of(plan));
        when(access.planReviewer(plan)).thenReturn("ACTOR-1");
        var target = new PlanTarget("TARGET-1", "REV-1", null, null, "TARGET-TYPE-1",
                "FACILITY", "ASSET-1", "F1", null, null, null,
                java.math.BigDecimal.TEN, null, "UNIT-1", null, null,
                TASK_UPDATED_AT, TASK_UPDATED_AT.plusSeconds(3600), null,
                PlanTargetStatus.DRAFT, TASK_UPDATED_AT.minusSeconds(3500), TASK_UPDATED_AT.minusSeconds(3400));
        when(targets.findByRevisionIdForUpdate("REV-1")).thenReturn(List.of(target));
        when(targets.save(any())).thenAnswer(invocation -> invocation.getArgument(0));
        when(plans.save(any())).thenAnswer(invocation -> invocation.getArgument(0));
        return new PlanningApprovalApplicationService(revisions, plans, targets, access, workflow,
                mock(PlanningQueryUseCase.class));
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

    private static final class FakePlanningWorkflow implements PlanningWorkflowContract {
        private final String targetModule;
        private final String targetId;
        private final Function<TransitionCommand, TransitionResult> transition;

        private FakePlanningWorkflow(
                String targetModule,
                String targetId,
                Function<TransitionCommand, TransitionResult> transition
        ) {
            this.targetModule = targetModule;
            this.targetId = targetId;
            this.transition = transition;
        }

        @Override
        public InstanceView instance(String id) {
            return new InstanceView("WF-1", targetModule, targetId, "IN_PROGRESS");
        }

        @Override
        public TaskView currentTask(String instanceId) {
            return new TaskView("TASK-1", TASK_UPDATED_AT);
        }

        @Override
        public List<ActionView> availableActions(
                String taskId,
                String actorReference,
                Set<String> effectivePermissions
        ) {
            return List.of(new ActionView(
                    "TRANSITION-1",
                    "APPROVE",
                    false,
                    false,
                    "planning:revisions:approve",
                    true
            ));
        }

        @Override
        public TransitionResult execute(TransitionCommand command) {
            return transition.apply(command);
        }
    }
}
