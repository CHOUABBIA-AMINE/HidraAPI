/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaExternalRoleMappingRepositoryAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.infrastructure.persistence.adapter
 *
 * @Description : Database-backed repository adapter for ExternalRoleMapping.
 *
 */
package dz.sh.hidra.modules.identity.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.identity.application.port.out.ExternalRoleMappingRepositoryPort;
import dz.sh.hidra.modules.identity.domain.model.ExternalRoleMapping;
import dz.sh.hidra.modules.identity.infrastructure.persistence.mapper.IdentityPersistenceMapper;
import dz.sh.hidra.modules.identity.infrastructure.persistence.repository.ExternalRoleMappingJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

/**
 * Database-backed repository adapter for ExternalRoleMapping.
 */
@Component
public class JpaExternalRoleMappingRepositoryAdapter implements ExternalRoleMappingRepositoryPort {

    private final ExternalRoleMappingJpaRepository repository;

    public JpaExternalRoleMappingRepositoryAdapter(ExternalRoleMappingJpaRepository repository) {
        this.repository = Objects.requireNonNull(repository, "ExternalRoleMappingJpaRepository must not be null.");
    }

    @Override
    public ExternalRoleMapping save(ExternalRoleMapping model) {
        return IdentityPersistenceMapper.toDomain(repository.save(IdentityPersistenceMapper.toEntity(model)));
    }

    @Override
    public Optional<ExternalRoleMapping> findById(String id) {
        return repository.findById(id).map(IdentityPersistenceMapper::toDomain);
    }
}
