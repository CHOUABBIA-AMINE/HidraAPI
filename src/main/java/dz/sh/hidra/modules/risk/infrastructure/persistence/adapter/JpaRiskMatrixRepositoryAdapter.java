/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaRiskMatrixRepositoryAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : risk
 * @Package     : dz.sh.hidra.modules.risk.infrastructure.persistence.adapter
 *
 * @Description : Database-backed adapter for RiskMatrix.
 *
 */
package dz.sh.hidra.modules.risk.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.risk.application.port.out.RiskMatrixRepositoryPort;
import dz.sh.hidra.modules.risk.domain.model.RiskMatrix;
import dz.sh.hidra.modules.risk.infrastructure.persistence.mapper.RiskPersistenceMapper;
import dz.sh.hidra.modules.risk.infrastructure.persistence.repository.RiskMatrixJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

/**
 * Database-backed repository adapter for RiskMatrix.
 */
@Component
public class JpaRiskMatrixRepositoryAdapter implements RiskMatrixRepositoryPort {

    private final RiskMatrixJpaRepository repository;

    public JpaRiskMatrixRepositoryAdapter(RiskMatrixJpaRepository repository) {
        this.repository = Objects.requireNonNull(repository, "RiskMatrixJpaRepository must not be null.");
    }

    @Override
    public RiskMatrix save(RiskMatrix model) {
        return RiskPersistenceMapper.toDomain(repository.save(RiskPersistenceMapper.toEntity(model)));
    }

    @Override
    public Optional<RiskMatrix> findById(String id) {
        return repository.findById(id).map(RiskPersistenceMapper::toDomain);
    }
}
