/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TelemetryDomainEvent
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Interface
 * @Layer       : Domain
 * @Module      : telemetry
 * @Package     : dz.sh.hidra.modules.telemetry.domain.event
 *
 * @Description : Telemetry domain event contract.
 *
 */
package dz.sh.hidra.modules.telemetry.domain.event;

import java.time.Instant;

/**
 * Telemetry domain event contract.
 */
public interface TelemetryDomainEvent {

    String eventId();

    String eventType();

    Instant occurredAt();
}
