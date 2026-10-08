/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaIntegrityAssessmentRepositoryAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-08
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : integrity
 * @Package     : dz.sh.hidra.modules.integrity.infrastructure.persistence.adapter
 *
 * @Description : Database-backed adapter for IntegrityAssessment.
 *
 */
package dz.sh.hidra.modules.integrity.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.integrity.application.port.out.IntegrityAssessmentRepositoryPort;
import dz.sh.hidra.modules.integrity.domain.model.IntegrityAssessment;
import dz.sh.hidra.modules.integrity.infrastructure.persistence.mapper.IntegrityPersistenceMapper;
import dz.sh.hidra.modules.integrity.infrastructure.persistence.repository.IntegrityAssessmentJpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.Optional;

/**
 * Database-backed repository adapter for IntegrityAssessment.
 */
@Component
public class JpaIntegrityAssessmentRepositoryAdapter implements IntegrityAssessmentRepositoryPort {

    private final IntegrityAssessmentJpaRepository repository;
    private final IntegrityAssessmentReferenceValidation validation;

    public JpaIntegrityAssessmentRepositoryAdapter(IntegrityAssessmentJpaRepository repository, IntegrityAssessmentReferenceValidation validation) {
        this.repository = Objects.requireNonNull(repository, "IntegrityAssessmentJpaRepository must not be null.");
        this.validation = Objects.requireNonNull(validation);
    }

    @Override
    @Transactional
    public IntegrityAssessment save(IntegrityAssessment model) {
        Objects.requireNonNull(model);
        var previous=repository.findByIdForUpdate(model.id()).map(IntegrityPersistenceMapper::toDomain).orElse(null);
        validation.validate(model,previous);
        return IntegrityPersistenceMapper.toDomain(repository.saveAndFlush(IntegrityPersistenceMapper.toEntity(model)));
    }

    @Override
    public Optional<IntegrityAssessment> findById(String id) {
        return repository.findById(id).map(IntegrityPersistenceMapper::toDomain);
    }
}
