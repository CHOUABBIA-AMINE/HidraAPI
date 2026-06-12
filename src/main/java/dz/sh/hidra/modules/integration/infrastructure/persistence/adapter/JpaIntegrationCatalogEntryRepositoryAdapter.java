/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaIntegrationCatalogEntryRepositoryAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : integration
 * @Package     : dz.sh.hidra.modules.integration.infrastructure.persistence.adapter
 *
 * @Description : Database-backed adapter for IntegrationCatalogEntry.
 *
 */
package dz.sh.hidra.modules.integration.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.integration.application.port.out.IntegrationCatalogEntryRepositoryPort;
import dz.sh.hidra.modules.integration.domain.model.IntegrationCatalogEntry;
import dz.sh.hidra.modules.integration.infrastructure.persistence.mapper.IntegrationPersistenceMapper;
import dz.sh.hidra.modules.integration.infrastructure.persistence.repository.IntegrationCatalogEntryJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

/**
 * Database-backed repository adapter for IntegrationCatalogEntry.
 */
@Component
public class JpaIntegrationCatalogEntryRepositoryAdapter implements IntegrationCatalogEntryRepositoryPort {

    private final IntegrationCatalogEntryJpaRepository repository;

    public JpaIntegrationCatalogEntryRepositoryAdapter(IntegrationCatalogEntryJpaRepository repository) {
        this.repository = Objects.requireNonNull(repository, "IntegrationCatalogEntryJpaRepository must not be null.");
    }

    @Override
    public IntegrationCatalogEntry save(IntegrationCatalogEntry model) {
        return IntegrationPersistenceMapper.toDomain(repository.save(IntegrationPersistenceMapper.toEntity(model)));
    }

    @Override
    public Optional<IntegrationCatalogEntry> findById(String id) {
        return repository.findById(id).map(IntegrationPersistenceMapper::toDomain);
    }
}
