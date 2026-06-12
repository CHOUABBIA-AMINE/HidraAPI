/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaReportCatalogEntryRepositoryAdapter
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Adapter
 * @Layer       : Infrastructure
 * @Module      : reporting
 * @Package     : dz.sh.hidra.modules.reporting.infrastructure.persistence.adapter
 *
 * @Description : Database-backed adapter for ReportCatalogEntry.
 *
 */
package dz.sh.hidra.modules.reporting.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.reporting.application.port.out.ReportCatalogEntryRepositoryPort;
import dz.sh.hidra.modules.reporting.domain.model.ReportCatalogEntry;
import dz.sh.hidra.modules.reporting.infrastructure.persistence.mapper.ReportingPersistenceMapper;
import dz.sh.hidra.modules.reporting.infrastructure.persistence.repository.ReportCatalogEntryJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

/**
 * Database-backed repository adapter for ReportCatalogEntry.
 */
@Component
public class JpaReportCatalogEntryRepositoryAdapter implements ReportCatalogEntryRepositoryPort {

    private final ReportCatalogEntryJpaRepository repository;

    public JpaReportCatalogEntryRepositoryAdapter(ReportCatalogEntryJpaRepository repository) {
        this.repository = Objects.requireNonNull(repository, "ReportCatalogEntryJpaRepository must not be null.");
    }

    @Override
    public ReportCatalogEntry save(ReportCatalogEntry model) {
        return ReportingPersistenceMapper.toDomain(repository.save(ReportingPersistenceMapper.toEntity(model)));
    }

    @Override
    public Optional<ReportCatalogEntry> findById(String id) {
        return repository.findById(id).map(ReportingPersistenceMapper::toDomain);
    }
}
