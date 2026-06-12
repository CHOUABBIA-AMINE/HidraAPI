/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : RecordDeliveryAttemptCommand
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Application
 * @Module      : notification
 * @Package     : dz.sh.hidra.modules.notification.application.command
 *
 * @Description : Command to record a notification delivery attempt.
 *
 */
package dz.sh.hidra.modules.notification.application.command;

import dz.sh.hidra.modules.notification.domain.value.DeliveryAttemptStatus;

import java.time.Instant;

/**
 * Command to record a notification delivery attempt.
 */
public record RecordDeliveryAttemptCommand(
        String messageId,
        int attemptNumber,
        String channelId,
        String providerReference,
        String providerMessageId,
        DeliveryAttemptStatus attemptStatus,
        Instant attemptedAt,
        Instant completedAt,
        String failureCode,
        String failureMessage,
        Instant nextRetryAt,
        String correlationId
) {
}
