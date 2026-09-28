/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : NotificationPolicy
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-28
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : notification
 * @Package     : dz.sh.hidra.modules.notification.domain.model
 *
 * @Description : Rules controlling delivery behavior for a category or source module.
 *
 */
package dz.sh.hidra.modules.notification.domain.model;

import dz.sh.hidra.modules.notification.domain.exception.InvalidNotificationValueException;
import java.time.Instant;

    /**
     * Rules controlling delivery behavior for a category or source module.
     *
         * @param id id
     * @param code code
     * @param nameAr nameAr
     * @param nameFr nameFr
     * @param nameEn nameEn
     * @param sourceModule sourceModule
     * @param categoryId categoryId
     * @param priorityId priorityId
     * @param defaultTemplateId defaultTemplateId
     * @param defaultChannelId defaultChannelId
     * @param recipientResolutionMode recipientResolutionMode
     * @param allowPreferenceOverride allowPreferenceOverride
     * @param allowQuietHourDelay allowQuietHourDelay
     * @param requiresAcknowledgement requiresAcknowledgement
     * @param maxRetryCount maxRetryCount
     * @param retryPolicyId retryPolicyId
     * @param active active
     * @param createdAt createdAt
     * @param updatedAt updatedAt
     */
    public record NotificationPolicy(
            String id,
        String code,
        String nameAr,
        String nameFr,
        String nameEn,
        String sourceModule,
        String categoryId,
        String priorityId,
        String defaultTemplateId,
        String defaultChannelId,
        String recipientResolutionMode,
        boolean allowPreferenceOverride,
        boolean allowQuietHourDelay,
        boolean requiresAcknowledgement,
        Integer maxRetryCount,
        String retryPolicyId,
        boolean active,
        Instant createdAt,
        Instant updatedAt
    ) {

        public NotificationPolicy {
        // HRA-051 required: id
        if (id == null || id.isBlank()) {
            throw new InvalidNotificationValueException("NotificationPolicy id must not be blank.");
        }
        // HRA-051 required: code
        if (code == null || code.isBlank()) {
            throw new InvalidNotificationValueException("NotificationPolicy code must not be blank.");
        }
        // HRA-051 required: categoryId
        if (categoryId == null || categoryId.isBlank()) {
            throw new InvalidNotificationValueException("NotificationPolicy category id must not be blank.");
        }

        id = normalize(id);
        code = normalize(code);
        nameAr = normalize(nameAr);
        nameFr = normalize(nameFr);
        nameEn = normalize(nameEn);
        sourceModule = normalize(sourceModule);
        categoryId = normalize(categoryId);
        priorityId = normalize(priorityId);
        defaultTemplateId = normalize(defaultTemplateId);
        defaultChannelId = normalize(defaultChannelId);
        recipientResolutionMode = normalize(recipientResolutionMode);
        retryPolicyId = normalize(retryPolicyId);
        }

        private static String normalize(String value) {
            if (value == null || value.isBlank()) {
                return null;
            }
            return value.trim();
        }
    }
