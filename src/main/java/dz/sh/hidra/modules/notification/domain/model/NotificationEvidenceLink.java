/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : NotificationEvidenceLink
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-28
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : notification
 * @Package     : dz.sh.hidra.modules.notification.domain.model
 *
 * @Description : Evidence reference linking notification to audit, workflow, or source objects.
 *
 */
package dz.sh.hidra.modules.notification.domain.model;

import dz.sh.hidra.modules.notification.domain.exception.InvalidNotificationValueException;
import java.time.Instant;

    /**
     * Evidence reference linking notification to audit, workflow, or source objects.
     *
         * @param id id
     * @param notificationRequestId notificationRequestId
     * @param messageId messageId
     * @param evidenceType evidenceType
     * @param referenceModule referenceModule
     * @param referenceType referenceType
     * @param referenceId referenceId
     * @param referenceCodeSnapshot referenceCodeSnapshot
     * @param createdAt createdAt
     */
    public record NotificationEvidenceLink(
            String id,
        String notificationRequestId,
        String messageId,
        String evidenceType,
        String referenceModule,
        String referenceType,
        String referenceId,
        String referenceCodeSnapshot,
        Instant createdAt
    ) {

        public NotificationEvidenceLink {
        // HRA-051 required: id
        if (id == null || id.isBlank()) {
            throw new InvalidNotificationValueException("NotificationEvidenceLink id must not be blank.");
        }
        // HRA-051 required: referenceId
        if (referenceId == null || referenceId.isBlank()) {
            throw new InvalidNotificationValueException("NotificationEvidenceLink reference id must not be blank.");
        }

        id = normalize(id);
        notificationRequestId = normalize(notificationRequestId);
        messageId = normalize(messageId);
        evidenceType = normalize(evidenceType);
        referenceModule = normalize(referenceModule);
        referenceType = normalize(referenceType);
        referenceId = normalize(referenceId);
        referenceCodeSnapshot = normalize(referenceCodeSnapshot);
        }

        private static String normalize(String value) {
            if (value == null || value.isBlank()) {
                return null;
            }
            return value.trim();
        }
    }
