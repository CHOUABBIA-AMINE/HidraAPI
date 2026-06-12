/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AlarmDomainEventPublisherPort
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : alarm
 * @Package     : dz.sh.hidra.modules.alarm.application.port.out
 *
 * @Description : Publisher port for alarm domain events.
 *
 */
package dz.sh.hidra.modules.alarm.application.port.out;

import dz.sh.hidra.modules.alarm.domain.event.AlarmDomainEvent;

/**
 * Publisher port for alarm domain events.
 */
public interface AlarmDomainEventPublisherPort {

    void publish(AlarmDomainEvent event);
}
