/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : OrganizationResponsibilityControllerTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-29
 *
 * @Type        : Class
 * @Layer       : Test
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization.api.rest.controller
 *
 * @Description : Verifies ORG-030 server-derived security context and owner-resolved REST responses.
 *
 */
package dz.sh.hidra.modules.organization.api.rest.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import dz.sh.hidra.kernel.domain.value.ActorId;
import dz.sh.hidra.modules.organization.api.rest.request.AssignResponsibilityRequest;
import dz.sh.hidra.modules.organization.api.rest.request.RegisterOperationalScopeRequest;
import dz.sh.hidra.modules.organization.application.command.AssignResponsibilityCommand;
import dz.sh.hidra.modules.organization.application.port.in.AssignResponsibilityUseCase;
import dz.sh.hidra.modules.organization.application.port.in.ListResponsibilitiesUseCase;
import dz.sh.hidra.modules.organization.application.port.in.OperationalScopeQueryUseCase;
import dz.sh.hidra.modules.organization.application.port.in.ReconcileResponsibilitiesUseCase;
import dz.sh.hidra.modules.organization.application.port.in.RegisterOperationalScopeUseCase;
import dz.sh.hidra.modules.organization.application.port.in.RevokeResponsibilityUseCase;
import dz.sh.hidra.modules.organization.domain.model.ResponsibilityAssignment;
import dz.sh.hidra.modules.organization.domain.value.AssignmentStatus;
import dz.sh.hidra.modules.organization.domain.value.OperationalScopeType;
import dz.sh.hidra.modules.organization.domain.value.ResponsibilityAssigneeType;
import dz.sh.hidra.modules.organization.domain.value.ResponsibilityType;
import dz.sh.hidra.platform.observability.LoggingContext;
import dz.sh.hidra.platform.security.AuthenticatedPrincipal;
import dz.sh.hidra.platform.security.CurrentSecurityContext;
import dz.sh.hidra.platform.security.HidraEffectivePermissionResolver;
import java.lang.reflect.RecordComponent;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;

class OrganizationResponsibilityControllerTest {

    private final RegisterOperationalScopeUseCase registerScope = mock(RegisterOperationalScopeUseCase.class);
    private final OperationalScopeQueryUseCase scopeQuery = mock(OperationalScopeQueryUseCase.class);
    private final AssignResponsibilityUseCase assign = mock(AssignResponsibilityUseCase.class);
    private final RevokeResponsibilityUseCase revoke = mock(RevokeResponsibilityUseCase.class);
    private final ListResponsibilitiesUseCase list = mock(ListResponsibilitiesUseCase.class);
    private final ReconcileResponsibilitiesUseCase reconcile = mock(ReconcileResponsibilitiesUseCase.class);
    private final CurrentSecurityContext securityContext = mock(CurrentSecurityContext.class);
    private final HidraEffectivePermissionResolver permissionResolver = mock(HidraEffectivePermissionResolver.class);
    private final Authentication authentication = mock(Authentication.class);

    @AfterEach
    void clearLoggingContext() {
        LoggingContext.clearAll();
    }

