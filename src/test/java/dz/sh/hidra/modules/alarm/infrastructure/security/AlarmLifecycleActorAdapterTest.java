/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AlarmLifecycleActorAdapterTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-08
 *
 * @Type        : Class
 * @Layer       : Test
 * @Module      : alarm
 * @Package     : dz.sh.hidra.modules.alarm.infrastructure.security
 *
 * @Description : Enforces Alarm-owned transactional lifecycle evidence and integrity.
 *
 */
package dz.sh.hidra.modules.alarm.infrastructure.security;

import dz.sh.hidra.platform.security.CurrentActorResolver;
import dz.sh.hidra.kernel.domain.value.ActorId;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class AlarmLifecycleActorAdapterTest {
    @Test void authenticatedCreationUsesTheSecurityActor() {
        var resolver=mock(CurrentActorResolver.class);
        when(resolver.currentActorId()).thenReturn(ActorId.of("actor"));
        when(resolver.currentPrincipalName()).thenReturn("Operator");
        var actor=new AlarmLifecycleActorAdapter(resolver).currentActor();
        assertEquals("actor",actor.id());assertEquals("Operator",actor.displayName());
    }
    @Test void trustedInternalCreationUsesTheEstablishedServerSystemActor() {
        var resolver=mock(CurrentActorResolver.class);
        when(resolver.currentActorId()).thenReturn(CurrentActorResolver.ANONYMOUS_ACTOR_ID);
        when(resolver.systemActorId()).thenReturn(CurrentActorResolver.SYSTEM_ACTOR_ID);
        assertEquals("system",new AlarmLifecycleActorAdapter(resolver).currentActor().id());
    }
}
