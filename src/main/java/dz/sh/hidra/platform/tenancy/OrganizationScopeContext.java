/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : OrganizationScopeContext
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Class
 * @Layer       : Platform
 * @Module      : platform
 * @Package     : dz.sh.hidra.platform.tenancy
 *
 * @Description : Holds the current technical organization scope identifier for the active thread.
 *
 */
package dz.sh.hidra.platform.tenancy;

import dz.sh.hidra.kernel.domain.value.OrganizationScopeId;
import java.util.Optional;

public final class OrganizationScopeContext {

    private static final ThreadLocal<OrganizationScopeId> CURRENT_SCOPE = new ThreadLocal<>();

    private OrganizationScopeContext() {
    }

    public static void set(OrganizationScopeId organizationScopeId) {
        if (organizationScopeId == null) {
            clear();
            return;
        }
        CURRENT_SCOPE.set(organizationScopeId);
    }

    public static void set(String organizationScopeId) {
        if (organizationScopeId == null || organizationScopeId.isBlank()) {
            clear();
            return;
        }
        set(OrganizationScopeId.of(organizationScopeId));
    }

    public static Optional<OrganizationScopeId> current() {
        return Optional.ofNullable(CURRENT_SCOPE.get());
    }

    public static OrganizationScopeId currentOrDefault(OrganizationScopeId defaultScopeId) {
        return current().orElse(defaultScopeId);
    }

    public static void clear() {
        CURRENT_SCOPE.remove();
    }
}
