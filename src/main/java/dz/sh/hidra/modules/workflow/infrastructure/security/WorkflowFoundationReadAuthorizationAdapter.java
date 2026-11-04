/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : WorkflowFoundationReadAuthorizationAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-04
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : workflow
 * @Package     : dz.sh.hidra.modules.workflow.infrastructure.security
 *
 * @Description : Resolves a fresh authenticated actor and owner-backed Workflow read admission.
 *
 */
package dz.sh.hidra.modules.workflow.infrastructure.security;

import dz.sh.hidra.modules.identity.application.contract.workflow.WorkflowActorContract;
import dz.sh.hidra.modules.organization.application.contract.workflow.WorkflowOrganizationContract;
import dz.sh.hidra.modules.workflow.application.port.out.WorkflowFoundationReadAuthorizationPort;
import dz.sh.hidra.platform.security.CurrentSecurityContext;
import java.time.Instant;
import java.util.List;
import java.util.Objects;
import org.springframework.stereotype.Component;

@Component
public final class WorkflowFoundationReadAuthorizationAdapter implements WorkflowFoundationReadAuthorizationPort {
    private static final String SUPERVISOR_PERMISSION = "workflow:instances:supervise";
    private final WorkflowActorContract actors;
    private final WorkflowOrganizationContract organizations;
    private final CurrentSecurityContext security;

    public WorkflowFoundationReadAuthorizationAdapter(WorkflowActorContract actors,
            WorkflowOrganizationContract organizations, CurrentSecurityContext security) {
        this.actors = Objects.requireNonNull(actors);
        this.organizations = Objects.requireNonNull(organizations);
        this.security = Objects.requireNonNull(security);
    }

    @Override
    public boolean permitted(Scope scope, List<Assignment> assignments) {
        if (scope == null || assignments == null || scope.instanceId() == null) return false;
        var principal = security.currentPrincipal().filter(p -> p.authenticated()).orElse(null);
        if (principal == null) return false;
        String actorId = principal.actorId().value();
        var actor = actors.eligibleActor(actorId, Instant.now())
                .filter(a -> actorId.equals(a.id())).orElse(null);
        if (actor == null) return false;
        if (actorId.equals(scope.startedByActorId())) return true;
        for (Assignment task : assignments) {
            if (task == null) continue;
            if (actorId.equals(task.assignedActorId()) || actorId.equals(task.claimedByActorId())
                   ) return true;
            if (task.assignedOrganizationUnitId() != null && actor.employeeId() != null
                    && organizations.availableUnit(task.assignedOrganizationUnitId(), Instant.now()).isPresent()
                    && organizations.eligibleMember(actor.employeeId(), task.assignedOrganizationUnitId(), Instant.now()))
                return true;
        }
        // A generic Workflow permission cannot bypass a foreign owner's current resource decision.
        return scope.ownerReadAdmitted()
                && actors.permitted(actorId, SUPERVISOR_PERMISSION, scope.instanceId());
    }
}
