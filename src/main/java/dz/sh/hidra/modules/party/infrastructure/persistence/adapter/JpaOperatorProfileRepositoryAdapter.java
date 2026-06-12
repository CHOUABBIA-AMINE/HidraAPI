/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaOperatorProfileRepositoryAdapter
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Adapter
 * @Layer       : Infrastructure
 * @Module      : party
 * @Package     : dz.sh.hidra.modules.party.infrastructure.persistence.adapter
 *
 * @Description : Database-backed adapter for OperatorProfile.
 *
 */
package dz.sh.hidra.modules.party.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.party.application.port.out.OperatorProfileRepositoryPort;
import dz.sh.hidra.modules.party.domain.model.OperatorProfile;
import dz.sh.hidra.modules.party.infrastructure.persistence.mapper.PartyPersistenceMapper;
import dz.sh.hidra.modules.party.infrastructure.persistence.repository.OperatorProfileJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

/**
 * Database-backed repository adapter for OperatorProfile.
 */
@Component
public class JpaOperatorProfileRepositoryAdapter implements OperatorProfileRepositoryPort {

    private final OperatorProfileJpaRepository repository;

    public JpaOperatorProfileRepositoryAdapter(OperatorProfileJpaRepository repository) {
        this.repository = Objects.requireNonNull(repository, "OperatorProfileJpaRepository must not be null.");
    }

    @Override
    public OperatorProfile save(OperatorProfile model) {
        return PartyPersistenceMapper.toDomain(
                repository.save(PartyPersistenceMapper.toEntity(model))
        );
    }

    @Override
    public Optional<OperatorProfile> findById(String id) {
        return repository.findById(id).map(PartyPersistenceMapper::toDomain);
    }
}
