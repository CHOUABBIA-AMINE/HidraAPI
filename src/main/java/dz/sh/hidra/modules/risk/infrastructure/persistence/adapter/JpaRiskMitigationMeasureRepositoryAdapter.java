/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaRiskMitigationMeasureRepositoryAdapter
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Adapter
 * @Layer       : Infrastructure
 * @Module      : risk
 * @Package     : dz.sh.hidra.modules.risk.infrastructure.persistence.adapter
 *
 * @Description : Database-backed adapter for RiskMitigationMeasure.
 *
 */
package dz.sh.hidra.modules.risk.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.risk.application.port.out.RiskMitigationMeasureRepositoryPort;
import dz.sh.hidra.modules.risk.domain.model.RiskMitigationMeasure;
import dz.sh.hidra.modules.risk.infrastructure.persistence.mapper.RiskPersistenceMapper;
import dz.sh.hidra.modules.risk.infrastructure.persistence.repository.RiskMitigationMeasureJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

/**
 * Database-backed repository adapter for RiskMitigationMeasure.
 */
@Component
public class JpaRiskMitigationMeasureRepositoryAdapter implements RiskMitigationMeasureRepositoryPort {

    private final RiskMitigationMeasureJpaRepository repository;

    public JpaRiskMitigationMeasureRepositoryAdapter(RiskMitigationMeasureJpaRepository repository) {
        this.repository = Objects.requireNonNull(repository, "RiskMitigationMeasureJpaRepository must not be null.");
    }

    @Override
    public RiskMitigationMeasure save(RiskMitigationMeasure model) {
        return RiskPersistenceMapper.toDomain(repository.save(RiskPersistenceMapper.toEntity(model)));
    }

    @Override
    public Optional<RiskMitigationMeasure> findById(String id) {
        return repository.findById(id).map(RiskPersistenceMapper::toDomain);
    }
}
