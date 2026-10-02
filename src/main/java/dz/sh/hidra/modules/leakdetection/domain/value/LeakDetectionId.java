/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : LeakDetectionId
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : leakdetection
 * @Package     : dz.sh.hidra.modules.leakdetection.domain.value
 *
 * @Description : Stable leak detection identifier.
 *
 */
package dz.sh.hidra.modules.leakdetection.domain.value;

import dz.sh.hidra.modules.leakdetection.domain.exception.InvalidLeakDetectionValueException;

import java.util.UUID;

/**
 * Stable leak detection identifier.
 *
 * @param value identifier value
 */
public record LeakDetectionId(String value) {

    public LeakDetectionId {
        value = requireText(value, "Leak detection ID must not be null or blank.");
    }

    public static LeakDetectionId of(String value) {
        return new LeakDetectionId(value);
    }

    public static LeakDetectionId newId() {
        return new LeakDetectionId(UUID.randomUUID().toString());
    }

    private static String requireText(String value, String message) {
        if (value == null || value.isBlank()) {
            throw new InvalidLeakDetectionValueException(message);
        }
        return value.trim();
    }
}
