/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaPartyRoleTranslationRepositoryAdapter
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Adapter
 * @Layer       : Infrastructure
 * @Module      : party
 * @Package     : dz.sh.hidra.modules.party.infrastructure.persistence.adapter
 *
 * @Description : Database-backed adapter for PartyRoleTranslation.
 *
 */
package dz.sh.hidra.modules.party.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.party.application.port.out.PartyRoleTranslationRepositoryPort;
import dz.sh.hidra.modules.party.domain.model.PartyRoleTranslation;
import dz.sh.hidra.modules.party.infrastructure.persistence.mapper.PartyPersistenceMapper;
import dz.sh.hidra.modules.party.infrastructure.persistence.repository.PartyRoleTranslationJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

/**
 * Database-backed repository adapter for PartyRoleTranslation.
 */
@Component
public class JpaPartyRoleTranslationRepositoryAdapter implements PartyRoleTranslationRepositoryPort {

    private final PartyRoleTranslationJpaRepository repository;

    public JpaPartyRoleTranslationRepositoryAdapter(PartyRoleTranslationJpaRepository repository) {
        this.repository = Objects.requireNonNull(repository, "PartyRoleTranslationJpaRepository must not be null.");
    }

    @Override
    public PartyRoleTranslation save(PartyRoleTranslation model) {
        return PartyPersistenceMapper.toDomain(
                repository.save(PartyPersistenceMapper.toEntity(model))
        );
    }

    @Override
    public Optional<PartyRoleTranslation> findById(String id) {
        return repository.findById(id).map(PartyPersistenceMapper::toDomain);
    }
}
