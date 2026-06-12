/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : NotificationDomainEvent
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Interface
 * @Layer       : Domain
 * @Module      : notification
 * @Package     : dz.sh.hidra.modules.notification.domain.event
 *
 * @Description : Notification domain event contract.
 *
 */
package dz.sh.hidra.modules.notification.domain.event;

import java.time.Instant;

/**
 * Notification domain event contract.
 */
public interface NotificationDomainEvent {

    String eventId();

    String eventType();

    Instant occurredAt();
}
