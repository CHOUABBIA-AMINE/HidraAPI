/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : NotificationAcknowledgedEvent
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : notification
 * @Package     : dz.sh.hidra.modules.notification.domain.event
 *
 * @Description : Published when a recipient acknowledges a notification.
 *
 */
package dz.sh.hidra.modules.notification.domain.event;

import java.time.Instant;

/**
 * Published when a recipient acknowledges a notification.
 */
public record NotificationAcknowledgedEvent(
        String eventId,
    String acknowledgementId,
    String messageId,
    Instant occurredAt
) implements NotificationDomainEvent {

    @Override
    public String eventType() {
        return "NotificationAcknowledgedEvent";
    }
}
