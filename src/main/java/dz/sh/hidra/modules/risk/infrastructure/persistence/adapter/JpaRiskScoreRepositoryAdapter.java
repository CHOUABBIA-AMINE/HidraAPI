/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaRiskScoreRepositoryAdapter
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Adapter
 * @Layer       : Infrastructure
 * @Module      : risk
 * @Package     : dz.sh.hidra.modules.risk.infrastructure.persistence.adapter
 *
 * @Description : Database-backed adapter for RiskScore.
 *
 */
package dz.sh.hidra.modules.risk.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.risk.application.port.out.RiskScoreRepositoryPort;
import dz.sh.hidra.modules.risk.domain.model.RiskScore;
import dz.sh.hidra.modules.risk.infrastructure.persistence.mapper.RiskPersistenceMapper;
import dz.sh.hidra.modules.risk.infrastructure.persistence.repository.RiskScoreJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

/**
 * Database-backed repository adapter for RiskScore.
 */
@Component
public class JpaRiskScoreRepositoryAdapter implements RiskScoreRepositoryPort {

    private final RiskScoreJpaRepository repository;

    public JpaRiskScoreRepositoryAdapter(RiskScoreJpaRepository repository) {
        this.repository = Objects.requireNonNull(repository, "RiskScoreJpaRepository must not be null.");
    }

    @Override
    public RiskScore save(RiskScore model) {
        return RiskPersistenceMapper.toDomain(repository.save(RiskPersistenceMapper.toEntity(model)));
    }

    @Override
    public Optional<RiskScore> findById(String id) {
        return repository.findById(id).map(RiskPersistenceMapper::toDomain);
    }
}
