/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaPartyRepositoryAdapter
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Adapter
 * @Layer       : Infrastructure
 * @Module      : party
 * @Package     : dz.sh.hidra.modules.party.infrastructure.persistence.adapter
 *
 * @Description : Database-backed adapter for Party.
 *
 */
package dz.sh.hidra.modules.party.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.party.application.port.out.PartyRepositoryPort;
import dz.sh.hidra.modules.party.domain.model.Party;
import dz.sh.hidra.modules.party.infrastructure.persistence.mapper.PartyPersistenceMapper;
import dz.sh.hidra.modules.party.infrastructure.persistence.repository.PartyJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

/**
 * Database-backed repository adapter for Party.
 */
@Component
public class JpaPartyRepositoryAdapter implements PartyRepositoryPort {

    private final PartyJpaRepository repository;

    public JpaPartyRepositoryAdapter(PartyJpaRepository repository) {
        this.repository = Objects.requireNonNull(repository, "PartyJpaRepository must not be null.");
    }

    @Override
    public Party save(Party model) {
        return PartyPersistenceMapper.toDomain(
                repository.save(PartyPersistenceMapper.toEntity(model))
        );
    }

    @Override
    public Optional<Party> findById(String id) {
        return repository.findById(id).map(PartyPersistenceMapper::toDomain);
    }
}
