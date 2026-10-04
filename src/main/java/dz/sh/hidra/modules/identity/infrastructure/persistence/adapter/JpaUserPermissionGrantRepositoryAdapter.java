/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaUserPermissionGrantRepositoryAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-04
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.infrastructure.persistence.adapter
 *
 * @Description : Database-backed repository adapter for UserPermissionGrant.
 *
 */
package dz.sh.hidra.modules.identity.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.identity.application.port.out.UserPermissionGrantRepositoryPort;
import dz.sh.hidra.modules.identity.domain.exception.InvalidIdentityValueException;
import dz.sh.hidra.modules.identity.domain.model.UserPermissionGrant;
import dz.sh.hidra.modules.identity.domain.value.GrantStatus;
import dz.sh.hidra.modules.identity.domain.value.PermissionStatus;
import dz.sh.hidra.modules.identity.infrastructure.persistence.mapper.IdentityPersistenceMapper;
import dz.sh.hidra.modules.identity.infrastructure.persistence.repository.PermissionJpaRepository;
import dz.sh.hidra.modules.identity.infrastructure.persistence.repository.UserPermissionGrantJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

/**
 * Database-backed repository adapter for UserPermissionGrant.
 */
@Component
public class JpaUserPermissionGrantRepositoryAdapter implements UserPermissionGrantRepositoryPort {

    private final UserPermissionGrantJpaRepository repository;
    private final PermissionJpaRepository permissionRepository;

    public JpaUserPermissionGrantRepositoryAdapter(
            UserPermissionGrantJpaRepository repository,
            PermissionJpaRepository permissionRepository
    ) {
        this.repository = Objects.requireNonNull(repository, "UserPermissionGrantJpaRepository must not be null.");
        this.permissionRepository = Objects.requireNonNull(permissionRepository, "PermissionJpaRepository must not be null.");
    }

    @Override
    public UserPermissionGrant save(UserPermissionGrant model) {
        Objects.requireNonNull(model, "UserPermissionGrant must not be null.");
        requireActivePermission(model.permissionId(), model.status());
        return IdentityPersistenceMapper.toDomain(repository.save(IdentityPersistenceMapper.toEntity(model)));
    }

    private void requireActivePermission(String permissionId, GrantStatus status) {
        if (status == GrantStatus.ACTIVE
                && !permissionRepository.existsByIdAndStatus(permissionId, PermissionStatus.ACTIVE)) {
            throw new InvalidIdentityValueException(
                    "ACTIVE UserPermissionGrant must reference an ACTIVE Permission."
            );
        }
    }

    @Override
    public Optional<UserPermissionGrant> findById(String id) {
        return repository.findById(id).map(IdentityPersistenceMapper::toDomain);
    }
}
