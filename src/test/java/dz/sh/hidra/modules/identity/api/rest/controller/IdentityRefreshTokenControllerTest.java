package dz.sh.hidra.modules.identity.api.rest.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import dz.sh.hidra.modules.identity.api.rest.request.RefreshTokenRequest;
import dz.sh.hidra.modules.identity.application.model.AuthenticationResult;
import dz.sh.hidra.modules.identity.application.port.in.*;
import dz.sh.hidra.modules.identity.domain.service.RefreshTokenRotationPolicy;
import dz.sh.hidra.modules.identity.domain.value.ProviderType;
import java.time.Instant;
import java.util.Set;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.server.ResponseStatusException;

class IdentityRefreshTokenControllerTest {
    private static final String SECRET="12345678-1234-1234-1234-123456789abc."
            +"AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA";
    @Test void refreshOnlyDelegatesOpaqueProofAndPreservesNewMetadata() {
        var direct=mock(AuthenticateDirectUserUseCase.class);
        var complete=mock(CompleteAuthenticatedPrincipalUseCase.class);
        var rotations=mock(RotateLocalRefreshTokenUseCase.class);
        Instant now=Instant.parse("2026-11-08T10:00:00Z");
        var issued=new AuthenticationResult("session","signedBearer","Bearer","jti",now,
                now.plusSeconds(900),"user","user",null,ProviderType.LOCAL,"p",
                Set.of(),Set.of(),"replacement",now.plusSeconds(604800));
        when(rotations.rotate(eq(SECRET),any(),any(),any())).thenReturn(issued);
        var controller=new IdentityAuthenticationController(direct,complete,rotations);
        var request=new MockHttpServletRequest();
        request.addHeader("X-Correlation-Id","correlation");
        request.addHeader("User-Agent","browser");
        var response=controller.refresh(new RefreshTokenRequest(SECRET),request);
        assertEquals("replacement",response.refreshToken());
        assertEquals(now.plusSeconds(604800),response.refreshExpiresAt());
        assertEquals("signedBearer",response.accessToken());
        verifyNoInteractions(direct,complete);
    }
    @Test void deniedAndReplayedTokensNeverExposeStoredPrincipalOrDigest() {
        var rotations=mock(RotateLocalRefreshTokenUseCase.class);
        var controller=new IdentityAuthenticationController(
                mock(AuthenticateDirectUserUseCase.class),
                mock(CompleteAuthenticatedPrincipalUseCase.class),rotations);
        when(rotations.rotate(eq(SECRET),any(),any(),any())).thenThrow(
                new RefreshTokenRotationPolicy.Replay());
        var ex=assertThrows(ResponseStatusException.class,
                ()->controller.refresh(new RefreshTokenRequest(SECRET),new MockHttpServletRequest()));
        assertEquals(HttpStatus.UNAUTHORIZED,ex.getStatusCode());
        assertFalse(ex.getMessage().contains(SECRET));
        verify(rotations).rotate(eq(SECRET),any(),any(),any());
    }
    @Test void logoutIsIdempotentAndDoesNotRequireBearer() {
        var rotations=mock(RotateLocalRefreshTokenUseCase.class);
        var controller=new IdentityAuthenticationController(
                mock(AuthenticateDirectUserUseCase.class),
                mock(CompleteAuthenticatedPrincipalUseCase.class),rotations);
        controller.logout(new RefreshTokenRequest(SECRET));
        controller.logout(new RefreshTokenRequest(SECRET));
        verify(rotations,times(2)).logout(SECRET);
        assertFalse(new RefreshTokenRequest(SECRET).toString().contains(SECRET));
    }
}
