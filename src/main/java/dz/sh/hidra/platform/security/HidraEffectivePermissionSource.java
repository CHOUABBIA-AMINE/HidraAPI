/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : HidraEffectivePermissionSource
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-11
 *
 * @Type        : Interface
 * @Layer       : Platform
 * @Module      : platform
 * @Package     : dz.sh.hidra.platform.security
 *
 * @Description : Defines the technical extension point used to resolve principal-specific effective permissions.
 *
 */
package dz.sh.hidra.platform.security;

import java.util.Set;

/**
 * Supplies effective Hidra permission codes for one authenticated principal.
 */
public interface HidraEffectivePermissionSource {

    Set<String> resolve(String principalName);
}
