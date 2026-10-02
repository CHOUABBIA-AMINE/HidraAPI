/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : IdentityAdministrationCommandApplicationServiceTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-11
 *
 * @Type        : Class
 * @Layer       : Application Test
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.application.service
 *
 * @Description : Verifies success, validation, and not-found behavior for identity administration commands.
 *
 */
package dz.sh.hidra.modules.identity.application.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import dz.sh.hidra.modules.identity.application.command.IdentityAdministrationCommands.CreatePermission;
import dz.sh.hidra.modules.identity.application.command.IdentityAdministrationCommands.CreateRole;
import dz.sh.hidra.modules.identity.application.command.IdentityAdministrationCommands.GrantRoleToUser;
import dz.sh.hidra.modules.identity.application.port.out.PermissionRepositoryPort;
import dz.sh.hidra.modules.identity.application.port.out.RolePermissionGrantRepositoryPort;
import dz.sh.hidra.modules.identity.application.port.out.RoleRepositoryPort;
import dz.sh.hidra.modules.identity.application.port.out.UserPermissionGrantRepositoryPort;
import dz.sh.hidra.modules.identity.application.port.out.UserRepositoryPort;
import dz.sh.hidra.modules.identity.application.port.out.UserRoleGrantRepositoryPort;
import dz.sh.hidra.modules.identity.domain.model.Role;
import java.util.NoSuchElementException;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class IdentityAdministrationCommandApplicationServiceTest {

    private UserRepositoryPort userRepository;
    private RoleRepositoryPort roleRepository;
    private PermissionRepositoryPort permissionRepository;
    private UserRoleGrantRepositoryPort userRoleGrantRepository;
    private RolePermissionGrantRepositoryPort rolePermissionGrantRepository;
    private UserPermissionGrantRepositoryPort userPermissionGrantRepository;
    private IdentityAdministrationCommandApplicationService service;

    @BeforeEach
    void setUp() {
        userRepository = mock(UserRepositoryPort.class);
        roleRepository = mock(RoleRepositoryPort.class);
        permissionRepository = mock(PermissionRepositoryPort.class);
        userRoleGrantRepository = mock(UserRoleGrantRepositoryPort.class);
        rolePermissionGrantRepository = mock(RolePermissionGrantRepositoryPort.class);
        userPermissionGrantRepository = mock(UserPermissionGrantRepositoryPort.class);
        service = new IdentityAdministrationCommandApplicationService(
                userRepository,
                roleRepository,
                permissionRepository,
                userRoleGrantRepository,
                rolePermissionGrantRepository,
                userPermissionGrantRepository
        );
    }

    @Test
    void createsRoleThroughDomainRepositoryPort() {
        when(roleRepository.save(any(Role.class))).thenAnswer(invocation -> invocation.getArgument(0));

        var result = service.createRole(new CreateRole(
                "OPERATIONS_REVIEWER",
                null,
                "Réviseur opérations",
                "Operations Reviewer",
                "Reviews operational data.",
                "BUSINESS",
                "ACTIVE"
        ));

        assertEquals("OPERATIONS_REVIEWER", result.code());
        assertEquals("BUSINESS", result.roleType());
        assertEquals("ACTIVE", result.status());
    }

    @Test
    void rejectsPermissionCodeOutsideIdentityRoadmapFormat() {
        CreatePermission command = new CreatePermission(
                "HIDRA_TELEMETRY_READ",
                null,
                "Lecture télémétrie",
                "Telemetry read",
                null,
                "telemetry",
                "points",
                "read",
                false,
                "ACTIVE"
        );

        assertThrows(IllegalArgumentException.class, () -> service.createPermission(command));
        verifyNoInteractions(permissionRepository);
    }

    @Test
    void reportsMissingGrantTargetAsNotFound() {
        when(userRepository.findById("missing-user")).thenReturn(Optional.empty());

        GrantRoleToUser command = new GrantRoleToUser(
                "missing-user",
                "role-1",
                null,
                null,
                null,
                "test",
                null,
                null,
                null
        );

        assertThrows(NoSuchElementException.class, () -> service.grantRoleToUser(command));
    }
}
