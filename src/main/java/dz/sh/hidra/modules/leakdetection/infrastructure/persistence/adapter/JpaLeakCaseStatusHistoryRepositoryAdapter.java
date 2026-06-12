/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaLeakCaseStatusHistoryRepositoryAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : leakdetection
 * @Package     : dz.sh.hidra.modules.leakdetection.infrastructure.persistence.adapter
 *
 * @Description : Database-backed adapter for LeakCaseStatusHistory.
 *
 */
package dz.sh.hidra.modules.leakdetection.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.leakdetection.application.port.out.LeakCaseStatusHistoryRepositoryPort;
import dz.sh.hidra.modules.leakdetection.domain.model.LeakCaseStatusHistory;
import dz.sh.hidra.modules.leakdetection.infrastructure.persistence.mapper.LeakDetectionPersistenceMapper;
import dz.sh.hidra.modules.leakdetection.infrastructure.persistence.repository.LeakCaseStatusHistoryJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

/**
 * Database-backed repository adapter for LeakCaseStatusHistory.
 */
@Component
public class JpaLeakCaseStatusHistoryRepositoryAdapter implements LeakCaseStatusHistoryRepositoryPort {

    private final LeakCaseStatusHistoryJpaRepository repository;

    public JpaLeakCaseStatusHistoryRepositoryAdapter(LeakCaseStatusHistoryJpaRepository repository) {
        this.repository = Objects.requireNonNull(repository, "LeakCaseStatusHistoryJpaRepository must not be null.");
    }

    @Override
    public LeakCaseStatusHistory save(LeakCaseStatusHistory model) {
        return LeakDetectionPersistenceMapper.toDomain(repository.save(LeakDetectionPersistenceMapper.toEntity(model)));
    }

    @Override
    public Optional<LeakCaseStatusHistory> findById(String id) {
        return repository.findById(id).map(LeakDetectionPersistenceMapper::toDomain);
    }
}
