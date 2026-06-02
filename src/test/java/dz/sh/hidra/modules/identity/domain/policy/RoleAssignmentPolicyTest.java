/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : RoleAssignmentPolicyTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Class
 * @Layer       : Identity Test
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.domain.policy
 *
 * @Description : Tests identity role assignment rules.
 *
 */
package dz.sh.hidra.modules.identity.domain.policy;

import dz.sh.hidra.modules.identity.domain.exception.RoleAssignmentNotAllowedException;
import dz.sh.hidra.modules.identity.domain.exception.UserLifecycleException;
import dz.sh.hidra.modules.identity.domain.model.Role;
import dz.sh.hidra.modules.identity.domain.model.User;
import dz.sh.hidra.modules.identity.domain.value.EmailAddress;
import dz.sh.hidra.modules.identity.domain.value.RoleCode;
import dz.sh.hidra.modules.identity.domain.value.RoleId;
import dz.sh.hidra.modules.identity.domain.value.RoleName;
import dz.sh.hidra.modules.identity.domain.value.UserId;
import dz.sh.hidra.modules.identity.domain.value.Username;
import org.junit.jupiter.api.Test;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneOffset;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Tests identity role assignment rules.
 *
 * <p>Business role: verifies that only active users can receive active roles and that
 * duplicate assignments are rejected.</p>
 *
 * <p>Architecture role: pure domain policy test with no Spring, JPA, platform security,
 * repository, or organization dependency.</p>
 *
 * <p>Validation responsibility: covers assignable state, inactive user rejection,
 * inactive role rejection, and duplicate role rejection.</p>
 *
 * <p>Usage: executed by the identity domain test suite.</p>
 */
class RoleAssignmentPolicyTest {

    private static final Clock FIXED_CLOCK = Clock.fixed(
            Instant.parse("2026-05-30T10:15:30Z"),
            ZoneOffset.UTC
    );

    private final RoleAssignmentPolicy policy = new RoleAssignmentPolicy();

    @Test
    void requireAssignableShouldAcceptActiveUserAndActiveRole() {
        User user = activeUser();
        Role role = role();

        assertDoesNotThrow(() -> policy.requireAssignable(user, role));
        assertTrue(policy.canAssign(user, role));
    }

    @Test
    void requireAssignableShouldRejectInactiveUser() {
        User user = registeredUser();
        Role role = role();

        assertThrows(UserLifecycleException.class, () -> policy.requireAssignable(user, role));
        assertFalse(policy.canAssign(user, role));
    }

    @Test
    void requireAssignableShouldRejectDisabledRole() {
        User user = activeUser();
        Role role = role();
        role.disable();

        assertThrows(RoleAssignmentNotAllowedException.class, () -> policy.requireAssignable(user, role));
        assertFalse(policy.canAssign(user, role));
    }

    @Test
    void requireAssignableShouldRejectDuplicateRole() {
        User user = activeUser();
        Role role = role();
        user.assignRole(role, FIXED_CLOCK);

        assertThrows(RoleAssignmentNotAllowedException.class, () -> policy.requireAssignable(user, role));
        assertFalse(policy.canAssign(user, role));
    }

    private static User registeredUser() {
        return User.register(
                UserId.of("user-1"),
                Username.of("abir.medjerab"),
                EmailAddress.of("abir.medjerab@sonatrach.dz")
        );
    }

    private static User activeUser() {
        User user = registeredUser();
        user.activate();
        return user;
    }

    private static Role role() {
        return Role.create(
                RoleId.of("role-1"),
                RoleCode.of("IDENTITY_ADMIN"),
                RoleName.of("Identity Administrator")
        );
    }
}
