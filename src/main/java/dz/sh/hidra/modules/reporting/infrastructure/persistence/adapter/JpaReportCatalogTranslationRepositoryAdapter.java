/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaReportCatalogTranslationRepositoryAdapter
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Adapter
 * @Layer       : Infrastructure
 * @Module      : reporting
 * @Package     : dz.sh.hidra.modules.reporting.infrastructure.persistence.adapter
 *
 * @Description : Database-backed adapter for ReportCatalogTranslation.
 *
 */
package dz.sh.hidra.modules.reporting.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.reporting.application.port.out.ReportCatalogTranslationRepositoryPort;
import dz.sh.hidra.modules.reporting.domain.model.ReportCatalogTranslation;
import dz.sh.hidra.modules.reporting.infrastructure.persistence.mapper.ReportingPersistenceMapper;
import dz.sh.hidra.modules.reporting.infrastructure.persistence.repository.ReportCatalogTranslationJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

/**
 * Database-backed repository adapter for ReportCatalogTranslation.
 */
@Component
public class JpaReportCatalogTranslationRepositoryAdapter implements ReportCatalogTranslationRepositoryPort {

    private final ReportCatalogTranslationJpaRepository repository;

    public JpaReportCatalogTranslationRepositoryAdapter(ReportCatalogTranslationJpaRepository repository) {
        this.repository = Objects.requireNonNull(repository, "ReportCatalogTranslationJpaRepository must not be null.");
    }

    @Override
    public ReportCatalogTranslation save(ReportCatalogTranslation model) {
        return ReportingPersistenceMapper.toDomain(repository.save(ReportingPersistenceMapper.toEntity(model)));
    }

    @Override
    public Optional<ReportCatalogTranslation> findById(String id) {
        return repository.findById(id).map(ReportingPersistenceMapper::toDomain);
    }
}
