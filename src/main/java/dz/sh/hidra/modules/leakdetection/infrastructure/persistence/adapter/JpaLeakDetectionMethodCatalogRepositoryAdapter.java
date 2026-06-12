/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaLeakDetectionMethodCatalogRepositoryAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : leakdetection
 * @Package     : dz.sh.hidra.modules.leakdetection.infrastructure.persistence.adapter
 *
 * @Description : Database-backed adapter for LeakDetectionMethodCatalog.
 *
 */
package dz.sh.hidra.modules.leakdetection.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.leakdetection.application.port.out.LeakDetectionMethodCatalogRepositoryPort;
import dz.sh.hidra.modules.leakdetection.domain.model.LeakDetectionMethodCatalog;
import dz.sh.hidra.modules.leakdetection.infrastructure.persistence.mapper.LeakDetectionPersistenceMapper;
import dz.sh.hidra.modules.leakdetection.infrastructure.persistence.repository.LeakDetectionMethodCatalogJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

/**
 * Database-backed repository adapter for LeakDetectionMethodCatalog.
 */
@Component
public class JpaLeakDetectionMethodCatalogRepositoryAdapter implements LeakDetectionMethodCatalogRepositoryPort {

    private final LeakDetectionMethodCatalogJpaRepository repository;

    public JpaLeakDetectionMethodCatalogRepositoryAdapter(LeakDetectionMethodCatalogJpaRepository repository) {
        this.repository = Objects.requireNonNull(repository, "LeakDetectionMethodCatalogJpaRepository must not be null.");
    }

    @Override
    public LeakDetectionMethodCatalog save(LeakDetectionMethodCatalog model) {
        return LeakDetectionPersistenceMapper.toDomain(repository.save(LeakDetectionPersistenceMapper.toEntity(model)));
    }

    @Override
    public Optional<LeakDetectionMethodCatalog> findById(String id) {
        return repository.findById(id).map(LeakDetectionPersistenceMapper::toDomain);
    }
}
