/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : WorkflowRestApiTestFixtures
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Test
 * @Layer       : Test
 * @Module      : workflow
 * @Package     : dz.sh.hidra.modules.workflow.api.rest
 *
 * @Description : Shared workflow REST mapper and controller test fixtures.
 *
 */
package dz.sh.hidra.modules.workflow.api.rest;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.time.Instant;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import dz.sh.hidra.kernel.application.pagination.PageResult;
import dz.sh.hidra.modules.workflow.api.rest.request.ApproveWorkflowTaskRequest;
import dz.sh.hidra.modules.workflow.api.rest.request.AssignWorkflowTaskRequest;
import dz.sh.hidra.modules.workflow.api.rest.request.CancelWorkflowInstanceRequest;
import dz.sh.hidra.modules.workflow.api.rest.request.ClaimWorkflowTaskRequest;
import dz.sh.hidra.modules.workflow.api.rest.request.CommentWorkflowTaskRequest;
import dz.sh.hidra.modules.workflow.api.rest.request.CreateWorkflowDefinitionRequest;
import dz.sh.hidra.modules.workflow.api.rest.request.CreateWorkflowStepRequest;
import dz.sh.hidra.modules.workflow.api.rest.request.CreateWorkflowTransitionRequest;
import dz.sh.hidra.modules.workflow.api.rest.request.DelegateWorkflowTaskRequest;
import dz.sh.hidra.modules.workflow.api.rest.request.EscalateWorkflowTaskRequest;
import dz.sh.hidra.modules.workflow.api.rest.request.RejectWorkflowTaskRequest;
import dz.sh.hidra.modules.workflow.api.rest.request.RequestWorkflowCorrectionRequest;
import dz.sh.hidra.modules.workflow.api.rest.request.StartWorkflowInstanceRequest;
import dz.sh.hidra.modules.workflow.api.rest.request.WorkflowActorReferenceRequest;
import dz.sh.hidra.modules.workflow.api.rest.request.WorkflowLocalizedNameRequest;
import dz.sh.hidra.modules.workflow.api.rest.request.WorkflowOrganizationReferenceRequest;
import dz.sh.hidra.modules.workflow.api.rest.request.WorkflowPriorityReferenceRequest;
import dz.sh.hidra.modules.workflow.api.rest.request.WorkflowReasonReferenceRequest;
import dz.sh.hidra.modules.workflow.api.rest.request.WorkflowTargetReferenceRequest;
import dz.sh.hidra.modules.workflow.api.rest.request.WorkflowTargetTypeReferenceRequest;
import dz.sh.hidra.modules.workflow.api.rest.request.WorkflowTypeReferenceRequest;
import dz.sh.hidra.modules.workflow.application.dto.WorkflowActionDto;
import dz.sh.hidra.modules.workflow.application.dto.WorkflowActorReferenceDto;
import dz.sh.hidra.modules.workflow.application.dto.WorkflowCatalogDto;
import dz.sh.hidra.modules.workflow.application.dto.WorkflowCatalogTranslationDto;
import dz.sh.hidra.modules.workflow.application.dto.WorkflowCommentDto;
import dz.sh.hidra.modules.workflow.application.dto.WorkflowDefinitionDto;
import dz.sh.hidra.modules.workflow.application.dto.WorkflowInstanceDto;
import dz.sh.hidra.modules.workflow.application.dto.WorkflowLocalizedNameDto;
import dz.sh.hidra.modules.workflow.application.dto.WorkflowPriorityReferenceDto;
import dz.sh.hidra.modules.workflow.application.dto.WorkflowReasonReferenceDto;
import dz.sh.hidra.modules.workflow.application.dto.WorkflowStepDto;
import dz.sh.hidra.modules.workflow.application.dto.WorkflowTargetReferenceDto;
import dz.sh.hidra.modules.workflow.application.dto.WorkflowTaskDto;
import dz.sh.hidra.modules.workflow.application.dto.WorkflowTimelineDto;
import dz.sh.hidra.modules.workflow.application.dto.WorkflowTransitionDto;
import dz.sh.hidra.modules.workflow.application.dto.WorkflowTypeReferenceDto;

/**
 * Shared workflow REST mapper and controller test fixtures.
 */
final class WorkflowRestApiTestFixtures {

    static final Instant NOW = Instant.parse("2026-06-08T10:15:30Z");

    private WorkflowRestApiTestFixtures() {
        // Test utility class.
    }

    static WorkflowLocalizedNameRequest localizedNameRequest() {
        return new WorkflowLocalizedNameRequest("تحقق القياسات", "Validation télémétrie", "Telemetry validation");
    }

    static WorkflowTypeReferenceRequest workflowTypeRequest() {
        return new WorkflowTypeReferenceRequest("workflow-type-telemetry-validation", "TELEMETRY_VALIDATION");
    }

    static WorkflowTargetTypeReferenceRequest targetTypeRequest() {
        return new WorkflowTargetTypeReferenceRequest("workflow-target-type-telemetry-reading", "TELEMETRY_READING");
    }

