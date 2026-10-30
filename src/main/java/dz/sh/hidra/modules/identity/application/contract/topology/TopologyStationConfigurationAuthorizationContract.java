/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TopologyStationConfigurationAuthorizationContract
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-30
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.application.contract.topology
 *
 * @Description : Defines exact station configuration authorization descriptors and opaque owner admission.
 *
 */
package dz.sh.hidra.modules.identity.application.contract.topology;

import dz.sh.hidra.platform.persistence.ProtectedRelationFence;
import java.time.Instant;
import java.util.*;

/** Descriptors request authority; only a future native issuer may qualify its private handles. */
public interface TopologyStationConfigurationAuthorizationContract {
    Prepared prepare();
    Admission acquire(Prepared prepared, Request request);
    Contribution contribute(Admission admission);
    void requireCurrent(Admission admission);
    List<ProtectedRelationFence> finalFences(Admission admission);
    interface Prepared {}
    interface Admission {}
    enum Operation { SAVE, READ_CURRENT, READ_REVISION, LIST_HISTORY }
    enum Decision { PERMIT, DENY, INDETERMINATE }
    enum Scope { COMPANY, FACILITY }
    int MAX_REFERENCED_UNITS = 128;
    String CONFIGURATION_RESOURCE = "STATION_INSTALLED_CONFIGURATION";
    String UNIT_RESOURCE = "INSTALLED_UNIT";

    static String permissionCode(Operation operation, String resourceType) {
        Objects.requireNonNull(operation);
        if (UNIT_RESOURCE.equals(resourceType)) return "topology:installed-unit:read";
        if (!CONFIGURATION_RESOURCE.equals(resourceType))
            throw new IllegalArgumentException("Invalid configuration authorization resource type");
        return "topology:installed-configuration:" + switch (operation) {
            case SAVE -> "save";
            case READ_CURRENT, READ_REVISION -> "read";
            case LIST_HISTORY -> "history";
        };
    }

    record Requirement(String resourceType, String resourceId, Scope scope, String scopeReference) {
        public Requirement {
            if (!CONFIGURATION_RESOURCE.equals(resourceType) && !UNIT_RESOURCE.equals(resourceType))
                throw new IllegalArgumentException("Invalid resource type");
            exact(resourceId); Objects.requireNonNull(scope); exact(scopeReference);
            if (UNIT_RESOURCE.equals(resourceType) && scope != Scope.COMPANY)
                throw new IllegalArgumentException("Referenced unit requires company-scoped read");
        }
        @Override public String toString() { return "Requirement[station configuration resource descriptor]"; }
    }

    /** Unit references must ultimately be derived and checked against actual owner evidence. */
    record Request(Operation operation, String companyPartyId, String facilityId,
            String revisionId, List<String> referencedUnitIds) {
        public Request {
            Objects.requireNonNull(operation); exact(companyPartyId); exact(facilityId);
            if (operation == Operation.SAVE || operation == Operation.READ_REVISION) exact(revisionId);
            else if (revisionId != null) throw new IllegalArgumentException("Unexpected revision identity");
            if (referencedUnitIds == null || referencedUnitIds.size() > MAX_REFERENCED_UNITS)
                throw new IllegalArgumentException("Bounded referenced-unit list required");
            var sorted = new TreeSet<String>();
            for (String unit : referencedUnitIds) {
                exact(unit);
                if (!sorted.add(unit)) throw new IllegalArgumentException("Duplicate referenced unit");
            }
            referencedUnitIds = List.copyOf(sorted);
        }

        public List<Requirement> requirements() {
            var requirements = new ArrayList<Requirement>();
            requirements.add(new Requirement(CONFIGURATION_RESOURCE, facilityId, Scope.COMPANY, companyPartyId));
            requirements.add(new Requirement(CONFIGURATION_RESOURCE, facilityId, Scope.FACILITY, facilityId));
            for (String unit : referencedUnitIds)
                requirements.add(new Requirement(UNIT_RESOURCE, unit, Scope.COMPANY, companyPartyId));
            requirements.sort(Comparator.comparing(Requirement::resourceType)
                .thenComparing(Requirement::resourceId).thenComparing(r -> r.scope().name())
                .thenComparing(Requirement::scopeReference));
            return List.copyOf(requirements);
        }

        @Override public String toString() { return "Request[station configuration authorization descriptors]"; }
    }

    record ResourceDecision(Requirement requirement, Decision decision) {
        public ResourceDecision { Objects.requireNonNull(requirement); Objects.requireNonNull(decision); }
    }

    /** Inspectable result data is never an Admission handle or source-eligibility proof. */
    record Contribution(Request request, List<ResourceDecision> decisions, String bindingId,
            String sessionId, String userId, Instant sampledAt, Instant validUntil, int backendPid, long transactionId) {
        public Contribution {
            Objects.requireNonNull(request); decisions = List.copyOf(decisions);
            if (!decisions.stream().map(ResourceDecision::requirement).toList().equals(request.requirements()))
                throw new IllegalArgumentException("Exact ordered requirement decisions required");
            exact(bindingId); exact(sessionId); exact(userId);
            if (sampledAt == null || validUntil == null || !sampledAt.isBefore(validUntil)
                    || backendPid <= 0 || transactionId <= 0)
                throw new IllegalArgumentException("Invalid physical authority bounds");
        }
        public Decision overallDecision() {
            if (decisions.stream().anyMatch(d -> d.decision() == Decision.DENY)) return Decision.DENY;
            if (decisions.stream().anyMatch(d -> d.decision() == Decision.INDETERMINATE)) return Decision.INDETERMINATE;
            return Decision.PERMIT;
        }
        @Override public String toString() { return "Contribution[station configuration Identity scalars]"; }
    }

    private static void exact(String value) {
        if (value == null || value.isBlank() || !value.equals(value.strip())
                || value.codePointCount(0, value.length()) > 80
                || value.codePoints().anyMatch(cp -> Character.isISOControl(cp) || cp >= 0xd800 && cp <= 0xdfff))
            throw new IllegalArgumentException("Exact bounded Unicode reference required");
    }
}
