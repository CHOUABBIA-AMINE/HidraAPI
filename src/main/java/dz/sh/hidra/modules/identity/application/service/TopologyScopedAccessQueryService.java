/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TopologyScopedAccessQueryService
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-11
 *
 * @Type        : Class
 * @Layer       : Application
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.application.service
 *
 * @Description : Adapts trusted current actors to paired evidence-backed Topology permission decisions.
 *
 */
package dz.sh.hidra.modules.identity.application.service;

import dz.sh.hidra.modules.identity.application.contract.topology.TopologyCurrentActorContract;
import dz.sh.hidra.modules.identity.application.contract.topology.TopologyScopedAccessContract;
import dz.sh.hidra.modules.identity.application.dto.PermissionDecisionDto;
import dz.sh.hidra.modules.identity.application.port.in.EvaluatePermissionUseCase;
import dz.sh.hidra.modules.identity.application.query.EvaluatePermissionQuery;
import dz.sh.hidra.modules.identity.domain.value.AuthorizationDecisionValue;
import dz.sh.hidra.modules.identity.domain.value.AuthorizationScope;
import dz.sh.hidra.modules.identity.domain.value.ScopeType;
import java.util.Objects;
import org.springframework.stereotype.Service;

/** Sequential owner decisions; does not establish ownership or atomic revocation. */
@Service
public final class TopologyScopedAccessQueryService implements TopologyScopedAccessContract {

    private final TopologyCurrentActorContract actors;
    private final EvaluatePermissionUseCase evaluator;

    public TopologyScopedAccessQueryService(TopologyCurrentActorContract actors, EvaluatePermissionUseCase evaluator) {
        this.actors = Objects.requireNonNull(actors, "actors must not be null");
        this.evaluator = Objects.requireNonNull(evaluator, "evaluator must not be null");
    }

    @Override
    public boolean permitted(Request request) {
        if (request == null) {
            return false;
        }
        var actor = actors.currentActor();
        if (actor.isEmpty()) {
            return false;
        }
        String userId = actor.orElseThrow().userId();
        String permissionCode = permissionCode(request.operation());
        var company = evaluator.evaluate(new EvaluatePermissionQuery(userId, permissionCode,
                "STATION_GRAPH_REVISION", request.facilityId(),
                new AuthorizationScope(ScopeType.CUSTOM, "topology-company:" + request.companyPartyId(), null)));
        if (!isPermit(company)) {
            return false;
        }
        return isPermit(evaluator.evaluate(new EvaluatePermissionQuery(userId, permissionCode,
                "STATION_GRAPH_REVISION", request.facilityId(),
                new AuthorizationScope(ScopeType.FACILITY, request.facilityId(), null))));
    }

    private static boolean isPermit(PermissionDecisionDto result) {
        return result != null && result.permitted() && result.decision() == AuthorizationDecisionValue.PERMIT;
    }

    private static String permissionCode(Operation operation) {
        return switch (operation) {
            case AUTHOR -> "topology:station-revision:author";
            case PUBLISH -> "topology:station-revision:publish";
            case READ_CURRENT -> "topology:station-revision:read-current";
            case READ_HISTORY -> "topology:station-revision:read-history";
            case LIST_HISTORY -> "topology:station-revision:list-history";
            case WITHDRAW -> "topology:station-revision:withdraw";
            case REACTIVATE -> "topology:station-revision:reactivate";
            case TRANSFER -> "topology:station-revision:transfer";
        };
    }
}
