/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaPartyStatusHistoryRepositoryAdapter
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Adapter
 * @Layer       : Infrastructure
 * @Module      : party
 * @Package     : dz.sh.hidra.modules.party.infrastructure.persistence.adapter
 *
 * @Description : Database-backed adapter for PartyStatusHistory.
 *
 */
package dz.sh.hidra.modules.party.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.party.application.port.out.PartyStatusHistoryRepositoryPort;
import dz.sh.hidra.modules.party.domain.model.PartyStatusHistory;
import dz.sh.hidra.modules.party.infrastructure.persistence.mapper.PartyPersistenceMapper;
import dz.sh.hidra.modules.party.infrastructure.persistence.repository.PartyStatusHistoryJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

/**
 * Database-backed repository adapter for PartyStatusHistory.
 */
@Component
public class JpaPartyStatusHistoryRepositoryAdapter implements PartyStatusHistoryRepositoryPort {

    private final PartyStatusHistoryJpaRepository repository;

    public JpaPartyStatusHistoryRepositoryAdapter(PartyStatusHistoryJpaRepository repository) {
        this.repository = Objects.requireNonNull(repository, "PartyStatusHistoryJpaRepository must not be null.");
    }

    @Override
    public PartyStatusHistory save(PartyStatusHistory model) {
        return PartyPersistenceMapper.toDomain(
                repository.save(PartyPersistenceMapper.toEntity(model))
        );
    }

    @Override
    public Optional<PartyStatusHistory> findById(String id) {
        return repository.findById(id).map(PartyPersistenceMapper::toDomain);
    }
}
