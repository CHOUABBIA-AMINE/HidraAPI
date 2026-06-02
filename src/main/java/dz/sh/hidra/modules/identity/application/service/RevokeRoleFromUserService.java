/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : RevokeRoleFromUserService
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Class
 * @Layer       : Application
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.application.service
 *
 * @Description : Application service revoking roles from identity users.
 *
 */
package dz.sh.hidra.modules.identity.application.service;

import dz.sh.hidra.kernel.domain.exception.InvalidValueObjectException;
import dz.sh.hidra.modules.identity.application.command.RevokeRoleFromUserCommand;
import dz.sh.hidra.modules.identity.application.dto.RoleDto;
import dz.sh.hidra.modules.identity.application.dto.UserDto;
import dz.sh.hidra.modules.identity.application.port.in.RevokeRoleFromUserUseCase;
import dz.sh.hidra.modules.identity.application.port.out.RoleRepository;
import dz.sh.hidra.modules.identity.application.port.out.UserRepository;
import dz.sh.hidra.modules.identity.domain.exception.IdentityDomainException;
import dz.sh.hidra.modules.identity.domain.exception.RoleAssignmentNotAllowedException;
import dz.sh.hidra.modules.identity.domain.model.Role;
import dz.sh.hidra.modules.identity.domain.model.User;

import java.util.List;

/**
 * Application service revoking roles from identity users.
 *
 * <p>Business role: removes an existing role assignment from a user.</p>
 *
 * <p>Architecture role: implements the revoke-role-from-user inbound port by loading the
 * user aggregate, invoking aggregate revocation, persisting the user, and projecting the
 * updated user DTO.</p>
 *
 * <p>Validation responsibility: rejects null dependencies and commands, fails for missing
 * users, and rejects revocation when the role is not currently assigned.</p>
 *
 * <p>Usage: called by future API adapters through {@link RevokeRoleFromUserUseCase}.</p>
 */
public final class RevokeRoleFromUserService implements RevokeRoleFromUserUseCase {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public RevokeRoleFromUserService(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = requireNonNull(userRepository, "UserRepository");
        this.roleRepository = requireNonNull(roleRepository, "RoleRepository");
    }

    @Override
    public UserDto revokeRoleFromUser(RevokeRoleFromUserCommand command) {
        RevokeRoleFromUserCommand requiredCommand = requireNonNull(command, "RevokeRoleFromUserCommand");

        User user = userRepository.findById(requiredCommand.userId())
                .orElseThrow(() -> new IdentityDomainException("User not found: " + requiredCommand.userId().value() + "."));

        boolean revoked = user.revokeRole(requiredCommand.roleId());

        if (!revoked) {
            throw new RoleAssignmentNotAllowedException(
                    "User does not have role: " + requiredCommand.roleId().value() + "."
            );
        }

        User savedUser = userRepository.save(user);
        return toUserDto(savedUser);
    }

    private UserDto toUserDto(User user) {
        List<RoleDto> roles = roleRepository.findAll().stream()
                .filter(role -> user.hasRole(role.id()))
                .map(this::toRoleDto)
                .toList();

        return new UserDto(user.id(), user.username(), user.emailAddress(), user.status(), user.employeeReference(), roles);
    }

    private RoleDto toRoleDto(Role role) {
        return new RoleDto(role.id(), role.code(), role.name(), role.status(), List.of());
    }

private static <T> T requireNonNull(T value, String fieldName) {
    if (value == null) {
        throw new InvalidValueObjectException(fieldName + " must not be null.");
    }
    return value;
}
}
