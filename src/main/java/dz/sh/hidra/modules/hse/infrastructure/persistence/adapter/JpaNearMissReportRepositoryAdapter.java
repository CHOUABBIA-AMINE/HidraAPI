/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaNearMissReportRepositoryAdapter
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Adapter
 * @Layer       : Infrastructure
 * @Module      : hse
 * @Package     : dz.sh.hidra.modules.hse.infrastructure.persistence.adapter
 *
 * @Description : Database-backed adapter for NearMissReport.
 *
 */
package dz.sh.hidra.modules.hse.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.hse.application.port.out.NearMissReportRepositoryPort;
import dz.sh.hidra.modules.hse.domain.model.NearMissReport;
import dz.sh.hidra.modules.hse.infrastructure.persistence.mapper.HsePersistenceMapper;
import dz.sh.hidra.modules.hse.infrastructure.persistence.repository.NearMissReportJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

/**
 * Database-backed repository adapter for NearMissReport.
 */
@Component
public class JpaNearMissReportRepositoryAdapter implements NearMissReportRepositoryPort {

    private final NearMissReportJpaRepository repository;

    public JpaNearMissReportRepositoryAdapter(NearMissReportJpaRepository repository) {
        this.repository = Objects.requireNonNull(repository, "NearMissReportJpaRepository must not be null.");
    }

    @Override
    public NearMissReport save(NearMissReport model) {
        return HsePersistenceMapper.toDomain(repository.save(HsePersistenceMapper.toEntity(model)));
    }

    @Override
    public Optional<NearMissReport> findById(String id) {
        return repository.findById(id).map(HsePersistenceMapper::toDomain);
    }
}
