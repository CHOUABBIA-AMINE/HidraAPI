/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : IdentityCode
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.domain.value
 *
 * @Description : Represents a stable normalized identity code.
 *
 */
package dz.sh.hidra.modules.identity.domain.value;

import dz.sh.hidra.modules.identity.domain.exception.InvalidIdentityValueException;

import java.util.Locale;

/**
 * Stable normalized identity code.
 *
 * @param value normalized code value
 */
public record IdentityCode(String value) {

    public IdentityCode {
        value = normalize(value);
    }

    public static IdentityCode of(String value) {
        return new IdentityCode(value);
    }

    private static String normalize(String value) {
        if (value == null || value.isBlank()) {
            throw new InvalidIdentityValueException("Identity code must not be null or blank.");
        }
        return value.trim().toUpperCase(Locale.ROOT);
    }
}
