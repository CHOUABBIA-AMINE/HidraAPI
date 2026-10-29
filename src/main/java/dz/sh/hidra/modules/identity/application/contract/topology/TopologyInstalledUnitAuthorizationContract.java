/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TopologyInstalledUnitAuthorizationContract
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-29
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.application.contract.topology
 *
 * @Description : Defines exact installed-unit authorization descriptors and opaque owner admission.
 *
 */
package dz.sh.hidra.modules.identity.application.contract.topology;

import dz.sh.hidra.platform.persistence.ProtectedRelationFence;
import java.time.Instant;
import java.util.*;

/** Requested descriptors are not ownership proof; only the issuer accepts its private tickets. */
public interface TopologyInstalledUnitAuthorizationContract {
    Prepared prepare();
    Admission acquire(Prepared prepared, Request request);
    Contribution contribute(Admission admission);
    void requireCurrent(Admission admission);
    List<ProtectedRelationFence> finalFences(Admission admission);
    interface Prepared {}
    interface Admission {}
    enum Operation { REGISTER_UNIT, READ_UNIT, RECORD_INSTALLATION, CLOSE_INSTALLATION,
        RELOCATE, READ_INSTALLATION, READ_HISTORY, REPLACE }
    enum Decision { PERMIT, DENY, INDETERMINATE }
    enum Scope { COMPANY, FACILITY }

    static String permissionCode(Operation operation) {
        return "topology:installed-unit:" + switch (Objects.requireNonNull(operation)) {
            case REGISTER_UNIT -> "register";
            case READ_UNIT -> "read";
            case RECORD_INSTALLATION -> "record-installation";
            case CLOSE_INSTALLATION -> "close-installation";
            case RELOCATE -> "relocate";
            case READ_INSTALLATION -> "read-installation";
            case READ_HISTORY -> "read-history";
            case REPLACE -> throw new IllegalArgumentException("Replacement requires a resource-specific permission");
        };
    }

    /** One workflow may require distinct permissions; each permission still covers one exact resource type. */
    static String permissionCode(Operation operation, String resourceType) {
        Objects.requireNonNull(operation);
        if (operation == Operation.REPLACE) {
            if ("INSTALLED_UNIT".equals(resourceType)) return "topology:installed-unit:replace-unit";
            if ("INSTALLED_UNIT_INSTALLATION".equals(resourceType)) return "topology:installed-unit:replace-installation";
            throw new IllegalArgumentException("Invalid replacement resource type");
        }
        String expected = operation == Operation.REGISTER_UNIT || operation == Operation.READ_UNIT
                ? "INSTALLED_UNIT" : "INSTALLED_UNIT_INSTALLATION";
        if (!expected.equals(resourceType)) throw new IllegalArgumentException("Invalid operation resource type");
        return permissionCode(operation);
    }

    record Requirement(String resourceType, String resourceId, Scope scope, String scopeReference) {
        public Requirement {
            if (!Set.of("INSTALLED_UNIT", "INSTALLED_UNIT_INSTALLATION").contains(resourceType))
                throw new IllegalArgumentException("Invalid resource type");
            exact(resourceId); Objects.requireNonNull(scope); exact(scopeReference);
        }
    }

