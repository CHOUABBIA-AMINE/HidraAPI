/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaRiskSourceRepositoryAdapter
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Adapter
 * @Layer       : Infrastructure
 * @Module      : risk
 * @Package     : dz.sh.hidra.modules.risk.infrastructure.persistence.adapter
 *
 * @Description : Database-backed adapter for RiskSource.
 *
 */
package dz.sh.hidra.modules.risk.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.risk.application.port.out.RiskSourceRepositoryPort;
import dz.sh.hidra.modules.risk.domain.model.RiskSource;
import dz.sh.hidra.modules.risk.infrastructure.persistence.mapper.RiskPersistenceMapper;
import dz.sh.hidra.modules.risk.infrastructure.persistence.repository.RiskSourceJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

/**
 * Database-backed repository adapter for RiskSource.
 */
@Component
public class JpaRiskSourceRepositoryAdapter implements RiskSourceRepositoryPort {

    private final RiskSourceJpaRepository repository;

    public JpaRiskSourceRepositoryAdapter(RiskSourceJpaRepository repository) {
        this.repository = Objects.requireNonNull(repository, "RiskSourceJpaRepository must not be null.");
    }

    @Override
    public RiskSource save(RiskSource model) {
        return RiskPersistenceMapper.toDomain(repository.save(RiskPersistenceMapper.toEntity(model)));
    }

    @Override
    public Optional<RiskSource> findById(String id) {
        return repository.findById(id).map(RiskPersistenceMapper::toDomain);
    }
}
