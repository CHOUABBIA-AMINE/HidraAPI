/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : IdentityAuthenticationControllerLocalApiTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-28
 *
 * @Type        : Class
 * @Layer       : Test
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.api.rest.controller
 *
 * @Description : Verifies the LOCAL direct-login API boundary forwards request metadata and returns the application-owned login result.
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

class IdentityAuthenticationControllerLocalApiTest {

    @Test
    void localLoginForwardsExplicitProviderCredentialsAndRequestMetadata() {
        AuthenticateDirectUserUseCase authenticateUseCase = mock(AuthenticateDirectUserUseCase.class);
        CompleteAuthenticatedPrincipalUseCase completionUseCase = mock(CompleteAuthenticatedPrincipalUseCase.class);
        IdentityAuthenticationController controller = new IdentityAuthenticationController(
                authenticateUseCase,
                completionUseCase
        );

        Instant issuedAt = Instant.parse("2026-09-15T09:00:00Z");
        Instant expiresAt = issuedAt.plusSeconds(900);
        when(authenticateUseCase.authenticate(org.mockito.ArgumentMatchers.any(DirectAuthenticationCommand.class)))
                .thenReturn(new AuthenticationResult(
                "session-1",
                "hidra.jwt.value",
                "Bearer",
                "jti-1",
                issuedAt,
                expiresAt,
                "user-1",
                "operator",
                "Pipeline Operator",
                ProviderType.LOCAL,
                "provider-local",
                Set.of("HIDRA_ADMIN"),
                Set.of("pipeline:read")
        ));

        MockHttpServletRequest servletRequest = new MockHttpServletRequest();
        servletRequest.setRemoteAddr("10.10.0.5");
        servletRequest.addHeader("User-Agent", "  HidraWeb/1.0  ");
        servletRequest.addHeader("X-Correlation-Id", "  corr-123  ");

        AuthenticationLoginResponse response = controller.login(
                new AuthenticationLoginRequest(ProviderType.LOCAL, "operator", "correct-secret"),
                servletRequest
        );

        ArgumentCaptor<DirectAuthenticationCommand> commandCaptor =
                ArgumentCaptor.forClass(DirectAuthenticationCommand.class);
        verify(authenticateUseCase).authenticate(commandCaptor.capture());
        DirectAuthenticationCommand command = commandCaptor.getValue();

        assertThat(command.providerType()).isEqualTo(ProviderType.LOCAL);
        assertThat(command.principal()).isEqualTo("operator");
        assertThat(command.credentials()).isEqualTo("correct-secret");
        assertThat(command.clientIp()).isEqualTo("10.10.0.5");
        assertThat(command.userAgent()).isEqualTo("HidraWeb/1.0");
        assertThat(command.correlationId()).isEqualTo("corr-123");

        assertThat(response.sessionId()).isEqualTo("session-1");
        assertThat(response.accessToken()).isEqualTo("hidra.jwt.value");
        assertThat(response.authenticationType()).isEqualTo(ProviderType.LOCAL);
        assertThat(response.roles()).containsExactly("HIDRA_ADMIN");
        assertThat(response.permissions()).containsExactly("pipeline:read");
    }
}
