/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : RoutePermissionDescriptor
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-13
 *
 * @Type        : Record
 * @Layer       : Platform
 * @Module      : platform
 * @Package     : dz.sh.hidra.platform.permissions
 *
 * @Description : Describes a route and its derived frontend permission metadata.
 *
 */
package dz.sh.hidra.platform.permissions;

import java.util.Set;

/**
 * Describes a route and its derived frontend permission metadata.
 */
public record RoutePermissionDescriptor(
        String route,
        Set<String> methods,
        String module,
        String resource,
        String action,
        String permission,
        String enforcementStatus
) { }