    @Test
    void assignmentBuildsSecurityContextOnlyFromAuthenticatedServerState() {
        authenticated();
        when(permissionResolver.resolve(authentication))
                .thenReturn(Set.of(OrganizationResponsibilityController.ASSIGN_PERMISSION));
        when(assign.assignResponsibility(any())).thenReturn("responsibility-1");
        LoggingContext.putRequestId("request-1");
        LoggingContext.putCorrelationId("correlation-1");

        OrganizationResponsibilityController controller = controller();
        controller.assign(
                new AssignResponsibilityRequest(
                        ResponsibilityType.RESPONSIBLE,
                        ResponsibilityAssigneeType.ORGANIZATION_UNIT,
                        "unit-1",
                        42L,
                        "primary pipeline responsibility",
                        Instant.parse("2026-09-29T08:00:00Z"),
                        null,
                        "workflow-1",
                        "operation-1"
                ),
                authentication
        );

        ArgumentCaptor<AssignResponsibilityCommand> captor =
                ArgumentCaptor.forClass(AssignResponsibilityCommand.class);
        verify(assign).assignResponsibility(captor.capture());

        AssignResponsibilityCommand command = captor.getValue();
        assertThat(command.context().actorId()).isEqualTo("actor-1");
        assertThat(command.context().actorUsername()).isEqualTo("operator");
        assertThat(command.context().effectivePermissions())
                .containsExactly(OrganizationResponsibilityController.ASSIGN_PERMISSION);
        assertThat(command.context().requestId()).isEqualTo("request-1");
        assertThat(command.context().correlationId()).isEqualTo("correlation-1");
        assertThat(command.context().workflowInstanceId()).isEqualTo("workflow-1");
        assertThat(command.context().operationReference()).isEqualTo("operation-1");
    }

    @Test
    void apiRequestsCannotCarryActorPermissionOrCorrelationSecurityFields() {
        assertThat(componentNames(AssignResponsibilityRequest.class))
                .doesNotContain(
                        "actorId",
                        "actorUsername",
                        "actorDisplayName",
                        "effectivePermissions",
                        "requestId",
                        "correlationId"
                );
        assertThat(componentNames(RegisterOperationalScopeRequest.class))
                .containsExactly("type", "targetId");
    }

    @Test
    void scopeRegistrationFailsBeforeUseCaseWhenPermissionIsMissing() {
        authenticated();
        when(permissionResolver.resolve(authentication)).thenReturn(Set.of());

        assertThatThrownBy(() -> controller().registerScope(
                new RegisterOperationalScopeRequest(OperationalScopeType.PIPELINE, "pipeline-1"),
                authentication
        )).isInstanceOf(AccessDeniedException.class)
                .hasMessageContaining(OrganizationResponsibilityController.REGISTER_SCOPE_PERMISSION);

        verifyNoInteractions(registerScope);
    }

    @Test
    void responsibilityReadUsesCurrentOwnerResolvedScopeDisplay() {
        authenticated();
        Instant now = Instant.parse("2026-09-29T09:00:00Z");
        when(list.listByScopeId(42L)).thenReturn(List.of(
                new ResponsibilityAssignment(
                        "responsibility-1",
                        ResponsibilityType.RESPONSIBLE,
                        ResponsibilityAssigneeType.ORGANIZATION_UNIT,
                        "unit-1",
                        42L,
                        "canonical",
                        now,
                        null,
                        AssignmentStatus.ACTIVE,
                        now,
                        now
                )
        ));
        when(scopeQuery.scope(42L)).thenReturn(
                new OperationalScopeQueryUseCase.ScopeView(
                        42L,
                        OperationalScopeType.PIPELINE,
                        "pipeline-1",
                        "PL-001",
                        "Current Pipeline Name",
                        true
                )
        );

        var response = controller().responsibilities(42L, null, null);

        assertThat(response).hasSize(1);
        assertThat(response.getFirst().scope().code()).isEqualTo("PL-001");
        assertThat(response.getFirst().scope().name()).isEqualTo("Current Pipeline Name");
        assertThat(response.getFirst().scope().targetId()).isEqualTo("pipeline-1");
    }

    private OrganizationResponsibilityController controller() {
        return new OrganizationResponsibilityController(
                registerScope,
                scopeQuery,
                assign,
                revoke,
                list,
                reconcile,
                securityContext,
                permissionResolver
        );
    }

    private void authenticated() {
        when(securityContext.currentPrincipal()).thenReturn(Optional.of(
                new AuthenticatedPrincipal(ActorId.of("actor-1"), "operator", true)
        ));
    }

    private static List<String> componentNames(Class<?> recordType) {
        return Arrays.stream(recordType.getRecordComponents())
                .map(RecordComponent::getName)
                .toList();
    }
}
