/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AssignRoleToUserService
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Class
 * @Layer       : Application
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.application.service
 *
 * @Description : Application service assigning roles to identity users.
 *
 */
package dz.sh.hidra.modules.identity.application.service;

import dz.sh.hidra.kernel.domain.exception.InvalidValueObjectException;
import dz.sh.hidra.modules.identity.application.command.AssignRoleToUserCommand;
import dz.sh.hidra.modules.identity.application.dto.RoleDto;
import dz.sh.hidra.modules.identity.application.dto.UserDto;
import dz.sh.hidra.modules.identity.application.port.in.AssignRoleToUserUseCase;
import dz.sh.hidra.modules.identity.application.port.out.DomainEventPublisherPort;
import dz.sh.hidra.modules.identity.application.port.out.RoleRepository;
import dz.sh.hidra.modules.identity.application.port.out.UserRepository;
import dz.sh.hidra.modules.identity.domain.event.RoleAssignedToUserEvent;
import dz.sh.hidra.modules.identity.domain.exception.IdentityDomainException;
import dz.sh.hidra.modules.identity.domain.model.Role;
import dz.sh.hidra.modules.identity.domain.model.User;
import dz.sh.hidra.modules.identity.domain.service.RoleAssignmentDomainService;

import java.time.Clock;
import java.time.Instant;
import java.util.List;

/**
 * Application service assigning roles to identity users.
 *
 * <p>Business role: assigns an active role to an active user when identity role
 * assignment rules allow it.</p>
 *
 * <p>Architecture role: implements the assign-role-to-user inbound port by loading
 * aggregates, delegating assignment rules to the domain service, persisting the user,
 * and publishing a domain event.</p>
 *
 * <p>Validation responsibility: rejects null dependencies and commands, fails for
 * missing user or role, and delegates lifecycle and duplicate-assignment checks to
 * domain code.</p>
 *
 * <p>Usage: called by future API adapters through {@link AssignRoleToUserUseCase}.</p>
 */
public final class AssignRoleToUserService implements AssignRoleToUserUseCase {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final DomainEventPublisherPort domainEventPublisher;
    private final RoleAssignmentDomainService roleAssignmentDomainService;
    private final Clock clock;

    public AssignRoleToUserService(
            UserRepository userRepository,
            RoleRepository roleRepository,
            DomainEventPublisherPort domainEventPublisher,
            RoleAssignmentDomainService roleAssignmentDomainService,
            Clock clock
    ) {
        this.userRepository = requireNonNull(userRepository, "UserRepository");
        this.roleRepository = requireNonNull(roleRepository, "RoleRepository");
        this.domainEventPublisher = requireNonNull(domainEventPublisher, "DomainEventPublisherPort");
        this.roleAssignmentDomainService = requireNonNull(roleAssignmentDomainService, "RoleAssignmentDomainService");
        this.clock = requireNonNull(clock, "Clock");
    }

    @Override
    public UserDto assignRoleToUser(AssignRoleToUserCommand command) {
        AssignRoleToUserCommand requiredCommand = requireNonNull(command, "AssignRoleToUserCommand");

        User user = userRepository.findById(requiredCommand.userId())
                .orElseThrow(() -> new IdentityDomainException("User not found: " + requiredCommand.userId().value() + "."));

        Role role = roleRepository.findById(requiredCommand.roleId())
                .orElseThrow(() -> new IdentityDomainException("Role not found: " + requiredCommand.roleId().value() + "."));

        roleAssignmentDomainService.assignRole(user, role, clock);

        User savedUser = userRepository.save(user);
        Instant occurredAt = Instant.now(clock);
        domainEventPublisher.publish(
                RoleAssignedToUserEvent.newEvent(savedUser.id(), role.id(), role.code(), occurredAt)
        );

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
