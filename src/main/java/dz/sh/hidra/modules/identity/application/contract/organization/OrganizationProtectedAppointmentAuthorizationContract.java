/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : OrganizationProtectedAppointmentAuthorizationContract
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-22
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.application.contract.organization
 *
 * @Description : Defines bounded appointment authorization requests and non-authoritative observations.
 *
 */
package dz.sh.hidra.modules.identity.application.contract.organization;

import dz.sh.hidra.platform.persistence.ProtectedRelationFence;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.TreeSet;

/**
 * Request descriptors and contributions are data, never ownership evidence or credentials.
 * The issuing owner must validate its private tickets at every lifecycle operation. Organization
 * must separately match every requested descriptor against actual current or pinned ownership
 * before exposing rows, counts or metadata. No implementation or permission grant is implied.
 */
public interface OrganizationProtectedAppointmentAuthorizationContract {
    int MAX_READ_PAIRS = 100;
    int MAX_IDENTIFIER_CODE_POINTS = 80;

    enum Action {
        CREATE, END, TRANSFER, CORRECT, READ_CURRENT, READ_HISTORY, READ_POLICY
    }

    enum Decision { PERMIT, DENY, INDETERMINATE }

    enum ResourceKind {
        COMPANY, UNIT, EMPLOYEE;

        public String resourceType() {
            return switch (this) {
                case COMPANY -> "ORGANIZATION_COMPANY";
                case UNIT -> "ORGANIZATION_UNIT";
                case EMPLOYEE -> "ORGANIZATION_EMPLOYEE";
            };
        }
    }

    /** Immutable requested ownership pair; neither field proves an actual binding. */
    record CompanyUnit(String companyPartyId, String organizationUnitId) {
        public CompanyUnit {
            exact(companyPartyId);
            exact(organizationUnitId);
        }
    }

    /**
     * Pairs must already be distinct and ordered by company ID, then unit ID, using String order.
     * Rejecting rather than sorting preserves an exact caller request for subsequent owner checks.
     * A null employee selects a unit/position view. Mutations always require an exact employee.
     */
    record Request(Action action, List<CompanyUnit> companyUnits, String employeeId) {
        public Request {
            Objects.requireNonNull(action, "Action required");
            companyUnits = List.copyOf(Objects.requireNonNull(companyUnits, "Ownership pairs required"));
            if (companyUnits.isEmpty() || companyUnits.size() > MAX_READ_PAIRS) {
                throw new IllegalArgumentException("Bounded nonempty ownership pairs required");
            }
            var order = Comparator.comparing(CompanyUnit::companyPartyId)
                    .thenComparing(CompanyUnit::organizationUnitId);
            for (int i = 1; i < companyUnits.size(); i++) {
                if (order.compare(companyUnits.get(i - 1), companyUnits.get(i)) >= 0) {
                    throw new IllegalArgumentException("Distinct canonical ownership pair order required");
                }
            }
            if (employeeId != null) exact(employeeId);
            switch (action) {
                case CREATE, END -> {
                    if (companyUnits.size() != 1 || employeeId == null) {
                        throw new IllegalArgumentException("One ownership pair and employee required");
                    }
                }
                case TRANSFER, CORRECT -> {
                    if (companyUnits.size() > 2 || employeeId == null
                            || !companyUnits.get(0).companyPartyId()
                                    .equals(companyUnits.get(companyUnits.size() - 1).companyPartyId())) {
                        throw new IllegalArgumentException("One or two same-company pairs and employee required");
                    }
                }
                case READ_POLICY -> {
                    if (employeeId != null) {
                        throw new IllegalArgumentException("Policy reads do not select an employee");
                    }
                }
                case READ_CURRENT, READ_HISTORY -> { /* Exact employee is optional for a unit view. */ }
            }
        }

        /**
         * One decision per distinct company, then distinct unit, then optional employee.
         * Each family is ordered by exact ID. Repeated units in historical company pairs share
         * their exact unit decision, while every represented company still requires its decision.
         */
        public List<Requirement> requirements() {
            var companies = new TreeSet<String>();
            var units = new TreeSet<String>();
            for (var pair : companyUnits) {
                companies.add(pair.companyPartyId());
                units.add(pair.organizationUnitId());
            }
            var result = new ArrayList<Requirement>();
            companies.forEach(id -> result.add(new Requirement(ResourceKind.COMPANY, id)));
            units.forEach(id -> result.add(new Requirement(ResourceKind.UNIT, id)));
            if (employeeId != null) result.add(new Requirement(ResourceKind.EMPLOYEE, employeeId));
            return List.copyOf(result);
        }
    }

    /** Fixed-kind exact selector. It cannot carry an arbitrary permission, scope or user ID. */
    record Requirement(ResourceKind kind, String reference) {
        public Requirement {
            Objects.requireNonNull(kind, "Resource kind required");
            exact(reference);
        }
    }

    record ResourceDecision(Requirement requirement, Decision decision) {
        public ResourceDecision {
            Objects.requireNonNull(requirement, "Requirement required");
            Objects.requireNonNull(decision, "Decision required");
        }
    }

    interface Prepared {}
    interface Admission {}

