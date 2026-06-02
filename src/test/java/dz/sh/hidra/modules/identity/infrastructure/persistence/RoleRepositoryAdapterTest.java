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
import dz.sh.hidra.modules.identity.infrastructure.persistence.entity.RoleJpaEntity;
import dz.sh.hidra.modules.identity.infrastructure.persistence.mapper.IdentityPersistenceMapper;
import dz.sh.hidra.modules.identity.infrastructure.persistence.repository.RoleJpaRepository;
import dz.sh.hidra.modules.identity.infrastructure.persistence.repository.RoleRepositoryAdapter;
import org.junit.jupiter.api.Test;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneOffset;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Tests identity role persistence adapter.
 *
 * <p>Business role: verifies that identity roles and role-permission assignments can be
 * saved and loaded through the application {@code RoleRepository} outbound port
 * implementation.</p>
 *
 * <p>Architecture role: persistence adapter test using plain JUnit and Mockito. It avoids
 * Spring Boot test slices so the test does not require {@code spring-boot-test-autoconfigure}
 * on the classpath.</p>
 *
 * <p>Validation responsibility: covers save mapping, lookup by identifier, lookup by role
 * code, existence checks, and permission assignment round-trip mapping.</p>
 *
 * <p>Usage: executed by the identity persistence test suite.</p>
 */
class RoleRepositoryAdapterTest {

    private static final Clock FIXED_CLOCK = Clock.fixed(
            Instant.parse("2026-05-30T10:15:30Z"),
            ZoneOffset.UTC
    );

    private final RoleJpaRepository roleJpaRepository = mock(RoleJpaRepository.class);
    private final IdentityPersistenceMapper mapper = new IdentityPersistenceMapper();
    private final RoleRepositoryAdapter adapter = new RoleRepositoryAdapter(roleJpaRepository, mapper);

    @Test
    void saveShouldMapDomainToJpaAndBack() {
        Role role = role();

        when(roleJpaRepository.save(any(RoleJpaEntity.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        Role savedRole = adapter.save(role);

        assertEquals(role.id(), savedRole.id());
        assertEquals(role.code(), savedRole.code());
        verify(roleJpaRepository).save(any(RoleJpaEntity.class));
    }

    @Test
    void findByIdShouldMapPermissionAssignmentsBackToDomain() {
        Role role = role();
        Permission permission = permission();
        role.grantPermission(permission, FIXED_CLOCK);
        RoleJpaEntity entity = mapper.toRoleEntity(role);

        when(roleJpaRepository.findById("role-1")).thenReturn(Optional.of(entity));

        Role foundRole = adapter.findById(RoleId.of("role-1")).orElseThrow();

        assertTrue(foundRole.hasPermission(permission.code()));
        assertEquals(1, foundRole.permissionAssignments().size());
        assertEquals(FIXED_CLOCK.instant(), foundRole.permissionAssignments().get(0).assignedAt());
    }

    @Test
    void findByCodeAndExistsByCodeShouldDelegateToJpaRepository() {
        Role role = role();
        RoleJpaEntity entity = mapper.toRoleEntity(role);

        when(roleJpaRepository.findByCode("IDENTITY_ADMIN")).thenReturn(Optional.of(entity));
        when(roleJpaRepository.existsByCode("IDENTITY_ADMIN")).thenReturn(true);

        assertTrue(adapter.findByCode(RoleCode.of("IDENTITY_ADMIN")).isPresent());
        assertTrue(adapter.existsByCode(RoleCode.of("IDENTITY_ADMIN")));
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
