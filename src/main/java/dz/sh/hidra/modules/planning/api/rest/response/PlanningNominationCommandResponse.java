/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : PlanningNominationCommandResponse
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-15
 *
 * @Type        : Class
 * @Layer       : REST Adapter
 * @Module      : planning
 * @Package     : dz.sh.hidra.modules.planning.api.rest.response
 *
 * @Description : Maps typed nomination receipts and history for REST clients.
 *
 */
package dz.sh.hidra.modules.planning.api.rest.response;

import dz.sh.hidra.modules.planning.application.port.in.PlanningNominationCommandUseCase;
import dz.sh.hidra.modules.planning.domain.value.NominationStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.Instant;
import java.util.UUID;

public final class PlanningNominationCommandResponse {
    private PlanningNominationCommandResponse() { }

    @Schema(name = "PlanningNominationLifecycleEvent")
    public record Event(UUID eventId, UUID commandId, String nominationId, String revisionId, long version,
            String actorId, NominationStatus previousStatus, NominationStatus status, String reason,
            Instant recordedAt) {
        public static Event from(PlanningNominationCommandUseCase.Event event) {
            return new Event(event.eventId(), event.commandId(), event.nominationId(), event.revisionId(),
                    event.version(), event.actorId(), event.previousStatus(), event.status(),
                    event.reason(), event.recordedAt());
        }
    }

    @Schema(name = "PlanningNominationCommandReceipt")
    public record Receipt(Event event, boolean replay) {
        public static Receipt from(PlanningNominationCommandUseCase.Receipt receipt) {
            return new Receipt(Event.from(receipt.event()), receipt.replay());
        }
    }
}
