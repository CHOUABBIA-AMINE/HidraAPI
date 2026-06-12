/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaLeakSeverityAssessmentRepositoryAdapter
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Adapter
 * @Layer       : Infrastructure
 * @Module      : leakdetection
 * @Package     : dz.sh.hidra.modules.leakdetection.infrastructure.persistence.adapter
 *
 * @Description : Database-backed adapter for LeakSeverityAssessment.
 *
 */
package dz.sh.hidra.modules.leakdetection.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.leakdetection.application.port.out.LeakSeverityAssessmentRepositoryPort;
import dz.sh.hidra.modules.leakdetection.domain.model.LeakSeverityAssessment;
import dz.sh.hidra.modules.leakdetection.infrastructure.persistence.mapper.LeakDetectionPersistenceMapper;
import dz.sh.hidra.modules.leakdetection.infrastructure.persistence.repository.LeakSeverityAssessmentJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

/**
 * Database-backed repository adapter for LeakSeverityAssessment.
 */
@Component
public class JpaLeakSeverityAssessmentRepositoryAdapter implements LeakSeverityAssessmentRepositoryPort {

    private final LeakSeverityAssessmentJpaRepository repository;

    public JpaLeakSeverityAssessmentRepositoryAdapter(LeakSeverityAssessmentJpaRepository repository) {
        this.repository = Objects.requireNonNull(repository, "LeakSeverityAssessmentJpaRepository must not be null.");
    }

    @Override
    public LeakSeverityAssessment save(LeakSeverityAssessment model) {
        return LeakDetectionPersistenceMapper.toDomain(repository.save(LeakDetectionPersistenceMapper.toEntity(model)));
    }

    @Override
    public Optional<LeakSeverityAssessment> findById(String id) {
        return repository.findById(id).map(LeakDetectionPersistenceMapper::toDomain);
    }
}
