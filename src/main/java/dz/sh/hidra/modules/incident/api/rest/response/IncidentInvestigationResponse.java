/**
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 * @Name        : IncidentInvestigationResponse
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-25
 * @Type        : Class
 * @Layer       : API
 * @Module      : incident
 * @Package     : dz.sh.hidra.modules.incident.api.rest.response
 * @Description : Typed T16 Incident investigation receipts and ordered historical reads.
 */
package dz.sh.hidra.modules.incident.api.rest.response;

import io.swagger.v3.oas.annotations.media.Schema;
import dz.sh.hidra.modules.incident.application.port.in.IncidentInvestigationUseCase;
import java.time.Instant;
import java.util.List;

public final class IncidentInvestigationResponse {
    private IncidentInvestigationResponse() {}
    @Schema(name = "IncidentInvestigationReceipt")
    public record Receipt(String incidentId,String eventId,String operation,long version,
            String investigationState,String actorId,Instant recordedAt,boolean replayed) {
        public static Receipt from(IncidentInvestigationUseCase.Receipt v) {
            return new Receipt(v.incidentId(),v.eventId(),v.operation(),v.version(),
                    v.investigationState(),v.actorId(),v.recordedAt(),v.replayed());
        }
    }
    @Schema(name = "IncidentInvestigationEvent")
    public record Event(String id,long version,String operation,String actorId,
            String requestId,String referenceType,String referenceId,String direction,
            String detail,String stateBefore,String stateAfter,Instant recordedAt) {
        public static Event from(IncidentInvestigationUseCase.Event e) {
            return new Event(e.id(),e.version(),e.operation(),e.actorId(),e.requestId(),
                    e.referenceType(),e.referenceId(),e.direction(),e.detail(),
                    e.stateBefore(),e.stateAfter(),e.recordedAt());
        }
    }
    @Schema(name = "IncidentInvestigationView")
    public record View(String incidentId,String incidentStatus,String investigationState,
            long version,String investigatorId,String submittedById,String reviewedById,
            String resolutionSummary,List<Event> history) {
        public static View from(IncidentInvestigationUseCase.View v) {
            return new View(v.incidentId(),v.incidentStatus(),v.investigationState(),v.version(),
                    v.investigatorId(),v.submittedById(),v.reviewedById(),v.resolutionSummary(),
                    v.history().stream().map(Event::from).toList());
        }
    }
}
