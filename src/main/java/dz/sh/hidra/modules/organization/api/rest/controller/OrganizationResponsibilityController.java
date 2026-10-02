/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : OrganizationResponsibilityController
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-29
 *
 * @Type        : Class
 * @Layer       : API
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization.api.rest.controller
 *
 * @Description : Canonical operational-scope and responsibility API with server-derived security context.
 *
 */
package dz.sh.hidra.modules.organization.api.rest.controller;

import dz.sh.hidra.modules.organization.api.rest.request.AssignResponsibilityRequest;
import dz.sh.hidra.modules.organization.api.rest.request.RegisterOperationalScopeRequest;
import dz.sh.hidra.modules.organization.api.rest.request.RevokeResponsibilityRequest;
import dz.sh.hidra.modules.organization.api.rest.response.OperationalScopeResponse;
import dz.sh.hidra.modules.organization.api.rest.response.ResponsibilityMutationResponse;
import dz.sh.hidra.modules.organization.api.rest.response.ResponsibilityReconciliationResponse;
import dz.sh.hidra.modules.organization.api.rest.response.ResponsibilityResponse;
import dz.sh.hidra.modules.organization.application.command.AssignResponsibilityCommand;
import dz.sh.hidra.modules.organization.application.command.RegisterOperationalScopeCommand;
import dz.sh.hidra.modules.organization.application.command.ResponsibilityOperationContext;
import dz.sh.hidra.modules.organization.application.command.RevokeResponsibilityCommand;
import dz.sh.hidra.modules.organization.application.dto.ResponsibilityReconciliationResult;
import dz.sh.hidra.modules.organization.application.port.in.AssignResponsibilityUseCase;
import dz.sh.hidra.modules.organization.application.port.in.ListResponsibilitiesUseCase;
import dz.sh.hidra.modules.organization.application.port.in.OperationalScopeQueryUseCase;
import dz.sh.hidra.modules.organization.application.port.in.ReconcileResponsibilitiesUseCase;
import dz.sh.hidra.modules.organization.application.port.in.RegisterOperationalScopeUseCase;
import dz.sh.hidra.modules.organization.application.port.in.RevokeResponsibilityUseCase;
import dz.sh.hidra.modules.organization.domain.model.ResponsibilityAssignment;
import dz.sh.hidra.modules.organization.domain.value.OperationalScopeReference;
import dz.sh.hidra.modules.organization.domain.value.ResponsibilityAssigneeType;
import dz.sh.hidra.platform.observability.LoggingContext;
import dz.sh.hidra.platform.security.AuthenticatedPrincipal;
import dz.sh.hidra.platform.security.CurrentSecurityContext;
import dz.sh.hidra.platform.security.HidraEffectivePermissionResolver;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/organization")
@Tag(name = "Organization Responsibilities", description = "Canonical operational scopes and effective-dated organization responsibilities.")
public class OrganizationResponsibilityController {

    public static final String REGISTER_SCOPE_PERMISSION = "organization:operational-scope:register";
    public static final String ASSIGN_PERMISSION = "organization:responsibility:assign";
    public static final String REVOKE_PERMISSION = "organization:responsibility:revoke";
    public static final String RECONCILE_PERMISSION = "organization:responsibility:reconcile";

    private final RegisterOperationalScopeUseCase registerOperationalScope;
    private final OperationalScopeQueryUseCase scopeQuery;
    private final AssignResponsibilityUseCase assignResponsibility;
    private final RevokeResponsibilityUseCase revokeResponsibility;
    private final ListResponsibilitiesUseCase listResponsibilities;
    private final ReconcileResponsibilitiesUseCase reconcileResponsibilities;
    private final CurrentSecurityContext securityContext;
    private final HidraEffectivePermissionResolver permissionResolver;

