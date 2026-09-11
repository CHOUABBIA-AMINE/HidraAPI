/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : HidraEffectivePermissionResolverTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-11
 *
 * @Type        : Class
 * @Layer       : Platform Test
 * @Module      : platform
 * @Package     : dz.sh.hidra.platform.security
 *
 * @Description : Verifies effective permissions from OAuth scopes, identity sources, and bootstrap administration.
 *
 */
package dz.sh.hidra.platform.security;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.Test;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

class HidraEffectivePermissionResolverTest {

    @Test
    void resolvesScopesAndIdentitySpecificPermissions() {
        HidraEffectivePermissionResolver resolver = new HidraEffectivePermissionResolver(List.of(
                principal -> "alice".equals(principal) ? Set.of("workflow:tasks:read") : Set.of()
        ));
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                "alice",
                "n/a",
                List.of(new SimpleGrantedAuthority("SCOPE_telemetry:points:read"))
        );

        Set<String> permissions = resolver.resolve(authentication);

        assertEquals(Set.of("telemetry:points:read", "workflow:tasks:read"), permissions);
    }

    @Test
    void bootstrapAdminReceivesWildcardWithoutInventingBusinessPermissions() {
        HidraEffectivePermissionResolver resolver = new HidraEffectivePermissionResolver(List.of());
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                "hidra-admin",
                "n/a",
                List.of(new SimpleGrantedAuthority("ROLE_HIDRA_ADMIN"))
        );

        assertTrue(resolver.resolve(authentication).contains(HidraEffectivePermissionResolver.ALL_PERMISSIONS));
    }
}
