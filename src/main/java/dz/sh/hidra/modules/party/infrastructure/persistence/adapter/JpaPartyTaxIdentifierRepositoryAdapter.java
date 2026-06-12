/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaPartyTaxIdentifierRepositoryAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : party
 * @Package     : dz.sh.hidra.modules.party.infrastructure.persistence.adapter
 *
 * @Description : Database-backed adapter for PartyTaxIdentifier.
 *
 */
package dz.sh.hidra.modules.party.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.party.application.port.out.PartyTaxIdentifierRepositoryPort;
import dz.sh.hidra.modules.party.domain.model.PartyTaxIdentifier;
import dz.sh.hidra.modules.party.infrastructure.persistence.mapper.PartyPersistenceMapper;
import dz.sh.hidra.modules.party.infrastructure.persistence.repository.PartyTaxIdentifierJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

/**
 * Database-backed repository adapter for PartyTaxIdentifier.
 */
@Component
public class JpaPartyTaxIdentifierRepositoryAdapter implements PartyTaxIdentifierRepositoryPort {

    private final PartyTaxIdentifierJpaRepository repository;

    public JpaPartyTaxIdentifierRepositoryAdapter(PartyTaxIdentifierJpaRepository repository) {
        this.repository = Objects.requireNonNull(repository, "PartyTaxIdentifierJpaRepository must not be null.");
    }

    @Override
    public PartyTaxIdentifier save(PartyTaxIdentifier model) {
        return PartyPersistenceMapper.toDomain(
                repository.save(PartyPersistenceMapper.toEntity(model))
        );
    }

    @Override
    public Optional<PartyTaxIdentifier> findById(String id) {
        return repository.findById(id).map(PartyPersistenceMapper::toDomain);
    }
}
