/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : PermissionSemanticRemediationTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-04
 *
 * @Type        : Class
 * @Layer       : Identity Test
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.semantic
 *
 * @Description : Verifies HMR-011 Permission semantic remediation.
 *
 */
package dz.sh.hidra.modules.identity.semantic;

import dz.sh.hidra.modules.identity.domain.exception.InvalidIdentityValueException;
import dz.sh.hidra.modules.identity.domain.model.Permission;
import dz.sh.hidra.modules.identity.domain.model.RolePermissionGrant;
import dz.sh.hidra.modules.identity.domain.value.GrantEffect;
import dz.sh.hidra.modules.identity.domain.value.GrantStatus;
import dz.sh.hidra.modules.identity.domain.value.PermissionStatus;
import dz.sh.hidra.modules.identity.infrastructure.persistence.adapter.JpaPermissionRepositoryAdapter;
import dz.sh.hidra.modules.identity.infrastructure.persistence.adapter.JpaRolePermissionGrantRepositoryAdapter;
import dz.sh.hidra.modules.identity.infrastructure.persistence.entity.PermissionJpaEntity;
import dz.sh.hidra.modules.identity.infrastructure.persistence.entity.RolePermissionGrantJpaEntity;
import dz.sh.hidra.modules.identity.infrastructure.persistence.repository.PermissionJpaRepository;
import dz.sh.hidra.modules.identity.infrastructure.persistence.repository.RolePermissionGrantJpaRepository;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Instant;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class PermissionSemanticRemediationTest {

    private static final Instant NOW = Instant.parse("2026-10-04T00:00:00Z");

    @Test
    void rejectsNonCanonicalPermissionCodeAndMissingRequiredFields() {
        assertThatThrownBy(() -> permission("TOPOLOGY.FACILITY.CREATE", "topology", "facility", "create", NOW, NOW))
                .isInstanceOf(InvalidIdentityValueException.class)
                .hasMessageContaining("lower-case");

        assertThatThrownBy(() -> permission("topology:facility:create", "topology", null, "create", NOW, NOW))
                .isInstanceOf(InvalidIdentityValueException.class)
                .hasMessageContaining("resource type");

        assertThatThrownBy(() -> permission("topology:facility:create", "topology", "facility", "create", null, NOW))
                .isInstanceOf(InvalidIdentityValueException.class)
                .hasMessageContaining("createdAt");
    }

    @Test
    void permissionRepositoryRejectsDuplicateCodeBeforeSave() {
        var repository = mock(PermissionJpaRepository.class);
        var adapter = new JpaPermissionRepositoryAdapter(repository);
        var model = permission("topology:facility:create", "topology", "facility", "create", NOW, NOW);

        when(repository.existsByCodeAndIdNot(model.code(), model.id())).thenReturn(true);

        assertThatThrownBy(() -> adapter.save(model))
                .isInstanceOf(InvalidIdentityValueException.class)
                .hasMessageContaining("unique");
        verify(repository, never()).save(any());
    }

    @Test
    void activeRoleGrantRejectsNonActivePermission() {
        var grantRepository = mock(RolePermissionGrantJpaRepository.class);
        var permissionRepository = mock(PermissionJpaRepository.class);
        var adapter = new JpaRolePermissionGrantRepositoryAdapter(grantRepository, permissionRepository);
        var grant = new RolePermissionGrant(
                "grant-1", "role-1", "permission-1", GrantEffect.GRANT,
                null, NOW, null, GrantStatus.ACTIVE, NOW
        );

        when(permissionRepository.existsByIdAndStatus("permission-1", PermissionStatus.ACTIVE)).thenReturn(false);

        assertThatThrownBy(() -> adapter.save(grant))
                .isInstanceOf(InvalidIdentityValueException.class)
                .hasMessageContaining("ACTIVE Permission");
        verify(grantRepository, never()).save(any());
    }

    @Test
    void disabledPermissionDoesNotExposeEffectiveGrantPermissionId() throws Exception {
        PermissionJpaEntity disabledPermission = new PermissionJpaEntity(
                "permission-1", "topology:facility:create", null, null, null, null,
                "topology", "facility", "create", false, PermissionStatus.DISABLED, NOW, NOW
        );
        RolePermissionGrantJpaEntity grant = new RolePermissionGrantJpaEntity(
                "grant-1", "role-1", "permission-1", GrantEffect.GRANT,
                null, NOW, null, GrantStatus.ACTIVE, NOW
        );

        Field permissionField = RolePermissionGrantJpaEntity.class.getDeclaredField("permission");
        permissionField.setAccessible(true);
        permissionField.set(grant, disabledPermission);

        assertThat(grant.permissionId()).isNull();
        assertThat(grant.storedPermissionId()).isEqualTo("permission-1");
    }

    @Test
    void migrationEnforcesPermissionContractAndGrantLifecycle() throws Exception {
        String sql = Files.readString(Path.of(
                "src/main/resources/db/migration/V20261004_011__hmr_011_identity_permission.sql"
        ));

        assertThat(sql).contains("CREATE UNIQUE INDEX uk_hmr011_identity_permission_code");
        assertThat(sql).contains("^[a-z0-9-]+:[a-z0-9-]+:[a-z0-9-]+$");
        assertThat(sql).contains("ALTER COLUMN resource_type SET NOT NULL");
        assertThat(sql).contains("hmr011_require_active_identity_permission");
        assertThat(sql).contains("hidra_identity_authorization_delegation_grant");
        assertThat(sql).contains("hidra_identity_external_permission_mapping");
    }

    private static Permission permission(
            String code,
            String permissionDomain,
            String resourceType,
            String action,
            Instant createdAt,
            Instant updatedAt
    ) {
        return new Permission(
                "permission-1", code, null, null, null, null,
                permissionDomain, resourceType, action, false,
                PermissionStatus.ACTIVE, createdAt, updatedAt
        );
    }
}
