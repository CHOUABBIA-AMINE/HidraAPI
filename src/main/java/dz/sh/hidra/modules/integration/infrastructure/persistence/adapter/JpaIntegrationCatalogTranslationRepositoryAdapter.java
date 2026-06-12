/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaIntegrationCatalogTranslationRepositoryAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : integration
 * @Package     : dz.sh.hidra.modules.integration.infrastructure.persistence.adapter
 *
 * @Description : Database-backed adapter for IntegrationCatalogTranslation.
 *
 */
package dz.sh.hidra.modules.integration.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.integration.application.port.out.IntegrationCatalogTranslationRepositoryPort;
import dz.sh.hidra.modules.integration.domain.model.IntegrationCatalogTranslation;
import dz.sh.hidra.modules.integration.infrastructure.persistence.mapper.IntegrationPersistenceMapper;
import dz.sh.hidra.modules.integration.infrastructure.persistence.repository.IntegrationCatalogTranslationJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

/**
 * Database-backed repository adapter for IntegrationCatalogTranslation.
 */
@Component
public class JpaIntegrationCatalogTranslationRepositoryAdapter implements IntegrationCatalogTranslationRepositoryPort {

    private final IntegrationCatalogTranslationJpaRepository repository;

    public JpaIntegrationCatalogTranslationRepositoryAdapter(IntegrationCatalogTranslationJpaRepository repository) {
        this.repository = Objects.requireNonNull(repository, "IntegrationCatalogTranslationJpaRepository must not be null.");
    }

    @Override
    public IntegrationCatalogTranslation save(IntegrationCatalogTranslation model) {
        return IntegrationPersistenceMapper.toDomain(repository.save(IntegrationPersistenceMapper.toEntity(model)));
    }

    @Override
    public Optional<IntegrationCatalogTranslation> findById(String id) {
        return repository.findById(id).map(IntegrationPersistenceMapper::toDomain);
    }
}
