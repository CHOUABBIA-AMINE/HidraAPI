/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : NotificationSuppressionRule
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : notification
 * @Package     : dz.sh.hidra.modules.notification.domain.model
 *
 * @Description : Rule preventing or delaying delivery.
 *
 */
package dz.sh.hidra.modules.notification.domain.model;

import dz.sh.hidra.modules.notification.domain.value.*;
import java.time.Instant;

    /**
     * Rule preventing or delaying delivery.
     *
         * @param id id
     * @param code code
     * @param sourceModule sourceModule
     * @param categoryId categoryId
     * @param priorityId priorityId
     * @param channelId channelId
     * @param recipientType recipientType
     * @param recipientReferenceId recipientReferenceId
     * @param reasonId reasonId
     * @param active active
     * @param validFrom validFrom
     * @param validTo validTo
     * @param createdAt createdAt
     * @param updatedAt updatedAt
     */
    public record NotificationSuppressionRule(
            String id,
        String code,
        String sourceModule,
        String categoryId,
        String priorityId,
        String channelId,
        NotificationRecipientType recipientType,
        String recipientReferenceId,
        String reasonId,
        boolean active,
        Instant validFrom,
        Instant validTo,
        Instant createdAt,
        Instant updatedAt
    ) {

        public NotificationSuppressionRule {
        id = normalize(id);
        code = normalize(code);
        sourceModule = normalize(sourceModule);
        categoryId = normalize(categoryId);
        priorityId = normalize(priorityId);
        channelId = normalize(channelId);
        recipientReferenceId = normalize(recipientReferenceId);
        reasonId = normalize(reasonId);
        }

        private static String normalize(String value) {
            if (value == null || value.isBlank()) {
                return null;
            }
            return value.trim();
        }
    }
