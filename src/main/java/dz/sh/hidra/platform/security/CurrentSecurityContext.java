/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : CurrentSecurityContext
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Interface
 * @Layer       : Platform
 * @Module      : platform
 * @Package     : dz.sh.hidra.platform.security
 *
 * @Description : Defines framework-neutral access to the current technical security principal.
 *
 */
package dz.sh.hidra.platform.security;

import java.util.Optional;

/**
 * Framework-neutral current security context access contract.
 */
public interface CurrentSecurityContext {

    Optional<AuthenticatedPrincipal> currentPrincipal();

    default boolean isAuthenticated() {
        return currentPrincipal()
                .map(AuthenticatedPrincipal::authenticated)
                .orElse(false);
    }

    void clear();
}
