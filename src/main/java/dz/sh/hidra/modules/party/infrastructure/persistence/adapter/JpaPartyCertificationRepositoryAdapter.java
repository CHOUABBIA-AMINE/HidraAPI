/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaPartyCertificationRepositoryAdapter
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Adapter
 * @Layer       : Infrastructure
 * @Module      : party
 * @Package     : dz.sh.hidra.modules.party.infrastructure.persistence.adapter
 *
 * @Description : Database-backed adapter for PartyCertification.
 *
 */
package dz.sh.hidra.modules.party.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.party.application.port.out.PartyCertificationRepositoryPort;
import dz.sh.hidra.modules.party.domain.model.PartyCertification;
import dz.sh.hidra.modules.party.infrastructure.persistence.mapper.PartyPersistenceMapper;
import dz.sh.hidra.modules.party.infrastructure.persistence.repository.PartyCertificationJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

/**
 * Database-backed repository adapter for PartyCertification.
 */
@Component
public class JpaPartyCertificationRepositoryAdapter implements PartyCertificationRepositoryPort {

    private final PartyCertificationJpaRepository repository;

    public JpaPartyCertificationRepositoryAdapter(PartyCertificationJpaRepository repository) {
        this.repository = Objects.requireNonNull(repository, "PartyCertificationJpaRepository must not be null.");
    }

    @Override
    public PartyCertification save(PartyCertification model) {
        return PartyPersistenceMapper.toDomain(
                repository.save(PartyPersistenceMapper.toEntity(model))
        );
    }

    @Override
    public Optional<PartyCertification> findById(String id) {
        return repository.findById(id).map(PartyPersistenceMapper::toDomain);
    }
}
