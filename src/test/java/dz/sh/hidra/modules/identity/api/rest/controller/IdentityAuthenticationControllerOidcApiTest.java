/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : IdentityAuthenticationControllerOidcApiTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-15
 *
 * @Type        : Class
 * @Layer       : Test
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.api.rest.controller
 *
 * @Description : Verifies the externally validated OIDC completion boundary preserves normalized Hidra identity and common token/session behavior.
 *
 */
package dz.sh.hidra.modules.identity.api.rest.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import dz.sh.hidra.modules.identity.api.rest.response.AuthenticationLoginResponse;
import dz.sh.hidra.modules.identity.application.model.AuthenticationCompletionResult;
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
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.security.access.AccessDeniedException;

class IdentityAuthenticationControllerOidcApiTest {

    @Test
    void oidcCompletionForwardsNormalizedPrincipalAndRequestMetadata() {
        AuthenticateDirectUserUseCase authenticateUseCase = mock(AuthenticateDirectUserUseCase.class);
        CompleteAuthenticatedPrincipalUseCase completionUseCase = mock(CompleteAuthenticatedPrincipalUseCase.class);
        IdentityAuthenticationController controller = new IdentityAuthenticationController(
                authenticateUseCase,
                completionUseCase
        );

        Instant issuedAt = Instant.parse("2026-09-15T09:00:00Z");
        Instant expiresAt = issuedAt.plusSeconds(900);
        HidraPrincipal principal = new HidraPrincipal(
                "user-oidc-1",
                "operator.oidc",
                "OIDC Operator",
                ProviderType.OIDC,
                "provider-oidc",
                Set.of(),
                Set.of("pipeline:read", "alarm:acknowledge")
        );
        LoginSession session = new LoginSession(
                "session-oidc-1",
                "user-oidc-1",
                "provider-oidc",
                "external-oidc-1",
                issuedAt,
                issuedAt,
                expiresAt,
                "10.20.0.7",
                "HidraWeb/2.0",
                LoginSessionStatus.ACTIVE,
                "corr-oidc-123"
        );
        IssuedAccessToken accessToken = new IssuedAccessToken(
                "hidra.oidc.jwt.value",
                "Bearer",
                "jti-oidc-1",
                issuedAt,
                expiresAt
        );
        when(completionUseCase.complete(
                principal,
                "10.20.0.7",
                "HidraWeb/2.0",
                "corr-oidc-123"
        )).thenReturn(new AuthenticationCompletionResult(principal, session, accessToken));

        MockHttpServletRequest servletRequest = new MockHttpServletRequest();
        servletRequest.setRemoteAddr("10.20.0.7");
        servletRequest.addHeader("User-Agent", "  HidraWeb/2.0  ");
        servletRequest.addHeader("X-Correlation-Id", "  corr-oidc-123  ");

        AuthenticationLoginResponse response = controller.completeOidc(principal, servletRequest);

        verify(completionUseCase).complete(
                principal,
                "10.20.0.7",
                "HidraWeb/2.0",
                "corr-oidc-123"
        );
        verify(authenticateUseCase, never()).authenticate(org.mockito.ArgumentMatchers.any());

        assertThat(response.sessionId()).isEqualTo("session-oidc-1");
        assertThat(response.accessToken()).isEqualTo("hidra.oidc.jwt.value");
        assertThat(response.tokenType()).isEqualTo("Bearer");
        assertThat(response.jti()).isEqualTo("jti-oidc-1");
        assertThat(response.userId()).isEqualTo("user-oidc-1");
        assertThat(response.username()).isEqualTo("operator.oidc");
        assertThat(response.displayName()).isEqualTo("OIDC Operator");
        assertThat(response.authenticationType()).isEqualTo(ProviderType.OIDC);
        assertThat(response.identityProviderId()).isEqualTo("provider-oidc");
        assertThat(response.roles()).isEmpty();
        assertThat(response.permissions()).containsExactlyInAnyOrder("pipeline:read", "alarm:acknowledge");
    }

    @Test
    void oidcCompletionRejectsMissingOrNonOidcHidraPrincipal() {
        AuthenticateDirectUserUseCase authenticateUseCase = mock(AuthenticateDirectUserUseCase.class);
        CompleteAuthenticatedPrincipalUseCase completionUseCase = mock(CompleteAuthenticatedPrincipalUseCase.class);
        IdentityAuthenticationController controller = new IdentityAuthenticationController(
                authenticateUseCase,
                completionUseCase
        );
        HidraPrincipal localPrincipal = new HidraPrincipal(
                "user-local-1",
                "operator.local",
                "Local Operator",
                ProviderType.LOCAL,
                "provider-local",
                Set.of(),
                Set.of("pipeline:read")
        );

        assertThatThrownBy(() -> controller.completeOidc(null, new MockHttpServletRequest()))
                .isInstanceOf(AccessDeniedException.class)
                .hasMessage("OIDC completion requires an externally validated OIDC Hidra principal.");
        assertThatThrownBy(() -> controller.completeOidc(localPrincipal, new MockHttpServletRequest()))
                .isInstanceOf(AccessDeniedException.class)
                .hasMessage("OIDC completion requires an externally validated OIDC Hidra principal.");

        verify(completionUseCase, never()).complete(
                org.mockito.ArgumentMatchers.any(),
                org.mockito.ArgumentMatchers.any(),
                org.mockito.ArgumentMatchers.any(),
                org.mockito.ArgumentMatchers.any()
        );
    }
}
