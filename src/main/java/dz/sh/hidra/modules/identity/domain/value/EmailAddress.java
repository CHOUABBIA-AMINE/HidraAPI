/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : EmailAddress
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.domain.value
 *
 * @Description : Represents a normalized optional email address.
 *
 */
package dz.sh.hidra.modules.identity.domain.value;

import dz.sh.hidra.modules.identity.domain.exception.InvalidIdentityValueException;

import java.util.Locale;

/**
 * Normalized email address.
 *
 * @param value email value
 */
public record EmailAddress(String value) {

    public EmailAddress {
        value = normalize(value);
    }

    public static EmailAddress of(String value) {
        return new EmailAddress(value);
    }

    private static String normalize(String value) {
        if (value == null || value.isBlank()) {
            throw new InvalidIdentityValueException("Email address must not be null or blank.");
        }
        String normalized = value.trim().toLowerCase(Locale.ROOT);
        if (!normalized.contains("@")) {
            throw new InvalidIdentityValueException("Email address must contain '@'.");
        }
        return normalized;
    }
}
