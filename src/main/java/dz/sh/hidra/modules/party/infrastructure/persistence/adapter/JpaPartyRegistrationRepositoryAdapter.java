/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaPartyRegistrationRepositoryAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : party
 * @Package     : dz.sh.hidra.modules.party.infrastructure.persistence.adapter
 *
 * @Description : Database-backed adapter for PartyRegistration.
 *
 */
package dz.sh.hidra.modules.party.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.party.application.port.out.PartyRegistrationRepositoryPort;
import dz.sh.hidra.modules.party.domain.model.PartyRegistration;
import dz.sh.hidra.modules.party.infrastructure.persistence.mapper.PartyPersistenceMapper;
import dz.sh.hidra.modules.party.infrastructure.persistence.repository.PartyRegistrationJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

/**
 * Database-backed repository adapter for PartyRegistration.
 */
@Component
public class JpaPartyRegistrationRepositoryAdapter implements PartyRegistrationRepositoryPort {

    private final PartyRegistrationJpaRepository repository;

    public JpaPartyRegistrationRepositoryAdapter(PartyRegistrationJpaRepository repository) {
        this.repository = Objects.requireNonNull(repository, "PartyRegistrationJpaRepository must not be null.");
    }

    @Override
    public PartyRegistration save(PartyRegistration model) {
        return PartyPersistenceMapper.toDomain(
                repository.save(PartyPersistenceMapper.toEntity(model))
        );
    }

    @Override
    public Optional<PartyRegistration> findById(String id) {
        return repository.findById(id).map(PartyPersistenceMapper::toDomain);
    }
}
