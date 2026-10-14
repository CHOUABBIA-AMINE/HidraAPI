/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : SpringDirectAuthenticationAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-14
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

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * Spring Security adapter for provider-selected LOCAL and LDAP/AD authentication.
 */
@Component
public final class SpringDirectAuthenticationAdapter implements DirectAuthenticationPort {

    private final TrustedAuthenticationVerificationRegistry registry;
    private final IdentityAuthenticationRequestRouter authenticationRequestRouter;
    private final AuthenticationManager authenticationManager;

    public SpringDirectAuthenticationAdapter(
            IdentityAuthenticationRequestRouter authenticationRequestRouter,
            @Qualifier("hidraAuthenticationManager") AuthenticationManager authenticationManager,
            TrustedAuthenticationVerificationRegistry registry) {
        this.registry = Objects.requireNonNull(registry);
        this.authenticationRequestRouter = Objects.requireNonNull(authenticationRequestRouter);
        this.authenticationManager = Objects.requireNonNull(authenticationManager);
    }

    @Override
    public dz.sh.hidra.modules.identity.application.model.VerifiedDirectAuthenticationResult verify(
            ProviderType type, String principal, String credentials) {
        registry.begin();
        try {
            Authentication actual = authenticateResult(type, principal, credentials);
            if (((HidraPrincipal) actual.getPrincipal()).authenticationType() != type)
                throw new IllegalStateException(
                        "Verified directory type disagrees with selected provider.");
            return new dz.sh.hidra.modules.identity.application.model
                    .VerifiedDirectAuthenticationResult(
                    (HidraPrincipal) actual.getPrincipal(), registry.verifiedHandle(actual));
        } catch (RuntimeException | Error failure) {
            registry.close();
            throw failure;
        }
    }

    @Override
    public void finishVerification() {
        registry.close();
    }

    @Override
    public HidraPrincipal authenticate(ProviderType type, String principal, String credentials) {
        return (HidraPrincipal) authenticateResult(type, principal, credentials).getPrincipal();
    }

    private Authentication authenticateResult(
            ProviderType providerType, String principal, String credentials) {
        Authentication request =
                authenticationRequestRouter.route(providerType, principal, credentials);
        Authentication authenticated = authenticationManager.authenticate(request);
        if (authenticated == null || !authenticated.isAuthenticated()) {
            throw new IllegalStateException(
                    "Direct authentication did not produce an authenticated result.");
        }
        if (!(authenticated.getPrincipal() instanceof HidraPrincipal hidraPrincipal)) {
            throw new IllegalStateException(
                    "Direct authentication did not produce a HidraPrincipal.");
        }
        return authenticated;
    }
}
