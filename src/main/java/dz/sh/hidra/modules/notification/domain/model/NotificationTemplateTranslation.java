/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : NotificationTemplateTranslation
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : notification
 * @Package     : dz.sh.hidra.modules.notification.domain.model
 *
 * @Description : Localized content for a template version.
 *
 */
package dz.sh.hidra.modules.notification.domain.model;

import java.time.Instant;

    /**
     * Localized content for a template version.
     *
         * @param id id
     * @param templateVersionId templateVersionId
     * @param locale locale
     * @param subject subject
     * @param body body
     * @param shortText shortText
     * @param createdAt createdAt
     * @param updatedAt updatedAt
     */
    public record NotificationTemplateTranslation(
            String id,
        String templateVersionId,
        String locale,
        String subject,
        String body,
        String shortText,
        Instant createdAt,
        Instant updatedAt
    ) {

        public NotificationTemplateTranslation {
        id = normalize(id);
        templateVersionId = normalize(templateVersionId);
        locale = normalize(locale);
        subject = normalize(subject);
        body = normalize(body);
        shortText = normalize(shortText);
        }

        private static String normalize(String value) {
            if (value == null || value.isBlank()) {
                return null;
            }
            return value.trim();
        }
    }
