/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaLeakDetectionMethodTranslationRepositoryAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : leakdetection
 * @Package     : dz.sh.hidra.modules.leakdetection.infrastructure.persistence.adapter
 *
 * @Description : Database-backed adapter for LeakDetectionMethodTranslation.
 *
 */
package dz.sh.hidra.modules.leakdetection.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.leakdetection.application.port.out.LeakDetectionMethodTranslationRepositoryPort;
import dz.sh.hidra.modules.leakdetection.domain.model.LeakDetectionMethodTranslation;
import dz.sh.hidra.modules.leakdetection.infrastructure.persistence.mapper.LeakDetectionPersistenceMapper;
import dz.sh.hidra.modules.leakdetection.infrastructure.persistence.repository.LeakDetectionMethodTranslationJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

/**
 * Database-backed repository adapter for LeakDetectionMethodTranslation.
 */
@Component
public class JpaLeakDetectionMethodTranslationRepositoryAdapter implements LeakDetectionMethodTranslationRepositoryPort {

    private final LeakDetectionMethodTranslationJpaRepository repository;

    public JpaLeakDetectionMethodTranslationRepositoryAdapter(LeakDetectionMethodTranslationJpaRepository repository) {
        this.repository = Objects.requireNonNull(repository, "LeakDetectionMethodTranslationJpaRepository must not be null.");
    }

    @Override
    public LeakDetectionMethodTranslation save(LeakDetectionMethodTranslation model) {
        return LeakDetectionPersistenceMapper.toDomain(repository.save(LeakDetectionPersistenceMapper.toEntity(model)));
    }

    @Override
    public Optional<LeakDetectionMethodTranslation> findById(String id) {
        return repository.findById(id).map(LeakDetectionPersistenceMapper::toDomain);
    }
}
