/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : HidraAuthenticationManagerConfigurationTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-19
 *
 * @Type        : Class
 * @Layer       : Test
 * @Module      : platform
 * @Package     : dz.sh.hidra.platform.configuration
 *
 * @Description : Verifies provider supports isolation, deterministic dispatch, and fail-closed authentication-manager behavior.
 *
 */
package dz.sh.hidra.platform.configuration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import dz.sh.hidra.platform.security.LdapAuthenticationToken;
import dz.sh.hidra.platform.security.LocalAuthenticationToken;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.ProviderNotFoundException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

class HidraAuthenticationManagerConfigurationTest {

    private final HidraAuthenticationManagerConfiguration configuration =
            new HidraAuthenticationManagerConfiguration();

    @Test
    void dispatchesLocalAndLdapRequestsOnlyToMatchingProviders() {
        AuthenticationProvider localProvider = mock(AuthenticationProvider.class);
        AuthenticationProvider ldapProvider = mock(AuthenticationProvider.class);
        when(localProvider.supports(LocalAuthenticationToken.class)).thenReturn(true);
        when(localProvider.supports(LdapAuthenticationToken.class)).thenReturn(false);
        when(ldapProvider.supports(LocalAuthenticationToken.class)).thenReturn(false);
        when(ldapProvider.supports(LdapAuthenticationToken.class)).thenReturn(true);

        LocalAuthenticationToken localRequest = LocalAuthenticationToken.unauthenticated("local-user", "secret");
        LdapAuthenticationToken ldapRequest = LdapAuthenticationToken.unauthenticated("ldap-user", "secret");
        Authentication localResult = UsernamePasswordAuthenticationToken.authenticated("local-user", null, java.util.List.of());
        Authentication ldapResult = UsernamePasswordAuthenticationToken.authenticated("ldap-user", null, java.util.List.of());
        when(localProvider.authenticate(localRequest)).thenReturn(localResult);
        when(ldapProvider.authenticate(ldapRequest)).thenReturn(ldapResult);

        AuthenticationManager manager = configuration.hidraAuthenticationManager(
                provider(localProvider, ldapProvider)
        );

        assertThat(manager.authenticate(localRequest)).isSameAs(localResult);
        assertThat(manager.authenticate(ldapRequest)).isSameAs(ldapResult);
        verify(localProvider).authenticate(localRequest);
        verify(ldapProvider).authenticate(ldapRequest);
        verify(localProvider, never()).authenticate(ldapRequest);
        verify(ldapProvider, never()).authenticate(localRequest);
    }

    @Test
    void failedSelectedLocalProviderDoesNotFallThroughToLdapProvider() {
        AuthenticationProvider localProvider = mock(AuthenticationProvider.class);
        AuthenticationProvider ldapProvider = mock(AuthenticationProvider.class);
        when(localProvider.supports(LocalAuthenticationToken.class)).thenReturn(true);
        when(localProvider.supports(LdapAuthenticationToken.class)).thenReturn(false);
        when(ldapProvider.supports(LocalAuthenticationToken.class)).thenReturn(false);
        when(ldapProvider.supports(LdapAuthenticationToken.class)).thenReturn(true);

        LocalAuthenticationToken request = LocalAuthenticationToken.unauthenticated("operator", "wrong-secret");
        when(localProvider.authenticate(request)).thenThrow(new BadCredentialsException("bad credentials"));

        AuthenticationManager manager = configuration.hidraAuthenticationManager(
                provider(localProvider, ldapProvider)
        );

        assertThatThrownBy(() -> manager.authenticate(request))
                .isInstanceOf(BadCredentialsException.class)
                .hasMessage("bad credentials");
        verify(localProvider).authenticate(request);
        verify(ldapProvider, never()).authenticate(request);
    }

    @Test
    void rejectsProviderThatClaimsBothHidraRequestTokenTypes() {
        AuthenticationProvider ambiguousProvider = mock(AuthenticationProvider.class);
        when(ambiguousProvider.supports(LocalAuthenticationToken.class)).thenReturn(true);
        when(ambiguousProvider.supports(LdapAuthenticationToken.class)).thenReturn(true);

        assertThatThrownBy(() -> configuration.hidraAuthenticationManager(provider(ambiguousProvider)))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("must support only one provider-specific request token type");
    }

    @Test
    void failsClosedWhenNoHidraProviderSupportsRequestType() {
        AuthenticationProvider unrelatedProvider = mock(AuthenticationProvider.class);
        when(unrelatedProvider.supports(LocalAuthenticationToken.class)).thenReturn(false);
        when(unrelatedProvider.supports(LdapAuthenticationToken.class)).thenReturn(false);

        AuthenticationManager manager = configuration.hidraAuthenticationManager(provider(unrelatedProvider));
        Authentication unsupportedRequest = UsernamePasswordAuthenticationToken.unauthenticated("operator", "secret");

        assertThatThrownBy(() -> manager.authenticate(unsupportedRequest))
                .isInstanceOf(ProviderNotFoundException.class);
        verify(unrelatedProvider, never()).authenticate(unsupportedRequest);
    }

    @SafeVarargs
    @SuppressWarnings("unchecked")
    private static ObjectProvider<AuthenticationProvider> provider(AuthenticationProvider... providers) {
        ObjectProvider<AuthenticationProvider> objectProvider = mock(ObjectProvider.class);
        when(objectProvider.orderedStream()).thenAnswer(invocation -> Stream.of(providers));
        return objectProvider;
    }
}
