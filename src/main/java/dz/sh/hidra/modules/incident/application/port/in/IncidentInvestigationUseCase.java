/**
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 * @Name        : IncidentInvestigationUseCase
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-15
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : incident
 * @Package     : dz.sh.hidra.modules.incident.application.port.in
 * @Description : Domain-driven signed Incident investigation command and read contract.
 */
package dz.sh.hidra.modules.incident.application.port.in;

import java.time.Instant;
import java.util.List;

public interface IncidentInvestigationUseCase {
    record Command(String requestId, long expectedVersion, String operation,
            String referenceType, String referenceId, String direction,
            String detail, String resolutionTypeId, boolean corrective, boolean preventive) {}
    record Event(String id, long version, String operation, String actorId, String requestId,
            String referenceType, String referenceId, String direction, String detail,
            String stateBefore, String stateAfter, Instant recordedAt) {}
    record View(String incidentId, String incidentStatus, String investigationState,
            long version, String investigatorId, String submittedById, String reviewedById,
            String resolutionSummary, List<Event> history) {}
    record Receipt(String incidentId, String eventId, String operation, long version,
            String investigationState, String actorId, Instant recordedAt, boolean replayed) {}
    Receipt execute(String incidentId, Command command);
    View history(String incidentId);
}
