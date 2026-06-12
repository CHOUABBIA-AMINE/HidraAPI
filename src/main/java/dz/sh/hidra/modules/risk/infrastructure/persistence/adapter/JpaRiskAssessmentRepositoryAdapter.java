/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaRiskAssessmentRepositoryAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : risk
 * @Package     : dz.sh.hidra.modules.risk.infrastructure.persistence.adapter
 *
 * @Description : Database-backed adapter for RiskAssessment.
 *
 */
package dz.sh.hidra.modules.risk.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.risk.application.port.out.RiskAssessmentRepositoryPort;
import dz.sh.hidra.modules.risk.domain.model.RiskAssessment;
import dz.sh.hidra.modules.risk.infrastructure.persistence.mapper.RiskPersistenceMapper;
import dz.sh.hidra.modules.risk.infrastructure.persistence.repository.RiskAssessmentJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

/**
 * Database-backed repository adapter for RiskAssessment.
 */
@Component
public class JpaRiskAssessmentRepositoryAdapter implements RiskAssessmentRepositoryPort {

    private final RiskAssessmentJpaRepository repository;

    public JpaRiskAssessmentRepositoryAdapter(RiskAssessmentJpaRepository repository) {
        this.repository = Objects.requireNonNull(repository, "RiskAssessmentJpaRepository must not be null.");
    }

    @Override
    public RiskAssessment save(RiskAssessment model) {
        return RiskPersistenceMapper.toDomain(repository.save(RiskPersistenceMapper.toEntity(model)));
    }

    @Override
    public Optional<RiskAssessment> findById(String id) {
        return repository.findById(id).map(RiskPersistenceMapper::toDomain);
    }
}
