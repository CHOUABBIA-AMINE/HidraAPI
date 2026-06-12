/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaRiskLikelihoodRepositoryAdapter
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Adapter
 * @Layer       : Infrastructure
 * @Module      : risk
 * @Package     : dz.sh.hidra.modules.risk.infrastructure.persistence.adapter
 *
 * @Description : Database-backed adapter for RiskLikelihood.
 *
 */
package dz.sh.hidra.modules.risk.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.risk.application.port.out.RiskLikelihoodRepositoryPort;
import dz.sh.hidra.modules.risk.domain.model.RiskLikelihood;
import dz.sh.hidra.modules.risk.infrastructure.persistence.mapper.RiskPersistenceMapper;
import dz.sh.hidra.modules.risk.infrastructure.persistence.repository.RiskLikelihoodJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

/**
 * Database-backed repository adapter for RiskLikelihood.
 */
@Component
public class JpaRiskLikelihoodRepositoryAdapter implements RiskLikelihoodRepositoryPort {

    private final RiskLikelihoodJpaRepository repository;

    public JpaRiskLikelihoodRepositoryAdapter(RiskLikelihoodJpaRepository repository) {
        this.repository = Objects.requireNonNull(repository, "RiskLikelihoodJpaRepository must not be null.");
    }

    @Override
    public RiskLikelihood save(RiskLikelihood model) {
        return RiskPersistenceMapper.toDomain(repository.save(RiskPersistenceMapper.toEntity(model)));
    }

    @Override
    public Optional<RiskLikelihood> findById(String id) {
        return repository.findById(id).map(RiskPersistenceMapper::toDomain);
    }
}
