/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : RoleTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Class
 * @Layer       : Identity Test
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.domain.model
 *
 * @Description : Tests identity role aggregate lifecycle and permission assignment rules.
 *
 */
package dz.sh.hidra.modules.identity.domain.model;

import dz.sh.hidra.modules.identity.domain.exception.RoleAssignmentNotAllowedException;
import dz.sh.hidra.modules.identity.domain.value.PermissionCode;
import dz.sh.hidra.modules.identity.domain.value.PermissionId;
import dz.sh.hidra.modules.identity.domain.value.PermissionName;
import dz.sh.hidra.modules.identity.domain.value.RoleCode;
import dz.sh.hidra.modules.identity.domain.value.RoleId;
import dz.sh.hidra.modules.identity.domain.value.RoleName;
import dz.sh.hidra.modules.identity.domain.value.RoleStatus;
import org.junit.jupiter.api.Test;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneOffset;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Tests identity role aggregate lifecycle and permission assignment rules.
 *
 * <p>Business role: verifies role creation, lifecycle changes, and permission grants.</p>
 *
 * <p>Architecture role: domain aggregate test. It does not depend on Spring, JPA,
 * platform security, repositories, or organization modules.</p>
 *
 * <p>Validation responsibility: covers active role creation, permission assignment,
 * duplicate permission rejection, and disabled-role mutation rejection.</p>
 *
 * <p>Usage: executed by the identity domain test suite.</p>
 */
class RoleTest {

    private static final Clock FIXED_CLOCK = Clock.fixed(
            Instant.parse("2026-05-30T10:15:30Z"),
            ZoneOffset.UTC
    );

    @Test
    void createShouldCreateActiveRoleWithoutPermissions() {
        Role role = newRole();

        assertEquals(RoleStatus.ACTIVE, role.status());
        assertTrue(role.isActive());
        assertTrue(role.permissionAssignments().isEmpty());
    }

    @Test
    void disableShouldMakeRoleInactiveForAssignments() {
        Role role = newRole();
        role.disable();

        assertEquals(RoleStatus.DISABLED, role.status());
    }

    @Test
    void grantPermissionShouldAddPermissionAssignment() {
        Role role = newRole();
        Permission permission = permission();

        role.grantPermission(permission, FIXED_CLOCK);

        assertTrue(role.hasPermission(permission.code()));
        assertEquals(1, role.permissionAssignments().size());
        assertEquals(FIXED_CLOCK.instant(), role.permissionAssignments().get(0).assignedAt());
    }

    @Test
    void grantPermissionShouldRejectDuplicatePermission() {
        Role role = newRole();
        Permission permission = permission();
        role.grantPermission(permission, FIXED_CLOCK);

        assertThrows(RoleAssignmentNotAllowedException.class, () -> role.grantPermission(permission, FIXED_CLOCK));
    }

    @Test
    void grantPermissionShouldRejectDisabledRole() {
        Role role = newRole();
        role.disable();

        assertThrows(RoleAssignmentNotAllowedException.class, () -> role.grantPermission(permission(), FIXED_CLOCK));
    }

    @Test
    void revokePermissionShouldRemoveExistingPermissionAssignment() {
        Role role = newRole();
        Permission permission = permission();
        role.grantPermission(permission, FIXED_CLOCK);

        boolean revoked = role.revokePermission(permission.code());

        assertTrue(revoked);
    }

    private static Role newRole() {
        return Role.create(
                RoleId.of("role-1"),
                RoleCode.of("IDENTITY_ADMIN"),
                RoleName.of("Identity Administrator")
        );
    }

    private static Permission permission() {
        return Permission.create(
                PermissionId.of("permission-1"),
                PermissionCode.of("identity:user:create"),
                PermissionName.of("Create identity user"),
                "Allows creating identity users."
        );
    }
}
