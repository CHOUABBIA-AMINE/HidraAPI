/**
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 * @Name        : LeakInvestigationUseCase
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-15
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : leakdetection
 * @Package     : dz.sh.hidra.modules.leakdetection.application.port.in
 * @Description : Typed governed Leak case creation, investigation and decision use cases.
 */
package dz.sh.hidra.modules.leakdetection.application.port.in;

import java.time.Instant;
import java.util.List;

public interface LeakInvestigationUseCase {
    record Command(String requestId, long expectedVersion, String operation,
            String referenceType, String referenceId, String direction,
            String detail, String disposition) {}
    record Event(String id, long version, String operation, String actorId, String requestId,
            String referenceType, String referenceId, String direction, String detail,
            String stateBefore, String stateAfter, Instant recordedAt) {}
    record View(String caseId, String candidateId, String caseStatus, String investigationState,
            long version, String investigatorId, String submittedById, String reviewedById,
            String disposition, List<Event> history) {}
    record Receipt(String caseId, String eventId, String operation, long version,
            String investigationState, String actorId, Instant recordedAt, boolean replayed) {}
    Receipt open(String candidateId, String requestId);
    Receipt execute(String caseId, Command command);
    View history(String caseId);
}
