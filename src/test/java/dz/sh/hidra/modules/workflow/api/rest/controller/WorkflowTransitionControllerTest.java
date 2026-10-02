/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : WorkflowTransitionControllerTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-11
 *
 * @Type        : Test
 * @Layer       : API
 * @Module      : workflow
 * @Package     : dz.sh.hidra.modules.workflow.api.rest.controller
 *
 * @Description : Verifies server-derived actor and permission mapping for workflow transition execution.
 *
 */
package dz.sh.hidra.modules.workflow.api.rest.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import dz.sh.hidra.kernel.domain.value.ActorId;
import dz.sh.hidra.modules.workflow.api.rest.request.ExecuteWorkflowTransitionRequest;
import dz.sh.hidra.modules.workflow.application.command.ExecuteWorkflowTransitionCommand;
import dz.sh.hidra.modules.workflow.application.dto.WorkflowTransitionExecutionDto;
import dz.sh.hidra.modules.workflow.application.port.in.ExecuteWorkflowTransitionUseCase;
import dz.sh.hidra.platform.security.AuthenticatedPrincipal;
import dz.sh.hidra.platform.security.CurrentSecurityContext;
import dz.sh.hidra.platform.security.HidraEffectivePermissionResolver;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

class WorkflowTransitionControllerTest {

    @Test
    void derivesActorAndPermissionsFromAuthenticatedSecurityContext() {
        ExecuteWorkflowTransitionUseCase useCase = mock(ExecuteWorkflowTransitionUseCase.class);
        CurrentSecurityContext securityContext = mock(CurrentSecurityContext.class);
        HidraEffectivePermissionResolver permissionResolver = new HidraEffectivePermissionResolver(List.of());
        WorkflowTransitionController controller = new WorkflowTransitionController(useCase, securityContext, permissionResolver);

        AuthenticatedPrincipal principal = new AuthenticatedPrincipal(ActorId.of("actor-42"), "alice", true);
        when(securityContext.currentPrincipal()).thenReturn(Optional.of(principal));

        var authentication = new UsernamePasswordAuthenticationToken(
                "alice",
                "n/a",
                List.of(new SimpleGrantedAuthority("PERMISSION_workflow:approve:execute"))
        );
        Instant version = Instant.parse("2026-09-11T12:00:00Z");
        Instant executed = Instant.parse("2026-09-11T12:01:00Z");
        when(useCase.execute(any())).thenReturn(new WorkflowTransitionExecutionDto(
                "action-1", "task-1", "APPROVED", "instance-1", "IN_PROGRESS",
                "transition-1", "APPROVE", "step-2", "task-2", executed
        ));

        var response = controller.execute(
                "task-1",
                "transition-1",
                new ExecuteWorkflowTransitionRequest(version, null, "approved", null, "corr-1"),
                authentication
        );

        assertEquals("action-1", response.actionId());
        assertEquals("APPROVE", response.decision());
        assertEquals("task-2", response.nextTaskId());

        ArgumentCaptor<ExecuteWorkflowTransitionCommand> captor = ArgumentCaptor.forClass(ExecuteWorkflowTransitionCommand.class);
        verify(useCase).execute(captor.capture());
        ExecuteWorkflowTransitionCommand command = captor.getValue();
        assertEquals("task-1", command.taskId());
        assertEquals("transition-1", command.transitionId());
        assertEquals(version, command.expectedTaskUpdatedAt());
        assertEquals("actor-42", command.actorId());
        assertEquals("alice", command.actorUsername());
        assertEquals("alice", command.actorDisplayName());
        assertEquals(Set.of("workflow:approve:execute"), command.effectivePermissions());
    }
}
