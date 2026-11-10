/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TelemetryNativeLocalActorAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-10
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.infrastructure.security
 *
 * @Description : Requires the exact current signed native LOCAL bearer for Telemetry.
 *
 */
package dz.sh.hidra.modules.identity.infrastructure.security;


import dz.sh.hidra.modules.identity.application.port.out.TelemetryNativeLocalActorPort;
import dz.sh.hidra.modules.identity.application.port.out.FoundationIdentityAuthorizationPort;
import dz.sh.hidra.modules.identity.application.port.out.OperationAuthenticationBindingRepositoryPort;
import dz.sh.hidra.modules.identity.application.port.out.AuthenticationSecurityStateQueryPort;
import java.time.Instant;
import java.util.Objects;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;

@Component
public class TelemetryNativeLocalActorAdapter implements TelemetryNativeLocalActorPort {
    private final FoundationIdentityAuthorizationPort current;
    private final OperationAuthenticationBindingRepositoryPort bindings;
    private final AuthenticationSecurityStateQueryPort state;
    public TelemetryNativeLocalActorAdapter(FoundationIdentityAuthorizationPort current,
            OperationAuthenticationBindingRepositoryPort bindings,
            AuthenticationSecurityStateQueryPort state) {
        this.current=Objects.requireNonNull(current);this.bindings=Objects.requireNonNull(bindings);
        this.state=Objects.requireNonNull(state);
    }
    @Override public String currentLocalActor() {
        String actor = current.authenticatedActor();
        var authentication=SecurityContextHolder.getContext().getAuthentication();
        if(!(authentication instanceof JwtAuthenticationToken nativeToken)
                || !authentication.isAuthenticated())
            throw new AccessDeniedException("Current LOCAL telemetry producer required.");
        var jwt=nativeToken.getToken();
        Object issuer=jwt.getClaims().get("iss");
        if(!(issuer instanceof String nativeIssuer)||nativeIssuer.isBlank()||jwt.getId()==null)
            throw new AccessDeniedException("Current LOCAL telemetry producer required.");
        var binding=bindings.findByNativeIssuerAndJti(nativeIssuer,jwt.getId())
                .orElseThrow(()->new AccessDeniedException("Current LOCAL telemetry producer required."));
        if(!actor.equals(binding.userId()) || !"LOCAL".equals(binding.authenticationType())
                || !"LOCAL".equals(binding.sessionProtocol()) || !state.current(binding,Instant.now()))
            throw new AccessDeniedException("Current LOCAL telemetry producer required.");
        return actor;
    }
}
