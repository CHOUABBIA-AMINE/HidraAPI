/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaPermissionRepositoryAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.infrastructure.persistence.adapter
 *
 * @Description : Database-backed adapter for Permission.
 *
 */
package dz.sh.hidra.modules.identity.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.identity.application.port.out.PermissionRepositoryPort;
import dz.sh.hidra.modules.identity.domain.model.Permission;
import dz.sh.hidra.modules.identity.infrastructure.persistence.mapper.IdentityPersistenceMapper;
import dz.sh.hidra.modules.identity.infrastructure.persistence.repository.PermissionJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

/**
 * Database-backed repository adapter for Permission.
 */
@Component
public class JpaPermissionRepositoryAdapter implements PermissionRepositoryPort {

    private final PermissionJpaRepository repository;

    public JpaPermissionRepositoryAdapter(PermissionJpaRepository repository) {
        this.repository = Objects.requireNonNull(repository, "PermissionJpaRepository must not be null.");
    }

    @Override
    public Permission save(Permission model) {
        return IdentityPersistenceMapper.toDomain(
                repository.save(IdentityPersistenceMapper.toEntity(model))
        );
    }

    @Override
    public Optional<Permission> findById(String id) {
        return repository.findById(id).map(IdentityPersistenceMapper::toDomain);
    }
}
