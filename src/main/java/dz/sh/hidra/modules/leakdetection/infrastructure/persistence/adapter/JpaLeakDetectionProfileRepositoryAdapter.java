/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaLeakDetectionProfileRepositoryAdapter
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Adapter
 * @Layer       : Infrastructure
 * @Module      : leakdetection
 * @Package     : dz.sh.hidra.modules.leakdetection.infrastructure.persistence.adapter
 *
 * @Description : Database-backed adapter for LeakDetectionProfile.
 *
 */
package dz.sh.hidra.modules.leakdetection.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.leakdetection.application.port.out.LeakDetectionProfileRepositoryPort;
import dz.sh.hidra.modules.leakdetection.domain.model.LeakDetectionProfile;
import dz.sh.hidra.modules.leakdetection.infrastructure.persistence.mapper.LeakDetectionPersistenceMapper;
import dz.sh.hidra.modules.leakdetection.infrastructure.persistence.repository.LeakDetectionProfileJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

/**
 * Database-backed repository adapter for LeakDetectionProfile.
 */
@Component
public class JpaLeakDetectionProfileRepositoryAdapter implements LeakDetectionProfileRepositoryPort {

    private final LeakDetectionProfileJpaRepository repository;

    public JpaLeakDetectionProfileRepositoryAdapter(LeakDetectionProfileJpaRepository repository) {
        this.repository = Objects.requireNonNull(repository, "LeakDetectionProfileJpaRepository must not be null.");
    }

    @Override
    public LeakDetectionProfile save(LeakDetectionProfile model) {
        return LeakDetectionPersistenceMapper.toDomain(repository.save(LeakDetectionPersistenceMapper.toEntity(model)));
    }

    @Override
    public Optional<LeakDetectionProfile> findById(String id) {
        return repository.findById(id).map(LeakDetectionPersistenceMapper::toDomain);
    }
}
