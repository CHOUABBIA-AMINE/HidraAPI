/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaLeakCandidateRepositoryAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-04
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : leakdetection
 * @Package     : dz.sh.hidra.modules.leakdetection.infrastructure.persistence.adapter
 *
 * @Description : Database-backed adapter enforcing LeakCandidate profile/run provenance.
 *
 */
package dz.sh.hidra.modules.leakdetection.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.leakdetection.application.port.out.LeakCandidateRepositoryPort;
import dz.sh.hidra.modules.leakdetection.domain.exception.InvalidLeakDetectionValueException;
import dz.sh.hidra.modules.leakdetection.domain.model.LeakCandidate;
import dz.sh.hidra.modules.leakdetection.domain.service.LeakConfidenceClassifier;
import dz.sh.hidra.modules.leakdetection.domain.value.LeakDetectionProfileStatus;
import dz.sh.hidra.modules.leakdetection.domain.value.LeakDetectionRunStatus;
import dz.sh.hidra.modules.leakdetection.infrastructure.persistence.mapper.LeakDetectionPersistenceMapper;
import dz.sh.hidra.modules.leakdetection.infrastructure.persistence.repository.LeakCandidateJpaRepository;
import dz.sh.hidra.modules.leakdetection.infrastructure.persistence.repository.LeakDetectionProfileJpaRepository;
import dz.sh.hidra.modules.leakdetection.infrastructure.persistence.repository.LeakDetectionRunJpaRepository;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

/**
 * Database-backed repository adapter for LeakCandidate.
 */
@Component
public class JpaLeakCandidateRepositoryAdapter implements LeakCandidateRepositoryPort {

    private static final LeakConfidenceClassifier CONFIDENCE_CLASSIFIER = new LeakConfidenceClassifier();

    private final LeakCandidateJpaRepository repository;
    private final LeakDetectionProfileJpaRepository profileRepository;
    private final LeakDetectionRunJpaRepository runRepository;

    public JpaLeakCandidateRepositoryAdapter(
            LeakCandidateJpaRepository repository,
            LeakDetectionProfileJpaRepository profileRepository,
            LeakDetectionRunJpaRepository runRepository
    ) {
        this.repository = Objects.requireNonNull(repository, "LeakCandidateJpaRepository must not be null.");
        this.profileRepository = Objects.requireNonNull(
                profileRepository,
                "LeakDetectionProfileJpaRepository must not be null."
        );
        this.runRepository = Objects.requireNonNull(
                runRepository,
                "LeakDetectionRunJpaRepository must not be null."
        );
    }

    @Override
    public LeakCandidate save(LeakCandidate model) {
        Objects.requireNonNull(model, "LeakCandidate must not be null.");
        validateDerivedSeverity(model);
        if (!repository.existsById(model.id())) {
            validateCreationProvenance(model);
        }
        return LeakDetectionPersistenceMapper.toDomain(
                repository.save(LeakDetectionPersistenceMapper.toEntity(model))
        );
    }

    @Override
    public Optional<LeakCandidate> findById(String id) {
        return repository.findById(id).map(LeakDetectionPersistenceMapper::toDomain);
    }

    @Override
    public List<LeakCandidate> findAll(int page, int size) {
        return repository.findAll(PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "updatedAt")))
                .stream()
                .map(LeakDetectionPersistenceMapper::toDomain)
                .toList();
    }

    @Override
    public long count() {
        return repository.count();
    }

    private static void validateDerivedSeverity(LeakCandidate model) {
        if (model.severityLevel() != CONFIDENCE_CLASSIFIER.classify(model.confidenceScore())) {
            throw new InvalidLeakDetectionValueException(
                    "LeakCandidate persisted severity must be derived from confidence score."
            );
        }
    }

    private void validateCreationProvenance(LeakCandidate model) {
        var profile = profileRepository.findById(model.profileId())
                .orElseThrow(() -> new InvalidLeakDetectionValueException(
                        "LeakCandidate profile must reference an existing LeakDetectionProfile."
                ));
        if (profile.status() != LeakDetectionProfileStatus.ACTIVE) {
            throw new InvalidLeakDetectionValueException(
                    "LeakCandidate creation requires an ACTIVE LeakDetectionProfile."
            );
        }

        if (model.runId() == null) {
            return;
        }

        var run = runRepository.findById(model.runId())
                .orElseThrow(() -> new InvalidLeakDetectionValueException(
                        "LeakCandidate run must reference an existing LeakDetectionRun."
                ));
        if (!model.profileId().equals(run.profileId())) {
            throw new InvalidLeakDetectionValueException(
                    "LeakCandidate run and profile provenance must match."
            );
        }
        if (run.status() != LeakDetectionRunStatus.RUNNING
                && run.status() != LeakDetectionRunStatus.COMPLETED) {
            throw new InvalidLeakDetectionValueException(
                    "LeakCandidate run must be RUNNING or COMPLETED."
            );
        }
    }
}
