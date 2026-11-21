/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TopologyPressureRestrictionAuthorizationQueryService
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-21
 *
 * @Type        : Class
 * @Layer       : Application
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.application.service
 *
 * @Description : Evaluates current scoped pressure restriction authority and reviewer eligibility.
 *
 */
package dz.sh.hidra.modules.identity.application.service;

import dz.sh.hidra.modules.identity.application.contract.topology.TopologyCurrentActorContract;
import dz.sh.hidra.modules.identity.application.contract.topology.TopologyPressureRestrictionAuthorizationContract;
import dz.sh.hidra.modules.identity.application.contract.workflow.WorkflowHumanActorContract;
import dz.sh.hidra.modules.identity.application.port.in.EvaluatePermissionUseCase;
import dz.sh.hidra.modules.identity.application.port.in.IdentityAdministrationQueryUseCase;
import dz.sh.hidra.modules.identity.application.query.EvaluatePermissionQuery;
import dz.sh.hidra.modules.identity.domain.service.RegionalResourceAccessPolicy;
import dz.sh.hidra.modules.identity.domain.value.AuthorizationDecisionValue;
import dz.sh.hidra.modules.identity.domain.value.AuthorizationScope;
import dz.sh.hidra.modules.identity.domain.value.ScopeType;
import dz.sh.hidra.modules.organization.application.contract.identity.IdentityRegionalResponsibilityContract;
import dz.sh.hidra.modules.topology.application.contract.identity.IdentityRegionalTopologyContract;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class TopologyPressureRestrictionAuthorizationQueryService
        implements TopologyPressureRestrictionAuthorizationContract {
    private final TopologyCurrentActorContract actors;
    private final WorkflowHumanActorContract humans;
    private final IdentityAdministrationQueryUseCase principals;
    private final EvaluatePermissionUseCase grants;
    private final IdentityRegionalResponsibilityContract responsibilities;
    private final IdentityRegionalTopologyContract topology;
    private final RegionalResourceAccessPolicy policy = new RegionalResourceAccessPolicy();

    public TopologyPressureRestrictionAuthorizationQueryService(TopologyCurrentActorContract actors,
            WorkflowHumanActorContract humans, IdentityAdministrationQueryUseCase principals,
            EvaluatePermissionUseCase grants, IdentityRegionalResponsibilityContract responsibilities,
            IdentityRegionalTopologyContract topology) {
        this.actors = actors;
        this.humans = humans;
        this.principals = principals;
        this.grants = grants;
        this.responsibilities = responsibilities;
        this.topology = topology;
    }

    @Override
    public Optional<String> current(Target target, Operation operation) {
        return actors.currentActor().map(TopologyCurrentActorContract.Actor::userId)
                .filter(actor -> permitted(target, operation, actor));
    }

    @Override
    public boolean reviewerEligible(Target target, String actor) {
        return permitted(target, Operation.REVIEW, actor);
    }

    private boolean permitted(Target target, Operation operation, String actor) {
        if (target == null || operation == null || actor == null || actor.isBlank()) return false;
        var at = Instant.now();
        if (humans.eligibleHuman(actor, at).filter(value -> actor.equals(value.id())).isEmpty()) return false;
        var principal = principals.principal(actor, List.of());
        if (!actor.equals(principal.userId()) || principal.employeeReferenceId() == null) return false;
        var kind = target.section() == null ? IdentityRegionalTopologyContract.Kind.PIPELINE
                : IdentityRegionalTopologyContract.Kind.PIPELINE_SEGMENT;
        var id = target.section() == null ? target.pipeline() : target.section();
        var resolution = topology.resolve(kind, id, target.company(), at).orElse(null);
        if (resolution == null || resolution.kind() != kind || !id.equals(resolution.resourceId())
                || !target.company().equals(resolution.companyPartyId())
                || resolution.ancestry().stream().noneMatch(scope ->
                    "PIPELINE".equals(scope.type()) && target.pipeline().equals(scope.targetId()))) return false;
        var requested = resolution.ancestry().stream()
                .map(scope -> new IdentityRegionalResponsibilityContract.Scope(scope.type(), scope.targetId())).toList();
        var eligible = responsibilities.eligibleScopes(actor, principal.employeeReferenceId(), requested, at);
        var regional = new ArrayList<RegionalResourceAccessPolicy.Candidate>();
        var operationScopes = new ArrayList<RegionalResourceAccessPolicy.Candidate>();
        for (var resource : requested) {
            var scope = new AuthorizationScope(ScopeType.valueOf(resource.type()), resource.targetId(), null);
            regional.add(new RegionalResourceAccessPolicy.Candidate(
                    decision(actor, RegionalAccessApplicationService.RESOURCE_READ, scope), eligible.contains(resource)));
            operationScopes.add(new RegionalResourceAccessPolicy.Candidate(
                    decision(actor, operation.permission(), scope), eligible.contains(resource)));
        }
        return policy.permits(true, regional) && policy.permits(true, operationScopes);
    }

    private RegionalResourceAccessPolicy.Grant decision(String actor, String permission, AuthorizationScope scope) {
        var value = grants.evaluate(new EvaluatePermissionQuery(actor, permission, null, null, scope));
        if (value == null || value.decision() == AuthorizationDecisionValue.INDETERMINATE)
            return RegionalResourceAccessPolicy.Grant.INDETERMINATE;
        if (value.decision() == AuthorizationDecisionValue.PERMIT && value.permitted())
            return RegionalResourceAccessPolicy.Grant.PERMIT;
        return "NO_GRANT_MATCHED".equals(value.reasonCode()) ? RegionalResourceAccessPolicy.Grant.NO_GRANT
                : RegionalResourceAccessPolicy.Grant.EXPLICIT_DENY;
    }
}
