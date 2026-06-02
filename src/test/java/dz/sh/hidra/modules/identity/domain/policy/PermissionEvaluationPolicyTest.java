/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : PermissionEvaluationPolicyTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Class
 * @Layer       : Identity Test
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.domain.policy
 *
 * @Description : Tests identity permission evaluation rules.
 *
 */
package dz.sh.hidra.modules.identity.domain.policy;

import dz.sh.hidra.modules.identity.domain.exception.IdentityDomainException;
import dz.sh.hidra.modules.identity.domain.exception.UserLifecycleException;
import dz.sh.hidra.modules.identity.domain.model.Permission;
import dz.sh.hidra.modules.identity.domain.model.Role;
import dz.sh.hidra.modules.identity.domain.model.User;
import dz.sh.hidra.modules.identity.domain.value.EmailAddress;
import dz.sh.hidra.modules.identity.domain.value.PermissionCode;
import dz.sh.hidra.modules.identity.domain.value.PermissionId;
import dz.sh.hidra.modules.identity.domain.value.PermissionName;
import dz.sh.hidra.modules.identity.domain.value.RoleCode;
import dz.sh.hidra.modules.identity.domain.value.RoleId;
import dz.sh.hidra.modules.identity.domain.value.RoleName;
import dz.sh.hidra.modules.identity.domain.value.UserId;
import dz.sh.hidra.modules.identity.domain.value.Username;
import org.junit.jupiter.api.Test;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneOffset;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Tests identity permission evaluation rules.
 *
 * <p>Business role: verifies that users receive permissions through active assigned
 * roles only.</p>
 *
 * <p>Architecture role: pure domain policy test with no Spring, JPA, platform security,
 * repository, or organization dependency.</p>
 *
 * <p>Validation responsibility: covers granted permission decisions, denied permission
 * decisions, inactive user rejection, and inactive role ignoring.</p>
 *
 * <p>Usage: executed by the identity domain test suite.</p>
 */
class PermissionEvaluationPolicyTest {

    private static final Clock FIXED_CLOCK = Clock.fixed(
            Instant.parse("2026-05-30T10:15:30Z"),
            ZoneOffset.UTC
    );

    private final PermissionEvaluationPolicy policy = new PermissionEvaluationPolicy();

    @Test
    void hasPermissionShouldReturnTrueWhenActiveAssignedRoleGrantsPermission() {
        User user = activeUser();
        Role role = role();
        Permission permission = permission("permission-1", "identity:user:create");
        role.grantPermission(permission, FIXED_CLOCK);
        user.assignRole(role, FIXED_CLOCK);

        boolean granted = policy.hasPermission(user, permission.code(), List.of(role));

        assertTrue(granted);
    }

    @Test
    void hasPermissionShouldReturnFalseWhenAssignedRoleDoesNotGrantPermission() {
        User user = activeUser();
        Role role = role();
        user.assignRole(role, FIXED_CLOCK);

        boolean granted = policy.hasPermission(
                user,
                PermissionCode.of("identity:user:create"),
                List.of(role)
        );

        assertFalse(granted);
    }

    @Test
    void hasPermissionShouldIgnoreInactiveRoles() {
        User user = activeUser();
        Role role = role();
        Permission permission = permission("permission-1", "identity:user:create");
        role.grantPermission(permission, FIXED_CLOCK);
        user.assignRole(role, FIXED_CLOCK);
        role.disable();

        boolean granted = policy.hasPermission(user, permission.code(), List.of(role));

        assertFalse(granted);
    }

    @Test
    void hasPermissionShouldRejectInactiveUser() {
        User user = registeredUser();

        assertThrows(
                UserLifecycleException.class,
                () -> policy.hasPermission(user, PermissionCode.of("identity:user:create"), List.of())
        );
    }

    @Test
    void requirePermissionShouldRejectMissingPermission() {
        User user = activeUser();
        Role role = role();
        user.assignRole(role, FIXED_CLOCK);

        assertThrows(
                IdentityDomainException.class,
                () -> policy.requirePermission(user, PermissionCode.of("identity:user:create"), List.of(role))
        );
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

    private static Permission permission(String id, String code) {
        return Permission.create(
                PermissionId.of(id),
                PermissionCode.of(code),
                PermissionName.of("Create identity user"),
                "Allows creating identity users."
        );
    }
}
