/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ActivateUserServiceTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Class
 * @Layer       : Identity Application Test
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.application.service
 *
 * @Description : Tests identity user activation application service.
 *
 */
package dz.sh.hidra.modules.identity.application.service;

import dz.sh.hidra.kernel.domain.event.DomainEvent;
import dz.sh.hidra.modules.identity.application.command.ActivateUserCommand;
import dz.sh.hidra.modules.identity.application.dto.UserDto;
import dz.sh.hidra.modules.identity.application.port.out.DomainEventPublisherPort;
import dz.sh.hidra.modules.identity.application.port.out.RoleRepository;
import dz.sh.hidra.modules.identity.application.port.out.UserRepository;
import dz.sh.hidra.modules.identity.domain.exception.IdentityDomainException;
import dz.sh.hidra.modules.identity.domain.model.Role;
import dz.sh.hidra.modules.identity.domain.model.User;
import dz.sh.hidra.modules.identity.domain.value.EmailAddress;
import dz.sh.hidra.modules.identity.domain.value.RoleCode;
import dz.sh.hidra.modules.identity.domain.value.RoleId;
import dz.sh.hidra.modules.identity.domain.value.UserId;
import dz.sh.hidra.modules.identity.domain.value.UserStatus;
import dz.sh.hidra.modules.identity.domain.value.Username;
import org.junit.jupiter.api.Test;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Tests identity user activation application service.
 *
 * <p>Business role: verifies that the activate-user use case moves an existing user to
 * active status.</p>
 *
 * <p>Architecture role: application service test using in-memory application ports. It
 * does not depend on Spring, JPA, platform security, REST controllers, or organization
 * modules.</p>
 *
 * <p>Validation responsibility: covers successful lifecycle change/event publication and
 * missing user rejection.</p>
 *
 * <p>Usage: executed by the identity application test suite.</p>
 */
class ActivateUserServiceTest {

    private static final Clock FIXED_CLOCK = Clock.fixed(
            Instant.parse("2026-05-30T10:15:30Z"),
            ZoneOffset.UTC
    );

    @Test
    void activateShouldActivateExistingUserAndPublishEvent() {
        InMemoryUserRepository userRepository = new InMemoryUserRepository();
        User user = registeredUser();
        userRepository.save(user);
        CapturingDomainEventPublisher publisher = new CapturingDomainEventPublisher();

        ActivateUserService service = new ActivateUserService(
                userRepository,
                new InMemoryRoleRepository(),
                publisher,
                FIXED_CLOCK
        );

        UserDto result = service.activate(new ActivateUserCommand(user.id()));

        assertEquals(UserStatus.ACTIVE, result.status());
        assertEquals(2, userRepository.savedUsers);
        assertEquals(1, publisher.events.size());
        assertEquals("identity.user.activated", publisher.events.get(0).eventType());
    }

    @Test
    void activateShouldRejectMissingUser() {
        ActivateUserService service = new ActivateUserService(
                new InMemoryUserRepository(),
                new InMemoryRoleRepository(),
                new CapturingDomainEventPublisher(),
                FIXED_CLOCK
        );

        assertThrows(
                IdentityDomainException.class,
                () -> service.activate(new ActivateUserCommand(UserId.of("missing-user")))
        );
    }

    private static User registeredUser() {
        return User.register(
                UserId.of("user-1"),
                Username.of("abir.medjerab"),
                EmailAddress.of("abir.medjerab@sonatrach.dz")
        );
    }

    private static final class InMemoryUserRepository implements UserRepository {

        private final Map<String, User> usersById = new LinkedHashMap<>();
        private int savedUsers;

        @Override
        public User save(User user) {
            usersById.put(user.id().value(), user);
            savedUsers++;
            return user;
        }

        @Override
        public Optional<User> findById(UserId userId) {
            return Optional.ofNullable(usersById.get(userId.value()));
        }

        @Override
        public Optional<User> findByUsername(Username username) {
            return usersById.values().stream().filter(user -> user.username().equals(username)).findFirst();
        }

        @Override
        public Optional<User> findByEmailAddress(EmailAddress emailAddress) {
            return usersById.values().stream().filter(user -> user.emailAddress().equals(emailAddress)).findFirst();
        }

        @Override
        public List<User> findAll() {
            return new ArrayList<>(usersById.values());
        }

        @Override
        public boolean existsByUsername(Username username) {
            return findByUsername(username).isPresent();
        }

        @Override
        public boolean existsByEmailAddress(EmailAddress emailAddress) {
            return findByEmailAddress(emailAddress).isPresent();
        }
    }

    private static final class InMemoryRoleRepository implements RoleRepository {

        @Override
        public Role save(Role role) {
            return role;
        }

        @Override
        public Optional<Role> findById(RoleId roleId) {
            return Optional.empty();
        }

        @Override
        public Optional<Role> findByCode(RoleCode roleCode) {
            return Optional.empty();
        }

        @Override
        public List<Role> findAll() {
            return List.of();
        }

        @Override
        public boolean existsByCode(RoleCode roleCode) {
            return false;
        }
    }

    private static final class CapturingDomainEventPublisher implements DomainEventPublisherPort {

        private final List<DomainEvent> events = new ArrayList<>();

        @Override
        public void publish(DomainEvent event) {
            events.add(event);
        }

        @Override
        public void publishAll(List<? extends DomainEvent> events) {
            this.events.addAll(events);
        }
    }
}
