/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaRiskScenarioRepositoryAdapter
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Adapter
 * @Layer       : Infrastructure
 * @Module      : risk
 * @Package     : dz.sh.hidra.modules.risk.infrastructure.persistence.adapter
 *
 * @Description : Database-backed adapter for RiskScenario.
 *
 */
package dz.sh.hidra.modules.risk.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.risk.application.port.out.RiskScenarioRepositoryPort;
import dz.sh.hidra.modules.risk.domain.model.RiskScenario;
import dz.sh.hidra.modules.risk.infrastructure.persistence.mapper.RiskPersistenceMapper;
import dz.sh.hidra.modules.risk.infrastructure.persistence.repository.RiskScenarioJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

/**
 * Database-backed repository adapter for RiskScenario.
 */
@Component
public class JpaRiskScenarioRepositoryAdapter implements RiskScenarioRepositoryPort {

    private final RiskScenarioJpaRepository repository;

    public JpaRiskScenarioRepositoryAdapter(RiskScenarioJpaRepository repository) {
        this.repository = Objects.requireNonNull(repository, "RiskScenarioJpaRepository must not be null.");
    }

    @Override
    public RiskScenario save(RiskScenario model) {
        return RiskPersistenceMapper.toDomain(repository.save(RiskPersistenceMapper.toEntity(model)));
    }

    @Override
    public Optional<RiskScenario> findById(String id) {
        return repository.findById(id).map(RiskPersistenceMapper::toDomain);
    }
}
