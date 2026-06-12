/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : NotificationApplicationService
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Application
 * @Module      : notification
 * @Package     : dz.sh.hidra.modules.notification.application.service
 *
 * @Description : Application service for notification requests, messages, and delivery attempts.
 *
 */
package dz.sh.hidra.modules.notification.application.service;

import dz.sh.hidra.modules.notification.application.command.CreateNotificationMessageCommand;
import dz.sh.hidra.modules.notification.application.command.ReceiveNotificationRequestCommand;
import dz.sh.hidra.modules.notification.application.command.RecordDeliveryAttemptCommand;
import dz.sh.hidra.modules.notification.application.dto.NotificationDeliveryAttemptSummaryDto;
import dz.sh.hidra.modules.notification.application.dto.NotificationMessageSummaryDto;
import dz.sh.hidra.modules.notification.application.dto.NotificationRequestSummaryDto;
import dz.sh.hidra.modules.notification.application.mapper.NotificationApplicationMapper;
import dz.sh.hidra.modules.notification.application.port.in.CreateNotificationMessageUseCase;
import dz.sh.hidra.modules.notification.application.port.in.ReceiveNotificationRequestUseCase;
import dz.sh.hidra.modules.notification.application.port.in.RecordDeliveryAttemptUseCase;
import dz.sh.hidra.modules.notification.application.port.out.NotificationDeliveryAttemptRepositoryPort;
import dz.sh.hidra.modules.notification.application.port.out.NotificationMessageRepositoryPort;
import dz.sh.hidra.modules.notification.application.port.out.NotificationRequestRepositoryPort;
import dz.sh.hidra.modules.notification.domain.model.NotificationDeliveryAttempt;
import dz.sh.hidra.modules.notification.domain.model.NotificationMessage;
import dz.sh.hidra.modules.notification.domain.model.NotificationRequest;
import dz.sh.hidra.modules.notification.domain.service.NotificationPayloadGuard;
import dz.sh.hidra.modules.notification.domain.value.NotificationId;
import dz.sh.hidra.modules.notification.domain.value.NotificationMessageStatus;
import dz.sh.hidra.modules.notification.domain.value.NotificationRequestStatus;

import java.time.Instant;
import java.util.Objects;

/**
 * Application service for notification requests, messages, and delivery attempts.
 */
public class NotificationApplicationService implements ReceiveNotificationRequestUseCase, CreateNotificationMessageUseCase, RecordDeliveryAttemptUseCase {

    private final NotificationRequestRepositoryPort requestRepositoryPort;
    private final NotificationMessageRepositoryPort messageRepositoryPort;
    private final NotificationDeliveryAttemptRepositoryPort deliveryAttemptRepositoryPort;
    private final NotificationPayloadGuard payloadGuard = new NotificationPayloadGuard();

    public NotificationApplicationService(
            NotificationRequestRepositoryPort requestRepositoryPort,
            NotificationMessageRepositoryPort messageRepositoryPort,
            NotificationDeliveryAttemptRepositoryPort deliveryAttemptRepositoryPort
    ) {
        this.requestRepositoryPort = Objects.requireNonNull(requestRepositoryPort, "Notification request repository port must not be null.");
        this.messageRepositoryPort = Objects.requireNonNull(messageRepositoryPort, "Notification message repository port must not be null.");
        this.deliveryAttemptRepositoryPort = Objects.requireNonNull(deliveryAttemptRepositoryPort, "Notification delivery attempt repository port must not be null.");
    }

    @Override
    public NotificationRequestSummaryDto receiveNotificationRequest(ReceiveNotificationRequestCommand command) {
        Objects.requireNonNull(command, "Receive notification request command must not be null.");
        payloadGuard.ensureNoBusinessOwnership(command.sourceEventType());
        Instant now = Instant.now();
        NotificationRequest request = new NotificationRequest(
                NotificationId.newId().value(),
                command.sourceModule(),
                command.sourceEventType(),
                command.sourceEventId(),
                command.targetType(),
                command.targetId(),
                command.targetCodeSnapshot(),
                command.targetLabelSnapshot(),
                command.categoryId(),
                command.priorityId(),
                command.policyId(),
                command.templateId(),
                command.templateVersionId(),
                command.requestedByActorId(),
                command.requestedByDisplayNameSnapshot(),
                now,
                command.correlationId(),
                command.requestId(),
                NotificationRequestStatus.RECEIVED,
                command.expiresAt(),
                now,
                now
        );
        return NotificationApplicationMapper.toSummary(requestRepositoryPort.save(request));
    }

    @Override
    public NotificationMessageSummaryDto createNotificationMessage(CreateNotificationMessageCommand command) {
        Objects.requireNonNull(command, "Create notification message command must not be null.");
        Instant now = Instant.now();
        NotificationMessage message = new NotificationMessage(
                NotificationId.newId().value(),
                command.requestId(),
                command.recipientId(),
                command.channelId(),
                command.templateId(),
                command.templateVersionId(),
                command.locale(),
                command.subjectRendered(),
                command.bodyRendered(),
                command.shortTextRendered(),
                command.payloadHash(),
                command.priorityId(),
                NotificationMessageStatus.DRAFT,
                command.scheduledAt(),
                command.expiresAt(),
                now,
                now
        );
        return NotificationApplicationMapper.toSummary(messageRepositoryPort.save(message));
    }

    @Override
    public NotificationDeliveryAttemptSummaryDto recordDeliveryAttempt(RecordDeliveryAttemptCommand command) {
        Objects.requireNonNull(command, "Record delivery attempt command must not be null.");
        payloadGuard.ensureNoProviderSecret(command.providerReference());
        Instant now = Instant.now();
        NotificationDeliveryAttempt attempt = new NotificationDeliveryAttempt(
                NotificationId.newId().value(),
                command.messageId(),
                command.attemptNumber(),
                command.channelId(),
                command.providerReference(),
                command.providerMessageId(),
                command.attemptStatus(),
                command.attemptedAt() == null ? now : command.attemptedAt(),
                command.completedAt(),
                command.failureCode(),
                command.failureMessage(),
                command.nextRetryAt(),
                command.correlationId(),
                now
        );
        return NotificationApplicationMapper.toSummary(deliveryAttemptRepositoryPort.save(attempt));
    }
}
