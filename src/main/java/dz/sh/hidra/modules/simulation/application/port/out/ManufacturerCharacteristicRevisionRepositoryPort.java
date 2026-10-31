/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ManufacturerCharacteristicRevisionRepositoryPort
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-30
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : simulation
 * @Package     : dz.sh.hidra.modules.simulation.application.port.out
 *
 * @Description : Strictly protected append-only persistence on the original PostgreSQL transaction.
 *
 */
package dz.sh.hidra.modules.simulation.application.port.out;

import dz.sh.hidra.modules.simulation.domain.model.ManufacturerCharacteristicRevision;
import dz.sh.hidra.modules.simulation.application.port.in.ManufacturerCharacteristicRevisionUseCase;
import dz.sh.hidra.platform.persistence.ProtectedRelationFence;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/** No method returns an authenticated credential or permits detached source mutation. */
public interface ManufacturerCharacteristicRevisionRepositoryPort {
    record Key(String companyPartyId, String sourceId, String revisionId) {}
    record Stored(Key key, ManufacturerCharacteristicRevision revision, String payloadSha256,
                  String workflowTargetDigest, String creatorUserId, String bindingId,
                  String sessionId, int backendPid, long transactionId, String databaseRole,
                  Instant insertedAt, byte[] canonicalPayload) {
        public Stored { canonicalPayload=canonicalPayload.clone(); }
        @Override public byte[] canonicalPayload() { return canonicalPayload.clone(); }
        @Override public String toString() { return "ManufacturerStoredRevision[redacted]"; }
    }
    record Qualification(String qualificationId, Key key, String sourceSha256, String workflowTargetDigest,
                         String workflowInstanceId, String workflowTaskId, String workflowActionId,
                         String approverId, String approverDisplayName, Instant approvedAt,
                         Instant qualifiedAt, String qualifyingUserId, String bindingId,
                         int backendPid, long transactionId, String databaseRole) {}
    /** Canonical Package A codec remains privately implemented by Simulation persistence. */
    byte[] encode(ManufacturerCharacteristicRevision revision);
    String sha256(byte[] canonicalPayload);
    interface Admission {}
    Admission acquire(ManufacturerCharacteristicRevisionUseCase.Command command);
    Optional<Stored> read(Admission admission, Key key);
    List<Stored> history(Admission admission, String companyPartyId, String sourceId);
    Optional<Stored> byWorkflowTarget(Admission admission, String domainSeparatedDigest);
    Optional<Qualification> qualification(Admission admission, Key key);
    /** Idempotent replay is allowed only for identical original payload and immutable provenance. */
    Stored append(Admission admission, Stored requested);
    Qualification appendQualification(Admission admission, Qualification requested);
    void requireCurrent(Admission admission);
    void prepareFinal(Admission admission, List<ProtectedRelationFence> relationFences, Instant observedAt, Instant validUntil);
    List<ProtectedRelationFence> finalFences(Admission admission);
    Set<String> mutationSql();
    boolean finalGuard(Admission admission);
    void flush();
}
