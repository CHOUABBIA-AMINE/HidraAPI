/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : GrantPermissionToRoleServiceTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Class
 * @Layer       : Identity Application Test
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.application.service
 *
 * @Description : Tests identity permission grant application service.
 *
 */
package dz.sh.hidra.modules.identity.application.service;

import dz.sh.hidra.kernel.domain.event.DomainEvent;
import dz.sh.hidra.modules.identity.application.command.GrantPermissionToRoleCommand;
import dz.sh.hidra.modules.identity.application.dto.RoleDto;
import dz.sh.hidra.modules.identity.application.port.out.DomainEventPublisherPort;
import dz.sh.hidra.modules.identity.application.port.out.PermissionRepository;
import dz.sh.hidra.modules.identity.application.port.out.RoleRepository;
import dz.sh.hidra.modules.identity.domain.exception.IdentityDomainException;
import dz.sh.hidra.modules.identity.domain.model.Permission;
import dz.sh.hidra.modules.identity.domain.model.Role;
import dz.sh.hidra.modules.identity.domain.value.PermissionCode;
import dz.sh.hidra.modules.identity.domain.value.PermissionId;
import dz.sh.hidra.modules.identity.domain.value.PermissionName;
import dz.sh.hidra.modules.identity.domain.value.RoleCode;
import dz.sh.hidra.modules.identity.domain.value.RoleId;
import dz.sh.hidra.modules.identity.domain.value.RoleName;
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
 * Tests identity permission grant application service.
 *
 * <p>Business role: verifies granting catalog permissions to roles through the
 * application use case.</p>
 *
 * <p>Architecture role: application service test using in-memory application ports. It
 * does not depend on Spring, JPA, platform security, REST controllers, or organization
 * modules.</p>
 *
 * <p>Validation responsibility: covers successful grant/event publication and missing
 * permission rejection.</p>
 *
 * <p>Usage: executed by the identity application test suite.</p>
 */
class GrantPermissionToRoleServiceTest {

    private static final Clock FIXED_CLOCK = Clock.fixed(
            Instant.parse("2026-05-30T10:15:30Z"),
            ZoneOffset.UTC
    );

    @Test
    void grantPermissionToRoleShouldGrantPermissionAndPublishEvent() {
        InMemoryRoleRepository roleRepository = new InMemoryRoleRepository();
        InMemoryPermissionRepository permissionRepository = new InMemoryPermissionRepository();
        CapturingDomainEventPublisher publisher = new CapturingDomainEventPublisher();

        Role role = role();
        Permission permission = permission();
        roleRepository.save(role);
        permissionRepository.save(permission);

        GrantPermissionToRoleService service = new GrantPermissionToRoleService(
                roleRepository,
                permissionRepository,
                publisher,
                FIXED_CLOCK
        );

        RoleDto result = service.grantPermissionToRole(
                new GrantPermissionToRoleCommand(role.id(), permission.code())
        );

        assertEquals(1, result.permissions().size());
        assertEquals("identity:user:create", result.permissions().get(0).code().value());
        assertEquals(1, publisher.events.size());
        assertEquals("identity.permission.granted-to-role", publisher.events.get(0).eventType());
    }

    @Test
    void grantPermissionToRoleShouldRejectMissingPermission() {
        InMemoryRoleRepository roleRepository = new InMemoryRoleRepository();
        Role role = role();
        roleRepository.save(role);

        GrantPermissionToRoleService service = new GrantPermissionToRoleService(
                roleRepository,
                new InMemoryPermissionRepository(),
                new CapturingDomainEventPublisher(),
                FIXED_CLOCK
        );

        assertThrows(
                IdentityDomainException.class,
                () -> service.grantPermissionToRole(
                        new GrantPermissionToRoleCommand(role.id(), PermissionCode.of("identity:user:create"))
                )
        );
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

    private static final class InMemoryPermissionRepository implements PermissionRepository {

        private final Map<String, Permission> permissionsById = new LinkedHashMap<>();

        @Override
        public Permission save(Permission permission) {
            permissionsById.put(permission.id().value(), permission);
            return permission;
        }

        @Override
        public Optional<Permission> findById(PermissionId permissionId) {
            return Optional.ofNullable(permissionsById.get(permissionId.value()));
        }

        @Override
        public Optional<Permission> findByCode(PermissionCode permissionCode) {
            return permissionsById.values().stream()
                    .filter(permission -> permission.code().equals(permissionCode))
                    .findFirst();
        }

        @Override
        public List<Permission> findAll() {
            return new ArrayList<>(permissionsById.values());
        }

        @Override
        public boolean existsByCode(PermissionCode permissionCode) {
            return findByCode(permissionCode).isPresent();
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