    /** Source/destination are separately bound even when both name the same facility. */
    record Request(Operation operation, String companyPartyId, String unitId,
            String installationId, String replacementInstallationId,
            String sourceFacilityId, String destinationFacilityId, String replacementUnitId) {
        public Request(Operation operation, String companyPartyId, String unitId,
                String installationId, String replacementInstallationId,
                String sourceFacilityId, String destinationFacilityId) {
            this(operation,companyPartyId,unitId,installationId,replacementInstallationId,
                    sourceFacilityId,destinationFacilityId,null);
        }
        public Request {
            Objects.requireNonNull(operation); exact(companyPartyId); exact(unitId);
            for (String id : new String[]{installationId,replacementInstallationId,sourceFacilityId,destinationFacilityId,replacementUnitId})
                if (id != null) exact(id);
            boolean unit = operation == Operation.REGISTER_UNIT || operation == Operation.READ_UNIT;
            boolean record = operation == Operation.RECORD_INSTALLATION;
            boolean relocate = operation == Operation.RELOCATE;
            if (operation == Operation.REPLACE) {
                if (replacementUnitId == null || unitId.equals(replacementUnitId)
                        || installationId == null || replacementInstallationId == null
                        || installationId.equals(replacementInstallationId) || sourceFacilityId == null
                        || !sourceFacilityId.equals(destinationFacilityId))
                    throw new IllegalArgumentException("Invalid replacement descriptors");
            } else {
                if (replacementUnitId != null) throw new IllegalArgumentException("Unexpected replacement unit");
                if (unit ? installationId != null || replacementInstallationId != null || sourceFacilityId != null || destinationFacilityId != null
                    : installationId == null || (relocate ? replacementInstallationId == null || installationId.equals(replacementInstallationId)
                        || sourceFacilityId == null || destinationFacilityId == null
                        : replacementInstallationId != null || (record ? sourceFacilityId != null || destinationFacilityId == null
                            : sourceFacilityId == null || destinationFacilityId != null)))
                    throw new IllegalArgumentException("Invalid operation descriptors");
            }
        }
        public List<Requirement> requirements() {
            var values = new TreeSet<Requirement>(Comparator.comparing(Requirement::resourceType)
                    .thenComparing(Requirement::resourceId).thenComparing(r -> r.scope().name())
                    .thenComparing(Requirement::scopeReference));
            if (operation == Operation.REPLACE) {
                for (String id : List.of(unitId,replacementUnitId))
                    values.add(new Requirement("INSTALLED_UNIT",id,Scope.COMPANY,companyPartyId));
                for (String id : List.of(installationId,replacementInstallationId)) {
                    values.add(new Requirement("INSTALLED_UNIT_INSTALLATION",id,Scope.COMPANY,companyPartyId));
                    values.add(new Requirement("INSTALLED_UNIT_INSTALLATION",id,Scope.FACILITY,sourceFacilityId));
                }
                return List.copyOf(values);
            }
            boolean unit = operation == Operation.REGISTER_UNIT || operation == Operation.READ_UNIT;
            String type = unit ? "INSTALLED_UNIT" : "INSTALLED_UNIT_INSTALLATION";
            var resources = replacementInstallationId == null ? List.of(unit ? unitId : installationId)
                    : List.of(installationId,replacementInstallationId);
            for (String id : resources) {
                values.add(new Requirement(type,id,Scope.COMPANY,companyPartyId));
                if (sourceFacilityId != null) values.add(new Requirement(type,id,Scope.FACILITY,sourceFacilityId));
                if (destinationFacilityId != null) values.add(new Requirement(type,id,Scope.FACILITY,destinationFacilityId));
            }
            return List.copyOf(values);
        }
    }
    record ResourceDecision(Requirement requirement, Decision decision) {
        public ResourceDecision { Objects.requireNonNull(requirement); Objects.requireNonNull(decision); }
    }
    record Contribution(Request request, List<ResourceDecision> decisions, String bindingId,
            String sessionId, String userId, Instant sampledAt, Instant validUntil, int backendPid, long transactionId) {
        public Contribution {
            Objects.requireNonNull(request); decisions=List.copyOf(decisions);
            if (!decisions.stream().map(ResourceDecision::requirement).toList().equals(request.requirements()))
                throw new IllegalArgumentException("Exact requirement decisions required");
            exact(bindingId); exact(sessionId); exact(userId);
            if (sampledAt==null || validUntil==null || !sampledAt.isBefore(validUntil) || backendPid<=0 || transactionId<=0)
                throw new IllegalArgumentException("Invalid physical authority bounds");
        }
        public Decision overallDecision() {
            if (decisions.stream().anyMatch(d -> d.decision()==Decision.DENY)) return Decision.DENY;
            if (decisions.stream().anyMatch(d -> d.decision()==Decision.INDETERMINATE)) return Decision.INDETERMINATE;
            return Decision.PERMIT;
        }
        @Override public String toString() { return "Contribution[installed-unit Identity scalars]"; }
    }
    private static void exact(String value) {
        if (value==null || value.isBlank() || !value.equals(value.strip()) || value.codePointCount(0,value.length())>80)
            throw new IllegalArgumentException("Exact bounded reference required");
    }
}
