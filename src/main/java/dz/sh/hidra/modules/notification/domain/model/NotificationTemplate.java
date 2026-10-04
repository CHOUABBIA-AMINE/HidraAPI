/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : NotificationTemplate
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-04
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : notification
 * @Package     : dz.sh.hidra.modules.notification.domain.model
 *
 * @Description : Reusable notification template.
 *
 */
package dz.sh.hidra.modules.notification.domain.model;

import dz.sh.hidra.modules.notification.domain.exception.InvalidNotificationValueException;
import dz.sh.hidra.modules.notification.domain.value.*;
import java.time.Instant;

    /**
     * Reusable notification template.
     *
         * @param id id
     * @param code code
     * @param nameAr nameAr
     * @param nameFr nameFr
     * @param nameEn nameEn
     * @param templateTypeId templateTypeId
     * @param categoryId categoryId
     * @param defaultChannelId defaultChannelId
     * @param status status
     * @param currentVersion currentVersion
     * @param systemDefined systemDefined
     * @param createdAt createdAt
     * @param updatedAt updatedAt
     */
    public record NotificationTemplate(
            String id,
        String code,
        String nameAr,
        String nameFr,
        String nameEn,
        String templateTypeId,
        String categoryId,
        String defaultChannelId,
        NotificationTemplateStatus status,
        Integer currentVersion,
        boolean systemDefined,
        Instant createdAt,
        Instant updatedAt
    ) {

        public NotificationTemplate {
        // HRA-051 required: id
        if (id == null || id.isBlank()) {
            throw new InvalidNotificationValueException("NotificationTemplate id must not be blank.");
        }
        // HRA-051 required: code
        if (code == null || code.isBlank()) {
            throw new InvalidNotificationValueException("NotificationTemplate code must not be blank.");
        }
        if (nameFr == null || nameFr.isBlank()) {
            throw new InvalidNotificationValueException("NotificationTemplate French name must not be blank.");
        }
        // HRA-051 required: templateTypeId
        if (templateTypeId == null || templateTypeId.isBlank()) {
            throw new InvalidNotificationValueException("NotificationTemplate template type id must not be blank.");
        }
        // HRA-051 required: status
        if (status == null) {
            throw new InvalidNotificationValueException("NotificationTemplate status must not be null.");
        }
        if (currentVersion != null && currentVersion <= 0) {
            throw new InvalidNotificationValueException("NotificationTemplate current version must be positive.");
        }
        if (status == NotificationTemplateStatus.ACTIVE && currentVersion == null) {
            throw new InvalidNotificationValueException(
                    "ACTIVE NotificationTemplate must define a current version."
            );
        }
        if (createdAt == null || updatedAt == null) {
            throw new InvalidNotificationValueException(
                    "NotificationTemplate createdAt and updatedAt must not be null."
            );
        }

        id = normalize(id);
        code = normalize(code);
        nameAr = normalize(nameAr);
        nameFr = normalize(nameFr);
        nameEn = normalize(nameEn);
        templateTypeId = normalize(templateTypeId);
        categoryId = normalize(categoryId);
        defaultChannelId = normalize(defaultChannelId);
        }
        public boolean usableForNewMessages() {
            return status == NotificationTemplateStatus.ACTIVE;
        }
        private static String normalize(String value) {
            if (value == null || value.isBlank()) {
                return null;
            }
            return value.trim();
        }
    }
