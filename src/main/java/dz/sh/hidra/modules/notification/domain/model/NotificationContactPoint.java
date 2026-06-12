/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : NotificationContactPoint
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : notification
 * @Package     : dz.sh.hidra.modules.notification.domain.model
 *
 * @Description : Delivery address and channel for a recipient profile.
 *
 */
package dz.sh.hidra.modules.notification.domain.model;

import java.time.Instant;

    /**
     * Delivery address and channel for a recipient profile.
     *
         * @param id id
     * @param recipientProfileId recipientProfileId
     * @param channelId channelId
     * @param addressValue addressValue
     * @param addressLabel addressLabel
     * @param verified verified
     * @param primaryForChannel primaryForChannel
     * @param active active
     * @param validFrom validFrom
     * @param validTo validTo
     * @param createdAt createdAt
     * @param updatedAt updatedAt
     */
    public record NotificationContactPoint(
            String id,
        String recipientProfileId,
        String channelId,
        String addressValue,
        String addressLabel,
        boolean verified,
        boolean primaryForChannel,
        boolean active,
        Instant validFrom,
        Instant validTo,
        Instant createdAt,
        Instant updatedAt
    ) {

        public NotificationContactPoint {
        id = normalize(id);
        recipientProfileId = normalize(recipientProfileId);
        channelId = normalize(channelId);
        addressValue = normalize(addressValue);
        addressLabel = normalize(addressLabel);
        }

        private static String normalize(String value) {
            if (value == null || value.isBlank()) {
                return null;
            }
            return value.trim();
        }
    }
