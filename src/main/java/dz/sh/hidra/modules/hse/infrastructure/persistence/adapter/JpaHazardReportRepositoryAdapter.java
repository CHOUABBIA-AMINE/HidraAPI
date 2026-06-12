/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaHazardReportRepositoryAdapter
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Adapter
 * @Layer       : Infrastructure
 * @Module      : hse
 * @Package     : dz.sh.hidra.modules.hse.infrastructure.persistence.adapter
 *
 * @Description : Database-backed adapter for HazardReport.
 *
 */
package dz.sh.hidra.modules.hse.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.hse.application.port.out.HazardReportRepositoryPort;
import dz.sh.hidra.modules.hse.domain.model.HazardReport;
import dz.sh.hidra.modules.hse.infrastructure.persistence.mapper.HsePersistenceMapper;
import dz.sh.hidra.modules.hse.infrastructure.persistence.repository.HazardReportJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

/**
 * Database-backed repository adapter for HazardReport.
 */
@Component
public class JpaHazardReportRepositoryAdapter implements HazardReportRepositoryPort {

    private final HazardReportJpaRepository repository;

    public JpaHazardReportRepositoryAdapter(HazardReportJpaRepository repository) {
        this.repository = Objects.requireNonNull(repository, "HazardReportJpaRepository must not be null.");
    }

    @Override
    public HazardReport save(HazardReport model) {
        return HsePersistenceMapper.toDomain(repository.save(HsePersistenceMapper.toEntity(model)));
    }

    @Override
    public Optional<HazardReport> findById(String id) {
        return repository.findById(id).map(HsePersistenceMapper::toDomain);
    }
}
