/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : NotificationContactPoint
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-28
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

import dz.sh.hidra.modules.notification.domain.exception.InvalidNotificationValueException;
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
        // HRA-051 required: id
        if (id == null || id.isBlank()) {
            throw new InvalidNotificationValueException("NotificationContactPoint id must not be blank.");
        }
        // HRA-051 required: recipientProfileId
        if (recipientProfileId == null || recipientProfileId.isBlank()) {
            throw new InvalidNotificationValueException("NotificationContactPoint recipient profile id must not be blank.");
        }
        // HRA-051 required: channelId
        if (channelId == null || channelId.isBlank()) {
            throw new InvalidNotificationValueException("NotificationContactPoint channel id must not be blank.");
        }
        // HRA-051 order: validFrom <= validTo
        if (validFrom != null && validTo != null && validTo.isBefore(validFrom)) {
            throw new InvalidNotificationValueException("NotificationContactPoint valid to must not be before valid from.");
        }

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
