/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : EvaluatePermissionServiceTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Class
 * @Layer       : Identity Application Test
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.application.service
 *
 * @Description : Tests identity permission evaluation application service.
 *
 */
package dz.sh.hidra.modules.identity.application.service;

import dz.sh.hidra.modules.identity.application.dto.PermissionDecisionDto;
import dz.sh.hidra.modules.identity.application.port.out.RoleRepository;
import dz.sh.hidra.modules.identity.application.port.out.UserRepository;
import dz.sh.hidra.modules.identity.application.query.CheckPermissionQuery;
import dz.sh.hidra.modules.identity.domain.exception.IdentityDomainException;
import dz.sh.hidra.modules.identity.domain.model.Permission;
import dz.sh.hidra.modules.identity.domain.model.Role;
import dz.sh.hidra.modules.identity.domain.model.User;
import dz.sh.hidra.modules.identity.domain.service.PermissionEvaluationDomainService;
import dz.sh.hidra.modules.identity.domain.value.EmailAddress;
import dz.sh.hidra.modules.identity.domain.value.PermissionCode;
import dz.sh.hidra.modules.identity.domain.value.PermissionId;
import dz.sh.hidra.modules.identity.domain.value.PermissionName;
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

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Tests identity permission evaluation application service.
 *
 * <p>Business role: verifies whether users receive requested permissions through their
 * assigned roles.</p>
 *
 * <p>Architecture role: application service test using in-memory application ports and
 * the real permission evaluation domain service.</p>
 *
 * <p>Validation responsibility: covers granted decisions, denied decisions, and missing
 * user rejection.</p>
 *
 * <p>Usage: executed by the identity application test suite.</p>
 */
class EvaluatePermissionServiceTest {

    private static final Clock FIXED_CLOCK = Clock.fixed(
            Instant.parse("2026-05-30T10:15:30Z"),
            ZoneOffset.UTC
    );

    @Test
    void evaluatePermissionShouldReturnGrantedWhenAssignedRoleGrantsPermission() {
        InMemoryUserRepository userRepository = new InMemoryUserRepository();
        InMemoryRoleRepository roleRepository = new InMemoryRoleRepository();

        User user = activeUser();
        Role role = role();
        Permission permission = permission();
        role.grantPermission(permission, FIXED_CLOCK);
        user.assignRole(role, FIXED_CLOCK);
        userRepository.save(user);
        roleRepository.save(role);

        EvaluatePermissionService service = new EvaluatePermissionService(
                userRepository,
                roleRepository,
                new PermissionEvaluationDomainService()
        );

        PermissionDecisionDto result = service.evaluatePermission(
                new CheckPermissionQuery(user.id(), permission.code())
        );

        assertTrue(result.granted());
    }

    @Test
    void evaluatePermissionShouldReturnDeniedWhenNoAssignedRoleGrantsPermission() {
        InMemoryUserRepository userRepository = new InMemoryUserRepository();
        InMemoryRoleRepository roleRepository = new InMemoryRoleRepository();

        User user = activeUser();
        Role role = role();
        user.assignRole(role, FIXED_CLOCK);
        userRepository.save(user);
        roleRepository.save(role);

        EvaluatePermissionService service = new EvaluatePermissionService(
                userRepository,
                roleRepository,
                new PermissionEvaluationDomainService()
        );

        PermissionDecisionDto result = service.evaluatePermission(
                new CheckPermissionQuery(user.id(), PermissionCode.of("identity:user:create"))
        );

        assertFalse(result.granted());
    }

    @Test
    void evaluatePermissionShouldRejectMissingUser() {
        EvaluatePermissionService service = new EvaluatePermissionService(
                new InMemoryUserRepository(),
                new InMemoryRoleRepository(),
                new PermissionEvaluationDomainService()
        );

        assertThrows(
                IdentityDomainException.class,
                () -> service.evaluatePermission(
                        new CheckPermissionQuery(UserId.of("missing-user"), PermissionCode.of("identity:user:create"))
                )
        );
    }

    private static User activeUser() {
        User user = User.register(
                UserId.of("user-1"),
                Username.of("abir.medjerab"),
                EmailAddress.of("abir.medjerab@sonatrach.dz")
        );
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

    private static Permission permission() {
        return Permission.create(
                PermissionId.of("permission-1"),
                PermissionCode.of("identity:user:create"),
                PermissionName.of("Create identity user"),
                "Allows creating identity users."
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
}
