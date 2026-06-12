/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AlarmEscalatedEvent
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : alarm
 * @Package     : dz.sh.hidra.modules.alarm.domain.event
 *
 * @Description : Published when an alarm is escalated.
 *
 */
package dz.sh.hidra.modules.alarm.domain.event;

import java.time.Instant;

/**
 * Published when an alarm is escalated.
 */
public record AlarmEscalatedEvent(
        String eventId,
    String alarmId,
    String incidentId,
    Instant occurredAt
) implements AlarmDomainEvent {

    @Override
    public String eventType() {
        return "AlarmEscalatedEvent";
    }
}
