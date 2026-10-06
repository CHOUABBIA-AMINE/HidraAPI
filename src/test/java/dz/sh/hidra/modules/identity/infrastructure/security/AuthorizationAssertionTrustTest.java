/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AuthorizationAssertionTrustTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-06
 *
 * @Type        : Class
 * @Layer       : Identity Test
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.infrastructure.security
 *
 * @Description : Resolves bounded and explainable Identity authorization evidence.
 *
 */
package dz.sh.hidra.modules.identity.infrastructure.security;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import dz.sh.hidra.modules.identity.application.model.VerifiedAuthorizationAssertion;
import dz.sh.hidra.modules.identity.domain.model.HidraPrincipal;
import dz.sh.hidra.modules.identity.domain.value.ProviderType;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.oauth2.jwt.Jwt;
import java.time.Instant;
import java.util.*;

class AuthorizationAssertionTrustTest {
    private final HidraPrincipal principal=new HidraPrincipal("u","user","User",ProviderType.OIDC,"provider",Set.of(),Set.of(),"external");
    @AfterEach void cleanup() { SecurityContextHolder.clearContext(); }
    @Test void requestDetailsAndGenericClaimsCannotSupplyAssertionAuthority() {
        var auth=new UsernamePasswordAuthenticationToken(principal,null,List.of());
        auth.setDetails(Map.of("roles",List.of("admin"),"claims",Map.of("groups",List.of("admins"))));
        SecurityContextHolder.getContext().setAuthentication(auth);
        var adapter=new SpringAuthorizationContextAdapter(mock(EntityManager.class),true);
        assertNull(adapter.currentFor("u"));
        assertNull(adapter.currentFor("other"));
    }
    @Test void assertionMustBeAuthenticatedAndBelongToEvaluatedUser() {
        var assertion=new VerifiedAuthorizationAssertion("u","provider","external","subject",Instant.now().plusSeconds(60),Map.of("roles",Set.of("operator")));
        var token=new HidraOidcAuthenticationToken(principal,assertion);
        SecurityContextHolder.getContext().setAuthentication(token);
        var adapter=new SpringAuthorizationContextAdapter(mock(EntityManager.class),false);
        assertSame(assertion,adapter.currentFor("u"));
        assertNull(adapter.currentFor("other"));
        token.setAuthenticated(false); assertNull(adapter.currentFor("u"));
        assertThrows(IllegalArgumentException.class,()->new HidraOidcAuthenticationToken(principal,
                new VerifiedAuthorizationAssertion("other","provider","external","subject",null,Map.of())));
    }
    @Test @SuppressWarnings("unchecked") void capturesOnlyConfiguredClaimPathsAndNoBearerCredentials() {
        EntityManager em=mock(EntityManager.class);
        TypedQuery<String> query=mock(TypedQuery.class);
        when(em.createQuery(anyString(),eq(String.class))).thenReturn(query);
        when(query.setParameter(eq("provider"),eq("provider"))).thenReturn(query);
        when(query.getResultList()).thenReturn(List.of("realm_access.roles"));
        var jwt=Jwt.withTokenValue("sensitive-bearer").header("alg","RS256").subject("subject")
                .expiresAt(Instant.now().plusSeconds(60)).claim("realm_access",Map.of("roles",List.of("operator")))
                .claim("email","private@example.invalid").claim("groups",List.of("unconfigured-admins")).build();
        var assertion=new SpringAuthorizationContextAdapter(em,true).capture(principal,jwt);
        assertEquals(Map.of("realm_access.roles",Set.of("operator")),assertion.claims());
        assertNull(new HidraOidcAuthenticationToken(principal,assertion).getCredentials());
        assertThrows(UnsupportedOperationException.class,()->assertion.claims().put("roles",Set.of("admin")));
    }
}
