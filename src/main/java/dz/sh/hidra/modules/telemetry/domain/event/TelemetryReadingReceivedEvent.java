/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TelemetryReadingReceivedEvent
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : telemetry
 * @Package     : dz.sh.hidra.modules.telemetry.domain.event
 *
 * @Description : Published when a raw telemetry reading is received.
 *
 */
package dz.sh.hidra.modules.telemetry.domain.event;

import java.time.Instant;

/**
 * Published when a raw telemetry reading is received.
 *
     * @param eventId eventId
 * @param readingId readingId
 * @param pointId pointId
 * @param occurredAt occurredAt
 */
public record TelemetryReadingReceivedEvent(
        String eventId,
    String readingId,
    String pointId,
    Instant occurredAt
) implements TelemetryDomainEvent {

    @Override
    public String eventType() {
        return "TelemetryReadingReceivedEvent";
    }
}
