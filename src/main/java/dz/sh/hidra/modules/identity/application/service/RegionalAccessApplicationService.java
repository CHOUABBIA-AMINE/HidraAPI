/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : RegionalAccessApplicationService
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-20
 *
 * @Type        : Class
 * @Layer       : Application
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.application.service
 *
 * @Description : Provides T33 scoped regional resource access evidence.
 *
 */
package dz.sh.hidra.modules.identity.application.service;

import dz.sh.hidra.modules.identity.application.contract.topology.TopologyCurrentActorContract;
import dz.sh.hidra.modules.identity.application.contract.topology.TopologyRegionalAccessContract;
import dz.sh.hidra.modules.identity.application.dto.PermissionDecisionDto;
import dz.sh.hidra.modules.identity.application.port.in.EvaluatePermissionUseCase;
import dz.sh.hidra.modules.identity.application.port.in.IdentityAdministrationQueryUseCase;
import dz.sh.hidra.modules.identity.application.port.in.ResolveRegionalAccessUseCase;
import dz.sh.hidra.modules.identity.application.query.EvaluatePermissionQuery;
import dz.sh.hidra.modules.identity.domain.service.RegionalResourceAccessPolicy;
import dz.sh.hidra.modules.identity.domain.value.AuthorizationDecisionValue;
import dz.sh.hidra.modules.identity.domain.value.AuthorizationScope;
import dz.sh.hidra.modules.identity.domain.value.ScopeType;
import dz.sh.hidra.modules.organization.application.contract.identity.IdentityRegionalResponsibilityContract;
import dz.sh.hidra.modules.topology.application.contract.identity.IdentityRegionalTopologyContract;
import java.time.Clock;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/** Identity owns the current actor and grant; Organization and Topology only supply owner facts. */
@Service
public class RegionalAccessApplicationService
        implements ResolveRegionalAccessUseCase, TopologyRegionalAccessContract {
    // New permission codes are provisioned only by authorized Identity administration.
    // No real employee or deployment role is seeded by T33.
    public static final String RESOURCE_READ = "topology:regional-resource:read";

    private final TopologyCurrentActorContract actors;
    private final IdentityAdministrationQueryUseCase principals;
    private final EvaluatePermissionUseCase grants;
    private final IdentityRegionalResponsibilityContract responsibilities;
    private final IdentityRegionalTopologyContract topology;
    private final RegionalResourceAccessPolicy policy;
    private final Clock clock;

    public RegionalAccessApplicationService(TopologyCurrentActorContract actors,
            IdentityAdministrationQueryUseCase principals, EvaluatePermissionUseCase grants,
            IdentityRegionalResponsibilityContract responsibilities,
            IdentityRegionalTopologyContract topology) {
        this(actors, principals, grants, responsibilities, topology,
                new RegionalResourceAccessPolicy(), Clock.systemUTC());
    }

    RegionalAccessApplicationService(TopologyCurrentActorContract actors,
            IdentityAdministrationQueryUseCase principals, EvaluatePermissionUseCase grants,
            IdentityRegionalResponsibilityContract responsibilities,
            IdentityRegionalTopologyContract topology, RegionalResourceAccessPolicy policy, Clock clock) {
        this.actors = Objects.requireNonNull(actors);
        this.principals = Objects.requireNonNull(principals);
        this.grants = Objects.requireNonNull(grants);
        this.responsibilities = Objects.requireNonNull(responsibilities);
        this.topology = Objects.requireNonNull(topology);
        this.policy = Objects.requireNonNull(policy);
        this.clock = Objects.requireNonNull(clock);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean permitted(Query request) {
        if (request == null) return false;
        try {
            var actor = actors.currentActor();
            if (actor.isEmpty()) return false;
            String userId = actor.orElseThrow().userId();
            var principal = principals.principal(userId, List.of());
            if (!userId.equals(principal.userId())
                    || principal.employeeReferenceId() == null
                    || principal.employeeReferenceId().isBlank()) return false;
            Instant at = clock.instant();
            var kind = IdentityRegionalTopologyContract.Kind.valueOf(request.resourceKind().name());
            var resolution = topology.resolve(kind, request.resourceId(), request.companyPartyId(), at);
            if (resolution.isEmpty()) return false;
            var owner = resolution.orElseThrow();
            if (owner.kind() != kind || !owner.resourceId().equals(request.resourceId())
                    || !owner.companyPartyId().equals(request.companyPartyId())) return false;

            var ancestors = owner.ancestry();
            var organizationRequests = ancestors.stream()
                    .map(s -> new IdentityRegionalResponsibilityContract.Scope(s.type(), s.targetId()))
                    .toList();
            var eligible = responsibilities.eligibleScopes(
                    userId, principal.employeeReferenceId(), organizationRequests, at);
            Set<IdentityRegionalResponsibilityContract.Scope> exact = new HashSet<>(eligible);
            var candidates = new ArrayList<RegionalResourceAccessPolicy.Candidate>();
            for (var scope : ancestors) {
                ScopeType scopeType = ScopeType.valueOf(scope.type());
                PermissionDecisionDto decision = grants.evaluate(
                        new EvaluatePermissionQuery(userId, RESOURCE_READ,
                                null, null, new AuthorizationScope(scopeType, scope.targetId(), null)));
                candidates.add(new RegionalResourceAccessPolicy.Candidate(
                        classify(decision),
                        exact.contains(new IdentityRegionalResponsibilityContract.Scope(
                                scope.type(), scope.targetId()))));
            }
            return policy.permits(true, candidates);
        } catch (RuntimeException unavailableOrInvalid) {
            // No exception reason or owner existence crosses this protected read boundary.
            return false;
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean permitted(TopologyRegionalAccessContract.Request request) {
        if (request == null) return false;
        try {
            return permitted(new Query(ResourceKind.valueOf(request.resourceKind().name()),
                    request.resourceId(), request.companyPartyId()));
        } catch (RuntimeException invalid) {
            return false;
        }
    }

    private static RegionalResourceAccessPolicy.Grant classify(PermissionDecisionDto decision) {
        if (decision == null || decision.decision() == AuthorizationDecisionValue.INDETERMINATE)
            return RegionalResourceAccessPolicy.Grant.INDETERMINATE;
        if (decision.decision() == AuthorizationDecisionValue.PERMIT && decision.permitted())
            return RegionalResourceAccessPolicy.Grant.PERMIT;
        if (decision.decision() == AuthorizationDecisionValue.DENY
                && "NO_GRANT_MATCHED".equals(decision.reasonCode()))
            return RegionalResourceAccessPolicy.Grant.NO_GRANT;
        return RegionalResourceAccessPolicy.Grant.EXPLICIT_DENY;
    }
}
