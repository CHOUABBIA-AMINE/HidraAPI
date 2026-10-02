/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : IdentityAdministratorGrantServiceTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-19
 *
 * @Type        : Class
 * @Layer       : Test
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.infrastructure.security
 *
 * @Description : Proves administrator elevation requires current global Identity-owned grants.
 *
 */
package dz.sh.hidra.modules.identity.infrastructure.security;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import dz.sh.hidra.modules.identity.domain.value.GrantStatus;
import dz.sh.hidra.modules.identity.domain.value.RoleStatus;
import dz.sh.hidra.modules.identity.domain.value.RoleType;
import dz.sh.hidra.modules.identity.domain.value.ScopeType;
import dz.sh.hidra.modules.identity.domain.value.UserStatus;
import dz.sh.hidra.modules.identity.domain.value.UserType;
import dz.sh.hidra.modules.identity.infrastructure.persistence.entity.RoleJpaEntity;
import dz.sh.hidra.modules.identity.infrastructure.persistence.entity.UserJpaEntity;
import dz.sh.hidra.modules.identity.infrastructure.persistence.entity.UserRoleGrantJpaEntity;
import dz.sh.hidra.modules.identity.infrastructure.persistence.repository.RoleJpaRepository;
import dz.sh.hidra.modules.identity.infrastructure.persistence.repository.UserJpaRepository;
import dz.sh.hidra.modules.identity.infrastructure.persistence.repository.UserRoleGrantJpaRepository;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class IdentityAdministratorGrantServiceTest {

    private static final String USER_ID = "user-1";
    private static final String ROLE_ID = "admin-role";
    private UserJpaRepository users;
    private RoleJpaRepository roles;
    private UserRoleGrantJpaRepository grants;
    private IdentityAdministratorGrantService service;

    @BeforeEach
    void setUp() {
        users = mock(UserJpaRepository.class);
        roles = mock(RoleJpaRepository.class);
        grants = mock(UserRoleGrantJpaRepository.class);
        service = new IdentityAdministratorGrantService(users, roles, grants);
        when(users.findById(USER_ID)).thenReturn(Optional.of(user(UserStatus.ACTIVE)));
        when(roles.findFirstByCode("HIDRA_ADMIN")).thenReturn(Optional.of(role(RoleStatus.ACTIVE)));
    }

    @Test
    void activeGlobalGrantEnablesAdministrator() {
        when(grants.findByUserIdAndRoleIdAndStatus(USER_ID, ROLE_ID, GrantStatus.ACTIVE))
                .thenReturn(List.of(grant(GrantStatus.ACTIVE, ScopeType.GLOBAL,
                        Instant.now().minusSeconds(60), null, null)));
        assertThat(service.hasActiveGlobalAdministratorGrant(USER_ID)).isTrue();
    }

    @Test
    void expiredGrantCannotEnableAdministrator() {
        when(grants.findByUserIdAndRoleIdAndStatus(USER_ID, ROLE_ID, GrantStatus.ACTIVE))
                .thenReturn(List.of(grant(GrantStatus.ACTIVE, ScopeType.GLOBAL,
                        Instant.now().minusSeconds(120), Instant.now().minusSeconds(60), null)));
        assertThat(service.hasActiveGlobalAdministratorGrant(USER_ID)).isFalse();
    }

    @Test
    void futureGrantCannotEnableAdministrator() {
        when(grants.findByUserIdAndRoleIdAndStatus(USER_ID, ROLE_ID, GrantStatus.ACTIVE))
                .thenReturn(List.of(grant(GrantStatus.ACTIVE, ScopeType.GLOBAL,
                        Instant.now().plusSeconds(60), null, null)));
        assertThat(service.hasActiveGlobalAdministratorGrant(USER_ID)).isFalse();
    }

    @Test
    void revokedGrantCannotEnableAdministrator() {
        when(grants.findByUserIdAndRoleIdAndStatus(USER_ID, ROLE_ID, GrantStatus.ACTIVE))
                .thenReturn(List.of(grant(GrantStatus.ACTIVE, ScopeType.GLOBAL,
                        Instant.now().minusSeconds(60), null, Instant.now().minusSeconds(1))));
        assertThat(service.hasActiveGlobalAdministratorGrant(USER_ID)).isFalse();
    }

    @Test
    void inactiveGrantCannotEnableAdministrator() {
        when(grants.findByUserIdAndRoleIdAndStatus(USER_ID, ROLE_ID, GrantStatus.ACTIVE))
                .thenReturn(List.of());
        assertThat(service.hasActiveGlobalAdministratorGrant(USER_ID)).isFalse();
    }

    @Test
    void scopedGrantCannotEnableGlobalAdministrator() {
        when(grants.findByUserIdAndRoleIdAndStatus(USER_ID, ROLE_ID, GrantStatus.ACTIVE))
                .thenReturn(List.of(grant(GrantStatus.ACTIVE, ScopeType.PIPELINE,
                        Instant.now().minusSeconds(60), null, null)));
        assertThat(service.hasActiveGlobalAdministratorGrant(USER_ID)).isFalse();
    }

    @Test
    void inactiveRoleCannotEnableAdministrator() {
        when(roles.findFirstByCode("HIDRA_ADMIN")).thenReturn(Optional.of(role(RoleStatus.DISABLED)));
        assertThat(service.hasActiveGlobalAdministratorGrant(USER_ID)).isFalse();
    }

    @Test
    void disabledUserCannotEnableAdministrator() {
        when(users.findById(USER_ID)).thenReturn(Optional.of(user(UserStatus.DISABLED)));
        assertThat(service.hasActiveGlobalAdministratorGrant(USER_ID)).isFalse();
    }

    private static UserJpaEntity user(UserStatus status) {
        Instant now = Instant.now();
        return new UserJpaEntity(USER_ID, "local-admin", null, "Local admin", UserType.HUMAN,
                status, null, null, 0, null, now, now, null, null, now);
    }

    private static RoleJpaEntity role(RoleStatus status) {
        Instant now = Instant.now();
        return new RoleJpaEntity(ROLE_ID, "HIDRA_ADMIN", null, null, "Hidra admin", null,
                RoleType.ADMIN, status, now, now);
    }

    private static UserRoleGrantJpaEntity grant(GrantStatus status, ScopeType scope,
                                               Instant from, Instant to, Instant revokedAt) {
        return new UserRoleGrantJpaEntity("grant-1", USER_ID, ROLE_ID, scope,
                scope == ScopeType.GLOBAL ? null : "pipeline-1", null, "test", null,
                from, to, status, Instant.now(), revokedAt, null);
    }
}
