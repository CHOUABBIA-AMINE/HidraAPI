/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TenantContext
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Platform
 * @Module      : platform
 * @Package     : dz.sh.hidra.platform.security
 *
 * @Description : Stores the current technical tenant identifier in thread-local context.
 *
 */
package dz.sh.hidra.platform.security;

import java.util.Optional;

/**
 * Thread-local holder for the current technical tenant identifier.
 */
public final class TenantContext {

    private static final ThreadLocal<String> CURRENT_TENANT = new ThreadLocal<>();

    private TenantContext() {
        throw new UnsupportedOperationException("Utility class must not be instantiated.");
    }

    public static void set(String tenantId) {
        String normalized = normalize(tenantId);
        if (normalized == null) {
            clear();
        } else {
            CURRENT_TENANT.set(normalized);
        }
    }

    public static Optional<String> current() {
        return Optional.ofNullable(CURRENT_TENANT.get());
    }

    public static String currentOrDefault(String defaultTenantId) {
        return current().orElse(defaultTenantId);
    }

    public static void clear() {
        CURRENT_TENANT.remove();
    }

    private static String normalize(String value) {
        if (value == null || value.isBlank()) {
            return null;
        }
        return value.trim();
    }
}
