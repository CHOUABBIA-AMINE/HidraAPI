/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AlarmDomainEvent
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Interface
 * @Layer       : Domain
 * @Module      : alarm
 * @Package     : dz.sh.hidra.modules.alarm.domain.event
 *
 * @Description : Alarm domain event contract.
 *
 */
package dz.sh.hidra.modules.alarm.domain.event;

import java.time.Instant;

/**
 * Alarm domain event contract.
 */
public interface AlarmDomainEvent {

    String eventId();

    String eventType();

    Instant occurredAt();
}
