/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : SuspendUserService
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Class
 * @Layer       : Application
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.application.service
 *
 * @Description : Application service suspending identity users.
 *
 */
package dz.sh.hidra.modules.identity.application.service;

import dz.sh.hidra.kernel.domain.exception.InvalidValueObjectException;
import dz.sh.hidra.modules.identity.application.command.SuspendUserCommand;
import dz.sh.hidra.modules.identity.application.dto.RoleDto;
import dz.sh.hidra.modules.identity.application.dto.UserDto;
import dz.sh.hidra.modules.identity.application.port.in.SuspendUserUseCase;
import dz.sh.hidra.modules.identity.application.port.out.DomainEventPublisherPort;
import dz.sh.hidra.modules.identity.application.port.out.RoleRepository;
import dz.sh.hidra.modules.identity.application.port.out.UserRepository;
import dz.sh.hidra.modules.identity.domain.event.UserSuspendedEvent;
import dz.sh.hidra.modules.identity.domain.exception.IdentityDomainException;
import dz.sh.hidra.modules.identity.domain.model.Role;
import dz.sh.hidra.modules.identity.domain.model.User;

import java.time.Clock;
import java.time.Instant;
import java.util.List;

/**
 * Application service suspending identity users.
 *
 * <p>Business role: moves an active user into suspended lifecycle state.</p>
 *
 * <p>Architecture role: implements the suspend-user inbound port and coordinates user
 * loading, aggregate mutation, persistence, response projection, and event publishing.</p>
 *
 * <p>Validation responsibility: rejects null dependencies and commands, fails for
 * missing users, and delegates lifecycle rules to the user aggregate.</p>
 *
 * <p>Usage: called by future API adapters through {@link SuspendUserUseCase}.</p>
 */
public final class SuspendUserService implements SuspendUserUseCase {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final DomainEventPublisherPort domainEventPublisher;
    private final Clock clock;

    public SuspendUserService(
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
    public UserDto suspendUser(SuspendUserCommand command) {
        SuspendUserCommand requiredCommand = requireNonNull(command, "SuspendUserCommand");

        User user = userRepository.findById(requiredCommand.userId())
                .orElseThrow(() -> new IdentityDomainException("User not found: " + requiredCommand.userId().value() + "."));

        user.suspend();
        User savedUser = userRepository.save(user);
        Instant occurredAt = Instant.now(clock);
        domainEventPublisher.publish(UserSuspendedEvent.newEvent(savedUser.id(), occurredAt));

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
