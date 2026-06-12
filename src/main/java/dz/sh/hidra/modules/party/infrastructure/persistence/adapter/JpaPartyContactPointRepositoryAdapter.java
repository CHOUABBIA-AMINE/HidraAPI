/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaPartyContactPointRepositoryAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : party
 * @Package     : dz.sh.hidra.modules.party.infrastructure.persistence.adapter
 *
 * @Description : Database-backed adapter for PartyContactPoint.
 *
 */
package dz.sh.hidra.modules.party.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.party.application.port.out.PartyContactPointRepositoryPort;
import dz.sh.hidra.modules.party.domain.model.PartyContactPoint;
import dz.sh.hidra.modules.party.infrastructure.persistence.mapper.PartyPersistenceMapper;
import dz.sh.hidra.modules.party.infrastructure.persistence.repository.PartyContactPointJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

/**
 * Database-backed repository adapter for PartyContactPoint.
 */
@Component
public class JpaPartyContactPointRepositoryAdapter implements PartyContactPointRepositoryPort {

    private final PartyContactPointJpaRepository repository;

    public JpaPartyContactPointRepositoryAdapter(PartyContactPointJpaRepository repository) {
        this.repository = Objects.requireNonNull(repository, "PartyContactPointJpaRepository must not be null.");
    }

    @Override
    public PartyContactPoint save(PartyContactPoint model) {
        return PartyPersistenceMapper.toDomain(
                repository.save(PartyPersistenceMapper.toEntity(model))
        );
    }

    @Override
    public Optional<PartyContactPoint> findById(String id) {
        return repository.findById(id).map(PartyPersistenceMapper::toDomain);
    }
}
