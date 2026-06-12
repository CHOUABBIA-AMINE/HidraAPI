/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : NotificationDeliveryAttemptRecordedEvent
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : notification
 * @Package     : dz.sh.hidra.modules.notification.domain.event
 *
 * @Description : Published when a delivery attempt is recorded.
 *
 */
package dz.sh.hidra.modules.notification.domain.event;

import java.time.Instant;

/**
 * Published when a delivery attempt is recorded.
 */
public record NotificationDeliveryAttemptRecordedEvent(
        String eventId,
    String deliveryAttemptId,
    String messageId,
    Instant occurredAt
) implements NotificationDomainEvent {

    @Override
    public String eventType() {
        return "NotificationDeliveryAttemptRecordedEvent";
    }
}
