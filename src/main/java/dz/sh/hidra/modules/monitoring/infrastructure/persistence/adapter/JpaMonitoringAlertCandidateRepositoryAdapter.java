/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaMonitoringAlertCandidateRepositoryAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : monitoring
 * @Package     : dz.sh.hidra.modules.monitoring.infrastructure.persistence.adapter
 *
 * @Description : Database-backed adapter for MonitoringAlertCandidate.
 *
 */
package dz.sh.hidra.modules.monitoring.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.monitoring.application.port.out.MonitoringAlertCandidateRepositoryPort;
import dz.sh.hidra.modules.monitoring.domain.model.MonitoringAlertCandidate;
import dz.sh.hidra.modules.monitoring.infrastructure.persistence.mapper.MonitoringPersistenceMapper;
import dz.sh.hidra.modules.monitoring.infrastructure.persistence.repository.MonitoringAlertCandidateJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

/**
 * Database-backed repository adapter for MonitoringAlertCandidate.
 */
@Component
public class JpaMonitoringAlertCandidateRepositoryAdapter implements MonitoringAlertCandidateRepositoryPort {

    private final MonitoringAlertCandidateJpaRepository repository;

    public JpaMonitoringAlertCandidateRepositoryAdapter(MonitoringAlertCandidateJpaRepository repository) {
        this.repository = Objects.requireNonNull(repository, "MonitoringAlertCandidateJpaRepository must not be null.");
    }

    @Override
    public MonitoringAlertCandidate save(MonitoringAlertCandidate model) {
        return MonitoringPersistenceMapper.toDomain(repository.save(MonitoringPersistenceMapper.toEntity(model)));
    }

    @Override
    public Optional<MonitoringAlertCandidate> findById(String id) {
        return repository.findById(id).map(MonitoringPersistenceMapper::toDomain);
    }
}
