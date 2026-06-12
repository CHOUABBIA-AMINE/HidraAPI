/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaLeakVerificationActionRepositoryAdapter
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Adapter
 * @Layer       : Infrastructure
 * @Module      : leakdetection
 * @Package     : dz.sh.hidra.modules.leakdetection.infrastructure.persistence.adapter
 *
 * @Description : Database-backed adapter for LeakVerificationAction.
 *
 */
package dz.sh.hidra.modules.leakdetection.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.leakdetection.application.port.out.LeakVerificationActionRepositoryPort;
import dz.sh.hidra.modules.leakdetection.domain.model.LeakVerificationAction;
import dz.sh.hidra.modules.leakdetection.infrastructure.persistence.mapper.LeakDetectionPersistenceMapper;
import dz.sh.hidra.modules.leakdetection.infrastructure.persistence.repository.LeakVerificationActionJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

/**
 * Database-backed repository adapter for LeakVerificationAction.
 */
@Component
public class JpaLeakVerificationActionRepositoryAdapter implements LeakVerificationActionRepositoryPort {

    private final LeakVerificationActionJpaRepository repository;

    public JpaLeakVerificationActionRepositoryAdapter(LeakVerificationActionJpaRepository repository) {
        this.repository = Objects.requireNonNull(repository, "LeakVerificationActionJpaRepository must not be null.");
    }

    @Override
    public LeakVerificationAction save(LeakVerificationAction model) {
        return LeakDetectionPersistenceMapper.toDomain(repository.save(LeakDetectionPersistenceMapper.toEntity(model)));
    }

    @Override
    public Optional<LeakVerificationAction> findById(String id) {
        return repository.findById(id).map(LeakDetectionPersistenceMapper::toDomain);
    }
}
