/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaRiskTreatmentPlanRepositoryAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : risk
 * @Package     : dz.sh.hidra.modules.risk.infrastructure.persistence.adapter
 *
 * @Description : Database-backed adapter for RiskTreatmentPlan.
 *
 */
package dz.sh.hidra.modules.risk.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.risk.application.port.out.RiskTreatmentPlanRepositoryPort;
import dz.sh.hidra.modules.risk.domain.model.RiskTreatmentPlan;
import dz.sh.hidra.modules.risk.infrastructure.persistence.mapper.RiskPersistenceMapper;
import dz.sh.hidra.modules.risk.infrastructure.persistence.repository.RiskTreatmentPlanJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

/**
 * Database-backed repository adapter for RiskTreatmentPlan.
 */
@Component
public class JpaRiskTreatmentPlanRepositoryAdapter implements RiskTreatmentPlanRepositoryPort {

    private final RiskTreatmentPlanJpaRepository repository;

    public JpaRiskTreatmentPlanRepositoryAdapter(RiskTreatmentPlanJpaRepository repository) {
        this.repository = Objects.requireNonNull(repository, "RiskTreatmentPlanJpaRepository must not be null.");
    }

    @Override
    public RiskTreatmentPlan save(RiskTreatmentPlan model) {
        return RiskPersistenceMapper.toDomain(repository.save(RiskPersistenceMapper.toEntity(model)));
    }

    @Override
    public Optional<RiskTreatmentPlan> findById(String id) {
        return repository.findById(id).map(RiskPersistenceMapper::toDomain);
    }
}
