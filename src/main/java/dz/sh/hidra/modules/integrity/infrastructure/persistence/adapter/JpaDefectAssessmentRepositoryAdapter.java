/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaDefectAssessmentRepositoryAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : integrity
 * @Package     : dz.sh.hidra.modules.integrity.infrastructure.persistence.adapter
 *
 * @Description : Database-backed adapter for DefectAssessment.
 *
 */
package dz.sh.hidra.modules.integrity.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.integrity.application.port.out.DefectAssessmentRepositoryPort;
import dz.sh.hidra.modules.integrity.domain.model.DefectAssessment;
import dz.sh.hidra.modules.integrity.infrastructure.persistence.mapper.IntegrityPersistenceMapper;
import dz.sh.hidra.modules.integrity.infrastructure.persistence.repository.DefectAssessmentJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

/**
 * Database-backed repository adapter for DefectAssessment.
 */
@Component
public class JpaDefectAssessmentRepositoryAdapter implements DefectAssessmentRepositoryPort {

    private final DefectAssessmentJpaRepository repository;

    public JpaDefectAssessmentRepositoryAdapter(DefectAssessmentJpaRepository repository) {
        this.repository = Objects.requireNonNull(repository, "DefectAssessmentJpaRepository must not be null.");
    }

    @Override
    public DefectAssessment save(DefectAssessment model) {
        return IntegrityPersistenceMapper.toDomain(repository.save(IntegrityPersistenceMapper.toEntity(model)));
    }

    @Override
    public Optional<DefectAssessment> findById(String id) {
        return repository.findById(id).map(IntegrityPersistenceMapper::toDomain);
    }
}
