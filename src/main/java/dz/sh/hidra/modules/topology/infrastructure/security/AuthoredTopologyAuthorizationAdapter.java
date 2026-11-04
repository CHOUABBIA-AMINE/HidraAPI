/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AuthoredTopologyAuthorizationAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-04
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.infrastructure.security
 *
 * @Description : Owns the bounded canonical Pipeline and persisted graph association contract.
 *
 */
package dz.sh.hidra.modules.topology.infrastructure.security;

import dz.sh.hidra.modules.topology.application.port.out.AuthoredTopologyAuthorizationPort;
import org.springframework.stereotype.Component;
import org.springframework.security.access.AccessDeniedException;
@Component
public final class AuthoredTopologyAuthorizationAdapter implements AuthoredTopologyAuthorizationPort {
    private final dz.sh.hidra.platform.security.HidraEffectivePermissionResolver permissions;
    public AuthoredTopologyAuthorizationAdapter(dz.sh.hidra.platform.security.HidraEffectivePermissionResolver permissions){this.permissions=java.util.Objects.requireNonNull(permissions);}
    /** Transport precondition only. The compiled graph owner independently proves native origin and current company/network rights. */
    @Override public void requireNativeAuthentication(){
        var current=org.springframework.security.core.context.SecurityContextHolder.getContext().getAuthentication();
        if(!(current instanceof org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken bearer)||!current.isAuthenticated()
                ||bearer.getToken().getClaimAsString("authentication_type")==null)throw new AccessDeniedException("Current native bearer required");
    }
    @Override public void requireLegacyMapAdministrator(){
        requireNativeAuthentication();var current=org.springframework.security.core.context.SecurityContextHolder.getContext().getAuthentication();
        if(current==null||!permissions.resolve(current).contains(dz.sh.hidra.platform.security.HidraEffectivePermissionResolver.ALL_PERMISSIONS))throw new AccessDeniedException("Current global map administrator required");
    }
}
