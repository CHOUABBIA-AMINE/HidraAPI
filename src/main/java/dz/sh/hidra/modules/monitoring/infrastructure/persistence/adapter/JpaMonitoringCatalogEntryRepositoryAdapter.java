/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaMonitoringCatalogEntryRepositoryAdapter
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Adapter
 * @Layer       : Infrastructure
 * @Module      : monitoring
 * @Package     : dz.sh.hidra.modules.monitoring.infrastructure.persistence.adapter
 *
 * @Description : Database-backed adapter for MonitoringCatalogEntry.
 *
 */
package dz.sh.hidra.modules.monitoring.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.monitoring.application.port.out.MonitoringCatalogEntryRepositoryPort;
import dz.sh.hidra.modules.monitoring.domain.model.MonitoringCatalogEntry;
import dz.sh.hidra.modules.monitoring.infrastructure.persistence.mapper.MonitoringPersistenceMapper;
import dz.sh.hidra.modules.monitoring.infrastructure.persistence.repository.MonitoringCatalogEntryJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

/**
 * Database-backed repository adapter for MonitoringCatalogEntry.
 */
@Component
public class JpaMonitoringCatalogEntryRepositoryAdapter implements MonitoringCatalogEntryRepositoryPort {

    private final MonitoringCatalogEntryJpaRepository repository;

    public JpaMonitoringCatalogEntryRepositoryAdapter(MonitoringCatalogEntryJpaRepository repository) {
        this.repository = Objects.requireNonNull(repository, "MonitoringCatalogEntryJpaRepository must not be null.");
    }

    @Override
    public MonitoringCatalogEntry save(MonitoringCatalogEntry model) {
        return MonitoringPersistenceMapper.toDomain(repository.save(MonitoringPersistenceMapper.toEntity(model)));
    }

    @Override
    public Optional<MonitoringCatalogEntry> findById(String id) {
        return repository.findById(id).map(MonitoringPersistenceMapper::toDomain);
    }
}
