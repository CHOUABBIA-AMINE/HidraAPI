/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : UserTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Class
 * @Layer       : Identity Test
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.domain.model
 *
 * @Description : Tests identity user aggregate lifecycle and role assignment rules.
 *
 */
package dz.sh.hidra.modules.identity.domain.model;

import dz.sh.hidra.modules.identity.domain.exception.RoleAssignmentNotAllowedException;
import dz.sh.hidra.modules.identity.domain.exception.UserLifecycleException;
import dz.sh.hidra.modules.identity.domain.value.EmailAddress;
import dz.sh.hidra.modules.identity.domain.value.RoleCode;
import dz.sh.hidra.modules.identity.domain.value.RoleId;
import dz.sh.hidra.modules.identity.domain.value.RoleName;
import dz.sh.hidra.modules.identity.domain.value.UserId;
import dz.sh.hidra.modules.identity.domain.value.UserStatus;
import dz.sh.hidra.modules.identity.domain.value.Username;
import org.junit.jupiter.api.Test;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneOffset;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Tests identity user aggregate lifecycle and role assignment rules.
 *
 * <p>Business role: verifies user registration, activation, suspension, disabling, and
 * role assignment behavior.</p>
 *
 * <p>Architecture role: domain aggregate test. It does not depend on Spring, JPA,
 * platform security, repositories, or organization modules.</p>
 *
 * <p>Validation responsibility: covers controlled lifecycle transitions, duplicate role
 * assignment rejection, inactive user assignment rejection, and role revocation.</p>
 *
 * <p>Usage: executed by the identity domain test suite.</p>
 */
class UserTest {

    private static final Clock FIXED_CLOCK = Clock.fixed(
            Instant.parse("2026-05-30T10:15:30Z"),
            ZoneOffset.UTC
    );

    @Test
    void registerShouldCreateRegisteredUserWithoutRoles() {
        User user = newUser();

        assertEquals(UserStatus.REGISTERED, user.status());
        assertTrue(user.isRegistered());
        assertFalse(user.isActive());
        assertTrue(user.roleAssignments().isEmpty());
    }

    @Test
    void activateShouldMakeRegisteredUserActive() {
        User user = newUser();

        user.activate();

        assertEquals(UserStatus.ACTIVE, user.status());
        assertTrue(user.isActive());
    }

    @Test
    void suspendShouldRequireActiveUser() {
        User user = newUser();

        assertThrows(UserLifecycleException.class, user::suspend);
    }

    @Test
    void suspendShouldMoveActiveUserToSuspended() {
        User user = activeUser();

        user.suspend();

        assertEquals(UserStatus.SUSPENDED, user.status());
        assertTrue(user.isSuspended());
    }

    @Test
    void disabledUserCannotBeActivated() {
        User user = newUser();
        user.disable();

        assertThrows(UserLifecycleException.class, user::activate);
    }

    @Test
    void assignRoleShouldAddRoleAssignmentForActiveUserAndRole() {
        User user = activeUser();
        Role role = role(RoleId.of("role-1"), RoleCode.of("IDENTITY_ADMIN"));

        user.assignRole(role, FIXED_CLOCK);

        assertTrue(user.hasRole(role.id()));
        assertEquals(1, user.roleAssignments().size());
        assertEquals(FIXED_CLOCK.instant(), user.roleAssignments().get(0).assignedAt());
    }

    @Test
    void assignRoleShouldRejectDuplicateRoleAssignment() {
        User user = activeUser();
        Role role = role(RoleId.of("role-1"), RoleCode.of("IDENTITY_ADMIN"));

        user.assignRole(role, FIXED_CLOCK);

        assertThrows(RoleAssignmentNotAllowedException.class, () -> user.assignRole(role, FIXED_CLOCK));
    }

    @Test
    void assignRoleShouldRejectInactiveUser() {
        User user = newUser();
        Role role = role(RoleId.of("role-1"), RoleCode.of("IDENTITY_ADMIN"));

        assertThrows(UserLifecycleException.class, () -> user.assignRole(role, FIXED_CLOCK));
    }

    @Test
    void revokeRoleShouldRemoveExistingRoleAssignment() {
        User user = activeUser();
        Role role = role(RoleId.of("role-1"), RoleCode.of("IDENTITY_ADMIN"));
        user.assignRole(role, FIXED_CLOCK);

        boolean revoked = user.revokeRole(role.id());

        assertTrue(revoked);
        assertFalse(user.hasRole(role.id()));
    }

    private static User newUser() {
        return User.register(
                UserId.of("user-1"),
                Username.of("abir.medjerab"),
                EmailAddress.of("abir.medjerab@sonatrach.dz")
        );
    }

    private static User activeUser() {
        User user = newUser();
        user.activate();
        return user;
    }

    private static Role role(RoleId roleId, RoleCode roleCode) {
        return Role.create(roleId, roleCode, RoleName.of("Identity Administrator"));
    }
}
