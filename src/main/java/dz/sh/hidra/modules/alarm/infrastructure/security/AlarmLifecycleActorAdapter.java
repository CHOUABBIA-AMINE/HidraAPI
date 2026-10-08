/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AlarmLifecycleActorAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-08
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : alarm
 * @Package     : dz.sh.hidra.modules.alarm.infrastructure.security
 *
 * @Description : Enforces Alarm-owned transactional lifecycle evidence and integrity.
 *
 */
package dz.sh.hidra.modules.alarm.infrastructure.security;

import dz.sh.hidra.modules.alarm.application.port.out.AlarmLifecycleActorPort;
import dz.sh.hidra.platform.security.CurrentActorResolver;
import java.util.Objects;
import org.springframework.stereotype.Component;

@Component
public class AlarmLifecycleActorAdapter implements AlarmLifecycleActorPort {
    private final CurrentActorResolver actors;
    public AlarmLifecycleActorAdapter(CurrentActorResolver actors) {this.actors = Objects.requireNonNull(actors);}
    @Override public Actor currentActor() {
        var id = actors.currentActorId();
        if (CurrentActorResolver.ANONYMOUS_ACTOR_ID.equals(id)) {
            return new Actor(actors.systemActorId().value(), "Hidra internal alarm creation");
        }
        return new Actor(id.value(), actors.currentPrincipalName());
    }
}
