/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaPartyLegalProfileRepositoryAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : party
 * @Package     : dz.sh.hidra.modules.party.infrastructure.persistence.adapter
 *
 * @Description : Database-backed adapter for PartyLegalProfile.
 *
 */
package dz.sh.hidra.modules.party.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.party.application.port.out.PartyLegalProfileRepositoryPort;
import dz.sh.hidra.modules.party.domain.model.PartyLegalProfile;
import dz.sh.hidra.modules.party.infrastructure.persistence.mapper.PartyPersistenceMapper;
import dz.sh.hidra.modules.party.infrastructure.persistence.repository.PartyLegalProfileJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

/**
 * Database-backed repository adapter for PartyLegalProfile.
 */
@Component
public class JpaPartyLegalProfileRepositoryAdapter implements PartyLegalProfileRepositoryPort {

    private final PartyLegalProfileJpaRepository repository;

    public JpaPartyLegalProfileRepositoryAdapter(PartyLegalProfileJpaRepository repository) {
        this.repository = Objects.requireNonNull(repository, "PartyLegalProfileJpaRepository must not be null.");
    }

    @Override
    public PartyLegalProfile save(PartyLegalProfile model) {
        return PartyPersistenceMapper.toDomain(
                repository.save(PartyPersistenceMapper.toEntity(model))
        );
    }

    @Override
    public Optional<PartyLegalProfile> findById(String id) {
        return repository.findById(id).map(PartyPersistenceMapper::toDomain);
    }
}
