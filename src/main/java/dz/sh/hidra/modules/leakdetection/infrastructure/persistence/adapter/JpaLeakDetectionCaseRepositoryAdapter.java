/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaLeakDetectionCaseRepositoryAdapter
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Adapter
 * @Layer       : Infrastructure
 * @Module      : leakdetection
 * @Package     : dz.sh.hidra.modules.leakdetection.infrastructure.persistence.adapter
 *
 * @Description : Database-backed adapter for LeakDetectionCase.
 *
 */
package dz.sh.hidra.modules.leakdetection.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.leakdetection.application.port.out.LeakDetectionCaseRepositoryPort;
import dz.sh.hidra.modules.leakdetection.domain.model.LeakDetectionCase;
import dz.sh.hidra.modules.leakdetection.infrastructure.persistence.mapper.LeakDetectionPersistenceMapper;
import dz.sh.hidra.modules.leakdetection.infrastructure.persistence.repository.LeakDetectionCaseJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

/**
 * Database-backed repository adapter for LeakDetectionCase.
 */
@Component
public class JpaLeakDetectionCaseRepositoryAdapter implements LeakDetectionCaseRepositoryPort {

    private final LeakDetectionCaseJpaRepository repository;

    public JpaLeakDetectionCaseRepositoryAdapter(LeakDetectionCaseJpaRepository repository) {
        this.repository = Objects.requireNonNull(repository, "LeakDetectionCaseJpaRepository must not be null.");
    }

    @Override
    public LeakDetectionCase save(LeakDetectionCase model) {
        return LeakDetectionPersistenceMapper.toDomain(repository.save(LeakDetectionPersistenceMapper.toEntity(model)));
    }

    @Override
    public Optional<LeakDetectionCase> findById(String id) {
        return repository.findById(id).map(LeakDetectionPersistenceMapper::toDomain);
    }
}
