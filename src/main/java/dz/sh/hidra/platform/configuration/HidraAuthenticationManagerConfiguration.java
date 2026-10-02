/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : HidraAuthenticationManagerConfiguration
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-15
 *
 * @Type        : Class
 * @Layer       : Platform
 * @Module      : platform
 * @Package     : dz.sh.hidra.platform.configuration
 *
 * @Description : Composes provider-specific Hidra authentication strategies behind one fail-closed ProviderManager.
 *
 */
package dz.sh.hidra.platform.configuration;

import dz.sh.hidra.platform.security.LdapAuthenticationToken;
import dz.sh.hidra.platform.security.LocalAuthenticationToken;
import java.util.List;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.ProviderNotFoundException;

/**
 * Composes Hidra's provider-specific direct-authentication strategies without using
 * provider ordering as the provider discriminator.
 */
@Configuration(proxyBeanMethods = false)
public class HidraAuthenticationManagerConfiguration {

    @Bean
    AuthenticationManager hidraAuthenticationManager(
            ObjectProvider<AuthenticationProvider> authenticationProviders
    ) {
        List<AuthenticationProvider> hidraProviders = authenticationProviders.orderedStream()
                .filter(HidraAuthenticationManagerConfiguration::supportsHidraRequest)
                .toList();

        AuthenticationManager failClosedParent = authentication -> {
            throw new ProviderNotFoundException(
                    "No Hidra authentication provider supports request type: "
                            + authentication.getClass().getName()
            );
        };

        ProviderManager providerManager = new ProviderManager(hidraProviders, failClosedParent);
        providerManager.setEraseCredentialsAfterAuthentication(true);
        return providerManager;
    }

    private static boolean supportsHidraRequest(AuthenticationProvider provider) {
        boolean supportsLocal = provider.supports(LocalAuthenticationToken.class);
        boolean supportsLdap = provider.supports(LdapAuthenticationToken.class);

        if (supportsLocal && supportsLdap) {
            throw new IllegalStateException(
                    "A Hidra AuthenticationProvider must support only one provider-specific request token type."
            );
        }

        return supportsLocal || supportsLdap;
    }
}
