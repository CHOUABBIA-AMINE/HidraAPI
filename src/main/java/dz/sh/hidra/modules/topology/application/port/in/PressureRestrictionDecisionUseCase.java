/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : PressureRestrictionDecisionUseCase
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-21
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.application.port.in
 *
 * @Description : Defines the owner commands and authorized reads for pressure restriction decisions.
 *
 */
package dz.sh.hidra.modules.topology.application.port.in;

import dz.sh.hidra.modules.topology.domain.model.PressureRestrictionDecisionRequest;
import dz.sh.hidra.modules.topology.domain.model.QualifiedPressureWorkflowCandidate.Kind;
import java.time.Instant;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;

public interface PressureRestrictionDecisionUseCase {
    record Target(String companyPartyId, String pipelineId, String sectionId, UUID requestId) {
        public Target {
            requireId(companyPartyId);
            requireId(pipelineId);
            if (sectionId != null) requireId(sectionId);
            if (requestId == null) throw new IllegalArgumentException("An exact request ID is required");
        }
    }

    /** These are addresses of existing Workflow records, never client assertions of approval. */
    record DecisionReference(UUID candidateId, String instanceId, String taskId, String actionId) {
        public DecisionReference {
            if (candidateId == null) throw new IllegalArgumentException("An exact candidate ID is required");
            requireId(instanceId);
            requireId(taskId);
            requireId(actionId);
        }
    }

    record Publish(Target target, long expectedVersion, List<DecisionReference> decisions) {
        public Publish {
            if (target == null || expectedVersion < 0 || decisions == null
                    || decisions.isEmpty() || decisions.size() > 2) {
                throw new IllegalArgumentException("An exact target, stream version and one or two decisions are required");
            }
            var candidates = new HashSet<UUID>();
            var actions = new HashSet<String>();
            for (DecisionReference decision : decisions) {
                if (decision == null || !candidates.add(decision.candidateId()) || !actions.add(decision.actionId())) {
                    throw new IllegalArgumentException("Each candidate requires its own Workflow decision");
                }
            }
            decisions = List.copyOf(decisions);
        }
    }

    record Candidate(UUID id, Kind kind, String workflowLocator) { }

    record RequestView(UUID requestId, long expectedVersion, Instant submittedAt,
                       List<Candidate> candidates, Published published) {
        public RequestView {
            candidates = List.copyOf(candidates);
        }
    }

    record Published(UUID requestId, long streamVersion, UUID revisionId, UUID terminationId,
                     Instant recordedAt) { }

    record DecisionEvidence(UUID candidateId, String instanceId, String taskId, String actionId,
                            String reviewerId, Instant decidedAt, String auditEventId) { }

    record EvidenceView(Published publication, List<DecisionEvidence> decisions) {
        public EvidenceView {
            decisions = List.copyOf(decisions);
        }
    }

    final class Conflict extends RuntimeException {
        private static final long serialVersionUID = 1L;

        public Conflict() {
            super("Pressure restriction request conflicts with its current state");
        }
    }

    RequestView submit(PressureRestrictionDecisionRequest request);
    RequestView request(Target target);
    Published publish(Publish command);
    EvidenceView evidence(Target target);

    private static void requireId(String id) {
        if (id == null || !id.matches("[A-Za-z0-9][A-Za-z0-9_.:-]{0,79}")) {
            throw new IllegalArgumentException("An exact bounded identifier is required");
        }
    }
}
