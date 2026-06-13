/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : LocalNotificationDeliveryGatewayAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-13
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : notification
 * @Package     : dz.sh.hidra.modules.notification.infrastructure.integration
 *
 * @Description : Local outbound notification delivery gateway used until a real provider connector is installed.
 *
 */
package dz.sh.hidra.modules.notification.infrastructure.integration;

import dz.sh.hidra.modules.notification.application.dto.NotificationDeliveryGatewayResult;
import dz.sh.hidra.modules.notification.application.port.out.NotificationDeliveryGatewayPort;
import dz.sh.hidra.modules.notification.domain.model.NotificationMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.UUID;

/**
 * Local outbound notification delivery gateway used until a real provider connector is installed.
 */
@Component
public final class LocalNotificationDeliveryGatewayAdapter implements NotificationDeliveryGatewayPort {

    private static final Logger LOGGER = LoggerFactory.getLogger(LocalNotificationDeliveryGatewayAdapter.class);

    private final String providerReference;

    public LocalNotificationDeliveryGatewayAdapter(
            @Value("${hidra.notification.push.provider-reference:hidra-local-async-push}") String providerReference
    ) {
        this.providerReference = Objects.requireNonNull(providerReference, "Notification provider reference must not be null.");
    }

    @Override
    public NotificationDeliveryGatewayResult push(NotificationMessage message) {
        Objects.requireNonNull(message, "Notification message must not be null.");
        String providerMessageId = providerReference + "-" + UUID.randomUUID();
        LOGGER.info(
                "Accepted notification message {} for asynchronous push through provider {} as {}.",
                message.id(),
                providerReference,
                providerMessageId
        );
        return NotificationDeliveryGatewayResult.accepted(providerReference, providerMessageId);
    }
}
