/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaMonitoringAcknowledgementRepositoryAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : monitoring
 * @Package     : dz.sh.hidra.modules.monitoring.infrastructure.persistence.adapter
 *
 * @Description : Database-backed adapter for MonitoringAcknowledgement.
 *
 */
package dz.sh.hidra.modules.monitoring.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.monitoring.application.port.out.MonitoringAcknowledgementRepositoryPort;
import dz.sh.hidra.modules.monitoring.domain.model.MonitoringAcknowledgement;
import dz.sh.hidra.modules.monitoring.infrastructure.persistence.mapper.MonitoringPersistenceMapper;
import dz.sh.hidra.modules.monitoring.infrastructure.persistence.repository.MonitoringAcknowledgementJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

/**
 * Database-backed repository adapter for MonitoringAcknowledgement.
 */
@Component
public class JpaMonitoringAcknowledgementRepositoryAdapter implements MonitoringAcknowledgementRepositoryPort {

    private final MonitoringAcknowledgementJpaRepository repository;

    public JpaMonitoringAcknowledgementRepositoryAdapter(MonitoringAcknowledgementJpaRepository repository) {
        this.repository = Objects.requireNonNull(repository, "MonitoringAcknowledgementJpaRepository must not be null.");
    }

    @Override
    public MonitoringAcknowledgement save(MonitoringAcknowledgement model) {
        return MonitoringPersistenceMapper.toDomain(repository.save(MonitoringPersistenceMapper.toEntity(model)));
    }

    @Override
    public Optional<MonitoringAcknowledgement> findById(String id) {
        return repository.findById(id).map(MonitoringPersistenceMapper::toDomain);
    }
}
