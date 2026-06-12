/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaPartyTypeRepositoryAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : party
 * @Package     : dz.sh.hidra.modules.party.infrastructure.persistence.adapter
 *
 * @Description : Database-backed adapter for PartyType.
 *
 */
package dz.sh.hidra.modules.party.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.party.application.port.out.PartyTypeRepositoryPort;
import dz.sh.hidra.modules.party.domain.model.PartyType;
import dz.sh.hidra.modules.party.infrastructure.persistence.mapper.PartyPersistenceMapper;
import dz.sh.hidra.modules.party.infrastructure.persistence.repository.PartyTypeJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

/**
 * Database-backed repository adapter for PartyType.
 */
@Component
public class JpaPartyTypeRepositoryAdapter implements PartyTypeRepositoryPort {

    private final PartyTypeJpaRepository repository;

    public JpaPartyTypeRepositoryAdapter(PartyTypeJpaRepository repository) {
        this.repository = Objects.requireNonNull(repository, "PartyTypeJpaRepository must not be null.");
    }

    @Override
    public PartyType save(PartyType model) {
        return PartyPersistenceMapper.toDomain(
                repository.save(PartyPersistenceMapper.toEntity(model))
        );
    }

    @Override
    public Optional<PartyType> findById(String id) {
        return repository.findById(id).map(PartyPersistenceMapper::toDomain);
    }
}
