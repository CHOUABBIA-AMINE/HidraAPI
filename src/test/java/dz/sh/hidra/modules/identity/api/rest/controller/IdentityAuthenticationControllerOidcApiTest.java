/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : IdentityAuthenticationControllerOidcApiTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-28
 *
 * @Type        : Class
 * @Layer       : Test
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.api.rest.controller
 *
 * @Description : Verifies Spring-injected OIDC identity is adapted to application-owned authentication input/result contracts.
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
import dz.sh.hidra.modules.identity.application.model.AuthenticatedPrincipalInput;
import dz.sh.hidra.modules.identity.application.model.AuthenticationResult;
import dz.sh.hidra.modules.identity.application.port.in.AuthenticateDirectUserUseCase;
import dz.sh.hidra.modules.identity.application.port.in.CompleteAuthenticatedPrincipalUseCase;
import dz.sh.hidra.modules.identity.domain.model.HidraPrincipal;
import dz.sh.hidra.modules.identity.domain.value.ProviderType;
import java.time.Instant;
import java.util.Set;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.security.access.AccessDeniedException;

class IdentityAuthenticationControllerOidcApiTest {

    @Test
    void oidcCompletionAdaptsSpringPrincipalToApplicationInputAndResult() {
        AuthenticateDirectUserUseCase authenticateUseCase = mock(AuthenticateDirectUserUseCase.class);
        CompleteAuthenticatedPrincipalUseCase completionUseCase = mock(CompleteAuthenticatedPrincipalUseCase.class);
        IdentityAuthenticationController controller = new IdentityAuthenticationController(
                authenticateUseCase,
                completionUseCase
        );

        Instant issuedAt = Instant.parse("2026-09-15T09:00:00Z");
        Instant expiresAt = issuedAt.plusSeconds(900);
        HidraPrincipal springPrincipal = new HidraPrincipal(
                "user-oidc-1",
                "operator.oidc",
                "OIDC Operator",
                ProviderType.OIDC,
                "provider-oidc",
                Set.of(),
                Set.of("pipeline:read", "alarm:acknowledge")
        );
        when(completionUseCase.complete(
                org.mockito.ArgumentMatchers.any(AuthenticatedPrincipalInput.class),
                org.mockito.ArgumentMatchers.eq("10.20.0.7"),
                org.mockito.ArgumentMatchers.eq("HidraWeb/2.0"),
                org.mockito.ArgumentMatchers.eq("corr-oidc-123")
        )).thenReturn(new AuthenticationResult(
                "session-oidc-1",
                "hidra.oidc.jwt.value",
                "Bearer",
                "jti-oidc-1",
                issuedAt,
                expiresAt,
                "user-oidc-1",
                "operator.oidc",
                "OIDC Operator",
                ProviderType.OIDC,
                "provider-oidc",
                Set.of(),
                Set.of("pipeline:read", "alarm:acknowledge")
        ));

        MockHttpServletRequest servletRequest = new MockHttpServletRequest();
        servletRequest.setRemoteAddr("10.20.0.7");
        servletRequest.addHeader("User-Agent", "  HidraWeb/2.0  ");
        servletRequest.addHeader("X-Correlation-Id", "  corr-oidc-123  ");

        AuthenticationLoginResponse response = controller.completeOidc(springPrincipal, servletRequest);

        ArgumentCaptor<AuthenticatedPrincipalInput> principalCaptor =
                ArgumentCaptor.forClass(AuthenticatedPrincipalInput.class);
        verify(completionUseCase).complete(
                principalCaptor.capture(),
                org.mockito.ArgumentMatchers.eq("10.20.0.7"),
                org.mockito.ArgumentMatchers.eq("HidraWeb/2.0"),
                org.mockito.ArgumentMatchers.eq("corr-oidc-123")
        );
        AuthenticatedPrincipalInput input = principalCaptor.getValue();
        assertThat(input.userId()).isEqualTo("user-oidc-1");
        assertThat(input.authenticationType()).isEqualTo(ProviderType.OIDC);
        assertThat(input.permissions()).containsExactlyInAnyOrder("pipeline:read", "alarm:acknowledge");
        verify(authenticateUseCase, never()).authenticate(org.mockito.ArgumentMatchers.any());

        assertThat(response.sessionId()).isEqualTo("session-oidc-1");
        assertThat(response.userId()).isEqualTo("user-oidc-1");
        assertThat(response.authenticationType()).isEqualTo(ProviderType.OIDC);
        assertThat(response.permissions()).containsExactlyInAnyOrder("pipeline:read", "alarm:acknowledge");
    }

    @Test
    void oidcCompletionRejectsMissingOrNonOidcSpringPrincipalBeforeApplicationCall() {
        CompleteAuthenticatedPrincipalUseCase completionUseCase = mock(CompleteAuthenticatedPrincipalUseCase.class);
        IdentityAuthenticationController controller = new IdentityAuthenticationController(
                mock(AuthenticateDirectUserUseCase.class),
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
