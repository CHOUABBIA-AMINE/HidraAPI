/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : CurrentSecurityContextTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Class
 * @Layer       : Platform Test
 * @Module      : platform
 * @Package     : dz.sh.hidra.platform.security.context
 *
 * @Description : Verifies current security context behavior.
 *
 */
package dz.sh.hidra.platform.security.context;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

import static org.assertj.core.api.Assertions.assertThat;

class CurrentSecurityContextTest {

    private final CurrentSecurityContext currentSecurityContext = new CurrentSecurityContext();

    @AfterEach
    void clear() {
        SecurityContextHolder.clearContext();
    }

    @Test
    void shouldHandleUnauthenticatedStateSafely() {
        assertThat(currentSecurityContext.currentPrincipal()).isEmpty();
        assertThat(currentSecurityContext.isAuthenticated()).isFalse();
    }

    @Test
    void shouldResolveAuthenticatedPrincipal() {
        TestingAuthenticationToken authentication = new TestingAuthenticationToken("actor-1", "n/a");
        authentication.setAuthenticated(true);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        assertThat(currentSecurityContext.currentPrincipal()).hasValueSatisfying(principal ->
                assertThat(principal.actorId().value()).isEqualTo("actor-1"));
    }
}
