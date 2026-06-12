/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaPartyContactPersonRepositoryAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : party
 * @Package     : dz.sh.hidra.modules.party.infrastructure.persistence.adapter
 *
 * @Description : Database-backed adapter for PartyContactPerson.
 *
 */
package dz.sh.hidra.modules.party.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.party.application.port.out.PartyContactPersonRepositoryPort;
import dz.sh.hidra.modules.party.domain.model.PartyContactPerson;
import dz.sh.hidra.modules.party.infrastructure.persistence.mapper.PartyPersistenceMapper;
import dz.sh.hidra.modules.party.infrastructure.persistence.repository.PartyContactPersonJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

/**
 * Database-backed repository adapter for PartyContactPerson.
 */
@Component
public class JpaPartyContactPersonRepositoryAdapter implements PartyContactPersonRepositoryPort {

    private final PartyContactPersonJpaRepository repository;

    public JpaPartyContactPersonRepositoryAdapter(PartyContactPersonJpaRepository repository) {
        this.repository = Objects.requireNonNull(repository, "PartyContactPersonJpaRepository must not be null.");
    }

    @Override
    public PartyContactPerson save(PartyContactPerson model) {
        return PartyPersistenceMapper.toDomain(
                repository.save(PartyPersistenceMapper.toEntity(model))
        );
    }

    @Override
    public Optional<PartyContactPerson> findById(String id) {
        return repository.findById(id).map(PartyPersistenceMapper::toDomain);
    }
}
