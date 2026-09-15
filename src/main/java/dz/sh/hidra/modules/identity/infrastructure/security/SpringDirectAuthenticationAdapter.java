/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : SpringDirectAuthenticationAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-15
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.infrastructure.security
 *
 * @Description : Executes provider-selected direct authentication through the Hidra Spring Security request router and AuthenticationManager.
 *
 */
package dz.sh.hidra.modules.identity.infrastructure.security;

import dz.sh.hidra.modules.identity.application.port.out.DirectAuthenticationPort;
import dz.sh.hidra.modules.identity.domain.model.HidraPrincipal;
import dz.sh.hidra.modules.identity.domain.value.ProviderType;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

/**
 * Spring Security adapter for provider-selected LOCAL and LDAP/AD authentication.
 */
@Component
public final class SpringDirectAuthenticationAdapter implements DirectAuthenticationPort {

    private final IdentityAuthenticationRequestRouter authenticationRequestRouter;
    private final AuthenticationManager authenticationManager;

    public SpringDirectAuthenticationAdapter(
            IdentityAuthenticationRequestRouter authenticationRequestRouter,
            @Qualifier("hidraAuthenticationManager") AuthenticationManager authenticationManager
    ) {
        this.authenticationRequestRouter = Objects.requireNonNull(authenticationRequestRouter);
        this.authenticationManager = Objects.requireNonNull(authenticationManager);
    }

    @Override
    public HidraPrincipal authenticate(ProviderType providerType, String principal, String credentials) {
        Authentication request = authenticationRequestRouter.route(providerType, principal, credentials);
        Authentication authenticated = authenticationManager.authenticate(request);
        if (authenticated == null || !authenticated.isAuthenticated()) {
            throw new IllegalStateException("Direct authentication did not produce an authenticated result.");
        }
        if (!(authenticated.getPrincipal() instanceof HidraPrincipal hidraPrincipal)) {
            throw new IllegalStateException("Direct authentication did not produce a HidraPrincipal.");
        }
        return hidraPrincipal;
    }
}
