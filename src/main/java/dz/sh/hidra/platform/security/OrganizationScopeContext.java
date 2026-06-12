/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : OrganizationScopeContext
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Utility
 * @Layer       : Platform
 * @Module      : platform
 * @Package     : dz.sh.hidra.platform.security
 *
 * @Description : Stores the current technical organization scope in thread-local context.
 *
 */
package dz.sh.hidra.platform.security;

import java.util.Optional;

/**
 * Thread-local holder for the current technical organization-scope identifier.
 */
public final class OrganizationScopeContext {

    private static final ThreadLocal<String> CURRENT_SCOPE = new ThreadLocal<>();

    private OrganizationScopeContext() {
        throw new UnsupportedOperationException("Utility class must not be instantiated.");
    }

    public static void set(String scopeId) {
        String normalized = normalize(scopeId);
        if (normalized == null) {
            clear();
        } else {
            CURRENT_SCOPE.set(normalized);
        }
    }

    public static Optional<String> current() {
        return Optional.ofNullable(CURRENT_SCOPE.get());
    }

    public static String currentOrDefault(String defaultScopeId) {
        return current().orElse(defaultScopeId);
    }

    public static void clear() {
        CURRENT_SCOPE.remove();
    }

    private static String normalize(String value) {
        if (value == null || value.isBlank()) {
            return null;
        }
        return value.trim();
    }
}
