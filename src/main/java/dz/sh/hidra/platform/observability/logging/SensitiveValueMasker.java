/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : SensitiveValueMasker
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Class
 * @Layer       : Platform
 * @Module      : platform
 * @Package     : dz.sh.hidra.platform.observability.logging
 *
 * @Description : Masks sensitive values before they are written to platform logs.
 *
 */
package dz.sh.hidra.platform.observability.logging;

import java.util.List;
import java.util.Locale;

public final class SensitiveValueMasker {

    public static final String MASKED_VALUE = "********";

    private static final List<String> SENSITIVE_KEY_PARTS = List.of(
            "password",
            "secret",
            "token",
            "authorization",
            "credential",
            "api-key",
            "apikey");

    private SensitiveValueMasker() {
    }

    public static String mask(String key, String value) {
        if (value == null) {
            return null;
        }
        if (isSensitiveKey(key)) {
            return MASKED_VALUE;
        }
        return value;
    }

    public static boolean isSensitiveKey(String key) {
        if (key == null || key.isBlank()) {
            return false;
        }
        String normalizedKey = key.toLowerCase(Locale.ROOT);
        return SENSITIVE_KEY_PARTS.stream().anyMatch(normalizedKey::contains);
    }
}
