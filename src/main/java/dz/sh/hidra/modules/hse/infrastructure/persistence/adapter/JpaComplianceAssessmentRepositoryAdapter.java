/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaComplianceAssessmentRepositoryAdapter
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Adapter
 * @Layer       : Infrastructure
 * @Module      : hse
 * @Package     : dz.sh.hidra.modules.hse.infrastructure.persistence.adapter
 *
 * @Description : Database-backed adapter for ComplianceAssessment.
 *
 */
package dz.sh.hidra.modules.hse.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.hse.application.port.out.ComplianceAssessmentRepositoryPort;
import dz.sh.hidra.modules.hse.domain.model.ComplianceAssessment;
import dz.sh.hidra.modules.hse.infrastructure.persistence.mapper.HsePersistenceMapper;
import dz.sh.hidra.modules.hse.infrastructure.persistence.repository.ComplianceAssessmentJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

/**
 * Database-backed repository adapter for ComplianceAssessment.
 */
@Component
public class JpaComplianceAssessmentRepositoryAdapter implements ComplianceAssessmentRepositoryPort {

    private final ComplianceAssessmentJpaRepository repository;

    public JpaComplianceAssessmentRepositoryAdapter(ComplianceAssessmentJpaRepository repository) {
        this.repository = Objects.requireNonNull(repository, "ComplianceAssessmentJpaRepository must not be null.");
    }

    @Override
    public ComplianceAssessment save(ComplianceAssessment model) {
        return HsePersistenceMapper.toDomain(repository.save(HsePersistenceMapper.toEntity(model)));
    }

    @Override
    public Optional<ComplianceAssessment> findById(String id) {
        return repository.findById(id).map(HsePersistenceMapper::toDomain);
    }
}
