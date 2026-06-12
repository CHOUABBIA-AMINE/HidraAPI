/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaPartyQualificationRepositoryAdapter
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Adapter
 * @Layer       : Infrastructure
 * @Module      : party
 * @Package     : dz.sh.hidra.modules.party.infrastructure.persistence.adapter
 *
 * @Description : Database-backed adapter for PartyQualification.
 *
 */
package dz.sh.hidra.modules.party.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.party.application.port.out.PartyQualificationRepositoryPort;
import dz.sh.hidra.modules.party.domain.model.PartyQualification;
import dz.sh.hidra.modules.party.infrastructure.persistence.mapper.PartyPersistenceMapper;
import dz.sh.hidra.modules.party.infrastructure.persistence.repository.PartyQualificationJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

/**
 * Database-backed repository adapter for PartyQualification.
 */
@Component
public class JpaPartyQualificationRepositoryAdapter implements PartyQualificationRepositoryPort {

    private final PartyQualificationJpaRepository repository;

    public JpaPartyQualificationRepositoryAdapter(PartyQualificationJpaRepository repository) {
        this.repository = Objects.requireNonNull(repository, "PartyQualificationJpaRepository must not be null.");
    }

    @Override
    public PartyQualification save(PartyQualification model) {
        return PartyPersistenceMapper.toDomain(
                repository.save(PartyPersistenceMapper.toEntity(model))
        );
    }

    @Override
    public Optional<PartyQualification> findById(String id) {
        return repository.findById(id).map(PartyPersistenceMapper::toDomain);
    }
}
