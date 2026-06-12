/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaHseCatalogEntryRepositoryAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : hse
 * @Package     : dz.sh.hidra.modules.hse.infrastructure.persistence.adapter
 *
 * @Description : Database-backed adapter for HseCatalogEntry.
 *
 */
package dz.sh.hidra.modules.hse.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.hse.application.port.out.HseCatalogEntryRepositoryPort;
import dz.sh.hidra.modules.hse.domain.model.HseCatalogEntry;
import dz.sh.hidra.modules.hse.infrastructure.persistence.mapper.HsePersistenceMapper;
import dz.sh.hidra.modules.hse.infrastructure.persistence.repository.HseCatalogEntryJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

/**
 * Database-backed repository adapter for HseCatalogEntry.
 */
@Component
public class JpaHseCatalogEntryRepositoryAdapter implements HseCatalogEntryRepositoryPort {

    private final HseCatalogEntryJpaRepository repository;

    public JpaHseCatalogEntryRepositoryAdapter(HseCatalogEntryJpaRepository repository) {
        this.repository = Objects.requireNonNull(repository, "HseCatalogEntryJpaRepository must not be null.");
    }

    @Override
    public HseCatalogEntry save(HseCatalogEntry model) {
        return HsePersistenceMapper.toDomain(repository.save(HsePersistenceMapper.toEntity(model)));
    }

    @Override
    public Optional<HseCatalogEntry> findById(String id) {
        return repository.findById(id).map(HsePersistenceMapper::toDomain);
    }
}
