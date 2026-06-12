/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaPartyRiskSnapshotRepositoryAdapter
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Adapter
 * @Layer       : Infrastructure
 * @Module      : party
 * @Package     : dz.sh.hidra.modules.party.infrastructure.persistence.adapter
 *
 * @Description : Database-backed adapter for PartyRiskSnapshot.
 *
 */
package dz.sh.hidra.modules.party.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.party.application.port.out.PartyRiskSnapshotRepositoryPort;
import dz.sh.hidra.modules.party.domain.model.PartyRiskSnapshot;
import dz.sh.hidra.modules.party.infrastructure.persistence.mapper.PartyPersistenceMapper;
import dz.sh.hidra.modules.party.infrastructure.persistence.repository.PartyRiskSnapshotJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

/**
 * Database-backed repository adapter for PartyRiskSnapshot.
 */
@Component
public class JpaPartyRiskSnapshotRepositoryAdapter implements PartyRiskSnapshotRepositoryPort {

    private final PartyRiskSnapshotJpaRepository repository;

    public JpaPartyRiskSnapshotRepositoryAdapter(PartyRiskSnapshotJpaRepository repository) {
        this.repository = Objects.requireNonNull(repository, "PartyRiskSnapshotJpaRepository must not be null.");
    }

    @Override
    public PartyRiskSnapshot save(PartyRiskSnapshot model) {
        return PartyPersistenceMapper.toDomain(
                repository.save(PartyPersistenceMapper.toEntity(model))
        );
    }

    @Override
    public Optional<PartyRiskSnapshot> findById(String id) {
        return repository.findById(id).map(PartyPersistenceMapper::toDomain);
    }
}
