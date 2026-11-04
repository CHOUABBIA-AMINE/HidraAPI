/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : IdentityLifecycleControllerTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-03
 *
 * @Type        : Class
 * @Layer       : API Test
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.api.rest.controller
 *
 * @Description : Verifies lifecycle HTTP DTO routing and live bearer authorization adaptation.
 *
 */
package dz.sh.hidra.modules.identity.api.rest.controller;

import dz.sh.hidra.modules.identity.api.rest.request.IdentityLifecycleRequests;
import dz.sh.hidra.modules.identity.application.port.in.FoundationIdentityLifecycleUseCase;
import dz.sh.hidra.modules.identity.application.port.out.AuthenticationSecurityStateQueryPort;
import dz.sh.hidra.modules.identity.application.port.out.OperationAuthenticationBindingRepositoryPort;
import dz.sh.hidra.modules.identity.domain.model.OperationAuthenticationBinding;
import dz.sh.hidra.modules.identity.infrastructure.security.FoundationIdentityAuthorizationAdapter;
import dz.sh.hidra.modules.identity.infrastructure.security.IdentityAdministratorGrantService;
import java.time.Instant;
import java.util.Optional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class IdentityLifecycleControllerTest {
    @AfterEach void cleanup() { SecurityContextHolder.clearContext(); }

    @Test void translatedOwnerConflictHasNonDisclosingHttp409() {
        var useCase = mock(FoundationIdentityLifecycleUseCase.class);
        var expected = Instant.parse("2026-11-04T08:00:00Z");
        when(useCase.deactivate("user-1",expected,"operator request")).thenThrow(
                new org.springframework.dao.InvalidDataAccessApiUsageException("private owner detail",
                        new IllegalStateException("Stale lifecycle version.")));
        var failure = assertThrows(org.springframework.web.server.ResponseStatusException.class,
                () -> new IdentityLifecycleController(useCase).deactivate("user-1",
                        new IdentityLifecycleRequests.Transition(expected,"operator request")));
        assertEquals(409,failure.getStatusCode().value());
        assertEquals("Identity lifecycle conflict.",failure.getReason());
        assertFalse(failure.getMessage().contains("private owner detail"));
    }

    @Test void activationUsesExplicitExpectedVersionAndReturnsNoSecret() {
        var useCase = mock(FoundationIdentityLifecycleUseCase.class);
        var controller = new IdentityLifecycleController(useCase);
        var expected = Instant.parse("2026-11-03T14:00:00Z");
        when(useCase.activate("user-1",expected,"Operator request")).thenReturn(
                new FoundationIdentityLifecycleUseCase.UserState("user-1","ACTIVE",expected.plusSeconds(1)));
        var result = controller.activate("user-1",new IdentityLifecycleRequests.Transition(expected,"Operator request"));
        assertEquals("ACTIVE",result.status());
        verify(useCase).activate("user-1",expected,"Operator request");
    }

    @Test void credentialResponseContainsOnlyGenerationAndDate() {
        var useCase = mock(FoundationIdentityLifecycleUseCase.class);
        var controller = new IdentityLifecycleController(useCase);
        when(useCase.credential(eq("user-1"),anyString(),eq(0L),eq("new user")))
                .thenReturn(new FoundationIdentityLifecycleUseCase.CredentialState("user-1",1,Instant.now()));
        var response = controller.credential("user-1",new IdentityLifecycleRequests.Credential(
                "secret-not-for-serialization",0,"new user"));
        assertEquals(1L,response.securityVersion());
        assertFalse(response.toString().contains("secret-not-for-serialization"));
    }

    @Test void nativeBearerMustHaveCurrentExactBindingAndLiveAdministratorGrant() {
        var bindings = mock(OperationAuthenticationBindingRepositoryPort.class);
        var states = mock(AuthenticationSecurityStateQueryPort.class);
        var admins = mock(IdentityAdministratorGrantService.class);
        var adapter = new FoundationIdentityAuthorizationAdapter(bindings,states,admins);
        var jwt = Jwt.withTokenValue("synthetic-signed-by-test-only").header("alg","HS256")
                .issuer("https://hidra.test").subject("admin-1").claim("jti","jti-1")
                .issuedAt(Instant.now().minusSeconds(30)).expiresAt(Instant.now().plusSeconds(60)).build();
        SecurityContextHolder.getContext().setAuthentication(new JwtAuthenticationToken(jwt));
        assertThrows(AccessDeniedException.class,adapter::authenticatedActor);
        verifyNoInteractions(bindings,states,admins);
        SecurityContextHolder.getContext().setAuthentication(new JwtAuthenticationToken(jwt,java.util.List.of()));
        assertThrows(AccessDeniedException.class,adapter::authenticatedActor);
        var binding = mock(OperationAuthenticationBinding.class);
        when(binding.userId()).thenReturn("admin-1");
        when(binding.nativeIssuer()).thenReturn("https://hidra.test");
        when(bindings.findByNativeIssuerAndJti("https://hidra.test","jti-1")).thenReturn(Optional.of(binding));
        when(states.current(eq(binding),any(Instant.class))).thenReturn(true);
        when(admins.hasActiveGlobalAdministratorGrant("admin-1")).thenReturn(true);
        assertEquals("admin-1",adapter.administratorActor());
        when(states.current(eq(binding),any(Instant.class))).thenReturn(false);
        assertThrows(AccessDeniedException.class,adapter::administratorActor);
    }
    @Test void malformedIssuerIsDeniedBeforeIdentityLookup() {
        var bindings = mock(OperationAuthenticationBindingRepositoryPort.class);
        var states = mock(AuthenticationSecurityStateQueryPort.class);
        var admins = mock(IdentityAdministratorGrantService.class);
        var queries = mock(dz.sh.hidra.modules.identity.application.port.in.IdentityAdministrationQueryUseCase.class);
        var lifecycle = new FoundationIdentityAuthorizationAdapter(bindings,states,admins);
        var permissions = new dz.sh.hidra.modules.identity.infrastructure.security.IdentityEffectivePermissionSourceAdapter(
                queries,admins,bindings,states);
        var jwt = Jwt.withTokenValue("synthetic-only").header("alg","HS256")
                .issuer("not-an-absolute-url").subject("admin-1").claim("jti","jti-1").build();
        SecurityContextHolder.getContext().setAuthentication(new JwtAuthenticationToken(jwt,java.util.List.of()));
        assertThrows(AccessDeniedException.class,lifecycle::authenticatedActor);
        assertThrows(AccessDeniedException.class,() -> permissions.resolve("admin-1"));
        verifyNoInteractions(bindings,states,admins,queries);
    }

    @Test void springClassProxiesCanWrapBothLifecycleBeans() {
        var service = new dz.sh.hidra.modules.identity.application.service.FoundationIdentityLifecycleApplicationService(
                mock(dz.sh.hidra.modules.identity.application.port.out.FoundationIdentityLifecyclePersistencePort.class),
                mock(dz.sh.hidra.modules.identity.application.port.out.FoundationIdentityAuthorizationPort.class),
                mock(org.springframework.security.crypto.password.PasswordEncoder.class));
        var persistence = new dz.sh.hidra.modules.identity.infrastructure.persistence.adapter.JdbcFoundationIdentityLifecycleAdapter(
                new org.springframework.jdbc.core.JdbcTemplate());
        for (Object target : new Object[] { service, persistence }) {
            var factory = new org.springframework.aop.framework.ProxyFactory(target);
            factory.setProxyTargetClass(true);
            factory.addAdvice((org.aopalliance.intercept.MethodInterceptor) invocation -> invocation.proceed());
            Object proxy = factory.getProxy();
            assertTrue(org.springframework.aop.support.AopUtils.isCglibProxy(proxy));
            assertNotSame(target, proxy);
        }
    }
}
