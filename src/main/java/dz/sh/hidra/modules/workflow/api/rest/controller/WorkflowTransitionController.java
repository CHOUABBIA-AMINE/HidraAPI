/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : WorkflowTransitionController
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-11
 *
 * @Type        : Class
 * @Layer       : API
 * @Module      : workflow
 * @Package     : dz.sh.hidra.modules.workflow.api.rest.controller
 *
 * @Description : Executes backend-defined workflow transitions for the authenticated actor.
 *
 */
package dz.sh.hidra.modules.workflow.api.rest.controller;

import dz.sh.hidra.modules.workflow.api.rest.request.ExecuteWorkflowTransitionRequest;
import dz.sh.hidra.modules.workflow.api.rest.response.WorkflowTransitionExecutionResponse;
import dz.sh.hidra.modules.workflow.application.command.ExecuteWorkflowTransitionCommand;
import dz.sh.hidra.modules.workflow.application.dto.WorkflowTransitionExecutionDto;
import dz.sh.hidra.modules.workflow.application.port.in.ExecuteWorkflowTransitionUseCase;
import dz.sh.hidra.platform.security.AuthenticatedPrincipal;
import dz.sh.hidra.platform.security.CurrentSecurityContext;
import dz.sh.hidra.platform.security.HidraEffectivePermissionResolver;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.Objects;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/workflow/tasks")
@Tag(name = "Workflow Transitions", description = "Authoritative execution of backend-defined task transitions.")
public final class WorkflowTransitionController {

    private final ExecuteWorkflowTransitionUseCase useCase;
    private final CurrentSecurityContext securityContext;
    private final HidraEffectivePermissionResolver permissionResolver;

    public WorkflowTransitionController(
            ExecuteWorkflowTransitionUseCase useCase,
            CurrentSecurityContext securityContext,
            HidraEffectivePermissionResolver permissionResolver
    ) {
        this.useCase = Objects.requireNonNull(useCase, "ExecuteWorkflowTransitionUseCase must not be null.");
        this.securityContext = Objects.requireNonNull(securityContext, "CurrentSecurityContext must not be null.");
        this.permissionResolver = Objects.requireNonNull(permissionResolver, "HidraEffectivePermissionResolver must not be null.");
    }

    @PostMapping("/{taskId}/transitions/{transitionId}/execute")
    @Operation(summary = "Execute one backend-defined workflow transition")
    public WorkflowTransitionExecutionResponse execute(
            @PathVariable String taskId,
            @PathVariable String transitionId,
            @Valid @RequestBody ExecuteWorkflowTransitionRequest request,
            Authentication authentication
    ) {
        AuthenticatedPrincipal principal = securityContext.currentPrincipal()
                .filter(AuthenticatedPrincipal::authenticated)
                .orElseThrow(() -> new AuthenticationCredentialsNotFoundException("Authenticated workflow actor is required."));
        WorkflowTransitionExecutionDto dto = useCase.execute(new ExecuteWorkflowTransitionCommand(
                taskId,
                transitionId,
                request.expectedTaskUpdatedAt(),
                request.reasonId(),
                request.decisionNote(),
                request.commentText(),
                request.correlationId(),
                principal.actorId().value(),
                principal.principalName(),
                principal.principalName(),
                permissionResolver.resolve(authentication)
        ));
        return new WorkflowTransitionExecutionResponse(
                dto.actionId(), dto.taskId(), dto.taskStatus(), dto.instanceId(), dto.instanceStatus(),
                dto.transitionId(), dto.decision(), dto.currentStepId(), dto.nextTaskId(), dto.executedAt()
        );
    }
}