    /** Prepare genuine native authentication outside any database transaction. */
    Prepared prepare();
    /** Consume only this issuer's preparation in the caller's actual protected transaction. */
    Admission acquire(Prepared prepared, Request request);
    /** Consume admission once; evaluate every requirement at one genuine native sample. */
    Contribution contribute(Admission admission);
    /** Recheck the private ticket, native/evidence expiry and physical transaction fences. */
    void requireCurrent(Admission admission);
    /** Recheck current authority before exposing scalar relation requirements. */
    List<ProtectedRelationFence> finalFences(Admission admission);

    /** Single-use issuer operation; only a private completed mutating admission can authenticate it. */
    CommitProof issueCommitProof(Admission admission, CommitIntent intent);
    /** Additional already-held Identity key fences, acquired before downstream owner fences. */
    List<ProtectedRelationFence> commitProofFences(Admission admission);

    record CommitIntent(java.util.UUID eventId, String commandSha256, String payloadSha256,
            Instant operationValidUntil) {
        public CommitIntent {
            Objects.requireNonNull(eventId, "Event required");
            if (commandSha256 == null || !commandSha256.matches("[0-9a-f]{64}")
                    || payloadSha256 == null || !payloadSha256.matches("[0-9a-f]{64}"))
                throw new IllegalArgumentException("Exact SHA256 required");
            Objects.requireNonNull(operationValidUntil, "Deadline required");
            if (operationValidUntil.getNano() % 1000 != 0)
                throw new IllegalArgumentException("Exact microseconds required");
            Math.addExact(Math.multiplyExact(operationValidUntil.getEpochSecond(), 1_000_000L),
                    operationValidUntil.getNano() / 1000);
        }
        @Override public String toString() { return "CommitIntent[redacted]"; }
    }

    /** Transport construction conveys no authority; SQL must authenticate every byte. */
    record CommitProof(byte[] frame, byte[] mac) {
        public CommitProof {
            if (frame == null || frame.length == 0 || frame.length > 4096 || mac == null || mac.length != 32)
                throw new IllegalArgumentException("Bounded commit proof required");
            frame = frame.clone(); mac = mac.clone();
        }
        @Override public byte[] frame() { return frame.clone(); }
        @Override public byte[] mac() { return mac.clone(); }
        @Override public String toString() { return "CommitProof[redacted]"; }
    }

    /** The complete catalog vocabulary is twenty definitions; this method creates no grant. */
    static String permissionCode(Action action, ResourceKind kind) {
        Objects.requireNonNull(action, "Action required");
        Objects.requireNonNull(kind, "Resource kind required");
        if (action == Action.READ_POLICY && kind == ResourceKind.EMPLOYEE) {
            throw new IllegalArgumentException("Employee policy permission is unsupported");
        }
        String resource = switch (kind) {
            case COMPANY -> "appointment-company";
            case UNIT -> "appointment-unit";
            case EMPLOYEE -> "appointment-employee";
        };
        String verb = switch (action) {
            case CREATE -> "create";
            case END -> "end";
            case TRANSFER -> "transfer";
            case CORRECT -> "correct";
            case READ_CURRENT -> "read-current";
            case READ_HISTORY -> "read-history";
            case READ_POLICY -> "read-policy";
        };
        return "organization:" + resource + ":" + verb;
    }

    /**
     * Complete ordered observations for a single sample, not a transferable authorization result.
     * Construction validates shape only. Only the issuing owner's live Admission can be current.
     */
    record Contribution(Request request, List<ResourceDecision> decisions,
            String bindingId, String sessionId, String userId, Instant sampledAt, Instant validUntil,
            int backendPid, long transactionId) {
        public Contribution {
            Objects.requireNonNull(request, "Request required");
            decisions = List.copyOf(Objects.requireNonNull(decisions, "Decisions required"));
            if (!decisions.stream().map(ResourceDecision::requirement).toList()
                    .equals(request.requirements())) {
                throw new IllegalArgumentException("Complete exact ordered decision coverage required");
            }
            exact(bindingId);
            exact(sessionId);
            exact(userId);
            Objects.requireNonNull(sampledAt, "Sample time required");
            Objects.requireNonNull(validUntil, "Expiry required");
            if (!sampledAt.isBefore(validUntil) || backendPid <= 0 || transactionId <= 0) {
                throw new IllegalArgumentException("Positive physical coordinates and live sample required");
            }
        }

        /** Scalar aggregation only: explicit DENY wins over uncertainty; all must permit. */
        public Decision overallDecision() {
            if (decisions.stream().anyMatch(d -> d.decision() == Decision.DENY)) return Decision.DENY;
            if (decisions.stream().anyMatch(d -> d.decision() == Decision.INDETERMINATE)) {
                return Decision.INDETERMINATE;
            }
            return Decision.PERMIT;
        }
    }

    private static void exact(String id) {
        if (id == null || id.isBlank() || !id.equals(id.trim()) || !id.equals(id.strip())
                || id.codePointCount(0, id.length()) > MAX_IDENTIFIER_CODE_POINTS
                || id.codePoints().anyMatch(c -> Character.isISOControl(c)
                        || c >= Character.MIN_SURROGATE && c <= Character.MAX_SURROGATE)
                || Character.isSpaceChar(id.codePointAt(0))
                || Character.isSpaceChar(id.codePointBefore(id.length()))) {
            throw new IllegalArgumentException("Exact bounded identifier required");
        }
    }
}

