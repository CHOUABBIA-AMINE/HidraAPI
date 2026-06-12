/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : DeviationDetectedEvent
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : monitoring
 * @Package     : dz.sh.hidra.modules.monitoring.domain.event
 *
 * @Description : Published when monitoring detects an actual-vs-expected deviation.
 *
 */
package dz.sh.hidra.modules.monitoring.domain.event;

import java.time.Instant;

/**
 * Published when monitoring detects an actual-vs-expected deviation.
 */
public record DeviationDetectedEvent(
        String eventId,
    String deviationId,
    String topologyAssetId,
    Instant occurredAt
) implements MonitoringDomainEvent {

    @Override
    public String eventType() {
        return "DeviationDetectedEvent";
    }
}
