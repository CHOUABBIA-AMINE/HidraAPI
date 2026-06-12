/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaExternalGroupMappingRepositoryAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.infrastructure.persistence.adapter
 *
 * @Description : Database-backed repository adapter for ExternalGroupMapping.
 *
 */
package dz.sh.hidra.modules.identity.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.identity.application.port.out.ExternalGroupMappingRepositoryPort;
import dz.sh.hidra.modules.identity.domain.model.ExternalGroupMapping;
import dz.sh.hidra.modules.identity.infrastructure.persistence.mapper.IdentityPersistenceMapper;
import dz.sh.hidra.modules.identity.infrastructure.persistence.repository.ExternalGroupMappingJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

/**
 * Database-backed repository adapter for ExternalGroupMapping.
 */
@Component
public class JpaExternalGroupMappingRepositoryAdapter implements ExternalGroupMappingRepositoryPort {

    private final ExternalGroupMappingJpaRepository repository;

    public JpaExternalGroupMappingRepositoryAdapter(ExternalGroupMappingJpaRepository repository) {
        this.repository = Objects.requireNonNull(repository, "ExternalGroupMappingJpaRepository must not be null.");
    }

    @Override
    public ExternalGroupMapping save(ExternalGroupMapping model) {
        return IdentityPersistenceMapper.toDomain(repository.save(IdentityPersistenceMapper.toEntity(model)));
    }

    @Override
    public Optional<ExternalGroupMapping> findById(String id) {
        return repository.findById(id).map(IdentityPersistenceMapper::toDomain);
    }
}
