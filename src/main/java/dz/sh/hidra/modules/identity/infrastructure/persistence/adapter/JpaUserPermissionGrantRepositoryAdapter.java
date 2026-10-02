/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaUserPermissionGrantRepositoryAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
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
import dz.sh.hidra.modules.identity.domain.model.UserPermissionGrant;
import dz.sh.hidra.modules.identity.infrastructure.persistence.mapper.IdentityPersistenceMapper;
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

    public JpaUserPermissionGrantRepositoryAdapter(UserPermissionGrantJpaRepository repository) {
        this.repository = Objects.requireNonNull(repository, "UserPermissionGrantJpaRepository must not be null.");
    }

    @Override
    public UserPermissionGrant save(UserPermissionGrant model) {
        return IdentityPersistenceMapper.toDomain(repository.save(IdentityPersistenceMapper.toEntity(model)));
    }

    @Override
    public Optional<UserPermissionGrant> findById(String id) {
        return repository.findById(id).map(IdentityPersistenceMapper::toDomain);
    }
}