    public OrganizationResponsibilityController(
            RegisterOperationalScopeUseCase registerOperationalScope,
            OperationalScopeQueryUseCase scopeQuery,
            AssignResponsibilityUseCase assignResponsibility,
            RevokeResponsibilityUseCase revokeResponsibility,
            ListResponsibilitiesUseCase listResponsibilities,
            ReconcileResponsibilitiesUseCase reconcileResponsibilities,
            CurrentSecurityContext securityContext,
            HidraEffectivePermissionResolver permissionResolver
    ) {
        this.registerOperationalScope = Objects.requireNonNull(registerOperationalScope);
        this.scopeQuery = Objects.requireNonNull(scopeQuery);
        this.assignResponsibility = Objects.requireNonNull(assignResponsibility);
        this.revokeResponsibility = Objects.requireNonNull(revokeResponsibility);
        this.listResponsibilities = Objects.requireNonNull(listResponsibilities);
        this.reconcileResponsibilities = Objects.requireNonNull(reconcileResponsibilities);
        this.securityContext = Objects.requireNonNull(securityContext);
        this.permissionResolver = Objects.requireNonNull(permissionResolver);
    }

    @PostMapping("/operational-scopes")
    @Operation(summary = "Register one owner-validated operational scope")
    public OperationalScopeResponse registerScope(
            @Valid @RequestBody RegisterOperationalScopeRequest request,
            Authentication authentication
    ) {
        requireAuthenticated();
        Set<String> permissions = permissions(authentication);
        requirePermission(permissions, REGISTER_SCOPE_PERMISSION);

        var registered = registerOperationalScope.registerOperationalScope(
                new RegisterOperationalScopeCommand(
                        new OperationalScopeReference(request.type(), request.targetId())
                )
        );
        return toScopeResponse(scopeQuery.scope(registered.id()));
    }

    @GetMapping("/operational-scopes/{scopeId}")
    @Operation(summary = "Read one operational scope with current owner-resolved display data")
    public OperationalScopeResponse scope(@PathVariable Long scopeId) {
        requireAuthenticated();
        return toScopeResponse(scopeQuery.scope(scopeId));
    }

    @PostMapping("/responsibilities")
    @Operation(summary = "Assign one workflow-approved responsibility")
    public ResponsibilityMutationResponse assign(
            @Valid @RequestBody AssignResponsibilityRequest request,
            Authentication authentication
    ) {
        AuthenticatedPrincipal principal = requireAuthenticated();
        Set<String> permissions = permissions(authentication);
        requirePermission(permissions, ASSIGN_PERMISSION);
        RequestContext requestContext = requestContext();

        String id = assignResponsibility.assignResponsibility(
                new AssignResponsibilityCommand(
                        request.responsibilityType(),
                        request.assigneeType(),
                        request.assigneeId(),
                        request.scopeId(),
                        request.description(),
                        request.validFrom(),
                        request.validTo(),
                        operationContext(
                                principal,
                                permissions,
                                request.workflowInstanceId(),
                                request.operationReference(),
                                requestContext
                        )
                )
        );
        return new ResponsibilityMutationResponse(id);
    }

    @PostMapping("/responsibilities/{assignmentId}/revoke")
    @Operation(summary = "Revoke one workflow-approved responsibility without deleting history")
    public ResponsibilityMutationResponse revoke(
            @PathVariable String assignmentId,
            @Valid @RequestBody RevokeResponsibilityRequest request,
            Authentication authentication
    ) {
        AuthenticatedPrincipal principal = requireAuthenticated();
        Set<String> permissions = permissions(authentication);
        requirePermission(permissions, REVOKE_PERMISSION);
        RequestContext requestContext = requestContext();

        String id = revokeResponsibility.revokeResponsibility(
                new RevokeResponsibilityCommand(
                        assignmentId,
                        request.effectiveAt(),
                        operationContext(
                                principal,
                                permissions,
                                request.workflowInstanceId(),
                                request.operationReference(),
                                requestContext
                        )
                )
        );
        return new ResponsibilityMutationResponse(id);
    }

