/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : HidraAdministratorJwtAuthorizationTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-19
 *
 * @Type        : Class
 * @Layer       : Platform Test
 * @Module      : platform
 * @Package     : dz.sh.hidra.platform.configuration
 *
 * @Description : Proves signed Hidra administrator JWTs still require a live administrator grant at the route boundary.
 *
 */
package dz.sh.hidra.platform.configuration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import dz.sh.hidra.modules.identity.application.port.in.IdentityAdministrationQueryUseCase;
import dz.sh.hidra.modules.identity.domain.model.HidraPrincipal;
import dz.sh.hidra.modules.identity.domain.value.ProviderType;
import dz.sh.hidra.modules.identity.infrastructure.security.HidraAccessTokenIssuer;
import dz.sh.hidra.modules.identity.infrastructure.security.IdentityAdministratorGrantService;
import dz.sh.hidra.modules.identity.infrastructure.security.IdentityEffectivePermissionSourceAdapter;
import dz.sh.hidra.platform.security.HidraEffectivePermissionResolver;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.Authentication;

class HidraAdministratorJwtAuthorizationTest {

    @Test
    void localAdministratorJwtIsAuthorizedOnlyWhileDatabaseGrantRemainsValid() {
        String userId = "local-administrator";
        var encoder = new HidraJwtEncoderConfiguration()
                .hidraJwtEncoder("0123456789abcdef0123456789abcdef");
        var decoder = new HidraJwtDecoderConfiguration()
                .hidraJwtDecoder("0123456789abcdef0123456789abcdef", "hidra-api", "hidra-api");
        var converter = new HidraSecurityConfiguration()
                .hidraJwtAuthenticationConverter("sub", "roles", "scope", "ROLE_");
        var issuer = new HidraAccessTokenIssuer(
                encoder, "hidra-api", "hidra-api", "sub", "roles", "scope", 900);
        var principal = new HidraPrincipal(userId, "local-admin", "Local admin",
                ProviderType.LOCAL, "local-provider", Set.of("HIDRA_ADMIN"), Set.of());

        var jwt = decoder.decode(issuer.issue(principal).tokenValue());
        Authentication authentication = converter.convert(jwt);
        assertThat(jwt.getClaimAsStringList("roles")).containsExactly("HIDRA_ADMIN");
        assertThat(authentication.getAuthorities()).extracting(a -> a.getAuthority())
                .contains("ROLE_HIDRA_ADMIN");

        AtomicBoolean activeGrant = new AtomicBoolean(true);
        IdentityAdministratorGrantService liveGrants = mock(IdentityAdministratorGrantService.class);
        when(liveGrants.hasActiveGlobalAdministratorGrant(userId))
                .thenAnswer(invocation -> activeGrant.get());
        IdentityAdministrationQueryUseCase query = mock(IdentityAdministrationQueryUseCase.class);
        when(query.principal(userId, List.of())).thenReturn(
                new IdentityAdministrationQueryUseCase.PrincipalView(userId, "IDENTITY_USER",
                        userId, "local-admin", "Local admin", null, List.of(), List.of()));
        var resolver = new HidraEffectivePermissionResolver(List.of(
                new IdentityEffectivePermissionSourceAdapter(query, liveGrants)));

        assertThat(resolver.hasPermission(authentication, "security:permissions:read")).isTrue();
        activeGrant.set(false);
        assertThat(resolver.hasPermission(authentication, "security:permissions:read")).isFalse();
    }

    @Test
    void ordinaryUserCannotUseWildcardEvenIfPermissionSourceReturnsIt() {
        var resolver = new HidraEffectivePermissionResolver(List.of(principal -> Set.of("*")));
        Authentication operator = org.springframework.security.authentication.UsernamePasswordAuthenticationToken
                .authenticated("operator", null, List.of());
        assertThat(resolver.hasPermission(operator, "security:permissions:read")).isFalse();
    }
}
