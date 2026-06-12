/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaRiskSignalRepositoryAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : monitoring
 * @Package     : dz.sh.hidra.modules.monitoring.infrastructure.persistence.adapter
 *
 * @Description : Database-backed adapter for RiskSignal.
 *
 */
package dz.sh.hidra.modules.monitoring.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.monitoring.application.port.out.RiskSignalRepositoryPort;
import dz.sh.hidra.modules.monitoring.domain.model.RiskSignal;
import dz.sh.hidra.modules.monitoring.infrastructure.persistence.mapper.MonitoringPersistenceMapper;
import dz.sh.hidra.modules.monitoring.infrastructure.persistence.repository.RiskSignalJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

/**
 * Database-backed repository adapter for RiskSignal.
 */
@Component
public class JpaRiskSignalRepositoryAdapter implements RiskSignalRepositoryPort {

    private final RiskSignalJpaRepository repository;

    public JpaRiskSignalRepositoryAdapter(RiskSignalJpaRepository repository) {
        this.repository = Objects.requireNonNull(repository, "RiskSignalJpaRepository must not be null.");
    }

    @Override
    public RiskSignal save(RiskSignal model) {
        return MonitoringPersistenceMapper.toDomain(repository.save(MonitoringPersistenceMapper.toEntity(model)));
    }

    @Override
    public Optional<RiskSignal> findById(String id) {
        return repository.findById(id).map(MonitoringPersistenceMapper::toDomain);
    }
}
