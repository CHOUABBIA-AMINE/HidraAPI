/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AsyncNotificationPushAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-13
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : notification
 * @Package     : dz.sh.hidra.modules.notification.infrastructure.messaging
 *
 * @Description : Asynchronously pushes notification messages and records delivery attempts.
 *
 */
package dz.sh.hidra.modules.notification.infrastructure.messaging;

import dz.sh.hidra.modules.notification.application.dto.NotificationDeliveryGatewayResult;
import dz.sh.hidra.modules.notification.application.port.out.NotificationAsyncPushPort;
import dz.sh.hidra.modules.notification.application.port.out.NotificationDeliveryAttemptRepositoryPort;
import dz.sh.hidra.modules.notification.application.port.out.NotificationDeliveryGatewayPort;
import dz.sh.hidra.modules.notification.application.port.out.NotificationMessageRepositoryPort;
import dz.sh.hidra.modules.notification.domain.model.NotificationDeliveryAttempt;
import dz.sh.hidra.modules.notification.domain.model.NotificationMessage;
import dz.sh.hidra.modules.notification.domain.value.DeliveryAttemptStatus;
import dz.sh.hidra.modules.notification.domain.value.NotificationId;
import dz.sh.hidra.modules.notification.domain.value.NotificationMessageStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Objects;

/**
 * Asynchronously pushes notification messages and records delivery attempts.
 */
@Component
public class AsyncNotificationPushAdapter implements NotificationAsyncPushPort {

    private static final Logger LOGGER = LoggerFactory.getLogger(AsyncNotificationPushAdapter.class);

    private final NotificationDeliveryGatewayPort deliveryGatewayPort;
    private final NotificationDeliveryAttemptRepositoryPort deliveryAttemptRepositoryPort;
    private final NotificationMessageRepositoryPort messageRepositoryPort;
    private final boolean enabled;

    public AsyncNotificationPushAdapter(
            NotificationDeliveryGatewayPort deliveryGatewayPort,
            NotificationDeliveryAttemptRepositoryPort deliveryAttemptRepositoryPort,
            NotificationMessageRepositoryPort messageRepositoryPort,
            @Value("${hidra.notification.async-push.enabled:true}") boolean enabled
    ) {
        this.deliveryGatewayPort = Objects.requireNonNull(deliveryGatewayPort, "Notification delivery gateway port must not be null.");
        this.deliveryAttemptRepositoryPort = Objects.requireNonNull(deliveryAttemptRepositoryPort, "Notification delivery attempt repository port must not be null.");
        this.messageRepositoryPort = Objects.requireNonNull(messageRepositoryPort, "Notification message repository port must not be null.");
        this.enabled = enabled;
    }

    @Override
    @Async("notificationPushExecutor")
    public void pushAsync(NotificationMessage message) {
        Objects.requireNonNull(message, "Notification message must not be null.");
        if (!enabled) {
            LOGGER.debug("Asynchronous notification push is disabled; message {} remains queued for later delivery.", message.id());
            return;
        }
        Instant startedAt = Instant.now();
        NotificationMessage dispatching = withStatus(message, NotificationMessageStatus.DISPATCHING, startedAt);
        messageRepositoryPort.save(dispatching);
        try {
            NotificationDeliveryGatewayResult result = deliveryGatewayPort.push(dispatching);
            DeliveryAttemptStatus attemptStatus = result.accepted() ? DeliveryAttemptStatus.SENT : DeliveryAttemptStatus.FAILED_TEMPORARY;
            NotificationMessageStatus messageStatus = result.accepted() ? NotificationMessageStatus.SENT : NotificationMessageStatus.RETRY_PENDING;
            Instant completedAt = Instant.now();
            messageRepositoryPort.save(withStatus(dispatching, messageStatus, completedAt));
            saveAttempt(dispatching, result, attemptStatus, startedAt, completedAt);
        } catch (RuntimeException ex) {
            Instant failedAt = Instant.now();
            messageRepositoryPort.save(withStatus(dispatching, NotificationMessageStatus.RETRY_PENDING, failedAt));
            saveAttempt(
                    dispatching,
                    NotificationDeliveryGatewayResult.rejected("hidra-notification-push", ex.getClass().getSimpleName(), ex.getMessage()),
                    DeliveryAttemptStatus.FAILED_TEMPORARY,
                    startedAt,
                    failedAt
            );
            LOGGER.warn("Asynchronous notification push failed for message {}.", message.id(), ex);
        }
    }

    private NotificationMessage withStatus(
            NotificationMessage source,
            NotificationMessageStatus status,
            Instant updatedAt
    ) {
        return new NotificationMessage(
                source.id(),
                source.requestId(),
                source.recipientId(),
                source.channelId(),
                source.templateId(),
                source.templateVersionId(),
                source.locale(),
                source.subjectRendered(),
                source.bodyRendered(),
                source.shortTextRendered(),
                source.payloadHash(),
                source.priorityId(),
                status,
                source.scheduledAt(),
                source.expiresAt(),
                source.createdAt(),
                updatedAt
        );
    }

    private void saveAttempt(
            NotificationMessage message,
            NotificationDeliveryGatewayResult result,
            DeliveryAttemptStatus status,
            Instant attemptedAt,
            Instant completedAt
    ) {
        NotificationDeliveryAttempt attempt = new NotificationDeliveryAttempt(
                NotificationId.newId().value(),
                message.id(),
                1,
                message.channelId(),
                result.providerReference(),
                result.providerMessageId(),
                status,
                attemptedAt,
                completedAt,
                result.failureCode(),
                result.failureMessage(),
                status == DeliveryAttemptStatus.FAILED_TEMPORARY ? completedAt.plusSeconds(60) : null,
                null,
                Instant.now()
        );
        deliveryAttemptRepositoryPort.save(attempt);
    }
}
