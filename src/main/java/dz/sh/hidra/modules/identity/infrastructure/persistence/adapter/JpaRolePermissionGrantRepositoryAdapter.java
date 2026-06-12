/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaRolePermissionGrantRepositoryAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
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
import dz.sh.hidra.modules.identity.domain.model.RolePermissionGrant;
import dz.sh.hidra.modules.identity.infrastructure.persistence.mapper.IdentityPersistenceMapper;
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

    public JpaRolePermissionGrantRepositoryAdapter(RolePermissionGrantJpaRepository repository) {
        this.repository = Objects.requireNonNull(repository, "RolePermissionGrantJpaRepository must not be null.");
    }

    @Override
    public RolePermissionGrant save(RolePermissionGrant model) {
        return IdentityPersistenceMapper.toDomain(repository.save(IdentityPersistenceMapper.toEntity(model)));
    }

    @Override
    public Optional<RolePermissionGrant> findById(String id) {
        return repository.findById(id).map(IdentityPersistenceMapper::toDomain);
    }
}
