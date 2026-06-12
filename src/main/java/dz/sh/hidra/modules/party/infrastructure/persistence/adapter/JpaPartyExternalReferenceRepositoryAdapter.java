/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaPartyExternalReferenceRepositoryAdapter
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Adapter
 * @Layer       : Infrastructure
 * @Module      : party
 * @Package     : dz.sh.hidra.modules.party.infrastructure.persistence.adapter
 *
 * @Description : Database-backed adapter for PartyExternalReference.
 *
 */
package dz.sh.hidra.modules.party.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.party.application.port.out.PartyExternalReferenceRepositoryPort;
import dz.sh.hidra.modules.party.domain.model.PartyExternalReference;
import dz.sh.hidra.modules.party.infrastructure.persistence.mapper.PartyPersistenceMapper;
import dz.sh.hidra.modules.party.infrastructure.persistence.repository.PartyExternalReferenceJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

/**
 * Database-backed repository adapter for PartyExternalReference.
 */
@Component
public class JpaPartyExternalReferenceRepositoryAdapter implements PartyExternalReferenceRepositoryPort {

    private final PartyExternalReferenceJpaRepository repository;

    public JpaPartyExternalReferenceRepositoryAdapter(PartyExternalReferenceJpaRepository repository) {
        this.repository = Objects.requireNonNull(repository, "PartyExternalReferenceJpaRepository must not be null.");
    }

    @Override
    public PartyExternalReference save(PartyExternalReference model) {
        return PartyPersistenceMapper.toDomain(
                repository.save(PartyPersistenceMapper.toEntity(model))
        );
    }

    @Override
    public Optional<PartyExternalReference> findById(String id) {
        return repository.findById(id).map(PartyPersistenceMapper::toDomain);
    }
}
