/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : NotificationMessageCreatedEvent
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : notification
 * @Package     : dz.sh.hidra.modules.notification.domain.event
 *
 * @Description : Published when a concrete notification message is created.
 *
 */
package dz.sh.hidra.modules.notification.domain.event;

import java.time.Instant;

/**
 * Published when a concrete notification message is created.
 */
public record NotificationMessageCreatedEvent(
        String eventId,
    String messageId,
    String requestId,
    Instant occurredAt
) implements NotificationDomainEvent {

    @Override
    public String eventType() {
        return "NotificationMessageCreatedEvent";
    }
}
