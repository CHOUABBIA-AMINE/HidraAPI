/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TelemetryPointRegisteredEvent
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : telemetry
 * @Package     : dz.sh.hidra.modules.telemetry.domain.event
 *
 * @Description : Published when a telemetry point is registered.
 *
 */
package dz.sh.hidra.modules.telemetry.domain.event;

import java.time.Instant;

/**
 * Published when a telemetry point is registered.
 *
     * @param eventId eventId
 * @param pointId pointId
 * @param occurredAt occurredAt
 */
public record TelemetryPointRegisteredEvent(
        String eventId,
    String pointId,
    Instant occurredAt
) implements TelemetryDomainEvent {

    @Override
    public String eventType() {
        return "TelemetryPointRegisteredEvent";
    }
}
