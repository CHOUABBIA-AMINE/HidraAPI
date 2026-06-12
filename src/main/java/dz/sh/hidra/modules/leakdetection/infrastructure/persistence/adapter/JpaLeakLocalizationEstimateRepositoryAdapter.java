/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaLeakLocalizationEstimateRepositoryAdapter
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Adapter
 * @Layer       : Infrastructure
 * @Module      : leakdetection
 * @Package     : dz.sh.hidra.modules.leakdetection.infrastructure.persistence.adapter
 *
 * @Description : Database-backed adapter for LeakLocalizationEstimate.
 *
 */
package dz.sh.hidra.modules.leakdetection.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.leakdetection.application.port.out.LeakLocalizationEstimateRepositoryPort;
import dz.sh.hidra.modules.leakdetection.domain.model.LeakLocalizationEstimate;
import dz.sh.hidra.modules.leakdetection.infrastructure.persistence.mapper.LeakDetectionPersistenceMapper;
import dz.sh.hidra.modules.leakdetection.infrastructure.persistence.repository.LeakLocalizationEstimateJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

/**
 * Database-backed repository adapter for LeakLocalizationEstimate.
 */
@Component
public class JpaLeakLocalizationEstimateRepositoryAdapter implements LeakLocalizationEstimateRepositoryPort {

    private final LeakLocalizationEstimateJpaRepository repository;

    public JpaLeakLocalizationEstimateRepositoryAdapter(LeakLocalizationEstimateJpaRepository repository) {
        this.repository = Objects.requireNonNull(repository, "LeakLocalizationEstimateJpaRepository must not be null.");
    }

    @Override
    public LeakLocalizationEstimate save(LeakLocalizationEstimate model) {
        return LeakDetectionPersistenceMapper.toDomain(repository.save(LeakDetectionPersistenceMapper.toEntity(model)));
    }

    @Override
    public Optional<LeakLocalizationEstimate> findById(String id) {
        return repository.findById(id).map(LeakDetectionPersistenceMapper::toDomain);
    }
}
