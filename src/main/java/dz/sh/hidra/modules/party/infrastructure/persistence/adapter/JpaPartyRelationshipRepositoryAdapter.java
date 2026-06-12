/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaPartyRelationshipRepositoryAdapter
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Adapter
 * @Layer       : Infrastructure
 * @Module      : party
 * @Package     : dz.sh.hidra.modules.party.infrastructure.persistence.adapter
 *
 * @Description : Database-backed adapter for PartyRelationship.
 *
 */
package dz.sh.hidra.modules.party.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.party.application.port.out.PartyRelationshipRepositoryPort;
import dz.sh.hidra.modules.party.domain.model.PartyRelationship;
import dz.sh.hidra.modules.party.infrastructure.persistence.mapper.PartyPersistenceMapper;
import dz.sh.hidra.modules.party.infrastructure.persistence.repository.PartyRelationshipJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

/**
 * Database-backed repository adapter for PartyRelationship.
 */
@Component
public class JpaPartyRelationshipRepositoryAdapter implements PartyRelationshipRepositoryPort {

    private final PartyRelationshipJpaRepository repository;

    public JpaPartyRelationshipRepositoryAdapter(PartyRelationshipJpaRepository repository) {
        this.repository = Objects.requireNonNull(repository, "PartyRelationshipJpaRepository must not be null.");
    }

    @Override
    public PartyRelationship save(PartyRelationship model) {
        return PartyPersistenceMapper.toDomain(
                repository.save(PartyPersistenceMapper.toEntity(model))
        );
    }

    @Override
    public Optional<PartyRelationship> findById(String id) {
        return repository.findById(id).map(PartyPersistenceMapper::toDomain);
    }
}
