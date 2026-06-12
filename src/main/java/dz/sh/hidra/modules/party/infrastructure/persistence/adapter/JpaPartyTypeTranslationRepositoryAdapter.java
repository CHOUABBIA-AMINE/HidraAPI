/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaPartyTypeTranslationRepositoryAdapter
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Adapter
 * @Layer       : Infrastructure
 * @Module      : party
 * @Package     : dz.sh.hidra.modules.party.infrastructure.persistence.adapter
 *
 * @Description : Database-backed adapter for PartyTypeTranslation.
 *
 */
package dz.sh.hidra.modules.party.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.party.application.port.out.PartyTypeTranslationRepositoryPort;
import dz.sh.hidra.modules.party.domain.model.PartyTypeTranslation;
import dz.sh.hidra.modules.party.infrastructure.persistence.mapper.PartyPersistenceMapper;
import dz.sh.hidra.modules.party.infrastructure.persistence.repository.PartyTypeTranslationJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

/**
 * Database-backed repository adapter for PartyTypeTranslation.
 */
@Component
public class JpaPartyTypeTranslationRepositoryAdapter implements PartyTypeTranslationRepositoryPort {

    private final PartyTypeTranslationJpaRepository repository;

    public JpaPartyTypeTranslationRepositoryAdapter(PartyTypeTranslationJpaRepository repository) {
        this.repository = Objects.requireNonNull(repository, "PartyTypeTranslationJpaRepository must not be null.");
    }

    @Override
    public PartyTypeTranslation save(PartyTypeTranslation model) {
        return PartyPersistenceMapper.toDomain(
                repository.save(PartyPersistenceMapper.toEntity(model))
        );
    }

    @Override
    public Optional<PartyTypeTranslation> findById(String id) {
        return repository.findById(id).map(PartyPersistenceMapper::toDomain);
    }
}
