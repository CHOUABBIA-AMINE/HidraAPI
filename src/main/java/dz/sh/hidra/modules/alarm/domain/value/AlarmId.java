/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AlarmId
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : alarm
 * @Package     : dz.sh.hidra.modules.alarm.domain.value
 *
 * @Description : Stable alarm identifier.
 *
 */
package dz.sh.hidra.modules.alarm.domain.value;

import dz.sh.hidra.modules.alarm.domain.exception.InvalidAlarmValueException;

import java.util.UUID;

/**
 * Stable alarm identifier.
 *
 * @param value identifier value
 */
public record AlarmId(String value) {

    public AlarmId {
        value = requireText(value, "Alarm ID must not be null or blank.");
    }

    public static AlarmId of(String value) {
        return new AlarmId(value);
    }

    public static AlarmId newId() {
        return new AlarmId(UUID.randomUUID().toString());
    }

    private static String requireText(String value, String message) {
        if (value == null || value.isBlank()) {
            throw new InvalidAlarmValueException(message);
        }
        return value.trim();
    }
}
