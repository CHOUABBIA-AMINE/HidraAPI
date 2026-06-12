/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaMonitoringThresholdRepositoryAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : monitoring
 * @Package     : dz.sh.hidra.modules.monitoring.infrastructure.persistence.adapter
 *
 * @Description : Database-backed adapter for MonitoringThreshold.
 *
 */
package dz.sh.hidra.modules.monitoring.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.monitoring.application.port.out.MonitoringThresholdRepositoryPort;
import dz.sh.hidra.modules.monitoring.domain.model.MonitoringThreshold;
import dz.sh.hidra.modules.monitoring.infrastructure.persistence.mapper.MonitoringPersistenceMapper;
import dz.sh.hidra.modules.monitoring.infrastructure.persistence.repository.MonitoringThresholdJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

/**
 * Database-backed repository adapter for MonitoringThreshold.
 */
@Component
public class JpaMonitoringThresholdRepositoryAdapter implements MonitoringThresholdRepositoryPort {

    private final MonitoringThresholdJpaRepository repository;

    public JpaMonitoringThresholdRepositoryAdapter(MonitoringThresholdJpaRepository repository) {
        this.repository = Objects.requireNonNull(repository, "MonitoringThresholdJpaRepository must not be null.");
    }

    @Override
    public MonitoringThreshold save(MonitoringThreshold model) {
        return MonitoringPersistenceMapper.toDomain(repository.save(MonitoringPersistenceMapper.toEntity(model)));
    }

    @Override
    public Optional<MonitoringThreshold> findById(String id) {
        return repository.findById(id).map(MonitoringPersistenceMapper::toDomain);
    }
}
