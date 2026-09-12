/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : PlanningApprovalController
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-12
 *
 * @Type        : Class
 * @Layer       : API
 * @Module      : planning
 * @Package     : dz.sh.hidra.modules.planning.api.rest.controller
 *
 * @Description : Exposes revision-scoped workflow approval context and execution without client-side task discovery.
 *
 */
package dz.sh.hidra.modules.planning.api.rest.controller;

import dz.sh.hidra.modules.planning.api.rest.request.ExecutePlanningApprovalActionRequest;
import dz.sh.hidra.modules.planning.application.port.in.PlanningApprovalUseCase;
import dz.sh.hidra.platform.security.AuthenticatedPrincipal;
import dz.sh.hidra.platform.security.CurrentSecurityContext;
import dz.sh.hidra.platform.security.HidraEffectivePermissionResolver;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.Objects;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/planning/revisions")
@Tag(name = "Planning Approval", description = "Authoritative plan-revision workflow approval integration.")
public final class PlanningApprovalController {

    private final PlanningApprovalUseCase useCase;
    private final CurrentSecurityContext securityContext;
    private final HidraEffectivePermissionResolver permissionResolver;

    public PlanningApprovalController(
            PlanningApprovalUseCase useCase,
            CurrentSecurityContext securityContext,
            HidraEffectivePermissionResolver permissionResolver
    ) {
        this.useCase = Objects.requireNonNull(useCase, "PlanningApprovalUseCase must not be null.");
        this.securityContext = Objects.requireNonNull(securityContext, "CurrentSecurityContext must not be null.");
        this.permissionResolver = Objects.requireNonNull(permissionResolver, "HidraEffectivePermissionResolver must not be null.");
    }

    @GetMapping("/{revisionId}/approval")
    @Operation(summary = "Get revision-scoped workflow approval context")
    public PlanningApprovalUseCase.ApprovalView approval(
            @PathVariable String revisionId,
            Authentication authentication
    ) {
        AuthenticatedPrincipal principal = principal();
        return useCase.approval(revisionId, principal.actorId().value(), permissionResolver.resolve(authentication));
    }

    @PostMapping("/{revisionId}/approval/actions/{transitionId}/execute")
    @Operation(summary = "Execute one backend-defined revision approval action")
    public PlanningApprovalUseCase.ExecutionView execute(
            @PathVariable String revisionId,
            @PathVariable String transitionId,
            @Valid @RequestBody ExecutePlanningApprovalActionRequest request,
            Authentication authentication
    ) {
        AuthenticatedPrincipal principal = principal();
        return useCase.execute(
                revisionId,
                transitionId,
                new PlanningApprovalUseCase.ExecuteCommand(
                        request.expectedTaskUpdatedAt(), request.reasonId(), request.decisionNote(), request.commentText(),
                        request.correlationId(), principal.actorId().value(), principal.principalName(),
                        principal.principalName(), permissionResolver.resolve(authentication)
                )
        );
    }

    private AuthenticatedPrincipal principal() {
        return securityContext.currentPrincipal()
                .filter(AuthenticatedPrincipal::authenticated)
                .orElseThrow(() -> new AuthenticationCredentialsNotFoundException("Authenticated planning approval actor is required."));
    }
}
