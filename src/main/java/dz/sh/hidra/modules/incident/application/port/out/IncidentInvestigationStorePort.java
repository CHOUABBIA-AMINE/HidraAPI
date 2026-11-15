/**
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 * @Name        : IncidentInvestigationStorePort
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-15
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : incident
 * @Package     : dz.sh.hidra.modules.incident.application.port.out
 * @Description : Atomic PostgreSQL-agnostic Incident investigation repository and audit port.
 */
package dz.sh.hidra.modules.incident.application.port.out;

import dz.sh.hidra.modules.incident.application.port.in.IncidentInvestigationUseCase;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

public interface IncidentInvestigationStorePort {
    record Snapshot(String incidentId, String parentStatus, String state, long version,
            String investigatorId, String submittedById, String reviewedById,
            String resolutionTypeId, String resolutionSummary, boolean corrective, boolean preventive,
            boolean hasEvidence, boolean hasAction, boolean hasRootCause) {}
    Snapshot lock(String incidentId);
    Snapshot view(String incidentId);
    Optional<IncidentInvestigationUseCase.Receipt> replay(String incidentId, String requestId, String hash);
    IncidentInvestigationUseCase.Receipt append(Snapshot before,
            IncidentInvestigationUseCase.Command command, String actorId,
            String digest, Instant at);
    List<IncidentInvestigationUseCase.Event> history(String incidentId);
    /** Only reviewed T16 cases are eligible for existing HMR-090 closure. */
    boolean reviewedForClosure(String incidentId, String closerId);
}
