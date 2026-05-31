/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : CurrentActorResolver
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Class
 * @Layer       : Platform
 * @Module      : platform
 * @Package     : dz.sh.hidra.platform.security.context
 *
 * @Description : Resolves the current technical actor identifier without business permission semantics.
 *
 */
package dz.sh.hidra.platform.security.context;

import dz.sh.hidra.kernel.domain.value.ActorId;
import org.springframework.stereotype.Component;

@Component
public class CurrentActorResolver {

    public static final ActorId ANONYMOUS_ACTOR_ID = ActorId.of("anonymous");
    public static final ActorId SYSTEM_ACTOR_ID = ActorId.of("system");

    private final CurrentSecurityContext currentSecurityContext;

    public CurrentActorResolver(CurrentSecurityContext currentSecurityContext) {
        this.currentSecurityContext = currentSecurityContext;
    }

    public ActorId currentActorId() {
        return currentSecurityContext.currentPrincipal()
                .map(AuthenticatedPrincipal::actorId)
                .orElse(ANONYMOUS_ACTOR_ID);
    }

    public ActorId systemActorId() {
        return SYSTEM_ACTOR_ID;
    }
}
