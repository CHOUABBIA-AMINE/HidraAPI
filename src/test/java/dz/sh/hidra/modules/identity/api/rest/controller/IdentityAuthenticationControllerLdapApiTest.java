/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : IdentityAuthenticationControllerLdapApiTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-15
 *
 * @Type        : Class
 * @Layer       : Test
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.api.rest.controller
 *
 * @Description : Verifies LDAP and Active Directory direct-login API requests preserve explicit provider selection and normalized Hidra responses.
 *
 */
package dz.sh.hidra.modules.identity.api.rest.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import dz.sh.hidra.modules.identity.api.rest.request.AuthenticationLoginRequest;
import dz.sh.hidra.modules.identity.api.rest.response.AuthenticationLoginResponse;
import dz.sh.hidra.modules.identity.application.model.DirectAuthenticationCommand;
import dz.sh.hidra.modules.identity.application.model.DirectAuthenticationResult;
import dz.sh.hidra.modules.identity.application.model.IssuedAccessToken;
import dz.sh.hidra.modules.identity.application.port.in.AuthenticateDirectUserUseCase;
import dz.sh.hidra.modules.identity.application.port.in.CompleteAuthenticatedPrincipalUseCase;
import dz.sh.hidra.modules.identity.domain.model.HidraPrincipal;
import dz.sh.hidra.modules.identity.domain.model.LoginSession;
import dz.sh.hidra.modules.identity.domain.value.LoginSessionStatus;
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
        HidraPrincipal principal = new HidraPrincipal(
                "user-directory-1",
                "operator.directory",
                "Directory Operator",
                providerType,
                providerId,
                Set.of(),
                Set.of("pipeline:read")
        );
        LoginSession session = new LoginSession(
                "session-directory-1",
                "user-directory-1",
                providerId,
                null,
                issuedAt,
                issuedAt,
                expiresAt,
                "10.10.0.8",
                "HidraWeb/1.0",
                LoginSessionStatus.ACTIVE,
                "corr-directory-1"
        );
        IssuedAccessToken accessToken = new IssuedAccessToken(
                "hidra.directory.jwt",
                "Bearer",
                "jti-directory-1",
                issuedAt,
                expiresAt
        );
        when(authenticateUseCase.authenticate(org.mockito.ArgumentMatchers.any(DirectAuthenticationCommand.class)))
                .thenReturn(new DirectAuthenticationResult(principal, session, accessToken));

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
        DirectAuthenticationCommand command = commandCaptor.getValue();

        assertThat(command.providerType()).isEqualTo(providerType);
        assertThat(command.principal()).isEqualTo("operator.directory");
        assertThat(command.credentials()).isEqualTo("directory-secret");
        assertThat(command.clientIp()).isEqualTo("10.10.0.8");
        assertThat(command.userAgent()).isEqualTo("HidraWeb/1.0");
        assertThat(command.correlationId()).isEqualTo("corr-directory-1");

        assertThat(response.authenticationType()).isEqualTo(providerType);
        assertThat(response.identityProviderId()).isEqualTo(providerId);
        assertThat(response.userId()).isEqualTo("user-directory-1");
        assertThat(response.username()).isEqualTo("operator.directory");
        assertThat(response.permissions()).containsExactly("pipeline:read");
    }
}
