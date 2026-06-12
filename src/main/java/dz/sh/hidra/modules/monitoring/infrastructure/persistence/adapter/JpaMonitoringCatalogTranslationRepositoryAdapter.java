/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaMonitoringCatalogTranslationRepositoryAdapter
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Adapter
 * @Layer       : Infrastructure
 * @Module      : monitoring
 * @Package     : dz.sh.hidra.modules.monitoring.infrastructure.persistence.adapter
 *
 * @Description : Database-backed adapter for MonitoringCatalogTranslation.
 *
 */
package dz.sh.hidra.modules.monitoring.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.monitoring.application.port.out.MonitoringCatalogTranslationRepositoryPort;
import dz.sh.hidra.modules.monitoring.domain.model.MonitoringCatalogTranslation;
import dz.sh.hidra.modules.monitoring.infrastructure.persistence.mapper.MonitoringPersistenceMapper;
import dz.sh.hidra.modules.monitoring.infrastructure.persistence.repository.MonitoringCatalogTranslationJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

/**
 * Database-backed repository adapter for MonitoringCatalogTranslation.
 */
@Component
public class JpaMonitoringCatalogTranslationRepositoryAdapter implements MonitoringCatalogTranslationRepositoryPort {

    private final MonitoringCatalogTranslationJpaRepository repository;

    public JpaMonitoringCatalogTranslationRepositoryAdapter(MonitoringCatalogTranslationJpaRepository repository) {
        this.repository = Objects.requireNonNull(repository, "MonitoringCatalogTranslationJpaRepository must not be null.");
    }

    @Override
    public MonitoringCatalogTranslation save(MonitoringCatalogTranslation model) {
        return MonitoringPersistenceMapper.toDomain(repository.save(MonitoringPersistenceMapper.toEntity(model)));
    }

    @Override
    public Optional<MonitoringCatalogTranslation> findById(String id) {
        return repository.findById(id).map(MonitoringPersistenceMapper::toDomain);
    }
}
