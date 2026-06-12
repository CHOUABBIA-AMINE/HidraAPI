/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaRiskThreatRepositoryAdapter
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Adapter
 * @Layer       : Infrastructure
 * @Module      : risk
 * @Package     : dz.sh.hidra.modules.risk.infrastructure.persistence.adapter
 *
 * @Description : Database-backed adapter for RiskThreat.
 *
 */
package dz.sh.hidra.modules.risk.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.risk.application.port.out.RiskThreatRepositoryPort;
import dz.sh.hidra.modules.risk.domain.model.RiskThreat;
import dz.sh.hidra.modules.risk.infrastructure.persistence.mapper.RiskPersistenceMapper;
import dz.sh.hidra.modules.risk.infrastructure.persistence.repository.RiskThreatJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

/**
 * Database-backed repository adapter for RiskThreat.
 */
@Component
public class JpaRiskThreatRepositoryAdapter implements RiskThreatRepositoryPort {

    private final RiskThreatJpaRepository repository;

    public JpaRiskThreatRepositoryAdapter(RiskThreatJpaRepository repository) {
        this.repository = Objects.requireNonNull(repository, "RiskThreatJpaRepository must not be null.");
    }

    @Override
    public RiskThreat save(RiskThreat model) {
        return RiskPersistenceMapper.toDomain(repository.save(RiskPersistenceMapper.toEntity(model)));
    }

    @Override
    public Optional<RiskThreat> findById(String id) {
        return repository.findById(id).map(RiskPersistenceMapper::toDomain);
    }
}
