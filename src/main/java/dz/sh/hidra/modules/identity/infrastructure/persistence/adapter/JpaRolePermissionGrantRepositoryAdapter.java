/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaRolePermissionGrantRepositoryAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-04
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.infrastructure.persistence.adapter
 *
 * @Description : Database-backed repository adapter for RolePermissionGrant.
 *
 */
package dz.sh.hidra.modules.identity.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.identity.application.port.out.RolePermissionGrantRepositoryPort;
import dz.sh.hidra.modules.identity.domain.exception.InvalidIdentityValueException;
import dz.sh.hidra.modules.identity.domain.model.RolePermissionGrant;
import dz.sh.hidra.modules.identity.domain.value.GrantStatus;
import dz.sh.hidra.modules.identity.domain.value.PermissionStatus;
import dz.sh.hidra.modules.identity.infrastructure.persistence.mapper.IdentityPersistenceMapper;
import dz.sh.hidra.modules.identity.infrastructure.persistence.repository.PermissionJpaRepository;
import dz.sh.hidra.modules.identity.infrastructure.persistence.repository.RolePermissionGrantJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

/**
 * Database-backed repository adapter for RolePermissionGrant.
 */
@Component
public class JpaRolePermissionGrantRepositoryAdapter implements RolePermissionGrantRepositoryPort {

    private final RolePermissionGrantJpaRepository repository;
    private final PermissionJpaRepository permissionRepository;

    public JpaRolePermissionGrantRepositoryAdapter(
            RolePermissionGrantJpaRepository repository,
            PermissionJpaRepository permissionRepository
    ) {
        this.repository = Objects.requireNonNull(repository, "RolePermissionGrantJpaRepository must not be null.");
        this.permissionRepository = Objects.requireNonNull(permissionRepository, "PermissionJpaRepository must not be null.");
    }

    @Override
    public RolePermissionGrant save(RolePermissionGrant model) {
        Objects.requireNonNull(model, "RolePermissionGrant must not be null.");
        requireActivePermission(model.permissionId(), model.status());
        return IdentityPersistenceMapper.toDomain(repository.save(IdentityPersistenceMapper.toEntity(model)));
    }

    private void requireActivePermission(String permissionId, GrantStatus status) {
        if (status == GrantStatus.ACTIVE
                && !permissionRepository.existsByIdAndStatus(permissionId, PermissionStatus.ACTIVE)) {
            throw new InvalidIdentityValueException(
                    "ACTIVE RolePermissionGrant must reference an ACTIVE Permission."
            );
        }
    }

    @Override
    public Optional<RolePermissionGrant> findById(String id) {
        return repository.findById(id).map(IdentityPersistenceMapper::toDomain);
    }
}
