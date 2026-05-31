/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : PermissionCodePolicy
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Class
 * @Layer       : Domain
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.domain.policy
 *
 * @Description : Domain policy validating identity permission code structure.
 *
 */
package dz.sh.hidra.modules.identity.domain.policy;

import dz.sh.hidra.kernel.domain.exception.InvalidValueObjectException;
import dz.sh.hidra.modules.identity.domain.value.PermissionCode;

import java.util.Locale;
import java.util.regex.Pattern;

/**
 * Domain policy for validating permission code structure.
 *
 * <p>Business role: protects the identity permission catalog by enforcing the canonical
 * business permission format {@code context:resource:action}.</p>
 *
 * <p>Architecture role: pure domain policy with no Spring Security, persistence, or
 * platform dependencies. It complements the {@link PermissionCode} value object and can
 * be reused by domain models and application services.</p>
 *
 * <p>Validation responsibility: requires exactly three lowercase parts separated by two
 * colon characters. Each part must start with a lowercase letter and may contain
 * lowercase letters, digits, or hyphens.</p>
 *
 * <p>Usage: call {@link #validate(String)} when accepting raw permission-code text, or
 * {@link #requireValid(PermissionCode)} when guarding already-created permission code
 * values.</p>
 */
public final class PermissionCodePolicy {

    private static final Pattern PERMISSION_CODE_PATTERN =
            Pattern.compile("^[a-z][a-z0-9-]*:[a-z][a-z0-9-]*:[a-z][a-z0-9-]*$");

    private PermissionCodePolicy() {
    }

    public static PermissionCode validate(String rawValue) {
        String normalizedValue = normalize(rawValue);

        if (!PERMISSION_CODE_PATTERN.matcher(normalizedValue).matches()) {
            throw new InvalidValueObjectException(
                    "PermissionCode must use the lowercase context:resource:action format."
            );
        }

        return PermissionCode.of(normalizedValue);
    }

    public static void requireValid(PermissionCode permissionCode) {
        if (permissionCode == null) {
            throw new InvalidValueObjectException("PermissionCode must not be null.");
        }
        validate(permissionCode.value());
    }

    public static boolean isValid(String rawValue) {
        try {
            validate(rawValue);
            return true;
        } catch (InvalidValueObjectException exception) {
            return false;
        }
    }

    public static String context(PermissionCode permissionCode) {
        requireValid(permissionCode);
        return permissionCode.context();
    }

    public static String resource(PermissionCode permissionCode) {
        requireValid(permissionCode);
        return permissionCode.resource();
    }

    public static String action(PermissionCode permissionCode) {
        requireValid(permissionCode);
        return permissionCode.action();
    }

    private static String normalize(String rawValue) {
        if (rawValue == null || rawValue.isBlank()) {
            throw new InvalidValueObjectException("PermissionCode must not be blank.");
        }

        return rawValue.trim().toLowerCase(Locale.ROOT);
    }
}
