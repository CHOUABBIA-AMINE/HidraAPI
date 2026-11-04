/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ConfigurationAdministrationAuthorizationAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-04
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : configuration
 * @Package     : dz.sh.hidra.modules.configuration.infrastructure.security
 *
 * @Description : Implements the bounded typed configuration administration contract.
 *
 */
package dz.sh.hidra.modules.configuration.infrastructure.security;

import dz.sh.hidra.modules.configuration.application.port.out.ConfigurationAdministrationAuthorizationPort;
import dz.sh.hidra.platform.security.HidraEffectivePermissionResolver;
import org.springframework.stereotype.Component;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.access.AccessDeniedException;

@Component
public final class ConfigurationAdministrationAuthorizationAdapter implements ConfigurationAdministrationAuthorizationPort {
    private final HidraEffectivePermissionResolver permissions;
    public ConfigurationAdministrationAuthorizationAdapter(HidraEffectivePermissionResolver permissions) {this.permissions=java.util.Objects.requireNonNull(permissions);}
    @Override public String administrator() {
        var current=SecurityContextHolder.getContext().getAuthentication();
        if(!(current instanceof JwtAuthenticationToken bearer)||!current.isAuthenticated()
                ||bearer.getToken().getClaimAsString("authentication_type")==null
                ||bearer.getToken().getExpiresAt()==null||!bearer.getToken().getExpiresAt().isAfter(java.time.Instant.now())
                ||!permissions.resolve(current).contains(HidraEffectivePermissionResolver.ALL_PERMISSIONS))
            throw new AccessDeniedException("Current Configuration administrator authority required.");
        return current.getName();
    }
}
