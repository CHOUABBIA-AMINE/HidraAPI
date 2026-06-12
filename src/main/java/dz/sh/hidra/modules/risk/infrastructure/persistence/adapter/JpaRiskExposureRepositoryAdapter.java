/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaRiskExposureRepositoryAdapter
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Adapter
 * @Layer       : Infrastructure
 * @Module      : risk
 * @Package     : dz.sh.hidra.modules.risk.infrastructure.persistence.adapter
 *
 * @Description : Database-backed adapter for RiskExposure.
 *
 */
package dz.sh.hidra.modules.risk.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.risk.application.port.out.RiskExposureRepositoryPort;
import dz.sh.hidra.modules.risk.domain.model.RiskExposure;
import dz.sh.hidra.modules.risk.infrastructure.persistence.mapper.RiskPersistenceMapper;
import dz.sh.hidra.modules.risk.infrastructure.persistence.repository.RiskExposureJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

/**
 * Database-backed repository adapter for RiskExposure.
 */
@Component
public class JpaRiskExposureRepositoryAdapter implements RiskExposureRepositoryPort {

    private final RiskExposureJpaRepository repository;

    public JpaRiskExposureRepositoryAdapter(RiskExposureJpaRepository repository) {
        this.repository = Objects.requireNonNull(repository, "RiskExposureJpaRepository must not be null.");
    }

    @Override
    public RiskExposure save(RiskExposure model) {
        return RiskPersistenceMapper.toDomain(repository.save(RiskPersistenceMapper.toEntity(model)));
    }

    @Override
    public Optional<RiskExposure> findById(String id) {
        return repository.findById(id).map(RiskPersistenceMapper::toDomain);
    }
}
