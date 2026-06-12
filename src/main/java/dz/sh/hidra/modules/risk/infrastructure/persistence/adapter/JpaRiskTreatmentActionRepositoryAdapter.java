/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaRiskTreatmentActionRepositoryAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : risk
 * @Package     : dz.sh.hidra.modules.risk.infrastructure.persistence.adapter
 *
 * @Description : Database-backed adapter for RiskTreatmentAction.
 *
 */
package dz.sh.hidra.modules.risk.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.risk.application.port.out.RiskTreatmentActionRepositoryPort;
import dz.sh.hidra.modules.risk.domain.model.RiskTreatmentAction;
import dz.sh.hidra.modules.risk.infrastructure.persistence.mapper.RiskPersistenceMapper;
import dz.sh.hidra.modules.risk.infrastructure.persistence.repository.RiskTreatmentActionJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

/**
 * Database-backed repository adapter for RiskTreatmentAction.
 */
@Component
public class JpaRiskTreatmentActionRepositoryAdapter implements RiskTreatmentActionRepositoryPort {

    private final RiskTreatmentActionJpaRepository repository;

    public JpaRiskTreatmentActionRepositoryAdapter(RiskTreatmentActionJpaRepository repository) {
        this.repository = Objects.requireNonNull(repository, "RiskTreatmentActionJpaRepository must not be null.");
    }

    @Override
    public RiskTreatmentAction save(RiskTreatmentAction model) {
        return RiskPersistenceMapper.toDomain(repository.save(RiskPersistenceMapper.toEntity(model)));
    }

    @Override
    public Optional<RiskTreatmentAction> findById(String id) {
        return repository.findById(id).map(RiskPersistenceMapper::toDomain);
    }
}
