/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : QualifiedOperatingConstraintReadPort
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-21
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.application.port.out
 *
 * @Description : Fetches bounded, immutable Topology constraint rows and canonical target identity.
 *
 */
package dz.sh.hidra.modules.topology.application.port.out;

import dz.sh.hidra.modules.topology.domain.model.QualifiedPressureRestriction.Scope;
import dz.sh.hidra.modules.topology.domain.model.QualifiedPressureWorkflowCandidate;
import dz.sh.hidra.modules.topology.domain.model.QualifiedPressureRestriction.Revision;
import dz.sh.hidra.modules.topology.domain.model.QualifiedPressureRestriction.Termination;
import java.time.Instant;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

/** A datastore source is not authorization or proof of a T38 decision. */
public interface QualifiedOperatingConstraintReadPort {
    record Snapshot(List<Revision> revisions, List<Termination> terminations) {
        public Snapshot {
            revisions = List.copyOf(Objects.requireNonNull(revisions));
            terminations = List.copyOf(Objects.requireNonNull(terminations));
        }
    }
    boolean targetMatches(Scope scope, String pipelineSystemId);
    boolean conditionAllows(Scope scope, String unitCode);
    boolean conditionKnown(Scope scope);
    Optional<QualifiedPressureWorkflowCandidate> candidate(UUID candidateId);
    Snapshot snapshot(Scope scope, Instant recordedBy);
}
