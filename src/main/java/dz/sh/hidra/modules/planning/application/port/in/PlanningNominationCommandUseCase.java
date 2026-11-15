/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : PlanningNominationCommandUseCase
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-15
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : planning
 * @Package     : dz.sh.hidra.modules.planning.application.port.in
 *
 * @Description : Defines the signed nomination commands and typed history.
 *
 */
package dz.sh.hidra.modules.planning.application.port.in;

import dz.sh.hidra.modules.planning.domain.service.NominationLifecyclePolicy.Decision;
import dz.sh.hidra.modules.planning.domain.value.NominationStatus;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

public interface PlanningNominationCommandUseCase {
    record Create(UUID commandId, Instant expectedRevisionUpdatedAt, String code, String nominationTypeId,
            String productTypeId, BigDecimal quantity, String quantityUnitId, BigDecimal rate, String rateUnitId,
            String destinationFacilityId, Instant periodStart, Instant periodEnd) { }

    record Disposition(UUID commandId, long expectedVersion, Decision decision, String reason) { }

    record Event(UUID eventId, UUID commandId, String nominationId, String revisionId, long version,
            String actorId, NominationStatus previousStatus, NominationStatus status, String reason,
            Instant recordedAt) { }

    record Receipt(Event event, boolean replay) { }

    Receipt create(String revisionId, Create command);
    Receipt dispose(String nominationId, Disposition command);
    List<Event> history(String nominationId);
}
