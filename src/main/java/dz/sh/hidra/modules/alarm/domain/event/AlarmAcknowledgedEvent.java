/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AlarmAcknowledgedEvent
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : alarm
 * @Package     : dz.sh.hidra.modules.alarm.domain.event
 *
 * @Description : Published when an alarm is acknowledged.
 *
 */
package dz.sh.hidra.modules.alarm.domain.event;

import java.time.Instant;

/**
 * Published when an alarm is acknowledged.
 */
public record AlarmAcknowledgedEvent(
        String eventId,
    String alarmId,
    String acknowledgedByActorId,
    Instant occurredAt
) implements AlarmDomainEvent {

    @Override
    public String eventType() {
        return "AlarmAcknowledgedEvent";
    }
}