    static WorkflowTargetReferenceRequest targetRequest() {
        return new WorkflowTargetReferenceRequest(
                "telemetry",
                targetTypeRequest(),
                "reading-001",
                "READING-001",
                "Telemetry reading reading-001");
    }

    static WorkflowActorReferenceRequest actorRequest() {
        return new WorkflowActorReferenceRequest("actor-001", "a.medjerab", "Abir MEDJERAB", "SUPERVISOR");
    }

    static WorkflowOrganizationReferenceRequest organizationRequest() {
        return new WorkflowOrganizationReferenceRequest("org-trc", "TRC", "VALIDATOR");
    }

    static WorkflowReasonReferenceRequest reasonRequest() {
        return new WorkflowReasonReferenceRequest("workflow-reason-out-of-range", "OUT_OF_RANGE");
    }

    static WorkflowPriorityReferenceRequest priorityRequest() {
        return new WorkflowPriorityReferenceRequest("workflow-priority-normal", "NORMAL");
    }

    static CreateWorkflowDefinitionRequest createDefinitionRequest() {
        return new CreateWorkflowDefinitionRequest(
                "TELEMETRY_READING_VALIDATION",
                localizedNameRequest(),
                workflowTypeRequest());
    }

    static CreateWorkflowStepRequest createStepRequest() {
        return new CreateWorkflowStepRequest(
                "SUPERVISOR_REVIEW",
                localizedNameRequest(),
                0,
                true);
    }

    static CreateWorkflowTransitionRequest createTransitionRequest() {
        return new CreateWorkflowTransitionRequest(
                "step-review",
                "step-complete",
                "APPROVE",
                false,
                false);
    }

    static StartWorkflowInstanceRequest startInstanceRequest() {
        return new StartWorkflowInstanceRequest(
                "definition-001",
                targetRequest(),
                actorRequest(),
                "corr-001");
    }

    static CancelWorkflowInstanceRequest cancelInstanceRequest() {
        return new CancelWorkflowInstanceRequest(
                actorRequest(),
                reasonRequest(),
                "Cancelled by supervisor",
                "corr-cancel");
    }

    static AssignWorkflowTaskRequest assignTaskRequest() {
        return new AssignWorkflowTaskRequest(
                actorRequest(),
                null,
                priorityRequest(),
                NOW.plusSeconds(3600),
                "corr-assign");
    }

    static ClaimWorkflowTaskRequest claimTaskRequest() {
        return new ClaimWorkflowTaskRequest(actorRequest(), "corr-claim");
    }

    static ApproveWorkflowTaskRequest approveTaskRequest() {
        return new ApproveWorkflowTaskRequest(
                actorRequest(),
                organizationRequest(),
                null,
                "Accepted",
                "All values are consistent",
                "corr-approve");
    }

    static RejectWorkflowTaskRequest rejectTaskRequest() {
        return new RejectWorkflowTaskRequest(
                actorRequest(),
                organizationRequest(),
                reasonRequest(),
                "Rejected",
                "Out of expected range",
                "corr-reject");
    }

    static RequestWorkflowCorrectionRequest correctionRequest() {
        return new RequestWorkflowCorrectionRequest(
                actorRequest(),
                organizationRequest(),
                reasonRequest(),
                "Please verify the source value",
                "corr-correction");
    }

    static DelegateWorkflowTaskRequest delegateTaskRequest() {
        return new DelegateWorkflowTaskRequest(
                actorRequest(),
                actorRequest(),
                null,
                reasonRequest(),
                "Delegated for workload balance",
                "corr-delegate");
    }

    static EscalateWorkflowTaskRequest escalateTaskRequest() {
        return new EscalateWorkflowTaskRequest(
                actorRequest(),
                organizationRequest(),
                reasonRequest(),
                "Escalated due to delay",
                "corr-escalate");
    }

    static CommentWorkflowTaskRequest commentRequest() {
        return new CommentWorkflowTaskRequest(
                actorRequest(),
                organizationRequest(),
                "Reading verified with field team",
                "corr-comment");
    }

    static WorkflowLocalizedNameDto localizedNameDto() {
        return new WorkflowLocalizedNameDto("تحقق القياسات", "Validation télémétrie", "Telemetry validation");
    }

    static WorkflowTypeReferenceDto workflowTypeDto() {
        return new WorkflowTypeReferenceDto(
                "workflow-type-telemetry-validation",
                "TELEMETRY_VALIDATION",
                "Validation télémétrie",
                "fr");
    }

    static WorkflowReasonReferenceDto reasonDto() {
        return new WorkflowReasonReferenceDto(
                "workflow-reason-out-of-range",
                "OUT_OF_RANGE",
                "Hors plage",
                "fr");
    }

    static WorkflowPriorityReferenceDto priorityDto() {
        return new WorkflowPriorityReferenceDto(
                "workflow-priority-normal",
                "NORMAL",
                "Normale",
                "fr");
    }

