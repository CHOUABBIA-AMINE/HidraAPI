/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ReceiveNotificationRequestCommand
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Application
 * @Module      : notification
 * @Package     : dz.sh.hidra.modules.notification.application.command
 *
 * @Description : Command to receive a notification request.
 *
 */
package dz.sh.hidra.modules.notification.application.command;

import java.time.Instant;

/**
 * Command to receive a notification request.
 */
public record ReceiveNotificationRequestCommand(
        String sourceModule,
        String sourceEventType,
        String sourceEventId,
        String targetType,
        String targetId,
        String targetCodeSnapshot,
        String targetLabelSnapshot,
        String categoryId,
        String priorityId,
        String policyId,
        String templateId,
        String templateVersionId,
        String requestedByActorId,
        String requestedByDisplayNameSnapshot,
        String correlationId,
        String requestId,
        Instant expiresAt
) {
}
