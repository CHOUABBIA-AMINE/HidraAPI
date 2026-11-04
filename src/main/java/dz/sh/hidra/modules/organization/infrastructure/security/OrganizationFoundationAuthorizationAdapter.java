/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : OrganizationFoundationAuthorizationAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-04
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization.application.port.out
 *
 * @Description : Coordinates the bounded Organization foundation contract.
 *
 */
package dz.sh.hidra.modules.organization.infrastructure.security;

import dz.sh.hidra.modules.organization.application.port.out.OrganizationFoundationAuthorizationPort;
import dz.sh.hidra.modules.identity.application.port.in.IdentityAdministrationQueryUseCase;
import dz.sh.hidra.platform.security.HidraEffectivePermissionResolver;
import org.springframework.stereotype.Component;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.access.AccessDeniedException;
@Component
public final class OrganizationFoundationAuthorizationAdapter implements OrganizationFoundationAuthorizationPort {
    private final HidraEffectivePermissionResolver permissions;
    private final IdentityAdministrationQueryUseCase users;
    public OrganizationFoundationAuthorizationAdapter(HidraEffectivePermissionResolver permissions,IdentityAdministrationQueryUseCase users) {
        this.permissions=java.util.Objects.requireNonNull(permissions);this.users=java.util.Objects.requireNonNull(users);
    }
    @Override public String administrator() {
        var current=SecurityContextHolder.getContext().getAuthentication();
        if(!(current instanceof JwtAuthenticationToken bearer)||!current.isAuthenticated()
                ||bearer.getToken().getClaimAsString("authentication_type")==null
                ||!permissions.resolve(current).contains(HidraEffectivePermissionResolver.ALL_PERMISSIONS))
            throw new AccessDeniedException("Current Organization administrator authority required.");
        return current.getName();
    }
    @Override public void requireActiveIdentity(String reference) {
        administrator();
        if(reference==null)return;
        if(reference.isBlank()||!reference.equals(reference.trim())||reference.length()>120)throw new IllegalArgumentException("Exact Identity user reference required.");
        var user=users.user(reference);
        if(user==null||!reference.equals(user.id())||!"ACTIVE".equals(user.status()))
            throw new IllegalStateException("Referenced Identity user must be active.");
    }
}
