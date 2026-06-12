/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : CreateNotificationMessageCommand
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Application
 * @Module      : notification
 * @Package     : dz.sh.hidra.modules.notification.application.command
 *
 * @Description : Command to create a notification message.
 *
 */
package dz.sh.hidra.modules.notification.application.command;

import java.time.Instant;

/**
 * Command to create a notification message.
 */
public record CreateNotificationMessageCommand(
        String requestId,
        String recipientId,
        String channelId,
        String templateId,
        String templateVersionId,
        String locale,
        String subjectRendered,
        String bodyRendered,
        String shortTextRendered,
        String payloadHash,
        String priorityId,
        Instant scheduledAt,
        Instant expiresAt
) {
}
