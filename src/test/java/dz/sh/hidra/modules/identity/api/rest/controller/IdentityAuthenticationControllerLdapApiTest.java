/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : IdentityAuthenticationControllerLdapApiTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-28
 *
 * @Type        : Class
 * @Layer       : Test
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.api.rest.controller
 *
 * @Description : Verifies LDAP and Active Directory direct-login requests preserve provider selection through application-owned results.
 *
 */
package dz.sh.hidra.modules.identity.api.rest.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import dz.sh.hidra.modules.identity.api.rest.request.AuthenticationLoginRequest;
import dz.sh.hidra.modules.identity.api.rest.response.AuthenticationLoginResponse;
import dz.sh.hidra.modules.identity.application.model.AuthenticationResult;
import dz.sh.hidra.modules.identity.application.model.DirectAuthenticationCommand;
import dz.sh.hidra.modules.identity.application.port.in.AuthenticateDirectUserUseCase;
import dz.sh.hidra.modules.identity.application.port.in.CompleteAuthenticatedPrincipalUseCase;
import dz.sh.hidra.modules.identity.domain.value.ProviderType;
import java.time.Instant;
import java.util.Set;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.mock.web.MockHttpServletRequest;

class IdentityAuthenticationControllerLdapApiTest {

    @Test
    void ldapLoginForwardsExplicitDirectoryProviderCredentialsAndMetadata() {
        assertDirectoryRequest(ProviderType.LDAP, "provider-ldap");
    }

    @Test
    void activeDirectoryLoginPreservesExplicitProviderSelection() {
        assertDirectoryRequest(ProviderType.ACTIVE_DIRECTORY, "provider-ad");
    }

    private static void assertDirectoryRequest(ProviderType providerType, String providerId) {
        AuthenticateDirectUserUseCase authenticateUseCase = mock(AuthenticateDirectUserUseCase.class);
        CompleteAuthenticatedPrincipalUseCase completionUseCase = mock(CompleteAuthenticatedPrincipalUseCase.class);
        IdentityAuthenticationController controller = new IdentityAuthenticationController(
                authenticateUseCase,
                completionUseCase
        );

        Instant issuedAt = Instant.parse("2026-09-15T10:00:00Z");
        Instant expiresAt = issuedAt.plusSeconds(900);
        when(authenticateUseCase.authenticate(org.mockito.ArgumentMatchers.any(DirectAuthenticationCommand.class)))
                .thenReturn(new AuthenticationResult(
                        "session-directory-1",
                        "hidra.directory.jwt",
                        "Bearer",
                        "jti-directory-1",
                        issuedAt,
                        expiresAt,
                        "user-directory-1",
                        "operator.directory",
                        "Directory Operator",
                        providerType,
                        providerId,
                        Set.of(),
                        Set.of("pipeline:read")
                ));

        MockHttpServletRequest servletRequest = new MockHttpServletRequest();
        servletRequest.setRemoteAddr("10.10.0.8");
        servletRequest.addHeader("User-Agent", "HidraWeb/1.0");
        servletRequest.addHeader("X-Correlation-Id", "corr-directory-1");

        AuthenticationLoginResponse response = controller.login(
                new AuthenticationLoginRequest(providerType, "operator.directory", "directory-secret"),
                servletRequest
        );

        ArgumentCaptor<DirectAuthenticationCommand> commandCaptor =
                ArgumentCaptor.forClass(DirectAuthenticationCommand.class);
        verify(authenticateUseCase).authenticate(commandCaptor.capture());

        assertThat(commandCaptor.getValue().providerType()).isEqualTo(providerType);
        assertThat(response.authenticationType()).isEqualTo(providerType);
        assertThat(response.identityProviderId()).isEqualTo(providerId);
        assertThat(response.userId()).isEqualTo("user-directory-1");
        assertThat(response.permissions()).containsExactly("pipeline:read");
    }
}
