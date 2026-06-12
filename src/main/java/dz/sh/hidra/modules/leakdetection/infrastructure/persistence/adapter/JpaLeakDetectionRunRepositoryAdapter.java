/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaLeakDetectionRunRepositoryAdapter
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Adapter
 * @Layer       : Infrastructure
 * @Module      : leakdetection
 * @Package     : dz.sh.hidra.modules.leakdetection.infrastructure.persistence.adapter
 *
 * @Description : Database-backed adapter for LeakDetectionRun.
 *
 */
package dz.sh.hidra.modules.leakdetection.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.leakdetection.application.port.out.LeakDetectionRunRepositoryPort;
import dz.sh.hidra.modules.leakdetection.domain.model.LeakDetectionRun;
import dz.sh.hidra.modules.leakdetection.infrastructure.persistence.mapper.LeakDetectionPersistenceMapper;
import dz.sh.hidra.modules.leakdetection.infrastructure.persistence.repository.LeakDetectionRunJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

/**
 * Database-backed repository adapter for LeakDetectionRun.
 */
@Component
public class JpaLeakDetectionRunRepositoryAdapter implements LeakDetectionRunRepositoryPort {

    private final LeakDetectionRunJpaRepository repository;

    public JpaLeakDetectionRunRepositoryAdapter(LeakDetectionRunJpaRepository repository) {
        this.repository = Objects.requireNonNull(repository, "LeakDetectionRunJpaRepository must not be null.");
    }

    @Override
    public LeakDetectionRun save(LeakDetectionRun model) {
        return LeakDetectionPersistenceMapper.toDomain(repository.save(LeakDetectionPersistenceMapper.toEntity(model)));
    }

    @Override
    public Optional<LeakDetectionRun> findById(String id) {
        return repository.findById(id).map(LeakDetectionPersistenceMapper::toDomain);
    }
}
