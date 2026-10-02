/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : HseId
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : hse
 * @Package     : dz.sh.hidra.modules.hse.domain.value
 *
 * @Description : Stable HSE identifier.
 *
 */
package dz.sh.hidra.modules.hse.domain.value;

import dz.sh.hidra.modules.hse.domain.exception.InvalidHseValueException;

import java.util.UUID;

/**
 * Stable HSE identifier.
 *
 * @param value identifier value
 */
public record HseId(String value) {

    public HseId {
        value = requireText(value, "HSE ID must not be null or blank.");
    }

    public static HseId of(String value) {
        return new HseId(value);
    }

    public static HseId newId() {
        return new HseId(UUID.randomUUID().toString());
    }

    private static String requireText(String value, String message) {
        if (value == null || value.isBlank()) {
            throw new InvalidHseValueException(message);
        }
        return value.trim();
    }
}
