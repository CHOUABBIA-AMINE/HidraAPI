/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : Username
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.domain.value
 *
 * @Description : Validated username value object.
 *
 */
package dz.sh.hidra.modules.identity.domain.value;

import dz.sh.hidra.kernel.domain.exception.InvalidValueObjectException;
import dz.sh.hidra.kernel.domain.model.ValueObject;

import java.util.Locale;
import java.util.regex.Pattern;

/**
 * Validated username for a HidraAPI login identity.
 *
 * <p>Business role: represents the human-readable login name used by an identity user.</p>
 *
 * <p>Architecture role: immutable domain value object that keeps username normalization
 * and validation inside the identity domain.</p>
 *
 * <p>Validation responsibility: rejects null, blank, too-short, too-long, or malformed
 * usernames. Usernames are normalized to lowercase.</p>
 *
 * <p>Usage: create with {@link #of(String)} before attaching the username to a user
 * aggregate or application command.</p>
 */
public record Username(String value) implements ValueObject {

    private static final int MIN_LENGTH = 3;
    private static final int MAX_LENGTH = 64;
    private static final Pattern USERNAME_PATTERN = Pattern.compile("^[a-z][a-z0-9._-]*$");

    public Username {
        if (value == null || value.isBlank()) {
            throw new InvalidValueObjectException("Username must not be blank.");
        }

        value = value.trim().toLowerCase(Locale.ROOT);

        if (value.length() < MIN_LENGTH || value.length() > MAX_LENGTH) {
            throw new InvalidValueObjectException("Username must contain between 3 and 64 characters.");
        }

        if (!USERNAME_PATTERN.matcher(value).matches()) {
            throw new InvalidValueObjectException(
                    "Username must start with a letter and contain only lowercase letters, digits, dots, underscores, or hyphens."
            );
        }
    }

    public static Username of(String value) {
        return new Username(value);
    }
}
