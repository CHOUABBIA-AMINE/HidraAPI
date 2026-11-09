/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : IdentityEffectivePermissionSourceAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-19
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.infrastructure.security
 *
 * @Description : Adapts identity effective grants to the platform permission-resolution extension point.
 *
 */
package dz.sh.hidra.modules.identity.infrastructure.security;

import dz.sh.hidra.modules.identity.application.port.in.IdentityAdministrationQueryUseCase;
import dz.sh.hidra.modules.identity.application.port.out.OperationAuthenticationBindingRepositoryPort;
import dz.sh.hidra.modules.identity.application.port.out.AuthenticationSecurityStateQueryPort;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import java.time.Instant;
import dz.sh.hidra.platform.security.HidraEffectivePermissionSource;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import org.springframework.stereotype.Component;

/**
 * Publishes Identity-owned effective permission meaning to technical security plumbing.
 */
@Component
public final class IdentityEffectivePermissionSourceAdapter implements HidraEffectivePermissionSource {

    private final IdentityAdministrationQueryUseCase queryUseCase;
    private final IdentityAdministratorGrantService administratorGrantService;
    private OperationAuthenticationBindingRepositoryPort bindings;
    private AuthenticationSecurityStateQueryPort currentState;

    public IdentityEffectivePermissionSourceAdapter(
            IdentityAdministrationQueryUseCase queryUseCase,
            IdentityAdministratorGrantService administratorGrantService
    ) {
        this.queryUseCase = Objects.requireNonNull(queryUseCase, "Identity administration query use case must not be null.");
        this.administratorGrantService = Objects.requireNonNull(administratorGrantService);
    }

    @org.springframework.beans.factory.annotation.Autowired
    public IdentityEffectivePermissionSourceAdapter(
            IdentityAdministrationQueryUseCase queryUseCase,
            IdentityAdministratorGrantService administratorGrantService,
            OperationAuthenticationBindingRepositoryPort bindings,
            AuthenticationSecurityStateQueryPort currentState) {
        this(queryUseCase, administratorGrantService);
        this.bindings = Objects.requireNonNull(bindings);
        this.currentState = Objects.requireNonNull(currentState);
    }

    private void verifyCurrentBearer(String principalName) {
        var actual = SecurityContextHolder.getContext().getAuthentication();
        if (!(actual instanceof JwtAuthenticationToken bearer)) return;
        if (!actual.getName().equals(principalName) || bindings == null || currentState == null)
            throw new AccessDeniedException("Current Identity bearer required.");
        var jwt = bearer.getToken();
        // The configured, signed native issuer can be a non-URL identifier.
        // Keep the exact stored-binding and current-session checks below.
        Object issuerClaim = jwt.getClaims().get("iss");
        String issuer = issuerClaim instanceof String ? (String) issuerClaim : null;
        String jti = jwt.getId();
        if (issuer == null || issuer.isBlank() || jti == null)
            throw new AccessDeniedException("Current Identity bearer required.");
        try {
            var b = bindings.findByNativeIssuerAndJti(issuer, jti)
                    .orElseThrow(() -> new AccessDeniedException("Current Identity bearer required."));
            if (!principalName.equals(b.userId())
                    || !issuer.equals(b.nativeIssuer())
                    || !currentState.current(b, Instant.now()))
                throw new AccessDeniedException("Current Identity bearer required.");
        } catch (AccessDeniedException denial) {
            throw denial;
        } catch (RuntimeException unavailable) {
            throw new IllegalStateException("Current Identity bearer verification unavailable.");
        }
    }

    @Override
    public Set<String> resolve(String principalName) {
        verifyCurrentBearer(principalName);
        IdentityAdministrationQueryUseCase.PrincipalView principal = queryUseCase.principal(principalName, List.of());
        Set<String> permissions = new LinkedHashSet<>(principal.effectivePermissions());
        // A wildcard is never granted by a stored permission code alone.
        permissions.remove("*");
        if (principal.userId() != null
                && administratorGrantService.hasActiveGlobalAdministratorGrant(principal.userId())) {
            permissions.add("*");
        }
        return Set.copyOf(permissions);
    }
}
