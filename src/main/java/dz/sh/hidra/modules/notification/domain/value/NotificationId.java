/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : NotificationId
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : notification
 * @Package     : dz.sh.hidra.modules.notification.domain.value
 *
 * @Description : Stable notification identifier.
 *
 */
package dz.sh.hidra.modules.notification.domain.value;

import dz.sh.hidra.modules.notification.domain.exception.InvalidNotificationValueException;

import java.util.UUID;

/**
 * Stable notification identifier.
 *
 * @param value identifier value
 */
public record NotificationId(String value) {

    public NotificationId {
        value = requireText(value, "Notification ID must not be null or blank.");
    }

    public static NotificationId of(String value) {
        return new NotificationId(value);
    }

    public static NotificationId newId() {
        return new NotificationId(UUID.randomUUID().toString());
    }

    private static String requireText(String value, String message) {
        if (value == null || value.isBlank()) {
            throw new InvalidNotificationValueException(message);
        }
        return value.trim();
    }
}
