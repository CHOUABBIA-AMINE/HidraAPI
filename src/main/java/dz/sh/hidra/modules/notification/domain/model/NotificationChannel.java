/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : NotificationChannel
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : notification
 * @Package     : dz.sh.hidra.modules.notification.domain.model
 *
 * @Description : Supported communication channel.
 *
 */
package dz.sh.hidra.modules.notification.domain.model;

import dz.sh.hidra.modules.notification.domain.value.*;
import java.time.Instant;

    /**
     * Supported communication channel.
     *
         * @param id id
     * @param code code
     * @param nameAr nameAr
     * @param nameFr nameFr
     * @param nameEn nameEn
     * @param channelType channelType
     * @param active active
     * @param providerReference providerReference
     * @param supportsDeliveryReceipt supportsDeliveryReceipt
     * @param supportsReadReceipt supportsReadReceipt
     * @param supportsHtml supportsHtml
     * @param supportsAttachments supportsAttachments
     * @param maxPayloadSize maxPayloadSize
     * @param createdAt createdAt
     * @param updatedAt updatedAt
     */
    public record NotificationChannel(
            String id,
        String code,
        String nameAr,
        String nameFr,
        String nameEn,
        NotificationChannelType channelType,
        boolean active,
        String providerReference,
        boolean supportsDeliveryReceipt,
        boolean supportsReadReceipt,
        boolean supportsHtml,
        boolean supportsAttachments,
        Integer maxPayloadSize,
        Instant createdAt,
        Instant updatedAt
    ) {

        public NotificationChannel {
        id = normalize(id);
        code = normalize(code);
        nameAr = normalize(nameAr);
        nameFr = normalize(nameFr);
        nameEn = normalize(nameEn);
        providerReference = normalize(providerReference);
        }

        private static String normalize(String value) {
            if (value == null || value.isBlank()) {
                return null;
            }
            return value.trim();
        }
    }
