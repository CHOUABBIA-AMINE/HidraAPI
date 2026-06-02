/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : RoleRepositoryAdapterTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Class
 * @Layer       : Identity Persistence Test
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.infrastructure.persistence
 *
 * @Description : Tests identity role persistence adapter.
 *
 */
package dz.sh.hidra.modules.identity.infrastructure.persistence;

import dz.sh.hidra.modules.identity.domain.model.Permission;
import dz.sh.hidra.modules.identity.domain.model.Role;
import dz.sh.hidra.modules.identity.domain.value.PermissionCode;
import dz.sh.hidra.modules.identity.domain.value.PermissionId;
import dz.sh.hidra.modules.identity.domain.value.PermissionName;
import dz.sh.hidra.modules.identity.domain.value.RoleCode;
import dz.sh.hidra.modules.identity.domain.value.RoleId;
import dz.sh.hidra.modules.identity.domain.value.RoleName;
import dz.sh.hidra.modules.identity.domain.value.RoleStatus;
import dz.sh.hidra.modules.identity.infrastructure.persistence.entity.PermissionJpaEntity;
import dz.sh.hidra.modules.identity.infrastructure.persistence.mapper.IdentityPersistenceMapper;
import dz.sh.hidra.modules.identity.infrastructure.persistence.repository.PermissionJpaRepository;
import dz.sh.hidra.modules.identity.infrastructure.persistence.repository.RoleRepositoryAdapter;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneOffset;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Tests identity role persistence adapter.
 *
 * <p>Business role: verifies that identity roles and role-permission assignments can be
 * persisted and loaded through the application {@code RoleRepository} outbound port
 * implementation.</p>
 *
 * <p>Architecture role: persistence adapter test using Spring Data JPA test support. It
 * exercises the infrastructure adapter and mapper without importing REST controllers,
 * platform security plumbing, or organization modules.</p>
 *
 * <p>Validation responsibility: covers save/load by identifier, lookup by role code,
 * uniqueness checks, and permission assignment round-trip.</p>
 *
 * <p>Usage: executed by the identity persistence test suite.</p>
 */
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
@Import({IdentityPersistenceMapper.class, RoleRepositoryAdapter.class})
class RoleRepositoryAdapterTest {

    private static final Clock FIXED_CLOCK = Clock.fixed(
            Instant.parse("2026-05-30T10:15:30Z"),
            ZoneOffset.UTC
    );

    @Autowired
    private RoleRepositoryAdapter roleRepositoryAdapter;

    @Autowired
    private PermissionJpaRepository permissionJpaRepository;

    @Test
    void saveShouldPersistAndLoadRoleById() {
        Role role = role();

        Role savedRole = roleRepositoryAdapter.save(role);

        Optional<Role> foundRole = roleRepositoryAdapter.findById(savedRole.id());

        assertTrue(foundRole.isPresent());
        assertEquals(savedRole.id(), foundRole.orElseThrow().id());
        assertEquals(RoleCode.of("IDENTITY_ADMIN"), foundRole.orElseThrow().code());
        assertEquals(RoleName.of("Identity Administrator"), foundRole.orElseThrow().name());
        assertEquals(RoleStatus.ACTIVE, foundRole.orElseThrow().status());
    }

    @Test
    void findByCodeAndExistsByCodeShouldReflectPersistedRole() {
        Role savedRole = roleRepositoryAdapter.save(role());

        Optional<Role> byCode = roleRepositoryAdapter.findByCode(RoleCode.of("IDENTITY_ADMIN"));

        assertTrue(byCode.isPresent());
        assertEquals(savedRole.id(), byCode.orElseThrow().id());
        assertTrue(roleRepositoryAdapter.existsByCode(RoleCode.of("IDENTITY_ADMIN")));
    }

    @Test
    void saveShouldPersistRolePermissionAssignments() {
        Permission permission = permission();
        permissionJpaRepository.save(
                PermissionJpaEntity.of(
                        permission.id().value(),
                        permission.code().value(),
                        permission.name().value(),
                        permission.description()
                )
        );

        Role role = role();
        role.grantPermission(permission, FIXED_CLOCK);

        Role savedRole = roleRepositoryAdapter.save(role);
        Role foundRole = roleRepositoryAdapter.findById(savedRole.id()).orElseThrow();

        assertTrue(foundRole.hasPermission(permission.code()));
        assertEquals(1, foundRole.permissionAssignments().size());
        assertEquals(FIXED_CLOCK.instant(), foundRole.permissionAssignments().get(0).assignedAt());
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
}
