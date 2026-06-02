/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : RegisterUserService
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Class
 * @Layer       : Application
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.application.service
 *
 * @Description : Application service registering identity users.
 *
 */
package dz.sh.hidra.modules.identity.application.service;

import dz.sh.hidra.kernel.domain.exception.BusinessRuleViolationException;
import dz.sh.hidra.kernel.domain.exception.InvalidValueObjectException;
import dz.sh.hidra.modules.identity.application.command.RegisterUserCommand;
import dz.sh.hidra.modules.identity.application.dto.UserDto;
import dz.sh.hidra.modules.identity.application.port.in.RegisterUserUseCase;
import dz.sh.hidra.modules.identity.application.port.out.DomainEventPublisherPort;
import dz.sh.hidra.modules.identity.application.port.out.UserRepository;
import dz.sh.hidra.modules.identity.domain.event.UserRegisteredEvent;
import dz.sh.hidra.modules.identity.domain.model.User;
import dz.sh.hidra.modules.identity.domain.value.UserId;

import java.time.Clock;
import java.time.Instant;
import java.util.List;

/**
 * Application service registering identity users.
 *
 * <p>Business role: creates a HidraAPI security identity when the requested username and
 * email address are unique.</p>
 *
 * <p>Architecture role: implements the register-user inbound port by orchestrating
 * repository access, user aggregate creation, and domain-event publishing. It does not
 * depend on Spring Security plumbing, persistence adapters, or organization structures.</p>
 *
 * <p>Validation responsibility: rejects null dependencies and commands, rejects
 * duplicate usernames and email addresses, and delegates value validation to domain
 * value objects.</p>
 *
 * <p>Usage: called by future API adapters through {@link RegisterUserUseCase}.</p>
 */
public final class RegisterUserService implements RegisterUserUseCase {

    private final UserRepository userRepository;
    private final DomainEventPublisherPort domainEventPublisher;
    private final Clock clock;

    public RegisterUserService(
            UserRepository userRepository,
            DomainEventPublisherPort domainEventPublisher,
            Clock clock
    ) {
        this.userRepository = requireNonNull(userRepository, "UserRepository");
        this.domainEventPublisher = requireNonNull(domainEventPublisher, "DomainEventPublisherPort");
        this.clock = requireNonNull(clock, "Clock");
    }

    @Override
    public UserDto registerUser(RegisterUserCommand command) {
        RegisterUserCommand requiredCommand = requireNonNull(command, "RegisterUserCommand");

        if (userRepository.existsByUsername(requiredCommand.username())) {
            throw new BusinessRuleViolationException(
                    "Username already exists: " + requiredCommand.username().value() + "."
            );
        }

        if (userRepository.existsByEmailAddress(requiredCommand.emailAddress())) {
            throw new BusinessRuleViolationException(
                    "Email address already exists: " + requiredCommand.emailAddress().value() + "."
            );
        }

        User user = User.register(
                UserId.newId(),
                requiredCommand.username(),
                requiredCommand.emailAddress()
        );

        User savedUser = userRepository.save(user);
        Instant occurredAt = Instant.now(clock);

        domainEventPublisher.publish(
                UserRegisteredEvent.newEvent(
                        savedUser.id(),
                        savedUser.username(),
                        savedUser.emailAddress(),
                        occurredAt
                )
        );

        return new UserDto(
                savedUser.id(),
                savedUser.username(),
                savedUser.emailAddress(),
                savedUser.status(),
                savedUser.employeeReference(),
                List.of()
        );
    }

private static <T> T requireNonNull(T value, String fieldName) {
    if (value == null) {
        throw new InvalidValueObjectException(fieldName + " must not be null.");
    }
    return value;
}
}
