/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaCustodyCatalogEntryRepositoryAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : custody
 * @Package     : dz.sh.hidra.modules.custody.infrastructure.persistence.adapter
 *
 * @Description : Database-backed adapter for CustodyCatalogEntry.
 *
 */
package dz.sh.hidra.modules.custody.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.custody.application.port.out.CustodyCatalogEntryRepositoryPort;
import dz.sh.hidra.modules.custody.domain.model.CustodyCatalogEntry;
import dz.sh.hidra.modules.custody.infrastructure.persistence.mapper.CustodyPersistenceMapper;
import dz.sh.hidra.modules.custody.infrastructure.persistence.repository.CustodyCatalogEntryJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

/**
 * Database-backed repository adapter for CustodyCatalogEntry.
 */
@Component
public class JpaCustodyCatalogEntryRepositoryAdapter implements CustodyCatalogEntryRepositoryPort {

    private final CustodyCatalogEntryJpaRepository repository;

    public JpaCustodyCatalogEntryRepositoryAdapter(CustodyCatalogEntryJpaRepository repository) {
        this.repository = Objects.requireNonNull(repository, "CustodyCatalogEntryJpaRepository must not be null.");
    }

    @Override
    public CustodyCatalogEntry save(CustodyCatalogEntry model) {
        return CustodyPersistenceMapper.toDomain(repository.save(CustodyPersistenceMapper.toEntity(model)));
    }

    @Override
    public Optional<CustodyCatalogEntry> findById(String id) {
        return repository.findById(id).map(CustodyPersistenceMapper::toDomain);
    }
}
