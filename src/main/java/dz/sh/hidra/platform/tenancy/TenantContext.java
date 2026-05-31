/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TenantContext
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Class
 * @Layer       : Platform
 * @Module      : platform
 * @Package     : dz.sh.hidra.platform.tenancy
 *
 * @Description : Holds the current technical tenant identifier for the active thread.
 *
 */
package dz.sh.hidra.platform.tenancy;

import java.util.Optional;

public final class TenantContext {

    private static final ThreadLocal<String> CURRENT_TENANT = new ThreadLocal<>();

    private TenantContext() {
    }

    public static void set(String tenantId) {
        if (tenantId == null || tenantId.isBlank()) {
            clear();
            return;
        }
        CURRENT_TENANT.set(tenantId.trim());
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
}
