/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaPartyOwnershipLinkRepositoryAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : party
 * @Package     : dz.sh.hidra.modules.party.infrastructure.persistence.adapter
 *
 * @Description : Database-backed adapter for PartyOwnershipLink.
 *
 */
package dz.sh.hidra.modules.party.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.party.application.port.out.PartyOwnershipLinkRepositoryPort;
import dz.sh.hidra.modules.party.domain.model.PartyOwnershipLink;
import dz.sh.hidra.modules.party.infrastructure.persistence.mapper.PartyPersistenceMapper;
import dz.sh.hidra.modules.party.infrastructure.persistence.repository.PartyOwnershipLinkJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

/**
 * Database-backed repository adapter for PartyOwnershipLink.
 */
@Component
public class JpaPartyOwnershipLinkRepositoryAdapter implements PartyOwnershipLinkRepositoryPort {

    private final PartyOwnershipLinkJpaRepository repository;

    public JpaPartyOwnershipLinkRepositoryAdapter(PartyOwnershipLinkJpaRepository repository) {
        this.repository = Objects.requireNonNull(repository, "PartyOwnershipLinkJpaRepository must not be null.");
    }

    @Override
    public PartyOwnershipLink save(PartyOwnershipLink model) {
        return PartyPersistenceMapper.toDomain(
                repository.save(PartyPersistenceMapper.toEntity(model))
        );
    }

    @Override
    public Optional<PartyOwnershipLink> findById(String id) {
        return repository.findById(id).map(PartyPersistenceMapper::toDomain);
    }
}