    @GetMapping("/responsibilities")
    @Operation(summary = "List responsibility assignments with current owner-resolved scope display")
    public List<ResponsibilityResponse> responsibilities(
            @RequestParam(required = false) Long scopeId,
            @RequestParam(required = false) ResponsibilityAssigneeType assigneeType,
            @RequestParam(required = false) String assigneeId
    ) {
        requireAuthenticated();

        List<ResponsibilityAssignment> assignments;
        if (scopeId != null && assigneeType == null && (assigneeId == null || assigneeId.isBlank())) {
            assignments = listResponsibilities.listByScopeId(scopeId);
        } else if (scopeId == null && assigneeType != null && assigneeId != null && !assigneeId.isBlank()) {
            assignments = listResponsibilities.listByAssignee(assigneeType, assigneeId);
        } else {
            throw new IllegalArgumentException(
                    "Provide either scopeId or the assigneeType/assigneeId pair."
            );
        }

        return assignments.stream().map(this::toResponsibilityResponse).toList();
    }

    @PostMapping("/responsibilities/reconcile")
    @Operation(summary = "Run authorized read-only responsibility integrity reconciliation")
    public ResponsibilityReconciliationResponse reconcile(Authentication authentication) {
        AuthenticatedPrincipal principal = requireAuthenticated();
        Set<String> permissions = permissions(authentication);
        requirePermission(permissions, RECONCILE_PERMISSION);
        RequestContext requestContext = requestContext();

        ResponsibilityReconciliationResult result =
                reconcileResponsibilities.reconcileResponsibilities(
                        operationContext(
                                principal,
                                permissions,
                                null,
                                "responsibility-reconcile:" + requestContext.requestId(),
                                requestContext
                        )
                );

        return new ResponsibilityReconciliationResponse(
                result.scannedAssignments(),
                result.issues().stream()
                        .map(issue -> new ResponsibilityReconciliationResponse.Issue(
                                issue.assignmentId(), issue.code(), issue.referenceId()
                        ))
                        .toList()
        );
    }

    private ResponsibilityResponse toResponsibilityResponse(ResponsibilityAssignment assignment) {
        OperationalScopeResponse scope = toScopeResponse(scopeQuery.scope(assignment.scopeId()));
        return new ResponsibilityResponse(
                assignment.id(),
                assignment.responsibilityType(),
                assignment.assigneeType(),
                assignment.assigneeId(),
                assignment.scopeId(),
                scope,
                assignment.description(),
                assignment.validFrom(),
                assignment.validTo(),
                assignment.status(),
                assignment.createdAt(),
                assignment.updatedAt()
        );
    }

    private static OperationalScopeResponse toScopeResponse(
            OperationalScopeQueryUseCase.ScopeView scope
    ) {
        return new OperationalScopeResponse(
                scope.id(),
                scope.type(),
                scope.targetId(),
                scope.code(),
                scope.name(),
                scope.assignable()
        );
    }

    private ResponsibilityOperationContext operationContext(
            AuthenticatedPrincipal principal,
            Set<String> permissions,
            String workflowInstanceId,
            String operationReference,
            RequestContext requestContext
    ) {
        return new ResponsibilityOperationContext(
                principal.actorId().value(),
                principal.principalName(),
                principal.principalName(),
                permissions,
                workflowInstanceId,
                operationReference,
                requestContext.requestId(),
                requestContext.correlationId()
        );
    }

    private AuthenticatedPrincipal requireAuthenticated() {
        return securityContext.currentPrincipal()
                .filter(AuthenticatedPrincipal::authenticated)
                .orElseThrow(() -> new AuthenticationCredentialsNotFoundException(
                        "Authenticated organization actor is required."
                ));
    }

    private Set<String> permissions(Authentication authentication) {
        return permissionResolver.resolve(authentication);
    }

    private static void requirePermission(Set<String> permissions, String permission) {
        if (!permissions.contains(HidraEffectivePermissionResolver.ALL_PERMISSIONS)
                && !permissions.contains(permission)) {
            throw new AccessDeniedException("Missing required permission: " + permission);
        }
    }

    private static RequestContext requestContext() {
        String requestId = LoggingContext.get(LoggingContext.REQUEST_ID)
                .orElseGet(() -> "org-" + UUID.randomUUID());
        String correlationId = LoggingContext.get(LoggingContext.CORRELATION_ID)
                .orElse(requestId);
        return new RequestContext(requestId, correlationId);
    }

    private record RequestContext(String requestId, String correlationId) { }
}
