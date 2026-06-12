/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaExternalObjectReferenceRepositoryAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : integration
 * @Package     : dz.sh.hidra.modules.integration.infrastructure.persistence.adapter
 *
 * @Description : Database-backed adapter for ExternalObjectReference.
 *
 */
package dz.sh.hidra.modules.integration.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.integration.application.port.out.ExternalObjectReferenceRepositoryPort;
import dz.sh.hidra.modules.integration.domain.model.ExternalObjectReference;
import dz.sh.hidra.modules.integration.infrastructure.persistence.mapper.IntegrationPersistenceMapper;
import dz.sh.hidra.modules.integration.infrastructure.persistence.repository.ExternalObjectReferenceJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

/**
 * Database-backed repository adapter for ExternalObjectReference.
 */
@Component
public class JpaExternalObjectReferenceRepositoryAdapter implements ExternalObjectReferenceRepositoryPort {

    private final ExternalObjectReferenceJpaRepository repository;

    public JpaExternalObjectReferenceRepositoryAdapter(ExternalObjectReferenceJpaRepository repository) {
        this.repository = Objects.requireNonNull(repository, "ExternalObjectReferenceJpaRepository must not be null.");
    }

    @Override
    public ExternalObjectReference save(ExternalObjectReference model) {
        return IntegrationPersistenceMapper.toDomain(repository.save(IntegrationPersistenceMapper.toEntity(model)));
    }

    @Override
    public Optional<ExternalObjectReference> findById(String id) {
        return repository.findById(id).map(IntegrationPersistenceMapper::toDomain);
    }
}
