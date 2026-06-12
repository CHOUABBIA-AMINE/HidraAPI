/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaRiskAggregationSnapshotRepositoryAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : risk
 * @Package     : dz.sh.hidra.modules.risk.infrastructure.persistence.adapter
 *
 * @Description : Database-backed adapter for RiskAggregationSnapshot.
 *
 */
package dz.sh.hidra.modules.risk.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.risk.application.port.out.RiskAggregationSnapshotRepositoryPort;
import dz.sh.hidra.modules.risk.domain.model.RiskAggregationSnapshot;
import dz.sh.hidra.modules.risk.infrastructure.persistence.mapper.RiskPersistenceMapper;
import dz.sh.hidra.modules.risk.infrastructure.persistence.repository.RiskAggregationSnapshotJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

/**
 * Database-backed repository adapter for RiskAggregationSnapshot.
 */
@Component
public class JpaRiskAggregationSnapshotRepositoryAdapter implements RiskAggregationSnapshotRepositoryPort {

    private final RiskAggregationSnapshotJpaRepository repository;

    public JpaRiskAggregationSnapshotRepositoryAdapter(RiskAggregationSnapshotJpaRepository repository) {
        this.repository = Objects.requireNonNull(repository, "RiskAggregationSnapshotJpaRepository must not be null.");
    }

    @Override
    public RiskAggregationSnapshot save(RiskAggregationSnapshot model) {
        return RiskPersistenceMapper.toDomain(repository.save(RiskPersistenceMapper.toEntity(model)));
    }

    @Override
    public Optional<RiskAggregationSnapshot> findById(String id) {
        return repository.findById(id).map(RiskPersistenceMapper::toDomain);
    }
}
