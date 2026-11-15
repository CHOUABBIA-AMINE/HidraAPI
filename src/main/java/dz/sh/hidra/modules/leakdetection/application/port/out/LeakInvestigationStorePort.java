/**
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 * @Name        : LeakInvestigationStorePort
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-15
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : leakdetection
 * @Package     : dz.sh.hidra.modules.leakdetection.application.port.out
 * @Description : Atomic Leak case revision, source correlation and append-only decision port.
 */
package dz.sh.hidra.modules.leakdetection.application.port.out;

import dz.sh.hidra.modules.leakdetection.application.port.in.LeakInvestigationUseCase;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

public interface LeakInvestigationStorePort {
    record Snapshot(String caseId, String candidateId, String parentStatus, String state,
            long version, String investigatorId, String submittedById, String reviewedById,
            String disposition, boolean hasEvidence, boolean hasAction) {}
    void serializeCandidate(String candidateId);
    Optional<String> caseForCandidate(String candidateId);
    void register(String caseId, String candidateId, String requestId, String hash,
            String actorId, Instant at);
    Snapshot lock(String caseId);
    Snapshot view(String caseId);
    Optional<LeakInvestigationUseCase.Receipt> replay(String caseId, String requestId, String hash);
    LeakInvestigationUseCase.Receipt append(Snapshot before, LeakInvestigationUseCase.Command command,
            String actorId, String hash, Instant at);
    List<LeakInvestigationUseCase.Event> history(String caseId);
}
