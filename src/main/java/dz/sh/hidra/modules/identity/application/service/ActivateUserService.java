/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ActivateUserService
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Class
 * @Layer       : Application
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.application.service
 *
 * @Description : Application service activating identity users.
 *
 */
package dz.sh.hidra.modules.identity.application.service;

import dz.sh.hidra.kernel.domain.exception.InvalidValueObjectException;
import dz.sh.hidra.modules.identity.application.command.ActivateUserCommand;
import dz.sh.hidra.modules.identity.application.dto.RoleDto;
import dz.sh.hidra.modules.identity.application.dto.UserDto;
import dz.sh.hidra.modules.identity.application.port.in.ActivateUserUseCase;
import dz.sh.hidra.modules.identity.application.port.out.DomainEventPublisherPort;
import dz.sh.hidra.modules.identity.application.port.out.RoleRepository;
import dz.sh.hidra.modules.identity.application.port.out.UserRepository;
import dz.sh.hidra.modules.identity.domain.event.UserActivatedEvent;
import dz.sh.hidra.modules.identity.domain.exception.IdentityDomainException;
import dz.sh.hidra.modules.identity.domain.model.Role;
import dz.sh.hidra.modules.identity.domain.model.User;

import java.time.Clock;
import java.time.Instant;
import java.util.List;

/**
 * Application service activating identity users.
 *
 * <p>Business role: moves a user into the active lifecycle state so identity permission
 * evaluation can consider that user.</p>
 *
 * <p>Architecture role: implements the activate-user inbound port and coordinates user
 * loading, aggregate mutation, persistence, response projection, and event publishing.</p>
 *
 * <p>Validation responsibility: rejects null dependencies and commands, fails for
 * missing users, and delegates lifecycle rules to the user aggregate.</p>
 *
 * <p>Usage: called by future API adapters through {@link ActivateUserUseCase}.</p>
 */
public final class ActivateUserService implements ActivateUserUseCase {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final DomainEventPublisherPort domainEventPublisher;
    private final Clock clock;

    public ActivateUserService(
            UserRepository userRepository,
            RoleRepository roleRepository,
            DomainEventPublisherPort domainEventPublisher,
            Clock clock
    ) {
        this.userRepository = requireNonNull(userRepository, "UserRepository");
        this.roleRepository = requireNonNull(roleRepository, "RoleRepository");
        this.domainEventPublisher = requireNonNull(domainEventPublisher, "DomainEventPublisherPort");
        this.clock = requireNonNull(clock, "Clock");
    }

    @Override
    public UserDto activate(ActivateUserCommand command) {
        ActivateUserCommand requiredCommand = requireNonNull(command, "ActivateUserCommand");

        User user = userRepository.findById(requiredCommand.userId())
                .orElseThrow(() -> new IdentityDomainException("User not found: " + requiredCommand.userId().value() + "."));

        user.activate();
        User savedUser = userRepository.save(user);
        Instant occurredAt = Instant.now(clock);
        domainEventPublisher.publish(UserActivatedEvent.newEvent(savedUser.id(), occurredAt));

        return toUserDto(savedUser);
    }

    private UserDto toUserDto(User user) {
        List<RoleDto> roles = roleRepository.findAll().stream()
                .filter(role -> user.hasRole(role.id()))
                .map(this::toRoleDto)
                .toList();

        return new UserDto(
                user.id(),
                user.username(),
                user.emailAddress(),
                user.status(),
                user.employeeReference(),
                roles
        );
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
