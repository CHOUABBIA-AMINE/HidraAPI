/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : WorkflowHumanActorQueryServiceTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-27
 *
 * @Type        : Class
 * @Layer       : Test
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.application.service
 *
 * @Description : Provides bounded product source publication governance.
 *
 */
package dz.sh.hidra.modules.identity.application.service;

import dz.sh.hidra.modules.identity.application.port.out.UserRepositoryPort;
import dz.sh.hidra.modules.identity.domain.model.User;
import dz.sh.hidra.modules.identity.domain.value.*;
import java.time.Instant;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
class WorkflowHumanActorQueryServiceTest {
    private final UserRepositoryPort users=mock(UserRepositoryPort.class);
    private final WorkflowHumanActorQueryService owner=new WorkflowHumanActorQueryService(users);
    private final Instant at=Instant.parse("2026-10-27T00:00:00Z");
    private User user(UserType type,UserStatus status,Instant lock,String display) {
        return new User("reviewer","reviewer",null,display,type,status,null,null,0,lock,at,at,null,null,at);
    }
    @Test void onlyExactActiveUnlockedHumanWithDisplayMayReview() {
        when(users.findById("reviewer")).thenReturn(Optional.of(user(UserType.HUMAN,UserStatus.ACTIVE,null,"Reviewer")));
        assertEquals("reviewer",owner.eligibleHuman("reviewer",at).orElseThrow().id());
        assertTrue(owner.eligibleHuman(" reviewer",at).isEmpty());assertTrue(owner.eligibleHuman("reviewer",null).isEmpty());
    }
    @Test void allNonhumanTypesRejectEvenWhenActive() {
        for(var type:UserType.values())if(type!=UserType.HUMAN){when(users.findById("reviewer")).thenReturn(Optional.of(user(type,UserStatus.ACTIVE,null,"Reviewer")));assertTrue(owner.eligibleHuman("reviewer",at).isEmpty());}
    }
    @Test void lockedInactiveMissingAndBlankDisplayReject() {
        for(var state:UserStatus.values())if(state!=UserStatus.ACTIVE){when(users.findById("reviewer")).thenReturn(Optional.of(user(UserType.HUMAN,state,null,"Reviewer")));assertTrue(owner.eligibleHuman("reviewer",at).isEmpty());}
        when(users.findById("reviewer")).thenReturn(Optional.of(user(UserType.HUMAN,UserStatus.ACTIVE,at.plusSeconds(1),"Reviewer")));assertTrue(owner.eligibleHuman("reviewer",at).isEmpty());
        when(users.findById("reviewer")).thenReturn(Optional.of(user(UserType.HUMAN,UserStatus.ACTIVE,null," ")));assertTrue(owner.eligibleHuman("reviewer",at).isEmpty());
        when(users.findById("reviewer")).thenReturn(Optional.empty());assertTrue(owner.eligibleHuman("reviewer",at).isEmpty());
    }
}
