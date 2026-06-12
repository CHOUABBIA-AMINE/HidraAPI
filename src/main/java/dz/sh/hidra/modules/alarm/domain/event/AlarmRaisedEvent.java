/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AlarmRaisedEvent
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : alarm
 * @Package     : dz.sh.hidra.modules.alarm.domain.event
 *
 * @Description : Published when a formal alarm is raised.
 *
 */
package dz.sh.hidra.modules.alarm.domain.event;

import java.time.Instant;

/**
 * Published when a formal alarm is raised.
 */
public record AlarmRaisedEvent(
        String eventId,
    String alarmId,
    String alarmNumber,
    Instant occurredAt
) implements AlarmDomainEvent {

    @Override
    public String eventType() {
        return "AlarmRaisedEvent";
    }
}
