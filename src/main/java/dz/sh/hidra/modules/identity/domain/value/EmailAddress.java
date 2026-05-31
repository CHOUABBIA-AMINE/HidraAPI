/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : EmailAddress
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.domain.value
 *
 * @Description : Validated email address value object.
 *
 */
package dz.sh.hidra.modules.identity.domain.value;

import dz.sh.hidra.kernel.domain.exception.InvalidValueObjectException;
import dz.sh.hidra.kernel.domain.model.ValueObject;

import java.util.Locale;
import java.util.regex.Pattern;

/**
 * Validated email address for an identity user.
 *
 * <p>Business role: represents the communication and login contact address associated
 * with a user identity.</p>
 *
 * <p>Architecture role: immutable domain value object that keeps email normalization
 * and format validation inside the identity domain.</p>
 *
 * <p>Validation responsibility: rejects null, blank, too-long, or malformed email
 * values. Email values are normalized to lowercase.</p>
 *
 * <p>Usage: create with {@link #of(String)} before assigning an email address to an
 * identity user.</p>
 */
public record EmailAddress(String value) implements ValueObject {

    private static final int MAX_LENGTH = 254;
    private static final Pattern EMAIL_PATTERN = Pattern.compile(
            "^[A-Za-z0-9.!#$%&'*+/=?^_`{|}~-]+@[A-Za-z0-9-]+(?:\\.[A-Za-z0-9-]+)+$"
    );

    public EmailAddress {
        if (value == null || value.isBlank()) {
            throw new InvalidValueObjectException("EmailAddress must not be blank.");
        }

        value = value.trim().toLowerCase(Locale.ROOT);

        if (value.length() > MAX_LENGTH) {
            throw new InvalidValueObjectException("EmailAddress must not exceed 254 characters.");
        }

        if (!EMAIL_PATTERN.matcher(value).matches()) {
            throw new InvalidValueObjectException("EmailAddress must use a valid email format.");
        }
    }

    public static EmailAddress of(String value) {
        return new EmailAddress(value);
    }
}
