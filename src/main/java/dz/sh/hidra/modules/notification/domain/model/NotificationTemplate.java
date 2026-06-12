/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : NotificationTemplate
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
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
