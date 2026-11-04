/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : PartyAdministrationAuthorizationAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-04
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : party
 * @Package     : dz.sh.hidra.modules.party.infrastructure.security
 *
 * @Description : Adapts existing native Identity-backed administrator meaning without private module access.
 *
 */
package dz.sh.hidra.modules.party.infrastructure.security;

import dz.sh.hidra.modules.party.application.port.out.PartyAdministrationAuthorizationPort;
import dz.sh.hidra.platform.security.HidraEffectivePermissionResolver;
import org.springframework.stereotype.Component;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.access.AccessDeniedException;
@Component
public final class PartyAdministrationAuthorizationAdapter implements PartyAdministrationAuthorizationPort {
    private final HidraEffectivePermissionResolver permissions;
    public PartyAdministrationAuthorizationAdapter(HidraEffectivePermissionResolver permissions) { this.permissions=java.util.Objects.requireNonNull(permissions); }
    @Override public String administrator() {
        var current=SecurityContextHolder.getContext().getAuthentication();
        if(!(current instanceof JwtAuthenticationToken bearer)||!current.isAuthenticated()
                ||bearer.getToken().getClaimAsString("authentication_type")==null
                ||!permissions.resolve(current).contains(HidraEffectivePermissionResolver.ALL_PERMISSIONS))
            throw new AccessDeniedException("Current Party administrator authority required.");
        return current.getName();
    }
}
