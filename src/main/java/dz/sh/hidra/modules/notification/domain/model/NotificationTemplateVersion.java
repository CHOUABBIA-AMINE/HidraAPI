/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : NotificationTemplateVersion
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : notification
 * @Package     : dz.sh.hidra.modules.notification.domain.model
 *
 * @Description : Immutable notification template version.
 *
 */
package dz.sh.hidra.modules.notification.domain.model;

import dz.sh.hidra.modules.notification.domain.value.*;
import java.time.Instant;

    /**
     * Immutable notification template version.
     *
         * @param id id
     * @param templateId templateId
     * @param versionNumber versionNumber
     * @param status status
     * @param subjectTemplate subjectTemplate
     * @param bodyTemplate bodyTemplate
     * @param contentFormat contentFormat
     * @param variableSchemaJson variableSchemaJson
     * @param createdByActorId createdByActorId
     * @param createdByDisplayNameSnapshot createdByDisplayNameSnapshot
     * @param createdAt createdAt
     * @param activatedAt activatedAt
     * @param retiredAt retiredAt
     */
    public record NotificationTemplateVersion(
            String id,
        String templateId,
        int versionNumber,
        NotificationTemplateVersionStatus status,
        String subjectTemplate,
        String bodyTemplate,
        NotificationContentFormat contentFormat,
        String variableSchemaJson,
        String createdByActorId,
        String createdByDisplayNameSnapshot,
        Instant createdAt,
        Instant activatedAt,
        Instant retiredAt
    ) {

        public NotificationTemplateVersion {
        id = normalize(id);
        templateId = normalize(templateId);
        subjectTemplate = normalize(subjectTemplate);
        bodyTemplate = normalize(bodyTemplate);
        variableSchemaJson = normalize(variableSchemaJson);
        createdByActorId = normalize(createdByActorId);
        createdByDisplayNameSnapshot = normalize(createdByDisplayNameSnapshot);
        }

        private static String normalize(String value) {
            if (value == null || value.isBlank()) {
                return null;
            }
            return value.trim();
        }
    }
