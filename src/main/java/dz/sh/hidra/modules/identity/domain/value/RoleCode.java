/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : RoleCode
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.domain.value
 *
 * @Description : Validated role code value object.
 *
 */
package dz.sh.hidra.modules.identity.domain.value;

import dz.sh.hidra.kernel.domain.exception.InvalidValueObjectException;
import dz.sh.hidra.kernel.domain.model.ValueObject;

import java.util.Locale;
import java.util.regex.Pattern;

/**
 * Validated technical code for an identity role.
 *
 * <p>Business role: provides a stable readable role code for role assignment and role
 * administration.</p>
 *
 * <p>Architecture role: immutable domain value object used by role models and role
 * assignment flows without coupling identity to platform security plumbing.</p>
 *
 * <p>Validation responsibility: rejects null, blank, too-short, too-long, or malformed
 * role codes. Role codes are normalized to uppercase.</p>
 *
 * <p>Usage: create with {@link #of(String)} before creating or searching for a role.</p>
 */
public record RoleCode(String value) implements ValueObject {

    private static final int MIN_LENGTH = 3;
    private static final int MAX_LENGTH = 64;
    private static final Pattern ROLE_CODE_PATTERN = Pattern.compile("^[A-Z][A-Z0-9_]*$");

    public RoleCode {
        if (value == null || value.isBlank()) {
            throw new InvalidValueObjectException("RoleCode must not be blank.");
        }

        value = value.trim().toUpperCase(Locale.ROOT);

        if (value.length() < MIN_LENGTH || value.length() > MAX_LENGTH) {
            throw new InvalidValueObjectException("RoleCode must contain between 3 and 64 characters.");
        }

        if (!ROLE_CODE_PATTERN.matcher(value).matches()) {
            throw new InvalidValueObjectException(
                    "RoleCode must start with an uppercase letter and contain only uppercase letters, digits, or underscores."
            );
        }
    }

    public static RoleCode of(String value) {
        return new RoleCode(value);
    }
}
