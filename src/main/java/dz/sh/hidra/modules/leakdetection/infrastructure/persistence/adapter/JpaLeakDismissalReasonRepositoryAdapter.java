/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaLeakDismissalReasonRepositoryAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : leakdetection
 * @Package     : dz.sh.hidra.modules.leakdetection.infrastructure.persistence.adapter
 *
 * @Description : Database-backed adapter for LeakDismissalReason.
 *
 */
package dz.sh.hidra.modules.leakdetection.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.leakdetection.application.port.out.LeakDismissalReasonRepositoryPort;
import dz.sh.hidra.modules.leakdetection.domain.model.LeakDismissalReason;
import dz.sh.hidra.modules.leakdetection.infrastructure.persistence.mapper.LeakDetectionPersistenceMapper;
import dz.sh.hidra.modules.leakdetection.infrastructure.persistence.repository.LeakDismissalReasonJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

/**
 * Database-backed repository adapter for LeakDismissalReason.
 */
@Component
public class JpaLeakDismissalReasonRepositoryAdapter implements LeakDismissalReasonRepositoryPort {

    private final LeakDismissalReasonJpaRepository repository;

    public JpaLeakDismissalReasonRepositoryAdapter(LeakDismissalReasonJpaRepository repository) {
        this.repository = Objects.requireNonNull(repository, "LeakDismissalReasonJpaRepository must not be null.");
    }

    @Override
    public LeakDismissalReason save(LeakDismissalReason model) {
        return LeakDetectionPersistenceMapper.toDomain(repository.save(LeakDetectionPersistenceMapper.toEntity(model)));
    }

    @Override
    public Optional<LeakDismissalReason> findById(String id) {
        return repository.findById(id).map(LeakDetectionPersistenceMapper::toDomain);
    }
}
