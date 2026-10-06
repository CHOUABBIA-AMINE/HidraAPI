/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : UserRoleGrantSemanticRemediationTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-06
 *
 * @Type        : Class
 * @Layer       : Identity Test
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.semantic
 *
 * @Description : Enforces the admitted Batch 5 UserRoleGrantSemanticRemediationTest contract.
 *
 */
package dz.sh.hidra.modules.identity.semantic;

import dz.sh.hidra.modules.identity.domain.model.*;
import dz.sh.hidra.modules.identity.domain.value.*;
import dz.sh.hidra.modules.identity.application.port.out.*;
import dz.sh.hidra.modules.identity.application.command.IdentityAdministrationCommands.GrantRoleToUser;
import dz.sh.hidra.modules.identity.application.service.IdentityAdministrationCommandApplicationService;
import java.time.Instant;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserRoleGrantSemanticRemediationTest {
    private User user(UserStatus status) {
        return new User("u","alice",null,null,UserType.HUMAN,status,null,null,0,null,
                Instant.now(),null,null,null,Instant.now());
    }
    @Test void ordinaryGrantRejectsEveryInactiveStateBeforePersistence() {
        var users = mock(UserRepositoryPort.class);
        var roles = mock(RoleRepositoryPort.class);
        var grants = mock(UserRoleGrantRepositoryPort.class);
        var service = new IdentityAdministrationCommandApplicationService(users,roles,
                mock(PermissionRepositoryPort.class),grants,mock(RolePermissionGrantRepositoryPort.class),mock(UserPermissionGrantRepositoryPort.class));
        var command = new GrantRoleToUser("u","role",null,null,null,null,null,null,null);
        for (var status : UserStatus.values()) {
            if (status == UserStatus.ACTIVE) continue;
            when(users.findById("u")).thenReturn(Optional.of(user(status)));
            assertThatThrownBy(() -> service.grantRoleToUser(command)).isInstanceOf(IllegalArgumentException.class);
        }
        verifyNoInteractions(grants,roles);
    }
    @Test void activeUserMayReceiveOpenEndedGrantWithoutReason() {
        var users = mock(UserRepositoryPort.class);
        var roles = mock(RoleRepositoryPort.class);
        var grants = mock(UserRoleGrantRepositoryPort.class);
        when(users.findById("u")).thenReturn(Optional.of(user(UserStatus.ACTIVE)));
        when(roles.findById("role")).thenReturn(Optional.of(mock(Role.class)));
        when(grants.save(any())).thenAnswer(x -> x.getArgument(0));
        var service = new IdentityAdministrationCommandApplicationService(users,roles,
                mock(PermissionRepositoryPort.class),grants,mock(RolePermissionGrantRepositoryPort.class),mock(UserPermissionGrantRepositoryPort.class));
        assertThat(service.grantRoleToUser(new GrantRoleToUser("u","role",null,null,null,null,null,null,null))).isNotBlank();
        var captor = org.mockito.ArgumentCaptor.forClass(UserRoleGrant.class);
        verify(grants).save(captor.capture());
        assertThat(captor.getValue().validTo()).isNull();
        assertThat(captor.getValue().grantReason()).isNull();
    }
}
