/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : GetUserPermissionsService
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Class
 * @Layer       : Application
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.application.service
 *
 * @Description : Application service retrieving effective identity user permissions.
 *
 */
package dz.sh.hidra.modules.identity.application.service;

import dz.sh.hidra.kernel.domain.exception.InvalidValueObjectException;
import dz.sh.hidra.modules.identity.application.dto.PermissionDto;
import dz.sh.hidra.modules.identity.application.port.in.GetUserPermissionsUseCase;
import dz.sh.hidra.modules.identity.application.port.out.PermissionRepository;
import dz.sh.hidra.modules.identity.application.port.out.RoleRepository;
import dz.sh.hidra.modules.identity.application.port.out.UserRepository;
import dz.sh.hidra.modules.identity.application.query.GetUserPermissionsQuery;
import dz.sh.hidra.modules.identity.domain.exception.IdentityDomainException;
import dz.sh.hidra.modules.identity.domain.model.Permission;
import dz.sh.hidra.modules.identity.domain.model.Role;
import dz.sh.hidra.modules.identity.domain.model.User;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

/**
 * Application service retrieving effective identity user permissions.
 *
 * <p>Business role: returns permissions granted to a user through active assigned roles.</p>
 *
 * <p>Architecture role: implements the get-user-permissions inbound port by coordinating
 * user, role, and permission repository ports without depending on persistence adapters,
 * Spring Security, or platform current-principal extraction.</p>
 *
 * <p>Validation responsibility: rejects null dependencies and queries and fails when the
 * user does not exist.</p>
 *
 * <p>Usage: called by future API adapters through {@link GetUserPermissionsUseCase}.</p>
 */
public final class GetUserPermissionsService implements GetUserPermissionsUseCase {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PermissionRepository permissionRepository;

    public GetUserPermissionsService(
            UserRepository userRepository,
            RoleRepository roleRepository,
            PermissionRepository permissionRepository
    ) {
        this.userRepository = requireNonNull(userRepository, "UserRepository");
        this.roleRepository = requireNonNull(roleRepository, "RoleRepository");
        this.permissionRepository = requireNonNull(permissionRepository, "PermissionRepository");
    }

    @Override
    public List<PermissionDto> getUserPermissions(GetUserPermissionsQuery query) {
        GetUserPermissionsQuery requiredQuery = requireNonNull(query, "GetUserPermissionsQuery");

        User user = userRepository.findById(requiredQuery.userId())
                .orElseThrow(() -> new IdentityDomainException("User not found: " + requiredQuery.userId().value() + "."));

        return roleRepository.findAll().stream()
                .filter(Role::isActive)
                .filter(role -> user.hasRole(role.id()))
                .flatMap(role -> role.permissionAssignments().stream())
                .map(assignment -> permissionRepository.findByCode(assignment.permissionCode()))
                .flatMap(Optional::stream)
                .distinct()
                .sorted(Comparator.comparing(permission -> permission.code().value()))
                .map(this::toPermissionDto)
                .toList();
    }

    private PermissionDto toPermissionDto(Permission permission) {
        return new PermissionDto(permission.id(), permission.code(), permission.name(), permission.description());
    }

private static <T> T requireNonNull(T value, String fieldName) {
    if (value == null) {
        throw new InvalidValueObjectException(fieldName + " must not be null.");
    }
    return value;
}
}
