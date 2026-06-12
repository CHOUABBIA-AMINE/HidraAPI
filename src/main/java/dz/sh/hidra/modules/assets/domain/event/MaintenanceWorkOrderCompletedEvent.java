/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : MaintenanceWorkOrderCompletedEvent
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : assets
 * @Package     : dz.sh.hidra.modules.assets.domain.event
 *
 * @Description : Published when a maintenance work order is completed.
 *
 */
package dz.sh.hidra.modules.assets.domain.event;

import java.time.Instant;

/**
 * Published when a maintenance work order is completed.
 */
public record MaintenanceWorkOrderCompletedEvent(
        String eventId,
    String workOrderId,
    Instant occurredAt
) implements AssetsDomainEvent {

    @Override
    public String eventType() {
        return "MaintenanceWorkOrderCompletedEvent";
    }
}
