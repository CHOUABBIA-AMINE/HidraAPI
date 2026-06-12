/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaPartyDocumentReferenceRepositoryAdapter
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Adapter
 * @Layer       : Infrastructure
 * @Module      : party
 * @Package     : dz.sh.hidra.modules.party.infrastructure.persistence.adapter
 *
 * @Description : Database-backed adapter for PartyDocumentReference.
 *
 */
package dz.sh.hidra.modules.party.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.party.application.port.out.PartyDocumentReferenceRepositoryPort;
import dz.sh.hidra.modules.party.domain.model.PartyDocumentReference;
import dz.sh.hidra.modules.party.infrastructure.persistence.mapper.PartyPersistenceMapper;
import dz.sh.hidra.modules.party.infrastructure.persistence.repository.PartyDocumentReferenceJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

/**
 * Database-backed repository adapter for PartyDocumentReference.
 */
@Component
public class JpaPartyDocumentReferenceRepositoryAdapter implements PartyDocumentReferenceRepositoryPort {

    private final PartyDocumentReferenceJpaRepository repository;

    public JpaPartyDocumentReferenceRepositoryAdapter(PartyDocumentReferenceJpaRepository repository) {
        this.repository = Objects.requireNonNull(repository, "PartyDocumentReferenceJpaRepository must not be null.");
    }

    @Override
    public PartyDocumentReference save(PartyDocumentReference model) {
        return PartyPersistenceMapper.toDomain(
                repository.save(PartyPersistenceMapper.toEntity(model))
        );
    }

    @Override
    public Optional<PartyDocumentReference> findById(String id) {
        return repository.findById(id).map(PartyPersistenceMapper::toDomain);
    }
}
