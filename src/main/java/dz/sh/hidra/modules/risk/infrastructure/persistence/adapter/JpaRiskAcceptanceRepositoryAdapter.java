/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaRiskAcceptanceRepositoryAdapter
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Adapter
 * @Layer       : Infrastructure
 * @Module      : risk
 * @Package     : dz.sh.hidra.modules.risk.infrastructure.persistence.adapter
 *
 * @Description : Database-backed adapter for RiskAcceptance.
 *
 */
package dz.sh.hidra.modules.risk.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.risk.application.port.out.RiskAcceptanceRepositoryPort;
import dz.sh.hidra.modules.risk.domain.model.RiskAcceptance;
import dz.sh.hidra.modules.risk.infrastructure.persistence.mapper.RiskPersistenceMapper;
import dz.sh.hidra.modules.risk.infrastructure.persistence.repository.RiskAcceptanceJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

/**
 * Database-backed repository adapter for RiskAcceptance.
 */
@Component
public class JpaRiskAcceptanceRepositoryAdapter implements RiskAcceptanceRepositoryPort {

    private final RiskAcceptanceJpaRepository repository;

    public JpaRiskAcceptanceRepositoryAdapter(RiskAcceptanceJpaRepository repository) {
        this.repository = Objects.requireNonNull(repository, "RiskAcceptanceJpaRepository must not be null.");
    }

    @Override
    public RiskAcceptance save(RiskAcceptance model) {
        return RiskPersistenceMapper.toDomain(repository.save(RiskPersistenceMapper.toEntity(model)));
    }

    @Override
    public Optional<RiskAcceptance> findById(String id) {
        return repository.findById(id).map(RiskPersistenceMapper::toDomain);
    }
}
