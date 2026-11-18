/**
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 * @Name        : HseWorkflowTargetLookup
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-18
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : hse
 * @Package     : dz.sh.hidra.modules.hse.infrastructure.integration
 * @Description : Resolves scoped enrolled HSE targets for signed Workflow operations.
 */
package dz.sh.hidra.modules.hse.infrastructure.integration;

import dz.sh.hidra.modules.hse.application.port.out.HseCapaAuthorityPort;
import dz.sh.hidra.modules.hse.application.port.out.HseCapaFacilityPort;
import dz.sh.hidra.modules.hse.application.port.out.HseCapaIncidentPort;
import dz.sh.hidra.modules.hse.application.port.out.HseCapaLifecycleStorePort;
import dz.sh.hidra.modules.workflow.application.contract.target.WorkflowOwnedTargetLookup;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Component
public class HseWorkflowTargetLookup implements WorkflowOwnedTargetLookup {
    private static final Set<String> TYPES = Set.of("HSE_CASE", "HSE_CAPA");
    private final HseCapaLifecycleStorePort store;
    private final HseCapaAuthorityPort authority;
    private final HseCapaFacilityPort facilities;
    private final HseCapaIncidentPort incidents;

    // Workflow registers this lookup during startup. The shared owner adapter uses
    // Incident services that themselves depend on Workflow, so resolve it on use.
    public HseWorkflowTargetLookup(HseCapaLifecycleStorePort store,
                                   @Lazy HseCapaAuthorityPort authority,
                                   @Lazy HseCapaFacilityPort facilities,
                                   @Lazy HseCapaIncidentPort incidents) {
        this.store = store;
        this.authority = authority;
        this.facilities = facilities;
        this.incidents = incidents;
    }

    @Override public String module() { return "hse"; }
    @Override public Set<String> targetTypeCodes() { return TYPES; }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public Optional<Target> eligibleTarget(String type, String id) {
        return resolve(type, id, true, false);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public boolean protectedReadAllowed(String type, String id, ProtectedRead operation) {
        return resolveAuthorized(type, id, false, false).isPresent();
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public boolean protectedReviewAllowed(String type, String id, String locator) {
        return resolve(type, id, true, true).isPresent();
    }

    private Optional<Target> resolve(String type, String id, boolean actionable, boolean review) {
        try {
            return resolveAuthorized(type, id, actionable, review);
        } catch (SecurityException | NoSuchElementException | AccessDeniedException unavailable) {
            return Optional.empty();
        }
    }

    private Optional<Target> resolveAuthorized(String type, String id, boolean actionable, boolean review) {
        if (type == null || !TYPES.contains(type) || id == null || id.isBlank()
                || id.length() > 80 || !id.equals(id.strip())) return Optional.empty();
        boolean capa = "HSE_CAPA".equals(type);
        // The internal lookup provides only the authorization scope; no target is
        // returned before the live owner checks, including for a Workflow assignee.
        var scope = (capa ? store.scopeByCapa(id) : store.scope(id)).orElseThrow();
        var actor = authority.require("hse:capa:read", scope.companyId(), scope.facilityId());
        var facility = facilities.active(scope.facilityId()).orElseThrow();
        if (!scope.companyId().equals(facility.companyId())
                || incidents.reviewed(scope.incidentId(), scope.companyId(), scope.facilityId()).isEmpty())
            return Optional.empty();
        if (review) authority.require("hse:capa:review", scope.companyId(), scope.facilityId());
        if (actionable) store.lockCase(scope.caseId());
        var ownerCase = store.caseView(scope.caseId());
        if (actionable && !Set.of("OPEN", "IN_PROGRESS").contains(ownerCase.status()))
            return Optional.empty();
        if (capa) {
            var target = store.capaView(scope.caseId(), id);
            if ((actionable && !"PROPOSED".equals(target.status()))
                    || (review && (actor.id().equals(target.ownerId())
                            || actor.id().equals(target.proposerId())))) return Optional.empty();
            return Optional.of(new Target(id, target.actionNumber(), target.title()));
        }
        if (actionable) {
            var actions = store.states(scope.caseId());
            if (actions.isEmpty() || actions.stream().anyMatch(a ->
                    a.status() != dz.sh.hidra.modules.hse.domain.value.CapaStatus.VERIFIED))
                return Optional.empty();
            if (review && (actor.id().equals(scope.actorId()) || actions.stream().anyMatch(a ->
                    actor.id().equals(a.ownerId()) || actor.id().equals(a.proposerId()))))
                return Optional.empty();
        }
        return Optional.of(new Target(id, ownerCase.caseNumber(), ownerCase.title()));
    }
}
