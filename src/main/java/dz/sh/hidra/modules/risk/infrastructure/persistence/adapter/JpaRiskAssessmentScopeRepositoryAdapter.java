/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaRiskAssessmentScopeRepositoryAdapter
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Adapter
 * @Layer       : Infrastructure
 * @Module      : risk
 * @Package     : dz.sh.hidra.modules.risk.infrastructure.persistence.adapter
 *
 * @Description : Database-backed adapter for RiskAssessmentScope.
 *
 */
package dz.sh.hidra.modules.risk.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.risk.application.port.out.RiskAssessmentScopeRepositoryPort;
import dz.sh.hidra.modules.risk.domain.model.RiskAssessmentScope;
import dz.sh.hidra.modules.risk.infrastructure.persistence.mapper.RiskPersistenceMapper;
import dz.sh.hidra.modules.risk.infrastructure.persistence.repository.RiskAssessmentScopeJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

/**
 * Database-backed repository adapter for RiskAssessmentScope.
 */
@Component
public class JpaRiskAssessmentScopeRepositoryAdapter implements RiskAssessmentScopeRepositoryPort {

    private final RiskAssessmentScopeJpaRepository repository;

    public JpaRiskAssessmentScopeRepositoryAdapter(RiskAssessmentScopeJpaRepository repository) {
        this.repository = Objects.requireNonNull(repository, "RiskAssessmentScopeJpaRepository must not be null.");
    }

    @Override
    public RiskAssessmentScope save(RiskAssessmentScope model) {
        return RiskPersistenceMapper.toDomain(repository.save(RiskPersistenceMapper.toEntity(model)));
    }

    @Override
    public Optional<RiskAssessmentScope> findById(String id) {
        return repository.findById(id).map(RiskPersistenceMapper::toDomain);
    }
}
