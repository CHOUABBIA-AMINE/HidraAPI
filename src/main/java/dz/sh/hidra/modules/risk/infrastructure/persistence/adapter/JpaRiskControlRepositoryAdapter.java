/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaRiskControlRepositoryAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : risk
 * @Package     : dz.sh.hidra.modules.risk.infrastructure.persistence.adapter
 *
 * @Description : Database-backed adapter for RiskControl.
 *
 */
package dz.sh.hidra.modules.risk.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.risk.application.port.out.RiskControlRepositoryPort;
import dz.sh.hidra.modules.risk.domain.model.RiskControl;
import dz.sh.hidra.modules.risk.infrastructure.persistence.mapper.RiskPersistenceMapper;
import dz.sh.hidra.modules.risk.infrastructure.persistence.repository.RiskControlJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

/**
 * Database-backed repository adapter for RiskControl.
 */
@Component
public class JpaRiskControlRepositoryAdapter implements RiskControlRepositoryPort {

    private final RiskControlJpaRepository repository;

    public JpaRiskControlRepositoryAdapter(RiskControlJpaRepository repository) {
        this.repository = Objects.requireNonNull(repository, "RiskControlJpaRepository must not be null.");
    }

    @Override
    public RiskControl save(RiskControl model) {
        return RiskPersistenceMapper.toDomain(repository.save(RiskPersistenceMapper.toEntity(model)));
    }

    @Override
    public Optional<RiskControl> findById(String id) {
        return repository.findById(id).map(RiskPersistenceMapper::toDomain);
    }
}
