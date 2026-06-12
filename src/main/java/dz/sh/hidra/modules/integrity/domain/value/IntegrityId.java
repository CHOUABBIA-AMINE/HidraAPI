/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : IntegrityId
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : integrity
 * @Package     : dz.sh.hidra.modules.integrity.domain.value
 *
 * @Description : Stable integrity identifier.
 *
 */
package dz.sh.hidra.modules.integrity.domain.value;

import dz.sh.hidra.modules.integrity.domain.exception.InvalidIntegrityValueException;

import java.util.UUID;

/**
 * Stable integrity identifier.
 *
 * @param value identifier value
 */
public record IntegrityId(String value) {

    public IntegrityId {
        value = requireText(value, "Integrity ID must not be null or blank.");
    }

    public static IntegrityId of(String value) {
        return new IntegrityId(value);
    }

    public static IntegrityId newId() {
        return new IntegrityId(UUID.randomUUID().toString());
    }

    private static String requireText(String value, String message) {
        if (value == null || value.isBlank()) {
            throw new InvalidIntegrityValueException(message);
        }
        return value.trim();
    }
}
