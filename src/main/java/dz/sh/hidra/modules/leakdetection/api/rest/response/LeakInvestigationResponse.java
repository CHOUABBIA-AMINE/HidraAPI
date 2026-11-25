/**
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 * @Name        : LeakInvestigationResponse
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-25
 * @Type        : Class
 * @Layer       : API
 * @Module      : leakdetection
 * @Package     : dz.sh.hidra.modules.leakdetection.api.rest.response
 * @Description : Typed T16 Leak suspected-case decision and historical audit views.
 */
package dz.sh.hidra.modules.leakdetection.api.rest.response;

import io.swagger.v3.oas.annotations.media.Schema;
import dz.sh.hidra.modules.leakdetection.application.port.in.LeakInvestigationUseCase;
import java.time.Instant;
import java.util.List;

public final class LeakInvestigationResponse {
    private LeakInvestigationResponse() {}
    @Schema(name = "LeakInvestigationReceipt")
    public record Receipt(String caseId,String eventId,String operation,long version,
            String investigationState,String actorId,Instant recordedAt,boolean replayed) {
        public static Receipt from(LeakInvestigationUseCase.Receipt v) {
            return new Receipt(v.caseId(),v.eventId(),v.operation(),v.version(),
                    v.investigationState(),v.actorId(),v.recordedAt(),v.replayed());
        }
    }
    @Schema(name = "LeakInvestigationEvent")
    public record Event(String id,long version,String operation,String actorId,
            String requestId,String referenceType,String referenceId,String direction,
            String detail,String stateBefore,String stateAfter,Instant recordedAt) {
        public static Event from(LeakInvestigationUseCase.Event e) {
            return new Event(e.id(),e.version(),e.operation(),e.actorId(),e.requestId(),
                    e.referenceType(),e.referenceId(),e.direction(),e.detail(),
                    e.stateBefore(),e.stateAfter(),e.recordedAt());
        }
    }
    @Schema(name = "LeakInvestigationView")
    public record View(String caseId,String candidateId,String caseStatus,String investigationState,
            long version,String investigatorId,String submittedById,String reviewedById,
            String disposition,List<Event> history) {
        public static View from(LeakInvestigationUseCase.View v) {
            return new View(v.caseId(),v.candidateId(),v.caseStatus(),v.investigationState(),
                    v.version(),v.investigatorId(),v.submittedById(),v.reviewedById(),
                    v.disposition(),v.history().stream().map(Event::from).toList());
        }
    }
}
