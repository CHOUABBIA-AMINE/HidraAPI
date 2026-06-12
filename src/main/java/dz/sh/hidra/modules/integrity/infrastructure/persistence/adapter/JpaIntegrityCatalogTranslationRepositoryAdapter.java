/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaIntegrityCatalogTranslationRepositoryAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : integrity
 * @Package     : dz.sh.hidra.modules.integrity.infrastructure.persistence.adapter
 *
 * @Description : Database-backed adapter for IntegrityCatalogTranslation.
 *
 */
package dz.sh.hidra.modules.integrity.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.integrity.application.port.out.IntegrityCatalogTranslationRepositoryPort;
import dz.sh.hidra.modules.integrity.domain.model.IntegrityCatalogTranslation;
import dz.sh.hidra.modules.integrity.infrastructure.persistence.mapper.IntegrityPersistenceMapper;
import dz.sh.hidra.modules.integrity.infrastructure.persistence.repository.IntegrityCatalogTranslationJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

/**
 * Database-backed repository adapter for IntegrityCatalogTranslation.
 */
@Component
public class JpaIntegrityCatalogTranslationRepositoryAdapter implements IntegrityCatalogTranslationRepositoryPort {

    private final IntegrityCatalogTranslationJpaRepository repository;

    public JpaIntegrityCatalogTranslationRepositoryAdapter(IntegrityCatalogTranslationJpaRepository repository) {
        this.repository = Objects.requireNonNull(repository, "IntegrityCatalogTranslationJpaRepository must not be null.");
    }

    @Override
    public IntegrityCatalogTranslation save(IntegrityCatalogTranslation model) {
        return IntegrityPersistenceMapper.toDomain(repository.save(IntegrityPersistenceMapper.toEntity(model)));
    }

    @Override
    public Optional<IntegrityCatalogTranslation> findById(String id) {
        return repository.findById(id).map(IntegrityPersistenceMapper::toDomain);
    }
}
