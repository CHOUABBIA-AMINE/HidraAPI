/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : EngineeringRevisionPublicationRepositoryPort
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-20
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.application.port.out
 *
 * @Description : Binds publication source and ledger evidence to an opaque protected admission.
 *
 */
package dz.sh.hidra.modules.topology.application.port.out;

import dz.sh.hidra.modules.topology.application.port.in.EngineeringRevisionPublicationUseCase.*;
import dz.sh.hidra.modules.topology.domain.model.EngineeringRevisionPublication.*;
import dz.sh.hidra.platform.persistence.ProtectedRelationFence;
import java.time.Instant;
import java.util.*;

/** An admission is implementation-owned; scalar observations cannot mint it or authorize work. */
public interface EngineeringRevisionPublicationRepositoryPort {
    interface Admission {}
    /** Materialized provenance only, produced after genuine paired current authorization. */
    record Observation(Scope scope, String actorUserId, String nativeBindingId, String sessionId,
            long userGeneration, Instant sampledAt, Instant validUntil, int backendPid, long transactionId) {
        public Observation {
            Objects.requireNonNull(scope); Objects.requireNonNull(actorUserId);
            Objects.requireNonNull(nativeBindingId); Objects.requireNonNull(sessionId);
            dz.sh.hidra.modules.topology.domain.model.EngineeringRevisionPublication.microseconds(sampledAt);
            dz.sh.hidra.modules.topology.domain.model.EngineeringRevisionPublication.microseconds(validUntil);
            if (userGeneration < 0 || !sampledAt.isBefore(validUntil) || backendPid < 1 || transactionId < 1)
                throw new IllegalArgumentException("Invalid publication observation");
        }
    }
    Admission acquire(Command command);
    List<ProtectedRelationFence> finalFences(Admission admission);
    void eligible(Admission admission);
    Snapshot source(Admission admission);
    Head currentHead(Admission admission);
    Optional<Receipt> receipt(Admission admission);
    Set<String> mutationSql();
    Result apply(Admission admission, Observation observation);
    void flush();
    boolean finalGuard(Admission admission, Observation observation, List<ProtectedRelationFence> frozenFences);
}
