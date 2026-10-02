/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : NotificationRequestSummaryDto
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Application
 * @Module      : notification
 * @Package     : dz.sh.hidra.modules.notification.application.dto
 *
 * @Description : Notification request summary DTO.
 *
 */
package dz.sh.hidra.modules.notification.application.dto;

import dz.sh.hidra.modules.notification.domain.value.NotificationRequestStatus;

import java.time.Instant;

/**
 * Notification request summary DTO.
 */
public record NotificationRequestSummaryDto(
        String id,
        String sourceModule,
        String sourceEventType,
        String sourceEventId,
        String targetType,
        String targetId,
        NotificationRequestStatus status,
        String correlationId,
        Instant requestedAt
) {
}
