/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : CurrentActorResolver
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Platform
 * @Module      : platform
 * @Package     : dz.sh.hidra.platform.security
 *
 * @Description : Resolves the current technical actor identifier.
 *
 */
package dz.sh.hidra.platform.security;

import dz.sh.hidra.kernel.domain.value.ActorId;

import java.util.Objects;

/**
 * Resolves the current technical actor identifier from the current security context.
 */
public class CurrentActorResolver {

    public static final ActorId ANONYMOUS_ACTOR_ID = ActorId.of("anonymous");
    public static final ActorId SYSTEM_ACTOR_ID = ActorId.of("system");

    private final CurrentSecurityContext currentSecurityContext;

    public CurrentActorResolver(CurrentSecurityContext currentSecurityContext) {
        this.currentSecurityContext = Objects.requireNonNull(
                currentSecurityContext,
                "Current security context must not be null."
        );
    }

    public ActorId currentActorId() {
        return currentSecurityContext.currentPrincipal()
                .filter(AuthenticatedPrincipal::authenticated)
                .map(AuthenticatedPrincipal::actorId)
                .orElse(ANONYMOUS_ACTOR_ID);
    }

    public ActorId systemActorId() {
        return SYSTEM_ACTOR_ID;
    }
}
