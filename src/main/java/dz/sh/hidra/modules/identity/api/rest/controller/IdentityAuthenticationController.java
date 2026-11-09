/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : IdentityAuthenticationController
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-28
 *
 * @Type        : Class
 * @Layer       : API
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.api.rest.controller
 *
 * @Description : Exposes direct login and externally validated OIDC completion through the Identity application boundary.
 *
 */
package dz.sh.hidra.modules.identity.api.rest.controller;

import dz.sh.hidra.modules.identity.api.rest.mapper.IdentityAuthenticationWebMapper;
import dz.sh.hidra.modules.identity.api.rest.request.AuthenticationLoginRequest;
import dz.sh.hidra.modules.identity.api.rest.request.RefreshTokenRequest;
import dz.sh.hidra.modules.identity.application.port.in.RotateLocalRefreshTokenUseCase;
import dz.sh.hidra.modules.identity.application.port.out.RefreshAttemptThrottlePort;
import dz.sh.hidra.modules.identity.domain.service.RefreshTokenRotationPolicy;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.bind.annotation.ResponseStatus;
import dz.sh.hidra.modules.identity.api.rest.response.AuthenticationLoginResponse;
import dz.sh.hidra.modules.identity.application.model.AuthenticatedPrincipalInput;
import dz.sh.hidra.modules.identity.application.model.AuthenticationResult;
import dz.sh.hidra.modules.identity.application.model.DirectAuthenticationCommand;
import dz.sh.hidra.modules.identity.application.port.in.AuthenticateDirectUserUseCase;
import dz.sh.hidra.modules.identity.application.port.in.CompleteAuthenticatedPrincipalUseCase;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import java.util.Objects;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Authentication HTTP boundary for direct credentials and externally validated OIDC identities.
 */
@RestController
@RequestMapping("/api/v1/identity/authentication")
public final class IdentityAuthenticationController {

    private final AuthenticateDirectUserUseCase authenticateDirectUserUseCase;
    private final CompleteAuthenticatedPrincipalUseCase completionUseCase;
    private final RotateLocalRefreshTokenUseCase rotation;

    public IdentityAuthenticationController(
            AuthenticateDirectUserUseCase authenticateDirectUserUseCase,
            CompleteAuthenticatedPrincipalUseCase completionUseCase) {
        this(authenticateDirectUserUseCase,completionUseCase,null);
    }

    @org.springframework.beans.factory.annotation.Autowired
    public IdentityAuthenticationController(
            AuthenticateDirectUserUseCase authenticateDirectUserUseCase,
            CompleteAuthenticatedPrincipalUseCase completionUseCase,
            RotateLocalRefreshTokenUseCase rotation) {
        this.authenticateDirectUserUseCase = Objects.requireNonNull(authenticateDirectUserUseCase);
        this.completionUseCase = Objects.requireNonNull(completionUseCase);
        this.rotation=rotation;
    }

    @PostMapping("/login")
    public AuthenticationLoginResponse login(
            @Valid @RequestBody AuthenticationLoginRequest request,
            HttpServletRequest servletRequest
    ) {
        Objects.requireNonNull(request, "AuthenticationLoginRequest must not be null.");

        AuthenticationResult result = authenticateDirectUserUseCase.authenticate(new DirectAuthenticationCommand(
                request.providerType(),
                request.principal(),
                request.credentials(),
                clientIp(servletRequest),
                header(servletRequest, "User-Agent"),
                header(servletRequest, "X-Correlation-Id")
        ));
        return IdentityAuthenticationWebMapper.response(result);
    }

    /** No expired bearer needed: the single-use opaque LOCAL credential is independently proved. */
    @PostMapping("/refresh")
    public AuthenticationLoginResponse refresh(
            @Valid @RequestBody RefreshTokenRequest input, HttpServletRequest request) {
        if(rotation==null)throw new ResponseStatusException(
                HttpStatus.UNAUTHORIZED,"Invalid refresh credentials.");
        try {
            return IdentityAuthenticationWebMapper.response(rotation.rotate(input.refreshToken(),
                    clientIp(request),header(request,"User-Agent"),
                    header(request,"X-Correlation-Id")));
        } catch (RefreshAttemptThrottlePort.Limited denied) {
            throw new ResponseStatusException(HttpStatus.TOO_MANY_REQUESTS,
                    "Refresh temporarily unavailable.");
        } catch (BadCredentialsException | RefreshTokenRotationPolicy.Replay
                | RefreshTokenRotationPolicy.Rejected denial) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,"Invalid refresh credentials.");
        }
    }

    /** Idempotent family revocation; a bearer is not required for a lost/expired access JWT. */
    @PostMapping("/logout")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void logout(@Valid @RequestBody RefreshTokenRequest input) {
        if(rotation!=null)rotation.logout(input.refreshToken());
    }

    @PostMapping("/oidc/complete")
    public AuthenticationLoginResponse completeOidc(
            @AuthenticationPrincipal Object springPrincipal,
            HttpServletRequest servletRequest
    ) {
        AuthenticatedPrincipalInput principal = IdentityAuthenticationWebMapper.oidcPrincipal(springPrincipal);
        AuthenticationResult result = completionUseCase.complete(
                principal,
                clientIp(servletRequest),
                header(servletRequest, "User-Agent"),
                header(servletRequest, "X-Correlation-Id")
        );
        return IdentityAuthenticationWebMapper.response(result);
    }

    private static String clientIp(HttpServletRequest request) {
        return request == null ? null : request.getRemoteAddr();
    }

    private static String header(HttpServletRequest request, String name) {
        if (request == null) {
            return null;
        }
        String value = request.getHeader(name);
        return value == null || value.isBlank() ? null : value.trim();
    }
}
