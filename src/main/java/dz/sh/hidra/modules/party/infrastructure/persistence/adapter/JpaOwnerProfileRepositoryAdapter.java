/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaOwnerProfileRepositoryAdapter
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Adapter
 * @Layer       : Infrastructure
 * @Module      : party
 * @Package     : dz.sh.hidra.modules.party.infrastructure.persistence.adapter
 *
 * @Description : Database-backed adapter for OwnerProfile.
 *
 */
package dz.sh.hidra.modules.party.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.party.application.port.out.OwnerProfileRepositoryPort;
import dz.sh.hidra.modules.party.domain.model.OwnerProfile;
import dz.sh.hidra.modules.party.infrastructure.persistence.mapper.PartyPersistenceMapper;
import dz.sh.hidra.modules.party.infrastructure.persistence.repository.OwnerProfileJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

/**
 * Database-backed repository adapter for OwnerProfile.
 */
@Component
public class JpaOwnerProfileRepositoryAdapter implements OwnerProfileRepositoryPort {

    private final OwnerProfileJpaRepository repository;

    public JpaOwnerProfileRepositoryAdapter(OwnerProfileJpaRepository repository) {
        this.repository = Objects.requireNonNull(repository, "OwnerProfileJpaRepository must not be null.");
    }

    @Override
    public OwnerProfile save(OwnerProfile model) {
        return PartyPersistenceMapper.toDomain(
                repository.save(PartyPersistenceMapper.toEntity(model))
        );
    }

    @Override
    public Optional<OwnerProfile> findById(String id) {
        return repository.findById(id).map(PartyPersistenceMapper::toDomain);
    }
}
