/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : PermissionCode
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.domain.value
 *
 * @Description : Validated permission code value object.
 *
 */
package dz.sh.hidra.modules.identity.domain.value;

import dz.sh.hidra.kernel.domain.exception.InvalidValueObjectException;
import dz.sh.hidra.kernel.domain.model.ValueObject;

import java.util.Locale;
import java.util.regex.Pattern;

/**
 * Validated permission code using the context:resource:action format.
 *
 * <p>Business role: represents the business access meaning granted to roles and
 * evaluated for users.</p>
 *
 * <p>Architecture role: immutable domain value object that keeps permission-code format
 * validation inside the identity domain instead of platform security plumbing.</p>
 *
 * <p>Validation responsibility: requires exactly three lowercase parts separated by two
 * colon characters. Each part may contain lowercase letters, digits, and hyphens.</p>
 *
 * <p>Usage: create with {@link #of(String)} before granting a permission to a role or
 * evaluating an access policy.</p>
 */
public record PermissionCode(String value) implements ValueObject {

    private static final Pattern PERMISSION_CODE_PATTERN =
            Pattern.compile("^[a-z][a-z0-9-]*:[a-z][a-z0-9-]*:[a-z][a-z0-9-]*$");

    public PermissionCode {
        if (value == null || value.isBlank()) {
            throw new InvalidValueObjectException("PermissionCode must not be blank.");
        }

        value = value.trim().toLowerCase(Locale.ROOT);

        if (!PERMISSION_CODE_PATTERN.matcher(value).matches()) {
            throw new InvalidValueObjectException(
                    "PermissionCode must use the lowercase context:resource:action format."
            );
        }
    }

    public static PermissionCode of(String value) {
        return new PermissionCode(value);
    }

    public String context() {
        return parts()[0];
    }

    public String resource() {
        return parts()[1];
    }

    public String action() {
        return parts()[2];
    }

    private String[] parts() {
        return value.split(":", -1);
    }
}