    static WorkflowActorReferenceDto actorDto() {
        return new WorkflowActorReferenceDto("actor-001", "a.medjerab", "Abir MEDJERAB", "SUPERVISOR");
    }

    static WorkflowTargetReferenceDto targetDto() {
        return new WorkflowTargetReferenceDto(
                "telemetry",
                workflowTypeDto(),
                "reading-001",
                "READING-001",
                "Telemetry reading reading-001");
    }

    static WorkflowStepDto stepDto() {
        return new WorkflowStepDto(
                "step-review",
                "definition-001",
                "SUPERVISOR_REVIEW",
                localizedNameDto(),
                0,
                true,
                NOW,
                NOW);
    }

    static WorkflowTransitionDto transitionDto() {
        return new WorkflowTransitionDto(
                "transition-approve",
                "definition-001",
                "step-review",
                "step-complete",
                "APPROVE",
                false,
                false,
                NOW,
                NOW);
    }

    static WorkflowDefinitionDto definitionDto() {
        return new WorkflowDefinitionDto(
                "definition-001",
                "TELEMETRY_READING_VALIDATION",
                localizedNameDto(),
                workflowTypeDto(),
                "ACTIVE",
                1,
                List.of(stepDto()),
                List.of(transitionDto()),
                NOW,
                NOW);
    }

    static WorkflowTaskDto taskDto() {
        return new WorkflowTaskDto(
                "task-001",
                "instance-001",
                "step-review",
                "OPEN",
                actorDto(),
                "org-trc",
                "TRC",
                priorityDto(),
                NOW.plusSeconds(3600),
                null,
                null,
                null,
                null,
                NOW,
                NOW);
    }

    static WorkflowActionDto actionDto() {
        return new WorkflowActionDto(
                "action-001",
                "instance-001",
                "task-001",
                "APPROVE",
                "APPROVE",
                reasonDto(),
                "Accepted",
                "All values are consistent",
                actorDto(),
                "org-trc",
                "TRC",
                "corr-approve",
                NOW);
    }

    static WorkflowInstanceDto instanceDto() {
        return new WorkflowInstanceDto(
                "instance-001",
                "definition-001",
                1,
                targetDto(),
                "STARTED",
                "step-review",
                actorDto(),
                NOW,
                null,
                null,
                "corr-001",
                List.of(taskDto()),
                List.of(actionDto()),
                NOW,
                NOW);
    }

    static WorkflowCommentDto commentDto() {
        return new WorkflowCommentDto(
                "comment-001",
                "instance-001",
                "task-001",
                actorDto(),
                "Reading verified with field team",
                NOW);
    }

    static WorkflowTimelineDto timelineDto() {
        return new WorkflowTimelineDto(
                "instance-001",
                targetDto(),
                List.of(actionDto()),
                List.of(commentDto()),
                List.of(taskDto()));
    }

    static WorkflowCatalogDto catalogDto() {
        return new WorkflowCatalogDto(
                "workflow-priority-normal",
                "PRIORITY",
                "NORMAL",
                true,
                20,
                true,
                List.of(new WorkflowCatalogTranslationDto(
                        "workflow-priority-normal-fr",
                        "workflow-priority-normal",
                        "fr",
                        "Normale",
                        "Priorité normale",
                        NOW,
                        NOW)),
                NOW,
                NOW);
    }

    static PageResult<WorkflowDefinitionDto> definitionPage() {
        return new PageResult<>(List.of(definitionDto()), 0, 20, 1, 1);
    }

    static PageResult<WorkflowInstanceDto> instancePage() {
        return new PageResult<>(List.of(instanceDto()), 0, 20, 1, 1);
    }

    static PageResult<WorkflowTaskDto> taskPage() {
        return new PageResult<>(List.of(taskDto()), 0, 20, 1, 1);
    }

    static PageResult<WorkflowCatalogDto> catalogPage() {
        return new PageResult<>(List.of(catalogDto()), 0, 20, 1, 1);
    }

    static <T> T fixedUseCase(Class<T> type, Object result) {
        return useCase(type, result, null);
    }

    static <T> T recordingUseCase(Class<T> type, Object result, AtomicReference<Object> lastArgument) {
        return useCase(type, result, lastArgument);
    }

    private static <T> T useCase(Class<T> type, Object result, AtomicReference<Object> lastArgument) {
        InvocationHandler handler = (Object proxy, Method method, Object[] arguments) -> {
            if ("toString".equals(method.getName())) {
                return type.getSimpleName() + "TestProxy";
            }
            if ("hashCode".equals(method.getName())) {
                return System.identityHashCode(proxy);
            }
            if ("equals".equals(method.getName())) {
                return proxy == arguments[0];
            }
            if (lastArgument != null && arguments != null && arguments.length == 1) {
                lastArgument.set(arguments[0]);
            }
            return result;
        };

        return type.cast(Proxy.newProxyInstance(type.getClassLoader(), new Class<?>[] {type}, handler));
    }
}
