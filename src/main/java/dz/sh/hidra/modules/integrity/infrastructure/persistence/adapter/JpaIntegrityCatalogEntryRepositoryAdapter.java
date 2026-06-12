/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaIntegrityCatalogEntryRepositoryAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : integrity
 * @Package     : dz.sh.hidra.modules.integrity.infrastructure.persistence.adapter
 *
 * @Description : Database-backed adapter for IntegrityCatalogEntry.
 *
 */
package dz.sh.hidra.modules.integrity.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.integrity.application.port.out.IntegrityCatalogEntryRepositoryPort;
import dz.sh.hidra.modules.integrity.domain.model.IntegrityCatalogEntry;
import dz.sh.hidra.modules.integrity.infrastructure.persistence.mapper.IntegrityPersistenceMapper;
import dz.sh.hidra.modules.integrity.infrastructure.persistence.repository.IntegrityCatalogEntryJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

/**
 * Database-backed repository adapter for IntegrityCatalogEntry.
 */
@Component
public class JpaIntegrityCatalogEntryRepositoryAdapter implements IntegrityCatalogEntryRepositoryPort {

    private final IntegrityCatalogEntryJpaRepository repository;

    public JpaIntegrityCatalogEntryRepositoryAdapter(IntegrityCatalogEntryJpaRepository repository) {
        this.repository = Objects.requireNonNull(repository, "IntegrityCatalogEntryJpaRepository must not be null.");
    }

    @Override
    public IntegrityCatalogEntry save(IntegrityCatalogEntry model) {
        return IntegrityPersistenceMapper.toDomain(repository.save(IntegrityPersistenceMapper.toEntity(model)));
    }

    @Override
    public Optional<IntegrityCatalogEntry> findById(String id) {
        return repository.findById(id).map(IntegrityPersistenceMapper::toDomain);
    }
}
