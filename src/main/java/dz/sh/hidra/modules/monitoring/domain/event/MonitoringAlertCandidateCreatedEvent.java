/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : MonitoringAlertCandidateCreatedEvent
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : monitoring
 * @Package     : dz.sh.hidra.modules.monitoring.domain.event
 *
 * @Description : Published when monitoring creates an alert candidate.
 *
 */
package dz.sh.hidra.modules.monitoring.domain.event;

import java.time.Instant;

/**
 * Published when monitoring creates an alert candidate.
 */
public record MonitoringAlertCandidateCreatedEvent(
        String eventId,
    String alertCandidateId,
    String deviationId,
    Instant occurredAt
) implements MonitoringDomainEvent {

    @Override
    public String eventType() {
        return "MonitoringAlertCandidateCreatedEvent";
    }
}
