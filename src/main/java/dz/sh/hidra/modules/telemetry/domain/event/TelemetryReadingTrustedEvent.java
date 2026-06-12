/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TelemetryReadingTrustedEvent
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : telemetry
 * @Package     : dz.sh.hidra.modules.telemetry.domain.event
 *
 * @Description : Published when a trusted telemetry reading is produced.
 *
 */
package dz.sh.hidra.modules.telemetry.domain.event;

import java.time.Instant;

/**
 * Published when a trusted telemetry reading is produced.
 *
     * @param eventId eventId
 * @param trustedReadingId trustedReadingId
 * @param pointId pointId
 * @param occurredAt occurredAt
 */
public record TelemetryReadingTrustedEvent(
        String eventId,
    String trustedReadingId,
    String pointId,
    Instant occurredAt
) implements TelemetryDomainEvent {

    @Override
    public String eventType() {
        return "TelemetryReadingTrustedEvent";
    }
}
