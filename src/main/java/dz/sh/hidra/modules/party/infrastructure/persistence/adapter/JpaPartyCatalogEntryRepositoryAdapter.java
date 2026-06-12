/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaPartyCatalogEntryRepositoryAdapter
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Adapter
 * @Layer       : Infrastructure
 * @Module      : party
 * @Package     : dz.sh.hidra.modules.party.infrastructure.persistence.adapter
 *
 * @Description : Database-backed adapter for PartyCatalogEntry.
 *
 */
package dz.sh.hidra.modules.party.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.party.application.port.out.PartyCatalogEntryRepositoryPort;
import dz.sh.hidra.modules.party.domain.model.PartyCatalogEntry;
import dz.sh.hidra.modules.party.infrastructure.persistence.mapper.PartyPersistenceMapper;
import dz.sh.hidra.modules.party.infrastructure.persistence.repository.PartyCatalogEntryJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

/**
 * Database-backed repository adapter for PartyCatalogEntry.
 */
@Component
public class JpaPartyCatalogEntryRepositoryAdapter implements PartyCatalogEntryRepositoryPort {

    private final PartyCatalogEntryJpaRepository repository;

    public JpaPartyCatalogEntryRepositoryAdapter(PartyCatalogEntryJpaRepository repository) {
        this.repository = Objects.requireNonNull(repository, "PartyCatalogEntryJpaRepository must not be null.");
    }

    @Override
    public PartyCatalogEntry save(PartyCatalogEntry model) {
        return PartyPersistenceMapper.toDomain(
                repository.save(PartyPersistenceMapper.toEntity(model))
        );
    }

    @Override
    public Optional<PartyCatalogEntry> findById(String id) {
        return repository.findById(id).map(PartyPersistenceMapper::toDomain);
    }
}
