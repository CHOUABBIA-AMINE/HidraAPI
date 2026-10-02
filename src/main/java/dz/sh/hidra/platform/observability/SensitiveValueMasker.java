/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : SensitiveValueMasker
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Platform
 * @Module      : platform
 * @Package     : dz.sh.hidra.platform.observability
 *
 * @Description : Masks sensitive technical log values.
 *
 */
package dz.sh.hidra.platform.observability;

import java.util.Locale;
import java.util.Set;

/**
 * Masks sensitive values before they are stored in logs.
 */
public final class SensitiveValueMasker {

    private static final String MASK = "****";

    private static final Set<String> SENSITIVE_TOKENS = Set.of(
            "password",
            "secret",
            "token",
            "authorization",
            "credential",
            "apikey",
            "api_key"
    );

    private SensitiveValueMasker() {
        throw new UnsupportedOperationException("Utility class must not be instantiated.");
    }

    public static String maskIfSensitive(String key, String value) {
        if (value == null) {
            return null;
        }
        if (isSensitiveKey(key)) {
            return MASK;
        }
        return value;
    }

    public static boolean isSensitiveKey(String key) {
        if (key == null || key.isBlank()) {
            return false;
        }
        String normalized = key.toLowerCase(Locale.ROOT).replace("-", "").replace("_", "");
        return SENSITIVE_TOKENS.stream().anyMatch(normalized::contains);
    }
}
