/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TelemetrySourceCreatedEvent
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : telemetry
 * @Package     : dz.sh.hidra.modules.telemetry.domain.event
 *
 * @Description : Published when a telemetry source is created.
 *
 */
package dz.sh.hidra.modules.telemetry.domain.event;

import java.time.Instant;

/**
 * Published when a telemetry source is created.
 *
     * @param eventId eventId
 * @param sourceId sourceId
 * @param occurredAt occurredAt
 */
public record TelemetrySourceCreatedEvent(
        String eventId,
    String sourceId,
    Instant occurredAt
) implements TelemetryDomainEvent {

    @Override
    public String eventType() {
        return "TelemetrySourceCreatedEvent";
    }
}
