/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AssignRoleToUserServiceTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Class
 * @Layer       : Identity Application Test
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.application.service
 *
 * @Description : Tests identity role assignment application service.
 *
 */
package dz.sh.hidra.modules.identity.application.service;

import dz.sh.hidra.kernel.domain.event.DomainEvent;
import dz.sh.hidra.modules.identity.application.command.AssignRoleToUserCommand;
import dz.sh.hidra.modules.identity.application.dto.UserDto;
import dz.sh.hidra.modules.identity.application.port.out.DomainEventPublisherPort;
import dz.sh.hidra.modules.identity.application.port.out.RoleRepository;
import dz.sh.hidra.modules.identity.application.port.out.UserRepository;
import dz.sh.hidra.modules.identity.domain.exception.IdentityDomainException;
import dz.sh.hidra.modules.identity.domain.exception.UserLifecycleException;
import dz.sh.hidra.modules.identity.domain.model.Role;
import dz.sh.hidra.modules.identity.domain.model.User;
import dz.sh.hidra.modules.identity.domain.service.RoleAssignmentDomainService;
import dz.sh.hidra.modules.identity.domain.value.EmailAddress;
import dz.sh.hidra.modules.identity.domain.value.RoleCode;
import dz.sh.hidra.modules.identity.domain.value.RoleId;
import dz.sh.hidra.modules.identity.domain.value.RoleName;
import dz.sh.hidra.modules.identity.domain.value.UserId;
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
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Tests identity role assignment application service.
 *
 * <p>Business role: verifies assigning active roles to active users through the
 * application use case.</p>
 *
 * <p>Architecture role: application service test using in-memory application ports and
 * the real role assignment domain service.</p>
 *
 * <p>Validation responsibility: covers successful assignment/event publication, missing
 * role rejection, and inactive user rejection.</p>
 *
 * <p>Usage: executed by the identity application test suite.</p>
 */
class AssignRoleToUserServiceTest {

    private static final Clock FIXED_CLOCK = Clock.fixed(
            Instant.parse("2026-05-30T10:15:30Z"),
            ZoneOffset.UTC
    );

    @Test
    void assignRoleToUserShouldAssignRoleAndPublishEvent() {
        InMemoryUserRepository userRepository = new InMemoryUserRepository();
        InMemoryRoleRepository roleRepository = new InMemoryRoleRepository();
        CapturingDomainEventPublisher publisher = new CapturingDomainEventPublisher();

        User user = activeUser();
        Role role = role();
        userRepository.save(user);
        roleRepository.save(role);

        AssignRoleToUserService service = new AssignRoleToUserService(
                userRepository,
                roleRepository,
                publisher,
                new RoleAssignmentDomainService(),
                FIXED_CLOCK
        );

        UserDto result = service.assignRoleToUser(new AssignRoleToUserCommand(user.id(), role.id()));

        assertTrue(userRepository.findById(user.id()).orElseThrow().hasRole(role.id()));
        assertEquals(1, result.roles().size());
        assertEquals(1, publisher.events.size());
        assertEquals("identity.role.assigned-to-user", publisher.events.get(0).eventType());
    }

    @Test
    void assignRoleToUserShouldRejectMissingRole() {
        InMemoryUserRepository userRepository = new InMemoryUserRepository();
        User user = activeUser();
        userRepository.save(user);

        AssignRoleToUserService service = new AssignRoleToUserService(
                userRepository,
                new InMemoryRoleRepository(),
                new CapturingDomainEventPublisher(),
                new RoleAssignmentDomainService(),
                FIXED_CLOCK
        );

        assertThrows(
                IdentityDomainException.class,
                () -> service.assignRoleToUser(new AssignRoleToUserCommand(user.id(), RoleId.of("missing-role")))
        );
    }

    @Test
    void assignRoleToUserShouldRejectInactiveUser() {
        InMemoryUserRepository userRepository = new InMemoryUserRepository();
        InMemoryRoleRepository roleRepository = new InMemoryRoleRepository();
        User user = registeredUser();
        Role role = role();
        userRepository.save(user);
        roleRepository.save(role);

        AssignRoleToUserService service = new AssignRoleToUserService(
                userRepository,
                roleRepository,
                new CapturingDomainEventPublisher(),
                new RoleAssignmentDomainService(),
                FIXED_CLOCK
        );

        assertThrows(
                UserLifecycleException.class,
                () -> service.assignRoleToUser(new AssignRoleToUserCommand(user.id(), role.id()))
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

    private static final class InMemoryUserRepository implements UserRepository {

        private final Map<String, User> usersById = new LinkedHashMap<>();

        @Override
        public User save(User user) {
            usersById.put(user.id().value(), user);
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

        private final Map<String, Role> rolesById = new LinkedHashMap<>();

        @Override
        public Role save(Role role) {
            rolesById.put(role.id().value(), role);
            return role;
        }

        @Override
        public Optional<Role> findById(RoleId roleId) {
            return Optional.ofNullable(rolesById.get(roleId.value()));
        }

        @Override
        public Optional<Role> findByCode(RoleCode roleCode) {
            return rolesById.values().stream().filter(role -> role.code().equals(roleCode)).findFirst();
        }

        @Override
        public List<Role> findAll() {
            return new ArrayList<>(rolesById.values());
        }

        @Override
        public boolean existsByCode(RoleCode roleCode) {
            return findByCode(roleCode).isPresent();
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
