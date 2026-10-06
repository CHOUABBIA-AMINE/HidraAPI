/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaLeakEscalationReferenceRepositoryAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-06
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : leakdetection
 * @Package     : dz.sh.hidra.modules.leakdetection.infrastructure.persistence.adapter
 *
 * @Description : Database-backed adapter for LeakEscalationReference.
 *
 */
package dz.sh.hidra.modules.leakdetection.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.leakdetection.application.port.out.LeakEscalationReferenceRepositoryPort;
import dz.sh.hidra.modules.leakdetection.domain.model.LeakEscalationReference;
import dz.sh.hidra.modules.leakdetection.infrastructure.persistence.mapper.LeakDetectionPersistenceMapper;
import dz.sh.hidra.modules.leakdetection.infrastructure.persistence.repository.LeakEscalationReferenceJpaRepository;
import dz.sh.hidra.modules.leakdetection.domain.exception.InvalidLeakDetectionValueException;
import dz.sh.hidra.modules.leakdetection.infrastructure.persistence.repository.LeakCandidateJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

/**
 * Database-backed repository adapter for LeakEscalationReference.
 */
@Component
public class JpaLeakEscalationReferenceRepositoryAdapter implements LeakEscalationReferenceRepositoryPort {

    private final LeakEscalationReferenceJpaRepository repository;

    private final LeakCandidateJpaRepository candidateRepository;

    public JpaLeakEscalationReferenceRepositoryAdapter(
            LeakEscalationReferenceJpaRepository repository,
            LeakCandidateJpaRepository candidateRepository
    ) {
        this.repository = Objects.requireNonNull(repository, "LeakEscalationReferenceJpaRepository must not be null.");
        this.candidateRepository = Objects.requireNonNull(candidateRepository, "Candidate repository must not be null.");
    }

    @Override
    public LeakEscalationReference save(LeakEscalationReference model) {
        Objects.requireNonNull(model, "LeakEscalationReference must not be null.");
        if (model.candidateId() != null && !candidateRepository.existsById(model.candidateId())) {
            throw new InvalidLeakDetectionValueException(
                    "Leak escalation candidate must reference an existing LeakCandidate."
            );
        }
        return LeakDetectionPersistenceMapper.toDomain(repository.save(LeakDetectionPersistenceMapper.toEntity(model)));
    }

    @Override
    public Optional<LeakEscalationReference> findById(String id) {
        return repository.findById(id).map(LeakDetectionPersistenceMapper::toDomain);
    }
}
