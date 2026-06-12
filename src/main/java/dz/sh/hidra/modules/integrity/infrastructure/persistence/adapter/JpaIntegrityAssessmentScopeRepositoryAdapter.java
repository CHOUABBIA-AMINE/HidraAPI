/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaIntegrityAssessmentScopeRepositoryAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : integrity
 * @Package     : dz.sh.hidra.modules.integrity.infrastructure.persistence.adapter
 *
 * @Description : Database-backed adapter for IntegrityAssessmentScope.
 *
 */
package dz.sh.hidra.modules.integrity.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.integrity.application.port.out.IntegrityAssessmentScopeRepositoryPort;
import dz.sh.hidra.modules.integrity.domain.model.IntegrityAssessmentScope;
import dz.sh.hidra.modules.integrity.infrastructure.persistence.mapper.IntegrityPersistenceMapper;
import dz.sh.hidra.modules.integrity.infrastructure.persistence.repository.IntegrityAssessmentScopeJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

/**
 * Database-backed repository adapter for IntegrityAssessmentScope.
 */
@Component
public class JpaIntegrityAssessmentScopeRepositoryAdapter implements IntegrityAssessmentScopeRepositoryPort {

    private final IntegrityAssessmentScopeJpaRepository repository;

    public JpaIntegrityAssessmentScopeRepositoryAdapter(IntegrityAssessmentScopeJpaRepository repository) {
        this.repository = Objects.requireNonNull(repository, "IntegrityAssessmentScopeJpaRepository must not be null.");
    }

    @Override
    public IntegrityAssessmentScope save(IntegrityAssessmentScope model) {
        return IntegrityPersistenceMapper.toDomain(repository.save(IntegrityPersistenceMapper.toEntity(model)));
    }

    @Override
    public Optional<IntegrityAssessmentScope> findById(String id) {
        return repository.findById(id).map(IntegrityPersistenceMapper::toDomain);
    }
}
